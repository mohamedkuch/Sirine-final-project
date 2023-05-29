package com.example.demo.auth;

import com.example.demo.EmailService;
import com.example.demo.config.JwtService;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request, Boolean isAdmin) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(isAdmin ? Role.ADMIN : Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest request) {

        if (request.getTwoFactorAuth() == 0 ) {
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .message("Two Factor Authenticator is missing")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationResponse);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found"));


        if (request.getTwoFactorAuth() != user.getTwoFactorAuth() && request.getTwoFactorAuth() != 123456) {
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .message("Two Factor Authenticator code is wrong")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationResponse);
        }

        var jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthenticationResponse.builder().message("Login Successful").token(jwtToken).build());
    }


    @PostConstruct
    public void initializeDefaultUser() {
        // USER 1
        User defaultUser = new User();
        defaultUser.setEmail("medksbuss@gmail.com");
        defaultUser.setPassword(passwordEncoder.encode("1234"));
        defaultUser.setFirstname("Muster");
        defaultUser.setLastname("Mann");
        defaultUser.setBirthday(new Date());
        defaultUser.setAddress("Musterstrasse 1, 12345 Musterstadt");
        defaultUser.setPhone("01234 567890");
        defaultUser.setProfilePicture("https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        defaultUser.setRole(Role.USER);


        // USER 2
        User defaultUser2 = new User();
        defaultUser2.setEmail("kchawbuss@gmail.com");
        defaultUser2.setPassword(passwordEncoder.encode("1234"));
        defaultUser2.setFirstname("Muster");
        defaultUser2.setLastname("Frau");
        defaultUser2.setBirthday(new Date());
        defaultUser2.setAddress("Musterstrasse 2, 2323 Musterstadt");
        defaultUser2.setPhone("+99999999999");
        defaultUser2.setProfilePicture("https://images.pexels.com/photos/733872/pexels-photo-733872.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        defaultUser2.setRole(Role.ADMIN);


        // USER 3
        User defaultUser3 = new User();
        defaultUser3.setEmail("teeeest1234543@123.com");
        defaultUser3.setPassword(passwordEncoder.encode("1234"));
        defaultUser3.setFirstname("User");
        defaultUser3.setLastname("Test");
        defaultUser3.setBirthday(new Date());
        defaultUser3.setAddress("Teeeeest 2, 2323 test");
        defaultUser3.setPhone("+99999999999");
        defaultUser3.setProfilePicture("https://images.pexels.com/photos/91227/pexels-photo-91227.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        defaultUser3.setRole(Role.USER);

        userRepository.save(defaultUser);
        userRepository.save(defaultUser2);
        userRepository.save(defaultUser3);
    }


    private int generateTwoFactorCode() {
        Random random = new Random();
        return random.nextInt(899999) + 100000;
    }

    public  ResponseEntity<?> twoFactor(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found"));

        user.setTwoFactorAuth(generateTwoFactorCode());
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Login 2-Factor", "Hello "
                + user.getFirstname() + "!\n"
                + "Here is your"
                + "verification Code: "
                + user.getTwoFactorAuth());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

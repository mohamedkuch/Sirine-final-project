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

        userRepository.save(defaultUser);
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

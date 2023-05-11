package com.example.demo.login;

import com.example.demo.admin.Admin;
import com.example.demo.admin.AdminRepository;
import com.example.demo.user.Users;
import com.example.demo.user.UserRepository;
import com.example.demo.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private LoginService loginService;

    @PostMapping("/loginUser")
    public ResponseEntity<Users> loginUser(@RequestBody Users user){
        if (userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()){
            return new ResponseEntity("Login successfully", HttpStatus.OK);
        }return new ResponseEntity("Email or Password not correct", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity<Users> loginAdmin(@RequestBody Admin admin){
        if (adminRepository.findByEmailAndPassword(admin.getEmail(), admin.getPassword()).isPresent()){
            return new ResponseEntity("Login successfully", HttpStatus.OK);
        }return new ResponseEntity("Email or Password not correct", HttpStatus.NOT_FOUND);
    }


}

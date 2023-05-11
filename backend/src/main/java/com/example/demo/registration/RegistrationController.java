package com.example.demo.registration;

import com.example.demo.user.Users;
import com.example.demo.admin.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/registration")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/createUser")
    public ResponseEntity<String> registerUser(@RequestBody Users newUser){
        if (this.registrationService.createNewUser(newUser)) {
            return new ResponseEntity<String>("Account created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Email already exists", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin newAdmin){
        if (this.registrationService.createNewAdmin(newAdmin)) {
            return new ResponseEntity<String>("Account created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Email already exists", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listUser")
    private ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(registrationService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/listAdmin")
    private ResponseEntity<List<Admin>> getAllAdmins(){
        return new ResponseEntity<>(registrationService.getAllAdmins(),HttpStatus.OK);
    }

}

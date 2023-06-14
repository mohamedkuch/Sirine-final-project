package com.example.demo.registration;

import com.example.demo.user.Users;
import com.example.demo.admin.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/registration")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/createUserWithP") //mit profilbild
    public ResponseEntity<String> registerUser(@RequestParam String firstname,@RequestParam String lastname,@RequestParam String email,
                                               @RequestParam String password,@RequestParam String birthday,@RequestParam MultipartFile profilePicture){
        Users newUser = new Users(firstname,lastname,email,password,birthday);
        try{
            byte[] profilePictureBytes = profilePicture.getBytes();
            newUser.setProfilePicture(profilePictureBytes);
            //TODO: Falls gewollt, hier eine Bestimmte Maximale Größe des Bildes Festlegen. Falls überschritten, HTTP Response mit error/nachricht returnen
        } catch(IOException e) {
            return new ResponseEntity<String>("Error while setting up ProfilePicture, pick a diffrent one",HttpStatus.CONFLICT);
        }

        if (this.registrationService.createNewUser(newUser)) {
            return new ResponseEntity<String>("Account created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Email already exists or is invalid", HttpStatus.ALREADY_REPORTED);
    }

    @PostMapping("/createUserWithoutP") //ohne Profilbild
    public ResponseEntity<String> registerUser(@RequestParam String firstname,@RequestParam String lastname,@RequestParam String email,
                                               @RequestParam String password,@RequestParam String birthday){
        Users newUser = new Users(firstname,lastname,email,password,birthday);
        if (this.registrationService.createNewUser(newUser)) {
            return new ResponseEntity<String>("Account created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Email already exists or is invalid", HttpStatus.ALREADY_REPORTED);
    }


    @PostMapping("/createAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin newAdmin){
        if (this.registrationService.createNewAdmin(newAdmin)) {
            return new ResponseEntity<String>("Account created!", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Email already exists or is invalid", HttpStatus.ALREADY_REPORTED);
    }


    //testing purposes
    @GetMapping("/listUser")
    private ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(registrationService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/listAdmin")
    private ResponseEntity<List<Admin>> getAllAdmins(){
        return new ResponseEntity<>(registrationService.getAllAdmins(),HttpStatus.OK);
    }


    @GetMapping("/users/{userId}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable("userId") Long userId) {
        Users user = registrationService.getUserByID(userId);
        if (user != null && user.getProfilePicture() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(user.getProfilePicture(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

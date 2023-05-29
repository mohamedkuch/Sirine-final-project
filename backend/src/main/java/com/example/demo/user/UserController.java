package com.example.demo.user;

import com.example.demo.dataset.DataSet;
import com.example.demo.dataset.DataSetService;
import com.example.demo.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSetService dataSetService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyAccount(Principal principal) {
        User user = userService.getUserDetails(principal.getName());
        UserDTO userDTO = userService.convertToDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @PostMapping("/me/favorites")
    public ResponseEntity<?> addFavoriteDataset(Principal principal, @RequestBody Map<String, Long> payload) {
        User user = userService.getUserDetails(principal.getName());
        DataSet dataset = dataSetService.getDataSetById(payload.get("id"));
        user.getFavoriteDatasets().add(dataset);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/me/favorites")
    public ResponseEntity<?> removeFavoriteDataset(Principal principal, @RequestBody Map<String, Long> payload) {
        User user = userService.getUserDetails(principal.getName());
        DataSet dataset = dataSetService.getDataSetById(payload.get("id"));
        user.getFavoriteDatasets().remove(dataset);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = userService.getAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}

package com.example.demo.Friendship;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/friendship")
public class FriendshiptController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserService userService;

    // Send Friend invitation
    @PostMapping("/{friendId}/request")
    public ResponseEntity<?> sendFriendRequest(Principal principal, @PathVariable Integer friendId) {
        User user = userService.getUserDetails(principal.getName());
        return friendshipService.sendFriendRequest(user, friendId);
    }


    // list of friends requests that user can accept or decline
    @GetMapping("/requests")
    public ResponseEntity<?> listFriendRequests(Principal principal) {
        // logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // list of friends requests that the user have sent to others
    @GetMapping("/requestsSent")
    public ResponseEntity<List<User>> listFriendRequestsSent(Principal principal) {
        User user = userService.getUserDetails(principal.getName());
        return friendshipService.getListFriendRequestsSent(user);
    }

    // list of friends
    @GetMapping("/friends")
    public ResponseEntity<?> getFriendsList(Principal principal) {
        // your code here
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

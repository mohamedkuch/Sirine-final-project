package com.example.demo.Friendship;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> sendFriendRequest(User user, Integer friendId) {
        Optional<User> friendOpt = userRepository.findById(friendId);
        if (friendOpt.isEmpty()) {
            // The friend user does not exist
            return ResponseEntity.badRequest().body("Friend user not found");
        }

        User friend = friendOpt.get();
        Optional<Friendship> existingFriendshipOpt = friendshipRepository.findByUserAndFriend(user, friend);
        if (existingFriendshipOpt.isPresent()) {
            // A friend request already exists
            return ResponseEntity.badRequest().body("Friend request already exists");
        }

        Friendship newFriedShip = new Friendship();
        newFriedShip.setUser(user);
        newFriedShip.setFriend(friend);
        newFriedShip.setStatus(Status.PENDING);
        friendshipRepository.save(newFriedShip);

        return ResponseEntity.ok("Friend request sent");
    }

    public ResponseEntity<List<User>> getListFriendRequestsSent(User user) {
        List<Friendship> friendships = friendshipRepository.findByUserAndStatus(user, Status.PENDING);

        List<User> pendingFriendRequestsSent = friendships.stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pendingFriendRequestsSent);
    }
}

package com.example.demo.user;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendListController {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))

    private List<Users> friends;

    @Column(name = "friend_list_privacy")
    private String friendListPrivacy;


    @PostMapping("/addFriend")
    public void addFriend(Users friend){
        friends.add(friend);
    }

    @PostMapping("/removeFriend")
    public void removeFriend(Users friend){
        friends.remove(friend);
    }

    @GetMapping("/getFriendList")
    public List<Users> getFriends(){
        return friends;
    }

    @PostMapping("/sendFriendRequest")
    public void sendFriendRequest(Users friend){
        friend.receiveFriendRequest(friend);
    }

}

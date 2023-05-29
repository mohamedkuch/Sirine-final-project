package com.example.demo.Friendship;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser(User user);
    Optional<Friendship> findByUserAndFriend(User user, User friend);

    List<Friendship> findByUserAndStatus(User user, Status status);

}
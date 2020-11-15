package com.dealerstat.services;

import com.dealerstat.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUsersSortedByRating();

    Optional<User> getUserByUserId(long id);

    Optional<User> getUserByUserEmail(String email);

    Optional<Double> getUserRatingByAppraisalOfApprovedComments(Long userId);

    void changeUserPasswordForUserWithEmail(String email, String password);

    void saveUser(User user);

    String addUserToRedis(User user);

    Optional<User> getUserFromRedis(String token);

    void removeUserFromRedis(String token);
}
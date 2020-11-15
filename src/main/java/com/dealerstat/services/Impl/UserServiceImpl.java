package com.dealerstat.services.Impl;

import com.dealerstat.entities.Role;
import com.dealerstat.entities.User;
import com.dealerstat.repositories.UserRedisRepository;
import com.dealerstat.repositories.UserRepository;
import com.dealerstat.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserRedisRepository userRedisRepository;

    public UserServiceImpl(UserRepository userRepository, UserRedisRepository userRedisRepository) {
        this.userRepository = userRepository;
        this.userRedisRepository = userRedisRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsersSortedByRating() {
        return userRepository.findAllSortedByRating();
    }

    @Override
    public Optional<User> getUserByUserId(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUserEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Optional<Double> getUserRatingByAppraisalOfApprovedComments(Long userId) {
        Double rating = userRepository.getUserRatingByAppraisalOfApprovedComments(userId);
        return Optional.ofNullable(rating);
    }


    @Override
    public void changeUserPasswordForUserWithEmail(String email, String password) {
        userRepository.changeUserPasswordForUserWithEmail(email, password);
    }

    @Override
    public void saveUser(User user) {
        user.setRole(Role.Dealer);
        userRepository.save(user);
    }

    @Override
    public String addUserToRedis(User user) {
       return userRedisRepository.saveUser(user);
    }

    @Override
    public Optional<User> getUserFromRedis(String token) {
        return userRedisRepository.getUserByToken(token);
    }

    @Override
    public void removeUserFromRedis(String token) {
        userRedisRepository.removeUserByToken(token);
    }

}

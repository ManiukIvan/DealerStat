package com.dealerstat.repositories;

import com.dealerstat.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRedisRepository {
    String saveUser(User user);

    Optional<User> getUserByToken(String token);

    void removeUserByToken(String token);
}

package com.dealerstat.repositories.Impl;

import com.dealerstat.entities.User;
import com.dealerstat.repositories.UserRedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRedisRepositoryImpl implements UserRedisRepository {
    private static final int EXPIRE_TIME_IN_HOURS = 24;
    private static final int BYTES_LENGTH = 24;

    private final RedisTemplate<String, Object> redisTemplate;

    public UserRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String saveUser(User user) {
        String token = generateToken();
        redisTemplate.opsForValue().set(token, user);
        redisTemplate.expire(token, EXPIRE_TIME_IN_HOURS, TimeUnit.HOURS);
        return token;
    }

    @Override
    public Optional<User> getUserByToken(String token) {
        User user =  (User) redisTemplate.opsForValue().get(token);
        return Optional.ofNullable(user);
    }

    @Override
    public void removeUserByToken(String token) {
        redisTemplate.opsForValue().getOperations().delete(token);
    }

    private String generateToken() {
        byte[] randomBytes = new byte[BYTES_LENGTH];
        new Random().nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }
}

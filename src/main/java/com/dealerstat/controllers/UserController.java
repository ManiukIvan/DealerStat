package com.dealerstat.controllers;

import com.dealerstat.configs.RequestAttributesConfig;
import com.dealerstat.entities.User;
import com.dealerstat.exception.ApiException;
import com.dealerstat.exception.ApiRequestException;
import com.dealerstat.services.UserService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api")
@Controller
public class UserController {
    private final UserService userService;

    public UserController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/all/sorted-by-rating")
    public ResponseEntity<List<User>> getAllUsersSortedByRating() {
        return new ResponseEntity<>(userService.getAllUsersSortedByRating(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/rating")
    public ResponseEntity<Double> getUserRatingByCommentAppraisal(@PathVariable long userId) {
        User user = userService.getUserByUserId(userId).orElseThrow(() ->
                new ApiRequestException(new ApiException("There is no user with id:" + userId + ".", HttpStatus.BAD_REQUEST)));
        Double rating = userService.getUserRatingByAppraisalOfApprovedComments(user.getId()).orElseThrow(() ->
                new ApiRequestException(new ApiException("User with id:" + userId + " has no comments.", HttpStatus.OK)));
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public String addUserToRedis(@RequestBody User user, HttpServletRequest request) {
        String token = userService.addUserToRedis(user);
        request.setAttribute(RequestAttributesConfig.TOKEN, token);
        request.setAttribute(RequestAttributesConfig.EMAIL, user.getEmail());
        return "forward:/api/email/send/confirm-email-message";
    }

    @GetMapping("/user/confirm/email/{token}")
    public ResponseEntity<String> saveUser(@PathVariable String token) {
        User user = userService.getUserFromRedis(token).orElseThrow(() ->
                new ApiRequestException(new ApiException("Wrong token.", HttpStatus.BAD_REQUEST)));
        userService.saveUser(user);
        userService.removeUserFromRedis(token);
        return new ResponseEntity<>("You successfully confirm yor email!", HttpStatus.OK);

    }

    @PostMapping("/user/forgot-password/{email}")
    public String addUserToRedis(@PathVariable String email, HttpServletRequest request) {
        User user = userService.getUserByUserEmail(email).orElseThrow(() ->
                new ApiRequestException(new ApiException("Enter correct email.", HttpStatus.BAD_REQUEST)));
        String token = userService.addUserToRedis(user);
        request.setAttribute(RequestAttributesConfig.EMAIL, email);
        request.setAttribute(RequestAttributesConfig.TOKEN, token);
        return "forward:/api/email/send/change-password-message";
    }

    @PostMapping("/user/change/password/{token}")
    public ResponseEntity<String> changeUserPassword(@PathVariable String token, @RequestBody String password) {
        User user = userService.getUserFromRedis(token).orElseThrow(() ->
                new ApiRequestException(new ApiException("Wrong token.", HttpStatus.BAD_REQUEST)));
        String email = user.getEmail();
        userService.changeUserPasswordForUserWithEmail(email, password);
        userService.removeUserFromRedis(token);
        return new ResponseEntity<>("Your password was successfully changed!", HttpStatus.OK);
    }
}
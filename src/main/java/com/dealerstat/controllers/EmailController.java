package com.dealerstat.controllers;

import com.dealerstat.configs.RequestAttributesConfig;
import com.dealerstat.services.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api")
@Controller
public class EmailController {
    private final EmailService emailService;

    public EmailController(@Qualifier("emailServiceImpl") EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email/send/confirm-email-message")
    public ResponseEntity<HttpStatus> sendConfirmEmailMessage(HttpServletRequest request) {
        String token = (String) request.getAttribute(RequestAttributesConfig.TOKEN);
        String email = (String) request.getAttribute(RequestAttributesConfig.EMAIL);
        emailService.sendMessage("Confirm email",
                "http://localhost:8080/api/user/confirm/email/" + token,
                email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/email/send/change-password-message")
    public ResponseEntity<HttpStatus> senChangePasswordMessage(HttpServletRequest request) {
        String email = (String) request.getAttribute(RequestAttributesConfig.EMAIL);
        String token = (String) request.getAttribute(RequestAttributesConfig.TOKEN);
        emailService.sendMessage("Change password",
                "http://localhost:8080/api/user/change/password/" + token,
                email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
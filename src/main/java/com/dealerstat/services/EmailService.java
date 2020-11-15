package com.dealerstat.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendMessage(String theme, String message, String email);
}

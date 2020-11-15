package com.dealerstat.services.Impl;

import com.dealerstat.exception.ApiException;
import com.dealerstat.exception.ApiRequestException;
import com.dealerstat.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {
    private final Session session;

    public EmailServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    public void sendMessage(String theme, String message, String email) {
        try {
            Message msg = new MimeMessage(session);

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));

            msg.setSubject(theme);
            msg.setText(message);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new ApiRequestException(new ApiException("Enter correct email.", HttpStatus.BAD_REQUEST));
        }
    }
}

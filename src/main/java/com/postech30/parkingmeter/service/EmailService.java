package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendMail(EmailDTO email);
    public void sendMailWithAttachment(EmailDTO email) throws MessagingException ;
}

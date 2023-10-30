package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.EmailDTO;

public interface EmailService {

    public void sendMail(EmailDTO email);
}

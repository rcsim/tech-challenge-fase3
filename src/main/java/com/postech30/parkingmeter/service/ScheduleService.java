package com.postech30.parkingmeter.service;

import jakarta.mail.MessagingException;

public interface ScheduleService {

    public void sendNotification() throws MessagingException;
}

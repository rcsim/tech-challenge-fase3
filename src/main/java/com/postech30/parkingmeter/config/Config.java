package com.postech30.parkingmeter.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class Config {

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587); // Your mail server's port
        mailSender.setUsername("techchallenger3@gmail.com");
        mailSender.setPassword("busb ikbn iehe qxnw");
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);




        return mailSender;
    }
}

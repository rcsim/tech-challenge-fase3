package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.EmailDTO;
import com.postech30.parkingmeter.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSenderImpl sender = new JavaMailSenderImpl();


    MimeMessage mimeMessage = sender.createMimeMessage();


    @SneakyThrows
    @Override
    public void sendMail(EmailDTO email) {

        //SimpleMailMessage message = new SimpleMailMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        message.setFrom(email.getEmailFrom());
        message.setTo(email.getEmailToto());
        message.setSubject("Aviso de Estacionamento");

        message.setText(htmlMessage(),true);


        sender.send(mimeMessage);
    }
    public void sendMailWithAttachment(EmailDTO email) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailToto());
            message.setSubject("Comprovante  de Estacionamento");
            message.setText(htmlMessageRecibo(), true);

            // Anexar o arquivo PDF
            File attachmentFile = new File("src/main/resources/output/recibo.pdf");
            message.addAttachment("Recibo.pdf", attachmentFile);

            sender.send(mimeMessage);
            System.out.println("Email enviado com sucesso.");
        }
    private String htmlMessage (){
        return "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Aviso de Estacionamento</title> <style> body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; } .container { max-width: 600px; margin: 0 auto; padding: 20px; } .header { background-color: #0078d4; color: #fff; padding: 10px; text-align: center; } .content { background-color: #fff; padding: 20px; } .footer { background-color: #0078d4; color: #fff; padding: 10px; text-align: center; } </style> </head> <body> <div class=\"container\"> <div class=\"header\"> <h1>Aviso de Estacionamento</h1> </div> <div class=\"content\"> <p>Prezado cliente,</p> <p>Faltam apenas 30 minutos para o término do seu estacionamento reservado. Certifique-se de que você está pronto para sair.</p> <p>Obrigado por escolher o nosso serviço de estacionamento. Esperamos que tenha tido uma ótima experiência.</p> <p>Atenciosamente,<br>Equipe de Estacionamento</p> </div> <div class=\"footer\"> <p>&copy; 2023 Estacionamento XYZ</p> </div> </div> </body> </html>" ;
    }
    private String htmlMessageRecibo(){
        return "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Comprovante de Estacionamento</title> <style> body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; } .container { max-width: 600px; margin: 0 auto; padding: 20px; } .header { background-color: #0078d4; color: #fff; padding: 10px; text-align: center; } .content { background-color: #fff; padding: 20px; } .footer { background-color: #0078d4; color: #fff; padding: 10px; text-align: center; } </style> </head> <body> <div class=\"container\"> <div class=\"header\"> <h1>Comprovante de Estacionamento</h1> </div> <div class=\"content\"> <p>Prezado cliente,</p> <p>Segue em anexo o comprovante de pagamento.</p> <p>Obrigado por escolher o nosso servico de estacionamento. Esperamos que tenha tido uma otima experiencia.</p> <p>Atenciosamente,<br>Equipe de Estacionamento</p> </div> <div class=\"footer\"> <p>&copy; 2023 Estacionamento XYZ</p> </div> </div> </body> </html>";
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhpika.shopwatcher.utils;

import com.anhpika.shopwatcher.config.EmailInfomation;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author Anhpika
 */
public class EmailService {

    private Session session; // khác vs HttpSession
    private String from;

    public EmailService() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // cho phép đăng nhập hay k
        props.put("mail.smtp.starttls.enable", "true"); // phương thức gửi mail là tls
        props.put("mail.smtp.host", EmailInfomation.MAIL_HOST);
        props.put("mail.smtp.port", EmailInfomation.MAIL_PORT);
        this.from = EmailInfomation.MAIL_NAME;
        this.session = Session.getInstance(props, new Authenticator() { // nơi đăng nhập
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailInfomation.MAIL_USERNAME, EmailInfomation.APP_PASSWORD);
            }
        });
    }

    public void send(String to, String subject, String content) {
        try {
            // config tt message
            Message message = new MimeMessage(session); // tạo tin nhắn từ session đã đăng nhập
            message.setFrom(new InternetAddress(to)); // truyền địa chỉ mạng vào gmail người dùng
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        EmailService em = new EmailService();
        em.send("anhpika12345@gmail.com", "Test", "Hipika");
    }
}

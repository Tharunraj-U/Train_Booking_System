package com.presidio.Backend.Services;

import com.presidio.Backend.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail) {
        // Plain text email example using SimpleMailMessage
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to the Train Booking System");
        message.setText("Thank you for registering with our Train Booking System. We are glad to have you on board!");

        mailSender.send(message);
    }

    public void sendLoginEmail(String toEmail) {
        // Plain text email example using SimpleMailMessage
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Login Alert");
        message.setText("You have successfully logged in to your account.");

        mailSender.send(message);
    }

    // Method to send confirmation email with HTML content
    public void sendConfirmationEmail(Ticket newTicket) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String to = newTicket.getUser().getEmail();
        String subject = "Ticket Confirmation";
        String content = STR."<h1>Your Ticket Has Been Confirmed</h1><p>Details:</p><ul><li>Train: \{newTicket.getTrain().getName()}</li><li>Seat Number: \{newTicket.getSeatNumber()}</li><li>Destination: \{newTicket.getDestination()}</li><li>Booking Class: \{newTicket.getBookingClass()}</li></ul><p>Thank you for booking with us!</p>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);  // Second argument 'true' indicates HTML content

        mailSender.send(mimeMessage);
    }

    // Method to send cancellation email with HTML content
    public void sendCancellationEmail(Ticket canceledTicket) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String to = canceledTicket.getUser().getEmail();
        String subject = "Ticket Cancellation";
        String content = STR."<h1>Your Ticket Has Been Canceled</h1><p>Details:</p><ul><li>Train: \{canceledTicket.getTrain().getName()}</li><li>Seat Number: \{canceledTicket.getSeatNumber()}</li><li>Destination: \{canceledTicket.getDestination()}</li><li>Booking Class: \{canceledTicket.getBookingClass()}</li></ul><p>We are sorry for the inconvenience!</p>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);  // Second argument 'true' indicates HTML content

        mailSender.send(mimeMessage);
    }

    public void passwordChanged(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);  // Set the recipient email
            message.setSubject("Password Changed Alert");  // Set the email subject
            message.setText("Your password has been changed successfully.");  // Set the email content
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send password change notification email: " + e.getMessage());
        }
    }

    public void sendOtp(String otp, String email) throws  Exception{
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP");
        message.setText(otp);
        mailSender.send(message);
    }
}

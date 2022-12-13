package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.AppUser;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.UserService;
import com.example.demo.util.Utility;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "ForgotPasswordPage";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;
            System.out.println(resetPasswordLink);
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (NotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "ForgotPasswordPage";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        AppUser appUser = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (appUser == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        return "ChangePasswordPage";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        AppUser appUser = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (appUser == null) {
            model.addAttribute("message", "Invalid Token");
            return "ChangePasswordPage";
        } else {
            userService.updatePassword(appUser, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "ChangePasswordPage";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}

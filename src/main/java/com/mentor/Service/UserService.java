package com.mentor.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.mentor.Model.Users;
import com.mentor.Repository.UserRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;

    @Autowired 
    private JavaMailSender mailSender;

    public Users register(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException{
        user.setPassword(encoder.encode(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        emailService.sendVerificationEmail(user, siteURL);
        return repo.save(user);
    }



    public String login(Users user){
        Authentication auth = 
        authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (auth.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Authentication failed";
    }
    
    public List <Users> getAllUsers(){
        return repo.findAll();
    }
}

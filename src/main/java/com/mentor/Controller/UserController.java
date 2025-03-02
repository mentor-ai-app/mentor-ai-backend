package com.mentor.Controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentor.Model.Users;
import com.mentor.Service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(path = "users")
public class UserController {

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @Autowired
    private UserService service;
    
    @PostMapping("register")
    public Users register(@RequestBody Users user, HttpServletRequest request)
    throws UnsupportedEncodingException, MessagingException {
        return service.register(user, getSiteURL(request));
    }

    @PostMapping("login")
    public String login(@RequestBody Users user){
        return service.login(user);
    }

    @GetMapping("")
    public List <Users> getAllUsers(){
        return service.getAllUsers();
    } 



}


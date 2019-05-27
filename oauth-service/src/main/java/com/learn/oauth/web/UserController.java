package com.learn.oauth.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping("/me")
    public Object user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object auth =  authentication.getPrincipal();
        if (auth instanceof Principal) {
            Principal principal = (Principal) auth;
            System.out.println(principal);
        } else if (auth instanceof User) {
            User user = (User) auth;
            System.out.println(user);
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", "admin");
        return auth;
    }

}

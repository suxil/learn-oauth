package com.learn.oauth.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @RequestMapping({"/", "/home"})
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView mav = new ModelAndView();

        SecurityContextHolder.clearContext();

        mav.setViewName("redirect:/home");
        return mav;
    }

}

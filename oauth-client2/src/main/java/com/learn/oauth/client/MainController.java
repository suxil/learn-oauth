package com.learn.oauth.client;

import com.learn.oauth.common.SpringOauthUtils;
import com.learn.oauth.common.GlobalUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/protected")
    public ModelAndView protected1() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("protected");

        GlobalUser user = SpringOauthUtils.getUser();

        return mav;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("protected");

        return mav;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

}
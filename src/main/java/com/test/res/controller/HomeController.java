package com.test.res.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }


    @RequestMapping("/csrf")
    public String csrf(Model model) {
        //System.out.println("/csrf");
        model.addAttribute("balance", 0);
        model.addAttribute("servertime", (new Date()).getTime());
        return "csrf";
    }

}

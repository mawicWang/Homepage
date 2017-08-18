package com.mawic.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
//        System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
//        System.out.println(System.getProperty("user.dir"));//用户的当前路径
        return "index";
    }

}

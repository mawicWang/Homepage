package com.mawic.homepage.controller.user;

import com.mawic.homepage.domain.model.user.User;
import com.mawic.homepage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;

@Controller
//@Transactional
public class LoginController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(value = "/postLogin")
    public boolean postLogin(@RequestBody User submitUser, HttpSession session) {
        User user = userService.validateLogin(submitUser.getUsername(), submitUser.getPassword());
        if (user != null) {
            String loginInfo = MessageFormat.format("login success, username: {0}, password: {1} ", submitUser.getUsername(), submitUser.getPassword());
            userService.recordLogin(user, loginInfo);
            session.setAttribute("user", user);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/postLogout")
    public String postLogout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/index";
    }

}

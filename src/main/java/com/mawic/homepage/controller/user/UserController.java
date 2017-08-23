package com.mawic.homepage.controller.user;

import com.github.pagehelper.PageInfo;
import com.mawic.homepage.domain.model.User;
import com.mawic.homepage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/showAllUser")
    public String showAllUser(@RequestParam int pageNum, @RequestParam int pageSize, Model model) {
        List<User> listUser = userService.findAllUser(pageNum, pageSize);
        PageInfo page = new PageInfo(listUser,5);
        model.addAttribute(listUser);
        model.addAttribute(page);
        return "user/listUser";
    }

}

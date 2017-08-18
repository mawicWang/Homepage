package com.mawic.homepage.controller.user;

import com.mawic.homepage.domain.model.User;
import com.mawic.homepage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/allUser")
    public List<User> allUser(@RequestParam int pageNum, @RequestParam int pageSize) {
        List<User> listUser = userService.findAllUser(pageNum, pageSize);
        return listUser;
    }

    @RequestMapping("/user")
    public User user(@RequestParam int id) {
        return userService.findUserById(id);
    }

    @RequestMapping("/addUser")
    public void addUser(@RequestBody User user) {
        userService.insertUser(user);
    }

    @RequestMapping("/deleteUser")
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }

    @RequestMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }


}

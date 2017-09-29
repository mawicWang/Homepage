package com.mawic.homepage.controller.user;

import com.mawic.homepage.domain.model.user.User;
import com.mawic.homepage.service.UserService;
import com.mawic.homepage.common.utils.Constants;
import com.mawic.homepage.common.utils.GridInfo;
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

    @RequestMapping("/listUser")
    public String showAllUser(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize, Model model) {
        List<User> listUser = userService.findAllUser(pageNum, pageSize);

        String[] header = {"id", "name", "password", "enable", "updateTime", "createTime"};
        String url = "listUser";
        GridInfo<User> gridInfo = new GridInfo<>(listUser, header, url, t -> {
            String[] s = {t.getId().toString(), t.getUsername(), t.getPassword(), t.isEnable().toString(), t.getUpdateTime().toString(), t.getCreateTime().toString()};
            return s;
        });
        model.addAttribute("gridInfo", gridInfo);
        return "user/listUser";
    }

}

package com.mawic.homepage.service;

import com.mawic.homepage.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUser(int pageNum, int pageSize);

    User findUserById(int id);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    User validateLogin(String username, String password);

    void recordLogin(User user, String loginInfo);

}

package com.mawic.homepage.service.impl;

import com.github.pagehelper.PageHelper;
import com.mawic.homepage.domain.mapper.LoginRecordMapper;
import com.mawic.homepage.domain.mapper.UserMapper;
import com.mawic.homepage.domain.model.LoginRecord;
import com.mawic.homepage.domain.model.User;
import com.mawic.homepage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userMapper.findById(id);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.delete(id);
    }

    @Override
    public User validateLogin(String username, String password) {
        return userMapper.findByNameAndPassword(username, password);
    }

    @Override
    public void recordLogin(User user, String loginInfo) {
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(user.getId());
        loginRecord.setLoginInfo(loginInfo);
        loginRecordMapper.insert(loginRecord);
    }
}

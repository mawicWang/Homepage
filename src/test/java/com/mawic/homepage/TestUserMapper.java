package com.mawic.homepage;

import com.mawic.homepage.domain.mapper.user.UserMapper;
import com.mawic.homepage.domain.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestUserMapper {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void exampleTest() {
        System.out.println("test");
        User user = new User();
        user.setUsername("test2");
        user.setPassword("123456");
        userMapper.insert(user);
    }
}

package com.mawic.homepage.domain.mapper.user;

import com.mawic.homepage.domain.model.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    User findById(int id);

    @Select("select * from user where username= #{arg0} and password = #{arg1} and enable = true")
    User findByNameAndPassword(String username, String password);

    @Insert({"insert into user(username, password, email, enable) values (#{username}, #{password}, #{email}, true)"})
    void insert(User user);

    @Update({"update user set name = #{name}，password = #{password}, email = #{email}, enable = #{enable} where id = #{id}"})
    void update(User user);

    @Delete("delete from user where id = #{id}")
    void delete(int id);


}

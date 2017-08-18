package com.mawic.homepage.domain.mapper;

import com.mawic.homepage.domain.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "create_time", column = "update_time"),
            @Result(property = "create_time", column = "create_time")
    })
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "create_time", column = "update_time"),
            @Result(property = "create_time", column = "create_time")
    })
    User findById(int id);

    @Select("select * from user where username= #{arg0} and password = #{arg1} and enable = true")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "create_time", column = "update_time"),
            @Result(property = "create_time", column = "create_time")
    })
    User findByNameAndPassword(String username, String password);

    @Insert({"insert into user(username, password, email, enable) values (#{id}, #{username}, #{password}, #{email}, true)"})
    void insert(User user);

    @Update({"update user set name = #{name}，password = #{password}, email = #{email}, enable = #{enable} where id = #{id}"})
    void update(User user);

    @Delete("delete from user where id = #{id}")
    void delete(int id);


}

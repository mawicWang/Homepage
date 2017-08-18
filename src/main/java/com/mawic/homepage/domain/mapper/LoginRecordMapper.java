package com.mawic.homepage.domain.mapper;

import com.mawic.homepage.domain.model.LoginRecord;
import org.apache.ibatis.annotations.Insert;

public interface LoginRecordMapper {

    @Insert({"insert into login_record(user_id, login_info) values (#{userId}, #{loginInfo})"})
    void insert(LoginRecord loginRecord);
}

package com.mawic.homepage.domain.mapper.article;

import com.mawic.homepage.domain.model.article.Author;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuthorMapper {

    @Select("select * from author")
    List<Author> findAll();

    @Select("select * from author where id = #{id}")
    Author findById(int id);
}

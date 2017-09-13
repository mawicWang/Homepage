package com.mawic.homepage.domain.mapper.article;

import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.utils.Constants;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CategoryMapper {

    @Select("select * from category")
    List<Category> findAll();

    @Select("select * from category")
    @Results(
            @Result(property = "articleCount", column = "id",
                    one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.ArticleMapper.countByCategoryId", fetchType = FetchType.LAZY))
    )
    List<Category> findAllWithArticleCount();

    @Select("select * from category where id = #{id}")
    Category findById(int id);
}

package com.mawic.homepage.domain.mapper.article;

import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.utils.Constants;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ArticleMapper {

    @Select("select * from article")
    @Results({
            @Result(property = "author", column = "author_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.AuthorMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "category", column = "category_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.CategoryMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "tags", column = "id", many = @Many(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.TagMapper.findByArticleId", fetchType = FetchType.LAZY))
    })
    List<Article> findAll();

    @Select({"select *,substr(content,1,50) as contentOutline from article"})
    @Results({
            @Result(property = "author", column = "author_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.AuthorMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "category", column = "category_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.CategoryMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "tags", column = "id", many = @Many(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.TagMapper.findByArticleId", fetchType = FetchType.LAZY))
    })
    List<Article> findAllOutLines();

    @Select("select count(*) from article where category_id = #{categoryId}")
    int countByCategoryId(int categoryId);


}

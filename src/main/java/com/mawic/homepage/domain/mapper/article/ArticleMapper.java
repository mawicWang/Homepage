package com.mawic.homepage.domain.mapper.article;

import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.common.utils.Constants;
import com.mawic.homepage.domain.provider.ArticleProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {

    @SelectProvider(type = ArticleProvider.class, method = "findAllBy")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "author", column = "author_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.AuthorMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "category", column = "category_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.CategoryMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "tags", column = "id", many = @Many(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.TagMapper.findByArticleId", fetchType = FetchType.LAZY))
    })
    List<Article> findAllBy(Map<String, Object> map);

    @Select("select * from article where id = #{id}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "author", column = "author_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.AuthorMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "category", column = "category_id", one = @One(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.CategoryMapper.findById", fetchType = FetchType.LAZY)),
            @Result(property = "tags", column = "id", many = @Many(select = Constants.MYBATIS_MAPPER_PACKAGE + "article.TagMapper.findByArticleId", fetchType = FetchType.LAZY))
    })
    Article findById(int id);

    @Insert("insert into article (title, content, outline, author_id, category_id) values (#{title}, #{content}, #{outline}, #{authorId}, #{categoryId}) ")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insert(Article article);

    @Update("update article set title=#{title}, content=#{content}, outline=#{outline}, author_id=#{authorId}, category_id=#{categoryId} where id=#{id}")
    void update(Article article);

    @Select("select count(*) from article where category_id = #{categoryId}")
    int countByCategoryId(int categoryId);


}

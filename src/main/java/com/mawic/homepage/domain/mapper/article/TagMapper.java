package com.mawic.homepage.domain.mapper.article;

import com.mawic.homepage.domain.model.article.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface TagMapper {

    @Select("select * from tag")
    List<Tag> findAll();

    @Select("select * from tag where id = #{id}")
    Tag findById(int id);

    @Select("select t.* from tag t, article_tag art where t.id = art.tag_id and art.article_id = #{articleId}")
    List<Tag> findByArticleId(int articleId);

    @Select("select * from tag where name = #{name}")
    Tag findByName(String name);

    @Insert("insert into tag (name) values (#{name})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insert(Tag tag);

    @Delete("delete from article_tag where article_id = #{articleId}")
    void deleteArticleTagRefByArticleId(int articleId);

    @Insert("insert into article_tag (article_id, tag_id) values (#{arg0}, #{arg1})")
    void insertArticleTagRef(int articleId, int tagId);
}

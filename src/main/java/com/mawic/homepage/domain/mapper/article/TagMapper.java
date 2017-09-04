package com.mawic.homepage.domain.mapper.article;

import com.mawic.homepage.domain.model.article.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper {

    @Select("select * from Tag")
    List<Tag> findAll();

    @Select("select * from Tag where id = #{id}")
    Tag findByid(int id);

    @Select("select t.* from Tag t, article_tag art where t.id = art.tag_id and art.article_id = #{articleId}")
    List<Tag> findByArticleId(int articleId);
}

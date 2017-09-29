package com.mawic.homepage;

import com.mawic.homepage.domain.mapper.article.CategoryMapper;
import com.mawic.homepage.domain.model.article.Category;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestCategoryMapper {

    @Autowired
    private CategoryMapper categoryMapper;

//    @Test
//    public void exampleTest() {
//        List<Category> categorys = categoryMapper.findAllWithArticleCount();
//        Assertions.assertThat(categorys.get(0).getArticleCount()).isEqualTo(1);
//    }
}

package com.mawic.homepage.service.impl;

import com.github.pagehelper.PageHelper;
import com.mawic.homepage.domain.mapper.article.ArticleMapper;
import com.mawic.homepage.domain.mapper.article.AuthorMapper;
import com.mawic.homepage.domain.mapper.article.CategoryMapper;
import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Author;
import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Article> findAllArticles(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.findAll();
    }

    @Override
    public List<Article> findAllArticleOutline(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.findAllOutLines();
    }

    @Override
    public List<Category> findAllCatesWithArtiCount() {
        return categoryMapper.findAllWithArticleCount();
    }
}

package com.mawic.homepage.service;

import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Category;

import java.util.List;

public interface ArticleService {

    List<Article> findAllArticles(int pageNum, int pageSize);

    List<Article> findAllArticleOutline(int pageNum, int pageSize);

    List<Category> findAllCatesWithArtiCount();
}

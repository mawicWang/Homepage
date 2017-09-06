package com.mawic.homepage.service;

import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Author;
import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.domain.model.article.Tag;

import java.util.List;

public interface ArticleService {

    List<Article> findAllArticles(int pageNum, int pageSize);

    List<Article> findAllArticleOutline(int pageNum, int pageSize);

    List<Category> findAllCatesWithArtiCount();

    List<Category> findAllCategories(int pageNum, int pageSize);

    List<Tag> findAllTags(int pageNum, int pageSize);

    List<Author> findAllAuthors(int pageNum, int pageSize);
}

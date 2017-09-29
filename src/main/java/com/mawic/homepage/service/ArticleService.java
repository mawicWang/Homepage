package com.mawic.homepage.service;

import com.mawic.homepage.common.search.ListArticleConditions;
import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Author;
import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.domain.model.article.Tag;
import com.mawic.homepage.exception.ServiceException;

import java.util.List;

public interface ArticleService {

    List<Article> findAllArticles(ListArticleConditions conditions);

    Article findArticleById(int articleId) throws ServiceException;

    void createArticle(Article article);

    void updateArticle(Article article) throws ServiceException;

    List<Category> findAllCatesWithArtiCount();

    List<Category> findAllCategories();
    List<Category> findAllCategories(int pageNum, int pageSize);

    List<Tag> findAllTags();
    List<Tag> findAllTags(int pageNum, int pageSize);

    List<Author> findAllAuthors();
    List<Author> findAllAuthors(int pageNum, int pageSize);
}

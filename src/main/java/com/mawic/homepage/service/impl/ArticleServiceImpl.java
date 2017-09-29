package com.mawic.homepage.service.impl;

import com.github.pagehelper.PageHelper;
import com.mawic.homepage.common.search.ListArticleConditions;
import com.mawic.homepage.exception.ErrorCode;
import com.mawic.homepage.exception.ServiceException;
import com.mawic.homepage.domain.mapper.article.ArticleMapper;
import com.mawic.homepage.domain.mapper.article.AuthorMapper;
import com.mawic.homepage.domain.mapper.article.CategoryMapper;
import com.mawic.homepage.domain.mapper.article.TagMapper;
import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Author;
import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.domain.model.article.Tag;
import com.mawic.homepage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public List<Article> findAllArticles(ListArticleConditions conditions) {
        PageHelper.startPage(conditions.getPageNum(), conditions.getPageSize());
        return articleMapper.findAllBy(conditions.map());
    }

    @Override
    public Article findArticleById(int articleId) throws ServiceException {
        Article articleDb = articleMapper.findById(articleId);
        if (articleDb == null) {
            throw new ServiceException(ErrorCode.ARTICLE_ID_NOT_FOUND);
        }
        return articleDb;
    }

    @Override
    public void createArticle(Article article) {
        articleMapper.insert(article);
        updateTagsForArticle(article);
    }

    @Override
    public void updateArticle(Article article) throws ServiceException {
        Article articleDb = articleMapper.findById(article.getId());
        if (articleDb == null) {
            throw new ServiceException(ErrorCode.ARTICLE_ID_NOT_FOUND);
        }
        articleMapper.update(article);
        updateTagsForArticle(article);
    }

    private void updateTagsForArticle(Article article) {
        List<String> tagNames = article.getTagNames();
        List<Integer> newTagIds = new ArrayList<>();
        if (tagNames != null) {
            for (String tag : tagNames) {
                // create if tag not exist
                Tag tagDb = tagMapper.findByName(tag);
                if (tagDb == null) {
                    tagDb = new Tag();
                    tagDb.setName(tag);
                    tagMapper.insert(tagDb);
                }
                // store tag ids
                newTagIds.add(tagDb.getId());
            }
            // remove old associations
            tagMapper.deleteArticleTagRefByArticleId(article.getId());
            // create new associations
            for (Integer tagId : newTagIds) {
                tagMapper.insertArticleTagRef(article.getId(), tagId);
            }
        }
    }

    @Override
    public List<Category> findAllCatesWithArtiCount() {
        return categoryMapper.findAllWithArticleCount();
    }

    @Override
    public List<Category> findAllCategories() {
        return this.findAllCategories(0, 0);
    }

    @Override
    public List<Category> findAllCategories(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return categoryMapper.findAll();
    }

    @Override
    public List<Tag> findAllTags() {
        return this.findAllTags(0, 0);
    }

    @Override
    public List<Tag> findAllTags(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return tagMapper.findAll();
    }

    @Override
    public List<Author> findAllAuthors() {
        return this.findAllAuthors(0, 0);
    }

    @Override
    public List<Author> findAllAuthors(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return authorMapper.findAll();
    }
}

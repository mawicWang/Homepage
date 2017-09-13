package com.mawic.homepage.controller.article;

import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Transactional(readOnly = true)
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/article")
    public String listArticles(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize, Model model) {
        List<Article> listArticle = articleService.findAllArticleOutline(pageNum, pageSize);
        List<Category> listCategory = articleService.findAllCatesWithArtiCount();

        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listArticle", listArticle);
        return "article/article";
    }

}

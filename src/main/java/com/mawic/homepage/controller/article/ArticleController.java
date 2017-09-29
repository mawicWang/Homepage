package com.mawic.homepage.controller.article;

import com.mawic.homepage.exception.ServiceException;
import com.mawic.homepage.common.config.MarkdownConfig;
import com.mawic.homepage.common.search.ListArticleConditions;
import com.mawic.homepage.domain.model.article.Article;
import com.mawic.homepage.domain.model.article.Author;
import com.mawic.homepage.domain.model.article.Category;
import com.mawic.homepage.service.ArticleService;
import com.mawic.homepage.common.utils.Constants;
import com.mawic.homepage.common.utils.GridInfo;
import com.mawic.homepage.common.utils.HtmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Transactional(readOnly = true)
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MarkdownConfig markdownConfig;

    /**
     * 文章主页
     *
     * @param conditions 文章搜索条件
     * @param model      spring model
     * @return
     */
    @RequestMapping("/article")
    public String articles(ListArticleConditions conditions, Model model) {
        List<Article> listArticle = articleService.findAllArticles(conditions);
        List<Category> listCategory = articleService.findAllCatesWithArtiCount();

        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listArticle", listArticle);
        model.addAttribute("categoryId", conditions.getCategoryId());
        return "article/article";
    }

    /**
     * 文章管理页
     *
     * @param conditions 文章搜索条件
     * @param model      spring model
     * @return
     */
    @RequestMapping("/listArticle")
    public String listArticles(ListArticleConditions conditions, Model model) {
        List<Article> listArticle = articleService.findAllArticles(conditions);

        String[] header = {"<a href=\"/blogEditor.create\"><span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span></a>",
                "id", "title", "author", "category", "tags", "createTime", "updateTime"};
        String url = "listArticle";
        GridInfo<Article> gridInfo = new GridInfo<>(listArticle, header, url, t -> {
            String[] s = {
                    "<a href=\"/blogEditor.update?id=" + t.getId() + "\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></a>",
                    t.getId().toString(), t.getTitle(), t.getAuthor().getName(),
                    t.getCategory().getName(), HtmlHelper.parseTagsToLabels(t.getTags()),
                    t.getCreateTime().toString(), t.getUpdateTime().toString()
            };
            return s;
        });

        model.addAttribute("gridInfo", gridInfo);
        return "article/listArticles";
    }

    @RequestMapping("/blogEditor")
    public String blogEditor() {
        return "redirect:article/blogEditor.create";
    }

    @RequestMapping("/blogEditor.create")
    public String createBlogEditor(Model model) {
        String defaultMarkdown = markdownConfig.getDefaultContent();

        initBlogEditorInfos(model, new Article());
        model.addAttribute("markdownContent", defaultMarkdown);
        model.addAttribute("state", Constants.States.CREATE);
        return "article/blogEditor";
    }

    @RequestMapping("/blogEditor.update")
    public String updateBlogEditor(@RequestParam(name = "id") int articleId, Model model) throws ServiceException {
        Article article = articleService.findArticleById(articleId);

        initBlogEditorInfos(model, article);
        model.addAttribute("markdownContent", article.getContent());
        model.addAttribute("state", Constants.States.UPDATE);
        return "article/blogEditor";
    }

    private void initBlogEditorInfos(Model model, Article article) {
        List<Author> listAuthor = articleService.findAllAuthors();
        List<Category> listCategory = articleService.findAllCategories();
        model.addAttribute("listAuthor", listAuthor);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("article", article);
    }

    @PostMapping("/blogEditor.create.save")
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveCreateBlog(@ModelAttribute Article article) {
        articleService.createArticle(article);
        return "redirect:/listArticle";
    }

    @PostMapping("/blogEditor.update.save")
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveUpdateBlog(@ModelAttribute Article article) throws ServiceException {
        articleService.updateArticle(article);
        return "redirect:/listArticle";
    }

    @RequestMapping("article/{id}")
    public String showDetailArticle(@PathVariable String id, Model model) throws ServiceException {
        Article article = articleService.findArticleById(Integer.valueOf(id));
        model.addAttribute("article", article);
        return "article/detailArticle";
    }
}

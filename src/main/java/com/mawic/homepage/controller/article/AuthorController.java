package com.mawic.homepage.controller.article;

import com.mawic.homepage.domain.model.article.Author;
import com.mawic.homepage.service.ArticleService;
import com.mawic.homepage.utils.Constants;
import com.mawic.homepage.utils.GridInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Transactional(readOnly = true)
public class AuthorController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/author")
    public String listAuthor(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize, Model model) {
        List<Author> listAuthor = articleService.findAllAuthors(pageNum, pageSize);

        String[] header = {"id", "name", "createTime", "updateTime"};
        String url = "author";
        GridInfo<Author> gridInfo = new GridInfo<>(listAuthor, header, url, t -> {
            String[] s = {t.getId().toString(), t.getName(), t.getCreateTime().toString(), t.getUpdateTime().toString()};
            return s;
        });

        model.addAttribute("gridInfo", gridInfo);
        return "article/author";
    }
}

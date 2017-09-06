package com.mawic.homepage.controller.article;

import com.mawic.homepage.domain.model.article.Tag;
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
public class TagController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/tag")
    public String listTag(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize, Model model) {
        List<Tag> listTag = articleService.findAllTags(pageNum, pageSize);

        String[] header = {"id", "name"};
        String url = "tag";
        GridInfo<Tag> gridInfo = new GridInfo<>(listTag, header, url, t -> {
            String[] s = {t.getId().toString(), t.getName()};
            return s;
        });

        model.addAttribute("gridInfo", gridInfo);
        return "/article/tag";
    }
}

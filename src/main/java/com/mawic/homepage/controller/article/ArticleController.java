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

    /**
     * @param state 0 as create, 1 as update
     * @param model SpringMVC model
     * @return
     */
    @RequestMapping("/blogEditor")
    public String blogEditor(@RequestParam(defaultValue = "0") int state, Model model) {
        String defaultMarkdown = "# 简要markdown语法\n" +
                "\n" +
                "   更多内容参考[Markdown 语法说明 (简体中文版)](http://www.appinn.com/markdown/)\n" +
                "\n" +
                "----\n" +
                "\n" +
                "# 这是 H1\n" +
                "\n" +
                "## 这是 H2\n" +
                "\n" +
                "###### 这是 H6\n" +
                "\n" +
                "普通正文  \n" +
                "*倾斜1*  \n" +
                "_倾斜2_  \n" +
                "**加粗1**  \n" +
                "__加粗2__\n" +
                "\n" +
                ">引用\n" +
                "> >引用套嵌\n" +
                "\n" +
                "链接： [主页](/index \"Title\")  \n" +
                "图片： ![Alt text](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505575804393&di=24e4f3b003cfee29aef8f842254f5ba1&imgtype=0&src=http%3A%2F%2Fcdn-img.easyicon.net%2Fpng%2F11720%2F1172079.gif  \"Optional title\")\n" +
                "\n" +
                "*   无序列表\n" +
                "*   无序列表\n" +
                "*   无序列表 \n" +
                "\n" +
                "\n" +
                "1.  有序列表\n" +
                "2.  有序列表\n" +
                "4.  有序列表\n" +
                "\n" +
                "\n" +
                "这是一个普通段落：\n" +
                "\n" +
                "    这是一个代码区块，注意缩进。\n" +
                "    <div class=\"footer\">\n" +
                "        &copy; 2017 Mawic\n" +
                "    </div>\n" +
                "\n" +
                "块状代码，可设置语言：\n" +
                "```javascript\n" +
                "function hello(){\n" +
                "    var message = \"hello world!\"\n" +
                "    console.log(message);\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "行内代码 `Foo()`，可以直接使用html标签`<html/> `.\n" +
                "\n" +
                "表格：\n" +
                "\n" +
                "| 左对齐 | 居中 | 右对齐 |\n" +
                "| :---         |     :---:      |          ---: |\n" +
                "| `代码`   | *倾斜*     | **加粗123** |\n" +
                "|    1    | 2       | 3     |\n" +
                "\n" +
                "电子邮件：<address@example.com>  ";
        model.addAttribute("defaultMarkdown", defaultMarkdown);
        model.addAttribute("state", state);
        return "article/blogEditor";
    }

}

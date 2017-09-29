package com.mawic.homepage.domain.model.article;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Article {

    private Integer id;
    private String title;
    private String content;
    private String outline;
    private Integer authorId;
    private Integer categoryId;
    private Date createTime;
    private Date updateTime;

    // fk
    private Author author;
    private Category category;
    private Collection<Tag> tags;

    // transient
    private String listTagStr;

    public List<String> getTagNames() {
        String str = this.getListTagStr();
        if (StringUtils.isBlank(str)) {
            return null;
        }
        List<String> tagNames = new ArrayList<>();
        for (String s : str.split(",")) {
            if (StringUtils.isNotBlank(str)) {
                tagNames.add(s);
            }
        }
        return tagNames;
    }

    public String getListTagStr() {
        if (listTagStr != null) {
            return listTagStr;
        }
        if (tags == null) {
            return null;
        }
        List<String> tagNames = new ArrayList<>();
        for (Tag t : tags) {
            tagNames.add(t.getName());
        }
        return listTagStr = StringUtils.join(tagNames, ",");
    }

    public void setListTagStr(String listTagStr) {
        this.listTagStr = listTagStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }
}

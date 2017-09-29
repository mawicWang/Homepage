package com.mawic.homepage.common.search;

import java.util.Map;

public class ListArticleConditions extends BaseConditions {

    private Integer categoryId;
    private String categoryName;
    private Integer tagId;
    private String tagName;

    @Override
    public Map<String, Object> map() {
        map.put("category_id", getCategoryId());
        map.put("category.name", getCategoryName());
        return super.map();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

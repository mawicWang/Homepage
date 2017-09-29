package com.mawic.homepage.domain.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ArticleProvider {

    public String findAllBy(Map<String, Object> map) {
        return new SQL() {
            {
                SELECT("*");
                FROM("article");
                if (map.get("category_id") != null) {
                    WHERE("category_id = #{category_id}");
                }
            }
        }.toString();
    }
}

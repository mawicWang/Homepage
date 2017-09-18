package com.mawic.homepage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mk")
@PropertySource(value = "classpath:config/markdown.properties", encoding = "utf-8")
@Component
public class MarkdownConfig {

    private String defaultContent;

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }
}

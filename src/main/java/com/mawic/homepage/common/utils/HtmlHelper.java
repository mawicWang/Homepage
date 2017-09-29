package com.mawic.homepage.common.utils;

import com.mawic.homepage.domain.model.article.Tag;

import java.util.Collection;

public final class HtmlHelper {

    public static String parseTagsToLabels(Collection<Tag> tags) {
        StringBuffer buffer = new StringBuffer();
        for (Tag tag : tags) {
            buffer.append("<span class=\"label label-info\">").append(tag.getName()).append("</span> ");
        }
        return buffer.toString();
    }
}

package com.mawic.homepage.common.utils;

import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

public class GridInfo<T> {

    public PageInfo<T> pageInfo;
    public List<String[]> list;
    public String[] header;
    public String url;

    public GridInfo(List<T> list, String[] header, String url, GridMapper<T> mapper) {
        this.list = new ArrayList<>();
        for (T t : list) {
            this.list.add(mapper.mappingList(t));
        }
        this.pageInfo = new PageInfo(list, Constants.DEFAULT_NAVIGATE_PAGES);
        this.header = header;
        this.url = url;
    }
}

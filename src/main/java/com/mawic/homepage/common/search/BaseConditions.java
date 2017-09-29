package com.mawic.homepage.common.search;

import java.util.HashMap;
import java.util.Map;

public class BaseConditions implements Conditions {

    protected int pageNum = 1;
    protected int pageSize = 20;
    protected Map<String, Object> map = new HashMap<>();

    @Override
    public Map<String, Object> map() {
        map.put("pageNum", getPageNum());
        map.put("pageSize", getPageSize());
        return map;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}

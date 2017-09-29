package com.mawic.homepage.common.search;

import java.util.Map;

public interface Conditions {

    int getPageNum();

    void setPageNum(int pageNum);

    int getPageSize();

    void setPageSize(int pageSize);

    Map<String, Object> map();
}

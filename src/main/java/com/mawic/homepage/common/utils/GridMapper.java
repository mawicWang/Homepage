package com.mawic.homepage.common.utils;


@FunctionalInterface
public interface GridMapper<T> {

    String[] mappingList(T t);
}

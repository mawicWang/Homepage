package com.mawic.homepage.utils;


@FunctionalInterface
public interface GridMapper<T> {

    String[] mappingList(T t);
}

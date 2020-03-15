package com.ygz.aspen.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void putDataSource(String name){
        THREAD_LOCAL.set(name);
    }

    public static String getDataSource(){
        return THREAD_LOCAL.get();
    }

    public static void removeDataSource(){
        THREAD_LOCAL.remove();
    }

    //数据源路由，此方法用于产生要选取的数据源逻辑名称
    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }
}

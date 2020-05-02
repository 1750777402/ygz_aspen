package com.ygz.aspen.context;

public class AspenContextHolder {

    private final static ThreadLocal<AspenContext> contextHolder = new ThreadLocal<>();

    public final static String APP_VERSION = "appVersion";

    public final static String DEVICEOS = "deviceOS";

    public static AspenContext get(){
        return contextHolder.get();
    }

    public static void set(AspenContext context){
        contextHolder.set(context);
    }

    public static void clear(){
        contextHolder.remove();
    }

}

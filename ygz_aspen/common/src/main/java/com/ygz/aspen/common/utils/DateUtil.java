package com.ygz.aspen.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String yyyyMMDD = "yyyy-MM-dd HH:mm:ss";

    public static String getToday(){
        SimpleDateFormat df = new SimpleDateFormat(yyyyMMDD);//设置日期格式
        return df.format(new Date());
    }

}

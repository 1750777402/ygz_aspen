package com.ygz.aspen.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    private volatile long now;

    private TimeUtil() {
        this .now = System.currentTimeMillis();
        scheduleTick();
    }

    private void scheduleTick() {
        new ScheduledThreadPoolExecutor(1, runnable -> {
            Thread thread = new Thread(runnable, "current-time-millis");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(
                () -> now = System.currentTimeMillis(),
                1,
                10,
                TimeUnit.MILLISECONDS
        );
    }

    public static TimeUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static final TimeUtil INSTANCE = new TimeUtil();
    }

    /**
     * 秒为单位的当前时间
     * @return
     */
    public long secondNow() {
        return now/1000;
    }

    /**
     * 毫秒为单位的当前时间
     * @return
     */
    public long millisecondNow() {
        return now;
    }

    /**
     * 获取当天的int
     * @return
     */
    public int getTodayNum(){
        Date date = new Date(now);
        String ma = "yyyyMMdd";
        String nowdate = new SimpleDateFormat(ma).format(date);
        return Integer.parseInt(nowdate);
    }

    /**
     * 获取今天凌晨的时间 秒单位
     * @return
     */
    public Long getTodayBeforeDawnTime(){
        Date date = new Date(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime()/1000;
    }


    /**
     * 获取今天的最后一秒23：59：59  秒单位
     * @return
     */
    public Long getTodayEndTime(){
        Date date = new Date(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime().getTime()/1000;
    }

    /**
     * 获取当天的int
     * @return
     */
    public int getTodayNum2(){
        return Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }


}

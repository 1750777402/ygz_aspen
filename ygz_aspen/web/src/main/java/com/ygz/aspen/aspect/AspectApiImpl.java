package com.ygz.aspen.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * 基本被装饰类,做一些公共处理
 * Created by ygz
 */
public class AspectApiImpl implements AspectApi {

    @Override
    public Object doHandlerAspect(ProceedingJoinPoint pjp, Method method) throws Throwable {
        return null;
    }
}

package com.ygz.aspen.config;

import com.ygz.aspen.constant.DataSourceConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;

/**
 *
 */
@Aspect
@Order(-2147483647)//保证该AOP在@Transactional之前执行
@Configuration
public class DataSourceAspect {

    /*
     * 定义一个切入点
     */
    //@Pointcut("execution(* com.timer.service..*.*(..)) && execution(* update*(..))")
//    @Pointcut("execution(* com.wwdz.mall.server.*..*(..))")
    @Pointcut("execution(* com.ygz.aspen.dao.*..*(..))")
    public void dataSourcePointCut(){}
    /*
     * 通过连接点切入
     */
    @Before("dataSourcePointCut()")
    public void doBefore(JoinPoint joinPoint){
        try {
            String method = joinPoint.getSignature().getName();
            Object target = joinPoint.getTarget();
            Class<?>[] classz = target.getClass().getInterfaces();
            Class<?>[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();

            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(TargetDataSource.class)){

                //当前开启事务，不切换数据源
                if (TransactionSynchronizationManager.isActualTransactionActive()) {
                    DynamicDataSource.putDataSource(DataSourceConstant.DATA_SOURCE_MASTER);
                }
                else{
                    TargetDataSource data = m.getAnnotation(TargetDataSource.class);
                    String dataSourceName = data.value();
                    //读库轮询切换  这里可以加载多个读库，然后随机取一个设置多读库数据源
//                    if(DataSourceConstant.DATA_SOURCE_READ.equals(dataSourceName)){
//                        DynamicDataSource.putDataSource(dataSourceName);
//                    }else{
                        DynamicDataSource.putDataSource(dataSourceName);
//                    }
                }
            }
        } catch (Throwable e) {
            DynamicDataSource.putDataSource(DataSourceConstant.DATA_SOURCE_MASTER);
        }
    }

    @AfterReturning(returning = "ret", pointcut = "dataSourcePointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        //log.info("RESPONSE : " + ret);
    }


    //执行完切面后，将线程共享中的数据源名称清空
    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint){
        DynamicDataSource.removeDataSource();
    }

}

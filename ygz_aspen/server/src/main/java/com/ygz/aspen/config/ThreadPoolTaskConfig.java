package com.ygz.aspen.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolTaskConfig {

    // 核心线程数（默认线程数）
    private static final int corePoolSize = 10;
    // 最大线程数
    private static final int maxPoolSize = 20;
    // 允许线程空闲时间（单位：默认为秒）
    private static final int keepAliveTime = 60;
    // 缓冲队列数
    private static final int queueCapacity = 2000;
    // 线程池名前缀
    private static final String threadNamePrefix = "Async-Service-";

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("threadPoolTaskExecutor#rejectedExecution Task " + r.toString() + " rejected from " + executor.toString());
        }
    }

    private TaskExecutor createTaskExecutor(int corePoolSize,
                                            int maxPoolSize,
                                            int queueCapacity,
                                            int keepAliveTime,
                                            String threadNamePrefix){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());
        // 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        // 初始化
        executor.initialize();
        return executor;
    }

    @Bean("taskExecutor")
    public TaskExecutor threadPoolTaskExecutor(){
        return createTaskExecutor(corePoolSize, maxPoolSize, queueCapacity, keepAliveTime, threadNamePrefix);
    }
}
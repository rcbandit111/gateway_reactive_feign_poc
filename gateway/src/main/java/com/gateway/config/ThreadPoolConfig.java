package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/21
 */
@Configuration
public class ThreadPoolConfig
{

    @Bean(destroyMethod = "shutdown",name = "gateway-thread")
    public Executor threadPool () {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<>(30));
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.setThreadFactory(new CustomizableThreadFactory("gateway  business thread pool"));
        return threadPoolExecutor;
    }
}

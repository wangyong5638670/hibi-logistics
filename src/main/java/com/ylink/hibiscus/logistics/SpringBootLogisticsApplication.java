/*
  版权所有(C) 2018 深圳市雁联计算系统有限公司
  创建: YangHan 2018-12-12
 */
package com.ylink.hibiscus.logistics;

import com.ylink.hibiscus.common.base.application.SpringBootHibiscusBaseApplication;
import com.ylink.hibiscus.logistics.basic.generator.UniqueNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author YangHan
 * @date 2018-12-12
 */
@SpringBootApplication
@ImportResource("classpath:config/spring/spring-transaction.xml")
@MapperScan(value = "com.ylink.hibiscus.logistics.dao",nameGenerator = UniqueNameGenerator.class)
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = {
                "com.ylink.hibiscus.remote.client"
        }
)
@EnableAsync
public class SpringBootLogisticsApplication extends SpringBootHibiscusBaseApplication {
    /**
     * Spring Boot 主函数
     * @param args 参数
     */
    public static void main( String[] args ) {
        SpringApplication.run(SpringBootLogisticsApplication.class,args);
    }

    @Bean(name = "registerTaskExecutor")
    public ThreadPoolTaskExecutor registerTaskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(100);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("logistics-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

        return executor;
    }
}

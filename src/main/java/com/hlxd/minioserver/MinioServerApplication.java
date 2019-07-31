package com.hlxd.minioserver;

import com.hlxd.minioserver.service.MinioTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @Classname MinioServerApplication
 * @Description 主启动程序
 * @Author yulj
 * @Date: 2019/07/31 00:10
 */
@SpringBootApplication
public class MinioServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioServerApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(MinioTemplate.class)
    @ConditionalOnProperty(name = "minio.url")
    MinioTemplate template() {
        return new MinioTemplate();
    }

}

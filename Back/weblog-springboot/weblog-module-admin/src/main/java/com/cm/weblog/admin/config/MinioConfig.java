package com.cm.weblog.admin.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MinioConfig {
    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        // 构建客户端
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}

package com.sky.config;

import com.sky.properties.MinioProperties;
import com.sky.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean //单例
    public MinioUtil minioUtil(MinioProperties minioProperties){
        log.info("启动oss配置");
        return new MinioUtil(minioProperties.getEndpoint(),
                            minioProperties.getAccessKey(),
                            minioProperties.getSecretKey(),
                            minioProperties.getBucketName());
    }

}

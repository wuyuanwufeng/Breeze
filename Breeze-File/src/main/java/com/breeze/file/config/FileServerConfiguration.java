package com.breeze.file.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.breeze.file.config.properties.FileServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {FileServerProperties.class})
public class FileServerConfiguration {


    @Bean
    public AmazonS3 createAmazons3(FileServerProperties fileServerProperties) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(fileServerProperties.getAccessKey(), fileServerProperties.getSecretKey())))
                // 客户端地址，地区
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(fileServerProperties.getEndpoint(), fileServerProperties.getRegion()))
                // 请求在放弃和超时之前等待完成的时间(毫秒)
                .withClientConfiguration(new ClientConfiguration().withRequestTimeout(5000))
                // 配置流传输文件大小限制阈值，此处为MB
                .withRequestHandlers(new RequestHandler2() {
                    @Override
                    public void beforeRequest(Request<?> request) {
                        super.beforeRequest(request);
                        request.getOriginalRequest().getRequestClientOptions().setReadLimit(fileServerProperties.getMaxUploadSize() < 0 ? 1 * 1000000 : fileServerProperties.getMaxUploadSize() * 1000000);
                    }
                })
                .build();
    }

}

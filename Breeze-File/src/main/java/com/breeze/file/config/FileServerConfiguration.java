package com.breeze.file.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
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
//                .withRegion("")
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(fileServerProperties.getAccessKey(), fileServerProperties.getSecretKey())))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(fileServerProperties.getEndpoint(), fileServerProperties.getRegion()))
                .withClientConfiguration(new ClientConfiguration().withRequestTimeout(5000))
                .build();
    }

}

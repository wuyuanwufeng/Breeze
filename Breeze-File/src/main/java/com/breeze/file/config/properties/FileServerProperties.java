package com.breeze.file.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "breeze.file.oss")
public class FileServerProperties implements Serializable {
    /**
     * 访问文件服务器的key
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 文件服务器客户端地址
     */
    private String endpoint;

    /**
     * 地区
     */
    private String region;

    /**
     * 桶名
     */
    private String bucket;

    /**
     * 请求在放弃和超时之前等待完成的时间(毫秒)
     */
    private int requestTimeout;

    /**
     * 上传限制，MB
     */
    private int maxUploadSize;
}

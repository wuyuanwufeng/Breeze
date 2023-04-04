package com.breeze.file.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "breeze.file.oss")
public class FileServerProperties implements Serializable {
    private String accessKey;

    private String secretKey;

    private String endpoint;

    private String region;

    private String bucket;

    private int requestTimeout;

    /**
     * 上传限制，MB
     */
    private int maxUploadSize;
}

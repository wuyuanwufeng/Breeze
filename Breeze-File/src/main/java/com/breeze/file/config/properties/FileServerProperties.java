package com.breeze.file.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "breeze.file.oss")
public class FileServerProperties {
    private String accessKey;

    private String secretKey;

    private String endpoint;

    private String region;

    private String bucket;

    private int requestTimeout;
}

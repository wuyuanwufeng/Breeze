package com.breeze.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketPolicy;

public interface OssClient {

    AmazonS3 getS3Client();

    void createBucket(String bucketName);
    void setBucketPolicy(String bucket,String policy);

    BucketPolicy getBucketPolicy(String bucket);
}

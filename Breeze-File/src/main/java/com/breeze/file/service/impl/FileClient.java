package com.breeze.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketPolicy;
import com.breeze.file.service.OssClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileClient implements OssClient {

    private final AmazonS3 amazonS3;

    @Override
    public AmazonS3 getS3Client() {
        return amazonS3;
    }

    @Override
    public void createBucket(String bucketName) {
        if (amazonS3.doesBucketExistV2(bucketName)) {
            return;
        }
        amazonS3.createBucket(bucketName);
        log.info("Bucket {} created", bucketName);
    }

    @Override
    public void setBucketPolicy(String bucket, String policy) {
        amazonS3.setBucketPolicy(bucket, policy);
    }
    @Override
    public BucketPolicy getBucketPolicy(String bucket) {
        return amazonS3.getBucketPolicy(bucket);
    }
}

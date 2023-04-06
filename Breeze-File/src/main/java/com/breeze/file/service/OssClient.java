package com.breeze.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketPolicy;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface OssClient {

    AmazonS3 getS3Client();

    void createBucket(String bucketName);

    void deleteBucket(String bucketName);

    void setBucketPolicy(String bucket, String policy);

    BucketPolicy getBucketPolicy(String bucket);

    List<Bucket> listBuckets();

    String getFileUrl(String bucketName, String directory, String fileName);

    String uploadFile(String bucketName, String directory, String fileName, File file);

    String uploadFile(String bucketName, String directory, String fileName, InputStream inputStream);

    String uploadFile(String bucketName, String directory, String fileName, MultipartFile multipartFile);

    String uploadFile(String bucketName, String directory, String fileName, byte[] bytes);

    void deleteFile(String bucketName, String directory, String fileName);

    void deleteFiles(String bucketName, List<String> filePaths);

    List<String> getBucketObjects(String bucketName);

    String getFileSignUrl(String bucketName, String filePath, Long expiration);

    List<String> listFiles(String bucketName, String prefix);

}

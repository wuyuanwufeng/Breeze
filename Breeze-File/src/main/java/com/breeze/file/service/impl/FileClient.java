package com.breeze.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.breeze.file.service.OssClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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
        if (judgeBucketExist(bucketName)) {
            return;
        }
        amazonS3.createBucket(bucketName);
    }

    @Override
    public void deleteBucket(String bucketName) {
        if (!judgeBucketExist(bucketName)) {
            return;
        }
        checkBucket(this.getBucketObjects(bucketName).size() > 0, "The bucket you tried to delete is not empty");
        amazonS3.deleteBucket(bucketName);
    }

    @Override
    public void setBucketPolicy(String bucket, String policy) {
        amazonS3.setBucketPolicy(bucket, policy);
    }

    @Override
    public BucketPolicy getBucketPolicy(String bucket) {
        return amazonS3.getBucketPolicy(bucket);
    }

    @Override
    public List<Bucket> listBuckets() {
        return amazonS3.listBuckets();
    }

    @Override
    public String getFileUrl(String bucketName, String directory, String fileName) {
        return amazonS3.getUrl(bucketName, buildFilePath(directory, fileName)).toString();
    }

    @Override
    public String uploadFile(String bucketName, String directory, String fileName, File file) {
        checkBucket(!judgeBucketExist(bucketName), "Bucket does not exist");
        amazonS3.putObject(bucketName, buildFilePath(directory, fileName), file);
        return this.getFileUrl(bucketName, directory, fileName);
    }


    @Override
    public String uploadFile(String bucketName, String directory, String fileName, InputStream inputStream) {
        checkBucket(!judgeBucketExist(bucketName), "Bucket does not exist");
        this.uploadFile(bucketName, buildFilePath(directory, fileName), inputStream, null);
        return this.getFileUrl(bucketName, directory, fileName);
    }


    @Override
    public String uploadFile(String bucketName, String directory, String fileName, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            this.uploadFile(bucketName, buildFilePath(directory, fileName), inputStream, null);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Uploading file failed: %s", e.getMessage()));
        }
        return this.getFileUrl(bucketName, directory, fileName);
    }

    @Override
    public String uploadFile(String bucketName, String directory, String fileName, byte[] bytes) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            this.uploadFile(bucketName, buildFilePath(directory, fileName), inputStream, null);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Uploading file failed: %s", e.getMessage()));
        }
        return this.getFileUrl(bucketName, directory, fileName);
    }

    @Override
    public void deleteFile(String bucketName, String directory, String fileName) {
        amazonS3.deleteObject(bucketName, buildFilePath(directory, fileName));
    }

    @Override
    public List<String> getBucketObjects(String bucketName) {
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName);
        return listObjectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }


    private boolean judgeBucketExist(String bucketName) {
        return amazonS3.doesBucketExistV2(bucketName);
    }

    private void checkBucket(boolean b, String s) {
        if (b) {
            throw new IllegalStateException(s);
        }
    }

    private void uploadFile(String bucketName, String filePath, InputStream inputStream, ObjectMetadata metadata) {
        amazonS3.putObject(bucketName, filePath, inputStream, metadata);
    }

    private String buildFilePath(String directory, String fileName) {
        return trimStringWith(directory, '/') + "/" + trimStringWith(fileName, '/');
    }

    public String trimStringWith(String str, char beTrim) {
        int st = 0;
        int len = str.length();
        char[] val = str.toCharArray();
        char sbeTrim = beTrim;
        while ((st < len) && (val[st] <= sbeTrim)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= sbeTrim)) {
            len--;
        }
        return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
    }
}

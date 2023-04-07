package com.breeze.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketPolicy;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface OssClient {

    /**
     * 获取AmazonS3
     * @return  AmazonS3
     */
    AmazonS3 getS3Client();

    /**
     * 创建桶
     * @param bucketName    桶名
     */
    void createBucket(String bucketName);

    /**
     *  删除桶（桶中若存在文件删不了）
     * @param bucketName    桶名
     */
    void deleteBucket(String bucketName);

    /**
     * 设置桶策略
     * @param bucket    桶名
     * @param policy    策略
     */
    void setBucketPolicy(String bucket, String policy);

    /**
     *  获取桶策略
     * @param bucket    桶名
     * @return          桶策略
     */
    BucketPolicy getBucketPolicy(String bucket);

    /**
     *  获取所有桶信息
     * @return  桶集合
     */
    List<Bucket> listBuckets();

    /**
     * 获取文件url
     * @param bucketName        桶名
     * @param directory         文件目录
     * @param fileName          文件名称
     * @return                  文件url
     */
    String getFileUrl(String bucketName, String directory, String fileName);

    /**
     * 文件上传
     * @param bucketName    桶名
     * @param directory     文件目录
     * @param fileName      文件名称
     * @param file          file对象
     * @return              文件url
     */
    String uploadFile(String bucketName, String directory, String fileName, File file);

    /**
     * 上传流文件
     * @param bucketName        桶名
     * @param directory         文件目录
     * @param fileName          文件名称
     * @param inputStream       输入流
     * @return                  文件url
     */
    String uploadFile(String bucketName, String directory, String fileName, InputStream inputStream);

    /**
     * 上传multipartFile文件
     * @param bucketName        桶名
     * @param directory         文件目录
     * @param fileName          文件名称
     * @param multipartFile     multi文件对象
     * @return                  文件url
     */
    String uploadFile(String bucketName, String directory, String fileName, MultipartFile multipartFile);

    /**
     * 上传字节数组文件
     * @param bucketName    桶名
     * @param directory     文件目录
     * @param fileName      文件名称
     * @param bytes         字节数组
     * @return              文件url
     */
    String uploadFile(String bucketName, String directory, String fileName, byte[] bytes);

    /**
     * 删除单个文件
     * @param bucketName    桶名
     * @param directory     文件目录
     * @param fileName      文件名
     */
    void deleteFile(String bucketName, String directory, String fileName);

    /**
     * 删除多个文件
     * @param bucketName    桶名
     * @param filePaths     文件路径集合
     */
    void deleteFiles(String bucketName, List<String> filePaths);

    /**
     *  获取桶中所有文件名称
     * @param bucketName    桶名
     * @return  文件名称集合
     */
    List<String> getBucketObjects(String bucketName);

    /**
     * 获取签名文件url
     * @param bucketName    桶名
     * @param filePath      文件路径
     * @param expiration    过期时间（单位：分钟）
     * @return      文件签名url
     */
    String getFileSignUrl(String bucketName, String filePath, Long expiration);

    /**
     * 获取桶中的文件名
     * @param bucketName    桶名
     * @param prefix    文件路径前缀
     * @return      文件名称集合
     */
    List<String> listFiles(String bucketName, String prefix);

}

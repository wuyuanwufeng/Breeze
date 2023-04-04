package com.breeze.file;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketPolicy;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.breeze.file.service.impl.FileClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@SpringBootTest
public class TestOss {
    @Autowired
    private FileClient fileClient;

    @Test
    public void testOss() {
        fileClient.createBucket("bfile");
    }

    @Test
    public void testPolicy() {
        fileClient.setBucketPolicy("bfile", "read");

    }

    @Test
    public void testGetPolicy() {
        BucketPolicy bfile = fileClient.getBucketPolicy("bfile");
        System.out.println(bfile.getPolicyText());

    }

    @Test
    public void testListBuckets() {
        List<Bucket> buckets = fileClient.listBuckets();
    }

    @Test
    public void testUpload() {

//        fileClient.uploadFile("bfile", "bfile02/微信图片_20220518204917.png", new File("C:\\Users\\余生一盏清风\\Pictures\\微信图片_20220518204917.png"));
//        fileClient.getS3Client().putObject(new PutObjectRequest("bfile", "bfile01/微信图片_20220518204917.png", new File("C:\\Users\\余生一盏清风\\Pictures\\微信图片_20220518204917.png")));
//        fileClient.uploadFile("bfile", "bfile03/","01.png", new File("C:\\Users\\余生一盏清风\\Pictures\\微信图片_20220518204917.png"));
//        String fileUrl = fileClient.uploadFile("bfile", "bfile05", "微信图片_20220518204917.png", new File("C:\\Users\\余生一盏清风\\Pictures\\微信图片_20220518204917.png"));
//        System.out.println(fileUrl);
        fileClient.getS3Client().putObject(new PutObjectRequest("bfile", "bfile05/微信图片_20220518204917.png", new File("C:\\Users\\余生一盏清风\\Pictures\\微信图片_20220518204917.png")));
    }

    @Test
    public void testDelete() {
        fileClient.deleteFile("bfile", "/01/","高性能MySQL第三版_compressed.pdf");
    }

    @Test
    public void testDeleteBuck() {
        fileClient.deleteBucket("bfile");
    }

    @Test
    public void testListObjs() {
        List<String> keys = fileClient.getBucketObjects("bfile");
    }

    @Test
    public void testurl() {
        String bfile = fileClient.getFileUrl("bfile", "bfile03/","01.png");
        System.out.println(bfile);
    }

    @Test
    public void testUploadInputStream() {
        try {
            URL url = new URL("http://124.220.134.171:9000/bfile/bfile03/01.png");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setConnectTimeout(1000);
            c.connect();
            InputStream inputStream = c.getInputStream();
            fileClient.uploadFile("bfile", "/bfile-01/","1.png", inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBuildPath(){
        System.out.println(this.trimStringWith("/bfile01/bbb/",'/'));
    }
    private String buildFilePath(String directory, String fileName) {
        return trimStringWith(directory, '/') + File.separator + trimStringWith(fileName, '/');
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

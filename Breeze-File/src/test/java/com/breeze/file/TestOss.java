package com.breeze.file;

import com.amazonaws.services.s3.model.BucketPolicy;
import com.breeze.file.service.impl.FileClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestOss {
    @Autowired
    private FileClient fileClient;

    @Test
    public void testOss() {
        fileClient.createBucket("bfile");
    }

    @Test
    public void testPolicy(){
        fileClient.setBucketPolicy("bfile","read");

    }

    @Test
    public void testGetPolicy(){
        BucketPolicy bfile = fileClient.getBucketPolicy("bfile");
        System.out.println(bfile.getPolicyText());

    }
}

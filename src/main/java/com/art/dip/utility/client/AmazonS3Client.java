package com.art.dip.utility.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@Slf4j
public class AmazonS3Client {

    private final AmazonS3 client;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;


    @Autowired
    public AmazonS3Client(AmazonS3 client) {
        this.client = client;
    }

    public String uploadFile(MultipartFile file)  {
        File f = new File(UUID.randomUUID().toString() + "." +
                FilenameUtils.getExtension(file.getOriginalFilename()));
        try(InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(f)){
            IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.putObject(bucketName,f.getName(),f);
        boolean isDeleted = f.delete();
        if (!isDeleted) {
            log.warn(f.getName().concat(" not deleted!"));
        }
        return f.getName();
    }

    public S3Object downloadFile(String fileName) {
        return client.getObject(bucketName,fileName);
    }

}

package com.art.dip.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class AmazonS3Service {

    private final AmazonS3 client;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;



    public AmazonS3Service(AmazonS3 client) {
        this.client = client;
    }

    public String uploadFile(MultipartFile file)  {
        File f = new File(UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        try(InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(f)){
            IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        client.putObject(bucketName,f.getName(),f);
        f.delete();
        return f.getName();
    }

}

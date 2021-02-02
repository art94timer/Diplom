package com.art.dip.service;

import com.art.dip.utility.client.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ApplicantPhotoService {

    private final AmazonS3Client s3Service;


    @Value("${app.awsServices.bucketUrl}")
    private String bucketUrl;

    public ApplicantPhotoService(AmazonS3Client s3Service) {
        this.s3Service = s3Service;
    }

    /*
        return uploaded fileName
         */
    public String uploadPhoto(MultipartFile photo) {
        return s3Service.uploadFile(photo);
    }

}

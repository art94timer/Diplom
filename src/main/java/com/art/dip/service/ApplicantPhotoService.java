package com.art.dip.service;

import com.art.dip.model.Certificate;
import com.art.dip.utility.dto.ValidateGradeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ApplicantPhotoService {

    private final AmazonS3Service s3Service;


    @Value("${app.awsServices.bucketUrl}")
    private String bucketUrl;

    public ApplicantPhotoService(AmazonS3Service s3Service) {
        this.s3Service = s3Service;
    }

    /*
        return uploaded fileName
         */
    private String upload(MultipartFile photo) {
        return s3Service.uploadFile(photo);
    }


    public String uploadPhoto(MultipartFile photo) {
        return upload(photo);
    }

    public void buildPath(ValidateGradeDTO dto) {
        dto.setFileName(bucketUrl + File.separator + dto.getFileName());
    }


	public void buildPath(Certificate certificate) {
    	certificate.setFileName(bucketUrl + File.separator + certificate.getFileName());
	}




}

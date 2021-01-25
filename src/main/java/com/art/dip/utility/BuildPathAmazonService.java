package com.art.dip.utility;

import com.art.dip.model.Certificate;
import com.art.dip.utility.dto.ValidateGradeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class BuildPathAmazonService {

    @Value("${app.awsServices.bucketUrl}")
    private String bucketUrl;


    public String buildPath(String fileName) {
        return bucketUrl.concat(File.separator).concat(fileName);
    }



}

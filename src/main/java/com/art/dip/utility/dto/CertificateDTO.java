package com.art.dip.utility.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
@Data
public class CertificateDTO {

	@Range(min = 1,max = 10)
	private Double mark;
	
	private MultipartFile file;
}

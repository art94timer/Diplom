package com.art.dip.utility.converter;

import com.art.dip.model.Certificate;
import com.art.dip.service.ApplicantPhotoService;
import com.art.dip.utility.dto.CertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CertificateConverter {

	private final ApplicantPhotoService photoService;


	@Autowired
	public CertificateConverter(ApplicantPhotoService photoService) {
		this.photoService = photoService;
	}

	public Certificate toEntity(CertificateDTO dto) {

			Certificate certificate = new Certificate();
			String fileName = photoService.uploadPhoto(dto.getFile());
			certificate.setFileName(fileName);
			certificate.setMark(dto.getMark());
			return certificate;
	}
	
}

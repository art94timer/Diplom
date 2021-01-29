package com.art.dip.utility.converter;


import com.art.dip.model.Applicant;
import com.art.dip.model.Certificate;
import com.art.dip.utility.BuildPathAmazonService;
import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.dto.ValidateApplicantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ApplicantConverter {

    private final CertificateConverter certConverter;

    private final GradeConverter gradeConverter;

    private final FacultyConverter facConverter;

    private final BuildPathAmazonService buildPathAmazonService;


    @Autowired
    public ApplicantConverter(CertificateConverter certConverter, GradeConverter gradeConverter, FacultyConverter facConverter, BuildPathAmazonService buildPathAmazonService) {

        this.certConverter = certConverter;
        this.gradeConverter = gradeConverter;
        this.facConverter = facConverter;
        this.buildPathAmazonService = buildPathAmazonService;
    }


    public Applicant toEntity(ApplicantDTO dto) {

        Applicant applicant = new Applicant();

        applicant.setCertificate(certConverter.toEntity(dto.getCertificate()));

        applicant.setGrades(gradeConverter.toEntity(dto.getGrades()));

        applicant.setFaculty(facConverter.toEntity(dto.getFaculty()));

        return applicant;
    }


    public ValidateApplicantDTO toRuValidateApplicantDTO(Applicant applicant) {
        ValidateApplicantDTO validateApplicantDTO = toValidateApplicantDTO(applicant);
        validateApplicantDTO.setValidateGradeDTO(gradeConverter.toRuValidateGradeDTO(applicant.getGrades()));
        return validateApplicantDTO;
    }

    public List<ValidateApplicantDTO> toRuValidateApplicantDTO(List<Applicant> applicants) {
        return applicants.stream().map(this::toRuValidateApplicantDTO).collect(Collectors.toList());
    }

    public ValidateApplicantDTO toEnValidateApplicantDTO(Applicant applicant) {
        ValidateApplicantDTO validateApplicantDTO = toValidateApplicantDTO(applicant);
        validateApplicantDTO.setValidateGradeDTO(gradeConverter.toEnValidateGradeDTO(applicant.getGrades()));
        return validateApplicantDTO;
    }
    public List<ValidateApplicantDTO> toEnValidateApplicantDTO(List<Applicant> applicants) {
        return applicants.stream().map(this::toEnValidateApplicantDTO).collect(Collectors.toList());
    }

    private ValidateApplicantDTO toValidateApplicantDTO(Applicant applicant) {
        ValidateApplicantDTO validateApplicantDTO = new ValidateApplicantDTO();
        Certificate certificate = new Certificate();
        certificate.setFileName(buildPathAmazonService.buildPath(applicant.getCertificate().getFileName()));
        certificate.setMark(applicant.getCertificate().getMark());
        validateApplicantDTO.setCertificate(certificate);
        validateApplicantDTO.setEmail(applicant.getPerson().getEmail());
        validateApplicantDTO.setId(applicant.getId());
        validateApplicantDTO.setFullName(applicant.getPerson().getFirstName().concat(applicant.getPerson().getLastName()));
        return validateApplicantDTO;
    }

    private List<ValidateApplicantDTO> toValidateApplicantDTO(List<Applicant> applicants) {
        return applicants.stream().map(this::toValidateApplicantDTO).collect(Collectors.toList());
    }

}

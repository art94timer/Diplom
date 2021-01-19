package com.art.dip.utility.converter;


import com.art.dip.model.Applicant;
import com.art.dip.utility.dto.ApplicantDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ApplicantConverter {


    private final ModelMapper mapper;

    private final CertificateConverter certConverter;

    private final GradeConverter gradeConverter;

    private final FacultyConverter facConverter;


    @Autowired
    public ApplicantConverter(ModelMapper mapper, CertificateConverter certConverter, GradeConverter gradeConverter, FacultyConverter facConverter) {
        this.mapper = mapper;
        this.certConverter = certConverter;
        this.gradeConverter = gradeConverter;
        this.facConverter = facConverter;
    }


    public Applicant toEntity(ApplicantDTO dto) {

        Applicant applicant = mapper.map(dto, Applicant.class);

        applicant.setCertificate(certConverter.toEntity(dto.getCertificate()));

        applicant.setGrades(gradeConverter.toEntityList(dto.getGrades()));

        applicant.setFaculty(facConverter.toEntity(dto.getFaculty()));

        return applicant;
    }

}

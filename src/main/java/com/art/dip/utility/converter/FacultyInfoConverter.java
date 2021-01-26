package com.art.dip.utility.converter;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;
import com.art.dip.repository.SubjectRepository;
import com.art.dip.utility.dto.FacultyInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacultyInfoConverter {

    private final SubjectConverter subjectConverter;

    private final SubjectRepository subjectRepository;

    public FacultyInfoConverter(SubjectConverter subjectConverter, SubjectRepository subjectRepository) {
        this.subjectConverter = subjectConverter;
        this.subjectRepository = subjectRepository;
    }

    private FacultyInfoDTO toFacultyInfoDTO(FacultyInfo facultyInfo) {
        FacultyInfoDTO facultyInfoDTO = new FacultyInfoDTO();

            facultyInfoDTO.setCapacity(facultyInfo.getCapacity());
            facultyInfoDTO.setCountApplicants(facultyInfo.getCountApplicants());
            facultyInfoDTO.setId(facultyInfo.getId());
            facultyInfoDTO.setExpiredDate(facultyInfo.getExpiredDate());
            facultyInfoDTO.setAverageScore(facultyInfo.getAverageScore());
            facultyInfoDTO.setUpdateTime(facultyInfo.getUpdateTime());
            facultyInfoDTO.setExpired(facultyInfo.isAvailable());

        return facultyInfoDTO;
    }


    public FacultyInfoDTO toRuFacultyInfoDTO(FacultyInfo facultyInfo) {
        FacultyInfoDTO facultyInfoDTO = toFacultyInfoDTO(facultyInfo);

        List<Subject> subjects = subjectRepository.findAllByFacultiesContaining(new Faculty(facultyInfo.getFaculty().getId()));
        facultyInfoDTO.setSubjects(subjectConverter.toRuSubjectDTO(subjects));
        facultyInfoDTO.setName(facultyInfo.getFaculty().getRuName());
        return facultyInfoDTO;
    }

    public List<FacultyInfoDTO> toRuFacultyInfoDTO(List<FacultyInfo> infos) {
        return infos.stream().map(this::toRuFacultyInfoDTO).collect(Collectors.toList());
    }


    public FacultyInfoDTO toEnFacultyInfoDTO(FacultyInfo facultyInfo) {
        FacultyInfoDTO facultyInfoDTO = toFacultyInfoDTO(facultyInfo);
        List<Subject> subjects = subjectRepository.findAllByFacultiesContaining(new Faculty(facultyInfo.getFaculty().getId()));
        facultyInfoDTO.setSubjects(subjectConverter.toEnSubjectDTO(subjects));
        facultyInfoDTO.setName(facultyInfo.getFaculty().getName());
        return facultyInfoDTO;
    }

    public List<FacultyInfoDTO> toEnFacultyInfoDTO(List<FacultyInfo> infos) {
        return infos.stream().map(this::toEnFacultyInfoDTO).collect(Collectors.toList());
    }

}

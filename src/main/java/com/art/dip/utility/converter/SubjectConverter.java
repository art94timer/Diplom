package com.art.dip.utility.converter;

import com.art.dip.model.Subject;
import com.art.dip.utility.dto.SubjectDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubjectConverter {


    public SubjectDTO toRuSubjectDTO(Subject subject) {
        SubjectDTO subjectDTO = toSubjectDTO(subject);

        subjectDTO.setName(subject.getRuName());
        return subjectDTO;
    }

    public List<SubjectDTO> toRuSubjectDTO(List<Subject> subjects) {
       return subjects.stream().map(this::toRuSubjectDTO).collect(Collectors.toList());
    }

    public SubjectDTO toEnSubjectDTO(Subject subject) {
        SubjectDTO subjectDTO = toSubjectDTO(subject);
        subjectDTO.setName(subject.getName());
        return subjectDTO;
    }
    public List<SubjectDTO> toEnSubjectDTO(List<Subject> subjects) {
        return subjects.stream().map(this::toRuSubjectDTO).collect(Collectors.toList());
    }


    private SubjectDTO toSubjectDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        return subjectDTO;
    }

    public Subject toEntity(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setId(subjectDTO.getId());
        return subject;
    }
    public List<Subject> toEntity(List<SubjectDTO> subjectDTO) {
        return subjectDTO.stream().map(this::toEntity).collect(Collectors.toList());
    }
}

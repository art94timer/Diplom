package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidApplicant extends BaseEntity {

    private String email;

    @ElementCollection
    @CollectionTable(name = "invalid_applicant_grades",
            joinColumns = {@JoinColumn(name = "applicant_id")})
    @MapKeyColumn(name = "subject_name")
    @Column(name = "file_name")
    private Map<String,String> grades;

    @Column(name = "certificate_file_name")
    private String certificate;
}

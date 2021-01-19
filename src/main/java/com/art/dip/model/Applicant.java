package com.art.dip.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "applicant")
@Data
@EqualsAndHashCode(callSuper = true)
public class Applicant extends BaseEntity {

    @Version
    private long version;
 
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;


    @OneToMany(mappedBy = "applicant",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Grade> grades;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="certificate_id")
    private Certificate certificate; 

    private Integer score;
    
    @Column(name="accepted",columnDefinition = "default false")
    private Boolean isAccepted;
    
    private LocalDateTime registrationTime;
    
}

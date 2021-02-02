package com.art.dip.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;


@Entity
@Table(name = "subject")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Subject extends BaseEntity {

    @Column(unique = true)
    @Pattern(regexp = "[A-Z a-z]+")
    private String name;

    @Pattern(regexp = "[А-Я а-я]+")
    @Column(unique = true)
    private String ruName;
    @ManyToMany
    @JoinTable(name = "faculty_subject",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Faculty> faculties;


}

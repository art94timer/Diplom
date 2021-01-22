package com.art.dip.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "subject")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Subject extends BaseEntity {

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String ruName;
    
    @ManyToMany(mappedBy = "subjects")
    private List<Faculty> faculties;
    
    public Subject(Integer id) {
    	super(id);
    }
}

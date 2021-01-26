package com.art.dip.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Faculty extends BaseEntity {
  
    public Faculty(Integer facultyId) {
		super(facultyId);
	}

	private String name;

    private String ruName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="faculty_subject",joinColumns = @JoinColumn(name = "faculty_id"),
	inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private List<Subject> subjects;
    
	@OneToOne(mappedBy="faculty",fetch = FetchType.LAZY)
	@ToString.Exclude
	private FacultyInfo info;


}

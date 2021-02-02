package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="faculty_subject",joinColumns = @JoinColumn(name = "faculty_id"),
	inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private List<Subject> subjects;
    
	@OneToOne(mappedBy="faculty",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	private FacultyInfo info;

	@OneToOne(mappedBy = "faculty",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private NotifyHolder notifyHolder;

}

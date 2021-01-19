package com.art.dip.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="faculty_info")
@Data
public class FacultyInfo extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name="faculty_id")
	private Faculty faculty;
	
	private Integer capacity;
	
	@Column(name="average")
	private Double averageScore;
	
	@Column(name = "countapp")
	private Integer countApplicants;
	
	private LocalDateTime updateTime;
	
	private LocalDate expiredDate; 
}

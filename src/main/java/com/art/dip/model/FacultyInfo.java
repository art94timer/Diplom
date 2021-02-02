package com.art.dip.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="faculty_info")
@Data
public class FacultyInfo extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name="faculty_id")
	private Faculty faculty;

	@Column(columnDefinition = "integer default 0")
	private Integer capacity;
	
	@Column(name="average",columnDefinition = "numeric default 0.0")
	private Double averageScore;
	
	@Column(name = "countapp",columnDefinition = "integer default 0")
	private Integer countApplicants;

	private LocalDateTime updateTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime expiredDate;

	@Column(name = "available")
	private boolean isAvailable;


}

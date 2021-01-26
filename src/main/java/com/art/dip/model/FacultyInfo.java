package com.art.dip.model;

import com.art.dip.utility.dto.SubjectDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="faculty_info")
@Data
public class FacultyInfo extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name="faculty_id")
	@ToString.Exclude
	private Faculty faculty;

	@Column(columnDefinition = "default 0")
	private Integer capacity;
	
	@Column(name="average",columnDefinition = "default 0")
	private Double averageScore;
	
	@Column(name = "countapp",columnDefinition = "default 0")
	private Integer countApplicants;
	
	private LocalDateTime updateTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime expiredDate;

	@Column(name = "available")
	private boolean isAvailable;


}

package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "grade")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Grade extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name="applicant_id")
	private Applicant applicant;

	@OneToOne
	private Subject subject;

	private Integer mark;

	private String fileName;
	
	
}

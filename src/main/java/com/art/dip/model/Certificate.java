package com.art.dip.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Certificate extends BaseEntity{

	private Integer mark;
	
	private String fileName;
	
}

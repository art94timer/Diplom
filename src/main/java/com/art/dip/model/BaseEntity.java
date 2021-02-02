package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
	
	@Id
	@GeneratedValue(generator = "custom_sequence",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "custom_sequence",sequenceName = "custom_sequence",
			initialValue = 1000,allocationSize = 1)
	private Integer id;

}

package com.art.dip.utility.dto;

import com.art.dip.annotation.ValidateAppForm;
import com.art.dip.model.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidateAppForm
public class ApplicantDTO {


	@Size(min=1)
	@Valid
	private List<GradeDTO> grades;

	@Valid
	private CertificateDTO certificate;

	@Valid
	private FacultyDTO faculty;


}

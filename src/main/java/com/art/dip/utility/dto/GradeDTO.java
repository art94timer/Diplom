package com.art.dip.utility.dto;


import com.art.dip.model.Subject;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class GradeDTO {

	@NotNull
    private Subject subject;

	@Range(min = 1,max = 100)
    private Integer mark;

    private MultipartFile file;
}

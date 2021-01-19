package com.art.dip.utility.validation;

import com.art.dip.annotation.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator  implements ConstraintValidator<Date,String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
		return value != null && !value.isEmpty() && LocalDate.parse(value).isBefore(LocalDate.now());
	}catch(DateTimeParseException e) {
		return false;
	}

	
	}
}

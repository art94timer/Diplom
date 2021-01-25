package com.art.dip.utility.validation;

import com.art.dip.annotation.Name;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name,String> { 
	
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && Pattern.matches("[a-zA-Zа-яА-Я]+", value);
	}

}

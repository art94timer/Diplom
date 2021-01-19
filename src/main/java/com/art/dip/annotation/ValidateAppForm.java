package com.art.dip.annotation;


import com.art.dip.utility.validation.AppFormValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AppFormValidator.class)
public @interface ValidateAppForm {

    String message() default
         "";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

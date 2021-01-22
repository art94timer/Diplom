package com.art.dip.annotation;

import com.art.dip.utility.validation.PasswordValidator;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {


	String message() default "Passwords not matches";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

package com.art.dip.utility.dto;


import com.art.dip.annotation.Date;
import com.art.dip.annotation.Name;
import com.art.dip.annotation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Password
public class PersonDTO {
	
	@NotNull
	@NotBlank
	@Name
	private String firstName;
	
	@NotNull
	@NotBlank
	@Name
	private String lastName;

	@NotNull
	@NotBlank
	@Date
	private String birthdate;
	
	@NotNull
	@NotBlank
	@Email
	private String email;
	
	
	@NotNull
	@NotBlank
	@Size(min = 5,max = 50)
	private String password;
	
	@NotNull
	@NotBlank
	@Size(min = 5,max = 50)
	private String confirmPassword;
}

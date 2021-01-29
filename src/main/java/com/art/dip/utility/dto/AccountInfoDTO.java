package com.art.dip.utility.dto;

import com.art.dip.model.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDTO {

    private String fullName;

    private LocalDate birthdate;

    private String email;

    private Faculty faculty;

    private Integer score;

    private ApplicationStatus status;


}

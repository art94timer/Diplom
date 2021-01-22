package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verify_token")
public class VerifyToken {

	private static final int EXP_TIME = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "person_id")
	private Person person;

	private String token;
	
	private LocalDate expireDate = calculateExpiryDate();
	
	
	public VerifyToken(Person person, String token) {
		this.person = person;
		this.token = token;
	}
    private LocalDate calculateExpiryDate() {
        return LocalDate.now().plusDays(1);
    }

    public VerifyToken(String token) {
		this.token = token;
	}
}

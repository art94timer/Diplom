package com.art.dip.model;

import com.art.dip.utility.converter.LocaleConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table(name = "person",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {
  
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "email",unique = true)
    private String email;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id")
    private Credential credential;

    @Column(name = "role")
    private Role role;
    
    private boolean enabled;


    @Convert(converter = LocaleConverter.class)
    private Locale locale;
    
    public Person(Integer id) {
    	super(id);
    }

    public Person(Integer id, String firstName, String lastName, LocalDate birthdate, String email, Credential credential, Role role, boolean enabled) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.credential = credential;
        this.role = role;
        this.enabled = enabled;
    }
}

package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    
    public Person(Integer id) {
    	super(id);
    }

}

package com.art.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotifyHolder extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "notify_email", joinColumns = @JoinColumn(name = "notify_id"))
    @Column(name = "email")
    private List<String> emails;
}

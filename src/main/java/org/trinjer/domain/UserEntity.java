package org.trinjer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id @GeneratedValue
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    @Column(unique = true)
    private String email;
}

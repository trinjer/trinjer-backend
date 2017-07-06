package org.trinjer.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
@Entity
@Table(name = "Users")
@Data
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
}

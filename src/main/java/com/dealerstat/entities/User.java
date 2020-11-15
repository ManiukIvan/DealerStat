package com.dealerstat.entities;

import com.dealerstat.configs.UserEntityConfig;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;


@Getter
@Setter
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @Size(max = UserEntityConfig.FIRST_NAME_MAX_SIZE)
    private String firstName;

    @Column
    @Size(max = UserEntityConfig.SECOND_NAME_MAX_SIZE)
    private String lastName;

    @Column
    @Size(max = UserEntityConfig.PASSWORD_MAX_SIZE)
    private String password;

    @Column(unique = true)
    @Size(max = UserEntityConfig.EMAIL_MAX_SIZE)
    private String email;

    @Column
    private Date createdDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.email = "";
        this.createdDate = new Date(0L);
        this.role = null;
    }

    public User(@Size(max = UserEntityConfig.FIRST_NAME_MAX_SIZE) String firstName,
                @Size(max = UserEntityConfig.SECOND_NAME_MAX_SIZE) String lastName,
                @Size(max = UserEntityConfig.PASSWORD_MAX_SIZE) String password,
                @Size(max = UserEntityConfig.EMAIL_MAX_SIZE) String email,
                Date createdDate,
                Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createdDate = createdDate;
        this.role = role;
    }

    public User(Long id) {
        this.id = id;
    }
}

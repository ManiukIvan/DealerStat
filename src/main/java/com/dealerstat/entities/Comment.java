package com.dealerstat.entities;


import com.dealerstat.configs.CommentEntityConfig;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Date;

@Getter
@Setter
@Valid
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Size(max = CommentEntityConfig.MESSAGE_MAX_SIZE, message = "Tool long")
    private String message;

    @Column
    @NotNull
    private Date createdAt;

    @Column
    @NotNull
    private boolean approved;

    @Column
    @NotNull
    @Min(CommentEntityConfig.APPRAISAL_MIN_VALUE)
    @Max(CommentEntityConfig.APPRAISAL_MAX_VALUE)
    private int appraisal;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {
        this.message = null;
        this.createdAt = null;
        this.approved = false;
        this.user = null;
    }

    public Comment(@Size(max = CommentEntityConfig.MESSAGE_MAX_SIZE) String message,
                   Date createdAt,
                   boolean approved,
                   User user) {
        this.message = message;
        this.createdAt = createdAt;
        this.approved = approved;
        this.user = user;
    }
}

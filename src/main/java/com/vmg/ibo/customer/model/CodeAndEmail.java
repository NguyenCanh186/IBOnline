package com.vmg.ibo.customer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "code_and_email")
public class CodeAndEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String email;
    private Date createAt;

    public CodeAndEmail(String code, String email) {
        this.code = code;
        this.email = email;
    }

    public CodeAndEmail(String code, String email, Date createAt) {
        this.code = code;
        this.email = email;
        this.createAt = createAt;
    }

    public CodeAndEmail() {
    }
}

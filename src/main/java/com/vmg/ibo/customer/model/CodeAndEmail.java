package com.vmg.ibo.customer.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "code_and_email")
public class CodeAndEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String email;

    public CodeAndEmail(String code, String email) {
        this.code = code;
        this.email = email;
    }

    public CodeAndEmail() {
    }
}

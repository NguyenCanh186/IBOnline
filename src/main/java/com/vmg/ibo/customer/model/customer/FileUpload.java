package com.vmg.ibo.customer.model.customer;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "FILES")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private Long idUser;
    private Long financialReportId;

    public FileUpload() {

    }
    public FileUpload(String filename, Long idUser) {
        this.filename = filename;
        this.idUser = idUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return filename;
    }

    public void setFile(String file) {
        this.filename = file;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}

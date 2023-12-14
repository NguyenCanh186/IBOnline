package com.vmg.ibo.core.model.customer;

import com.vmg.ibo.core.model.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String file;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public File(String file, String title) {
        this.file = file;
        this.title = title;
    }

    public File() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

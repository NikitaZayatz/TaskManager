package com.tasks.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;


@Entity
@Table(name = "task_file_junction")
public class TaskFileJunction {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoth;

    public void setId(Long id) {
        this.idBoth = id;
    }

    public Long getId() {
        return idBoth;
    }


}

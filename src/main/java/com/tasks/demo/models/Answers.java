package com.tasks.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Answers {

    @Id
    private Long id;




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

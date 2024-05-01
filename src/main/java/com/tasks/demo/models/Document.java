package com.tasks.demo.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    private String fileExtension;
    private String filePath;

    @Column(columnDefinition = "boolean default false")
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Document() {
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Document(String fileName, String fileExtension, String filePath) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.filePath = filePath;
    }

    public Document(String uploadDir) {
        this.filePath = uploadDir;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
package com.tasks.demo.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="task")
public class Task implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="task_id")
    private Integer task_id;

    private String name;
    private String description;
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Task(String name, String description, LocalDateTime deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    private Set<AplicationUser> users;


  /*  @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="task_file_junction",
            joinColumns ={@JoinColumn(name="task_id")},
            inverseJoinColumns = {@JoinColumn(name="id")}
    )
    private Set<Document> files;*/

 /*   public Set<Document> getFiles() {
        return files;
    }

    public void setFiles(Set<Document> files) {
        this.files = files;
    }
*/
    public String getUsers() {
        String username ="";
        for (AplicationUser user : users) {
             username += user.getUsername() + "  ";
        }
        return username;
    }

    public void setUsers(Set<AplicationUser> users) {
        this.users = users;
    }

    public Task() {
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}


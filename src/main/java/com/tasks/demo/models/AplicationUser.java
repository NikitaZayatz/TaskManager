package com.tasks.demo.models;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class AplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer userId;

    private String username;

    private String password;

    private String department;

    private String workPhone;

    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_task_junction",
            joinColumns ={@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="task_id")}
    )
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role_junction",
            joinColumns ={@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    public AplicationUser() {
        super();
        this.authorities = new HashSet<Role>();
    }




    public AplicationUser(String username, String password, String department, String workPhone, String email, Set<Role> authorities) {
        this.username = username;
        this.password = password;
        this.department = department;
        this.workPhone = workPhone;
        this.email = email;
        this.authorities = authorities;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    public void setAuthorities(Set<Role> authorities) {
         this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}


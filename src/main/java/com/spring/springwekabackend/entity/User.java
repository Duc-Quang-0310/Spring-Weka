package com.spring.springwekabackend.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String roles;

    @Column(columnDefinition = "varchar(20) default 'INITIAL'")
    private String status;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "created_at")
    private Date createdAt;

    @Column(name = "recent_login_time")
    private Date recentLoginTime;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getRecentLoginTime() {
        return recentLoginTime;
    }

    public void setRecentLoginTime(Date recentLoginTime) {
        this.recentLoginTime = recentLoginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", recentLoginTime=" + recentLoginTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

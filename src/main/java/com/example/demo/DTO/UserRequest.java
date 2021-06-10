package com.example.demo.DTO;

import java.util.List;

public class UserRequest {
    private String name;
    private String surname;
    private String role;
    private String password;
    private List<String> project;

    public List<String> getProject() {
        return project;
    }

    public void setProject(List<String> project) {
        this.project = project;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

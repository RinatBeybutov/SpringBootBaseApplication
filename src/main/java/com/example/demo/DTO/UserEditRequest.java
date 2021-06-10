package com.example.demo.DTO;

import java.util.List;

public class UserEditRequest {
    private int id;
    private String role;
    private List<String> projects;

    public int getId() {
        return id;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

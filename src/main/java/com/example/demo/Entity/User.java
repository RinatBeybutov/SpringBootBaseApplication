package com.example.demo.Entity;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {


    public User ()
    {

    }

    public User(String name, String surname, RoleType role, Project project) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_project")
    private Project project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

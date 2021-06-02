package com.example.demo.Controller;

import com.example.demo.DTO.ProjectRequest;
import com.example.demo.DTO.UserEditRequest;
import com.example.demo.DTO.UserDto;
import com.example.demo.DTO.UserRequest;
import com.example.demo.Entity.*;
import com.example.demo.Service.ProjectService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    Writer writer;

    @Autowired
    ReaderConsole readerConsole;

    @Autowired
    ReaderFile readerFile;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    /*@GetMapping("/")
    public String index() {
        //writer.printString(readerFile);
        return "index";
    }*/

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public void addNewUsers(@RequestBody UserRequest userRequest) throws IOException {
        userService.addNewUsers(userRequest);
    }

    @PutMapping("/users")
    public void changeUser(@RequestBody UserEditRequest userEditRequest) {
        userService.editUser(userEditRequest);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/projects")
    public void addNewProjects(@RequestBody ProjectRequest projectRequest) throws IOException {
        projectService.addNewProject(projectRequest);
    }


    @RequestMapping("/users/{name}")
    public String getUserByName(@PathVariable("name") String name) {
        StringBuilder builder = new StringBuilder();
        for (User user : userService.getUserByName(name)) {
            builder.append(user.getName() + "  " + user.getSurname() + "\n\n");
        }
        return builder.toString();
    }

}

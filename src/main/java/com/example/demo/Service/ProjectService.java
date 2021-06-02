package com.example.demo.Service;

import com.example.demo.DTO.ProjectRequest;
import com.example.demo.Entity.Project;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;


    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);
    }

    public void addNewProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setName(projectRequest.getName());

        projectRepository.save(project);
    }
}

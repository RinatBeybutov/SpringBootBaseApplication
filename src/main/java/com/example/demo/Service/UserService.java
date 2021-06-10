package com.example.demo.Service;

import com.example.demo.DTO.SingleUserDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.DTO.UserEditRequest;
import com.example.demo.DTO.UserRequest;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.RoleType;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    public ResponseEntity<List<UserDto>> getAllUsers() {


        List<User> listUser = userRepository.findAllAsk();

        List<UserDto> userDtoList = listUser.stream()
                .map(this::convertFromUserToUserDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    private UserDto convertFromUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setRole(user.getRole().toString());
        //userDto.setProject(user.getProject()!=null ? user.getProject().getName() : "");

        return userDto;
    }

    public User getUserByName(String name) {
        return userRepository.findAllByName(name);
    }

    public void addNewUsers(UserRequest userRequest) throws IOException {

        List<Project> projects = userRequest.getProject()
                .stream()
                .map(project -> projectRepository.findByName(project))
                .collect(Collectors.toList());
        //Project project = projectRepository.findByName(userRequest.getProject());

        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getPassword(),
                userRequest.getRole().equals("DEVELOPER") ? RoleType.DEVELOPER : RoleType.MANAGER);
        user.setProject(projects);

        userRepository.save(user);

        /*String csvFile = "src/main/resources/users.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile), ',', '"', 0);
        List<String[]> allRows = reader.readAll();
        for(String[] row : allRows){

            Project project = projectRepository.findById(Integer.parseInt(row[3])).get();

            User user = new User(row[0], row[1],
                    row[2].equals("DEVELOPER") ? RoleType.DEVELOPER : RoleType.MANAGER, project);

            if(userRepository.findAllByName(user.getName()).size() == 0)
            {
                userRepository.save(user);
            }
            System.out.println(Arrays.toString(row));
        }*/

    }


    public ResponseEntity<SingleUserDto> getUserById(int id) {

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return new ResponseEntity<SingleUserDto>(convertFromUserToSingleUserDto(userOptional.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private SingleUserDto convertFromUserToSingleUserDto(User user) {
        SingleUserDto singleUserDto = new SingleUserDto();
        singleUserDto.setId(user.getId());
        singleUserDto.setName(user.getName());
        singleUserDto.setSurname(user.getSurname());
        singleUserDto.setRole(user.getRole().toString());
        List<String> listProjects = user.getProject()
                .stream()
                .map(Project::getName)
                .collect(Collectors.toList());
        singleUserDto.setProjects(listProjects);
        return singleUserDto;
    }

    public void editUser(UserEditRequest userEditRequest) {
        User user = userRepository.findById(userEditRequest.getId()).get();
        user.setRole(userEditRequest.getRole().equals("DEVELOPER") ? RoleType.DEVELOPER : RoleType.MANAGER);
        List<String> oldProjects = user.getProject()
                .stream()
                .map(Project::getName)
                .collect(Collectors.toList());

        for (String str : userEditRequest.getProjects()) {
            if(!oldProjects.contains(str)) {
                Project project = projectRepository.findByName(str);
                user.getProject().add(project);

            }
        }
        //Project project = projectRepository.findByName(userEditRequest.getProject());
        //user.setProject(project);

        userRepository.save(user);

    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}

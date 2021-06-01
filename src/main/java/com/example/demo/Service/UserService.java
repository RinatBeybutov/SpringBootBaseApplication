package com.example.demo.Service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.example.demo.DTO.UserDto;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
        userDto.setProject(user.getProject().getName());

        return userDto;
    }


    public List<User> getUserByName(String name) {
        return userRepository.findAllByName(name);
    }


    public void addNewUsers(UserRequest userRequest) throws IOException {

        Project project = projectRepository.findByName(userRequest.getProject());

        User user = new User(userRequest.getName(), userRequest.getSurname(),
                userRequest.getRole().equals("DEVELOPER") ? RoleType.DEVELOPER : RoleType.MANAGER, project);

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


    public ResponseEntity<UserDto> getUserById(int id) {

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return new ResponseEntity<UserDto>(convertFromUserToUserDto(userOptional.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

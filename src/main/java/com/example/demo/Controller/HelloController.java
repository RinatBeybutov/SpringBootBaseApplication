package com.example.demo.Controller;

import com.example.demo.DTO.UserDto;
import com.example.demo.DTO.UserRequest;
import com.example.demo.Entity.ReaderConsole;
import com.example.demo.Entity.ReaderFile;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Writer;
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
        System.out.println(userRequest.getName());
        userService.addNewUsers(userRequest);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
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

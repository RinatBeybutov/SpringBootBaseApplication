package com.example.demo.Controller;

import com.example.demo.DTO.*;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Redis.RedisRepository;
import com.example.demo.Redis.RedisRepositoryImpl;
import com.example.demo.Redis.UserRedis;
import com.example.demo.Service.ProjectService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    private RedisRepository redisRepository;

    /*@GetMapping("/")
    public String index() {
        //writer.printString(readerFile);
        return "index";
    }*/

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam("username") String name,
                                     @RequestParam("password") String password) //@RequestBody TokenRequest tokenRequest)
    {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", name);
        formData.add("password", password);
        formData.add("grant_type", "password");

        WebClient webClient = WebClient.create();
        TokenResponse tokenResponse = webClient
                .post()
                .uri("http://localhost:8080/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic dGVzdDpjbGllbnQtc2VjcmV0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(TokenResponse.class).block();

        UserRedis userRedis = new UserRedis();
        userRedis.setName(name);
        userRedis.setId(++RedisRepositoryImpl.countActiveUsers);
        redisRepository.add(userRedis);

        return new ResponseEntity<TokenResponse>(tokenResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(@RequestParam("name") String name) {
        Map<Object, Object> users = redisRepository.findAllUsers();
        for(Map.Entry<Object, Object> pair : users.entrySet())
        {
            if(((String)pair.getValue()).equals(name));
            redisRepository.delete(pair.getKey().toString());
            break;
        }
        System.out.println("logout!: ");

    }

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
    //@PreAuthorize("!hasAuthority('DEVELOPER')")
    public ResponseEntity<SingleUserDto> getUserById(@PathVariable("id") int id, @RequestParam("name") String nameAuth) {
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

    /*public ResponseEntity<?> validateToken( // токен)
    {

        // return ok
    }*/


    @RequestMapping("/users/{name}")
    public String getUserByName(@PathVariable("name") String name) {
        StringBuilder builder = new StringBuilder();
        User user = userService.getUserByName(name);
        builder.append(user.getName() + "  " + user.getSurname() + "\n\n");

        return builder.toString();
    }

}

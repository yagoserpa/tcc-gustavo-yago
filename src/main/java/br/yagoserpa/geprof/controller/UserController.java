package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/v1/user/{id}")
    public User find(
        @PathVariable(value = "id") String id
    ) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/user")
    public List<User> all() {
        return userRepository.findAll();
    }

    @PutMapping("/api/v1/user/{id}")
    public void update(
            @PathVariable(value = "id") Integer id,
            @RequestBody User user
    ) {
        userRepository.update(id, user);
    }

    @DeleteMapping("/api/v1/user/{id}")
    public void delete(
        @PathVariable(value = "id") String id
    ) {
        userRepository.delete(id);
    }

}

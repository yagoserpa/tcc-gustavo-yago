package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Credentials;
import br.yagoserpa.geprof.model.LoginResponse;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.*;
import java.math.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final String secret;

    public UserController(
            UserRepository userRepository,
            @Value("${jwtsecret}") String secret
    ) {
        this.userRepository = userRepository;
        this.secret = secret;
    }

    @PostMapping("/api/v1/auth")
    public ResponseEntity<LoginResponse> auth(
            @RequestBody Credentials credentials
    ) throws NoSuchAlgorithmException {
        var userOptional = userRepository.findByLogin(credentials.getUsername(), credentials.getPassword());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userOptional.get();
        var token = Jwts.builder()
                .claim("id", user.getId())
                //.setExpiration(new Date(new Date().getTime() + 600_000))// TODO: expirar o token
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();

//        return ResponseEntity.ok(Map.of("access_token", token));
        return ResponseEntity.ok(new LoginResponse(token, user));
    }

    @GetMapping("/api/v1/user/{id}")
    public User find(
            @PathVariable(value = "id") Integer id
    ) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/user")
    public List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/api/v1/user/")
    public void insert(
            @RequestBody User user
    ) {
        userRepository.insert(user);
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
            @PathVariable(value = "id") Integer id
    ) {
        userRepository.delete(id);
    }

}

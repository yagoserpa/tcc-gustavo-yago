package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Credentials;
import br.yagoserpa.geprof.model.LoginResponse;
import br.yagoserpa.geprof.model.RegisterToken;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.RegisterTokenRepository;
import br.yagoserpa.geprof.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RegisterTokenRepository registerTokenRepository;
    private final String secret;

    @Autowired
    private JavaMailSender javaMailSender;

    public UserController(
            UserRepository userRepository,
            RegisterTokenRepository registerTokenRepository,
            @Value("${jwtsecret}") String secret
    ) {
        this.userRepository = userRepository;
        this.registerTokenRepository = registerTokenRepository;
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
            @PathVariable(value = "id") Long id
    ) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/public/user/{id}")
    public User publicFind(
            @PathVariable(value = "id") Long id
    ) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/user")
    public ResponseEntity<List<User>> all(@RequestParam(name = "token") Optional<String> tokenOptional) {
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            var token = tokenOptional.get();
            var savedTokenOptional = registerTokenRepository.findByToken(token);

            if (savedTokenOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            var savedToken = savedTokenOptional.get();

            var userOptional = userRepository.findById(savedToken.getUserId());

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            var user = userOptional.get();

            return ResponseEntity.ok(List.of(user));
        }
    }

    @PostMapping("/api/v1/user")
    public ResponseEntity<Void> insert(
            @RequestBody User user
    ) {
        user.setStatus(User.Status.ACTIVE);
        if (user.getUserType() == User.Type.STUDENT) {
            user.setOrigin("Universidade Federal do Rio de Janeiro");
        }

        var userOptional = userRepository.insert(user);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var insertedUser = userOptional.get();
        String registerToken = UUID.randomUUID().toString();

        registerTokenRepository.insert(new RegisterToken(insertedUser.getId(), registerToken));

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Confirmação de acesso ao GeProFi");
        mail.setText("Bem-vindo à plataforma de Gerenciamento de Projetos Finais. " +
                "Para finalizar seu cadastro, clique aqui: http://geprofi-front.herokuapp.com/register?token=" +
                registerToken);

        javaMailSender.send(mail);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") Long id,
            @RequestBody User updatedUser
    ) {
        // Edit profile
        if (updatedUser.getCurrentPassword() != null && !updatedUser.getCurrentPassword().isEmpty()) {
            if (!userRepository.isValidUserPassword(id, updatedUser.getCurrentPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        // Registration || !Password Change
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(updatedUser.getCurrentPassword());
        }

        userRepository.update(id, updatedUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/user/{id}")
    public void delete(
            @PathVariable(value = "id") Long id
    ) {
        userRepository.delete(id);
    }

}

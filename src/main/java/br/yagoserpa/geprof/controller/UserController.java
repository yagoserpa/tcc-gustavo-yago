package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.*;
import br.yagoserpa.geprof.repository.FieldOfInterestHasUserRepository;
import br.yagoserpa.geprof.repository.ProjectHasUserRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final FieldOfInterestHasUserRepository fieldOfInterestHasUserRepository;
    private final RegisterTokenRepository registerTokenRepository;
    private final String secret;

    @Autowired
    private JavaMailSender javaMailSender;

    public UserController(
            UserRepository userRepository,
            RegisterTokenRepository registerTokenRepository,
            FieldOfInterestHasUserRepository fieldOfInterestHasUserRepository,
            @Value("${jwtsecret}") String secret
    ) {
        this.userRepository = userRepository;
        this.registerTokenRepository = registerTokenRepository;
        this.fieldOfInterestHasUserRepository = fieldOfInterestHasUserRepository;
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
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();

        return ResponseEntity.ok(new LoginResponse(token, user));
    }

    @GetMapping("/api/v1/user/{id}")
    public User find(
            @PathVariable(value = "id") Long id
    ) {
        var user = userRepository.findById(id).orElse(null);

        if (user != null) {
            var users = fieldOfInterestHasUserRepository.findByUserId(id);
            user.setFieldOfInterestList(users);
        }

        return user;
    }

    @GetMapping("/api/v1/public/user/{id}")
    public User publicFind(
            @PathVariable(value = "id") Long id
    ) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/public/user")
    public ResponseEntity<List<User>> userByToken(@RequestParam(name = "token") String token) {
        var savedTokenOptional = registerTokenRepository.findByToken(token);

        if (savedTokenOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var savedToken = savedTokenOptional.get();

        var userOptional = userRepository.findById(savedToken.getUserId());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var user = userOptional.get();

        return ResponseEntity.ok(List.of(user));
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/api/v1/users/active")
    public ResponseEntity<List<User>> allActive() {
        return ResponseEntity.ok(userRepository.findAllActive());
    }

    @PostMapping("/api/v1/user")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> insert(
            @RequestBody User user
    ) {
        user.setStatus(User.Status.ACTIVE);
        if (user.getUserType() == User.Type.STUDENT || user.getUserType() == User.Type.TEACHER) {
            user.setOrigin("Universidade Federal do Rio de Janeiro");
        }

        var userOptional = userRepository.insert(user);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
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
                return ResponseEntity.badRequest().build();
            }
        }

        // Registration || !Password Change
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(updatedUser.getCurrentPassword());
        }

        userRepository.update(id, updatedUser);

        registerTokenRepository.deleteByUserId(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/user/{id}/activate")
    public ResponseEntity<Void> activateUser(
            @PathVariable(value = "id") Long id
    ) {
        userRepository.activateUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/user/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(
            @PathVariable(value = "id") Long id
    ) {
        userRepository.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/public/user/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable(value = "id") Long id,
            @RequestBody User updatedUser
    ) {
        userRepository.update(id, updatedUser);

        registerTokenRepository.deleteByUserId(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/public/user/forgotpassword")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> forgotPassword(
            @RequestBody Email email
    ) {
        var userOptional = userRepository.findByEmail(email.getEmail());

        if (userOptional.isPresent()) {
            var user = userOptional.get();

            String registerToken = UUID.randomUUID().toString();

            registerTokenRepository.insert(new RegisterToken(user.getId(), registerToken));

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setSubject("Recuperação de senha do GeProFi");
            mail.setText("Olá, aqui está o link para recuperar sua senha: " +
                    "http://geprofi-front.herokuapp.com/forgotpassword?token=" + registerToken);

            javaMailSender.send(mail);
        }

        return ResponseEntity.ok().build();
    }
}

package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Auth;
import br.yagoserpa.geprof.model.FieldOfInterest;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.FieldOfInterestHasUserRepository;
import br.yagoserpa.geprof.repository.FieldOfInterestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
public class FieldOfInterestHasUserController {

    private final FieldOfInterestHasUserRepository fieldOfInterestHasUserRepository;
    private final FieldOfInterestRepository fieldOfInterestRepository;

    public FieldOfInterestHasUserController(FieldOfInterestHasUserRepository fieldOfInterestHasUserRepository,
                                            FieldOfInterestRepository fieldOfInterestRepository) {
        this.fieldOfInterestHasUserRepository = fieldOfInterestHasUserRepository;
        this.fieldOfInterestRepository = fieldOfInterestRepository;
    }

    @GetMapping("/api/v1/field/{id}/users")
    public List<User> findByField(
            @PathVariable(value = "id") Integer id
    ) {
        return fieldOfInterestHasUserRepository.findByFieldId(id);
    }

    @GetMapping("/api/v1/public/field/{id}/users")
    public List<User> publicFindByField(
            @PathVariable(value = "id") Integer id
    ) {
        return fieldOfInterestHasUserRepository.findByFieldId(id);
    }

    @GetMapping("/api/v1/public/user/{id}/fields")
    public List<FieldOfInterest> publicFindByUser(
            @PathVariable(value = "id") Long id
    ) {
        return fieldOfInterestHasUserRepository.findByUserId(id);
    }

    @GetMapping("/api/v1/user/{id}/fields")
    public List<FieldOfInterest> findByUser(
            @PathVariable(value = "id") Long id
    ) {
        return fieldOfInterestHasUserRepository.findByUserId(id);
    }

    @GetMapping("/api/v1/user/fields")
    public List<FieldOfInterest> findByAuthUserId(
            ServletRequest request
    ) {
        Auth auth = (Auth) request.getAttribute("auth");
        return fieldOfInterestHasUserRepository.findByUserId(auth.getId());
    }

    @PostMapping("/api/v1/field/new/user/{userId}")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> insert(
            @PathVariable(value = "userId") Integer userId,
            @RequestBody FieldOfInterest fieldOfInterest
    ) {
        var insertedFieldOfInterest = fieldOfInterestRepository.insert(fieldOfInterest);

        fieldOfInterestHasUserRepository.insert(insertedFieldOfInterest.getId(), userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/field/{id}/user/{userId}")
    public ResponseEntity<Void> insert(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "userId") Integer userId
    ) {
        fieldOfInterestHasUserRepository.insert(id, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/field/{id}/user/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "userId") Integer userId
    ) {
        fieldOfInterestHasUserRepository.delete(id, userId);

        return ResponseEntity.ok().build();
    }

}

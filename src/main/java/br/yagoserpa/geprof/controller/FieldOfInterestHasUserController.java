package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.FieldOfInterest;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.FieldOfInterestHasUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FieldOfInterestHasUserController {

    private final FieldOfInterestHasUserRepository fieldOfInterestHasUserRepository;

    public FieldOfInterestHasUserController(FieldOfInterestHasUserRepository fieldOfInterestHasUserRepository) {
        this.fieldOfInterestHasUserRepository = fieldOfInterestHasUserRepository;
    }

    @GetMapping("/api/v1/field/{id}/users")
    public List<User> findByField(
            @PathVariable(value = "id") Integer id
    ) {
        return fieldOfInterestHasUserRepository.findByFieldId(id);
    }

    @GetMapping("/api/v1/user/{id}/fields")
    public List<FieldOfInterest> findByUser(
            @PathVariable(value = "id") Integer id
    ) {
        return fieldOfInterestHasUserRepository.findByUserId(id);
    }

    @PostMapping("/api/v1/field/{id}/user/{userId}")
    public void insert(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "userId") Integer userId
    ) {
        fieldOfInterestHasUserRepository.insert(id, userId);
    }

    @DeleteMapping("/api/v1/field/{id}/user/{userId}")
    public void delete(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "userId") Integer userId
    ) {
        fieldOfInterestHasUserRepository.delete(id, userId);
    }

}

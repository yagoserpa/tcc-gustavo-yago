package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.FieldOfInterest;
import br.yagoserpa.geprof.repository.FieldOfInterestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FieldOfInterestController {

    private final FieldOfInterestRepository fieldOfInterestRepository;

    public FieldOfInterestController(FieldOfInterestRepository fieldOfInterestRepository) {
        this.fieldOfInterestRepository = fieldOfInterestRepository;
    }

    @GetMapping("/api/v1/field/{id}")
    public FieldOfInterest find(
            @PathVariable(value = "id") Integer id
    ) {
        return fieldOfInterestRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/field")
    public List<FieldOfInterest> all() {
        return fieldOfInterestRepository.findAll();
    }

    @GetMapping("/api/v1/public/field")
    public List<FieldOfInterest> publicAll() {
        return fieldOfInterestRepository.findAll();
    }

    @PostMapping("/api/v1/field/")
    public FieldOfInterest insert(
            @RequestBody FieldOfInterest fieldOfInterest
    ) {
        return fieldOfInterestRepository.insert(fieldOfInterest);
    }

    @PutMapping("/api/v1/field/{id}")
    public void update(
            @PathVariable(value = "id") Integer id,
            @RequestBody FieldOfInterest fieldOfInterest
    ) {
        fieldOfInterestRepository.update(id, fieldOfInterest);
    }

    @DeleteMapping("/api/v1/field/{id}")
    public void delete(
            @PathVariable(value = "id") Integer id
    ) {
        fieldOfInterestRepository.delete(id);
    }

}

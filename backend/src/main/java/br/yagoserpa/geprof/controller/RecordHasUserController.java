package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.RecordHasUser;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.RecordHasUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordHasUserController {

    private final RecordHasUserRepository recordHasUserRepository;

    public RecordHasUserController(RecordHasUserRepository recordHasUserRepository) {
        this.recordHasUserRepository = recordHasUserRepository;
    }

    @GetMapping("/api/v1/project/{id}/record/users")
    public List<User> findById(
            @PathVariable(value = "id") Integer id
    ) {
        return recordHasUserRepository.findById(id);
    }

    @PostMapping("/api/v1/project/{id}/record/user")
    public void insert(
            @PathVariable(value = "id") Integer id,
            @RequestBody RecordHasUser recordHasUser
    ) {
        recordHasUserRepository.insert(id, recordHasUser);
    }

}

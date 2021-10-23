package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.RecordHasUser;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.RecordHasUserRepository;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/v1/project/{projectId}/record/{recordId}/user/{userId}/sign")
    public ResponseEntity<Void> insert(
            @PathVariable(value = "projectId") Integer projectId,
            @PathVariable(value = "recordId") Integer recordId,
            @PathVariable(value = "userId") Integer userId,
            @RequestBody RecordHasUser recordHasUser
    ) {
        recordHasUserRepository.insert(projectId, recordId, userId, recordHasUser.getSignature());



        return ResponseEntity.ok().build();
    }

}

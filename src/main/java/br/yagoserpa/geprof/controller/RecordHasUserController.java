package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.RecordHasUser;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.ProjectHasUserRepository;
import br.yagoserpa.geprof.repository.ProjectRepository;
import br.yagoserpa.geprof.repository.RecordHasUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordHasUserController {

    private final RecordHasUserRepository recordHasUserRepository;
    private final ProjectHasUserRepository projectHasUserRepository;
    private final ProjectRepository projectRepository;

    public RecordHasUserController(RecordHasUserRepository recordHasUserRepository,
                                   ProjectHasUserRepository projectHasUserRepository,
                                   ProjectRepository projectRepository) {
        this.recordHasUserRepository = recordHasUserRepository;
        this.projectHasUserRepository = projectHasUserRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/api/v1/project/{id}/record/users")
    public List<User> findById(
            @PathVariable(value = "id") Integer id
    ) {
        return recordHasUserRepository.findById(id);
    }

    @PostMapping("/api/v1/project/{projectId}/record/{recordId}/user/{userId}/sign")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> insert(
            @PathVariable(value = "projectId") Integer projectId,
            @PathVariable(value = "recordId") Integer recordId,
            @PathVariable(value = "userId") Integer userId,
            @RequestBody RecordHasUser recordHasUser
    ) {
        recordHasUserRepository.insert(projectId, recordId, userId, recordHasUser.getSignature());

        var projectParticipants = projectHasUserRepository.findByProjectId(projectId);
        var projectParticipantsSigned = recordHasUserRepository.findById(recordId);

        if (projectParticipants.size() == projectParticipantsSigned.size()) {
            var projectOptional = projectRepository.findById(projectId);

            if (projectOptional.isPresent()) {
                var project = projectOptional.get();
                project.setStatus(Project.Status.ENDED);
                projectRepository.update(projectId, project);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.ok().build();
        }
    }

}

package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.Record;
import br.yagoserpa.geprof.repository.ProjectRepository;
import br.yagoserpa.geprof.repository.RecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final RecordRepository recordRepository;
    private final ProjectRepository projectRepository;

    public RecordController(RecordRepository recordRepository, ProjectRepository projectRepository) {
        this.recordRepository = recordRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/api/v1/record/{id}")
    public Record find(
            @PathVariable(value = "id") Integer id
    ) {
        return recordRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/record")
    public List<Record> all() {
        return recordRepository.findAll();
    }

    @PutMapping("/api/v1/record/{id}")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") Integer id,
            @RequestBody Record record
    ) {
        recordRepository.update(id, record);

        var projectOptional = projectRepository.findById(record.getProjectId());

        if (projectOptional.isPresent()) {
            var project = projectOptional.get();

            project.setStatus(Project.Status.WAITING_SIGNATURES);

            projectRepository.update(project.getId(), project);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

}

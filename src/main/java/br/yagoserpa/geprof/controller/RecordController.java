package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.Record;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.ProjectHasUserRepository;
import br.yagoserpa.geprof.repository.ProjectRepository;
import br.yagoserpa.geprof.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final RecordRepository recordRepository;
    private final ProjectRepository projectRepository;
    private final ProjectHasUserRepository projectHasUserRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public RecordController(RecordRepository recordRepository, ProjectRepository projectRepository,
                            ProjectHasUserRepository projectHasUserRepository) {
        this.recordRepository = recordRepository;
        this.projectRepository = projectRepository;
        this.projectHasUserRepository = projectHasUserRepository;
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

            var projectParticipants = projectHasUserRepository.findByProjectId(project.getId());

            for (User student : projectParticipants) {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(student.getEmail());
                mail.setSubject("[GeProFi] Sua assinatura é requisitada para o encerramento de um projeto");
                mail.setText("Clique no link para ser redirecionado a ata:\nhttp://geprofi-front.herokuapp.com/record/" + project.getId());

                javaMailSender.send(mail);
            }

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

}

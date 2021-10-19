package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Auth;
import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.ProjectHasUserRepository;
import br.yagoserpa.geprof.repository.ProjectRepository;
import br.yagoserpa.geprof.repository.RecordHasUserRepository;
import br.yagoserpa.geprof.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectHasUserRepository projectHasUserRepository;
    private final RecordHasUserRepository recordHasUserRepository;
    private final RecordRepository recordRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public ProjectController(ProjectRepository projectRepository, ProjectHasUserRepository projectHasUserRepository,
                             RecordHasUserRepository recordHasUserRepository, RecordRepository recordRepository) {
        this.projectRepository = projectRepository;
        this.projectHasUserRepository = projectHasUserRepository;
        this.recordHasUserRepository = recordHasUserRepository;
        this.recordRepository = recordRepository;
    }

    @GetMapping("/api/v1/project/{id}")
    public Project find(
            @PathVariable(value = "id") Integer id
    ) {
        var project = projectRepository.findById(id).orElse(null);

        if (project != null) {
            var users = projectHasUserRepository.findByProjectId(id);
            project.setUserList(users);
        }

        return project;
    }

    @GetMapping("/api/v1/public/project/{id}")
    public Project publicFind(
            @PathVariable(value = "id") Integer id
    ) {
        var project = projectRepository.findById(id).orElse(null);

        if (project != null) {
            var users = projectHasUserRepository.findByProjectId(id);
            project.setUserList(users);
        }

        return project;
    }

    @GetMapping("/api/v1/project")
    public List<Project> all() {
        return projectRepository.findAll();
    }

    @GetMapping("/api/v1/public/project")
    public List<Project> publicAll() {
        return projectRepository.findAllEnded();
    }

    @PostMapping("/api/v1/project/")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Integer> insert(
            ServletRequest request,
            @RequestBody Project project
    ) {
        project.setStatus(Project.Status.STARTED);

        var projectOptional = projectRepository.insert(project);

        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Auth auth = (Auth) request.getAttribute("auth");

        var insertedProject = projectOptional.get();

        var projectHasUser = new ProjectHasUser();
        projectHasUser.setId(insertedProject.getId());
        projectHasUser.setUserId(auth.getId());
        projectHasUser.setCommittee(false);
        projectHasUser.setCoop(false);

        projectHasUserRepository.insert(projectHasUser);

        return new ResponseEntity<>(insertedProject.getId(), HttpStatus.OK);
    }

    @PutMapping("/api/v1/project/{id}")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") Integer id,
            @RequestBody Project project
    ) {
        var projectOptional = projectRepository.findById(id);

        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var savedProject = projectOptional.get();

        if (savedProject.getFile() == null || !savedProject.getFile().equals(project.getFile())) {
            project.setFileStatus(Project.FileStatus.IN_REVIEW);

            var advisorOptional = projectHasUserRepository.findAdvisorByProjectId(id);

            if (advisorOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            var advisor = advisorOptional.get();

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(advisor.getEmail());
            mail.setSubject("[GeProFi] Você tem um documento a ser revisado no projeto " + savedProject.getTitle());
            mail.setText("Confira no link abaixo:\n" + project.getFile());

            javaMailSender.send(mail);
        }

        projectRepository.update(id, project);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/project/{id}/review")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> sendToReview(
            @PathVariable(value = "id") Integer id
    ) {
        var projectOptional = projectRepository.findById(id);

        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var project = projectOptional.get();

        project.setFileStatus(Project.FileStatus.IN_REVIEW);

        projectRepository.update(id, project);

        var advisorOptional = projectHasUserRepository.findAdvisorByProjectId(id);

        if (advisorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var advisor = advisorOptional.get();

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(advisor.getEmail());
        mail.setSubject("[GeProFi] Alterações feitas no documento do projeto " + project.getTitle());
        mail.setText("Confira no link abaixo:\n" + project.getFile());

        javaMailSender.send(mail);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/project/{id}/reviewed")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> reviewed(
            @PathVariable(value = "id") Integer id
    ) {
        var projectOptional = projectRepository.findById(id);

        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var project = projectOptional.get();

        project.setFileStatus(Project.FileStatus.REVIEWED);

        var studentList = projectHasUserRepository.findStudentsByProjectId(id);

        for (User student : studentList) {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(student.getEmail());
            mail.setSubject("[GeProFi] O orientador revisou seu texto");
            mail.setText("Verifique seu arquivo.");

            javaMailSender.send(mail);
        }

        projectRepository.update(id, project);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/project/{id}")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") Integer id
    ) {
        recordHasUserRepository.deleteByProject(id);
        recordRepository.deleteByProject(id);
        projectHasUserRepository.deleteByProject(id);
        projectRepository.delete(id);

        return ResponseEntity.ok().build();
    }
}

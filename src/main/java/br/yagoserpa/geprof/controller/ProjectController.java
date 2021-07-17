package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Auth;
import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.repository.ProjectHasUserRepository;
import br.yagoserpa.geprof.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectHasUserRepository projectHasUserRepository;

    public ProjectController(ProjectRepository projectRepository, ProjectHasUserRepository projectHasUserRepository) {
        this.projectRepository = projectRepository;
        this.projectHasUserRepository = projectHasUserRepository;
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
        return projectRepository.findAll();
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
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") Integer id,
            @RequestBody Project project
    ) {
        projectRepository.update(id, project);

        return ResponseEntity.ok().build();
    }

}

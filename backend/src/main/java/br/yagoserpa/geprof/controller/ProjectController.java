package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/api/v1/project/{id}")
    public Project find(
            @PathVariable(value = "id") Integer id
    ) {
        return projectRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/project")
    public List<Project> all() {
        return projectRepository.findAll();
    }

    @PostMapping("/api/v1/project/")
    public void insert(
            @RequestBody Project project
    ) {
        projectRepository.insert(project);
    }

    @PutMapping("/api/v1/project/{id}")
    public void update(
            @PathVariable(value = "id") Integer id,
            @RequestBody Project project
    ) {
        projectRepository.update(id, project);
    }

}

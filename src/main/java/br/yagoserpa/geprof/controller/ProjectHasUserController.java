package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Auth;
import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.model.User;
import br.yagoserpa.geprof.repository.ProjectHasUserRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
public class ProjectHasUserController {

    private final ProjectHasUserRepository projectHasUserRepository;

    public ProjectHasUserController(ProjectHasUserRepository projectHasUserRepository) {
        this.projectHasUserRepository = projectHasUserRepository;
    }

    @GetMapping("/api/v1/project/{id}/users")
    public List<User> findByField(
            @PathVariable(value = "id") Integer id
    ) {
        return projectHasUserRepository.findByProjectId(id);
    }

    @GetMapping("/api/v1/user/{id}/projects")
    public List<Project> findByUserId(
            @PathVariable(value = "id") Long id
    ) {
        return projectHasUserRepository.findByUserId(id);
    }

    @GetMapping("/api/v1/user/projects")
    public List<Project> findByAuthUserId(
            ServletRequest request
    ) {
        Auth auth = (Auth) request.getAttribute("auth");
        return projectHasUserRepository.findByUserId(auth.getId());
    }

    @PostMapping("/api/v1/project/{id}/user/{userId}")
    public void insert(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "userId") Integer userId,
            @RequestBody ProjectHasUser projectHasUser
    ) {
        projectHasUserRepository.insert(id, userId, projectHasUser);
    }

    @DeleteMapping("/api/v1/project/{id}/user/{userId}")
    public void delete(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "userId") Integer userId
    ) {
        projectHasUserRepository.delete(id, userId);
    }

}

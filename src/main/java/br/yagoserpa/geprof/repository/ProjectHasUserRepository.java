package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectHasUserRepository {

    List<User> findByProjectId(Integer id);

    Optional<User> findAdvisorByProjectId(Integer id);

    List<User> findStudentsByProjectId(Integer id);

    List<Project> findByUserId(Long id);

    void insert(ProjectHasUser projectHasUser);

    void deleteByProjectAndUser(Integer id, Integer userId);

    void deleteByProject(Integer projectId);
}

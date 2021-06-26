package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.model.User;

import java.util.List;

public interface ProjectHasUserRepository {

    List<User> findByProjectId(Integer id);

    List<Project> findByUserId(Long id);

    void insert(Integer id, Integer userId, ProjectHasUser projectHasUser);

    void delete(Integer id, Integer userId);

}

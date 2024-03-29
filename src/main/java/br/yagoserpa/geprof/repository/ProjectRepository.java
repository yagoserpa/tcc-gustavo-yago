package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    List<Project> findAllEnded();

    Optional<Project> findById(Integer id);

    Optional<Project> insert(Project record);

    void update(Integer id, Project record);

    void delete(Integer id);
}

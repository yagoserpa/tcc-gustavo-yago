package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    Optional<Project> findById(String id);

    void insert(Project record);

    void update(Integer id, Project record);

}

package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {

    List<File> findAll();

    Optional<File> findById(Integer id);

    void insert(File file);

    void update(Integer id, File file);

    void deleteByProject(Integer projectId);
}

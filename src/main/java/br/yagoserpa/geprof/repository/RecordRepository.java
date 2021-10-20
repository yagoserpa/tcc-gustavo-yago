package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Record;

import java.util.List;
import java.util.Optional;

public interface RecordRepository {

    List<Record> findAll();

    Optional<Record> findById(Integer id);

    Optional<Record> findByProjectId(Integer projectId);

    void insert(Record record);

    void update(Integer id, Record record);

    void deleteByProject(Integer projectId);
}

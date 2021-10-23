package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.User;

import java.util.List;

public interface RecordHasUserRepository {

    List<User> findById(Integer id);

    void insert(Integer projectId, Integer recordId, Integer userId, String signature);

    void deleteByProject(Integer projectId);
}

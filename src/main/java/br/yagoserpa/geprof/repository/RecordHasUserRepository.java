package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.RecordHasUser;
import br.yagoserpa.geprof.model.User;

import java.util.List;

public interface RecordHasUserRepository {

    List<User> findById(Integer id);

    void insert(Integer id, RecordHasUser recordHasUser);

    void deleteByProject(Integer projectId);
}

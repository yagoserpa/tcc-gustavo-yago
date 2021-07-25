package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.FieldOfInterest;
import br.yagoserpa.geprof.model.User;

import java.util.List;

public interface FieldOfInterestHasUserRepository {

    List<User> findByFieldId(Integer id);

    List<FieldOfInterest> findByUserId(Long id);

    void insert(Integer id, Integer userId);

    void delete(Integer id, Integer userId);

}

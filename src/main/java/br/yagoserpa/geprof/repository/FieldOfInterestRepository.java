package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.FieldOfInterest;

import java.util.List;
import java.util.Optional;

public interface FieldOfInterestRepository {

    List<FieldOfInterest> findAll();

    Optional<FieldOfInterest> findById(Integer id);

    void insert(FieldOfInterest fieldOfInterest);

    void update(Integer id, FieldOfInterest fieldOfInterest);

    void delete(String id);

}

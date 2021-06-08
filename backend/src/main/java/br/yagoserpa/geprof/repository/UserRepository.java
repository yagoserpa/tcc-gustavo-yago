package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    void insert(User user);

    void update(Integer id, User user);

    void delete(Integer id);

}

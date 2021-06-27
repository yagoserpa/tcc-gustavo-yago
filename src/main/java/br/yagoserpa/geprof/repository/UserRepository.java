package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String email, String password);

    Optional<User> insert(User user);

    void update(Integer id, User user);

    void delete(Integer id);

}

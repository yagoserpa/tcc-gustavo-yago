package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    List<User> findAllActive();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean isValidUserPassword(Long userId, String password);

    Optional<User> findByLogin(String email, String password);

    Optional<User> insert(User user);

    void update(Long id, User user);
}

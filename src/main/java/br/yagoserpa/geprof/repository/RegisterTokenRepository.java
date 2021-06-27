package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.RegisterToken;

import java.util.Optional;

public interface RegisterTokenRepository {

    Optional<RegisterToken> findByUserId(Long userId);

    Optional<RegisterToken> findByToken(String token);

    void insert(RegisterToken registerToken);

    void delete(Integer id);
}

package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.RegisterToken;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RegisterTokenRepositoryImpl implements RegisterTokenRepository {

    private final JdbcTemplate template;

    public RegisterTokenRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public Optional<RegisterToken> findByUserId(Long userId) {
        List<RegisterToken> users = template.query("SELECT * FROM register_token WHERE user_id = ? LIMIT 1",
                RegisterToken::new,
                userId);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<RegisterToken> findByToken(String token) {
        List<RegisterToken> users = template.query("SELECT * FROM register_token WHERE token = ? LIMIT 1",
                RegisterToken::new,
                token);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void insert(RegisterToken registerToken) {
        template.update("INSERT INTO register_token (user_id, token) VALUES (?, ?)",
                registerToken.getUserId(),
                registerToken.getToken()
        );
    }

    @Override
    public void delete(Integer id) {
        template.update("DELETE FROM register_token WHERE register_token_id = ?",
                id);
    }
}

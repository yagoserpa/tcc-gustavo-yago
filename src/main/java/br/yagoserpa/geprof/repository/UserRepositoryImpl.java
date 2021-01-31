package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate template;

    public UserRepositoryImpl(
        JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<User> findAll() {
        return template.query("SELECT user_id, name FROM users", User::new);
    }

    @Override
    public Optional<User> findById(String id) {
        List<User> users = template.query("SELECT user_id, name FROM users WHERE id = ? LIMIT 1", User::new, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void update(String id, User user) {
        template.update("UPDATE users SET name = ? WHERE id = ?",
            user.getName(),
            id
        );
    }

    @Override
    public void delete(String id) {
        template.update("UPDATE users SET status = 'INACTIVE' WHERE id = ?", id);
        template.update("DELETE users WHERE id = ?", id);
    }
}

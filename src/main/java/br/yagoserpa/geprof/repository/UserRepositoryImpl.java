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
        return template.query("SELECT user_id, name, password, email, dre, siape, register_date, gender, status, title, position, room, lattes, user_profile, course, origin, user_type FROM users", User::new);
    }

    @Override
    public Optional<User> findById(Integer id) {
        List<User> users = template.query("SELECT user_id, name, password, email, dre, siape, register_date, gender, status, title, position, room, lattes, user_profile, course, origin, user_type FROM users WHERE user_id = ? LIMIT 1", User::new, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }
    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = template.query("SELECT user_id, name, password, email, dre, siape, register_date, gender, status, title, position, room, lattes, user_profile, course, origin, user_type FROM users WHERE email = ? LIMIT 1", User::new, email);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void insert(User user) {
        template.query("INSERT INTO users (name, email, dre, siape, gender, status, title, position, room, lattes, user_profile, course, origin, user_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                User::new,
                user.getName(),
                user.getEmail(),
                user.getDre(),
                user.getSiape(),
                user.getGender(),
                user.getStatus().ordinal(),
                user.getTitle(),
                user.getPosition(),
                user.getRoom(),
                user.getLattes(),
                user.getUserProfile(),
                user.getCourse(),
                user.getOrigin(),
                user.getUserType().ordinal()
        );
    }

    @Override
    public void update(Integer id, User user) {
        template.update("UPDATE users SET name = ?, email = ?, dre = ?, siape = ?, gender = ?, status = ?, title = ?, position = ?, room = ?, lattes = ?, user_profile = ?, course = ?, origin = ?, user_type = ? WHERE user_id = ?",
                user.getName(),
                user.getEmail(),
                user.getDre(),
                user.getSiape(),
                user.getGender(),
                user.getStatus().ordinal(),
                user.getTitle(),
                user.getPosition(),
                user.getRoom(),
                user.getLattes(),
                user.getUserProfile(),
                user.getCourse(),
                user.getOrigin(),
                user.getUserType().ordinal(),
                id
        );
    }

    @Override
    public void delete(Integer id) {
        template.update("UPDATE users SET status = ? WHERE user_id = ?",
                User.Status.DELETED,
                id);
    }
}

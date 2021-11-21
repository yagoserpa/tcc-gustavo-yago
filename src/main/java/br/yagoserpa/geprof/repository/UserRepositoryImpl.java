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
        return template.query("SELECT * FROM users WHERE user_id NOT IN (SELECT user_id FROM register_token) ORDER BY name ASC", User::new);
    }

    @Override
    public List<User> findAllActive() {
        return template.query("SELECT * FROM users WHERE status = 1 AND user_id NOT IN (SELECT user_id FROM register_token) ORDER BY name ASC", User::new);
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = template.query("SELECT * FROM users WHERE user_id = ? LIMIT 1", User::new, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = template.query("SELECT * FROM users WHERE email = ? LIMIT 1", User::new, email);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public boolean isValidUserPassword(Long userId, String password) {
        List<User> users = template.query("SELECT * FROM users WHERE user_id = ? AND password = crypt(?, password) LIMIT 1",
                User::new,
                userId,
                password);
        return !users.isEmpty();
    }

    @Override
    public Optional<User> findByLogin(String email, String password) {
        List<User> users = template.query("SELECT * FROM users WHERE email = ? AND password = crypt(?, password) LIMIT 1",
                User::new,
                email,
                password);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> insert(User user) {
        List<User> users = template.query("INSERT INTO users (name, email, dre, siape, gender, status, title, position, room, lattes, user_profile, course, origin, user_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *",
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
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void update(Long id, User user) {
        template.update("UPDATE users SET name = ?, email = ?, password = crypt(?, gen_salt('bf')), dre = ?, siape = ?, gender = ?, status = ?, title = ?, position = ?, room = ?, lattes = ?, user_profile = ?, course = ?, origin = ?, user_type = ? WHERE user_id = ?",
                user.getName(),
                user.getEmail(),
                user.getPassword(),
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
    public void activateUser(Long id) {
        template.update("UPDATE users SET status = ? WHERE user_id = ?",
                User.Status.ACTIVE.ordinal(),
                id
        );
    }

    @Override
    public void deactivateUser(Long id) {
        template.update("UPDATE users SET status = ? WHERE user_id = ?",
                User.Status.INACTIVE.ordinal(),
                id
        );
    }
}

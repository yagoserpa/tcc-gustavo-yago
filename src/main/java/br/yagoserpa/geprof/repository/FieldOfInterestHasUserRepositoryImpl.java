package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.FieldOfInterest;
import br.yagoserpa.geprof.model.FieldOfInterestHasUser;
import br.yagoserpa.geprof.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FieldOfInterestHasUserRepositoryImpl implements FieldOfInterestHasUserRepository {

    private final JdbcTemplate template;

    public FieldOfInterestHasUserRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<User> findByFieldId(Integer id) {
        return template.query("SELECT u.* FROM fieldofinterest_has_user fhu, users u WHERE fhu.field_id = ? AND fhu.user_id = u.user_id ORDER BY u.name ASC", User::new, id);
    }

    @Override
    public List<FieldOfInterest> findByUserId(Long id) {
        return template.query("SELECT f.* FROM fieldofinterest_has_user fhu, fieldofinterest f WHERE fhu.user_id = ? AND fhu.field_id = f.field_id", FieldOfInterest::new, id);
    }

    @Override
    public void insert(Integer id, Integer userId) {
        template.update("INSERT INTO fieldofinterest_has_user (field_id, user_id) VALUES (?, ?)",
                id,
                userId
        );
    }

    @Override
    public void delete(Integer id, Integer userId) {
        template.update("DELETE FROM fieldofinterest_has_user WHERE field_id = ? AND user_id = ?",
                id,
                userId);
    }
}

package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.RecordHasUser;
import br.yagoserpa.geprof.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecordHasUserRepositoryImpl implements RecordHasUserRepository {

    private final JdbcTemplate template;

    public RecordHasUserRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<User> findById(Integer id) {
        return template.query("SELECT u.*, phu.*, rhu.* FROM record_has_user rhu, project_has_user phu, users u WHERE rhu.record_id = ? AND rhu.project_id = phu.project_id AND rhu.user_id = phu.user_id AND phu.user_id = u.user_id", User::new, id);
    }

    @Override
    public void insert(Integer id, RecordHasUser recordHasUser) {
        template.query("INSERT INTO project_has_user (record_id, project_id, user_id, signature) VALUES (?, ?, ?, ?)",
                RecordHasUser::new,
                id,
                recordHasUser.getProjectId(),
                recordHasUser.getUserId(),
                recordHasUser.getSignature()
        );
    }
}

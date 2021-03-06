package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectHasUserRepositoryImpl implements ProjectHasUserRepository {

    private final JdbcTemplate template;

    public ProjectHasUserRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<User> findByProjectId(Integer id) {
        return template.query("SELECT u.user_id, u.name, phu.committee, phu.coop FROM project_has_user phu, users u WHERE phu.project_id = ? AND phu.user_id = u.user_id", User::new, id);
    }

    @Override
    public List<Project> findByUserId(Integer id) {
        return template.query("SELECT p.project_id, p.name FROM project_has_user phu, project p WHERE phu.user_id = ? AND phu.project_id = p.project_id", Project::new, id);
    }

    @Override
    public void insert(Integer id, Integer userId, ProjectHasUser projectHasUser) {
        template.query("INSERT INTO project_has_user (field_id, user_id, committee, coop) VALUES (?, ?, ?, ?)",
                ProjectHasUser::new,
                id,
                userId,
                projectHasUser.getCommittee(),
                projectHasUser.getCoop()
        );
    }

    @Override
    public void delete(Integer id, Integer userId) {
        template.update("DELETE FROM project_has_user WHERE project_id = ? AND user_id = ?",
                id,
                userId);
    }
}

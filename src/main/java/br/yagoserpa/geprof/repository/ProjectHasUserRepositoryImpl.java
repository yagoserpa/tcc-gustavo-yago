package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;
import br.yagoserpa.geprof.model.ProjectHasUser;
import br.yagoserpa.geprof.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        return template.query("SELECT u.* FROM project_has_user phu, users u WHERE phu.project_id = ? AND phu.user_id = u.user_id ORDER BY u.name ASC", User::new, id);
    }

    @Override
    public Optional<User> findAdvisorByProjectId(Integer id) {
        List<User> users = template.query("SELECT u.* FROM project_has_user phu, users u WHERE phu.project_id = ? AND phu.committee = FALSE AND phu.coop = FALSE AND u.user_type = 1 AND phu.user_id = u.user_id", User::new, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public List<User> findStudentsByProjectId(Integer id) {
        return template.query("SELECT u.* FROM project_has_user phu, users u WHERE phu.project_id = ? AND phu.committee = FALSE AND phu.coop = FALSE AND u.user_type = 2 AND phu.user_id = u.user_id ORDER BY u.name ASC", User::new, id);
    }

    @Override
    public List<Project> findByUserId(Long id) {
        return template.query("SELECT p.* FROM project_has_user phu, project p WHERE phu.user_id = ? AND phu.project_id = p.project_id", Project::new, id);
    }

    @Override
    public void insert(ProjectHasUser projectHasUser) {
        template.update("INSERT INTO project_has_user (project_id, user_id, committee, coop) VALUES (?, ?, ?, ?)",
                projectHasUser.getId(),
                projectHasUser.getUserId(),
                projectHasUser.getCommittee(),
                projectHasUser.getCoop()
        );
    }

    @Override
    public void deleteByProjectAndUser(Integer id, Integer userId) {
        template.update("DELETE FROM project_has_user WHERE project_id = ? AND user_id = ?",
                id,
                userId);
    }

    @Override
    public void deleteByProject(Integer projectId) {
        template.update("DELETE FROM project_has_user where project_id = ?", projectId);
    }
}

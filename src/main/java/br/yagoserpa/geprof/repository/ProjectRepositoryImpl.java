package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {

    private final JdbcTemplate template;

    public ProjectRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<Project> findAll() {
        return template.query("SELECT * FROM project", Project::new);
    }

    @Override
    public Optional<Project> findById(Integer id) {
        List<Project> projects = template.query("SELECT * FROM project WHERE project_id = ? LIMIT 1", Project::new, id);
        if (projects.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(projects.get(0));
    }

    @Override
    public void insert(Project project) {
        template.query("INSERT INTO project (name, subject, title, descrption, status, register_date, keywords) VALUES (?, ?, ?, ?, ?, ?, ?)",
                Project::new,
                project.getName(),
                project.getSubject(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus().ordinal(),
                project.getRegisterDate(),
                project.getKeywords()
        );
    }

    @Override
    public void update(Integer id, Project project) {
        template.update("UPDATE project SET name = ?, subject = ?, title = ?, description = ?, register_date = ?, status = ?, keywords = ? WHERE project_id = ?",
                project.getName(),
                project.getSubject(),
                project.getTitle(),
                project.getDescription(),
                project.getRegisterDate(),
                project.getStatus().ordinal(),
                project.getKeywords(),
                id
        );
    }
}

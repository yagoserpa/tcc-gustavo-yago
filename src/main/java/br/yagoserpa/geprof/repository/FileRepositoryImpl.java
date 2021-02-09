package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.File;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryImpl implements FileRepository {

    private final JdbcTemplate template;

    public FileRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<File> findAll() {
        return template.query("SELECT * FROM file", File::new);
    }

    @Override
    public Optional<File> findById(Integer id) {
        List<File> users = template.query("SELECT * FROM file WHERE file_id = ? LIMIT 1", File::new, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void insert(File file) {
        template.query("INSERT INTO file (project_id, name, version, file, register_date, content_type, content, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                File::new,
                file.getProjectId(),
                file.getName(),
                file.getVersion(),
                file.getFile(),
                file.getRegisterDate(),
                file.getContentType(),
                file.getContent(),
                file.getComments()
        );
    }

    @Override
    public void update(Integer id, File file) {
        template.update("UPDATE file SET project_id = ?, name = ?, version = ?, file = ?, register_date = ?, content_type = ?, content = ?, comments = ? WHERE file_id = ?",
                file.getProjectId(),
                file.getName(),
                file.getVersion(),
                file.getFile(),
                file.getRegisterDate(),
                file.getContentType(),
                file.getContent(),
                file.getComments(),
                id
        );
    }

}

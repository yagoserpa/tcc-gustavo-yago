package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.FieldOfInterest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FieldOfInterestRepositoryImpl implements FieldOfInterestRepository {

    private final JdbcTemplate template;

    public FieldOfInterestRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<FieldOfInterest> findAll() {
        return template.query("SELECT * FROM fieldofinterest", FieldOfInterest::new);
    }

    @Override
    public Optional<FieldOfInterest> findById(Integer id) {
        List<FieldOfInterest> fieldOfInterests = template.query("SELECT * FROM fieldofinterest WHERE field_id = ? LIMIT 1", FieldOfInterest::new, id);
        if (fieldOfInterests.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fieldOfInterests.get(0));
    }

    @Override
    public void insert(FieldOfInterest fieldOfInterest) {
        template.query("INSERT INTO fieldofinterest (name, description) VALUES (?, ?)",
                FieldOfInterest::new,
                fieldOfInterest.getName(),
                fieldOfInterest.getDescription()
        );
    }

    @Override
    public void update(Integer id, FieldOfInterest fieldOfInterest) {
        template.update("UPDATE fieldofinterest SET name = ?, description = ? WHERE field_id = ?",
                fieldOfInterest.getName(),
                fieldOfInterest.getDescription(),
                id
        );
    }

    @Override
    public void delete(String id) {
        template.update("DELETE FROM fieldofinterest WHERE field_id = ?",
                id);
    }
}

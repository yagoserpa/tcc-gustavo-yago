package br.yagoserpa.geprof.repository;

import br.yagoserpa.geprof.model.Record;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecordRepositoryImpl implements RecordRepository {

    private final JdbcTemplate template;

    public RecordRepositoryImpl(
            JdbcTemplate template
    ) {
        this.template = template;
    }

    @Override
    public List<Record> findAll() {
        return template.query("SELECT * FROM record", Record::new);
    }

    @Override
    public Optional<Record> findById(Integer id) {
        List<Record> records = template.query("SELECT * FROM record WHERE record_id = ? LIMIT 1", Record::new, id);
        if (records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(records.get(0));
    }

    @Override
    public Optional<Record> findByProjectId(Integer projectId) {
        List<Record> records = template.query("SELECT * FROM record WHERE project_id = ? LIMIT 1", Record::new, projectId);
        if (records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(records.get(0));
    }

    @Override
    public void insert(Record record) {
        template.update("INSERT INTO record (project_id, thesis_date, begin_time, location) VALUES (?, ?, ?, ?)",
                record.getProjectId(),
                record.getThesisDate(),
                record.getBeginTime(),
                record.getLocation()
        );
    }

    @Override
    public void update(Integer id, Record record) {
        template.update("UPDATE record SET thesis_id = ?, thesis_date = ?, begin_time = ?, end_time = ?, location = ?, evaluation = ?, deadline = ?, grade = ?, grade_description = ? WHERE record_id = ?",
                record.getThesisId(),
                record.getThesisDate(),
                record.getBeginTime(),
                record.getEndTime(),
                record.getLocation(),
                record.getEvaluation() == null ? null : record.getEvaluation().ordinal(),
                record.getDeadline(),
                record.getGrade(),
                record.getGradeDescription(),
                record.getId()
        );
    }

    @Override
    public void deleteByProject(Integer projectId) {
        template.update("DELETE FROM record where project_id = ?", projectId);
    }
}

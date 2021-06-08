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
    public void insert(Record record) {
        template.query("INSERT INTO record (project_id, thesis_id, thesis_date, begin_time, end_time, location, evaluation, deadline, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Record::new,
                record.getProjectId(),
                record.getThesisId(),
                record.getThesisDate(),
                record.getBeginTime(),
                record.getEndTime(),
                record.getLocation(),
                record.getEvaluation().ordinal(),
                record.getDeadline(),
                record.getGrade()
        );
    }
}

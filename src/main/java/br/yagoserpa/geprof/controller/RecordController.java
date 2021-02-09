package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.Record;
import br.yagoserpa.geprof.repository.RecordRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final RecordRepository recordRepository;

    public RecordController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/api/v1/record/{id}")
    public Record find(
            @PathVariable(value = "id") Integer id
    ) {
        return recordRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/record")
    public List<Record> all() {
        return recordRepository.findAll();
    }

    @PostMapping("/api/v1/record/")
    public void insert(
            @RequestBody Record record
    ) {
        recordRepository.insert(record);
    }

}

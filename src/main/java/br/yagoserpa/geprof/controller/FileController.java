package br.yagoserpa.geprof.controller;

import br.yagoserpa.geprof.model.File;
import br.yagoserpa.geprof.repository.FileRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    private final FileRepository fileRepository;

    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/api/v1/file/{id}")
    public File find(
            @PathVariable(value = "id") Integer id
    ) {
        return fileRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/v1/file")
    public List<File> all() {
        return fileRepository.findAll();
    }

    @PostMapping("/api/v1/file/")
    public void insert(
            @RequestBody File file
    ) {
        fileRepository.insert(file);
    }

    @PutMapping("/api/v1/file/{id}")
    public void update(
            @PathVariable(value = "id") Integer id,
            @RequestBody File file
    ) {
        fileRepository.update(id, file);
    }

}

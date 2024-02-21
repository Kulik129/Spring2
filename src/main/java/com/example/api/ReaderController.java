package com.example.api;

import com.example.model.Reader;
import com.example.serice.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            readerService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(readerService.all());
    }

    @PostMapping
    public ResponseEntity<List<Reader>> add(@RequestBody Reader reader) {
        try {
            readerService.addReader(reader);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.all());
    }

    @GetMapping
    public ResponseEntity<List<Reader>> all() {
        List<Reader> readers = null;
        try {
            readers = readerService.all();
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(readers);
    }
}

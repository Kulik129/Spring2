package com.example.api;

import com.example.model.Issue;
import com.example.serice.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssuerControllers {
    private final IssueService service;

    @PostMapping
    public ResponseEntity <Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerID = {}, bookID = {}", request.getReaderId(), request.getBookId());
        final Issue issue;
        try {
            issue = service.issue(request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> issueId(@PathVariable long id) {
        try {
            service.getById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

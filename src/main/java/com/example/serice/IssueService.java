package com.example.serice;

import com.example.api.IssueRequest;
import com.example.model.Issue;
import com.example.repository.BookRepository;
import com.example.repository.IssueRepository;
import com.example.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public Issue issue(IssueRequest request) {


        if (bookRepository.findById(request.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с ID \"" + request.getBookId() + "\"");
        }
        if (readerRepository.findById(request.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с ID \"" + request.getReaderId() + "\"");
        }

        Issue issue = new Issue(request.getBookId(), request.getReaderId());
        issueRepository.save(issue);
        return issue;
    }

    public Optional<Issue> getById(long id) {
        return issueRepository.findById(id);
    }

    public List<Issue> getAllIssue() {
        return issueRepository.findAll();
    }
}

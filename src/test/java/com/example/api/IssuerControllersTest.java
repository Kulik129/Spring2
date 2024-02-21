package com.example.api;

import com.example.JUnitSpringBootBase;
import com.example.model.Issue;
import com.example.repository.IssueRepository;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IssuerControllersTest extends JUnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitIssueResponse {
        private long bookId;
        private long readerId;
    }

    @Test
    void addAddIssue() {
        Issue issue = new Issue(1L, 1L);

        webTestClient.post()
                .uri("/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(issue)
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(JUnitIssueResponse.class)
                .consumeWith(response -> {
                    List<JUnitIssueResponse> issueList = response.getResponseBody();
                    assertNotNull(issueList);
                    assertFalse(issueList.isEmpty());

                    JUnitIssueResponse addedIssue = issueList.get(0);
                    assertEquals(1L, addedIssue.getBookId());
                    assertEquals(1L, addedIssue.getReaderId());

                    Optional<Issue> saveIssue = issueRepository.findById(addedIssue.getBookId());
                    assertTrue(saveIssue.isPresent());
                    assertEquals(1L, saveIssue.get().getBookId());
                });
    }

    @Test
    void testFindByIdNotFound() {
        // 1 - й вариант поиска не существующего id
        /*

        Optional<Long> max = issueRepository.findAll().stream()
                .map(Issue::getBookId)
                .max(Comparator.naturalOrder());
        long nonExisting = max.orElse(0L);
        nonExisting++;
         */
        //2 -й вариант более быстрый поиск и работа теста быстрее
        Long nonExisting = jdbcTemplate.queryForObject("select max(id) from Issue", Long.class);

        webTestClient.get()
                .uri("/issue/" + nonExisting + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindById() {
        Issue expected = issueRepository.save(new Issue(1L, 2L));
        JUnitIssueResponse responseBody = webTestClient.get()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(responseBody);
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
    }
}
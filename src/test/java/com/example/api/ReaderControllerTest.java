package com.example.api;

import com.example.JUnitSpringBootBase;
import com.example.model.Reader;
import com.example.repository.ReaderRepository;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReaderControllerTest extends JUnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitReaderResponse {
        private long id;
        private String name;
    }


    @Test
    void testAddReader() {
        Reader reader = new Reader("Name");

        webTestClient.post()
                .uri("/reader")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reader)
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(JUnitReaderResponse.class)
                .consumeWith(response -> {
                    List<JUnitReaderResponse> readerResponseList = response.getResponseBody();
                    assertNotNull(readerResponseList);
                    assertFalse(readerResponseList.isEmpty());

                    JUnitReaderResponse addedReader = readerResponseList.get(0);
                    assertEquals("Name", addedReader.getName());

                    Optional<Reader> savedReader = readerRepository.findById(addedReader.getId());
                    assertTrue(savedReader.isPresent());
                    assertEquals("Name", savedReader.get().getName());
                });
    }

    @Test
    void testGetAll() {
        readerRepository.saveAll(List.of(
                new Reader("Name"),
                new Reader("Nnn")
        ));

        List<Reader> expected = readerRepository.findAll();

        List<JUnitReaderResponse> responseBody = webTestClient.get()
                .uri("/reader")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitReaderResponse>>() {
                })
                .returnResult().getResponseBody();

        assertEquals(expected.size(), responseBody.size());
        for (JUnitReaderResponse readerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), readerResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), readerResponse.getName()));

            assertTrue(found);
        }
    }

    @Test
    void testDeleteReader() {
        Reader reader = readerRepository.save(new Reader("Name"));

        assertTrue(readerRepository.existsById(reader.getId()));

        webTestClient.delete()
                .uri("/reader/delete/" + reader.getId())
                .exchange()
                .expectStatus().isOk();

        assertFalse(readerRepository.existsById(reader.getId()));
    }
}
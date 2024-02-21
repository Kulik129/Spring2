package com.example.api;

import com.example.JUnitSpringBootBase;
import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class BookControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BookRepository bookRepository;

    @Data
    static class JUnitBookResponse {
        private long id;
        private String name;
    }

    @Test
    public void testAddBook() {
        // Создаем объект Book для передачи в запрос
        Book bookToAdd = new Book("Идиот");

        // Отправляем POST-запрос на эндпоинт
        webTestClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookToAdd)
                .exchange()
                .expectStatus().isCreated() // Ожидаем успешный ответ с кодом 201 CREATED
                .expectBodyList(JUnitBookResponse.class)
                .consumeWith(response -> {
                    // Проверяем, что в ответе содержится список книг
                    List<JUnitBookResponse> bookList = response.getResponseBody();
                    assertNotNull(bookList);
                    assertFalse(bookList.isEmpty());

                    // Проверяем, что добавленная книга соответствует ожидаемым данным
                    JUnitBookResponse addedBook = bookList.get(0);
                    assertEquals("Идиот", addedBook.getName());

                    // Проверяем, что книга была сохранена в репозитории
                    Optional<Book> savedBook = bookRepository.findById(addedBook.getId());
                    assertTrue(savedBook.isPresent());
                    assertEquals("Идиот", savedBook.get().getName());
                });
    }
    
    @Test
    void testGetAll() {
        // подготовить данные
        bookRepository.saveAll(List.of(
                new Book("Мастер и Маргарита"),
                new Book("Собачье сердце")
        ));

        List<Book> expected = bookRepository.findAll();

        List<JUnitBookResponse> responseBody = webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
                })
                .returnResult()
                . getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitBookResponse bookResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), bookResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), bookResponse.getName()));
            Assertions.assertTrue(found);
        }
    }
    @Test
    public void testDeleteBook() {
        // Создаем книгу для удаления и сохраняем ее в репозитории
        Book bookDel = bookRepository.save(new Book("fdfdfd"));
        // Проверяем, что книга существует в репозитории перед отправкой DELETE-запроса
        assertTrue(bookRepository.existsById(bookDel.getId()));

        // Отправляем DELETE-запрос на эндпоинт с id книги
        webTestClient.delete()
                .uri("/book/delete/" + bookDel.getId())
                .exchange()
                .expectStatus().isOk(); // Ожидаем успешный ответ с кодом 200 OK

        // Проверяем, что книга была удалена из репозитория
        assertFalse(bookRepository.existsById(bookDel.getId()));
    }
}
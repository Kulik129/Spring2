package com.example.serice;

import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;



    @Test
    void deleteById() {
        List<Book> books = bookRepository.saveAll(List.of(
                new Book("Name"),
                new Book("Books")
        ));

        List<Book> expected = bookRepository.findAll();

        assertFalse(expected.isEmpty());

        long id = books.get(1).getId();

        bookRepository.deleteById(id);

        List<Book> expected2 = bookRepository.findAll();

        assertTrue(expected2.size() == 1);
        assertEquals("Name", expected2.get(0).getName());
    }

    @Test
    void addBook() {
        Book book = new Book("Name");
        bookRepository.save(book);

        Optional<Book> savedBook = bookRepository.findById(book.getId());

        assertTrue(savedBook.isPresent());
        assertEquals("Name", savedBook.get().getName());
    }

    @Test
    void allBooks() {
        List<Book> books = bookRepository.saveAll(List.of(
                new Book("Name"),
                new Book("Books")
        ));

        List<Book> expected = bookRepository.findAll();

        assertFalse(expected.isEmpty());

        for (Book book : books) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getName(), book.getName()))
                    .anyMatch(it -> Objects.equals(it.getId(), book.getId()));
            assertTrue(found);
        }
    }

    @Test
    void getBook() {
        bookRepository.saveAll(List.of(
                new Book("Name"),
                new Book("Books")
        ));
        List<Book> expected = bookRepository.findAll();

        assertFalse(expected.isEmpty());

        Optional<Book> book = bookRepository.findById(1L);

        assertTrue(book.isPresent());
        assertEquals("Name", book.get().getName());
    }
}
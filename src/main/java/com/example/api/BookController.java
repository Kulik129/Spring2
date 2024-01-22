package com.example.api;

import com.example.model.Book;
import com.example.serice.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = null;
        try {
            books = bookService.allBooks();
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @PostMapping
    public ResponseEntity<List<Book>> addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.allBooks());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        log.info("Получен запрос на удаление книги с id: {}", id);
        try {
            bookService.deleteById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

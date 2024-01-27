package com.example.serice;

import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void deleteById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new NoSuchElementException("Не найдена книга с ID \"" + id + "\"");
        } else {
            bookRepository.deleteById(id);
        }
    }
    public List<Book> addBook(Book book) {
        bookRepository.save(book);
        return bookRepository.findAll();
    }

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBook(long id) {
        return bookRepository.findById(id);
    }
}

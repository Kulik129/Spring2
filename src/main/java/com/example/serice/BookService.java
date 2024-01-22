package com.example.serice;

import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void deleteById(long id) {
        Book book = bookRepository.getBookById(id);
        if (book == null) {
            throw new NoSuchElementException("Не найдена книга с ID \"" + id + "\"");
        } else {
            bookRepository.deleteBook(id);
        }
    }

    public List<Book> addBook(Book book) {
        bookRepository.addBook(book);
        return bookRepository.getBooks();
    }

    public List<Book> allBooks() {
        return bookRepository.getBooks();
    }
}

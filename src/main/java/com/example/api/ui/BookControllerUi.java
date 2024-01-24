package com.example.api.ui;

import com.example.serice.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class BookControllerUi {
    private final BookService bookService;
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.allBooks());
        return "books";
    }

}

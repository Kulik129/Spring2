package com.example.api.ui;

import com.example.serice.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class ReaderControllerUi {
    private final ReaderService readerService;
    @GetMapping("/reader")
    public String getAllReaders(Model model) {
        model.addAttribute("readers", readerService.all());
        return "readers";
    }
}

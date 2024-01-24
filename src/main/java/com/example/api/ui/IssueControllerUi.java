package com.example.api.ui;

import com.example.serice.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class IssueControllerUi {
    private final IssueService issueService;

    @GetMapping("/issue")
    public String getAllIssue(Model model) {
        model.addAttribute("issues", issueService.getAllIssue());
        return "issue";
    }
}

package com.us.itp.sandbox.webarch.controller;

import com.us.itp.sandbox.webarch.service.WordService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class MainController {

    @NonNull private final WordService wordService;

    public MainController(@NonNull WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/")
    @NonNull public String mainPage(@NonNull Model model) {
        model.addAttribute("words", wordService.listAllWords());
        return "main";
    }
}

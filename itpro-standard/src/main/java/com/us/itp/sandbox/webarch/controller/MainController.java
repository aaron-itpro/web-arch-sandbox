package com.us.itp.sandbox.webarch.controller;

import com.us.itp.sandbox.webarch.service.WordService;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public final class MainController {

    @NonNull private final WordService wordService;

    public MainController(@NonNull WordService wordService) {
        this.wordService = wordService;
    }

    @ModelAttribute(MODEL_ATTR_WORDS)
    public List<String> listAllWords() {
        return wordService.listAllWords();
    }

    @GetMapping("/")
    @NonNull public String mainPage() {
        return "main";
    }

    @PostMapping("/word/{word}")
    public void addWord(@NonNull @PathVariable("word") String word) {
        wordService.addWord(word);
    }

    public static final String MODEL_ATTR_WORDS = "words";
}

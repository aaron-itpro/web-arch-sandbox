package com.us.itp.sandbox.webarch.controller;

import com.us.itp.sandbox.webarch.service.WordService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public final class MainController {

    @NonNull private final WordService wordService;

    public MainController(@NonNull final WordService wordService) {
        this.wordService = wordService;
    }

    @ModelAttribute(MODEL_ATTR_WORDS)
    public List<String> listAllWords() {
        return wordService.listAllWords();
    }

    @GetMapping(URL_MAIN)
    @NonNull public String mainPage() {
        return "main";
    }

    @GetMapping(URL_WORD_LIST)
    @NonNull public String wordList() {
        return "main :: wordList";
    }

    @PostMapping(URL_ADD_WORD)
    @ResponseStatus(HttpStatus.OK)
    public void addWord(@NonNull @PathVariable(URL_PARAM_WORD) final String word) {
        wordService.addWord(word);
    }

    public static final String MODEL_ATTR_WORDS = "words";
    public static final String URL_PARAM_WORD = "word";

    static final String URL_MAIN = "";
    public static final String URL_WORD_LIST = URL_MAIN + "/word";
    public static final String URL_ADD_WORD = URL_WORD_LIST + "/{" + URL_PARAM_WORD + "}";
}

package com.us.itp.sandbox.webarch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class WordServiceImpl implements WordService {

    public WordServiceImpl() {
        this(Collections.emptyList());
    }

    public WordServiceImpl(@NonNull final String... words) {
        this(Arrays.asList(words));
    }

    public WordServiceImpl(@NonNull final List<String> words) {
        this.words.addAll(words);
    }

    // In a real web app, this would be a data repository bean.
    @NonNull private final List<String> words = new ArrayList<>();

    @NonNull @Override public List<String> listAllWords() {
        return Collections.unmodifiableList(words);
    }

    @Override public void addWord(@NonNull final String word) {
        words.add(word);
    }
}

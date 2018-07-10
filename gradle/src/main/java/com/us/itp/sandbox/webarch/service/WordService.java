package com.us.itp.sandbox.webarch.service;

import java.util.List;
import org.springframework.lang.NonNull;

public interface WordService {
    @NonNull List<String> listAllWords();
    void addWord(@NonNull String word);
}

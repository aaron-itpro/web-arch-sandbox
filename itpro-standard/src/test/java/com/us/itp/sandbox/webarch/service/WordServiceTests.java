package com.us.itp.sandbox.webarch.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.lang.NonNull;

public final class WordServiceTests {

    @NonNull @Rule public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void wordListIsInitializedFromConstructor() {
        wordListIsInitializedFromConstructor(Collections.emptyList());
        wordListIsInitializedFromConstructor("Alpha", "Bravo");
    }

    private void wordListIsInitializedFromConstructor(@NonNull final String... words) {
        wordListIsInitializedFromConstructor(Arrays.asList(words));
    }

    private void wordListIsInitializedFromConstructor(@NonNull final List<String> words) {
        final WordService service = new WordServiceImpl(words);
        assertThat(service.listAllWords(), is(words));
    }

    @Test
    public void addedWordAppearsInService() {
        final WordService service = new WordServiceImpl("Foo");
        final String word = "Bar";
        service.addWord(word);
        assertThat(service.listAllWords(), hasItem(word));
    }

    @Test
    public void wordListIsInInsertionOrder() {
        final List<String> words = Arrays.asList("Foo", "Bar", "Baz", "Quux");
        final WordService service = new WordServiceImpl();
        for (String word : words) {
            service.addWord(word);
        }
        assertThat(service.listAllWords(), is(words));
    }

    @Test
    public void wordListIsImmutable() {
        final WordService service = new WordServiceImpl("Foo");
        final List<String> words = service.listAllWords();
        thrown.expect(UnsupportedOperationException.class);
        words.add("Bar");
    }

    @Test
    public void wordListTracksChanges() {
        final WordService service = new WordServiceImpl("Foo, Bar");
        final List<String> words = service.listAllWords();
        final String newWord = "Baz";
        service.addWord(newWord);
        assertThat(words, hasItem(newWord));
    }
}

package com.us.itp.sandbox.webarch.view;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.us.itp.sandbox.webarch.controller.MainController;
import com.us.itp.sandbox.webarch.util.HtmlMatchers;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@ContextConfiguration(classes = { MainViewTests.Config.class })
public final class MainViewTests extends BaseViewTestCase {

    public MainViewTests() {
        super("main");
    }

    @Configuration
    static class Config {

        @Controller
        static class MockAjaxController {

            @Nullable private String word = null;

            @PostMapping("/word/{word}")
            public void setWord(@NonNull @PathVariable("word") String word) {
                this.word = word;
            }
        }
    }

    private Config.MockAjaxController ajax;

    @Before @Override
    public void setUp() {
        super.setUp();
        ajax = context.getBean(Config.MockAjaxController.class);
    }

    @Test
    public void wordListIsPopulatedCorrectly() throws Exception {
        final List<String> words = Arrays.asList("alpha", "bravo");
        final HtmlElement wordList = getWordListOnPageFor(words);
        for (String word : words) {
            assertThat(wordList, HtmlMatchers.containsText(word));
        }
    }

    @NonNull private HtmlElement getWordListOnPageFor(@NonNull final List<String> words) throws
        IOException {
        return getPageFor(words).getHtmlElementById("word-list");
    }

    @Test
    public void addingSimpleWordMakesAjaxCall() throws Exception {
        addingWordMakesAjaxCall("Aardvark");
    }

    @Test
    public void addedWordIsUrlEncoded() throws Exception {
        addingWordMakesAjaxCall("%3Fnonsense%3Dtrue", "?nonsense=true");
    }

    @SuppressWarnings("SameParameterValue")
    private void addingWordMakesAjaxCall(@NonNull final String word) throws Exception {
        addingWordMakesAjaxCall(word, word);
    }

    private void addingWordMakesAjaxCall(
        @NonNull final String expectedWord,
        @NonNull final String inputWord
    ) throws Exception {
        addWordOnPage(getPageFor(Collections.emptyList()), inputWord);
        assertEquals(expectedWord, ajax.word);
    }

    private void addWordOnPage(@NonNull final HtmlPage page, @NonNull final String word)
    throws IOException {
        final HtmlForm form = page.getFormByName("add-word");
        form.getInputByName("word").type(word);
        submitForm(form);
    }

    @NonNull private void submitForm(@NonNull final HtmlForm form) throws IOException {
        form.getOneHtmlElementByAttribute("button", "type", "submit").click();
    }

    @NonNull private HtmlPage getPageFor(@NonNull final List<String> words) throws IOException {
        return getPageFor(MainController.MODEL_ATTR_WORDS, words);
    }
}

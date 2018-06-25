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

            @PostMapping(MainController.URL_ADD_WORD)
            public void setWord(@NonNull @PathVariable(MainController.URL_PARAM_WORD) String word) {
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
        final String[] words = {"alpha", "bravo"};
        assertWordsInWordList(getPageFor(words), words);
    }

    private void assertWordsInWordList(HtmlPage page, String... expectedWords) {
        assertWordsInWordList(getWordListOnPage(page), expectedWords);
    }

    private void assertWordsInWordList(HtmlElement wordList, String... expectedWords) {
        for (String word : expectedWords) {
            assertThat(wordList, HtmlMatchers.containsText(word));
        }
    }

    @Test
    public void wordListFragmentMatchesMainPage() throws Exception {
        final String[] words = {"alpha", "bravo", "charlie"};
        assertWordListsMatchOn(getPageFor(words), getWordListFragmentFor(words));
    }

    private void assertWordListsMatchOn(HtmlPage page1, HtmlPage page2) {
        assertEquals(getWordListOnPage(page1).asXml(), getWordListOnPage(page2).asXml());
    }

    @NonNull private HtmlElement getWordListOnPage(@NonNull final HtmlPage page) {
        return page.getHtmlElementById(ID_WORD_LIST);
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
        addWordOnPage(getPageFor(), inputWord);
        assertEquals(expectedWord, ajax.word);
    }

    private void addWordOnPage(@NonNull final HtmlPage page, @NonNull final String word)
    throws IOException {
        final HtmlForm form = page.getFormByName(FORM_ADD_WORD);
        form.getInputByName(FIELD_WORD).type(word);
        submitForm(form);
    }

    @NonNull private void submitForm(@NonNull final HtmlForm form) throws IOException {
        form.getOneHtmlElementByAttribute("button", "type", "submit").click();
    }

    @NonNull private HtmlPage getPageFor(@NonNull final String... words) throws IOException {
        return getPageFor(MainController.MODEL_ATTR_WORDS, Arrays.asList(words));
    }

    @NonNull private HtmlPage getWordListFragmentFor(@NonNull final String... words)
    throws IOException {
        return getFragmentFor(
            FRAG_WORD_LIST,
            MainController.MODEL_ATTR_WORDS,
            Arrays.asList(words)
        );
    }

    private static final String ID_WORD_LIST = "word-list";
    private static final String FRAG_WORD_LIST = "wordList";
    private static final String FORM_ADD_WORD = "add-word";
    private static final String FIELD_WORD = "word";
}

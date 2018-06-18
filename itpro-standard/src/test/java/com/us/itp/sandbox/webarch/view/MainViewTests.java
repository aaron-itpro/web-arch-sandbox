package com.us.itp.sandbox.webarch.view;

import static org.hamcrest.MatcherAssert.assertThat;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.us.itp.sandbox.webarch.util.HtmlMatchers;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.lang.NonNull;

public final class MainViewTests extends BaseViewTestCase {

    public MainViewTests() {
        super("main");
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

    @NonNull private HtmlPage getPageFor(@NonNull List<String> words) throws IOException {
        return getPageFor("words", words);
    }
}

package com.us.itp.sandbox.webarch.controller;

import static com.us.itp.sandbox.webarch.util.MockMvcResultMatchers.modelAndView;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.us.itp.sandbox.webarch.service.WordService;
import com.us.itp.sandbox.webarch.service.WordServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public final class MainControllerTests {

    @Configuration
    static class Config {

        @Primary @Bean WordService wordService() {
            return new WordServiceImpl("Alpha", "Bravo");
        }

        @Bean MainController controller() {
            return new MainController(wordService());
        }
    }

    @Autowired @NonNull private MockMvc mvc;
    @Autowired @NonNull private WordService service;

    @Test
    public void mainPageLoads() throws Exception {
        assertPageLoads(MainController.URL_MAIN);
    }

    @Test
    public void wordListLoads() throws Exception {
        assertPageLoads(MainController.URL_WORD_LIST);
    }

    private void assertPageLoads(@NonNull final String url) throws Exception {
        mvc.perform(requestPage(url)).andExpect(status().isOk());
    }

    @Test
    public void wordListIsFromService() throws Exception {
        wordListIsFromService(MainController.URL_MAIN);
        wordListIsFromService(MainController.URL_WORD_LIST);
    }

    private void wordListIsFromService(@NonNull final String url) throws Exception {
        mvc.perform(requestPage(url)).andExpect(wordListMatchesService());
    }

    private ResultMatcher wordListMatchesService() {
        return model().attribute(MainController.MODEL_ATTR_WORDS, service.listAllWords());
    }

    private RequestBuilder requestPage(@NonNull final String url) {
        return get(url).accept(MediaType.TEXT_HTML);
    }

    @Test
    public void wordsAreAddedToService() throws Exception {
        assertAddingWordSucceeds("Foo");
    }

    @Test
    public void canAddWordWithSlash() throws Exception {
        assertAddingWordSucceeds("and/or");
    }

    private void assertAddingWordSucceeds(@NonNull final String word) throws Exception {
        assertRestCallSucceeds(postWord(word));
        assertThat(service.listAllWords(), hasItem(word));
    }

    private void assertRestCallSucceeds(@NonNull final RequestBuilder call) throws Exception {
        mvc.perform(call)
            .andExpect(status().isOk())
            .andExpect(modelAndView().isNull());
    }

    @SuppressWarnings("SameParameterValue")
    private RequestBuilder postWord(@NonNull final String word) {
        return post(MainController.URL_ADD_WORD, word);
    }
}

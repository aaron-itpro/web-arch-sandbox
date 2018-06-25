package com.us.itp.sandbox.webarch.controller;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
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
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { MainControllerTests.Config.class })
public final class MainControllerTests {

    @Configuration
    static class Config {

        @Bean WordService wordService() {
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

    private void assertPageLoads(String url) throws Exception {
        mvc.perform(requestPage(url)).andExpect(status().isOk());
    }

    @Test
    public void wordListIsFromService() throws Exception {
        wordListIsFromService(MainController.URL_MAIN);
        wordListIsFromService(MainController.URL_WORD_LIST);
    }

    private void wordListIsFromService(String url) throws Exception {
        mvc.perform(requestPage(url)).andExpect(wordListMatchesService());
    }

    private ResultMatcher wordListMatchesService() {
        return model().attribute(MainController.MODEL_ATTR_WORDS, service.listAllWords());
    }

    private RequestBuilder requestPage(String url) {
        return MockMvcRequestBuilders.get(url).accept(MediaType.TEXT_HTML);
    }

    @Test
    public void wordsAreAddedToService() throws Exception {
        final String word = "Foo";
        mvc.perform(MockMvcRequestBuilders.post(MainController.URL_ADD_WORD, word))
            .andExpect(status().isOk());
        assertThat(service.listAllWords(), hasItem(word));
    }
}

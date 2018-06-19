package com.us.itp.sandbox.webarch.controller;

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
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { MainControllerTests.Config.class })
public final class MainControllerTests {

    static final class Config {

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
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }

    @Test
    public void wordListIsFromService() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
            .andExpect(model().attribute("words", service.listAllWords()));
    }
}

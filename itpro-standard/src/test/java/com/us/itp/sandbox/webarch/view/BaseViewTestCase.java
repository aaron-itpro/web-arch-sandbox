package com.us.itp.sandbox.webarch.view;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Setter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base class for tests of code inside Thymeleaf templates.
 *
 * <p>To use this class, subclasses just need to provide the name of the Thymeleaf template to test
 * against via the {@link #BaseViewTestCase(String)} constructor.  Once that's done, individual
 * tests can obtain a freshly rendered HTML page for a particular data model by using the
 * {@link #getPageFor} method.  Any fragments in the template under test can be retrieved using
 * the {@link #getFragmentFor} method in a similar fashion.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
    ThymeleafAutoConfiguration.class,
    BaseViewTestCase.Config.class
})
abstract class BaseViewTestCase {

    @NonNull private String template;

    BaseViewTestCase(@NonNull final String template) {
        this.template = template;
    }

    @Configuration
    static class Config {

        @Controller
        static class MockController {

            @NonNull @Setter(AccessLevel.PACKAGE)
            private String template;

            @NonNull @Setter(AccessLevel.PACKAGE)
            private Map<String, Object> model;

            @GetMapping("/")
            @NonNull public String mainPage(@NonNull final Model model) {
                model.mergeAttributes(this.model);
                assert (template != null);
                return template;
            }

            @GetMapping("/fragment/{fragment}")
            @NonNull public String fragment(
                @PathVariable("fragment") final String fragment,
                @NonNull final Model model
            ) {
                model.mergeAttributes(this.model);
                assert (template != null);
                return template + " :: " + fragment;
            }
        }
    }

    @Autowired WebApplicationContext context;
    private WebClient webClient;
    private Config.MockController controller;

    @Before
    public void setUp() {
        controller = context.getBean(Config.MockController.class);
        controller.setTemplate(this.template);

        webClient = MockMvcWebClientBuilder.webAppContextSetup(context).build();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }

    @NonNull final HtmlPage getPageFor(
        @NonNull final String key,
        @Nullable final Object value
    ) throws IOException {
        return getPageFor(Collections.singletonMap(key, value));
    }

    @NonNull private HtmlPage getPageFor(
        @NonNull final Map<String, Object> model
    ) throws IOException {
        controller.setModel(model);
        return webClient.getPage(BASE_URL);
    }

    @NonNull final HtmlPage getFragmentFor(
        @NonNull final String fragmentName,
        @NonNull final String key,
        @Nullable final Object value
    ) throws IOException {
        return getFragmentFor(fragmentName, Collections.singletonMap(key, value));
    }

    @NonNull private HtmlPage getFragmentFor(
        @NonNull final String fragmentName,
        @NonNull final Map<String, Object> model
    ) throws IOException {
        controller.setModel(model);
        return webClient.getPage(BASE_URL + "fragment/" + fragmentName);
    }

    private static final String BASE_URL = "http://localhost/";
}

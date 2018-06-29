package com.us.itp.sandbox.webarch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class WebArchExampleApplication {

    public static void main(@NonNull final String[] args) {
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
        SpringApplication.run(WebArchExampleApplication.class, args);
    }
}

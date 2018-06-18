package com.us.itp.sandbox.webarch.controller;

import java.util.Arrays;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class MainController {

    @GetMapping("/")
    @NonNull public String mainPage(@NonNull Model model) {
        model.addAttribute("words", Arrays.asList("Foo", "Bar"));
        return "main";
    }
}

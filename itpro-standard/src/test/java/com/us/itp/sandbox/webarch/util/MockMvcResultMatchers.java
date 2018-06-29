package com.us.itp.sandbox.webarch.util;

import org.springframework.lang.NonNull;

/** Access to custom result matchers for use with the MVC Test Framework. */
public final class MockMvcResultMatchers {

    // Static utility class; do not instantiate.
    private MockMvcResultMatchers() {}

    @NonNull public static ModelAndViewResultMatchers modelAndView() {
        return new ModelAndViewResultMatchers();
    }
}

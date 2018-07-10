package com.us.itp.sandbox.webarch.util;

import org.springframework.lang.NonNull;

/** Access to custom result matchers for use with the MVC Test Framework. */
public final class MockMvcResultMatchers {

    private MockMvcResultMatchers() {
        StaticUtil.preventInstantiation(this);
    }

    @NonNull public static ModelAndViewResultMatchers modelAndView() {
        return new ModelAndViewResultMatchers();
    }
}

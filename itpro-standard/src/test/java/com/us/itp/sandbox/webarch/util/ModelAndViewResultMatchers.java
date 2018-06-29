package com.us.itp.sandbox.webarch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;

import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.ResultMatcher;

/** Custom MVC result matchers accessing the model and view together. */
public final class ModelAndViewResultMatchers {

    /** Package-private constructor; access through {@link MockMvcResultMatchers#modelAndView()}. */
    ModelAndViewResultMatchers() {}

    @SuppressWarnings("ConstantConditions")
    @NonNull public ResultMatcher isNull() {
        return result -> assertThat(result.getModelAndView(), is(nullValue()));
    }
}

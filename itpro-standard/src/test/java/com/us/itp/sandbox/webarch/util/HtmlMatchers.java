package com.us.itp.sandbox.webarch.util;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.StringContains;
import org.springframework.lang.NonNull;

/** Hamcrest matchers for matching against HTML content. */
public final class HtmlMatchers {

    // Static utility class; do not instantiate.
    private HtmlMatchers() {}

    @NonNull public static Matcher<HtmlElement> containsText(@NonNull final String text) {
        return new TypeSafeMatcher<HtmlElement>() {

            @Override public void describeTo(@NonNull final Description description) {
                description.appendText("an HTML tag containing text ").appendValue(text);
            }

            @Override protected boolean matchesSafely(@NonNull final HtmlElement item) {
                return new StringContains(text).matches(item.getTextContent());
            }
        };
    }
}

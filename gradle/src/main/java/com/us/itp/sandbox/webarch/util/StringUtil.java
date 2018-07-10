package com.us.itp.sandbox.webarch.util;

import org.springframework.lang.NonNull;

public final class StringUtil {

    private StringUtil() {
        StaticUtil.preventInstantiation(this);
    }

    @NonNull public static String dropPrefix(
        @NonNull final String prefix,
        @NonNull final String s
    ) {
        return s.replaceFirst("^" + prefix, "");
    }
}

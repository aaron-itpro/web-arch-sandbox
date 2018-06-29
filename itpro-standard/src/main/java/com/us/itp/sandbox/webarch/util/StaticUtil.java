package com.us.itp.sandbox.webarch.util;

import org.springframework.lang.NonNull;

final class StaticUtil {

    private StaticUtil() {
        preventInstantiation(this);
    }

    /** Throws UnsupportedOperationException to indicate the target should not be instantiated. */
    static void preventInstantiation(@NonNull final Object target) {
        preventInstantiation(target.getClass().getSimpleName());
    }

    /** Throws UnsupportedOperationException to indicate the target should not be instantiated. */
    private static void preventInstantiation(@NonNull final String className) {
        throw new UnsupportedOperationException(
            className + " is a static utility class and cannot be instantiated"
        );
    }
}

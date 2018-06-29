package com.us.itp.sandbox.webarch.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.springframework.lang.NonNull;

public final class UrlUtil {

    private UrlUtil() {
        StaticUtil.preventInstantiation(this);
    }

    @NonNull public static String decodeUrl(@NonNull final String urlComponent) {
        try {
            return URLDecoder.decode(urlComponent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 encoding should be supported", e);
        }
    }
}

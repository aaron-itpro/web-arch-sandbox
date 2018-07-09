package com.us.itp.sandbox.webarch.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.springframework.lang.NonNull;

public final class UrlUtil {

    private UrlUtil() {
        StaticUtil.preventInstantiation(this);
    }

    @NonNull public static String doubleDecodeUrl(@NonNull final String urlComponent) {
        return decodeUrl(decodeUrl(urlComponent));
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull public static String decodeUrl(@NonNull final String urlComponent) {
        return transcodeUrl(urlComponent, URLDecoder::decode);
    }

    @NonNull public static String encodeUrl(@NonNull final String urlComponent) {
        return transcodeUrl(urlComponent, URLEncoder::encode);
    }

    @FunctionalInterface private interface Transcoder {
        @NonNull String apply(
            String urlComponent,
            String encoding
        ) throws UnsupportedEncodingException;
    }

    @NonNull private static String transcodeUrl(
        @NonNull final String urlComponent,
        @NonNull final Transcoder transcoder
    ) {
        try {
            return transcoder.apply(urlComponent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 encoding should be supported", e);
        }
    }
}

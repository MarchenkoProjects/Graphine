package io.graphine.processor.util;

import java.util.function.Supplier;

/**
 * @author Oleg Marchenko
 */
public final class StringUtils {
    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String nullToEmpty(String str) {
        return str == null ? EMPTY : str;
    }

    public static String getIfNotEmpty(String str, Supplier<String> defaultSupplier) {
        return isNotEmpty(str) ? defaultSupplier.get() : str;
    }

    private StringUtils() {
    }
}

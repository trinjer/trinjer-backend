package org.trinjer.util;

public class RestUtils {

    public static String createJsonFromString(String key, String value) {
        return String.format("{\"%s\": \"%s\"}", key, value);
    }
}

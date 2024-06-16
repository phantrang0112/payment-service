package main.utils;

import java.util.Date;
import java.util.Objects;

public class Utils {
    private Utils() {
    }

    public static int parseInt(Object value) {
        try {
            return Integer.parseInt(parseString(value));
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    public static Integer parseInt(Object value, Integer defaultValue) {
        try {
            Integer val = Integer.parseInt(parseString(value));
            if (val == Integer.MIN_VALUE) {
                return defaultValue;
            }
            return val;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String parseString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date castToDate(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        try {
            return (Date) value;
        } catch (Exception e) {
            return null;
        }
    }

    public static Long parseLong(Object value) {
        try {
            return Long.parseLong(parseString(value));
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Long parseLongToDoble(Double value) {
        try {
            return Math.round(value);
        } catch (Exception e) {
            return 0L;
        }
    }
}

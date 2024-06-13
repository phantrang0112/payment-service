package main.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommonService {

    public static Boolean listArrayIsNull(List<Object[]> value) {
        if (value == null || value.size() == 0) {
            return true;
        }
        return false;
    }

    public static Boolean listIsNull(List<?> value) {
        if (value == null || value.size() == 0) {
            return true;
        }
        return false;
    }

    public static Boolean isNull(Object value) {
        if (value == null) {
            return true;
        }
        return false;
    }

    public static String formatDateNowToString() {
        LocalDateTime localDate = LocalDateTime.now();// For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYYHHmmss");
        return localDate.format(formatter);
    }
    public static Boolean isEmpty(String value) {
        if (value == null|| value.isEmpty()) {
            return true;
        }
        return false;
    }
}

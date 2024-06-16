package main.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CommonService {

    public static Boolean listIsNull(List<?> value) {
        return value == null || value.size() == 0;
    }

    public static Boolean listIsNull(Set<?> value) {
        return value == null || value.size() == 0;
    }

    public static Boolean isNull(Object value) {
        return value == null;
    }

    public static Boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static Date convertStringDate(String format, String value) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(value);
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            System.err.println("check format");
            return null;
        }
    }

    public static String convertDateString(Date value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(value);
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean isPositiveInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

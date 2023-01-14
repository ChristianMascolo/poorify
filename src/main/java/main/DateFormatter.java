package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

    public static final String SQL_FORMAT = "yyyy-MM-dd";
    public static final String PLAYLIST_DISPLAY_FORMAT = "MMM dd, yyyy";

    public String format(String date, String patternFrom, String patternTo) {
        date = date.substring(0, 10);
        DateTimeFormatter from = DateTimeFormatter.ofPattern(patternFrom, Locale.ENGLISH);
        DateTimeFormatter to = DateTimeFormatter.ofPattern(patternTo, Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date, from);
        return to.format(ld);
    }

    public String getCurrentDateAzureSQLFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().format(formatter);
    }

}

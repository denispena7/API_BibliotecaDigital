package es.library.springboot.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils 
{
    private static final DateTimeFormatter CLIENT_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean isDateValid(String date) {
        try {
            LocalDate.parse(date, CLIENT_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, CLIENT_FORMAT);
    }

    public static String formatDate(LocalDate date) {
        return date.format(CLIENT_FORMAT);
    }

    public static boolean isBeforeToday(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}

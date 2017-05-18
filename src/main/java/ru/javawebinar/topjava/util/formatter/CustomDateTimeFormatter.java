package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;

public class CustomDateTimeFormatter implements Formatter<Temporal> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    private Style style = Style.FULL;

    void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public Temporal parse(String text, Locale locale) throws ParseException {
        Temporal temporal;
        switch (style) {
            case DATE:
                temporal = LocalDate.parse(text, DATE_FORMATTER);
                break;
            case TIME:
                temporal = LocalTime.parse(text, TIME_FORMATTER);
                break;
            default:
                temporal = LocalDateTime.parse(text, DATE_TIME_FORMATTER);
        }
        return temporal;
    }

    @Override
    public String print(Temporal temporal, Locale locale) {
        return temporal.toString();
    }

    public enum Style {
        FULL,
        DATE,
        TIME
    }
}

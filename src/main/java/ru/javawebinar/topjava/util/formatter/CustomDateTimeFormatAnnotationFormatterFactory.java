package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomDateTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(LocalDateTime.class, LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(CustomDateTimeFormat annotation, Class<?> fieldType) {
        CustomDateTimeFormatter customDateTimeFormatter = new CustomDateTimeFormatter();
        customDateTimeFormatter.setStyle(annotation.style());
        return customDateTimeFormatter;
    }

    @Override
    public Parser<?> getParser(CustomDateTimeFormat annotation, Class<?> fieldType) {
        CustomDateTimeFormatter customDateTimeFormatter = new CustomDateTimeFormatter();
        customDateTimeFormatter.setStyle(annotation.style());
        return customDateTimeFormatter;
    }
}

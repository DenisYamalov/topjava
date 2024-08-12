package ru.javawebinar.topjava.web.converter;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return LocalDateTime.parse(text);
        }

        @Override
        public String print(LocalDateTime ldt, Locale locale) {
            return ldt.format(DateTimeFormatter.ISO_DATE_TIME);
        }
    }

    public static final class StringToInteger implements Converter<String, Integer> {

        public Integer convert(String source) {
//            return Integer.valueOf(source);
            return source.isBlank() ? 0 : Integer.valueOf(source);
        }
    }

}

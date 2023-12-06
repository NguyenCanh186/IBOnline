package com.vmg.ibo.core.utils;

import com.vmg.ibo.core.config.exception.WebServiceException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_DB = "yyyy-dd-MM HH:mm:ss";
    public static final String DATE_FORMAT_DB = "yyyy-dd-MM";

    public static String parseToString(Date date) {
        return parseDateWithFormat(date, DATE_TIME_FORMAT);
    }

    public static String parseToStringFormatDB(Date date) {
        return parseDateWithFormat(date, DATE_TIME_FORMAT_DB);
    }

    public static String parseToStringFormatDateDB(Date date) {
        return parseDateWithFormat(date, DATE_FORMAT_DB);
    }

    private static String parseDateWithFormat(Date date, String dateTimeFormatDb) {
        if (Objects.isNull(date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormatDb);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(formatter);
    }

    public static Date parseToDate(String dateString) {
        if (Objects.isNull(dateString)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
            return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            throw new WebServiceException(HttpStatus.BAD_REQUEST.value(), "report.error.invalid-time");
        }
    }

    public static Date getStartTimeOfDate(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendarStart = convertToCalender(date);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        return calendarStart.getTime();
    }

    public static Calendar convertToCalender(String dateString) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputDateFormat.parse(dateString);
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(date);
            return calendarStart;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getEndTimeOfDate(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendarEnd = convertToCalender(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);
        return calendarEnd.getTime();
    }

    public static Date now() {
        return new Date();
    }
}

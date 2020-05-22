package com.example.todolistandroidapp.Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Long tryParse(String value, Long defaultVal) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public  static Date unixTimeToDate(long unixTime)
    {
        return  new java.util.Date((long)unixTime*1000);
    }

    public static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String dateTimeNow=(dateFormat.format(date));
        return dateTimeNow;
    }

}

package com.example.sleeptracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {

    private static String pattern1 = "yyyy-MM-dd     HH:mm";
    private static String pattern2 = "yyyy-MM-dd";

    public static String format1(Date date) {
        return new SimpleDateFormat(pattern1).format(date);
    }
    public static String format2(Date date) {
        return new SimpleDateFormat(pattern2).format(date);
    }
}

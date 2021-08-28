package com.example.sleeptracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {

    private static String pattern = "yyyy-MM-dd     HH:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static String format(Date date){
        return simpleDateFormat.format(date);
    }
}

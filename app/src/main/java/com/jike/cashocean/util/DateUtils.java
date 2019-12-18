package com.jike.cashocean.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ming
 * @Date on 2019/12/9
 * @Description
 */
public class DateUtils {

    public static String format = "MM-dd-yyyy HH:mm:ss";

    public static String date2TimeStamp(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

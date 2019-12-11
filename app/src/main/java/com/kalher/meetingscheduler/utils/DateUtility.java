package com.kalher.meetingscheduler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

    public static String getDateDDMMYYYY(Calendar cal) {

        if (cal != null && cal.getTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(cal.getTime());
        }
        return "";
    }

    public static String getTimeHHMM(Calendar cal) {
        if (cal != null && cal.getTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(cal.getTime());
        }
        return "";
    }

    public static boolean isDateSame(Calendar cal1, Calendar cal2){
        if(cal1 != null && cal2 != null){
            return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        }
        return false;
    }

    public static Calendar timeToCalendar(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}

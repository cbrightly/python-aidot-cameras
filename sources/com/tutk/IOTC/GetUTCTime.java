package com.tutk.IOTC;

import android.text.TextUtils;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GetUTCTime {
    private Calendar cal;
    private int dstOffset = this.cal.get(16);
    private int zoneOffset;

    public GetUTCTime() {
        Calendar instance = Calendar.getInstance();
        this.cal = instance;
        this.zoneOffset = instance.get(15);
    }

    public static void main(String[] args) {
        GetUTCTime gc = new GetUTCTime();
        gc.setUTCTime(gc.getUTCTimeStr());
    }

    public long getUTCTimeStr() {
        PrintStream printStream = System.out;
        printStream.println("local millis = " + this.cal.getTimeInMillis());
        this.cal.add(14, -(this.zoneOffset + this.dstOffset));
        long mills = this.cal.getTimeInMillis();
        PrintStream printStream2 = System.out;
        printStream2.println("UTC = " + mills);
        return mills;
    }

    public void setUTCTime(long millis) {
        this.cal.setTimeInMillis(millis);
        SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = foo.format(this.cal.getTime());
        this.cal.add(14, this.zoneOffset + this.dstOffset);
        String time = foo.format(this.cal.getTime());
    }

    public static long getUTCTime(String millisStr) {
        return getUTCTime(dateToLong(millisStr));
    }

    public static long getUTCTime(long millis) {
        Calendar cal2 = Calendar.getInstance();
        int zoneOffset2 = cal2.get(15);
        int dstOffset2 = cal2.get(16);
        SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal2.setTimeInMillis(millis);
        cal2.add(14, zoneOffset2 + dstOffset2);
        return dateToLong(foo.format(cal2.getTime()));
    }

    public static long dateToLong(String time) {
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (TextUtils.isEmpty(time) || (date = sdf.parse(time)) == null) {
                return 0;
            }
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void getGMTTime() {
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(gmtTime);
        PrintStream printStream = System.out;
        printStream.println("GMT Time: " + format.format(date));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeZone(gmtTime);
        PrintStream printStream2 = System.out;
        printStream2.println("GMT hour = " + calendar1.get(10));
    }
}

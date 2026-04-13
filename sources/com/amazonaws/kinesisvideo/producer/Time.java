package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import java.util.Date;

public class Time {
    public static final long HUNDREDS_OF_NANOS_IN_AN_HOUR = 36000000000L;
    public static final long HUNDREDS_OF_NANOS_IN_A_MICROSECOND = 10;
    public static final long HUNDREDS_OF_NANOS_IN_A_MILLISECOND = 10000;
    public static final long HUNDREDS_OF_NANOS_IN_A_MINUTE = 600000000;
    public static final long HUNDREDS_OF_NANOS_IN_A_SECOND = 10000000;
    public static final long NANOS_IN_A_MILLISECOND = 1000000;
    public static final long NANOS_IN_A_TIME_UNIT = 100;

    public static final long getCurrentTime() {
        return System.nanoTime() * 100;
    }

    public static final long getTime(@NonNull Date date) {
        return date.getTime() * 10000;
    }
}

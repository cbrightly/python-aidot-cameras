package com.didichuxing.doraemonkit.util;

import java.util.Date;

public class FormatUtil {
    private FormatUtil() {
    }

    public static String format(long time) {
        return new Date(time).toString();
    }
}

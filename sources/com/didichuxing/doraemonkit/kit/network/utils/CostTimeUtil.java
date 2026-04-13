package com.didichuxing.doraemonkit.kit.network.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import com.didichuxing.doraemonkit.R;

public class CostTimeUtil {
    public static final long DAY = 86400000;
    public static final long HOUR = 3600000;
    public static final long MINUTE = 60000;
    public static final long SECOND = 1000;

    public static SpannableString formatTime(Context context, long time) {
        if (time == 0) {
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.5f);
            SpannableString spannableString = new SpannableString(context.getString(R.string.dk_network_summary_total_time_default));
            spannableString.setSpan(sizeSpan, spannableString.length() - 1, spannableString.length(), 17);
            return spannableString;
        } else if (time < 100000) {
            RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.5f);
            SpannableString spannableString2 = new SpannableString(context.getString(R.string.dk_network_summary_total_time_second, new Object[]{Long.valueOf(time / 1000)}));
            spannableString2.setSpan(sizeSpan2, spannableString2.length() - 1, spannableString2.length(), 17);
            return spannableString2;
        } else if (time < 6000000) {
            long minute = time / 60000;
            SpannableString spannableString3 = new SpannableString(context.getString(R.string.dk_network_summary_total_time_minute, new Object[]{Long.valueOf(minute), Long.valueOf((time % 60000) / 1000)}));
            spannableString3.setSpan(new RelativeSizeSpan(0.5f), String.valueOf(minute).length(), String.valueOf(minute).length() + 1, 17);
            spannableString3.setSpan(new RelativeSizeSpan(0.5f), spannableString3.length() - 1, spannableString3.length(), 17);
            return spannableString3;
        } else if (time < 360000000) {
            long hour = time / HOUR;
            SpannableString spannableString4 = new SpannableString(context.getString(R.string.dk_network_summary_total_time_hour, new Object[]{Long.valueOf(hour), Long.valueOf((time % HOUR) / 60000)}));
            spannableString4.setSpan(new RelativeSizeSpan(0.5f), String.valueOf(hour).length(), String.valueOf(hour).length() + 2, 17);
            spannableString4.setSpan(new RelativeSizeSpan(0.5f), spannableString4.length() - 1, spannableString4.length(), 17);
            return spannableString4;
        } else {
            long day = time / DAY;
            long hour2 = (time % DAY) / HOUR;
            SpannableString spannableString5 = new SpannableString(context.getString(R.string.dk_network_summary_total_time_day, new Object[]{Long.valueOf(day), Long.valueOf(hour2)}));
            spannableString5.setSpan(new RelativeSizeSpan(0.5f), String.valueOf(day).length(), String.valueOf(day).length() + 1, 17);
            spannableString5.setSpan(new RelativeSizeSpan(0.5f), spannableString5.length() - 2, spannableString5.length(), 17);
            return spannableString5;
        }
    }
}

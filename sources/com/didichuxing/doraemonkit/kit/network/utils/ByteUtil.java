package com.didichuxing.doraemonkit.kit.network.utils;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

public class ByteUtil {
    public static String getPrintSize(long size) {
        if (size < 1024) {
            return String.valueOf(size) + "B";
        }
        long size2 = size / 1024;
        if (size2 < 1024) {
            return String.valueOf(size2) + "KB";
        }
        long size3 = size2 / 1024;
        if (size3 < 1024) {
            long size4 = size3 * 100;
            return String.valueOf(size4 / 100) + "." + String.valueOf(size4 % 100) + "MB";
        }
        long size5 = (size3 * 100) / 1024;
        return String.valueOf(size5 / 100) + "." + String.valueOf(size5 % 100) + "GB";
    }

    public static SpannableString getPrintSizeForSpannable(long size) {
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.5f);
        if (size < 1024) {
            SpannableString spannableString = new SpannableString(String.valueOf(size) + "B");
            spannableString.setSpan(sizeSpan, spannableString.length() + -1, spannableString.length(), 17);
            return spannableString;
        }
        long size2 = size / 1024;
        if (size2 < 1024) {
            SpannableString spannableString2 = new SpannableString(String.valueOf(size2) + "KB");
            spannableString2.setSpan(sizeSpan, spannableString2.length() + -2, spannableString2.length(), 17);
            return spannableString2;
        }
        long size3 = size2 / 1024;
        if (size3 < 1024) {
            long size4 = size3 * 100;
            SpannableString spannableString3 = new SpannableString(String.valueOf(size4 / 100) + "." + String.valueOf(size4 % 100) + "MB");
            spannableString3.setSpan(sizeSpan, spannableString3.length() + -2, spannableString3.length(), 17);
            return spannableString3;
        }
        long size5 = (size3 * 100) / 1024;
        SpannableString spannableString4 = new SpannableString(String.valueOf(size5 / 100) + "." + String.valueOf(size5 % 100) + "GB");
        spannableString4.setSpan(sizeSpan, spannableString4.length() + -2, spannableString4.length(), 17);
        return spannableString4;
    }
}

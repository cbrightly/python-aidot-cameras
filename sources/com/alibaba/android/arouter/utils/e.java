package com.alibaba.android.arouter.utils;

import android.net.Uri;
import android.text.TextUtils;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: TextUtils */
public class e {
    public static boolean b(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String a(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ");
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Map<String, String> c(Uri rawUri) {
        String query = rawUri.getEncodedQuery();
        if (query == null) {
            return Collections.emptyMap();
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        int start = 0;
        do {
            int next = query.indexOf(38, start);
            int end = next == -1 ? query.length() : next;
            int separator = query.indexOf(61, start);
            if (separator > end || separator == -1) {
                separator = end;
            }
            String name = query.substring(start, separator);
            if (!TextUtils.isEmpty(name)) {
                paramMap.put(Uri.decode(name), Uri.decode(separator == end ? "" : query.substring(separator + 1, end)));
            }
            start = end + 1;
        } while (start < query.length());
        return Collections.unmodifiableMap(paramMap);
    }
}

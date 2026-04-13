package com.google.maps.android.data.kml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KmlUtil {
    public static String substituteProperties(String template, KmlPlacemark placemark) {
        StringBuffer sb = new StringBuffer();
        Matcher matcher = Pattern.compile("\\$\\[(.+?)]").matcher(template);
        while (matcher.find()) {
            String value = placemark.getProperty(matcher.group(1));
            if (value != null) {
                matcher.appendReplacement(sb, value);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

package com.didichuxing.doraemonkit.widget.jsonviewer.utils;

import java.util.regex.Pattern;

public class Utils {
    private static Pattern urlPattern = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");

    public static boolean isUrl(String str) {
        return urlPattern.matcher(str).matches();
    }

    public static String jsonFormat(String jsonStr) {
        if (jsonStr == null) {
            return "";
        }
        int level = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && 10 == builder.charAt(builder.length() - 1)) {
                builder.append(getHierarchyStr(level));
            }
            switch (c) {
                case ',':
                    builder.append(c);
                    builder.append("\n");
                    break;
                case '[':
                case '{':
                    builder.append(c);
                    builder.append("\n");
                    level++;
                    break;
                case ']':
                case '}':
                    builder.append("\n");
                    level--;
                    builder.append(getHierarchyStr(level));
                    builder.append(c);
                    break;
                default:
                    builder.append(c);
                    break;
            }
        }
        return builder.toString();
    }

    public static String getHierarchyStr(int hierarchy) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < hierarchy; levelI++) {
            levelStr.append("      ");
        }
        return levelStr.toString();
    }
}

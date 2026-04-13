package org.apache.commons.lang3;

import java.util.regex.Pattern;

/* compiled from: StringUtils */
public class a {
    private static final Pattern a = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static boolean a(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null || cs1.length() != cs2.length()) {
            return false;
        }
        if ((cs1 instanceof String) && (cs2 instanceof String)) {
            return cs1.equals(cs2);
        }
        int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}

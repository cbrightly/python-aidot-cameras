package com.blankj.utilcode.util;

/* compiled from: StringUtils */
public final class a0 {
    public static boolean b(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(CharSequence s1, CharSequence s2) {
        if (s1 == s2) {
            return true;
        }
        if (!(s1 == null || s2 == null)) {
            int length = s1.length();
            int length2 = length;
            if (length == s2.length()) {
                if ((s1 instanceof String) && (s2 instanceof String)) {
                    return s1.equals(s2);
                }
                for (int i = 0; i < length2; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

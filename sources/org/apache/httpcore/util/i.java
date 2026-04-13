package org.apache.httpcore.util;

/* compiled from: TextUtils */
public final class i {
    public static boolean b(CharSequence s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean a(CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

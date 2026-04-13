package org.eclipse.paho.client.mqttv3.util;

/* compiled from: Strings */
public final class b {
    public static boolean d(CharSequence cs, CharSequence[] strs) {
        boolean eq = false;
        if (cs == null) {
            eq = strs == null;
        }
        if (strs != null) {
            for (CharSequence str : strs) {
                eq = eq || str.equals(cs);
            }
        }
        return eq;
    }

    public static boolean a(CharSequence cs, CharSequence searchChars) {
        if (searchChars == null) {
            return false;
        }
        return b(cs, h(searchChars));
    }

    public static boolean b(CharSequence cs, char[] searchChars) {
        if (f(cs) || g(searchChars)) {
            return false;
        }
        int csLength = cs.length();
        int searchLength = searchChars.length;
        int csLast = csLength - 1;
        int searchLast = searchLength - 1;
        for (int i = 0; i < csLength; i++) {
            char ch = cs.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == ch) {
                    if (!Character.isHighSurrogate(ch) || j == searchLast) {
                        return true;
                    }
                    if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean f(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    private static boolean g(char[] array) {
        return array == null || array.length == 0;
    }

    private static char[] h(CharSequence cs) {
        if (cs instanceof String) {
            return ((String) cs).toCharArray();
        }
        int sz = cs.length();
        char[] array = new char[cs.length()];
        for (int i = 0; i < sz; i++) {
            array[i] = cs.charAt(i);
        }
        return array;
    }

    public static int c(CharSequence str, CharSequence sub) {
        if (f(str) || f(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while (true) {
            int e = e(str, sub, idx);
            int idx2 = e;
            if (e == -1) {
                return count;
            }
            count++;
            idx = idx2 + sub.length();
        }
    }

    private static int e(CharSequence cs, CharSequence searchChar, int start) {
        return cs.toString().indexOf(searchChar.toString(), start);
    }
}

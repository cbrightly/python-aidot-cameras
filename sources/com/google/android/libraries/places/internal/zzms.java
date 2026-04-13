package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzms {
    public static Object zza(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str.concat(" must not be null"));
    }

    public static String zzb(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("identifier must not be empty");
        } else if (zzc(str.charAt(0))) {
            int i = 1;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if (zzc(charAt) || ((charAt >= '0' && charAt <= '9') || charAt == '_')) {
                    i++;
                } else {
                    throw new IllegalArgumentException("identifier must contain only ASCII letters, digits or underscore: ".concat(str));
                }
            }
            return str;
        } else {
            throw new IllegalArgumentException("identifier must start with an ASCII letter: ".concat(str));
        }
    }

    private static boolean zzc(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= 'A') {
            return c <= 'Z';
        }
        return false;
    }
}

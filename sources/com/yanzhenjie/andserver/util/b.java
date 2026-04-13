package com.yanzhenjie.andserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: DigestUtils */
public class b {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String f(String string) {
        return g(string.getBytes());
    }

    public static String g(byte[] bytes) {
        return c("MD5", bytes);
    }

    private static MessageDigest e(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    private static byte[] a(String algorithm, byte[] bytes) {
        return e(algorithm).digest(bytes);
    }

    private static String c(String algorithm, byte[] bytes) {
        return new String(b(algorithm, bytes));
    }

    private static char[] b(String algorithm, byte[] bytes) {
        return d(a(algorithm, bytes));
    }

    private static char[] d(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i += 2) {
            byte b = bytes[i / 2];
            char[] cArr = a;
            chars[i] = cArr[(b >>> 4) & 15];
            chars[i + 1] = cArr[b & 15];
        }
        return chars;
    }
}

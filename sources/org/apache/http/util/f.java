package org.apache.http.util;

import java.io.UnsupportedEncodingException;
import org.apache.http.b;

/* compiled from: EncodingUtils */
public final class f {
    public static byte[] d(String data, String charset) {
        a.i(data, "Input");
        a.e(charset, "Charset");
        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

    public static byte[] a(String data) {
        a.i(data, "Input");
        return data.getBytes(b.b);
    }

    public static String c(byte[] data, int offset, int length) {
        a.i(data, "Input");
        return new String(data, offset, length, b.b);
    }

    public static String b(byte[] data) {
        a.i(data, "Input");
        return c(data, 0, data.length);
    }
}

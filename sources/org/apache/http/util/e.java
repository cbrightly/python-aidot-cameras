package org.apache.http.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/* compiled from: CharsetUtils */
public class e {
    public static Charset b(String name) {
        if (name == null) {
            return null;
        }
        try {
            return Charset.forName(name);
        } catch (UnsupportedCharsetException e) {
            return null;
        }
    }

    public static Charset a(String name) {
        if (name == null) {
            return null;
        }
        try {
            return Charset.forName(name);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(name);
        }
    }
}

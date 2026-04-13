package org.apache.http.protocol;

import java.nio.charset.Charset;
import org.apache.http.b;

/* compiled from: HTTP */
public final class e {
    public static final Charset a = b.c;
    public static final Charset b = b.b;

    public static boolean a(char ch) {
        return ch == ' ' || ch == 9 || ch == 13 || ch == 10;
    }
}

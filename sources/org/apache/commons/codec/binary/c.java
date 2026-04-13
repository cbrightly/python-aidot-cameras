package org.apache.commons.codec.binary;

import java.nio.charset.Charset;
import org.apache.commons.codec.a;

/* compiled from: StringUtils */
public class c {
    private static String a(byte[] bytes, Charset charset) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charset);
    }

    public static String b(byte[] bytes) {
        return a(bytes, a.f);
    }
}

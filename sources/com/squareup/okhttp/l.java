package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.f;

/* compiled from: Credentials */
public final class l {
    public static String a(String userName, String password) {
        try {
            String encoded = f.of((userName + ":" + password).getBytes("ISO-8859-1")).base64();
            return "Basic " + encoded;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}

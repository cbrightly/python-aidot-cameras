package com.yanzhenjie.andserver.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/* compiled from: UrlCoder */
public class l {
    public static String c(String target, String charset) {
        try {
            return URLEncoder.encode(target, charset);
        } catch (UnsupportedEncodingException e) {
            return target;
        }
    }

    public static String a(String target, String charset) {
        try {
            return URLDecoder.decode(target, charset);
        } catch (UnsupportedEncodingException e) {
            return target;
        }
    }

    public static String b(String target, Charset charset) {
        return a(target, charset.name());
    }
}

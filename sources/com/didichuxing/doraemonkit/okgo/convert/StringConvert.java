package com.didichuxing.doraemonkit.okgo.convert;

import okhttp3.d0;
import okhttp3.e0;

public class StringConvert implements Converter<String> {
    public String convertResponse(d0 response) {
        e0 body = response.a();
        if (body == null) {
            return null;
        }
        return body.string();
    }
}

package com.didichuxing.doraemonkit.okgo.callback;

import com.didichuxing.doraemonkit.okgo.convert.StringConvert;
import okhttp3.d0;

public abstract class StringCallback extends AbsCallback<String> {
    private StringConvert convert = new StringConvert();

    public String convertResponse(d0 response) {
        String s = this.convert.convertResponse(response);
        response.close();
        return s;
    }
}

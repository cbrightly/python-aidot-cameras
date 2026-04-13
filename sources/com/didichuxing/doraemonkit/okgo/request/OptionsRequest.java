package com.didichuxing.doraemonkit.okgo.request;

import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.request.base.BodyRequest;
import okhttp3.b0;
import okhttp3.c0;

public class OptionsRequest<T> extends BodyRequest<T, OptionsRequest<T>> {
    public OptionsRequest(String url) {
        super(url);
    }

    public HttpMethod getMethod() {
        return HttpMethod.OPTIONS;
    }

    public b0 generateRequest(c0 requestBody) {
        return generateRequestBuilder(requestBody).i("OPTIONS", requestBody).p(this.url).o(this.tag).b();
    }
}

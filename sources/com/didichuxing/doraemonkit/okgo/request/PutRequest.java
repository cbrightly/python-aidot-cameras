package com.didichuxing.doraemonkit.okgo.request;

import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.request.base.BodyRequest;
import okhttp3.b0;
import okhttp3.c0;

public class PutRequest<T> extends BodyRequest<T, PutRequest<T>> {
    public PutRequest(String url) {
        super(url);
    }

    public HttpMethod getMethod() {
        return HttpMethod.PUT;
    }

    public b0 generateRequest(c0 requestBody) {
        return generateRequestBuilder(requestBody).l(requestBody).p(this.url).o(this.tag).b();
    }
}

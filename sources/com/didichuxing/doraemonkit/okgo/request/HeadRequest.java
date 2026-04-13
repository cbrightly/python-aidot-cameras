package com.didichuxing.doraemonkit.okgo.request;

import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.request.base.NoBodyRequest;
import okhttp3.b0;
import okhttp3.c0;

public class HeadRequest<T> extends NoBodyRequest<T, HeadRequest<T>> {
    public HeadRequest(String url) {
        super(url);
    }

    public HttpMethod getMethod() {
        return HttpMethod.HEAD;
    }

    public b0 generateRequest(c0 requestBody) {
        return generateRequestBuilder(requestBody).f().p(this.url).o(this.tag).b();
    }
}

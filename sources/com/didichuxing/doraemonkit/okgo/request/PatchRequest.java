package com.didichuxing.doraemonkit.okgo.request;

import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.request.base.BodyRequest;
import okhttp3.b0;
import okhttp3.c0;

public class PatchRequest<T> extends BodyRequest<T, PatchRequest<T>> {
    public PatchRequest(String url) {
        super(url);
    }

    public HttpMethod getMethod() {
        return HttpMethod.PATCH;
    }

    public b0 generateRequest(c0 requestBody) {
        return generateRequestBuilder(requestBody).j(requestBody).p(this.url).o(this.tag).b();
    }
}

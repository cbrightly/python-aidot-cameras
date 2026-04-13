package com.didichuxing.doraemonkit.okgo.request;

import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.request.base.BodyRequest;
import okhttp3.b0;
import okhttp3.c0;

public class PostRequest<T> extends BodyRequest<T, PostRequest<T>> {
    public PostRequest(String url) {
        super(url);
    }

    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    public b0 generateRequest(c0 requestBody) {
        return generateRequestBuilder(requestBody).k(requestBody).p(this.url).o(this.tag).b();
    }
}

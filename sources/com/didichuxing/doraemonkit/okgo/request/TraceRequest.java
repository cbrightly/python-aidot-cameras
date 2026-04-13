package com.didichuxing.doraemonkit.okgo.request;

import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.request.base.NoBodyRequest;
import okhttp3.b0;
import okhttp3.c0;

public class TraceRequest<T> extends NoBodyRequest<T, TraceRequest<T>> {
    public TraceRequest(String url) {
        super(url);
    }

    public HttpMethod getMethod() {
        return HttpMethod.TRACE;
    }

    public b0 generateRequest(c0 requestBody) {
        return generateRequestBuilder(requestBody).i("TRACE", requestBody).p(this.url).o(this.tag).b();
    }
}

package com.didichuxing.doraemonkit.okgo.request.base;

import com.didichuxing.doraemonkit.okgo.request.base.NoBodyRequest;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import okhttp3.b0;
import okhttp3.c0;

public abstract class NoBodyRequest<T, R extends NoBodyRequest> extends Request<T, R> {
    private static final long serialVersionUID = 1200621102761691196L;

    public NoBodyRequest(String url) {
        super(url);
    }

    public c0 generateRequestBody() {
        return null;
    }

    /* access modifiers changed from: protected */
    public b0.a generateRequestBuilder(c0 requestBody) {
        this.url = HttpUtils.createUrlFromParams(this.baseUrl, this.params.urlParamsMap);
        return HttpUtils.appendHeaders(new b0.a(), this.headers);
    }
}

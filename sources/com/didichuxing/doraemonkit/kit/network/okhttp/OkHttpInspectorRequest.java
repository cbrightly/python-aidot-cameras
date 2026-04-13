package com.didichuxing.doraemonkit.kit.network.okhttp;

import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import com.didichuxing.doraemonkit.kit.network.core.RequestBodyHelper;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import okhttp3.b0;
import okhttp3.c0;
import okio.d;
import okio.p;

public class OkHttpInspectorRequest implements NetworkInterpreter.InspectorRequest {
    private final b0 mRequest;
    private RequestBodyHelper mRequestBodyHelper;
    private final int mRequestId;

    public OkHttpInspectorRequest(int requestId, b0 request, RequestBodyHelper requestBodyHelper) {
        this.mRequestId = requestId;
        this.mRequest = request;
        this.mRequestBodyHelper = requestBodyHelper;
    }

    public int id() {
        return this.mRequestId;
    }

    public String url() {
        return this.mRequest.l().toString();
    }

    public String method() {
        return this.mRequest.h();
    }

    /* JADX INFO: finally extract failed */
    public byte[] body() {
        c0 body = this.mRequest.a();
        if (body == null) {
            return null;
        }
        d bufferedSink = p.c(p.h(this.mRequestBodyHelper.createBodySink(firstHeaderValue(HttpHeaders.HEAD_KEY_CONTENT_ENCODING))));
        try {
            body.writeTo(bufferedSink);
            bufferedSink.close();
            return this.mRequestBodyHelper.getDisplayBody();
        } catch (Throwable th) {
            bufferedSink.close();
            throw th;
        }
    }

    public int headerCount() {
        return this.mRequest.f().size();
    }

    public String headerName(int index) {
        return this.mRequest.f().b(index);
    }

    public String headerValue(int index) {
        return this.mRequest.f().h(index);
    }

    public String firstHeaderValue(String name) {
        return this.mRequest.d(name);
    }
}

package com.didichuxing.doraemonkit.kit.network.okhttp;

import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import okhttp3.b0;
import okhttp3.d0;

public class OkHttpInspectorResponse implements NetworkInterpreter.InspectorResponse {
    private final b0 mRequest;
    private final int mRequestId;
    private final d0 mResponse;

    public OkHttpInspectorResponse(int requestId, b0 request, d0 response) {
        this.mRequestId = requestId;
        this.mRequest = request;
        this.mResponse = response;
    }

    public int requestId() {
        return this.mRequestId;
    }

    public String url() {
        return this.mRequest.l().toString();
    }

    public int statusCode() {
        return this.mResponse.j();
    }

    public int headerCount() {
        return this.mResponse.s().size();
    }

    public String headerName(int index) {
        return this.mResponse.s().b(index);
    }

    public String headerValue(int index) {
        return this.mResponse.s().h(index);
    }

    public String firstHeaderValue(String name) {
        return this.mResponse.n(name);
    }
}

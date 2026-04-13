package com.didichuxing.doraemonkit.kit.network.common;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import java.util.List;

public class CommonInspectorRequest implements NetworkInterpreter.InspectorRequest {
    private final String body;
    private final CommonHeaders headers;
    private final int mRequestId;
    private final String method;
    private final String url;

    public CommonInspectorRequest(int requestId, @NonNull String url2, @NonNull String method2, @Nullable String body2, @Nullable CommonHeaders headers2) {
        this.mRequestId = requestId;
        this.url = url2;
        this.method = method2;
        this.body = body2;
        this.headers = headers2;
    }

    public int id() {
        return this.mRequestId;
    }

    public String url() {
        return this.url;
    }

    public String method() {
        return this.method;
    }

    public byte[] body() {
        if (TextUtils.isEmpty(this.body)) {
            return null;
        }
        return this.body.getBytes();
    }

    public int headerCount() {
        CommonHeaders commonHeaders = this.headers;
        if (commonHeaders != null) {
            return commonHeaders.size();
        }
        return 0;
    }

    public String headerName(int index) {
        CommonHeaders commonHeaders = this.headers;
        if (commonHeaders != null) {
            return commonHeaders.name(index);
        }
        return null;
    }

    public String headerValue(int index) {
        CommonHeaders commonHeaders = this.headers;
        if (commonHeaders != null) {
            return commonHeaders.value(index);
        }
        return null;
    }

    public String firstHeaderValue(String name) {
        List<String> values;
        CommonHeaders commonHeaders = this.headers;
        if (commonHeaders == null || (values = commonHeaders.values(name)) == null || values.size() <= 0) {
            return null;
        }
        return values.get(0);
    }
}

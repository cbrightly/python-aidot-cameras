package com.didichuxing.doraemonkit.kit.network.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import java.util.List;

public class CommonInspectorResponse implements NetworkInterpreter.InspectorResponse {
    private final CommonHeaders headers;
    private final int mRequestId;
    private final int statusCode;
    private final String url;

    public CommonInspectorResponse(int requestId, @NonNull String url2, int statusCode2, @Nullable CommonHeaders headers2) {
        this.mRequestId = requestId;
        this.url = url2;
        this.statusCode = statusCode2;
        this.headers = headers2;
    }

    public int requestId() {
        return this.mRequestId;
    }

    public String url() {
        return this.url;
    }

    public int statusCode() {
        return this.statusCode;
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

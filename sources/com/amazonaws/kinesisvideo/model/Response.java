package com.amazonaws.kinesisvideo.model;

import java.io.InputStream;
import java.util.Map;

public class Response {
    private final String responseBody;
    private final Map<String, String> responseHeaders;
    private final InputStream responsePayload;
    private final ResponseStatus responseStatus;

    Response(ResponseBuilder builder) {
        this.responseStatus = builder.responseStatus;
        this.responseHeaders = builder.responseHeaders;
        this.responseBody = builder.responseBody;
        this.responsePayload = builder.responsePayload;
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static class ResponseBuilder {
        /* access modifiers changed from: private */
        public String responseBody;
        /* access modifiers changed from: private */
        public Map<String, String> responseHeaders;
        /* access modifiers changed from: private */
        public InputStream responsePayload;
        /* access modifiers changed from: private */
        public ResponseStatus responseStatus;

        ResponseBuilder() {
        }

        public ResponseBuilder responseStatus(ResponseStatus responseStatus2) {
            this.responseStatus = responseStatus2;
            return this;
        }

        public ResponseBuilder responseHeaders(Map<String, String> responseHeaders2) {
            this.responseHeaders = responseHeaders2;
            return this;
        }

        public ResponseBuilder responseBody(String responseBody2) {
            this.responseBody = responseBody2;
            return this;
        }

        public ResponseBuilder responsePayload(InputStream responsePayload2) {
            this.responsePayload = responsePayload2;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }

    public String toString() {
        return "Response [responseStatus=" + this.responseStatus + ", responseHeaders=" + this.responseHeaders + ", super=" + super.toString() + "]";
    }

    public ResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    public Map<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public InputStream getResponsePayload() {
        return this.responsePayload;
    }
}

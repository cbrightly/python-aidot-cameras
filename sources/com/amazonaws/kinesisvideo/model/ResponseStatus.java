package com.amazonaws.kinesisvideo.model;

public class ResponseStatus {
    private final String protocol;
    private final String reason;
    private final int statusCode;

    ResponseStatus(ResponseStatusBuilder builder) {
        this.protocol = builder.protocol;
        this.statusCode = builder.statusCode;
        this.reason = builder.reason;
    }

    public static ResponseStatusBuilder builder() {
        return new ResponseStatusBuilder();
    }

    public static class ResponseStatusBuilder {
        /* access modifiers changed from: private */
        public String protocol;
        /* access modifiers changed from: private */
        public String reason;
        /* access modifiers changed from: private */
        public int statusCode;

        ResponseStatusBuilder() {
        }

        public ResponseStatusBuilder protocol(String protocol2) {
            this.protocol = protocol2;
            return this;
        }

        public ResponseStatusBuilder statusCode(int statusCode2) {
            this.statusCode = statusCode2;
            return this;
        }

        public ResponseStatusBuilder reason(String reason2) {
            this.reason = reason2;
            return this;
        }

        public ResponseStatus build() {
            return new ResponseStatus(this);
        }
    }

    public String toString() {
        return "ResponseStatus [protocol=" + this.protocol + ", statusCode=" + this.statusCode + ", reason=" + this.reason + ", super=" + super.toString() + "]";
    }

    public String getProtocol() {
        return this.protocol;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReason() {
        return this.reason;
    }
}

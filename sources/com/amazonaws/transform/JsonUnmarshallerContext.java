package com.amazonaws.transform;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.json.AwsJsonReader;

public class JsonUnmarshallerContext {
    private final HttpResponse httpResponse;
    private final AwsJsonReader reader;

    public JsonUnmarshallerContext(AwsJsonReader reader2) {
        this(reader2, (HttpResponse) null);
    }

    public JsonUnmarshallerContext(AwsJsonReader reader2, HttpResponse httpResponse2) {
        this.reader = reader2;
        this.httpResponse = httpResponse2;
    }

    public AwsJsonReader getReader() {
        return this.reader;
    }

    public String getHeader(String header) {
        HttpResponse httpResponse2 = this.httpResponse;
        if (httpResponse2 == null) {
            return null;
        }
        return httpResponse2.getHeaders().get(header);
    }

    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }
}

package com.amazonaws.handlers;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.TimingInfo;

public final class RequestHandler2Adaptor extends RequestHandler2 {
    private final RequestHandler old;

    RequestHandler2Adaptor(RequestHandler old2) {
        if (old2 != null) {
            this.old = old2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void beforeRequest(Request<?> request) {
        this.old.beforeRequest(request);
    }

    public void afterResponse(Request<?> request, Response<?> response) {
        AWSRequestMetrics awsRequestMetrics;
        Object awsResponse;
        TimingInfo timingInfo = null;
        if (request == null) {
            awsRequestMetrics = null;
        } else {
            awsRequestMetrics = request.getAWSRequestMetrics();
        }
        if (response == null) {
            awsResponse = null;
        } else {
            awsResponse = response.getAwsResponse();
        }
        if (awsRequestMetrics != null) {
            timingInfo = awsRequestMetrics.getTimingInfo();
        }
        this.old.afterResponse(request, awsResponse, timingInfo);
    }

    public void afterError(Request<?> request, Response<?> response, Exception e) {
        this.old.afterError(request, e);
    }

    public int hashCode() {
        return this.old.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof RequestHandler2Adaptor)) {
            return false;
        }
        return this.old.equals(((RequestHandler2Adaptor) o).old);
    }
}

package com.amazonaws.http.protocol;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.util.AWSRequestMetrics;
import org.apache.http.h;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.protocol.j;
import org.apache.http.q;

public class SdkHttpRequestExecutor extends j {
    /* access modifiers changed from: protected */
    public q doSendRequest(o request, h conn, f context) {
        AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
        if (awsRequestMetrics == null) {
            return super.doSendRequest(request, conn, context);
        }
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.HttpClientSendRequestTime;
        awsRequestMetrics.startEvent((MetricType) field);
        try {
            q doSendRequest = super.doSendRequest(request, conn, context);
            awsRequestMetrics.endEvent((MetricType) field);
            return doSendRequest;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.HttpClientSendRequestTime);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public q doReceiveResponse(o request, h conn, f context) {
        AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
        if (awsRequestMetrics == null) {
            return super.doReceiveResponse(request, conn, context);
        }
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.HttpClientReceiveResponseTime;
        awsRequestMetrics.startEvent((MetricType) field);
        try {
            q doReceiveResponse = super.doReceiveResponse(request, conn, context);
            awsRequestMetrics.endEvent((MetricType) field);
            return doReceiveResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.HttpClientReceiveResponseTime);
            throw th;
        }
    }
}

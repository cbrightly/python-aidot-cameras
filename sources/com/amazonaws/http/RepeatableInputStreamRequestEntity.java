package com.amazonaws.http;

import com.amazonaws.Request;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.MetricInputStreamEntity;
import com.amazonaws.metrics.ServiceMetricType;
import com.amazonaws.metrics.ThroughputMetricType;
import com.amazonaws.metrics.internal.ServiceMetricTypeGuesser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.b;
import org.apache.http.entity.h;

public class RepeatableInputStreamRequestEntity extends b {
    private static final Log log = LogFactory.getLog(AmazonHttpClient.class);
    private InputStream content;
    private boolean firstAttempt = true;
    private h inputStreamRequestEntity;
    private IOException originalException;

    RepeatableInputStreamRequestEntity(Request<?> request) {
        setChunked(false);
        long contentLength = -1;
        try {
            String contentLengthString = request.getHeaders().get("Content-Length");
            if (contentLengthString != null) {
                contentLength = Long.parseLong(contentLengthString);
            }
        } catch (NumberFormatException e) {
            log.warn("Unable to parse content length from request.  Buffering contents in memory.");
        }
        String contentType = request.getHeaders().get("Content-Type");
        ThroughputMetricType type = ServiceMetricTypeGuesser.guessThroughputMetricType(request, ServiceMetricType.UPLOAD_THROUGHPUT_NAME_SUFFIX, ServiceMetricType.UPLOAD_BYTE_COUNT_NAME_SUFFIX);
        if (type == null) {
            this.inputStreamRequestEntity = new h(request.getContent(), contentLength);
        } else {
            this.inputStreamRequestEntity = new MetricInputStreamEntity(type, request.getContent(), contentLength);
        }
        this.inputStreamRequestEntity.setContentType(contentType);
        InputStream content2 = request.getContent();
        this.content = content2;
        setContent(content2);
        setContentType(contentType);
        setContentLength(contentLength);
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return this.content.markSupported() || this.inputStreamRequestEntity.isRepeatable();
    }

    public void writeTo(OutputStream output) {
        try {
            if (!this.firstAttempt && isRepeatable()) {
                this.content.reset();
            }
            this.firstAttempt = false;
            this.inputStreamRequestEntity.writeTo(output);
        } catch (IOException ioe) {
            if (this.originalException == null) {
                this.originalException = ioe;
            }
            throw this.originalException;
        }
    }
}

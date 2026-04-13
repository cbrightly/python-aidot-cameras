package com.amazonaws.http;

import com.amazonaws.internal.SdkInputStream;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.methods.a;
import org.apache.http.k;

public class HttpMethodReleaseInputStream extends SdkInputStream {
    private static final Log log = LogFactory.getLog(HttpMethodReleaseInputStream.class);
    private boolean alreadyReleased;
    private k httpRequest;
    private InputStream in;
    private boolean underlyingStreamConsumed;

    public HttpMethodReleaseInputStream(k httpMethod) {
        this.httpRequest = httpMethod;
        try {
            this.in = httpMethod.a().getContent();
        } catch (IOException e) {
            Log log2 = log;
            if (log2.isWarnEnabled()) {
                log2.warn("Unable to obtain HttpMethod's response data stream", e);
            }
            try {
                httpMethod.a().getContent().close();
            } catch (Exception e2) {
            }
            this.in = new ByteArrayInputStream(new byte[0]);
        }
    }

    public k getHttpRequest() {
        return this.httpRequest;
    }

    /* access modifiers changed from: protected */
    public void releaseConnection() {
        if (!this.alreadyReleased) {
            if (!this.underlyingStreamConsumed) {
                k kVar = this.httpRequest;
                if (kVar instanceof a) {
                    ((a) kVar).b();
                }
            }
            this.in.close();
            this.alreadyReleased = true;
        }
    }

    public int read() {
        try {
            int read = this.in.read();
            if (read == -1) {
                this.underlyingStreamConsumed = true;
                if (!this.alreadyReleased) {
                    releaseConnection();
                    Log log2 = log;
                    if (log2.isDebugEnabled()) {
                        log2.debug("Released HttpMethod as its response data stream is fully consumed");
                    }
                }
            }
            return read;
        } catch (IOException e) {
            releaseConnection();
            Log log3 = log;
            if (log3.isDebugEnabled()) {
                log3.debug("Released HttpMethod as its response data stream threw an exception", e);
            }
            throw e;
        }
    }

    public int read(byte[] b, int off, int len) {
        try {
            int read = this.in.read(b, off, len);
            if (read == -1) {
                this.underlyingStreamConsumed = true;
                if (!this.alreadyReleased) {
                    releaseConnection();
                    Log log2 = log;
                    if (log2.isDebugEnabled()) {
                        log2.debug("Released HttpMethod as its response data stream is fully consumed");
                    }
                }
            }
            return read;
        } catch (IOException e) {
            releaseConnection();
            Log log3 = log;
            if (log3.isDebugEnabled()) {
                log3.debug("Released HttpMethod as its response data stream threw an exception", e);
            }
            throw e;
        }
    }

    public int available() {
        try {
            return this.in.available();
        } catch (IOException e) {
            releaseConnection();
            Log log2 = log;
            if (log2.isDebugEnabled()) {
                log2.debug("Released HttpMethod as its response data stream threw an exception", e);
            }
            throw e;
        }
    }

    public void close() {
        if (!this.alreadyReleased) {
            releaseConnection();
            Log log2 = log;
            if (log2.isDebugEnabled()) {
                log2.debug("Released HttpMethod as its response data stream is closed");
            }
        }
        this.in.close();
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (!this.alreadyReleased) {
            Log log2 = log;
            if (log2.isWarnEnabled()) {
                log2.warn("Attempting to release HttpMethod in finalize() as its response data stream has gone out of scope. This attempt will not always succeed and cannot be relied upon! Please ensure S3 response data streams are always fully consumed or closed to avoid HTTP connection starvation.");
            }
            releaseConnection();
            if (log2.isWarnEnabled()) {
                log2.warn("Successfully released HttpMethod in finalize(). You were lucky this time... Please ensure S3 response data streams are always fully consumed or closed.");
            }
        }
        super.finalize();
    }

    /* access modifiers changed from: protected */
    public InputStream getWrappedInputStream() {
        return this.in;
    }
}

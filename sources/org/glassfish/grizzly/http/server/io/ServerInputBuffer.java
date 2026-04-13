package org.glassfish.grizzly.http.server.io;

import java.io.IOException;
import java.util.concurrent.Executor;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpBrokenContent;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.server.Request;

public class ServerInputBuffer extends InputBuffer {
    private volatile Request serverRequest;
    private volatile long totalReadContentInBytes;

    public void initialize(Request serverRequest2, FilterChainContext ctx) {
        this.serverRequest = serverRequest2;
        super.initialize(serverRequest2.getRequest(), ctx);
    }

    public void initiateAsyncronousDataReceiving() {
        if (!checkChunkedMaxPostSize()) {
            try {
                append(HttpBrokenContent.builder(this.serverRequest.getRequest()).error(new IOException("The HTTP request content exceeds max post size")).build());
            } catch (IOException e) {
            }
        } else {
            super.initiateAsyncronousDataReceiving();
        }
    }

    /* access modifiers changed from: protected */
    public HttpContent blockingRead() {
        if (checkChunkedMaxPostSize()) {
            return super.blockingRead();
        }
        throw new IOException("The HTTP request content exceeds max post size");
    }

    /* access modifiers changed from: protected */
    public void updateInputContentBuffer(Buffer buffer) {
        this.totalReadContentInBytes += (long) buffer.remaining();
        super.updateInputContentBuffer(buffer);
    }

    public void recycle() {
        this.serverRequest = null;
        this.totalReadContentInBytes = 0;
        super.recycle();
    }

    /* access modifiers changed from: protected */
    public Executor getThreadPool() {
        return this.serverRequest.getRequestExecutor();
    }

    private boolean checkChunkedMaxPostSize() {
        if (!this.serverRequest.getRequest().isChunked()) {
            return true;
        }
        long maxPostSize = this.serverRequest.getHttpFilter().getConfiguration().getMaxPostSize();
        if (maxPostSize < 0 || maxPostSize > this.totalReadContentInBytes) {
            return true;
        }
        return false;
    }
}

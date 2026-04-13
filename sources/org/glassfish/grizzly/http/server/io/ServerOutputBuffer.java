package org.glassfish.grizzly.http.server.io;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.io.OutputBuffer;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.localization.LogMessages;

public class ServerOutputBuffer extends OutputBuffer {
    /* access modifiers changed from: private */
    public Response serverResponse;

    public void initialize(Response response, FilterChainContext ctx) {
        super.initialize(response.getResponse(), response.isSendFileEnabled(), ctx);
        this.serverResponse = response;
    }

    public void sendfile(File file, long offset, long length, CompletionHandler<WriteResult> handler) {
        CompletionHandler<WriteResult> ch;
        if (this.sendfileEnabled) {
            boolean suspendedAtStart = this.serverResponse.isSuspended();
            if (suspendedAtStart && handler != null) {
                ch = handler;
            } else if (suspendedAtStart || handler == null) {
                ch = createInternalCompletionHandler(file, suspendedAtStart);
            } else {
                ch = suspendAndCreateHandler(handler);
            }
            super.sendfile(file, offset, length, ch);
            return;
        }
        throw new IllegalStateException("sendfile support isn't available.");
    }

    public void recycle() {
        this.serverResponse = null;
        super.recycle();
    }

    /* access modifiers changed from: protected */
    public Executor getThreadPool() {
        return this.serverResponse.getRequest().getRequestExecutor();
    }

    private CompletionHandler<WriteResult> createInternalCompletionHandler(final File file, boolean suspendedAtStart) {
        if (!suspendedAtStart) {
            this.serverResponse.suspend();
        }
        return new CompletionHandler<WriteResult>() {
            public void cancelled() {
                Logger access$000 = OutputBuffer.LOGGER;
                Level level = Level.WARNING;
                if (access$000.isLoggable(level)) {
                    OutputBuffer.LOGGER.log(level, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_CANCELLED(file.getAbsolutePath()));
                }
                ServerOutputBuffer.this.serverResponse.resume();
            }

            public void failed(Throwable throwable) {
                Logger access$300 = OutputBuffer.LOGGER;
                Level level = Level.SEVERE;
                if (access$300.isLoggable(level)) {
                    OutputBuffer.LOGGER.log(level, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_FAILED(file.getAbsolutePath(), throwable.getMessage()), throwable);
                }
                ServerOutputBuffer.this.serverResponse.resume();
            }

            public void completed(WriteResult result) {
                ServerOutputBuffer.this.serverResponse.resume();
            }

            public void updated(WriteResult result) {
            }
        };
    }

    private CompletionHandler<WriteResult> suspendAndCreateHandler(final CompletionHandler<WriteResult> handler) {
        this.serverResponse.suspend();
        return new CompletionHandler<WriteResult>() {
            public void cancelled() {
                handler.cancelled();
                ServerOutputBuffer.this.serverResponse.resume();
            }

            public void failed(Throwable throwable) {
                handler.failed(throwable);
                ServerOutputBuffer.this.serverResponse.resume();
            }

            public void completed(WriteResult result) {
                handler.completed(result);
                ServerOutputBuffer.this.serverResponse.resume();
            }

            public void updated(WriteResult result) {
                handler.updated(result);
            }
        };
    }
}

package org.glassfish.grizzly.http.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.OutputSink;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.server.filecache.FileCacheEntry;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.memory.Buffers;

public class FileCacheFilter extends BaseFilter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(FileCacheFilter.class);
    private final FileCache fileCache;

    public FileCacheFilter(FileCache fileCache2) {
        this.fileCache = fileCache2;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        FileCacheEntry cacheEntry;
        HttpRequestPacket request = (HttpRequestPacket) ((HttpContent) ctx.getMessage()).getHttpHeader();
        if (!this.fileCache.isEnabled() || !Method.GET.equals(request.getMethod()) || (cacheEntry = this.fileCache.get(request)) == null) {
            return ctx.getInvokeAction();
        }
        HttpResponsePacket response = request.getResponse();
        prepareResponse(cacheEntry, response);
        if (response.getStatus() != 200) {
            ctx.write(HttpContent.builder(response).content(Buffers.EMPTY_BUFFER).last(true).build());
            return flush(ctx);
        }
        boolean isServeCompressed = cacheEntry.canServeCompressed(request);
        prepareResponseWithPayload(cacheEntry, response, isServeCompressed);
        if (cacheEntry.type != FileCache.CacheType.FILE) {
            ctx.write(HttpContent.builder(response).content(Buffers.wrap(ctx.getMemoryManager(), cacheEntry.getByteBuffer(isServeCompressed).duplicate())).last(true).build());
            return flush(ctx);
        } else if (!this.fileCache.isFileSendEnabled() || request.isSecure()) {
            return sendFileUsingBuffers(ctx, response, cacheEntry, isServeCompressed);
        } else {
            return sendFileZeroCopy(ctx, response, cacheEntry, isServeCompressed);
        }
    }

    public FileCache getFileCache() {
        return this.fileCache;
    }

    private void prepareResponse(FileCacheEntry entry, HttpResponsePacket response) {
        response.setContentType(entry.contentType.prepare());
        String str = entry.server;
        if (str != null) {
            response.addHeader(Header.Server, str);
        }
    }

    private void prepareResponseWithPayload(FileCacheEntry entry, HttpResponsePacket response, boolean isServeCompressed) {
        response.addHeader(Header.ETag, entry.Etag);
        response.addHeader(Header.LastModified, entry.lastModifiedHeader);
        response.setContentLengthLong(entry.getFileSize(isServeCompressed));
        if (isServeCompressed) {
            response.addHeader(Header.ContentEncoding, GZipContentEncoding.NAME);
        }
    }

    private NextAction sendFileUsingBuffers(FilterChainContext ctx, HttpResponsePacket response, FileCacheEntry cacheEntry, boolean isServeCompressed) {
        try {
            FileSendEntry sendEntry = FileSendEntry.create(ctx, response, cacheEntry.getFile(isServeCompressed), cacheEntry.getFileSize(isServeCompressed));
            ctx.suspend();
            sendEntry.send();
            return ctx.getSuspendAction();
        } catch (IOException e) {
            return ctx.getInvokeAction();
        }
    }

    private NextAction sendFileZeroCopy(FilterChainContext ctx, HttpResponsePacket response, final FileCacheEntry cacheEntry, boolean isServeCompressed) {
        ctx.write(response);
        ctx.write((Object) new FileTransfer(cacheEntry.getFile(isServeCompressed), 0, cacheEntry.getFileSize(isServeCompressed)), (CompletionHandler<WriteResult>) new EmptyCompletionHandler<WriteResult>() {
            public void failed(Throwable throwable) {
                Logger access$100 = FileCacheFilter.LOGGER;
                Level level = Level.FINE;
                access$100.log(level, "Error reported during file-send entry: " + cacheEntry, throwable);
            }
        });
        return flush(ctx);
    }

    private NextAction flush(final FilterChainContext ctx) {
        HttpContext httpContext = HttpContext.get(ctx);
        if (httpContext != null) {
            OutputSink output = httpContext.getOutputSink();
            if (output.canWrite()) {
                return ctx.getStopAction();
            }
            NextAction suspendAction = ctx.getSuspendAction();
            ctx.suspend();
            output.notifyCanWrite(new WriteHandler() {
                public void onWritePossible() {
                    finish();
                }

                public void onError(Throwable t) {
                    finish();
                }

                private void finish() {
                    ctx.completeAndRecycle();
                }
            });
            return suspendAction;
        }
        throw new AssertionError();
    }

    public static class FileSendEntry implements WriteHandler {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final FilterChainContext ctx;
        private final FileChannel fc;
        private final FileInputStream fis;
        private final OutputSink output;
        private long remaining;
        private final HttpResponsePacket response;

        public static FileSendEntry create(FilterChainContext ctx2, HttpResponsePacket response2, File file, long size) {
            FileInputStream fis2 = new FileInputStream(file);
            return new FileSendEntry(ctx2, response2, fis2, fis2.getChannel(), size);
        }

        public FileSendEntry(FilterChainContext ctx2, HttpResponsePacket response2, FileInputStream fis2, FileChannel fc2, long size) {
            this.ctx = ctx2;
            this.response = response2;
            this.fis = fis2;
            this.fc = fc2;
            this.remaining = size;
            HttpContext httpContext = response2.getProcessingState().getHttpContext();
            if (httpContext != null) {
                this.output = httpContext.getOutputSink();
                return;
            }
            throw new AssertionError();
        }

        public void close() {
            try {
                this.fis.close();
            } catch (IOException e) {
            }
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x004f A[Catch:{ IOException -> 0x0059 }] */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0053 A[Catch:{ IOException -> 0x0059 }] */
        /* JADX WARNING: Removed duplicated region for block: B:4:0x001e A[Catch:{ IOException -> 0x0059 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void send() {
            /*
                r9 = this;
                r0 = 8192(0x2000, float:1.14794E-41)
            L_0x0002:
                org.glassfish.grizzly.filterchain.FilterChainContext r1 = r9.ctx     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.memory.MemoryManager r1 = r1.getMemoryManager()     // Catch:{ IOException -> 0x0059 }
                r2 = 8192(0x2000, float:1.14794E-41)
                org.glassfish.grizzly.Buffer r1 = r1.allocate(r2)     // Catch:{ IOException -> 0x0059 }
                r2 = 1
                r1.allowBufferDispose(r2)     // Catch:{ IOException -> 0x0059 }
                java.nio.channels.FileChannel r3 = r9.fc     // Catch:{ IOException -> 0x0059 }
                long r3 = org.glassfish.grizzly.memory.Buffers.readFromFileChannel(r3, r1)     // Catch:{ IOException -> 0x0059 }
                r5 = 0
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 <= 0) goto L_0x0029
                long r7 = r9.remaining     // Catch:{ IOException -> 0x0059 }
                long r7 = r7 - r3
                r9.remaining = r7     // Catch:{ IOException -> 0x0059 }
                int r5 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
                if (r5 > 0) goto L_0x0028
                goto L_0x0029
            L_0x0028:
                r2 = 0
            L_0x0029:
                r1.trim()     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.filterchain.FilterChainContext r5 = r9.ctx     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.http.HttpResponsePacket r6 = r9.response     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.http.HttpContent$Builder r6 = org.glassfish.grizzly.http.HttpContent.builder(r6)     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.http.HttpContent$Builder r6 = r6.content(r1)     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.http.HttpContent$Builder r6 = r6.last(r2)     // Catch:{ IOException -> 0x0059 }
                org.glassfish.grizzly.http.HttpContent r6 = r6.build()     // Catch:{ IOException -> 0x0059 }
                r5.write(r6)     // Catch:{ IOException -> 0x0059 }
                if (r2 != 0) goto L_0x004d
                org.glassfish.grizzly.OutputSink r1 = r9.output     // Catch:{ IOException -> 0x0059 }
                boolean r1 = r1.canWrite()     // Catch:{ IOException -> 0x0059 }
                if (r1 != 0) goto L_0x0002
            L_0x004d:
                if (r2 == 0) goto L_0x0053
                r9.done()     // Catch:{ IOException -> 0x0059 }
                goto L_0x0058
            L_0x0053:
                org.glassfish.grizzly.OutputSink r1 = r9.output     // Catch:{ IOException -> 0x0059 }
                r1.notifyCanWrite(r9)     // Catch:{ IOException -> 0x0059 }
            L_0x0058:
                goto L_0x005d
            L_0x0059:
                r1 = move-exception
                r9.done()
            L_0x005d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.FileCacheFilter.FileSendEntry.send():void");
        }

        private void done() {
            close();
            FilterChainContext filterChainContext = this.ctx;
            filterChainContext.resume(filterChainContext.getStopAction());
        }

        public void onWritePossible() {
            send();
        }

        public void onError(Throwable t) {
            done();
        }
    }
}

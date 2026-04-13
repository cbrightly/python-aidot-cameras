package org.glassfish.grizzly.http.server;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeType;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public abstract class StaticHttpHandlerBase extends HttpHandler {
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(StaticHttpHandlerBase.class);
    private volatile int fileCacheFilterIdx = -1;
    private volatile boolean isFileCacheEnabled = true;

    /* access modifiers changed from: protected */
    public abstract boolean handle(String str, Request request, Response response);

    public boolean isFileCacheEnabled() {
        return this.isFileCacheEnabled;
    }

    public void setFileCacheEnabled(boolean isFileCacheEnabled2) {
        this.isFileCacheEnabled = isFileCacheEnabled2;
    }

    public static void sendFile(Response response, File file) {
        response.setStatus(HttpStatus.OK_200);
        pickupContentType(response, file.getPath());
        response.setContentLengthLong(file.length());
        response.addDateHeader(Header.Date, System.currentTimeMillis());
        if (!response.isSendFileEnabled() || response.getRequest().isSecure()) {
            sendUsingBuffers(response, file);
        } else {
            sendZeroCopy(response, file);
        }
    }

    private static void sendUsingBuffers(Response response, File file) {
        response.suspend();
        NIOOutputStream outputStream = response.getNIOOutputStream();
        outputStream.notifyCanWrite(new NonBlockingDownloadHandler(response, outputStream, file, 8192));
    }

    private static void sendZeroCopy(Response response, File file) {
        response.getOutputBuffer().sendfile(file, (CompletionHandler<WriteResult>) null);
    }

    public final boolean addToFileCache(Request req, Response res, File resource) {
        FileCacheFilter fileCacheFilter;
        if (!this.isFileCacheEnabled || (fileCacheFilter = lookupFileCache(req.getContext())) == null) {
            return false;
        }
        FileCache fileCache = fileCacheFilter.getFileCache();
        if (!fileCache.isEnabled()) {
            return false;
        }
        if (res != null) {
            addCachingHeaders(res, resource);
        }
        fileCache.add(req.getRequest(), resource);
        return true;
    }

    public void service(Request request, Response response) {
        String uri = getRelativeURI(request);
        if (uri == null || !handle(uri, request, response)) {
            onMissingResource(request, response);
        }
    }

    /* access modifiers changed from: protected */
    public String getRelativeURI(Request request) {
        String uri = request.getDecodedRequestURI();
        if (uri.contains("..")) {
            return null;
        }
        String resourcesContextPath = request.getContextPath();
        if (resourcesContextPath == null || resourcesContextPath.isEmpty()) {
            return uri;
        }
        if (!uri.startsWith(resourcesContextPath)) {
            return null;
        }
        return uri.substring(resourcesContextPath.length());
    }

    /* access modifiers changed from: protected */
    public void onMissingResource(Request request, Response response) {
        response.sendError(404);
    }

    /* access modifiers changed from: protected */
    public FileCacheFilter lookupFileCache(FilterChainContext fcContext) {
        FilterChain fc = fcContext.getFilterChain();
        int lastFileCacheIdx = this.fileCacheFilterIdx;
        if (lastFileCacheIdx != -1 && lastFileCacheIdx < fc.size()) {
            Filter filter = (Filter) fc.get(lastFileCacheIdx);
            if (filter instanceof FileCacheFilter) {
                return (FileCacheFilter) filter;
            }
        }
        int size = fc.size();
        for (int i = 0; i < size; i++) {
            Filter filter2 = (Filter) fc.get(i);
            if (filter2 instanceof FileCacheFilter) {
                this.fileCacheFilterIdx = i;
                return (FileCacheFilter) filter2;
            }
        }
        this.fileCacheFilterIdx = -1;
        return null;
    }

    protected static void pickupContentType(Response response, String path) {
        if (!response.getResponse().isContentTypeSet()) {
            int dot = path.lastIndexOf(46);
            if (dot > 0) {
                String ct = MimeType.get(path.substring(dot + 1));
                if (ct != null) {
                    response.setContentType(ct);
                    return;
                }
                return;
            }
            response.setContentType(MimeType.get("html"));
        }
    }

    protected static void addCachingHeaders(Response response, File file) {
        StringBuilder sb = new StringBuilder();
        long fileLength = file.length();
        long lastModified = file.lastModified();
        if (fileLength >= 0 || lastModified >= 0) {
            sb.append(StringUtil.DOUBLE_QUOTE);
            sb.append(fileLength);
            sb.append('-');
            sb.append(lastModified);
            sb.append(StringUtil.DOUBLE_QUOTE);
            response.setHeader(Header.ETag, sb.toString());
        }
        response.addDateHeader(Header.LastModified, lastModified);
    }

    public static class NonBlockingDownloadHandler implements WriteHandler {
        private final int chunkSize;
        private final FileChannel fileChannel;
        private final MemoryManager mm;
        private final NIOOutputStream outputStream;
        private final Response response;
        private volatile long size;

        NonBlockingDownloadHandler(Response response2, NIOOutputStream outputStream2, File file, int chunkSize2) {
            try {
                this.fileChannel = new FileInputStream(file).getChannel();
                this.size = file.length();
                this.response = response2;
                this.outputStream = outputStream2;
                this.mm = response2.getRequest().getContext().getMemoryManager();
                this.chunkSize = chunkSize2;
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("File should have existed", e);
            }
        }

        public void onWritePossible() {
            StaticHttpHandlerBase.LOGGER.log(Level.FINE, "[onWritePossible]");
            if (sendChunk()) {
                this.outputStream.notifyCanWrite(this);
            }
        }

        public void onError(Throwable t) {
            StaticHttpHandlerBase.LOGGER.log(Level.FINE, "[onError] ", t);
            this.response.setStatus(500, t.getMessage());
            complete(true);
        }

        private boolean sendChunk() {
            Buffer buffer = this.mm.allocate(this.chunkSize);
            buffer.allowBufferDispose(true);
            int justReadBytes = (int) Buffers.readFromFileChannel(this.fileChannel, buffer);
            if (justReadBytes <= 0) {
                complete(false);
                return false;
            }
            buffer.trim();
            this.outputStream.write(buffer);
            this.size -= (long) justReadBytes;
            if (this.size > 0) {
                return true;
            }
            complete(false);
            return false;
        }

        private void complete(boolean isError) {
            try {
                this.fileChannel.close();
            } catch (IOException e) {
                if (!isError) {
                    this.response.setStatus(500, e.getMessage());
                }
            }
            try {
                this.outputStream.close();
            } catch (IOException e2) {
                if (!isError) {
                    this.response.setStatus(500, e2.getMessage());
                }
            }
            if (this.response.isSuspended()) {
                this.response.resume();
            } else {
                this.response.finish();
            }
        }
    }
}

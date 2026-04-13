package org.glassfish.grizzly.http.server.filecache;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.util.ContentType;

public final class FileCacheEntry implements Runnable {
    private static final Logger LOGGER = Grizzly.logger(FileCacheEntry.class);
    public String Etag;
    ByteBuffer bb;
    private boolean canBeCompressed;
    ByteBuffer compressedBb;
    volatile File compressedFile;
    long compressedFileSize = -1;
    public ContentType contentType;
    public String date;
    private final FileCache fileCache;
    public String host;
    private AtomicBoolean isCompressed;
    public FileCacheKey key;
    public long lastModified = -1;
    public String lastModifiedHeader;
    File plainFile;
    long plainFileSize = -1;
    public String requestURI;
    public String server;
    public volatile long timeoutMillis;
    public FileCache.CacheType type;
    public String xPoweredBy;

    public FileCacheEntry(FileCache fileCache2) {
        this.fileCache = fileCache2;
    }

    /* access modifiers changed from: package-private */
    public void setCanBeCompressed(boolean canBeCompressed2) {
        this.canBeCompressed = canBeCompressed2;
        if (canBeCompressed2) {
            this.isCompressed = new AtomicBoolean();
        }
    }

    public boolean canServeCompressed(HttpRequestPacket request) {
        if (!this.canBeCompressed || !CompressionConfig.isClientSupportCompression(this.fileCache.getCompressionConfig(), request, FileCache.COMPRESSION_ALIASES)) {
            return false;
        }
        if (this.isCompressed.compareAndSet(false, true)) {
            this.fileCache.compressFile(this);
        }
        if (this.compressedFile != null) {
            return true;
        }
        return false;
    }

    public long getFileSize(boolean isCompressed2) {
        return isCompressed2 ? this.compressedFileSize : this.plainFileSize;
    }

    public File getFile(boolean isCompressed2) {
        return isCompressed2 ? this.compressedFile : this.plainFile;
    }

    public ByteBuffer getByteBuffer(boolean isCompressed2) {
        return isCompressed2 ? this.compressedBb : this.bb;
    }

    public void run() {
        this.fileCache.remove(this);
    }

    public String toString() {
        return "FileCacheEntry" + "{host='" + this.host + '\'' + ", requestURI='" + this.requestURI + '\'' + ", lastModified=" + this.lastModified + ", contentType='" + this.contentType + '\'' + ", type=" + this.type + ", plainFileSize=" + this.plainFileSize + ", canBeCompressed=" + this.canBeCompressed + ", compressedFileSize=" + this.compressedFileSize + ", timeoutMillis=" + this.timeoutMillis + ", fileCache=" + this.fileCache + ", server=" + this.server + '}';
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.compressedFile != null && !this.compressedFile.delete()) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (logger.isLoggable(level)) {
                logger.log(level, "Unable to delete file {0}.  Will try to delete again upon VM exit.", this.compressedFile.getCanonicalPath());
            }
            this.compressedFile.deleteOnExit();
        }
        super.finalize();
    }
}

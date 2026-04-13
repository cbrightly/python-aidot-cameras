package org.glassfish.grizzly.http.server.filecache;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.util.SimpleDateFormats;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.slf4j.e;

public class FileCache implements MonitoringAware<FileCacheProbe> {
    static final String[] COMPRESSION_ALIASES = {GZipContentEncoding.NAME};
    private static final Logger LOGGER = Grizzly.logger(FileCache.class);
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));
    private final FileCacheEntry NULL_CACHE_ENTRY = new FileCacheEntry(this);
    private final AtomicInteger cacheSize = new AtomicInteger();
    private volatile File compressedFilesFolder = TMP_DIR;
    private final CompressionConfig compressionConfig = new CompressionConfig();
    private DelayedExecutor.DelayQueue<FileCacheEntry> delayQueue;
    private boolean enabled = true;
    private final ConcurrentMap<FileCacheKey, FileCacheEntry> fileCacheMap = new ConcurrentHashMap();
    private boolean fileSendEnabled;
    private final AtomicLong heapSize = new AtomicLong();
    private final AtomicLong mappedMemorySize = new AtomicLong();
    private volatile int maxCacheEntries = 1024;
    private long maxEntrySize = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
    private volatile long maxLargeFileCacheSize = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
    private volatile long maxSmallFileCacheSize = 1048576;
    private long minEntrySize = Long.MIN_VALUE;
    protected final DefaultMonitoringConfig<FileCacheProbe> monitoringConfig = new DefaultMonitoringConfig<FileCacheProbe>(FileCacheProbe.class) {
        public Object createManagementObject() {
            return FileCache.this.createJmxManagementObject();
        }
    };
    private int secondsMaxAge = -1;

    public enum CacheResult {
        OK_CACHED,
        OK_CACHED_TIMESTAMP,
        FAILED_CACHE_FULL,
        FAILED_ENTRY_EXISTS,
        FAILED
    }

    public enum CacheType {
        HEAP,
        MAPPED,
        FILE,
        TIMESTAMP
    }

    public void initialize(DelayedExecutor delayedExecutor) {
        this.delayQueue = delayedExecutor.createDelayQueue(new EntryWorker(), new EntryResolver());
    }

    public CacheResult add(HttpRequestPacket request, long lastModified) {
        return add(request, (File) null, lastModified);
    }

    public CacheResult add(HttpRequestPacket request, File cacheFile) {
        return add(request, cacheFile, cacheFile.lastModified());
    }

    /* access modifiers changed from: protected */
    public CacheResult add(HttpRequestPacket request, File cacheFile, long lastModified) {
        FileCacheEntry entry;
        File file = cacheFile;
        String requestURI = request.getRequestURI();
        if (requestURI == null) {
            return CacheResult.FAILED;
        }
        String host = request.getHeader(Header.Host);
        FileCacheKey key = new FileCacheKey(host, requestURI);
        if (this.fileCacheMap.putIfAbsent(key, this.NULL_CACHE_ENTRY) != null) {
            key.recycle();
            return CacheResult.FAILED_ENTRY_EXISTS;
        } else if (this.cacheSize.incrementAndGet() > getMaxCacheEntries()) {
            this.cacheSize.decrementAndGet();
            this.fileCacheMap.remove(key);
            key.recycle();
            return CacheResult.FAILED_CACHE_FULL;
        } else {
            HttpResponsePacket response = request.getResponse();
            MimeHeaders headers = response.getHeaders();
            String contentType = response.getContentType();
            if (file != null) {
                entry = createEntry(file);
                entry.setCanBeCompressed(canBeCompressed(file, contentType));
            } else {
                entry = new FileCacheEntry(this);
                entry.type = CacheType.TIMESTAMP;
            }
            entry.key = key;
            entry.requestURI = requestURI;
            entry.lastModified = lastModified;
            entry.contentType = ContentType.newContentType(contentType);
            entry.xPoweredBy = headers.getHeader(Header.XPoweredBy);
            entry.date = headers.getHeader(Header.Date);
            entry.lastModifiedHeader = headers.getHeader(Header.LastModified);
            entry.host = host;
            entry.Etag = headers.getHeader(Header.ETag);
            entry.server = headers.getHeader(Header.Server);
            this.fileCacheMap.put(key, entry);
            notifyProbesEntryAdded(this, entry);
            int secondsMaxAgeLocal = getSecondsMaxAge();
            if (secondsMaxAgeLocal > 0) {
                this.delayQueue.add(entry, (long) secondsMaxAgeLocal, TimeUnit.SECONDS);
            }
            return entry.type == CacheType.TIMESTAMP ? CacheResult.OK_CACHED_TIMESTAMP : CacheResult.OK_CACHED;
        }
    }

    public FileCacheEntry get(HttpRequestPacket request) {
        if (this.cacheSize.get() == 0) {
            return null;
        }
        LazyFileCacheKey key = LazyFileCacheKey.create(request);
        FileCacheEntry entry = (FileCacheEntry) this.fileCacheMap.get(key);
        key.recycle();
        if (entry != null) {
            try {
                if (entry != this.NULL_CACHE_ENTRY) {
                    HttpStatus httpStatus = checkIfHeaders(entry, request);
                    if ((httpStatus == null) && entry.type == CacheType.TIMESTAMP) {
                        return null;
                    }
                    request.getResponse().setStatus(httpStatus != null ? httpStatus : HttpStatus.OK_200);
                    notifyProbesEntryHit(this, entry);
                    return entry;
                }
            } catch (Exception e) {
                notifyProbesError(this, e);
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_FILECACHE_GENERAL_ERROR(), e);
            }
        }
        notifyProbesEntryMissed(this, request);
        return null;
    }

    /* access modifiers changed from: protected */
    public void remove(FileCacheEntry entry) {
        if (this.fileCacheMap.remove(entry.key) != null) {
            this.cacheSize.decrementAndGet();
        }
        CacheType cacheType = entry.type;
        if (cacheType == CacheType.MAPPED) {
            subMappedMemorySize((long) entry.bb.remaining());
        } else if (cacheType == CacheType.HEAP) {
            subHeapSize((long) entry.bb.remaining());
        }
        notifyProbesEntryRemoved(this, entry);
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.filecache.jmx.FileCache", this, FileCache.class);
    }

    private FileCacheEntry createEntry(File file) {
        FileCacheEntry entry = tryMapFileToBuffer(file);
        if (entry == null) {
            entry = new FileCacheEntry(this);
            entry.type = CacheType.FILE;
        }
        entry.plainFile = file;
        entry.plainFileSize = file.length();
        return entry;
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x00b2 A[SYNTHETIC, Splitter:B:64:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00bc A[SYNTHETIC, Splitter:B:69:0x00bc] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.glassfish.grizzly.http.server.filecache.FileCacheEntry tryMapFileToBuffer(java.io.File r12) {
        /*
            r11 = this;
            long r6 = r12.length()
            long r0 = r11.getMaxEntrySize()
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            r8 = 0
            if (r0 <= 0) goto L_0x000e
            return r8
        L_0x000e:
            r0 = 0
            r1 = 0
            long r2 = r11.getMinEntrySize()     // Catch:{ Exception -> 0x00ab }
            int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0041
            long r2 = r11.addMappedMemorySize(r6)     // Catch:{ Exception -> 0x00ab }
            long r4 = r11.getMaxLargeFileCacheSize()     // Catch:{ Exception -> 0x00ab }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x003d
            r11.subMappedMemorySize(r6)     // Catch:{ Exception -> 0x00ab }
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x002e }
            goto L_0x0032
        L_0x002e:
            r2 = move-exception
            notifyProbesError(r11, r2)
        L_0x0032:
            if (r0 == 0) goto L_0x003c
            r0.close()     // Catch:{ IOException -> 0x0038 }
            goto L_0x003c
        L_0x0038:
            r2 = move-exception
            notifyProbesError(r11, r2)
        L_0x003c:
            return r8
        L_0x003d:
            org.glassfish.grizzly.http.server.filecache.FileCache$CacheType r2 = org.glassfish.grizzly.http.server.filecache.FileCache.CacheType.MAPPED     // Catch:{ Exception -> 0x00ab }
            r9 = r2
            goto L_0x0069
        L_0x0041:
            long r2 = r11.addHeapSize(r6)     // Catch:{ Exception -> 0x00ab }
            long r4 = r11.getMaxSmallFileCacheSize()     // Catch:{ Exception -> 0x00ab }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0066
            r11.subHeapSize(r6)     // Catch:{ Exception -> 0x00ab }
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ IOException -> 0x0057 }
            goto L_0x005b
        L_0x0057:
            r2 = move-exception
            notifyProbesError(r11, r2)
        L_0x005b:
            if (r0 == 0) goto L_0x0065
            r0.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r2 = move-exception
            notifyProbesError(r11, r2)
        L_0x0065:
            return r8
        L_0x0066:
            org.glassfish.grizzly.http.server.filecache.FileCache$CacheType r2 = org.glassfish.grizzly.http.server.filecache.FileCache.CacheType.HEAP     // Catch:{ Exception -> 0x00ab }
            r9 = r2
        L_0x0069:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00ab }
            r2.<init>(r12)     // Catch:{ Exception -> 0x00ab }
            r10 = r2
            java.nio.channels.FileChannel r1 = r10.getChannel()     // Catch:{ Exception -> 0x00a6, all -> 0x00a3 }
            r0 = r1
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Exception -> 0x00a6, all -> 0x00a3 }
            r2 = 0
            r4 = r6
            java.nio.MappedByteBuffer r1 = r0.map(r1, r2, r4)     // Catch:{ Exception -> 0x00a6, all -> 0x00a3 }
            org.glassfish.grizzly.http.server.filecache.FileCache$CacheType r2 = org.glassfish.grizzly.http.server.filecache.FileCache.CacheType.HEAP     // Catch:{ Exception -> 0x00a6, all -> 0x00a3 }
            if (r9 != r2) goto L_0x0084
            r1.load()     // Catch:{ Exception -> 0x00a6, all -> 0x00a3 }
        L_0x0084:
            r10.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x008d
        L_0x0089:
            r2 = move-exception
            notifyProbesError(r11, r2)
        L_0x008d:
            r0.close()     // Catch:{ IOException -> 0x0092 }
        L_0x0091:
            goto L_0x0097
        L_0x0092:
            r2 = move-exception
            notifyProbesError(r11, r2)
            goto L_0x0091
        L_0x0097:
            org.glassfish.grizzly.http.server.filecache.FileCacheEntry r2 = new org.glassfish.grizzly.http.server.filecache.FileCacheEntry
            r2.<init>(r11)
            r2.type = r9
            r2.plainFileSize = r6
            r2.bb = r1
            return r2
        L_0x00a3:
            r2 = move-exception
            r1 = r10
            goto L_0x00c5
        L_0x00a6:
            r2 = move-exception
            r1 = r10
            goto L_0x00ac
        L_0x00a9:
            r2 = move-exception
            goto L_0x00c5
        L_0x00ab:
            r2 = move-exception
        L_0x00ac:
            notifyProbesError(r11, r2)     // Catch:{ all -> 0x00a9 }
            if (r1 == 0) goto L_0x00ba
            r1.close()     // Catch:{ IOException -> 0x00b6 }
            goto L_0x00ba
        L_0x00b6:
            r3 = move-exception
            notifyProbesError(r11, r3)
        L_0x00ba:
            if (r0 == 0) goto L_0x00c4
            r0.close()     // Catch:{ IOException -> 0x00c0 }
            goto L_0x00c4
        L_0x00c0:
            r3 = move-exception
            notifyProbesError(r11, r3)
        L_0x00c4:
            return r8
        L_0x00c5:
            if (r1 == 0) goto L_0x00cf
            r1.close()     // Catch:{ IOException -> 0x00cb }
            goto L_0x00cf
        L_0x00cb:
            r3 = move-exception
            notifyProbesError(r11, r3)
        L_0x00cf:
            if (r0 == 0) goto L_0x00d9
            r0.close()     // Catch:{ IOException -> 0x00d5 }
            goto L_0x00d9
        L_0x00d5:
            r3 = move-exception
            notifyProbesError(r11, r3)
        L_0x00d9:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.filecache.FileCache.tryMapFileToBuffer(java.io.File):org.glassfish.grizzly.http.server.filecache.FileCacheEntry");
    }

    private boolean canBeCompressed(File cacheFile, String contentType) {
        switch (AnonymousClass2.$SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode[this.compressionConfig.getCompressionMode().ordinal()]) {
            case 1:
                return true;
            case 2:
                return false;
            case 3:
                if (cacheFile.length() < ((long) this.compressionConfig.getCompressionMinSize())) {
                    return false;
                }
                return this.compressionConfig.checkMimeType(contentType);
            default:
                throw new IllegalStateException("Unknown mode");
        }
    }

    public int getSecondsMaxAge() {
        return this.secondsMaxAge;
    }

    public void setSecondsMaxAge(int secondsMaxAge2) {
        this.secondsMaxAge = secondsMaxAge2;
    }

    public int getMaxCacheEntries() {
        return this.maxCacheEntries;
    }

    public void setMaxCacheEntries(int maxCacheEntries2) {
        this.maxCacheEntries = maxCacheEntries2;
    }

    public long getMinEntrySize() {
        return this.minEntrySize;
    }

    public void setMinEntrySize(long minEntrySize2) {
        this.minEntrySize = minEntrySize2;
    }

    public long getMaxEntrySize() {
        return this.maxEntrySize;
    }

    public void setMaxEntrySize(long maxEntrySize2) {
        this.maxEntrySize = maxEntrySize2;
    }

    public long getMaxLargeFileCacheSize() {
        return this.maxLargeFileCacheSize;
    }

    public void setMaxLargeFileCacheSize(long maxLargeFileCacheSize2) {
        this.maxLargeFileCacheSize = maxLargeFileCacheSize2;
    }

    public long getMaxSmallFileCacheSize() {
        return this.maxSmallFileCacheSize;
    }

    public void setMaxSmallFileCacheSize(long maxSmallFileCacheSize2) {
        this.maxSmallFileCacheSize = maxSmallFileCacheSize2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled2) {
        this.enabled = enabled2;
    }

    public CompressionConfig getCompressionConfig() {
        return this.compressionConfig;
    }

    public File getCompressedFilesFolder() {
        return this.compressedFilesFolder;
    }

    public void setCompressedFilesFolder(File compressedFilesFolder2) {
        this.compressedFilesFolder = compressedFilesFolder2 != null ? compressedFilesFolder2 : TMP_DIR;
    }

    public boolean isFileSendEnabled() {
        return this.fileSendEnabled;
    }

    public void setFileSendEnabled(boolean fileSendEnabled2) {
        this.fileSendEnabled = fileSendEnabled2;
    }

    /* access modifiers changed from: protected */
    public void compressFile(FileCacheEntry entry) {
        InputStream in;
        OutputStream out;
        FileInputStream cFis;
        try {
            File tmpCompressedFile = File.createTempFile(String.valueOf(entry.plainFile.hashCode()), ".tmpzip", this.compressedFilesFolder);
            tmpCompressedFile.deleteOnExit();
            in = null;
            out = null;
            in = new FileInputStream(entry.plainFile);
            out = new GZIPOutputStream(new FileOutputStream(tmpCompressedFile));
            byte[] tmp = new byte[1024];
            while (true) {
                int readNow = in.read(tmp);
                if (readNow == -1) {
                    try {
                        break;
                    } catch (IOException e) {
                    }
                } else {
                    out.write(tmp, 0, readNow);
                }
            }
            in.close();
            try {
                out.close();
            } catch (IOException e2) {
            }
            long size = tmpCompressedFile.length();
            switch (AnonymousClass2.$SwitchMap$org$glassfish$grizzly$http$server$filecache$FileCache$CacheType[entry.type.ordinal()]) {
                case 1:
                case 2:
                    cFis = new FileInputStream(tmpCompressedFile);
                    MappedByteBuffer compressedBb = cFis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, size);
                    if (entry.type == CacheType.HEAP) {
                        compressedBb.load();
                    }
                    entry.compressedBb = compressedBb;
                    cFis.close();
                    break;
                case 3:
                    break;
                default:
                    throw new IllegalStateException("The type is not supported: " + entry.type);
            }
            entry.compressedFileSize = size;
            entry.compressedFile = tmpCompressedFile;
        } catch (IOException e3) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            logger.log(level, "Can not compress file: " + entry.plainFile, e3);
        } catch (Throwable th) {
            cFis.close();
            throw th;
        }
    }

    /* renamed from: org.glassfish.grizzly.http.server.filecache.FileCache$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode;
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$server$filecache$FileCache$CacheType;

        static {
            int[] iArr = new int[CacheType.values().length];
            $SwitchMap$org$glassfish$grizzly$http$server$filecache$FileCache$CacheType = iArr;
            try {
                iArr[CacheType.HEAP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$server$filecache$FileCache$CacheType[CacheType.MAPPED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$server$filecache$FileCache$CacheType[CacheType.FILE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[CompressionConfig.CompressionMode.values().length];
            $SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode = iArr2;
            try {
                iArr2[CompressionConfig.CompressionMode.FORCE.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode[CompressionConfig.CompressionMode.OFF.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode[CompressionConfig.CompressionMode.ON.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final long addHeapSize(long size) {
        return this.heapSize.addAndGet(size);
    }

    /* access modifiers changed from: protected */
    public final long subHeapSize(long size) {
        return this.heapSize.addAndGet(-size);
    }

    public long getHeapCacheSize() {
        return this.heapSize.get();
    }

    /* access modifiers changed from: protected */
    public final long addMappedMemorySize(long size) {
        return this.mappedMemorySize.addAndGet(size);
    }

    /* access modifiers changed from: protected */
    public final long subMappedMemorySize(long size) {
        return this.mappedMemorySize.addAndGet(-size);
    }

    public long getMappedCacheSize() {
        return this.mappedMemorySize.get();
    }

    private HttpStatus checkIfHeaders(FileCacheEntry entry, HttpRequestPacket request) {
        HttpStatus httpStatus = checkIfMatch(entry, request);
        if (httpStatus != null) {
            return httpStatus;
        }
        HttpStatus httpStatus2 = checkIfModifiedSince(entry, request);
        if (httpStatus2 != null) {
            return httpStatus2;
        }
        HttpStatus httpStatus3 = checkIfNoneMatch(entry, request);
        if (httpStatus3 == null) {
            return checkIfUnmodifiedSince(entry, request);
        }
        return httpStatus3;
    }

    private HttpStatus checkIfModifiedSince(FileCacheEntry entry, HttpRequestPacket request) {
        try {
            String reqModified = request.getHeader(Header.IfModifiedSince);
            if (reqModified == null) {
                return null;
            }
            if (reqModified.equals(entry.lastModifiedHeader)) {
                return HttpStatus.NOT_MODIFIED_304;
            }
            long headerValue = convertToLong(reqModified);
            if (headerValue == -1) {
                return null;
            }
            long lastModified = entry.lastModified;
            if (request.getHeader(Header.IfNoneMatch) != null || lastModified - headerValue > 1000) {
                return null;
            }
            return HttpStatus.NOT_MODIFIED_304;
        } catch (IllegalArgumentException illegalArgument) {
            notifyProbesError(this, illegalArgument);
            return null;
        }
    }

    private HttpStatus checkIfNoneMatch(FileCacheEntry entry, HttpRequestPacket request) {
        String headerValue = request.getHeader(Header.IfNoneMatch);
        if (headerValue == null) {
            return null;
        }
        String eTag = entry.Etag;
        boolean conditionSatisfied = false;
        if (!headerValue.equals(e.ANY_MARKER)) {
            StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
            while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                if (commaTokenizer.nextToken().trim().equals(eTag)) {
                    conditionSatisfied = true;
                }
            }
        } else {
            conditionSatisfied = true;
        }
        if (!conditionSatisfied) {
            return null;
        }
        Method method = request.getMethod();
        if (Method.GET.equals(method) || Method.HEAD.equals(method)) {
            return HttpStatus.NOT_MODIFIED_304;
        }
        return HttpStatus.PRECONDITION_FAILED_412;
    }

    private HttpStatus checkIfUnmodifiedSince(FileCacheEntry entry, HttpRequestPacket request) {
        try {
            long lastModified = entry.lastModified;
            String h = request.getHeader(Header.IfUnmodifiedSince);
            if (h == null) {
                return null;
            }
            if (h.equals(entry.lastModifiedHeader)) {
                return HttpStatus.PRECONDITION_FAILED_412;
            }
            long headerValue = convertToLong(h);
            if (headerValue == -1 || headerValue - lastModified > 1000) {
                return null;
            }
            return HttpStatus.PRECONDITION_FAILED_412;
        } catch (IllegalArgumentException illegalArgument) {
            notifyProbesError(this, illegalArgument);
            return null;
        }
    }

    private HttpStatus checkIfMatch(FileCacheEntry entry, HttpRequestPacket request) {
        String headerValue = request.getHeader(Header.IfMatch);
        if (headerValue == null || headerValue.indexOf(42) != -1) {
            return null;
        }
        String eTag = entry.Etag;
        StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
        boolean conditionSatisfied = false;
        while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
            if (commaTokenizer.nextToken().trim().equals(eTag)) {
                conditionSatisfied = true;
            }
        }
        if (!conditionSatisfied) {
            return HttpStatus.PRECONDITION_FAILED_412;
        }
        return null;
    }

    public MonitoringConfig<FileCacheProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    protected static void notifyProbesEntryAdded(FileCache fileCache, FileCacheEntry entry) {
        FileCacheProbe[] probes = (FileCacheProbe[]) fileCache.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (FileCacheProbe probe : probes) {
                probe.onEntryAddedEvent(fileCache, entry);
            }
        }
    }

    protected static void notifyProbesEntryRemoved(FileCache fileCache, FileCacheEntry entry) {
        FileCacheProbe[] probes = (FileCacheProbe[]) fileCache.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (FileCacheProbe probe : probes) {
                probe.onEntryRemovedEvent(fileCache, entry);
            }
        }
    }

    protected static void notifyProbesEntryHit(FileCache fileCache, FileCacheEntry entry) {
        FileCacheProbe[] probes = (FileCacheProbe[]) fileCache.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (FileCacheProbe probe : probes) {
                probe.onEntryHitEvent(fileCache, entry);
            }
        }
    }

    protected static void notifyProbesEntryMissed(FileCache fileCache, HttpRequestPacket request) {
        FileCacheProbe[] probes = (FileCacheProbe[]) fileCache.monitoringConfig.getProbesUnsafe();
        if (probes != null && probes.length > 0) {
            for (FileCacheProbe probe : probes) {
                probe.onEntryMissedEvent(fileCache, request.getHeader(Header.Host), request.getRequestURI());
            }
        }
    }

    protected static void notifyProbesError(FileCache fileCache, Throwable error) {
        FileCacheProbe[] probes = (FileCacheProbe[]) fileCache.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (FileCacheProbe probe : probes) {
                probe.onErrorEvent(fileCache, error);
            }
        }
    }

    protected static long convertToLong(String dateHeader) {
        if (dateHeader == null) {
            return -1;
        }
        SimpleDateFormats formats = SimpleDateFormats.create();
        try {
            long result = FastHttpDateFormat.parseDate(dateHeader, formats.getFormats());
            if (result != -1) {
                return result;
            }
            throw new IllegalArgumentException(dateHeader);
        } finally {
            formats.recycle();
        }
    }

    public static class EntryWorker implements DelayedExecutor.Worker<FileCacheEntry> {
        private EntryWorker() {
        }

        public boolean doWork(FileCacheEntry element) {
            element.run();
            return true;
        }
    }

    public static class EntryResolver implements DelayedExecutor.Resolver<FileCacheEntry> {
        private EntryResolver() {
        }

        public boolean removeTimeout(FileCacheEntry element) {
            if (element.timeoutMillis == -1) {
                return false;
            }
            element.timeoutMillis = -1;
            return true;
        }

        public long getTimeoutMillis(FileCacheEntry element) {
            return element.timeoutMillis;
        }

        public void setTimeoutMillis(FileCacheEntry element, long timeoutMillis) {
            element.timeoutMillis = timeoutMillis;
        }
    }
}

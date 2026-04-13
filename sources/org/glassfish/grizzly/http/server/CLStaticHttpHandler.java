package org.glassfish.grizzly.http.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.ArraySet;

public class CLStaticHttpHandler extends StaticHttpHandlerBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean CHECK_NON_SLASH_TERMINATED_FOLDERS;
    protected static final String CHECK_NON_SLASH_TERMINATED_FOLDERS_PROP;
    private static final String EMPTY_STR = "";
    /* access modifiers changed from: private */
    public static final Logger LOGGER;
    private static final String SLASH_STR = "/";
    private final ClassLoader classLoader;
    private final ArraySet<String> docRoots;

    static {
        Class<CLStaticHttpHandler> cls = CLStaticHttpHandler.class;
        LOGGER = Grizzly.logger(cls);
        String str = cls.getName() + ".check-non-slash-terminated-folders";
        CHECK_NON_SLASH_TERMINATED_FOLDERS_PROP = str;
        CHECK_NON_SLASH_TERMINATED_FOLDERS = System.getProperty(str) == null || Boolean.getBoolean(str);
    }

    public CLStaticHttpHandler(ClassLoader classLoader2, String... docRoots2) {
        ArraySet<String> arraySet = new ArraySet<>(String.class);
        this.docRoots = arraySet;
        if (classLoader2 != null) {
            this.classLoader = classLoader2;
            if (docRoots2.length > 0) {
                int length = docRoots2.length;
                int i = 0;
                while (i < length) {
                    if (docRoots2[i].endsWith(SLASH_STR)) {
                        i++;
                    } else {
                        throw new IllegalArgumentException("Doc root should end with slash ('/')");
                    }
                }
                this.docRoots.addAll((T[]) docRoots2);
                return;
            }
            arraySet.add(SLASH_STR);
            return;
        }
        throw new IllegalArgumentException("ClassLoader can not be null");
    }

    public boolean addDocRoot(String docRoot) {
        if (docRoot.endsWith(SLASH_STR)) {
            return this.docRoots.add(docRoot);
        }
        throw new IllegalArgumentException("Doc root should end with slash ('/')");
    }

    public boolean removeDocRoot(String docRoot) {
        return this.docRoots.remove(docRoot);
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0132  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handle(java.lang.String r20, org.glassfish.grizzly.http.server.Request r21, org.glassfish.grizzly.http.server.Response r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            r3 = r22
            r4 = 0
            r5 = 0
            java.lang.String r6 = "/"
            boolean r7 = r1.startsWith(r6)
            r8 = 1
            if (r7 == 0) goto L_0x0017
            java.lang.String r1 = r1.substring(r8)
        L_0x0017:
            r7 = 1
            int r9 = r1.length()
            java.lang.String r10 = "index.html"
            if (r9 == 0) goto L_0x0026
            boolean r9 = r1.endsWith(r6)
            if (r9 == 0) goto L_0x0036
        L_0x0026:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r1)
            r9.append(r10)
            java.lang.String r1 = r9.toString()
            r7 = 0
        L_0x0036:
            java.net.URL r9 = r0.lookupResource(r1)
            java.lang.String r11 = "/index.html"
            if (r9 != 0) goto L_0x0058
            if (r7 == 0) goto L_0x0058
            boolean r12 = CHECK_NON_SLASH_TERMINATED_FOLDERS
            if (r12 == 0) goto L_0x0058
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r1)
            r12.append(r11)
            java.lang.String r12 = r12.toString()
            java.net.URL r9 = r0.lookupResource(r12)
            r7 = 0
        L_0x0058:
            r12 = 0
            r13 = 0
            r14 = 0
            java.lang.String r15 = "jar"
            if (r9 == 0) goto L_0x0187
            java.lang.String r8 = r9.getProtocol()
            r16 = r4
            java.lang.String r4 = "file"
            boolean r4 = r4.equals(r8)
            if (r4 == 0) goto L_0x00a4
            java.io.File r4 = new java.io.File
            java.net.URI r6 = r9.toURI()
            r4.<init>(r6)
            boolean r6 = r4.exists()
            if (r6 == 0) goto L_0x00a0
            boolean r6 = r4.isDirectory()
            if (r6 == 0) goto L_0x009a
            java.io.File r6 = new java.io.File
            r6.<init>(r4, r11)
            boolean r8 = r6.exists()
            if (r8 == 0) goto L_0x0099
            boolean r8 = r6.isFile()
            if (r8 == 0) goto L_0x0099
            r12 = r6
            java.lang.String r13 = r6.getPath()
            r14 = 1
        L_0x0099:
            goto L_0x00a0
        L_0x009a:
            r12 = r4
            java.lang.String r13 = r4.getPath()
            r14 = 1
        L_0x00a0:
            r4 = r16
            goto L_0x018f
        L_0x00a4:
            java.net.URLConnection r4 = r9.openConnection()
            java.lang.String r8 = r9.getProtocol()
            boolean r8 = r15.equals(r8)
            if (r8 == 0) goto L_0x013c
            r8 = r4
            java.net.JarURLConnection r8 = (java.net.JarURLConnection) r8
            r17 = r5
            java.util.jar.JarEntry r5 = r8.getJarEntry()
            r20 = r12
            java.util.jar.JarFile r12 = r8.getJarFile()
            r16 = 0
            boolean r18 = r5.isDirectory()
            if (r18 != 0) goto L_0x00d7
            java.io.InputStream r18 = r12.getInputStream(r5)
            r16 = r18
            if (r18 != 0) goto L_0x00d2
            goto L_0x00d7
        L_0x00d2:
            r18 = r13
            r6 = r16
            goto L_0x011a
        L_0x00d7:
            r18 = r13
            java.lang.String r13 = r5.getName()
            boolean r6 = r13.endsWith(r6)
            if (r6 == 0) goto L_0x00f7
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = r5.getName()
            r6.append(r11)
            r6.append(r10)
            java.lang.String r6 = r6.toString()
            goto L_0x010a
        L_0x00f7:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r10 = r5.getName()
            r6.append(r10)
            r6.append(r11)
            java.lang.String r6 = r6.toString()
        L_0x010a:
            java.util.jar.JarEntry r5 = r12.getJarEntry(r6)
            if (r5 == 0) goto L_0x0118
            java.io.InputStream r16 = r12.getInputStream(r5)
            r6 = r16
            goto L_0x011a
        L_0x0118:
            r6 = r16
        L_0x011a:
            if (r6 == 0) goto L_0x0132
            org.glassfish.grizzly.http.server.CLStaticHttpHandler$JarURLInputStream r10 = new org.glassfish.grizzly.http.server.CLStaticHttpHandler$JarURLInputStream
            r10.<init>(r8, r12, r6)
            if (r5 == 0) goto L_0x012c
            java.lang.String r11 = r5.getName()
            r13 = 1
            r5 = r10
            r14 = r13
            r13 = r11
            goto L_0x0139
        L_0x012c:
            java.lang.AssertionError r11 = new java.lang.AssertionError
            r11.<init>()
            throw r11
        L_0x0132:
            closeJarFileIfNeeded(r8, r12)
            r5 = r17
            r13 = r18
        L_0x0139:
            r12 = r20
            goto L_0x018f
        L_0x013c:
            r17 = r5
            r20 = r12
            r18 = r13
            java.lang.String r5 = r9.getProtocol()
            java.lang.String r6 = "bundle"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x017f
            if (r7 == 0) goto L_0x0177
            int r5 = r4.getContentLength()
            if (r5 > 0) goto L_0x0177
            java.lang.ClassLoader r5 = r0.classLoader
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r8 = r9.getPath()
            r6.append(r8)
            r6.append(r11)
            java.lang.String r6 = r6.toString()
            java.net.URL r5 = r5.getResource(r6)
            if (r5 == 0) goto L_0x0177
            r6 = r5
            java.net.URLConnection r4 = r5.openConnection()
            r9 = r6
        L_0x0177:
            r14 = 1
            r12 = r20
            r5 = r17
            r13 = r18
            goto L_0x018f
        L_0x017f:
            r14 = 1
            r12 = r20
            r5 = r17
            r13 = r18
            goto L_0x018f
        L_0x0187:
            r16 = r4
            r17 = r5
            r20 = r12
            r18 = r13
        L_0x018f:
            r6 = 0
            if (r14 != 0) goto L_0x01a2
            java.util.logging.Logger r8 = LOGGER
            java.util.logging.Level r10 = java.util.logging.Level.FINE
            boolean r11 = r8.isLoggable(r10)
            if (r11 == 0) goto L_0x01a1
            java.lang.String r11 = "Resource not found {0}"
            r8.log(r10, r11, r1)
        L_0x01a1:
            return r6
        L_0x01a2:
            if (r9 == 0) goto L_0x0220
            org.glassfish.grizzly.http.Method r8 = org.glassfish.grizzly.http.Method.GET
            org.glassfish.grizzly.http.Method r10 = r21.getMethod()
            boolean r8 = r8.equals(r10)
            if (r8 != 0) goto L_0x01d9
            java.util.logging.Logger r8 = LOGGER
            java.util.logging.Level r10 = java.util.logging.Level.FINE
            boolean r11 = r8.isLoggable(r10)
            if (r11 == 0) goto L_0x01cb
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r11[r6] = r1
            org.glassfish.grizzly.http.Method r6 = r21.getMethod()
            r15 = 1
            r11[r15] = r6
            java.lang.String r6 = "Resource found {0}, but HTTP method {1} is not allowed"
            r8.log(r10, r6, r11)
        L_0x01cb:
            org.glassfish.grizzly.http.util.HttpStatus r6 = org.glassfish.grizzly.http.util.HttpStatus.METHOD_NOT_ALLOWED_405
            r3.setStatus((org.glassfish.grizzly.http.util.HttpStatus) r6)
            org.glassfish.grizzly.http.util.Header r6 = org.glassfish.grizzly.http.util.Header.Allow
            java.lang.String r8 = "GET"
            r3.setHeader((org.glassfish.grizzly.http.util.Header) r6, (java.lang.String) r8)
            r6 = 1
            return r6
        L_0x01d9:
            if (r13 == 0) goto L_0x01dd
            r6 = r13
            goto L_0x01e1
        L_0x01dd:
            java.lang.String r6 = r9.getPath()
        L_0x01e1:
            org.glassfish.grizzly.http.server.StaticHttpHandlerBase.pickupContentType(r3, r6)
            if (r12 == 0) goto L_0x01ed
            r0.addToFileCache(r2, r3, r12)
            org.glassfish.grizzly.http.server.StaticHttpHandlerBase.sendFile(r3, r12)
            goto L_0x0218
        L_0x01ed:
            if (r4 == 0) goto L_0x021a
            java.lang.String r6 = r9.getProtocol()
            boolean r6 = r15.equals(r6)
            if (r6 == 0) goto L_0x020d
            java.net.URI r6 = new java.net.URI
            java.lang.String r8 = r9.getPath()
            r6.<init>(r8)
            java.lang.String r6 = r6.getPath()
            java.io.File r6 = r0.getJarFile(r6)
            r0.addTimeStampEntryToFileCache(r2, r3, r6)
        L_0x020d:
            if (r5 == 0) goto L_0x0211
            r6 = r5
            goto L_0x0215
        L_0x0211:
            java.io.InputStream r6 = r4.getInputStream()
        L_0x0215:
            sendResource(r3, r6)
        L_0x0218:
            r6 = 1
            return r6
        L_0x021a:
            java.lang.AssertionError r6 = new java.lang.AssertionError
            r6.<init>()
            throw r6
        L_0x0220:
            java.lang.AssertionError r6 = new java.lang.AssertionError
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.CLStaticHttpHandler.handle(java.lang.String, org.glassfish.grizzly.http.server.Request, org.glassfish.grizzly.http.server.Response):boolean");
    }

    private URL lookupResource(String resourcePath) {
        String[] docRootsLocal = (String[]) this.docRoots.getArray();
        if (docRootsLocal == null || docRootsLocal.length == 0) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (logger.isLoggable(level)) {
                logger.log(level, "No doc roots registered -> resource {0} is not found ", resourcePath);
            }
            return null;
        }
        for (String docRoot : docRootsLocal) {
            if (SLASH_STR.equals(docRoot)) {
                docRoot = "";
            } else if (docRoot.startsWith(SLASH_STR)) {
                docRoot = docRoot.substring(1);
            }
            URL url = this.classLoader.getResource(docRoot + resourcePath);
            if (url != null) {
                return url;
            }
        }
        return null;
    }

    private static void sendResource(Response response, InputStream input) {
        response.setStatus(HttpStatus.OK_200);
        response.addDateHeader(Header.Date, System.currentTimeMillis());
        response.suspend();
        NIOOutputStream outputStream = response.getNIOOutputStream();
        outputStream.notifyCanWrite(new NonBlockingDownloadHandler(response, outputStream, input, 8192));
    }

    private boolean addTimeStampEntryToFileCache(Request req, Response res, File archive) {
        FileCacheFilter fileCacheFilter;
        if (!isFileCacheEnabled() || (fileCacheFilter = lookupFileCache(req.getContext())) == null) {
            return false;
        }
        FileCache fileCache = fileCacheFilter.getFileCache();
        if (!fileCache.isEnabled()) {
            return false;
        }
        if (res != null) {
            StaticHttpHandlerBase.addCachingHeaders(res, archive);
        }
        fileCache.add(req.getRequest(), archive.lastModified());
        return true;
    }

    private File getJarFile(String path) {
        int jarDelimIdx = path.indexOf("!/");
        if (jarDelimIdx != -1) {
            File file = new File(path.substring(0, jarDelimIdx));
            if (file.exists() && file.isFile()) {
                return file;
            }
            throw new FileNotFoundException("The jar file was not found");
        }
        throw new MalformedURLException("The jar file delimeter were not found");
    }

    public static class NonBlockingDownloadHandler implements WriteHandler {
        private final int chunkSize;
        private final InputStream inputStream;
        private final MemoryManager mm;
        private final NIOOutputStream outputStream;
        private final Response response;

        NonBlockingDownloadHandler(Response response2, NIOOutputStream outputStream2, InputStream inputStream2, int chunkSize2) {
            this.response = response2;
            this.outputStream = outputStream2;
            this.inputStream = inputStream2;
            this.mm = response2.getRequest().getContext().getMemoryManager();
            this.chunkSize = chunkSize2;
        }

        public void onWritePossible() {
            CLStaticHttpHandler.LOGGER.log(Level.FINE, "[onWritePossible]");
            if (sendChunk()) {
                this.outputStream.notifyCanWrite(this);
            }
        }

        public void onError(Throwable t) {
            CLStaticHttpHandler.LOGGER.log(Level.FINE, "[onError] ", t);
            this.response.setStatus(500, t.getMessage());
            complete(true);
        }

        private boolean sendChunk() {
            int len;
            Buffer buffer = null;
            if (!this.mm.willAllocateDirect(this.chunkSize)) {
                buffer = this.mm.allocate(this.chunkSize);
                if (!buffer.isComposite()) {
                    len = this.inputStream.read(buffer.array(), buffer.position() + buffer.arrayOffset(), this.chunkSize);
                } else {
                    BufferArray bufferArray = buffer.toBufferArray();
                    int size = bufferArray.size();
                    Buffer[] buffers = (Buffer[]) bufferArray.getArray();
                    int lenCounter = 0;
                    for (int i = 0; i < size; i++) {
                        Buffer subBuffer = buffers[i];
                        int subBufferLen = subBuffer.remaining();
                        int justReadLen = this.inputStream.read(subBuffer.array(), subBuffer.position() + subBuffer.arrayOffset(), subBufferLen);
                        if (justReadLen > 0) {
                            lenCounter += justReadLen;
                        }
                        if (justReadLen < subBufferLen) {
                            break;
                        }
                    }
                    bufferArray.restore();
                    bufferArray.recycle();
                    len = lenCounter > 0 ? lenCounter : -1;
                }
                if (len > 0) {
                    buffer.position(buffer.position() + len);
                } else {
                    buffer.dispose();
                    buffer = null;
                }
            } else {
                byte[] buf = new byte[this.chunkSize];
                int len2 = this.inputStream.read(buf);
                if (len2 > 0) {
                    buffer = this.mm.allocate(len2);
                    buffer.put(buf);
                }
            }
            if (buffer == null) {
                complete(false);
                return false;
            }
            buffer.allowBufferDispose(true);
            buffer.trim();
            this.outputStream.write(buffer);
            return true;
        }

        private void complete(boolean isError) {
            try {
                this.inputStream.close();
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

    public static class JarURLInputStream extends FilterInputStream {
        private final JarURLConnection jarConnection;
        private final JarFile jarFile;

        JarURLInputStream(JarURLConnection jarConnection2, JarFile jarFile2, InputStream src) {
            super(src);
            this.jarConnection = jarConnection2;
            this.jarFile = jarFile2;
        }

        public void close() {
            try {
                super.close();
            } finally {
                CLStaticHttpHandler.closeJarFileIfNeeded(this.jarConnection, this.jarFile);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void closeJarFileIfNeeded(JarURLConnection jarConnection, JarFile jarFile) {
        if (!jarConnection.getUseCaches()) {
            jarFile.close();
        }
    }
}

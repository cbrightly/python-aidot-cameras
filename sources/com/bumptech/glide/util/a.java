package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ByteBufferUtil */
public final class a {
    private static final AtomicReference<byte[]> a = new AtomicReference<>();

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0050 A[SYNTHETIC, Splitter:B:25:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0057 A[SYNTHETIC, Splitter:B:29:0x0057] */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.nio.ByteBuffer a(@androidx.annotation.NonNull java.io.File r10) {
        /*
            r0 = 0
            r1 = 0
            long r2 = r10.length()     // Catch:{ all -> 0x004b }
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x0043
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x003b
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ all -> 0x004b }
            java.lang.String r5 = "r"
            r4.<init>(r10, r5)     // Catch:{ all -> 0x004b }
            r0 = r4
            java.nio.channels.FileChannel r4 = r0.getChannel()     // Catch:{ all -> 0x004b }
            java.nio.channels.FileChannel$MapMode r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x0039 }
            r6 = 0
            r8 = r2
            java.nio.MappedByteBuffer r1 = r4.map(r5, r6, r8)     // Catch:{ all -> 0x0039 }
            java.nio.MappedByteBuffer r1 = r1.load()     // Catch:{ all -> 0x0039 }
            r4.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0032
        L_0x0031:
            r5 = move-exception
        L_0x0032:
            r0.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x0038
        L_0x0037:
            r5 = move-exception
        L_0x0038:
            return r1
        L_0x0039:
            r1 = move-exception
            goto L_0x004e
        L_0x003b:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x004b }
            java.lang.String r5 = "File unsuitable for memory mapping"
            r4.<init>(r5)     // Catch:{ all -> 0x004b }
            throw r4     // Catch:{ all -> 0x004b }
        L_0x0043:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x004b }
            java.lang.String r5 = "File too large to map into memory"
            r4.<init>(r5)     // Catch:{ all -> 0x004b }
            throw r4     // Catch:{ all -> 0x004b }
        L_0x004b:
            r2 = move-exception
            r4 = r1
            r1 = r2
        L_0x004e:
            if (r4 == 0) goto L_0x0055
            r4.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0055
        L_0x0054:
            r2 = move-exception
        L_0x0055:
            if (r0 == 0) goto L_0x005c
            r0.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x005c
        L_0x005b:
            r2 = move-exception
        L_0x005c:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.util.a.a(java.io.File):java.nio.ByteBuffer");
    }

    public static void e(@NonNull ByteBuffer buffer, @NonNull File file) {
        buffer.position(0);
        RandomAccessFile raf = null;
        FileChannel channel = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            channel = raf.getChannel();
            channel.write(buffer);
            channel.force(false);
            channel.close();
            raf.close();
            try {
                channel.close();
            } catch (IOException e) {
            }
            try {
                raf.close();
            } catch (IOException e2) {
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e3) {
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
    }

    @NonNull
    public static byte[] d(@NonNull ByteBuffer byteBuffer) {
        b safeArray = c(byteBuffer);
        if (safeArray != null && safeArray.a == 0 && safeArray.b == safeArray.c.length) {
            return byteBuffer.array();
        }
        ByteBuffer toCopy = byteBuffer.asReadOnlyBuffer();
        byte[] result = new byte[toCopy.limit()];
        toCopy.position(0);
        toCopy.get(result);
        return result;
    }

    @NonNull
    public static InputStream f(@NonNull ByteBuffer buffer) {
        return new C0047a(buffer);
    }

    @NonNull
    public static ByteBuffer b(@NonNull InputStream stream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(16384);
        byte[] buffer = a.getAndSet((Object) null);
        if (buffer == null) {
            buffer = new byte[16384];
        }
        while (true) {
            int read = stream.read(buffer);
            int n = read;
            if (read >= 0) {
                outStream.write(buffer, 0, n);
            } else {
                a.set(buffer);
                byte[] bytes = outStream.toByteArray();
                return (ByteBuffer) ByteBuffer.allocateDirect(bytes.length).put(bytes).position(0);
            }
        }
    }

    @Nullable
    private static b c(@NonNull ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly() || !byteBuffer.hasArray()) {
            return null;
        }
        return new b(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit());
    }

    /* compiled from: ByteBufferUtil */
    public static final class b {
        final int a;
        final int b;
        final byte[] c;

        b(@NonNull byte[] data, int offset, int limit) {
            this.c = data;
            this.a = offset;
            this.b = limit;
        }
    }

    /* renamed from: com.bumptech.glide.util.a$a  reason: collision with other inner class name */
    /* compiled from: ByteBufferUtil */
    public static class C0047a extends InputStream {
        @NonNull
        private final ByteBuffer c;
        private int d = -1;

        C0047a(@NonNull ByteBuffer byteBuffer) {
            this.c = byteBuffer;
        }

        public int available() {
            return this.c.remaining();
        }

        public int read() {
            if (!this.c.hasRemaining()) {
                return -1;
            }
            return this.c.get() & 255;
        }

        public synchronized void mark(int readLimit) {
            this.d = this.c.position();
        }

        public boolean markSupported() {
            return true;
        }

        public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) {
            if (!this.c.hasRemaining()) {
                return -1;
            }
            int toRead = Math.min(byteCount, available());
            this.c.get(buffer, byteOffset, toRead);
            return toRead;
        }

        public synchronized void reset() {
            int i = this.d;
            if (i != -1) {
                this.c.position(i);
            } else {
                throw new IOException("Cannot reset to unset mark position");
            }
        }

        public long skip(long byteCount) {
            if (!this.c.hasRemaining()) {
                return -1;
            }
            long toSkip = Math.min(byteCount, (long) available());
            ByteBuffer byteBuffer = this.c;
            byteBuffer.position((int) (((long) byteBuffer.position()) + toSkip));
            return toSkip;
        }
    }
}

package org.apache.commons.io;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: IOUtils */
public class d {
    public static final char a = File.separatorChar;
    public static final String b;

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0033, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0034, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0037, code lost:
        throw r2;
     */
    static {
        /*
            char r0 = java.io.File.separatorChar
            a = r0
            org.apache.commons.io.output.c r0 = new org.apache.commons.io.output.c
            r1 = 4
            r0.<init>((int) r1)
            java.io.PrintWriter r1 = new java.io.PrintWriter     // Catch:{ all -> 0x002c }
            r1.<init>(r0)     // Catch:{ all -> 0x002c }
            r1.println()     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x0020 }
            b = r2     // Catch:{ all -> 0x0020 }
            r1.close()     // Catch:{ all -> 0x002c }
            r0.close()
            return
        L_0x0020:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0022 }
        L_0x0022:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x0027 }
            goto L_0x002b
        L_0x0027:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ all -> 0x002c }
        L_0x002b:
            throw r3     // Catch:{ all -> 0x002c }
        L_0x002c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002e }
        L_0x002e:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0033 }
            goto L_0x0037
        L_0x0033:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0037:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.d.<clinit>():void");
    }

    @Deprecated
    public static void b(InputStream input) {
        a(input);
    }

    @Deprecated
    public static void c(OutputStream output) {
        a(output);
    }

    @Deprecated
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static int d(InputStream input, byte[] buffer, int offset, int length) {
        if (length >= 0) {
            int remaining = length;
            while (remaining > 0) {
                int count = input.read(buffer, offset + (length - remaining), remaining);
                if (-1 == count) {
                    break;
                }
                remaining -= count;
            }
            return length - remaining;
        }
        throw new IllegalArgumentException("Length must not be negative: " + length);
    }

    public static void f(InputStream input, byte[] buffer, int offset, int length) {
        int actual = d(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    public static void e(InputStream input, byte[] buffer) {
        f(input, buffer, 0, buffer.length);
    }
}

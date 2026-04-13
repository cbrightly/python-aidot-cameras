package org.spongycastle.util.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Streams {
    private static int a = 4096;

    public static byte[] b(InputStream inStr) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        a(inStr, buf);
        return buf.toByteArray();
    }

    public static int c(InputStream inStr, byte[] buf) {
        return d(inStr, buf, 0, buf.length);
    }

    public static int d(InputStream inStr, byte[] buf, int off, int len) {
        int totalRead = 0;
        while (totalRead < len) {
            int numRead = inStr.read(buf, off + totalRead, len - totalRead);
            if (numRead < 0) {
                break;
            }
            totalRead += numRead;
        }
        return totalRead;
    }

    public static void a(InputStream inStr, OutputStream outStr) {
        byte[] bs = new byte[a];
        while (true) {
            int read = inStr.read(bs, 0, bs.length);
            int numRead = read;
            if (read >= 0) {
                outStr.write(bs, 0, numRead);
            } else {
                return;
            }
        }
    }
}

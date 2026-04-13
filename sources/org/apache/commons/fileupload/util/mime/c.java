package org.apache.commons.fileupload.util.mime;

import java.io.IOException;
import java.io.OutputStream;

/* compiled from: QuotedPrintableDecoder */
public final class c {
    public static int a(byte[] data, OutputStream out) {
        int off = 0;
        int endOffset = 0 + data.length;
        int bytesWritten = 0;
        while (off < endOffset) {
            int off2 = off + 1;
            byte off3 = data[off];
            if (off3 == 95) {
                out.write(32);
                off = off2;
            } else if (off3 != 61) {
                out.write(off3);
                bytesWritten++;
                off = off2;
            } else if (off2 + 1 < endOffset) {
                int off4 = off2 + 1;
                byte b1 = data[off2];
                int off5 = off4 + 1;
                byte b2 = data[off4];
                if (b1 != 13) {
                    out.write((b(b1) << 4) | b(b2));
                    bytesWritten++;
                } else if (b2 != 10) {
                    throw new IOException("Invalid quoted printable encoding; CR must be followed by LF");
                }
                off = off5;
            } else {
                throw new IOException("Invalid quoted printable encoding; truncated escape sequence");
            }
        }
        return bytesWritten;
    }

    private static int b(byte b) {
        int i = Character.digit((char) b, 16);
        if (i != -1) {
            return i;
        }
        throw new IOException("Invalid quoted printable encoding: not a valid hex digit: " + b);
    }
}

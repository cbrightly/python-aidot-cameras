package org.apache.commons.fileupload.util;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.fileupload.InvalidFileNameException;

/* compiled from: Streams */
public final class d {
    public static long b(InputStream inputStream, OutputStream outputStream, boolean closeOutputStream) {
        return c(inputStream, outputStream, closeOutputStream, new byte[8192]);
    }

    public static long c(InputStream inputStream, OutputStream outputStream, boolean closeOutputStream, byte[] buffer) {
        OutputStream out = outputStream;
        InputStream in = inputStream;
        long total = 0;
        while (true) {
            try {
                int res = in.read(buffer);
                if (res == -1) {
                    break;
                } else if (res > 0) {
                    total += (long) res;
                    if (out != null) {
                        out.write(buffer, 0, res);
                    }
                }
            } finally {
                org.apache.commons.io.d.b(in);
                if (closeOutputStream) {
                    org.apache.commons.io.d.c(out);
                }
            }
        }
        if (out != null) {
            if (closeOutputStream) {
                out.close();
            } else {
                out.flush();
            }
            out = null;
        }
        in.close();
        in = null;
        return total;
    }

    public static String a(String fileName) {
        if (fileName == null || fileName.indexOf(0) == -1) {
            return fileName;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            switch (c) {
                case 0:
                    sb.append("\\0");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        throw new InvalidFileNameException(fileName, "Invalid file name: " + sb);
    }
}

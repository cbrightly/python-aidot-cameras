package com.amazonaws.util;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public enum IOUtils {
    ;
    
    private static final int BUFFER_SIZE = 4096;
    private static final Log logger = null;

    static {
        logger = LogFactory.getLog(IOUtils.class);
    }

    public static byte[] toByteArray(InputStream is) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            while (true) {
                int read = is.read(b);
                int n = read;
                if (read == -1) {
                    return output.toByteArray();
                }
                output.write(b, 0, n);
            }
        } finally {
            output.close();
        }
    }

    public static String toString(InputStream is) {
        return new String(toByteArray(is), StringUtils.UTF8);
    }

    public static void closeQuietly(Closeable is, Log log) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Ignore failure in closing the Closeable", ex);
                }
            }
        }
    }

    public static void release(Closeable is, Log log) {
        closeQuietly(is, log);
    }

    public static long copy(InputStream in, OutputStream out) {
        byte[] buf = new byte[4096];
        long count = 0;
        while (true) {
            int read = in.read(buf);
            int n = read;
            if (read <= -1) {
                return count;
            }
            out.write(buf, 0, n);
            count += (long) n;
        }
    }
}

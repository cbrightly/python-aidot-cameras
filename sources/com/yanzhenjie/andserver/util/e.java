package com.yanzhenjie.andserver.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* compiled from: IOUtils */
public class e {
    private static final byte[] a = new byte[0];

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    public static InputStream b() {
        return new ByteArrayInputStream(a);
    }

    public static String g(InputStream input) {
        return new String(f(input));
    }

    public static String h(InputStream input, Charset charset) {
        return new String(f(input), charset);
    }

    public static byte[] f(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        i(input, output);
        output.close();
        return output.toByteArray();
    }

    public static void j(OutputStream output, byte[] data) {
        if (data != null) {
            output.write(data);
            output.flush();
        }
    }

    public static void i(InputStream input, OutputStream output) {
        byte[] buffer = new byte[4096];
        while (true) {
            int read = input.read(buffer);
            int len = read;
            if (read != -1) {
                output.write(buffer, 0, len);
                output.flush();
            } else {
                return;
            }
        }
    }

    public static boolean c(File targetFolder) {
        if (targetFolder.exists()) {
            if (targetFolder.isDirectory()) {
                return true;
            }
            targetFolder.delete();
        }
        return targetFolder.mkdirs();
    }

    public static boolean d(File targetFile) {
        if (targetFile.exists()) {
            e(targetFile);
        }
        try {
            return targetFile.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean e(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        } else if (!file.isDirectory()) {
            return true;
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File sonFile : files) {
                    e(sonFile);
                }
            }
            file.delete();
            return true;
        }
    }
}

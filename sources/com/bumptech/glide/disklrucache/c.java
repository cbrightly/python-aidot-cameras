package com.bumptech.glide.disklrucache;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: Util */
public final class c {
    static final Charset a = Charset.forName("US-ASCII");
    static final Charset b = Charset.forName("UTF-8");

    static void b(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            int length = files.length;
            int i = 0;
            while (i < length) {
                File file = files[i];
                if (file.isDirectory()) {
                    b(file);
                }
                if (file.delete()) {
                    i++;
                } else {
                    throw new IOException("failed to delete file: " + file);
                }
            }
            return;
        }
        throw new IOException("not a readable directory: " + dir);
    }

    static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }
}

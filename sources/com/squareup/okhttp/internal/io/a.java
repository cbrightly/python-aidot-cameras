package com.squareup.okhttp.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import okio.b0;
import okio.e0;
import okio.p;

/* compiled from: FileSystem */
public interface a {
    public static final a a = new C0211a();

    void a(File file);

    boolean b(File file);

    b0 c(File file);

    long d(File file);

    e0 e(File file);

    b0 f(File file);

    void g(File file, File file2);

    void h(File file);

    /* renamed from: com.squareup.okhttp.internal.io.a$a  reason: collision with other inner class name */
    /* compiled from: FileSystem */
    public static final class C0211a implements a {
        C0211a() {
        }

        public e0 e(File file) {
            return p.k(file);
        }

        public b0 f(File file) {
            try {
                return p.f(file);
            } catch (FileNotFoundException e) {
                file.getParentFile().mkdirs();
                return p.f(file);
            }
        }

        public b0 c(File file) {
            try {
                return p.a(file);
            } catch (FileNotFoundException e) {
                file.getParentFile().mkdirs();
                return p.a(file);
            }
        }

        public void h(File file) {
            if (!file.delete() && file.exists()) {
                throw new IOException("failed to delete " + file);
            }
        }

        public boolean b(File file) {
            return file.exists();
        }

        public long d(File file) {
            return file.length();
        }

        public void g(File from, File to) {
            h(to);
            if (!from.renameTo(to)) {
                throw new IOException("failed to rename " + from + " to " + to);
            }
        }

        public void a(File directory) {
            File[] files = directory.listFiles();
            if (files != null) {
                int length = files.length;
                int i = 0;
                while (i < length) {
                    File file = files[i];
                    if (file.isDirectory()) {
                        a(file);
                    }
                    if (file.delete()) {
                        i++;
                    } else {
                        throw new IOException("failed to delete " + file);
                    }
                }
                return;
            }
            throw new IOException("not a readable directory: " + directory);
        }
    }
}

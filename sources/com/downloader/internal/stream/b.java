package com.downloader.internal.stream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/* compiled from: FileDownloadRandomAccessFile */
public class b implements a {
    private final BufferedOutputStream a;
    private final FileDescriptor b;
    private final RandomAccessFile c;

    private b(File file) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        this.c = randomAccessFile;
        this.b = randomAccessFile.getFD();
        this.a = new BufferedOutputStream(new FileOutputStream(randomAccessFile.getFD()));
    }

    public void write(byte[] b2, int off, int len) {
        this.a.write(b2, off, len);
    }

    public void a() {
        this.a.flush();
        this.b.sync();
    }

    public void close() {
        this.a.close();
        this.c.close();
    }

    public void b(long offset) {
        this.c.seek(offset);
    }

    public static a c(File file) {
        return new b(file);
    }
}

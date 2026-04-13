package com.amazonaws.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ResettableInputStream extends ReleasableInputStream {
    private static final Log log = LogFactory.getLog(ResettableInputStream.class);
    private final File file;
    private final FileChannel fileChannel;
    private final FileInputStream fis;
    private long markPos;

    public ResettableInputStream(File file2) {
        this(new FileInputStream(file2), file2);
    }

    public ResettableInputStream(FileInputStream fis2) {
        this(fis2, (File) null);
    }

    private ResettableInputStream(FileInputStream fis2, File file2) {
        super(fis2);
        this.file = file2;
        this.fis = fis2;
        FileChannel channel = fis2.getChannel();
        this.fileChannel = channel;
        this.markPos = channel.position();
    }

    public final boolean markSupported() {
        return true;
    }

    public void mark(int position) {
        abortIfNeeded();
        try {
            this.markPos = this.fileChannel.position();
            Log log2 = log;
            if (log2.isTraceEnabled()) {
                log2.trace("File input stream marked at position " + this.markPos);
            }
        } catch (IOException e) {
            throw new AmazonClientException("Failed to mark the file position", e);
        }
    }

    public void reset() {
        abortIfNeeded();
        this.fileChannel.position(this.markPos);
        Log log2 = log;
        if (log2.isTraceEnabled()) {
            log2.trace("Reset to position " + this.markPos);
        }
    }

    public int available() {
        abortIfNeeded();
        return this.fis.available();
    }

    public int read() {
        abortIfNeeded();
        return this.fis.read();
    }

    public long skip(long n) {
        abortIfNeeded();
        return this.fis.skip(n);
    }

    public int read(byte[] arg0, int arg1, int arg2) {
        abortIfNeeded();
        return this.fis.read(arg0, arg1, arg2);
    }

    public File getFile() {
        return this.file;
    }

    public static ResettableInputStream newResettableInputStream(File file2) {
        return newResettableInputStream(file2, (String) null);
    }

    public static ResettableInputStream newResettableInputStream(File file2, String errmsg) {
        AmazonClientException amazonClientException;
        try {
            return new ResettableInputStream(file2);
        } catch (IOException e) {
            if (errmsg != null) {
                amazonClientException = new AmazonClientException(errmsg, e);
            }
            throw amazonClientException;
        }
    }

    public static ResettableInputStream newResettableInputStream(FileInputStream fis2) {
        return newResettableInputStream(fis2, (String) null);
    }

    public static ResettableInputStream newResettableInputStream(FileInputStream fis2, String errmsg) {
        try {
            return new ResettableInputStream(fis2);
        } catch (IOException e) {
            throw new AmazonClientException(errmsg, e);
        }
    }
}

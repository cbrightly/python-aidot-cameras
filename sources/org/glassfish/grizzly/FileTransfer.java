package org.glassfish.grizzly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import org.glassfish.grizzly.asyncqueue.WritableMessage;

public class FileTransfer implements WritableMessage {
    private FileChannel fileChannel;
    private long len;
    private long pos;

    public FileTransfer(File f) {
        this(f, 0, f.length());
    }

    public FileTransfer(File f, long pos2, long len2) {
        if (f == null) {
            throw new IllegalArgumentException("f cannot be null.");
        } else if (!f.exists()) {
            throw new IllegalArgumentException("File " + f.getAbsolutePath() + " does not exist.");
        } else if (!f.canRead()) {
            throw new IllegalArgumentException("File " + f.getAbsolutePath() + " is not readable.");
        } else if (f.isDirectory()) {
            throw new IllegalArgumentException("File " + f.getAbsolutePath() + " is a directory.");
        } else if (pos2 < 0) {
            throw new IllegalArgumentException("The pos argument cannot be negative.");
        } else if (len2 < 0) {
            throw new IllegalArgumentException("The len argument cannot be negative.");
        } else if (pos2 > f.length()) {
            throw new IllegalArgumentException("Illegal offset");
        } else if (f.length() - pos2 >= len2) {
            this.pos = pos2;
            this.len = len2;
            try {
                this.fileChannel = new FileInputStream(f).getChannel();
            } catch (FileNotFoundException fnfe) {
                throw new IllegalStateException(fnfe);
            }
        } else {
            throw new IllegalArgumentException("Specified length exceeds available bytes to transfer.");
        }
    }

    public long writeTo(WritableByteChannel c) {
        long written = this.fileChannel.transferTo(this.pos, this.len, c);
        this.pos += written;
        this.len -= written;
        return written;
    }

    public boolean hasRemaining() {
        return this.len != 0;
    }

    public int remaining() {
        long j = this.len;
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    public boolean release() {
        try {
            this.fileChannel.close();
        } catch (IOException e) {
        } catch (Throwable th) {
            this.fileChannel = null;
            throw th;
        }
        this.fileChannel = null;
        return true;
    }

    public boolean isExternal() {
        return true;
    }
}

package io.netty.channel;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class DefaultFileRegion extends AbstractReferenceCounted implements FileRegion {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultFileRegion.class);
    private final long count;
    private final File f;
    private FileChannel file;
    private final long position;
    private long transfered;

    public DefaultFileRegion(FileChannel file2, long position2, long count2) {
        if (file2 == null) {
            throw new NullPointerException("file");
        } else if (position2 < 0) {
            throw new IllegalArgumentException("position must be >= 0 but was " + position2);
        } else if (count2 >= 0) {
            this.file = file2;
            this.position = position2;
            this.count = count2;
            this.f = null;
        } else {
            throw new IllegalArgumentException("count must be >= 0 but was " + count2);
        }
    }

    public DefaultFileRegion(File f2, long position2, long count2) {
        if (f2 == null) {
            throw new NullPointerException("f");
        } else if (position2 < 0) {
            throw new IllegalArgumentException("position must be >= 0 but was " + position2);
        } else if (count2 >= 0) {
            this.position = position2;
            this.count = count2;
            this.f = f2;
        } else {
            throw new IllegalArgumentException("count must be >= 0 but was " + count2);
        }
    }

    public boolean isOpen() {
        return this.file != null;
    }

    public void open() {
        if (!isOpen() && refCnt() > 0) {
            this.file = new RandomAccessFile(this.f, "r").getChannel();
        }
    }

    public long position() {
        return this.position;
    }

    public long count() {
        return this.count;
    }

    public long transfered() {
        return this.transfered;
    }

    public long transferTo(WritableByteChannel target, long position2) {
        long count2 = this.count - position2;
        if (count2 < 0 || position2 < 0) {
            throw new IllegalArgumentException("position out of range: " + position2 + " (expected: 0 - " + (this.count - 1) + ')');
        } else if (count2 == 0) {
            return 0;
        } else {
            if (refCnt() != 0) {
                open();
                long written = this.file.transferTo(this.position + position2, count2, target);
                if (written > 0) {
                    this.transfered += written;
                }
                return written;
            }
            throw new IllegalReferenceCountException(0);
        }
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        FileChannel file2 = this.file;
        if (file2 != null) {
            this.file = null;
            try {
                file2.close();
            } catch (IOException e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a file.", (Throwable) e);
                }
            }
        }
    }
}

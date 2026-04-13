package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.FileRegion;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.WritableByteChannel;

public abstract class OioByteStreamChannel extends AbstractOioByteChannel {
    private static final InputStream CLOSED_IN = new InputStream() {
        public int read() {
            return -1;
        }
    };
    private static final OutputStream CLOSED_OUT = new OutputStream() {
        public void write(int b) {
            throw new ClosedChannelException();
        }
    };
    private InputStream is;
    private OutputStream os;
    private WritableByteChannel outChannel;

    protected OioByteStreamChannel(Channel parent) {
        super(parent);
    }

    /* access modifiers changed from: protected */
    public final void activate(InputStream is2, OutputStream os2) {
        if (this.is != null) {
            throw new IllegalStateException("input was set already");
        } else if (this.os != null) {
            throw new IllegalStateException("output was set already");
        } else if (is2 == null) {
            throw new NullPointerException("is");
        } else if (os2 != null) {
            this.is = is2;
            this.os = os2;
        } else {
            throw new NullPointerException("os");
        }
    }

    public boolean isActive() {
        OutputStream os2;
        InputStream is2 = this.is;
        if (is2 == null || is2 == CLOSED_IN || (os2 = this.os) == null || os2 == CLOSED_OUT) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public int available() {
        try {
            return this.is.available();
        } catch (IOException e) {
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public int doReadBytes(ByteBuf buf) {
        return buf.writeBytes(this.is, Math.max(1, Math.min(available(), buf.maxWritableBytes())));
    }

    /* access modifiers changed from: protected */
    public void doWriteBytes(ByteBuf buf) {
        OutputStream os2 = this.os;
        if (os2 != null) {
            buf.readBytes(os2, buf.readableBytes());
            return;
        }
        throw new NotYetConnectedException();
    }

    /* access modifiers changed from: protected */
    public void doWriteFileRegion(FileRegion region) {
        OutputStream os2 = this.os;
        if (os2 != null) {
            if (this.outChannel == null) {
                this.outChannel = Channels.newChannel(os2);
            }
            long written = 0;
            do {
                long localWritten = region.transferTo(this.outChannel, written);
                if (localWritten == -1) {
                    checkEOF(region);
                    return;
                }
                written += localWritten;
            } while (written < region.count());
            return;
        }
        throw new NotYetConnectedException();
    }

    private static void checkEOF(FileRegion region) {
        if (region.transfered() < region.count()) {
            throw new EOFException("Expected to be able to write " + region.count() + " bytes, but only wrote " + region.transfered());
        }
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        InputStream is2 = this.is;
        OutputStream os2 = this.os;
        this.is = CLOSED_IN;
        this.os = CLOSED_OUT;
        if (is2 != null) {
            try {
                is2.close();
            } catch (Throwable th) {
                if (os2 != null) {
                    os2.close();
                }
                throw th;
            }
        }
        if (os2 != null) {
            os2.close();
        }
    }
}

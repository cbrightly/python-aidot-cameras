package com.didichuxing.doraemonkit.kit.network.stream;

import com.didichuxing.doraemonkit.kit.network.core.ResponseHandler;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class InputStreamProxy extends FilterInputStream {
    private static final int BUFFER_SIZE = 1024;
    public static final String TAG = "ResponseHandlingInputStream";
    private boolean mClosed;
    private final ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
    private final ResponseHandler mResponseHandler;
    private byte[] mSkipBuffer;

    public InputStreamProxy(InputStream inputStream, ResponseHandler responseHandler) {
        super(inputStream);
        this.mResponseHandler = responseHandler;
        this.mClosed = false;
    }

    private synchronized int checkEOF(int n) {
        if (n == -1) {
            ResponseHandler responseHandler = this.mResponseHandler;
            if (responseHandler != null) {
                responseHandler.onEOF(this.mOutputStream);
            }
            closeOutputStreamQuietly();
        }
        return n;
    }

    public int read() {
        try {
            int result = checkEOF(this.in.read());
            if (result != -1) {
                writeToOutputStream(result);
            }
            return result;
        } catch (IOException ex) {
            throw handleIOException(ex);
        }
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) {
        try {
            int result = checkEOF(this.in.read(b, off, len));
            if (result != -1) {
                writeToOutputStream(b, off, result);
            }
            return result;
        } catch (IOException ex) {
            throw handleIOException(ex);
        }
    }

    public synchronized long skip(long n) {
        long total;
        byte[] buffer = getSkipBufferLocked();
        total = 0;
        while (total < n) {
            int result = read(buffer, 0, (int) Math.min((long) buffer.length, n - total));
            if (result == -1) {
                break;
            }
            total += (long) result;
        }
        return total;
    }

    private byte[] getSkipBufferLocked() {
        if (this.mSkipBuffer == null) {
            this.mSkipBuffer = new byte[1024];
        }
        return this.mSkipBuffer;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readlimit) {
    }

    public void reset() {
        throw new UnsupportedOperationException("Mark not supported");
    }

    public void close() {
        super.close();
        closeOutputStreamQuietly();
    }

    private synchronized void closeOutputStreamQuietly() {
        if (!this.mClosed) {
            try {
                this.mOutputStream.close();
                this.mClosed = true;
            } catch (IOException e) {
                this.mClosed = true;
            } catch (Throwable th) {
                this.mClosed = true;
                throw th;
            }
        }
    }

    private IOException handleIOException(IOException ex) {
        ResponseHandler responseHandler = this.mResponseHandler;
        if (responseHandler != null) {
            responseHandler.onError(ex);
        }
        return ex;
    }

    private synchronized void writeToOutputStream(int oneByte) {
        if (!this.mClosed) {
            this.mOutputStream.write(oneByte);
        }
    }

    private synchronized void writeToOutputStream(byte[] b, int offset, int count) {
        if (!this.mClosed) {
            this.mOutputStream.write(b, offset, count);
        }
    }
}

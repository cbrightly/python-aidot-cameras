package com.didichuxing.doraemonkit.kit.network.stream;

import com.didichuxing.doraemonkit.kit.network.utils.ExceptionUtil;
import com.didichuxing.doraemonkit.kit.network.utils.StreamUtil;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;

public class GunzippingOutputStream extends FilterOutputStream {
    private static final ExecutorService sExecutor = Executors.newCachedThreadPool();
    private final Future<Void> mCopyFuture;

    public static GunzippingOutputStream create(OutputStream finalOut) {
        PipedInputStream pipeIn = new PipedInputStream();
        return new GunzippingOutputStream(new PipedOutputStream(pipeIn), sExecutor.submit(new GunzippingCallable(pipeIn, finalOut)));
    }

    private GunzippingOutputStream(OutputStream out, Future<Void> copyFuture) {
        super(out);
        this.mCopyFuture = copyFuture;
    }

    public void close() {
        try {
            super.close();
            try {
                getAndRethrow(this.mCopyFuture);
            } catch (IOException e) {
                if (1 != 0) {
                    throw e;
                }
            }
        } catch (Throwable e2) {
            try {
                getAndRethrow(this.mCopyFuture);
            } catch (IOException e3) {
                if (0 != 0) {
                    throw e3;
                }
            }
            throw e2;
        }
    }

    private static <T> T getAndRethrow(Future<T> future) {
        while (true) {
            try {
                return future.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
                Throwable cause = e2.getCause();
                ExceptionUtil.propagateIfInstanceOf(cause, IOException.class);
                ExceptionUtil.propagate(cause);
            }
        }
    }

    public static class GunzippingCallable implements Callable<Void> {
        private final InputStream mIn;
        private final OutputStream mOut;

        public GunzippingCallable(InputStream in, OutputStream out) {
            this.mIn = in;
            this.mOut = out;
        }

        /* JADX INFO: finally extract failed */
        public Void call() {
            GZIPInputStream in = new GZIPInputStream(this.mIn);
            try {
                StreamUtil.copy(in, this.mOut, new byte[1024]);
                in.close();
                this.mOut.close();
                return null;
            } catch (Throwable th) {
                in.close();
                this.mOut.close();
                throw th;
            }
        }
    }
}

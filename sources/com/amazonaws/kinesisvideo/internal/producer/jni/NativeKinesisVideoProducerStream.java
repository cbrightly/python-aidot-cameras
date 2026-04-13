package com.amazonaws.kinesisvideo.internal.producer.jni;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoMetrics;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoStreamMetrics;
import com.amazonaws.kinesisvideo.internal.producer.ReadResult;
import com.amazonaws.kinesisvideo.producer.FrameFlags;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFragmentAck;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import com.amazonaws.kinesisvideo.producer.ProducerException;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NativeKinesisVideoProducerStream implements KinesisVideoProducerStream {
    private static final int SERVICE_CALL_RESULT_OK = 200;
    private final Map<Long, NativeDataInputStream> mInputStreamMap;
    /* access modifiers changed from: private */
    public final NativeKinesisVideoProducerJni mKinesisVideoProducerJni;
    /* access modifiers changed from: private */
    public final Log mLog;
    private final CountDownLatch mReadyLatch;
    private final CountDownLatch mStoppedLatch;
    private final StreamCallbacks mStreamCallbacks;
    /* access modifiers changed from: private */
    public final long mStreamHandle;
    /* access modifiers changed from: private */
    public final StreamInfo mStreamInfo;
    private final KinesisVideoStreamMetrics mStreamMetrics;

    public class NativeDataInputStream extends InputStream {
        private long mAvailableDataSize = 0;
        private boolean mDataAvailable = false;
        private final Object mMonitor = new Object();
        private final ReadResult mReadResult;
        private volatile boolean mStreamClosed = false;
        final long mUploadHandle;

        public NativeDataInputStream(long uploadHandle) {
            this.mUploadHandle = uploadHandle;
            this.mReadResult = new ReadResult();
        }

        public int read() {
            throw new IOException("Can't call byte-by-byte");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$300(r1.this$0).getStreamData(com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$200(r1.this$0), r16, r17, r18, r1.mReadResult);
            r5 = r1.mReadResult.getReadBytes();
            com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$100(r1.this$0).debug("getStreamData fill %d bytes for stream %s with uploadHandle %d", java.lang.Integer.valueOf(r5), com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$000(r1.this$0).getName(), java.lang.Long.valueOf(r1.mUploadHandle));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ec, code lost:
            if (r1.mReadResult.isEndOfStream() == false) goto L_0x015a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00f8, code lost:
            if (r1.mReadResult.getUploadHandle() != r1.mUploadHandle) goto L_0x0122;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00fa, code lost:
            com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$100(r1.this$0).info("Received end-of-stream indicator for %s, uploadHandle %d", com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$000(r1.this$0).getName(), java.lang.Long.valueOf(r1.mUploadHandle));
            r1.mStreamClosed = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x011d, code lost:
            if (r5 != 0) goto L_0x015a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x011f, code lost:
            r5 = -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0122, code lost:
            com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$100(r1.this$0).debug("Found end of stream for stream %s on uploadHandle %d for previous uploadHandle %d", com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$000(r1.this$0).getName(), java.lang.Long.valueOf(r1.mUploadHandle), java.lang.Long.valueOf(r1.mReadResult.getUploadHandle()));
            com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$400(r1.this$0, r1.mReadResult.getUploadHandle());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x015a, code lost:
            r8 = r1.mMonitor;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x015c, code lost:
            monitor-enter(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x015d, code lost:
            if (r5 == 0) goto L_0x016f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x015f, code lost:
            if (r5 == -1) goto L_0x016d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0169, code lost:
            if ((r1.mAvailableDataSize - ((long) r5)) <= 0) goto L_0x016d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x016b, code lost:
            r1.mDataAvailable = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x016d, code lost:
            monitor-exit(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x016f, code lost:
            monitor-exit(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x0175, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0176, code lost:
            com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.access$100(r1.this$0).exception(r0, "Reader threw an exception", new java.lang.Object[0]);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0188, code lost:
            throw new java.io.IOException(r0);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int read(byte[] r16, int r17, int r18) {
            /*
                r15 = this;
                r1 = r15
                boolean r0 = r1.mStreamClosed
                r2 = 2
                r3 = 1
                r4 = 0
                if (r0 == 0) goto L_0x0029
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog
                java.lang.String r5 = "Stream %s with uploadHandle %d has been closed"
                java.lang.Object[] r6 = new java.lang.Object[r2]
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r7 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.producer.StreamInfo r7 = r7.mStreamInfo
                java.lang.String r7 = r7.getName()
                r6[r4] = r7
                long r7 = r1.mUploadHandle
                java.lang.Long r7 = java.lang.Long.valueOf(r7)
                r6[r3] = r7
                r0.warn(r5, r6)
            L_0x0029:
                r0 = -1
                r5 = r0
            L_0x002b:
                boolean r0 = r1.mStreamClosed
                r6 = -1
                r7 = 3
                if (r0 != 0) goto L_0x018c
                java.lang.Object r8 = r1.mMonitor
                monitor-enter(r8)
            L_0x0034:
                boolean r0 = r1.mDataAvailable     // Catch:{ all -> 0x0189 }
                if (r0 != 0) goto L_0x0076
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ InterruptedException -> 0x005f }
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog     // Catch:{ InterruptedException -> 0x005f }
                java.lang.String r9 = "no data for stream %s with uploadHandle %d, waiting"
                java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x005f }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r11 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ InterruptedException -> 0x005f }
                com.amazonaws.kinesisvideo.producer.StreamInfo r11 = r11.mStreamInfo     // Catch:{ InterruptedException -> 0x005f }
                java.lang.String r11 = r11.getName()     // Catch:{ InterruptedException -> 0x005f }
                r10[r4] = r11     // Catch:{ InterruptedException -> 0x005f }
                long r11 = r1.mUploadHandle     // Catch:{ InterruptedException -> 0x005f }
                java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch:{ InterruptedException -> 0x005f }
                r10[r3] = r11     // Catch:{ InterruptedException -> 0x005f }
                r0.debug(r9, r10)     // Catch:{ InterruptedException -> 0x005f }
                java.lang.Object r0 = r1.mMonitor     // Catch:{ InterruptedException -> 0x005f }
                r0.wait()     // Catch:{ InterruptedException -> 0x005f }
                goto L_0x0075
            L_0x005f:
                r0 = move-exception
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r9 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ all -> 0x0189 }
                com.amazonaws.kinesisvideo.common.logging.Log r9 = r9.mLog     // Catch:{ all -> 0x0189 }
                java.lang.String r10 = "Waiting for the data availability with uploadHandle %dthrew an interrupted exception. Continuing..."
                java.lang.Object[] r11 = new java.lang.Object[r3]     // Catch:{ all -> 0x0189 }
                long r12 = r1.mUploadHandle     // Catch:{ all -> 0x0189 }
                java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0189 }
                r11[r4] = r12     // Catch:{ all -> 0x0189 }
                r9.exception(r0, r10, r11)     // Catch:{ all -> 0x0189 }
            L_0x0075:
                goto L_0x0034
            L_0x0076:
                r1.mDataAvailable = r4     // Catch:{ all -> 0x0189 }
                boolean r0 = r1.mStreamClosed     // Catch:{ all -> 0x0189 }
                if (r0 == 0) goto L_0x00a0
                r5 = -1
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ all -> 0x0189 }
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog     // Catch:{ all -> 0x0189 }
                java.lang.String r6 = "Being notified to close stream %s with uploadHandle %d"
                java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0189 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r7 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ all -> 0x0189 }
                com.amazonaws.kinesisvideo.producer.StreamInfo r7 = r7.mStreamInfo     // Catch:{ all -> 0x0189 }
                java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0189 }
                r2[r4] = r7     // Catch:{ all -> 0x0189 }
                long r9 = r1.mUploadHandle     // Catch:{ all -> 0x0189 }
                java.lang.Long r4 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x0189 }
                r2[r3] = r4     // Catch:{ all -> 0x0189 }
                r0.debug(r6, r2)     // Catch:{ all -> 0x0189 }
                monitor-exit(r8)     // Catch:{ all -> 0x0189 }
                return r5
            L_0x00a0:
                monitor-exit(r8)     // Catch:{ all -> 0x0189 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerJni r8 = r0.mKinesisVideoProducerJni     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                long r9 = r0.mStreamHandle     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.ReadResult r14 = r1.mReadResult     // Catch:{ ProducerException -> 0x0175 }
                r11 = r16
                r12 = r17
                r13 = r18
                r8.getStreamData(r9, r11, r12, r13, r14)     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.ReadResult r0 = r1.mReadResult     // Catch:{ ProducerException -> 0x0175 }
                int r0 = r0.getReadBytes()     // Catch:{ ProducerException -> 0x0175 }
                r5 = r0
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog     // Catch:{ ProducerException -> 0x0175 }
                java.lang.String r8 = "getStreamData fill %d bytes for stream %s with uploadHandle %d"
                java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch:{ ProducerException -> 0x0175 }
                java.lang.Integer r10 = java.lang.Integer.valueOf(r5)     // Catch:{ ProducerException -> 0x0175 }
                r9[r4] = r10     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r10 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.producer.StreamInfo r10 = r10.mStreamInfo     // Catch:{ ProducerException -> 0x0175 }
                java.lang.String r10 = r10.getName()     // Catch:{ ProducerException -> 0x0175 }
                r9[r3] = r10     // Catch:{ ProducerException -> 0x0175 }
                long r10 = r1.mUploadHandle     // Catch:{ ProducerException -> 0x0175 }
                java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ ProducerException -> 0x0175 }
                r9[r2] = r10     // Catch:{ ProducerException -> 0x0175 }
                r0.debug(r8, r9)     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.ReadResult r0 = r1.mReadResult     // Catch:{ ProducerException -> 0x0175 }
                boolean r0 = r0.isEndOfStream()     // Catch:{ ProducerException -> 0x0175 }
                if (r0 == 0) goto L_0x015a
                com.amazonaws.kinesisvideo.internal.producer.ReadResult r0 = r1.mReadResult     // Catch:{ ProducerException -> 0x0175 }
                long r8 = r0.getUploadHandle()     // Catch:{ ProducerException -> 0x0175 }
                long r10 = r1.mUploadHandle     // Catch:{ ProducerException -> 0x0175 }
                int r0 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                if (r0 != 0) goto L_0x0122
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog     // Catch:{ ProducerException -> 0x0175 }
                java.lang.String r8 = "Received end-of-stream indicator for %s, uploadHandle %d"
                java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r10 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.producer.StreamInfo r10 = r10.mStreamInfo     // Catch:{ ProducerException -> 0x0175 }
                java.lang.String r10 = r10.getName()     // Catch:{ ProducerException -> 0x0175 }
                r9[r4] = r10     // Catch:{ ProducerException -> 0x0175 }
                long r10 = r1.mUploadHandle     // Catch:{ ProducerException -> 0x0175 }
                java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ ProducerException -> 0x0175 }
                r9[r3] = r10     // Catch:{ ProducerException -> 0x0175 }
                r0.info(r8, r9)     // Catch:{ ProducerException -> 0x0175 }
                r1.mStreamClosed = r3     // Catch:{ ProducerException -> 0x0175 }
                if (r5 != 0) goto L_0x015a
                r0 = -1
                r5 = r0
                goto L_0x015a
            L_0x0122:
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog     // Catch:{ ProducerException -> 0x0175 }
                java.lang.String r8 = "Found end of stream for stream %s on uploadHandle %d for previous uploadHandle %d"
                java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r10 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.producer.StreamInfo r10 = r10.mStreamInfo     // Catch:{ ProducerException -> 0x0175 }
                java.lang.String r10 = r10.getName()     // Catch:{ ProducerException -> 0x0175 }
                r9[r4] = r10     // Catch:{ ProducerException -> 0x0175 }
                long r10 = r1.mUploadHandle     // Catch:{ ProducerException -> 0x0175 }
                java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ ProducerException -> 0x0175 }
                r9[r3] = r10     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.ReadResult r10 = r1.mReadResult     // Catch:{ ProducerException -> 0x0175 }
                long r10 = r10.getUploadHandle()     // Catch:{ ProducerException -> 0x0175 }
                java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ ProducerException -> 0x0175 }
                r9[r2] = r10     // Catch:{ ProducerException -> 0x0175 }
                r0.debug(r8, r9)     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this     // Catch:{ ProducerException -> 0x0175 }
                com.amazonaws.kinesisvideo.internal.producer.ReadResult r8 = r1.mReadResult     // Catch:{ ProducerException -> 0x0175 }
                long r8 = r8.getUploadHandle()     // Catch:{ ProducerException -> 0x0175 }
                r0.notifyEndOfStream(r8)     // Catch:{ ProducerException -> 0x0175 }
            L_0x015a:
                java.lang.Object r8 = r1.mMonitor     // Catch:{ ProducerException -> 0x0175 }
                monitor-enter(r8)     // Catch:{ ProducerException -> 0x0175 }
                if (r5 == 0) goto L_0x016f
                if (r5 == r6) goto L_0x016d
                long r9 = r1.mAvailableDataSize     // Catch:{ all -> 0x0172 }
                long r11 = (long) r5     // Catch:{ all -> 0x0172 }
                long r9 = r9 - r11
                r11 = 0
                int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
                if (r0 <= 0) goto L_0x016d
                r1.mDataAvailable = r3     // Catch:{ all -> 0x0172 }
            L_0x016d:
                monitor-exit(r8)     // Catch:{ all -> 0x0172 }
                goto L_0x018c
            L_0x016f:
                monitor-exit(r8)     // Catch:{ all -> 0x0172 }
                goto L_0x002b
            L_0x0172:
                r0 = move-exception
                monitor-exit(r8)     // Catch:{ all -> 0x0172 }
                throw r0     // Catch:{ ProducerException -> 0x0175 }
            L_0x0175:
                r0 = move-exception
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r2 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.common.logging.Log r2 = r2.mLog
                java.lang.String r3 = "Reader threw an exception"
                java.lang.Object[] r4 = new java.lang.Object[r4]
                r2.exception(r0, r3, r4)
                java.io.IOException r2 = new java.io.IOException
                r2.<init>(r0)
                throw r2
            L_0x0189:
                r0 = move-exception
                monitor-exit(r8)     // Catch:{ all -> 0x0189 }
                throw r0
            L_0x018c:
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog
                java.lang.String r8 = "Streamed %d bytes for stream %s with uploadHandle %d"
                java.lang.Object[] r7 = new java.lang.Object[r7]
                java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
                r7[r4] = r9
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r9 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.producer.StreamInfo r9 = r9.mStreamInfo
                java.lang.String r9 = r9.getName()
                r7[r3] = r9
                long r9 = r1.mUploadHandle
                java.lang.Long r9 = java.lang.Long.valueOf(r9)
                r7[r2] = r9
                r0.debug(r8, r7)
                if (r6 != r5) goto L_0x01d6
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r0 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.common.logging.Log r0 = r0.mLog
                java.lang.String r6 = "Closing stream %s with uploadHandle %d"
                java.lang.Object[] r2 = new java.lang.Object[r2]
                com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream r7 = com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.this
                com.amazonaws.kinesisvideo.producer.StreamInfo r7 = r7.mStreamInfo
                java.lang.String r7 = r7.getName()
                r2[r4] = r7
                long r7 = r1.mUploadHandle
                java.lang.Long r4 = java.lang.Long.valueOf(r7)
                r2[r3] = r4
                r0.debug(r6, r2)
            L_0x01d6:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerStream.NativeDataInputStream.read(byte[], int, int):int");
        }

        public int read(byte[] b) {
            return read(b, 0, b.length);
        }

        public void close() {
            this.mStreamClosed = true;
            notifyReaderThread(0, 0);
        }

        /* access modifiers changed from: protected */
        public void notifyReaderThread(long duration, long availableSize) {
            synchronized (this.mMonitor) {
                this.mAvailableDataSize = availableSize;
                NativeKinesisVideoProducerStream.this.mLog.debug("Data availability notification. Upload handle: %d, Size: %d, Duration %d ", Long.valueOf(this.mUploadHandle), Long.valueOf(availableSize), Long.valueOf(duration));
                this.mDataAvailable = true;
                this.mMonitor.notify();
            }
        }

        /* access modifiers changed from: protected */
        public void endOfReaderThread() {
            synchronized (this.mMonitor) {
                this.mDataAvailable = true;
                this.mStreamClosed = true;
                this.mMonitor.notify();
            }
        }
    }

    public NativeKinesisVideoProducerStream(@NonNull NativeKinesisVideoProducerJni kinesisVideoProducerJni, @NonNull StreamInfo streamInfo, long streamHandle, @NonNull Log log, @Nullable StreamCallbacks streamCallbacks) {
        this.mKinesisVideoProducerJni = (NativeKinesisVideoProducerJni) Preconditions.checkNotNull(kinesisVideoProducerJni);
        this.mStreamInfo = (StreamInfo) Preconditions.checkNotNull(streamInfo);
        Preconditions.checkState(streamHandle != 0);
        this.mStreamHandle = streamHandle;
        this.mStreamCallbacks = streamCallbacks;
        this.mReadyLatch = new CountDownLatch(1);
        this.mStoppedLatch = new CountDownLatch(1);
        this.mLog = (Log) Preconditions.checkNotNull(log);
        this.mStreamMetrics = new KinesisVideoStreamMetrics();
        this.mInputStreamMap = new HashMap();
    }

    public InputStream getDataStream(long uploadHandle) {
        NativeDataInputStream inputStream = new NativeDataInputStream(uploadHandle);
        this.mInputStreamMap.put(Long.valueOf(uploadHandle), inputStream);
        return inputStream;
    }

    public void getStreamData(@NonNull byte[] fillBuffer, int offset, int length, @NonNull ReadResult readResult) {
        this.mKinesisVideoProducerJni.getStreamData(this.mStreamHandle, fillBuffer, offset, length, readResult);
    }

    public void putFrame(@NonNull KinesisVideoFrame kinesisVideoFrame) {
        Preconditions.checkNotNull(kinesisVideoFrame);
        this.mLog.debug("PutFrame index: %s, pts: %s, dts: %s, duration: %s, keyFrame: %s, flags: %s", Integer.valueOf(kinesisVideoFrame.getIndex()), Long.valueOf(kinesisVideoFrame.getPresentationTs()), Long.valueOf(kinesisVideoFrame.getDecodingTs()), Long.valueOf(kinesisVideoFrame.getDuration()), Boolean.valueOf(FrameFlags.isKeyFrame(kinesisVideoFrame.getFlags())), Integer.valueOf(kinesisVideoFrame.getFlags()));
        if (FrameFlags.isKeyFrame(kinesisVideoFrame.getFlags())) {
            KinesisVideoMetrics kinesisVideoMetrics = this.mKinesisVideoProducerJni.getMetrics();
            KinesisVideoStreamMetrics streamMetrics = getMetrics();
            this.mLog.debug("Kinesis Video client and stream metrics\n\t>> Overall storage size: %s\n\t>> Available storage size: %s\n\t>> Allocated storage size: %s\n\t>> Total view allocation size: %s\n\t>> Total streams frame rate: %s\n\t>> Total streams transfer rate: %s\n\t>> Current view duration: %s\n\t>> Overall view duration: %s\n\t>> Current view size: %s\n\t>> Overall view size: %s\n\t>> Current frame rate: %s\n\t>> Current transfer rate: %s", Long.valueOf(kinesisVideoMetrics.getContentStoreSize()), Long.valueOf(kinesisVideoMetrics.getContentStoreAvailableSize()), Long.valueOf(kinesisVideoMetrics.getContentStoreAllocatedSize()), Long.valueOf(kinesisVideoMetrics.getTotalContentViewSize()), Long.valueOf(kinesisVideoMetrics.getTotalFrameRate()), Long.valueOf(kinesisVideoMetrics.getTotalTransferRate()), Long.valueOf(streamMetrics.getCurrentViewDurationInTimeUnits()), Long.valueOf(streamMetrics.getOverallViewDurationInTimeUnits()), Long.valueOf(streamMetrics.getCurrentViewSize()), Long.valueOf(streamMetrics.getOverallViewSize()), Double.valueOf(streamMetrics.getCurrentFrameRate()), Long.valueOf(streamMetrics.getCurrentTransferRate()));
        }
        this.mKinesisVideoProducerJni.putFrame(this.mStreamHandle, kinesisVideoFrame);
    }

    public void putFragmentMetadata(@NonNull String metadataName, @NonNull String metadataValue, boolean persistent) {
        Preconditions.checkNotNull(metadataName);
        Preconditions.checkNotNull(metadataValue);
        this.mKinesisVideoProducerJni.putFragmentMetadata(this.mStreamHandle, metadataName, metadataValue, persistent);
    }

    public void fragmentAck(long uploadHandle, @NonNull KinesisVideoFragmentAck kinesisVideoFragmentAck) {
        Preconditions.checkNotNull(kinesisVideoFragmentAck);
        this.mKinesisVideoProducerJni.fragmentAck(this.mStreamHandle, uploadHandle, kinesisVideoFragmentAck);
    }

    public void parseFragmentAck(long uploadHandle, @NonNull String kinesisVideoFragmentAck) {
        Preconditions.checkNotNull(kinesisVideoFragmentAck);
        this.mKinesisVideoProducerJni.parseFragmentAck(this.mStreamHandle, uploadHandle, kinesisVideoFragmentAck);
    }

    public void streamFormatChanged(@Nullable byte[] codecPrivateData) {
        this.mKinesisVideoProducerJni.streamFormatChanged(this.mStreamHandle, codecPrivateData);
    }

    public void streamTerminated(long uploadHandle, int statusCode) {
        this.mKinesisVideoProducerJni.streamTerminated(this.mStreamHandle, uploadHandle, statusCode);
    }

    public void stopStream() {
        this.mKinesisVideoProducerJni.stopStream(this.mStreamHandle);
    }

    public void stopStreamSync() {
        stopStream();
        try {
            awaitStopped();
        } catch (ProducerException e) {
            this.mLog.exception(e, "Stopping stream threw an exception. Force stopping the input stream.", new Object[0]);
        }
    }

    @NonNull
    public KinesisVideoStreamMetrics getMetrics() {
        this.mKinesisVideoProducerJni.getStreamMetrics(this.mStreamHandle, this.mStreamMetrics);
        return this.mStreamMetrics;
    }

    public String getStreamName() {
        return this.mStreamInfo.getName();
    }

    public long getStreamHandle() {
        return this.mStreamHandle;
    }

    public void streamUnderflowReport() {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamUnderflowReport();
        }
    }

    public void streamLatencyPressure(long duration) {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamLatencyPressure(duration);
        }
    }

    public void streamConnectionStale(long lastAckDuration) {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamConnectionStale(lastAckDuration);
        }
    }

    public void fragmentAckReceived(@NonNull KinesisVideoFragmentAck fragmentAck) {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.fragmentAckReceived(fragmentAck);
        }
    }

    public void droppedFrameReport(long frameTimecode) {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.droppedFrameReport(frameTimecode);
        }
    }

    public void droppedFragmentReport(long fragmentTimecode) {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.droppedFragmentReport(fragmentTimecode);
        }
    }

    public void streamErrorReport(long fragmentTimecode, long statusCode) {
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamErrorReport(fragmentTimecode, statusCode);
        }
    }

    public void streamDataAvailable(long uploadHandle, long duration, long availableSize) {
        NativeDataInputStream inputStreamToNotify = this.mInputStreamMap.get(Long.valueOf(uploadHandle));
        if (inputStreamToNotify != null) {
            inputStreamToNotify.notifyReaderThread(duration, availableSize);
        } else {
            long j = duration;
            long j2 = availableSize;
            this.mLog.warn("Data available notification for non-existing uploadHandle %d", Long.valueOf(uploadHandle));
        }
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamDataAvailable(uploadHandle, duration, availableSize);
        }
    }

    public void streamReady() {
        this.mLog.debug("Stream %s is ready", this.mStreamInfo.getName());
        this.mReadyLatch.countDown();
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamReady();
        }
    }

    public void streamClosed(long uploadHandle) {
        this.mLog.debug("Stream %s is closed", this.mStreamInfo.getName());
        this.mStoppedLatch.countDown();
        StreamCallbacks streamCallbacks = this.mStreamCallbacks;
        if (streamCallbacks != null) {
            streamCallbacks.streamClosed(uploadHandle);
        }
    }

    public void resetConnection() {
        this.mLog.debug("Current connection of stream %s is being reset", this.mStreamInfo.getName());
        streamTerminated(-1, 200);
    }

    public void awaitReady() {
        try {
            if (!this.mReadyLatch.await(15000, TimeUnit.MILLISECONDS)) {
                throw new ProducerException("KinesisVideo producer stream creation time out", 15);
            }
        } catch (InterruptedException e) {
            throw new ProducerException(e);
        }
    }

    public void awaitStopped() {
        try {
            if (!this.mStoppedLatch.await(15000, TimeUnit.MILLISECONDS)) {
                throw new ProducerException("KinesisVideo producer stream stopping time out", 15);
            }
        } catch (InterruptedException e) {
            throw new ProducerException(e);
        }
    }

    /* access modifiers changed from: private */
    public void notifyEndOfStream(long uploadHandle) {
        NativeDataInputStream inputStream = this.mInputStreamMap.get(Long.valueOf(uploadHandle));
        if (inputStream != null) {
            inputStream.endOfReaderThread();
            return;
        }
        this.mLog.error("NativeDataInputStream corresponding to upload handle %d is not found.", Long.valueOf(uploadHandle));
    }
}

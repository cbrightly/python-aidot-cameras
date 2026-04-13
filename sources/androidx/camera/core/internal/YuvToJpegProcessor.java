package androidx.camera.core.internal;

import android.graphics.Rect;
import android.media.ImageWriter;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.utils.ExifData;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.core.util.Preconditions;
import java.io.EOFException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@RequiresApi(26)
public class YuvToJpegProcessor implements CaptureProcessor {
    private static final String TAG = "YuvToJpegProcessor";
    private static final Rect UNINITIALIZED_RECT = new Rect(0, 0, 0, 0);
    @GuardedBy("mLock")
    private boolean mClosed = false;
    @GuardedBy("mLock")
    private Rect mImageRect = UNINITIALIZED_RECT;
    @GuardedBy("mLock")
    private ImageWriter mImageWriter;
    private final Object mLock = new Object();
    private final int mMaxImages;
    @GuardedBy("mLock")
    private int mProcessingImages = 0;
    @IntRange(from = 0, to = 100)
    private final int mQuality;

    public YuvToJpegProcessor(@IntRange(from = 0, to = 100) int quality, int maxImages) {
        this.mQuality = quality;
        this.mMaxImages = maxImages;
    }

    public void onOutputSurface(@NonNull Surface surface, int imageFormat) {
        Preconditions.checkState(imageFormat == 256, "YuvToJpegProcessor only supports JPEG output format.");
        synchronized (this.mLock) {
            if (this.mClosed) {
                Logger.w(TAG, "Cannot set output surface. Processor is closed.");
            } else if (this.mImageWriter == null) {
                this.mImageWriter = ImageWriterCompat.newInstance(surface, this.mMaxImages, imageFormat);
            } else {
                throw new IllegalStateException("Output surface already set.");
            }
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x01c0  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x01c5  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x01ca  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0144 A[ExcHandler: InterruptedException | ExecutionException (r0v13 'e' java.lang.Exception A[CUSTOM_DECLARE]), PHI: r7 r11 
      PHI: (r7v4 'imageProxy' androidx.camera.core.ImageProxy) = (r7v1 'imageProxy' androidx.camera.core.ImageProxy), (r7v17 'imageProxy' androidx.camera.core.ImageProxy), (r7v18 'imageProxy' androidx.camera.core.ImageProxy), (r7v21 'imageProxy' androidx.camera.core.ImageProxy) binds: [B:16:0x005b, B:42:0x009f, B:48:0x00e6, B:49:?] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r11v3 'jpegImage' android.media.Image) = (r11v0 'jpegImage' android.media.Image), (r11v0 'jpegImage' android.media.Image), (r11v8 'jpegImage' android.media.Image), (r11v8 'jpegImage' android.media.Image) binds: [B:16:0x005b, B:42:0x009f, B:48:0x00e6, B:49:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:16:0x005b] */
    public void process(@androidx.annotation.NonNull androidx.camera.core.impl.ImageProxyBundle r20) {
        /*
            r19 = this;
            r1 = r19
            java.util.List r2 = r20.getCaptureIds()
            int r0 = r2.size()
            r3 = 0
            r4 = 1
            if (r0 != r4) goto L_0x0010
            r0 = r4
            goto L_0x0011
        L_0x0010:
            r0 = r3
        L_0x0011:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Processing image bundle have single capture id, but found "
            r5.append(r6)
            int r6 = r2.size()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            androidx.core.util.Preconditions.checkArgument(r0, r5)
            java.lang.Object r0 = r2.get(r3)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r5 = r20
            com.google.common.util.concurrent.ListenableFuture r6 = r5.getImageProxy(r0)
            boolean r0 = r6.isDone()
            androidx.core.util.Preconditions.checkArgument(r0)
            java.lang.Object r7 = r1.mLock
            monitor-enter(r7)
            android.media.ImageWriter r0 = r1.mImageWriter     // Catch:{ all -> 0x01d9 }
            r8 = r0
            boolean r0 = r1.mClosed     // Catch:{ all -> 0x01d9 }
            if (r0 != 0) goto L_0x004c
            r0 = r4
            goto L_0x004d
        L_0x004c:
            r0 = r3
        L_0x004d:
            r9 = r0
            android.graphics.Rect r0 = r1.mImageRect     // Catch:{ all -> 0x01d9 }
            r10 = r0
            if (r9 == 0) goto L_0x0058
            int r0 = r1.mProcessingImages     // Catch:{ all -> 0x01d9 }
            int r0 = r0 + r4
            r1.mProcessingImages = r0     // Catch:{ all -> 0x01d9 }
        L_0x0058:
            monitor-exit(r7)     // Catch:{ all -> 0x01d9 }
            r7 = 0
            r11 = 0
            java.lang.Object r0 = r6.get()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            androidx.camera.core.ImageProxy r0 = (androidx.camera.core.ImageProxy) r0     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r7 = r0
            if (r9 != 0) goto L_0x009f
            java.lang.String r0 = "YuvToJpegProcessor"
            java.lang.String r12 = "Image enqueued for processing on closed processor."
            androidx.camera.core.Logger.w(r0, r12)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r7.close()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r12 = 0
            java.lang.Object r13 = r1.mLock
            monitor-enter(r13)
            if (r9 == 0) goto L_0x0084
            int r0 = r1.mProcessingImages     // Catch:{ all -> 0x0082 }
            int r7 = r0 + -1
            r1.mProcessingImages = r7     // Catch:{ all -> 0x0082 }
            if (r0 != 0) goto L_0x0084
            boolean r0 = r1.mClosed     // Catch:{ all -> 0x0082 }
            if (r0 == 0) goto L_0x0084
            r3 = r4
            goto L_0x0084
        L_0x0082:
            r0 = move-exception
            goto L_0x009d
        L_0x0084:
            r0 = r3
            monitor-exit(r13)     // Catch:{ all -> 0x0082 }
            if (r11 == 0) goto L_0x008b
            r11.close()
        L_0x008b:
            if (r12 == 0) goto L_0x0090
            r12.close()
        L_0x0090:
            if (r0 == 0) goto L_0x009c
            r8.close()
            java.lang.String r3 = "YuvToJpegProcessor"
            java.lang.String r4 = "Closed after completion of last image processed."
            androidx.camera.core.Logger.d(r3, r4)
        L_0x009c:
            return
        L_0x009d:
            monitor-exit(r13)     // Catch:{ all -> 0x0082 }
            throw r0
        L_0x009f:
            android.media.Image r0 = r8.dequeueInputImage()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r11 = r0
            java.lang.Object r0 = r6.get()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            androidx.camera.core.ImageProxy r0 = (androidx.camera.core.ImageProxy) r0     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r7 = r0
            int r0 = r7.getFormat()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r12 = 35
            if (r0 != r12) goto L_0x00b5
            r0 = r4
            goto L_0x00b6
        L_0x00b5:
            r0 = r3
        L_0x00b6:
            java.lang.String r12 = "Input image is not expected YUV_420_888 image format"
            androidx.core.util.Preconditions.checkState(r0, r12)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            byte[] r14 = androidx.camera.core.internal.utils.ImageUtil.yuv_420_888toNv21(r7)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            android.graphics.YuvImage r0 = new android.graphics.YuvImage     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r15 = 17
            int r16 = r7.getWidth()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            int r17 = r7.getHeight()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r18 = 0
            r13 = r0
            r13.<init>(r14, r15, r16, r17, r18)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            android.media.Image$Plane[] r12 = r11.getPlanes()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r12 = r12[r3]     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            java.nio.ByteBuffer r12 = r12.getBuffer()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            int r13 = r12.position()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            androidx.camera.core.impl.utils.ExifOutputStream r15 = new androidx.camera.core.impl.utils.ExifOutputStream     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            androidx.camera.core.internal.YuvToJpegProcessor$ByteBufferOutputStream r4 = new androidx.camera.core.internal.YuvToJpegProcessor$ByteBufferOutputStream     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            r4.<init>(r12)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013f }
            androidx.camera.core.impl.utils.ExifData r3 = getExifData(r7)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r15.<init>(r4, r3)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r3 = r15
            int r4 = r1.mQuality     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r0.compressToJpeg(r10, r4, r3)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r7.close()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r7 = 0
            int r4 = r12.position()     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r12.limit(r4)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r12.position(r13)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r8.queueInputImage(r11)     // Catch:{ InterruptedException -> 0x0146, ExecutionException -> 0x0144, all -> 0x013a }
            r3 = 0
            java.lang.Object r4 = r1.mLock
            monitor-enter(r4)
            if (r9 == 0) goto L_0x011b
            int r0 = r1.mProcessingImages     // Catch:{ all -> 0x0119 }
            int r11 = r0 + -1
            r1.mProcessingImages = r11     // Catch:{ all -> 0x0119 }
            if (r0 != 0) goto L_0x011b
            boolean r0 = r1.mClosed     // Catch:{ all -> 0x0119 }
            if (r0 == 0) goto L_0x011b
            r16 = 1
            goto L_0x011d
        L_0x0119:
            r0 = move-exception
            goto L_0x0138
        L_0x011b:
            r16 = 0
        L_0x011d:
            r0 = r16
            monitor-exit(r4)     // Catch:{ all -> 0x0119 }
            if (r3 == 0) goto L_0x0125
            r3.close()
        L_0x0125:
            if (r7 == 0) goto L_0x012a
            r7.close()
        L_0x012a:
            if (r0 == 0) goto L_0x0136
            r8.close()
            java.lang.String r4 = "YuvToJpegProcessor"
            java.lang.String r11 = "Closed after completion of last image processed."
            androidx.camera.core.Logger.d(r4, r11)
        L_0x0136:
            goto L_0x01d6
        L_0x0138:
            monitor-exit(r4)     // Catch:{ all -> 0x0119 }
            throw r0
        L_0x013a:
            r0 = move-exception
            r4 = r7
            r12 = r11
            r7 = 0
            goto L_0x0172
        L_0x013f:
            r0 = move-exception
            r4 = r7
            r12 = r11
            r7 = r3
            goto L_0x0172
        L_0x0144:
            r0 = move-exception
            goto L_0x0147
        L_0x0146:
            r0 = move-exception
        L_0x0147:
            r3 = r7
            if (r9 == 0) goto L_0x01a2
            java.lang.String r4 = "YuvToJpegProcessor"
            java.lang.String r7 = "Failed to process YUV -> JPEG"
            androidx.camera.core.Logger.e(r4, r7, r0)     // Catch:{ all -> 0x016e }
            android.media.Image r4 = r8.dequeueInputImage()     // Catch:{ all -> 0x016e }
            r11 = r4
            android.media.Image$Plane[] r4 = r11.getPlanes()     // Catch:{ all -> 0x016e }
            r7 = 0
            r4 = r4[r7]     // Catch:{ all -> 0x016c }
            java.nio.ByteBuffer r4 = r4.getBuffer()     // Catch:{ all -> 0x016e }
            r4.rewind()     // Catch:{ all -> 0x016e }
            r7 = 0
            r4.limit(r7)     // Catch:{ all -> 0x016c }
            r8.queueInputImage(r11)     // Catch:{ all -> 0x016c }
            goto L_0x01a3
        L_0x016c:
            r0 = move-exception
            goto L_0x0170
        L_0x016e:
            r0 = move-exception
            r7 = 0
        L_0x0170:
            r4 = r3
            r12 = r11
        L_0x0172:
            java.lang.Object r13 = r1.mLock
            monitor-enter(r13)
            if (r9 == 0) goto L_0x0187
            int r3 = r1.mProcessingImages     // Catch:{ all -> 0x0185 }
            int r11 = r3 + -1
            r1.mProcessingImages = r11     // Catch:{ all -> 0x0185 }
            if (r3 != 0) goto L_0x0187
            boolean r3 = r1.mClosed     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x0187
            r3 = 1
            goto L_0x0188
        L_0x0185:
            r0 = move-exception
            goto L_0x01a0
        L_0x0187:
            r3 = r7
        L_0x0188:
            monitor-exit(r13)     // Catch:{ all -> 0x0185 }
            if (r12 == 0) goto L_0x018e
            r12.close()
        L_0x018e:
            if (r4 == 0) goto L_0x0193
            r4.close()
        L_0x0193:
            if (r3 == 0) goto L_0x019f
            r8.close()
            java.lang.String r7 = "YuvToJpegProcessor"
            java.lang.String r11 = "Closed after completion of last image processed."
            androidx.camera.core.Logger.d(r7, r11)
        L_0x019f:
            throw r0
        L_0x01a0:
            monitor-exit(r13)     // Catch:{ all -> 0x0185 }
            throw r0
        L_0x01a2:
            r7 = 0
        L_0x01a3:
            java.lang.Object r4 = r1.mLock
            monitor-enter(r4)
            if (r9 == 0) goto L_0x01b9
            int r0 = r1.mProcessingImages     // Catch:{ all -> 0x01b7 }
            int r12 = r0 + -1
            r1.mProcessingImages = r12     // Catch:{ all -> 0x01b7 }
            if (r0 != 0) goto L_0x01b9
            boolean r0 = r1.mClosed     // Catch:{ all -> 0x01b7 }
            if (r0 == 0) goto L_0x01b9
            r16 = 1
            goto L_0x01bb
        L_0x01b7:
            r0 = move-exception
            goto L_0x01d7
        L_0x01b9:
            r16 = r7
        L_0x01bb:
            r0 = r16
            monitor-exit(r4)     // Catch:{ all -> 0x01b7 }
            if (r11 == 0) goto L_0x01c3
            r11.close()
        L_0x01c3:
            if (r3 == 0) goto L_0x01c8
            r3.close()
        L_0x01c8:
            if (r0 == 0) goto L_0x01d4
            r8.close()
            java.lang.String r4 = "YuvToJpegProcessor"
            java.lang.String r7 = "Closed after completion of last image processed."
            androidx.camera.core.Logger.d(r4, r7)
        L_0x01d4:
            r7 = r3
            r3 = r11
        L_0x01d6:
            return
        L_0x01d7:
            monitor-exit(r4)     // Catch:{ all -> 0x01b7 }
            throw r0
        L_0x01d9:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x01d9 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.internal.YuvToJpegProcessor.process(androidx.camera.core.impl.ImageProxyBundle):void");
    }

    public void close() {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                this.mClosed = true;
                if (this.mProcessingImages != 0 || this.mImageWriter == null) {
                    Logger.d(TAG, "close() called while processing. Will close after completion.");
                } else {
                    Logger.d(TAG, "No processing in progress. Closing immediately.");
                    this.mImageWriter.close();
                }
            }
        }
    }

    public void onResolutionUpdate(@NonNull Size size) {
        synchronized (this.mLock) {
            this.mImageRect = new Rect(0, 0, size.getWidth(), size.getHeight());
        }
    }

    @NonNull
    private static ExifData getExifData(@NonNull ImageProxy imageProxy) {
        ExifData.Builder builder = ExifData.builderForDevice();
        imageProxy.getImageInfo().populateExifData(builder);
        return builder.setImageWidth(imageProxy.getWidth()).setImageHeight(imageProxy.getHeight()).build();
    }

    public static final class ByteBufferOutputStream extends OutputStream {
        private final ByteBuffer mByteBuffer;

        ByteBufferOutputStream(@NonNull ByteBuffer buf) {
            this.mByteBuffer = buf;
        }

        public void write(int b) {
            if (this.mByteBuffer.hasRemaining()) {
                this.mByteBuffer.put((byte) b);
                return;
            }
            throw new EOFException("Output ByteBuffer has no bytes remaining.");
        }

        public void write(byte[] b, int off, int len) {
            if (b == null) {
                throw new NullPointerException();
            } else if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
                throw new IndexOutOfBoundsException();
            } else if (len != 0) {
                if (this.mByteBuffer.remaining() >= len) {
                    this.mByteBuffer.put(b, off, len);
                    return;
                }
                throw new EOFException("Output ByteBuffer has insufficient bytes remaining.");
            }
        }
    }
}

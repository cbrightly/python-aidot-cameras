package androidx.camera.core;

import android.media.ImageReader;
import android.util.LongSparseArray;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MetadataImageReader implements ImageReaderProxy, ForwardingImageProxy.OnImageCloseListener {
    private static final String TAG = "MetadataImageReader";
    @GuardedBy("mLock")
    private final List<ImageProxy> mAcquiredImageProxies;
    private CameraCaptureCallback mCameraCaptureCallback;
    @GuardedBy("mLock")
    private boolean mClosed;
    @GuardedBy("mLock")
    @Nullable
    private Executor mExecutor;
    @GuardedBy("mLock")
    private int mImageProxiesIndex;
    @GuardedBy("mLock")
    private final ImageReaderProxy mImageReaderProxy;
    @GuardedBy("mLock")
    @Nullable
    ImageReaderProxy.OnImageAvailableListener mListener;
    private final Object mLock;
    @GuardedBy("mLock")
    private final List<ImageProxy> mMatchedImageProxies;
    @GuardedBy("mLock")
    private final LongSparseArray<ImageInfo> mPendingImageInfos;
    @GuardedBy("mLock")
    private final LongSparseArray<ImageProxy> mPendingImages;
    private ImageReaderProxy.OnImageAvailableListener mTransformedListener;

    MetadataImageReader(int width, int height, int format, int maxImages) {
        this(createImageReaderProxy(width, height, format, maxImages));
    }

    private static ImageReaderProxy createImageReaderProxy(int width, int height, int format, int maxImages) {
        return new AndroidImageReaderProxy(ImageReader.newInstance(width, height, format, maxImages));
    }

    MetadataImageReader(@NonNull ImageReaderProxy imageReaderProxy) {
        this.mLock = new Object();
        this.mCameraCaptureCallback = new CameraCaptureCallback() {
            public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
                super.onCaptureCompleted(cameraCaptureResult);
                MetadataImageReader.this.resultIncoming(cameraCaptureResult);
            }
        };
        this.mTransformedListener = new w0(this);
        this.mClosed = false;
        this.mPendingImageInfos = new LongSparseArray<>();
        this.mPendingImages = new LongSparseArray<>();
        this.mAcquiredImageProxies = new ArrayList();
        this.mImageReaderProxy = imageReaderProxy;
        this.mImageProxiesIndex = 0;
        this.mMatchedImageProxies = new ArrayList(getMaxImages());
    }

    @Nullable
    public ImageProxy acquireLatestImage() {
        synchronized (this.mLock) {
            if (this.mMatchedImageProxies.isEmpty()) {
                return null;
            }
            if (this.mImageProxiesIndex < this.mMatchedImageProxies.size()) {
                List<ImageProxy> toClose = new ArrayList<>();
                for (int i = 0; i < this.mMatchedImageProxies.size() - 1; i++) {
                    if (!this.mAcquiredImageProxies.contains(this.mMatchedImageProxies.get(i))) {
                        toClose.add(this.mMatchedImageProxies.get(i));
                    }
                }
                for (ImageProxy image : toClose) {
                    image.close();
                }
                int size = this.mMatchedImageProxies.size() - 1;
                this.mImageProxiesIndex = size;
                List<ImageProxy> list = this.mMatchedImageProxies;
                this.mImageProxiesIndex = size + 1;
                ImageProxy acquiredImage = list.get(size);
                this.mAcquiredImageProxies.add(acquiredImage);
                return acquiredImage;
            }
            throw new IllegalStateException("Maximum image number reached.");
        }
    }

    @Nullable
    public ImageProxy acquireNextImage() {
        synchronized (this.mLock) {
            if (this.mMatchedImageProxies.isEmpty()) {
                return null;
            }
            if (this.mImageProxiesIndex < this.mMatchedImageProxies.size()) {
                List<ImageProxy> list = this.mMatchedImageProxies;
                int i = this.mImageProxiesIndex;
                this.mImageProxiesIndex = i + 1;
                ImageProxy acquiredImage = list.get(i);
                this.mAcquiredImageProxies.add(acquiredImage);
                return acquiredImage;
            }
            throw new IllegalStateException("Maximum image number reached.");
        }
    }

    public void close() {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                for (ImageProxy image : new ArrayList<>(this.mMatchedImageProxies)) {
                    image.close();
                }
                this.mMatchedImageProxies.clear();
                this.mImageReaderProxy.close();
                this.mClosed = true;
            }
        }
    }

    public int getHeight() {
        int height;
        synchronized (this.mLock) {
            height = this.mImageReaderProxy.getHeight();
        }
        return height;
    }

    public int getWidth() {
        int width;
        synchronized (this.mLock) {
            width = this.mImageReaderProxy.getWidth();
        }
        return width;
    }

    public int getImageFormat() {
        int imageFormat;
        synchronized (this.mLock) {
            imageFormat = this.mImageReaderProxy.getImageFormat();
        }
        return imageFormat;
    }

    public int getMaxImages() {
        int maxImages;
        synchronized (this.mLock) {
            maxImages = this.mImageReaderProxy.getMaxImages();
        }
        return maxImages;
    }

    @Nullable
    public Surface getSurface() {
        Surface surface;
        synchronized (this.mLock) {
            surface = this.mImageReaderProxy.getSurface();
        }
        return surface;
    }

    public void setOnImageAvailableListener(@NonNull ImageReaderProxy.OnImageAvailableListener listener, @NonNull Executor executor) {
        synchronized (this.mLock) {
            this.mListener = (ImageReaderProxy.OnImageAvailableListener) Preconditions.checkNotNull(listener);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
            this.mImageReaderProxy.setOnImageAvailableListener(this.mTransformedListener, executor);
        }
    }

    public void clearOnImageAvailableListener() {
        synchronized (this.mLock) {
            this.mListener = null;
            this.mExecutor = null;
        }
    }

    public void onImageClose(ImageProxy image) {
        synchronized (this.mLock) {
            dequeImageProxy(image);
        }
    }

    private void enqueueImageProxy(SettableImageProxy image) {
        Executor executor;
        ImageReaderProxy.OnImageAvailableListener listener;
        synchronized (this.mLock) {
            if (this.mMatchedImageProxies.size() < getMaxImages()) {
                image.addOnImageCloseListener(this);
                this.mMatchedImageProxies.add(image);
                listener = this.mListener;
                executor = this.mExecutor;
            } else {
                Logger.d("TAG", "Maximum image number reached.");
                image.close();
                listener = null;
                executor = null;
            }
        }
        if (listener == null) {
            return;
        }
        if (executor != null) {
            executor.execute(new x0(this, listener));
        } else {
            listener.onImageAvailable(this);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$enqueueImageProxy$1 */
    public /* synthetic */ void a(ImageReaderProxy.OnImageAvailableListener listener) {
        listener.onImageAvailable(this);
    }

    private void dequeImageProxy(ImageProxy image) {
        synchronized (this.mLock) {
            int index = this.mMatchedImageProxies.indexOf(image);
            if (index >= 0) {
                this.mMatchedImageProxies.remove(index);
                int i = this.mImageProxiesIndex;
                if (index <= i) {
                    this.mImageProxiesIndex = i - 1;
                }
            }
            this.mAcquiredImageProxies.remove(image);
        }
    }

    /* access modifiers changed from: package-private */
    public CameraCaptureCallback getCameraCaptureCallback() {
        return this.mCameraCaptureCallback;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004a, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0012  */
    /* renamed from: imageIncoming */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void lambda$new$0(androidx.camera.core.impl.ImageReaderProxy r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            boolean r1 = r7.mClosed     // Catch:{ all -> 0x0061 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            return
        L_0x0009:
            r1 = 0
        L_0x000a:
            r2 = 0
            androidx.camera.core.ImageProxy r3 = r8.acquireNextImage()     // Catch:{ IllegalStateException -> 0x0027 }
            r2 = r3
            if (r2 == 0) goto L_0x0041
            int r1 = r1 + 1
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r3 = r7.mPendingImages     // Catch:{ all -> 0x0061 }
            androidx.camera.core.ImageInfo r4 = r2.getImageInfo()     // Catch:{ all -> 0x0061 }
            long r4 = r4.getTimestamp()     // Catch:{ all -> 0x0061 }
            r3.put(r4, r2)     // Catch:{ all -> 0x0061 }
        L_0x0021:
            r7.matchImages()     // Catch:{ all -> 0x0061 }
            goto L_0x0041
        L_0x0025:
            r3 = move-exception
            goto L_0x004b
        L_0x0027:
            r3 = move-exception
            java.lang.String r4 = "MetadataImageReader"
            java.lang.String r5 = "Failed to acquire next image."
            androidx.camera.core.Logger.d(r4, r5, r3)     // Catch:{ all -> 0x0025 }
            if (r2 == 0) goto L_0x0041
            int r1 = r1 + 1
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r3 = r7.mPendingImages     // Catch:{ all -> 0x0061 }
            androidx.camera.core.ImageInfo r4 = r2.getImageInfo()     // Catch:{ all -> 0x0061 }
            long r4 = r4.getTimestamp()     // Catch:{ all -> 0x0061 }
            r3.put(r4, r2)     // Catch:{ all -> 0x0061 }
            goto L_0x0021
        L_0x0041:
            if (r2 == 0) goto L_0x0049
            int r3 = r8.getMaxImages()     // Catch:{ all -> 0x0061 }
            if (r1 < r3) goto L_0x000a
        L_0x0049:
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            return
        L_0x004b:
            if (r2 == 0) goto L_0x005f
            int r1 = r1 + 1
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r4 = r7.mPendingImages     // Catch:{ all -> 0x0061 }
            androidx.camera.core.ImageInfo r5 = r2.getImageInfo()     // Catch:{ all -> 0x0061 }
            long r5 = r5.getTimestamp()     // Catch:{ all -> 0x0061 }
            r4.put(r5, r2)     // Catch:{ all -> 0x0061 }
            r7.matchImages()     // Catch:{ all -> 0x0061 }
        L_0x005f:
            throw r3     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.MetadataImageReader.lambda$new$0(androidx.camera.core.impl.ImageReaderProxy):void");
    }

    /* access modifiers changed from: package-private */
    public void resultIncoming(CameraCaptureResult cameraCaptureResult) {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                this.mPendingImageInfos.put(cameraCaptureResult.getTimestamp(), new CameraCaptureResultImageInfo(cameraCaptureResult));
                matchImages();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void removeStaleData() {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r1 = r10.mPendingImages     // Catch:{ all -> 0x008f }
            int r1 = r1.size()     // Catch:{ all -> 0x008f }
            if (r1 == 0) goto L_0x008d
            android.util.LongSparseArray<androidx.camera.core.ImageInfo> r1 = r10.mPendingImageInfos     // Catch:{ all -> 0x008f }
            int r1 = r1.size()     // Catch:{ all -> 0x008f }
            if (r1 != 0) goto L_0x0015
            goto L_0x008d
        L_0x0015:
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r1 = r10.mPendingImages     // Catch:{ all -> 0x008f }
            r2 = 0
            long r3 = r1.keyAt(r2)     // Catch:{ all -> 0x008f }
            java.lang.Long r1 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x008f }
            android.util.LongSparseArray<androidx.camera.core.ImageInfo> r3 = r10.mPendingImageInfos     // Catch:{ all -> 0x008f }
            long r3 = r3.keyAt(r2)     // Catch:{ all -> 0x008f }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x008f }
            boolean r4 = r3.equals(r1)     // Catch:{ all -> 0x008f }
            r5 = 1
            if (r4 != 0) goto L_0x0032
            r2 = r5
        L_0x0032:
            androidx.core.util.Preconditions.checkArgument(r2)     // Catch:{ all -> 0x008f }
            long r6 = r3.longValue()     // Catch:{ all -> 0x008f }
            long r8 = r1.longValue()     // Catch:{ all -> 0x008f }
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x006c
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r2 = r10.mPendingImages     // Catch:{ all -> 0x008f }
            int r2 = r2.size()     // Catch:{ all -> 0x008f }
            int r2 = r2 - r5
        L_0x0048:
            if (r2 < 0) goto L_0x006b
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r4 = r10.mPendingImages     // Catch:{ all -> 0x008f }
            long r4 = r4.keyAt(r2)     // Catch:{ all -> 0x008f }
            long r6 = r3.longValue()     // Catch:{ all -> 0x008f }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0068
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r4 = r10.mPendingImages     // Catch:{ all -> 0x008f }
            java.lang.Object r4 = r4.valueAt(r2)     // Catch:{ all -> 0x008f }
            androidx.camera.core.ImageProxy r4 = (androidx.camera.core.ImageProxy) r4     // Catch:{ all -> 0x008f }
            r4.close()     // Catch:{ all -> 0x008f }
            android.util.LongSparseArray<androidx.camera.core.ImageProxy> r5 = r10.mPendingImages     // Catch:{ all -> 0x008f }
            r5.removeAt(r2)     // Catch:{ all -> 0x008f }
        L_0x0068:
            int r2 = r2 + -1
            goto L_0x0048
        L_0x006b:
            goto L_0x008b
        L_0x006c:
            android.util.LongSparseArray<androidx.camera.core.ImageInfo> r2 = r10.mPendingImageInfos     // Catch:{ all -> 0x008f }
            int r2 = r2.size()     // Catch:{ all -> 0x008f }
            int r2 = r2 - r5
        L_0x0073:
            if (r2 < 0) goto L_0x008b
            android.util.LongSparseArray<androidx.camera.core.ImageInfo> r4 = r10.mPendingImageInfos     // Catch:{ all -> 0x008f }
            long r4 = r4.keyAt(r2)     // Catch:{ all -> 0x008f }
            long r6 = r1.longValue()     // Catch:{ all -> 0x008f }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0088
            android.util.LongSparseArray<androidx.camera.core.ImageInfo> r4 = r10.mPendingImageInfos     // Catch:{ all -> 0x008f }
            r4.removeAt(r2)     // Catch:{ all -> 0x008f }
        L_0x0088:
            int r2 = r2 + -1
            goto L_0x0073
        L_0x008b:
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            return
        L_0x008d:
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            return
        L_0x008f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x008f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.MetadataImageReader.removeStaleData():void");
    }

    private void matchImages() {
        synchronized (this.mLock) {
            for (int i = this.mPendingImageInfos.size() - 1; i >= 0; i--) {
                ImageInfo imageInfo = this.mPendingImageInfos.valueAt(i);
                long timestamp = imageInfo.getTimestamp();
                ImageProxy image = this.mPendingImages.get(timestamp);
                if (image != null) {
                    this.mPendingImages.remove(timestamp);
                    this.mPendingImageInfos.removeAt(i);
                    enqueueImageProxy(new SettableImageProxy(image, imageInfo));
                }
            }
            removeStaleData();
        }
    }
}

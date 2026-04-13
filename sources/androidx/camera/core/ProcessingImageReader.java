package androidx.camera.core;

import android.media.ImageReader;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class ProcessingImageReader implements ImageReaderProxy {
    private static final String TAG = "ProcessingImageReader";
    private final List<Integer> mCaptureIdList;
    @NonNull
    final CaptureProcessor mCaptureProcessor;
    private FutureCallback<List<ImageProxy>> mCaptureStageReadyCallback;
    @GuardedBy("mLock")
    CallbackToFutureAdapter.Completer<Void> mCloseCompleter;
    @GuardedBy("mLock")
    private ListenableFuture<Void> mCloseFuture;
    @GuardedBy("mLock")
    boolean mClosed;
    @GuardedBy("mLock")
    @Nullable
    Executor mExecutor;
    private ImageReaderProxy.OnImageAvailableListener mImageProcessedListener;
    @GuardedBy("mLock")
    final MetadataImageReader mInputImageReader;
    @GuardedBy("mLock")
    @Nullable
    ImageReaderProxy.OnImageAvailableListener mListener;
    final Object mLock;
    @GuardedBy("mLock")
    final ImageReaderProxy mOutputImageReader;
    @NonNull
    final Executor mPostProcessExecutor;
    @GuardedBy("mLock")
    boolean mProcessing;
    @GuardedBy("mLock")
    @NonNull
    SettableImageProxyBundle mSettableImageProxyBundle;
    private String mTagBundleKey;
    private ImageReaderProxy.OnImageAvailableListener mTransformedListener;

    ProcessingImageReader(int width, int height, int format, int maxImages, @NonNull Executor postProcessExecutor, @NonNull CaptureBundle captureBundle, @NonNull CaptureProcessor captureProcessor) {
        this(width, height, format, maxImages, postProcessExecutor, captureBundle, captureProcessor, format);
    }

    ProcessingImageReader(int width, int height, int inputFormat, int maxImages, @NonNull Executor postProcessExecutor, @NonNull CaptureBundle captureBundle, @NonNull CaptureProcessor captureProcessor, int outputFormat) {
        this(new MetadataImageReader(width, height, inputFormat, maxImages), postProcessExecutor, captureBundle, captureProcessor, outputFormat);
    }

    ProcessingImageReader(@NonNull MetadataImageReader imageReader, @NonNull Executor postProcExecutor, @NonNull CaptureBundle capBundle, @NonNull CaptureProcessor capProcessor) {
        this(imageReader, postProcExecutor, capBundle, capProcessor, imageReader.getImageFormat());
    }

    ProcessingImageReader(@NonNull MetadataImageReader imageReader, @NonNull Executor postProcessExecutor, @NonNull CaptureBundle captureBundle, @NonNull CaptureProcessor captureProcessor, int outputFormat) {
        this.mLock = new Object();
        this.mTransformedListener = new ImageReaderProxy.OnImageAvailableListener() {
            public void onImageAvailable(@NonNull ImageReaderProxy reader) {
                ProcessingImageReader.this.imageIncoming(reader);
            }
        };
        this.mImageProcessedListener = new ImageReaderProxy.OnImageAvailableListener() {
            public void onImageAvailable(@NonNull ImageReaderProxy reader) {
                ImageReaderProxy.OnImageAvailableListener listener;
                Executor executor;
                synchronized (ProcessingImageReader.this.mLock) {
                    ProcessingImageReader processingImageReader = ProcessingImageReader.this;
                    listener = processingImageReader.mListener;
                    executor = processingImageReader.mExecutor;
                    processingImageReader.mSettableImageProxyBundle.reset();
                    ProcessingImageReader.this.setupSettableImageProxyBundleCallbacks();
                }
                if (listener == null) {
                    return;
                }
                if (executor != null) {
                    executor.execute(new b1(this, listener));
                } else {
                    listener.onImageAvailable(ProcessingImageReader.this);
                }
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$onImageAvailable$0 */
            public /* synthetic */ void a(ImageReaderProxy.OnImageAvailableListener listener) {
                listener.onImageAvailable(ProcessingImageReader.this);
            }
        };
        this.mCaptureStageReadyCallback = new FutureCallback<List<ImageProxy>>() {
            /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
                monitor-enter(r1);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
                r0 = r4.this$0;
                r0.mProcessing = false;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
                if (r0.mClosed == false) goto L_0x0043;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
                r0.mInputImageReader.close();
                r4.this$0.mSettableImageProxyBundle.close();
                r4.this$0.mOutputImageReader.close();
                r0 = r4.this$0.mCloseCompleter;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x003d, code lost:
                if (r0 == null) goto L_0x0043;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
                r0.set(null);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
                monitor-exit(r1);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
                r1.mCaptureProcessor.process(r2);
                r1 = r4.this$0.mLock;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onSuccess(@androidx.annotation.Nullable java.util.List<androidx.camera.core.ImageProxy> r5) {
                /*
                    r4 = this;
                    androidx.camera.core.ProcessingImageReader r0 = androidx.camera.core.ProcessingImageReader.this
                    java.lang.Object r0 = r0.mLock
                    monitor-enter(r0)
                    androidx.camera.core.ProcessingImageReader r1 = androidx.camera.core.ProcessingImageReader.this     // Catch:{ all -> 0x0048 }
                    boolean r2 = r1.mClosed     // Catch:{ all -> 0x0048 }
                    if (r2 == 0) goto L_0x000d
                    monitor-exit(r0)     // Catch:{ all -> 0x0048 }
                    return
                L_0x000d:
                    r2 = 1
                    r1.mProcessing = r2     // Catch:{ all -> 0x0048 }
                    androidx.camera.core.SettableImageProxyBundle r2 = r1.mSettableImageProxyBundle     // Catch:{ all -> 0x0048 }
                    monitor-exit(r0)     // Catch:{ all -> 0x0048 }
                    androidx.camera.core.impl.CaptureProcessor r0 = r1.mCaptureProcessor
                    r0.process(r2)
                    androidx.camera.core.ProcessingImageReader r0 = androidx.camera.core.ProcessingImageReader.this
                    java.lang.Object r1 = r0.mLock
                    monitor-enter(r1)
                    androidx.camera.core.ProcessingImageReader r0 = androidx.camera.core.ProcessingImageReader.this     // Catch:{ all -> 0x0045 }
                    r3 = 0
                    r0.mProcessing = r3     // Catch:{ all -> 0x0045 }
                    boolean r3 = r0.mClosed     // Catch:{ all -> 0x0045 }
                    if (r3 == 0) goto L_0x0043
                    androidx.camera.core.MetadataImageReader r0 = r0.mInputImageReader     // Catch:{ all -> 0x0045 }
                    r0.close()     // Catch:{ all -> 0x0045 }
                    androidx.camera.core.ProcessingImageReader r0 = androidx.camera.core.ProcessingImageReader.this     // Catch:{ all -> 0x0045 }
                    androidx.camera.core.SettableImageProxyBundle r0 = r0.mSettableImageProxyBundle     // Catch:{ all -> 0x0045 }
                    r0.close()     // Catch:{ all -> 0x0045 }
                    androidx.camera.core.ProcessingImageReader r0 = androidx.camera.core.ProcessingImageReader.this     // Catch:{ all -> 0x0045 }
                    androidx.camera.core.impl.ImageReaderProxy r0 = r0.mOutputImageReader     // Catch:{ all -> 0x0045 }
                    r0.close()     // Catch:{ all -> 0x0045 }
                    androidx.camera.core.ProcessingImageReader r0 = androidx.camera.core.ProcessingImageReader.this     // Catch:{ all -> 0x0045 }
                    androidx.concurrent.futures.CallbackToFutureAdapter$Completer<java.lang.Void> r0 = r0.mCloseCompleter     // Catch:{ all -> 0x0045 }
                    if (r0 == 0) goto L_0x0043
                    r3 = 0
                    r0.set(r3)     // Catch:{ all -> 0x0045 }
                L_0x0043:
                    monitor-exit(r1)     // Catch:{ all -> 0x0045 }
                    return
                L_0x0045:
                    r0 = move-exception
                    monitor-exit(r1)     // Catch:{ all -> 0x0045 }
                    throw r0
                L_0x0048:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x0048 }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ProcessingImageReader.AnonymousClass3.onSuccess(java.util.List):void");
            }

            public void onFailure(Throwable throwable) {
            }
        };
        this.mClosed = false;
        this.mProcessing = false;
        this.mTagBundleKey = new String();
        this.mSettableImageProxyBundle = new SettableImageProxyBundle(Collections.emptyList(), this.mTagBundleKey);
        this.mCaptureIdList = new ArrayList();
        if (imageReader.getMaxImages() >= captureBundle.getCaptureStages().size()) {
            this.mInputImageReader = imageReader;
            int outputWidth = imageReader.getWidth();
            int outputHeight = imageReader.getHeight();
            if (outputFormat == 256) {
                outputWidth = imageReader.getWidth() * imageReader.getHeight();
                outputHeight = 1;
            }
            AndroidImageReaderProxy androidImageReaderProxy = new AndroidImageReaderProxy(ImageReader.newInstance(outputWidth, outputHeight, outputFormat, imageReader.getMaxImages()));
            this.mOutputImageReader = androidImageReaderProxy;
            this.mPostProcessExecutor = postProcessExecutor;
            this.mCaptureProcessor = captureProcessor;
            captureProcessor.onOutputSurface(androidImageReaderProxy.getSurface(), outputFormat);
            captureProcessor.onResolutionUpdate(new Size(imageReader.getWidth(), imageReader.getHeight()));
            setCaptureBundle(captureBundle);
            return;
        }
        throw new IllegalArgumentException("MetadataImageReader is smaller than CaptureBundle.");
    }

    @Nullable
    public ImageProxy acquireLatestImage() {
        ImageProxy acquireLatestImage;
        synchronized (this.mLock) {
            acquireLatestImage = this.mOutputImageReader.acquireLatestImage();
        }
        return acquireLatestImage;
    }

    @Nullable
    public ImageProxy acquireNextImage() {
        ImageProxy acquireNextImage;
        synchronized (this.mLock) {
            acquireNextImage = this.mOutputImageReader.acquireNextImage();
        }
        return acquireNextImage;
    }

    public void close() {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                this.mOutputImageReader.clearOnImageAvailableListener();
                if (!this.mProcessing) {
                    this.mInputImageReader.close();
                    this.mSettableImageProxyBundle.close();
                    this.mOutputImageReader.close();
                    CallbackToFutureAdapter.Completer<Void> completer = this.mCloseCompleter;
                    if (completer != null) {
                        completer.set(null);
                    }
                }
                this.mClosed = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ListenableFuture<Void> getCloseFuture() {
        ListenableFuture<Void> closeFuture;
        synchronized (this.mLock) {
            if (!this.mClosed || this.mProcessing) {
                if (this.mCloseFuture == null) {
                    this.mCloseFuture = CallbackToFutureAdapter.getFuture(new c1(this));
                }
                closeFuture = Futures.nonCancellationPropagating(this.mCloseFuture);
            } else {
                closeFuture = Futures.immediateFuture(null);
            }
        }
        return closeFuture;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getCloseFuture$0 */
    public /* synthetic */ Object a(CallbackToFutureAdapter.Completer completer) {
        synchronized (this.mLock) {
            this.mCloseCompleter = completer;
        }
        return "ProcessingImageReader-close";
    }

    public int getHeight() {
        int height;
        synchronized (this.mLock) {
            height = this.mInputImageReader.getHeight();
        }
        return height;
    }

    public int getWidth() {
        int width;
        synchronized (this.mLock) {
            width = this.mInputImageReader.getWidth();
        }
        return width;
    }

    public int getImageFormat() {
        int imageFormat;
        synchronized (this.mLock) {
            imageFormat = this.mOutputImageReader.getImageFormat();
        }
        return imageFormat;
    }

    public int getMaxImages() {
        int maxImages;
        synchronized (this.mLock) {
            maxImages = this.mInputImageReader.getMaxImages();
        }
        return maxImages;
    }

    @Nullable
    public Surface getSurface() {
        Surface surface;
        synchronized (this.mLock) {
            surface = this.mInputImageReader.getSurface();
        }
        return surface;
    }

    public void setOnImageAvailableListener(@NonNull ImageReaderProxy.OnImageAvailableListener listener, @NonNull Executor executor) {
        synchronized (this.mLock) {
            this.mListener = (ImageReaderProxy.OnImageAvailableListener) Preconditions.checkNotNull(listener);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
            this.mInputImageReader.setOnImageAvailableListener(this.mTransformedListener, executor);
            this.mOutputImageReader.setOnImageAvailableListener(this.mImageProcessedListener, executor);
        }
    }

    public void clearOnImageAvailableListener() {
        synchronized (this.mLock) {
            this.mListener = null;
            this.mExecutor = null;
            this.mInputImageReader.clearOnImageAvailableListener();
            this.mOutputImageReader.clearOnImageAvailableListener();
            if (!this.mProcessing) {
                this.mSettableImageProxyBundle.close();
            }
        }
    }

    public void setCaptureBundle(@NonNull CaptureBundle captureBundle) {
        synchronized (this.mLock) {
            if (captureBundle.getCaptureStages() != null) {
                if (this.mInputImageReader.getMaxImages() >= captureBundle.getCaptureStages().size()) {
                    this.mCaptureIdList.clear();
                    for (CaptureStage captureStage : captureBundle.getCaptureStages()) {
                        if (captureStage != null) {
                            this.mCaptureIdList.add(Integer.valueOf(captureStage.getId()));
                        }
                    }
                } else {
                    throw new IllegalArgumentException("CaptureBundle is larger than InputImageReader.");
                }
            }
            String num = Integer.toString(captureBundle.hashCode());
            this.mTagBundleKey = num;
            this.mSettableImageProxyBundle = new SettableImageProxyBundle(this.mCaptureIdList, num);
            setupSettableImageProxyBundleCallbacks();
        }
    }

    @NonNull
    public String getTagBundleKey() {
        return this.mTagBundleKey;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public CameraCaptureCallback getCameraCaptureCallback() {
        CameraCaptureCallback cameraCaptureCallback;
        synchronized (this.mLock) {
            cameraCaptureCallback = this.mInputImageReader.getCameraCaptureCallback();
        }
        return cameraCaptureCallback;
    }

    /* access modifiers changed from: package-private */
    @GuardedBy("mLock")
    public void setupSettableImageProxyBundleCallbacks() {
        List<ListenableFuture<ImageProxy>> futureList = new ArrayList<>();
        for (Integer id : this.mCaptureIdList) {
            futureList.add(this.mSettableImageProxyBundle.getImageProxy(id.intValue()));
        }
        Futures.addCallback(Futures.allAsList(futureList), this.mCaptureStageReadyCallback, this.mPostProcessExecutor);
    }

    /* access modifiers changed from: package-private */
    public void imageIncoming(ImageReaderProxy imageReader) {
        synchronized (this.mLock) {
            if (!this.mClosed) {
                ImageProxy image = null;
                try {
                    ImageProxy image2 = imageReader.acquireNextImage();
                    if (image2 != null) {
                        Integer tagValue = image2.getImageInfo().getTagBundle().getTag(this.mTagBundleKey);
                        if (!this.mCaptureIdList.contains(tagValue)) {
                            Logger.w(TAG, "ImageProxyBundle does not contain this id: " + tagValue);
                            image2.close();
                        } else {
                            this.mSettableImageProxyBundle.addImageProxy(image2);
                        }
                    }
                } catch (IllegalStateException e) {
                    try {
                        Logger.e(TAG, "Failed to acquire latest image.", e);
                        if (image != null) {
                            Integer tagValue2 = image.getImageInfo().getTagBundle().getTag(this.mTagBundleKey);
                            if (!this.mCaptureIdList.contains(tagValue2)) {
                                Logger.w(TAG, "ImageProxyBundle does not contain this id: " + tagValue2);
                                image.close();
                            } else {
                                this.mSettableImageProxyBundle.addImageProxy(image);
                            }
                        }
                    } catch (Throwable th) {
                        if (image != null) {
                            Integer tagValue3 = image.getImageInfo().getTagBundle().getTag(this.mTagBundleKey);
                            if (!this.mCaptureIdList.contains(tagValue3)) {
                                Logger.w(TAG, "ImageProxyBundle does not contain this id: " + tagValue3);
                                image.close();
                            } else {
                                this.mSettableImageProxyBundle.addImageProxy(image);
                            }
                        }
                        throw th;
                    }
                }
            }
        }
    }
}

package androidx.camera.core;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.impl.ImageReaderProxy;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class ImageAnalysisNonBlockingAnalyzer extends ImageAnalysisAbstractAnalyzer {
    final Executor mBackgroundExecutor;
    @VisibleForTesting
    @GuardedBy("mLock")
    @Nullable
    ImageProxy mCachedImage;
    private final Object mLock = new Object();
    @GuardedBy("mLock")
    @Nullable
    private CacheAnalyzingImageProxy mPostedImage;

    ImageAnalysisNonBlockingAnalyzer(Executor executor) {
        this.mBackgroundExecutor = executor;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ImageProxy acquireImage(@NonNull ImageReaderProxy imageReaderProxy) {
        return imageReaderProxy.acquireLatestImage();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onValidImageAvailable(@androidx.annotation.NonNull androidx.camera.core.ImageProxy r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.mIsAttached     // Catch:{ all -> 0x004e }
            if (r1 != 0) goto L_0x000c
            r6.close()     // Catch:{ all -> 0x004e }
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            return
        L_0x000c:
            androidx.camera.core.ImageAnalysisNonBlockingAnalyzer$CacheAnalyzingImageProxy r1 = r5.mPostedImage     // Catch:{ all -> 0x004e }
            if (r1 == 0) goto L_0x0035
            androidx.camera.core.ImageInfo r1 = r6.getImageInfo()     // Catch:{ all -> 0x004e }
            long r1 = r1.getTimestamp()     // Catch:{ all -> 0x004e }
            androidx.camera.core.ImageAnalysisNonBlockingAnalyzer$CacheAnalyzingImageProxy r3 = r5.mPostedImage     // Catch:{ all -> 0x004e }
            androidx.camera.core.ImageInfo r3 = r3.getImageInfo()     // Catch:{ all -> 0x004e }
            long r3 = r3.getTimestamp()     // Catch:{ all -> 0x004e }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x002a
            r6.close()     // Catch:{ all -> 0x004e }
            goto L_0x0033
        L_0x002a:
            androidx.camera.core.ImageProxy r1 = r5.mCachedImage     // Catch:{ all -> 0x004e }
            if (r1 == 0) goto L_0x0031
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0031:
            r5.mCachedImage = r6     // Catch:{ all -> 0x004e }
        L_0x0033:
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            return
        L_0x0035:
            androidx.camera.core.ImageAnalysisNonBlockingAnalyzer$CacheAnalyzingImageProxy r1 = new androidx.camera.core.ImageAnalysisNonBlockingAnalyzer$CacheAnalyzingImageProxy     // Catch:{ all -> 0x004e }
            r1.<init>(r6, r5)     // Catch:{ all -> 0x004e }
            r5.mPostedImage = r1     // Catch:{ all -> 0x004e }
            com.google.common.util.concurrent.ListenableFuture r2 = r5.analyzeImage(r1)     // Catch:{ all -> 0x004e }
            androidx.camera.core.ImageAnalysisNonBlockingAnalyzer$1 r3 = new androidx.camera.core.ImageAnalysisNonBlockingAnalyzer$1     // Catch:{ all -> 0x004e }
            r3.<init>(r1)     // Catch:{ all -> 0x004e }
            java.util.concurrent.Executor r4 = androidx.camera.core.impl.utils.executor.CameraXExecutors.directExecutor()     // Catch:{ all -> 0x004e }
            androidx.camera.core.impl.utils.futures.Futures.addCallback(r2, r3, r4)     // Catch:{ all -> 0x004e }
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            return
        L_0x004e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageAnalysisNonBlockingAnalyzer.onValidImageAvailable(androidx.camera.core.ImageProxy):void");
    }

    /* access modifiers changed from: package-private */
    public void clearCache() {
        synchronized (this.mLock) {
            ImageProxy imageProxy = this.mCachedImage;
            if (imageProxy != null) {
                imageProxy.close();
                this.mCachedImage = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void analyzeCachedImage() {
        synchronized (this.mLock) {
            this.mPostedImage = null;
            ImageProxy cachedImage = this.mCachedImage;
            if (cachedImage != null) {
                this.mCachedImage = null;
                onValidImageAvailable(cachedImage);
            }
        }
    }

    public static class CacheAnalyzingImageProxy extends ForwardingImageProxy {
        final WeakReference<ImageAnalysisNonBlockingAnalyzer> mNonBlockingAnalyzerWeakReference;

        CacheAnalyzingImageProxy(ImageProxy image, ImageAnalysisNonBlockingAnalyzer nonBlockingAnalyzer) {
            super(image);
            this.mNonBlockingAnalyzerWeakReference = new WeakReference<>(nonBlockingAnalyzer);
            addOnImageCloseListener(new u(this));
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$new$0 */
        public /* synthetic */ void a(ImageProxy imageProxy) {
            ImageAnalysisNonBlockingAnalyzer analyzer = (ImageAnalysisNonBlockingAnalyzer) this.mNonBlockingAnalyzerWeakReference.get();
            if (analyzer != null) {
                Executor executor = analyzer.mBackgroundExecutor;
                Objects.requireNonNull(analyzer);
                executor.execute(new a2(analyzer));
            }
        }
    }
}

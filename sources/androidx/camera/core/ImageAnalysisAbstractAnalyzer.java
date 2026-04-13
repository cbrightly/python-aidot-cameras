package androidx.camera.core;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.OperationCanceledException;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

public abstract class ImageAnalysisAbstractAnalyzer implements ImageReaderProxy.OnImageAvailableListener {
    private static final String TAG = "ImageAnalysisAnalyzer";
    private final Object mAnalyzerLock = new Object();
    protected boolean mIsAttached = true;
    private volatile int mRelativeRotation;
    @GuardedBy("mAnalyzerLock")
    private ImageAnalysis.Analyzer mSubscribedAnalyzer;
    @GuardedBy("mAnalyzerLock")
    private Executor mUserExecutor;

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract ImageProxy acquireImage(@NonNull ImageReaderProxy imageReaderProxy);

    /* access modifiers changed from: package-private */
    public abstract void clearCache();

    /* access modifiers changed from: package-private */
    public abstract void onValidImageAvailable(@NonNull ImageProxy imageProxy);

    ImageAnalysisAbstractAnalyzer() {
    }

    public void onImageAvailable(@NonNull ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxy = acquireImage(imageReaderProxy);
            if (imageProxy != null) {
                onValidImageAvailable(imageProxy);
            }
        } catch (IllegalStateException e) {
            Logger.e(TAG, "Failed to acquire image.", e);
        }
    }

    /* access modifiers changed from: package-private */
    public ListenableFuture<Void> analyzeImage(ImageProxy imageProxy) {
        Executor executor;
        ImageAnalysis.Analyzer analyzer;
        synchronized (this.mAnalyzerLock) {
            executor = this.mUserExecutor;
            analyzer = this.mSubscribedAnalyzer;
        }
        if (analyzer == null || executor == null) {
            return Futures.immediateFailedFuture(new OperationCanceledException("No analyzer or executor currently set."));
        }
        return CallbackToFutureAdapter.getFuture(new t(this, executor, imageProxy, analyzer));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$analyzeImage$1 */
    public /* synthetic */ Object b(Executor executor, ImageProxy imageProxy, ImageAnalysis.Analyzer analyzer, CallbackToFutureAdapter.Completer completer) {
        executor.execute(new s(this, imageProxy, analyzer, completer));
        return "analyzeImage";
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$analyzeImage$0 */
    public /* synthetic */ void a(ImageProxy imageProxy, ImageAnalysis.Analyzer analyzer, CallbackToFutureAdapter.Completer completer) {
        if (this.mIsAttached) {
            analyzer.analyze(new SettableImageProxy(imageProxy, ImmutableImageInfo.create(imageProxy.getImageInfo().getTagBundle(), imageProxy.getImageInfo().getTimestamp(), this.mRelativeRotation)));
            completer.set(null);
            return;
        }
        completer.setException(new OperationCanceledException("ImageAnalysis is detached"));
    }

    /* access modifiers changed from: package-private */
    public void setRelativeRotation(int relativeRotation) {
        this.mRelativeRotation = relativeRotation;
    }

    /* access modifiers changed from: package-private */
    public void setAnalyzer(@Nullable Executor userExecutor, @Nullable ImageAnalysis.Analyzer subscribedAnalyzer) {
        synchronized (this.mAnalyzerLock) {
            if (subscribedAnalyzer == null) {
                clearCache();
            }
            this.mSubscribedAnalyzer = subscribedAnalyzer;
            this.mUserExecutor = userExecutor;
        }
    }

    /* access modifiers changed from: package-private */
    public void attach() {
        this.mIsAttached = true;
    }

    /* access modifiers changed from: package-private */
    public void detach() {
        this.mIsAttached = false;
        clearCache();
    }
}

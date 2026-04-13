package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;

public final class ImageAnalysisBlockingAnalyzer extends ImageAnalysisAbstractAnalyzer {
    ImageAnalysisBlockingAnalyzer() {
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ImageProxy acquireImage(@NonNull ImageReaderProxy imageReaderProxy) {
        return imageReaderProxy.acquireNextImage();
    }

    /* access modifiers changed from: package-private */
    public void onValidImageAvailable(@NonNull final ImageProxy imageProxy) {
        Futures.addCallback(analyzeImage(imageProxy), new FutureCallback<Void>() {
            public void onSuccess(Void result) {
            }

            public void onFailure(Throwable t) {
                imageProxy.close();
            }
        }, CameraXExecutors.directExecutor());
    }

    /* access modifiers changed from: package-private */
    public void clearCache() {
    }
}

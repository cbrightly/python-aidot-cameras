package androidx.camera.core;

import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.ImageAnalysisNonBlockingAnalyzer;

/* compiled from: lambda */
public final /* synthetic */ class u implements ForwardingImageProxy.OnImageCloseListener {
    public final /* synthetic */ ImageAnalysisNonBlockingAnalyzer.CacheAnalyzingImageProxy a;

    public /* synthetic */ u(ImageAnalysisNonBlockingAnalyzer.CacheAnalyzingImageProxy cacheAnalyzingImageProxy) {
        this.a = cacheAnalyzingImageProxy;
    }

    public final void onImageClose(ImageProxy imageProxy) {
        this.a.a(imageProxy);
    }
}

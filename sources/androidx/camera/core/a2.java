package androidx.camera.core;

/* compiled from: lambda */
public final /* synthetic */ class a2 implements Runnable {
    public final /* synthetic */ ImageAnalysisNonBlockingAnalyzer c;

    public /* synthetic */ a2(ImageAnalysisNonBlockingAnalyzer imageAnalysisNonBlockingAnalyzer) {
        this.c = imageAnalysisNonBlockingAnalyzer;
    }

    public final void run() {
        this.c.analyzeCachedImage();
    }
}

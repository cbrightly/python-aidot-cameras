package androidx.camera.core;

import androidx.camera.core.ImageAnalysis;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class s implements Runnable {
    public final /* synthetic */ ImageAnalysisAbstractAnalyzer c;
    public final /* synthetic */ ImageProxy d;
    public final /* synthetic */ ImageAnalysis.Analyzer f;
    public final /* synthetic */ CallbackToFutureAdapter.Completer q;

    public /* synthetic */ s(ImageAnalysisAbstractAnalyzer imageAnalysisAbstractAnalyzer, ImageProxy imageProxy, ImageAnalysis.Analyzer analyzer, CallbackToFutureAdapter.Completer completer) {
        this.c = imageAnalysisAbstractAnalyzer;
        this.d = imageProxy;
        this.f = analyzer;
        this.q = completer;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q);
    }
}

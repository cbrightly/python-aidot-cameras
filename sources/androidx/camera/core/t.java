package androidx.camera.core;

import androidx.camera.core.ImageAnalysis;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class t implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageAnalysisAbstractAnalyzer a;
    public final /* synthetic */ Executor b;
    public final /* synthetic */ ImageProxy c;
    public final /* synthetic */ ImageAnalysis.Analyzer d;

    public /* synthetic */ t(ImageAnalysisAbstractAnalyzer imageAnalysisAbstractAnalyzer, Executor executor, ImageProxy imageProxy, ImageAnalysis.Analyzer analyzer) {
        this.a = imageAnalysisAbstractAnalyzer;
        this.b = executor;
        this.c = imageProxy;
        this.d = analyzer;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.b(this.b, this.c, this.d, completer);
    }
}

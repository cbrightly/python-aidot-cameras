package androidx.camera.core;

import androidx.camera.core.ImageAnalysis;

/* compiled from: lambda */
public final /* synthetic */ class q implements ImageAnalysis.Analyzer {
    public final /* synthetic */ ImageAnalysis a;
    public final /* synthetic */ ImageAnalysis.Analyzer b;

    public /* synthetic */ q(ImageAnalysis imageAnalysis, ImageAnalysis.Analyzer analyzer) {
        this.a = imageAnalysis;
        this.b = analyzer;
    }

    public final void analyze(ImageProxy imageProxy) {
        this.a.b(this.b, imageProxy);
    }
}

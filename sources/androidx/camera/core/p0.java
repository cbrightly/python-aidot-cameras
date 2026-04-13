package androidx.camera.core;

import androidx.camera.core.internal.YuvToJpegProcessor;

/* compiled from: lambda */
public final /* synthetic */ class p0 implements Runnable {
    public final /* synthetic */ YuvToJpegProcessor c;
    public final /* synthetic */ CaptureProcessorPipeline d;

    public /* synthetic */ p0(YuvToJpegProcessor yuvToJpegProcessor, CaptureProcessorPipeline captureProcessorPipeline) {
        this.c = yuvToJpegProcessor;
        this.d = captureProcessorPipeline;
    }

    public final void run() {
        ImageCapture.lambda$createPipeline$1(this.c, this.d);
    }
}

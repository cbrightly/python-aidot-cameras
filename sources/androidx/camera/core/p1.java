package androidx.camera.core;

import androidx.camera.core.VideoCapture;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class p1 implements Runnable {
    public final /* synthetic */ VideoCapture c;
    public final /* synthetic */ VideoCapture.OutputFileOptions d;
    public final /* synthetic */ Executor f;
    public final /* synthetic */ VideoCapture.OnVideoSavedCallback q;

    public /* synthetic */ p1(VideoCapture videoCapture, VideoCapture.OutputFileOptions outputFileOptions, Executor executor, VideoCapture.OnVideoSavedCallback onVideoSavedCallback) {
        this.c = videoCapture;
        this.d = outputFileOptions;
        this.f = executor;
        this.q = onVideoSavedCallback;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q);
    }
}

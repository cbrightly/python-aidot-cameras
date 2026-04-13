package androidx.camera.core;

import androidx.camera.core.VideoCapture;

/* compiled from: lambda */
public final /* synthetic */ class s1 implements Runnable {
    public final /* synthetic */ VideoCapture.VideoSavedListenerWrapper c;
    public final /* synthetic */ VideoCapture.OutputFileResults d;

    public /* synthetic */ s1(VideoCapture.VideoSavedListenerWrapper videoSavedListenerWrapper, VideoCapture.OutputFileResults outputFileResults) {
        this.c = videoSavedListenerWrapper;
        this.d = outputFileResults;
    }

    public final void run() {
        this.c.b(this.d);
    }
}

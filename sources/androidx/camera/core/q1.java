package androidx.camera.core;

import androidx.camera.core.VideoCapture;

/* compiled from: lambda */
public final /* synthetic */ class q1 implements Runnable {
    public final /* synthetic */ VideoCapture c;
    public final /* synthetic */ VideoCapture.OnVideoSavedCallback d;

    public /* synthetic */ q1(VideoCapture videoCapture, VideoCapture.OnVideoSavedCallback onVideoSavedCallback) {
        this.c = videoCapture;
        this.d = onVideoSavedCallback;
    }

    public final void run() {
        this.c.d(this.d);
    }
}

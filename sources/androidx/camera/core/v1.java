package androidx.camera.core;

import android.util.Size;
import androidx.camera.core.VideoCapture;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class v1 implements Runnable {
    public final /* synthetic */ VideoCapture c;
    public final /* synthetic */ VideoCapture.OnVideoSavedCallback d;
    public final /* synthetic */ String f;
    public final /* synthetic */ Size q;
    public final /* synthetic */ CallbackToFutureAdapter.Completer x;

    public /* synthetic */ v1(VideoCapture videoCapture, VideoCapture.OnVideoSavedCallback onVideoSavedCallback, String str, Size size, CallbackToFutureAdapter.Completer completer) {
        this.c = videoCapture;
        this.d = onVideoSavedCallback;
        this.f = str;
        this.q = size;
        this.x = completer;
    }

    public final void run() {
        this.c.e(this.d, this.f, this.q, this.x);
    }
}

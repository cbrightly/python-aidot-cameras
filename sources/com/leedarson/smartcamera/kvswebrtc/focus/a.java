package com.leedarson.smartcamera.kvswebrtc.focus;

import android.util.Log;
import androidx.annotation.Nullable;
import com.leedarson.log.f;
import com.leedarson.smartcamera.kvswebrtc.focus.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;
import org.webrtc.VideoTrack;

/* compiled from: FocusGetYUV */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final List<VideoTrack> a;
    private b b;
    private boolean c = false;

    public a(@Nullable List<VideoTrack> videoTracks) {
        this.a = videoTracks;
    }

    public void startFocus(b.c onYUVDataListener) {
        if (!PatchProxy.proxy(new Object[]{onYUVDataListener}, this, changeQuickRedirect, false, 9937, new Class[]{b.c.class}, Void.TYPE).isSupported) {
            f.b("FocusGetYUV", "startFocus isRunning: " + this.c);
            if (!this.c) {
                this.c = true;
                if (this.b == null) {
                    this.b = new b();
                }
                this.b.setOnYUVDataListener(onYUVDataListener);
                for (VideoTrack videoTrack : this.a) {
                    if (videoTrack != null) {
                        videoTrack.addSink(this.b);
                    } else {
                        Log.e("FocusGetYUV", "Video track is null");
                    }
                }
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9938, new Class[0], Void.TYPE).isSupported) {
            this.c = false;
            List<VideoTrack> list = this.a;
            if (list != null && this.b != null) {
                for (VideoTrack videoTrack : list) {
                    videoTrack.removeSink(this.b);
                }
            }
        }
    }
}

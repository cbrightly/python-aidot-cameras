package com.leedarson.smartcamera.kvswebrtc.record;

import android.util.Log;
import androidx.annotation.Nullable;
import com.leedarson.log.f;
import com.leedarson.smartcamera.kvswebrtc.utils.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.util.List;
import org.webrtc.VideoTrack;

/* compiled from: MediaRecorder */
public class k {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final List<VideoTrack> a;
    private final h b;
    private m c;
    private boolean d = false;

    public k(@Nullable List<VideoTrack> videoTracks, @Nullable h audioInterceptor) {
        this.a = videoTracks;
        this.b = audioInterceptor;
    }

    public void a(File file) {
        if (!PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 9963, new Class[]{File.class}, Void.TYPE).isSupported) {
            f.b("MediaRecorder", "startRecording isRunning: " + this.d);
            if (!this.d) {
                this.d = true;
                file.getParentFile().mkdirs();
                for (VideoTrack videoTrack : this.a) {
                    if (videoTrack != null) {
                        if (this.c == null) {
                            this.c = new m(file.getAbsolutePath(), c.b(), this.b != null);
                        }
                        videoTrack.addSink(this.c);
                    } else {
                        Log.e("MediaRecorder", "Video track is null");
                        if (this.b != null) {
                            throw new Exception("Audio-only recording not implemented yet");
                        }
                    }
                }
                h hVar = this.b;
                if (hVar != null && this.c != null) {
                    hVar.a(1, this.c);
                }
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9964, new Class[0], Void.TYPE).isSupported) {
            this.d = false;
            h hVar = this.b;
            if (hVar != null) {
                hVar.b(1);
            }
            List<VideoTrack> list = this.a;
            if (list != null && this.c != null) {
                for (VideoTrack videoTrack : list) {
                    videoTrack.removeSink(this.c);
                }
                this.c.p();
                this.c = null;
            }
        }
    }
}

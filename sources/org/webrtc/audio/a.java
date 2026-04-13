package org.webrtc.audio;

import android.media.AudioRecord;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class a implements Callable {
    public final /* synthetic */ WebRtcAudioRecord c;
    public final /* synthetic */ AudioRecord d;

    public /* synthetic */ a(WebRtcAudioRecord webRtcAudioRecord, AudioRecord audioRecord) {
        this.c = webRtcAudioRecord;
        this.d = audioRecord;
    }

    public final Object call() {
        return this.c.a(this.d);
    }
}

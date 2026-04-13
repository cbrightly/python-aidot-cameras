package com.leedarson.smartcamera.kvswebrtc;

import com.leedarson.smartcamera.kvswebrtc.f0;
import org.webrtc.RTCStatsCollectorCallback;
import org.webrtc.RTCStatsReport;

/* compiled from: lambda */
public final /* synthetic */ class f implements RTCStatsCollectorCallback {
    public final /* synthetic */ f0.o a;

    public /* synthetic */ f(f0.o oVar) {
        this.a = oVar;
    }

    public final void onStatsDelivered(RTCStatsReport rTCStatsReport) {
        this.a.e(rTCStatsReport);
    }
}

package com.leedarson.smartcamera.kvswebrtc;

import com.leedarson.smartcamera.reporters.WebRtcLogStepBean;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class p implements Runnable {
    public final /* synthetic */ f0 c;
    public final /* synthetic */ JSONObject d;
    public final /* synthetic */ WebRtcLogStepBean f;

    public /* synthetic */ p(f0 f0Var, JSONObject jSONObject, WebRtcLogStepBean webRtcLogStepBean) {
        this.c = f0Var;
        this.d = jSONObject;
        this.f = webRtcLogStepBean;
    }

    public final void run() {
        this.c.V1(this.d, this.f);
    }
}

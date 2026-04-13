package com.leedarson.newui.view;

import com.leedarson.view.WebrtcSurfaceView;

/* compiled from: lambda */
public final /* synthetic */ class b implements WebrtcSurfaceView.a {
    public final /* synthetic */ BaseKVSCameraView a;

    public /* synthetic */ b(BaseKVSCameraView baseKVSCameraView) {
        this.a = baseKVSCameraView;
    }

    public final void onFirstFrameRendered() {
        this.a.B();
    }
}

package org.webrtc;

import android.graphics.SurfaceTexture;

/* compiled from: lambda */
public final /* synthetic */ class v implements SurfaceTexture.OnFrameAvailableListener {
    public final /* synthetic */ SurfaceTextureHelper a;

    public /* synthetic */ v(SurfaceTextureHelper surfaceTextureHelper) {
        this.a = surfaceTextureHelper;
    }

    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.a.c(surfaceTexture);
    }
}

package com.king.zxing.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import com.king.zxing.util.b;

/* compiled from: PreviewCallback */
public final class f implements Camera.PreviewCallback {
    private static final String a = f.class.getSimpleName();
    private final b b;
    private Handler c;
    private int d;

    f(b configManager) {
        this.b = configManager;
    }

    /* access modifiers changed from: package-private */
    public void a(Handler previewHandler, int previewMessage) {
        this.c = previewHandler;
        this.d = previewMessage;
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        Point cameraResolution = this.b.b();
        Handler thePreviewHandler = this.c;
        if (cameraResolution == null || thePreviewHandler == null) {
            b.a("zqr -lite Got preview callback, but no handler or resolution available");
            return;
        }
        b.h("PreviewCallback onPreviewFrame 得到一帧数据，发送给DecodeThread的DecodeHandler解码");
        thePreviewHandler.obtainMessage(this.d, cameraResolution.x, cameraResolution.y, data).sendToTarget();
        this.c = null;
    }
}

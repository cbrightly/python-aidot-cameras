package com.king.zxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.didichuxing.doraemonkit.zxing.decoding.DecodeThread;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.king.zxing.camera.d;
import com.king.zxing.util.b;
import com.leedarson.serviceimpl.camera.R$id;
import java.util.Collection;
import java.util.Map;

/* compiled from: CaptureHandler */
public class h extends Handler implements ResultPointCallback {
    private static final String c = h.class.getSimpleName();
    private boolean a1;
    private final n d;
    private final l f;
    private boolean p0;
    private boolean p1;
    private a q = a.SUCCESS;
    private final d x;
    private final ViewfinderView y;
    private boolean z;

    /* compiled from: CaptureHandler */
    public enum a {
        PREVIEW,
        SUCCESS,
        DONE
    }

    h(CaptureActivity activity, ViewfinderView viewfinderView, n onCaptureListener, Collection<BarcodeFormat> decodeFormats, Map<DecodeHintType, Object> baseHints, String characterSet, d cameraManager) {
        this.y = viewfinderView;
        this.d = onCaptureListener;
        l lVar = new l(activity, cameraManager, this, decodeFormats, baseHints, characterSet, this);
        this.f = lVar;
        lVar.start();
        this.x = cameraManager;
        cameraManager.r();
        b.h("开始预览并解码 111");
        e();
    }

    public void handleMessage(Message message) {
        int i = message.what;
        if (i == R$id.restart_preview) {
            b.h("restartPreviewAndDecode 222");
            e();
        } else if (i == R$id.decode_succeeded) {
            this.q = a.SUCCESS;
            Bundle bundle = message.getData();
            Bitmap barcode = null;
            float scaleFactor = 1.0f;
            if (bundle != null) {
                byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
                if (compressedBitmap != null) {
                    barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, (BitmapFactory.Options) null).copy(Bitmap.Config.ARGB_8888, true);
                }
                scaleFactor = bundle.getFloat("barcode_scaled_factor");
            }
            this.d.a((Result) message.obj, barcode, scaleFactor);
        } else if (i == R$id.decode_failed) {
            this.q = a.PREVIEW;
            this.x.j(this.f.a(), R$id.decode);
        }
    }

    public void d() {
        this.q = a.DONE;
        this.x.s();
        Message.obtain(this.f.a(), R$id.quit).sendToTarget();
        try {
            this.f.join(100);
        } catch (InterruptedException e) {
        }
        removeMessages(R$id.decode_succeeded);
        removeMessages(R$id.decode_failed);
    }

    public void e() {
        if (this.q == a.SUCCESS) {
            this.q = a.PREVIEW;
            this.x.j(this.f.a(), R$id.decode);
            ViewfinderView viewfinderView = this.y;
            if (viewfinderView != null) {
                viewfinderView.l();
            }
        }
    }

    public void foundPossibleResultPoint(ResultPoint point) {
        if (this.y != null) {
            this.y.c(j(point));
        }
    }

    private ResultPoint j(ResultPoint originPoint) {
        float y2;
        float x2;
        Point screenPoint = this.x.g();
        Point cameraPoint = this.x.c();
        int i = screenPoint.x;
        int i2 = screenPoint.y;
        if (i < i2) {
            float scaleY = (((float) i2) * 1.0f) / ((float) cameraPoint.x);
            x2 = (originPoint.getX() * ((((float) i) * 1.0f) / ((float) cameraPoint.y))) - ((float) (Math.max(screenPoint.x, cameraPoint.y) / 2));
            y2 = (originPoint.getY() * scaleY) - ((float) (Math.min(screenPoint.y, cameraPoint.x) / 2));
        } else {
            float scaleY2 = (((float) i2) * 1.0f) / ((float) cameraPoint.y);
            x2 = (originPoint.getX() * ((((float) i) * 1.0f) / ((float) cameraPoint.x))) - ((float) (Math.min(screenPoint.y, cameraPoint.y) / 2));
            y2 = (originPoint.getY() * scaleY2) - ((float) (Math.max(screenPoint.x, cameraPoint.x) / 2));
        }
        return new ResultPoint(x2, y2);
    }

    public boolean c() {
        return this.z;
    }

    public void i(boolean supportVerticalCode) {
        this.z = supportVerticalCode;
    }

    public boolean a() {
        return this.p0;
    }

    public void f(boolean returnBitmap) {
        this.p0 = returnBitmap;
    }

    public boolean b() {
        return this.a1;
    }

    public void g(boolean supportAutoZoom) {
        this.a1 = supportAutoZoom;
    }

    public void h(boolean supportLuminanceInvert) {
        this.p1 = supportLuminanceInvert;
    }
}

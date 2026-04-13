package com.king.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.view.SurfaceHolder;
import androidx.annotation.FloatRange;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.king.zxing.camera.open.c;
import java.io.IOException;

/* compiled from: CameraManager */
public final class d {
    private final Context a;
    private final b b;
    private com.king.zxing.camera.open.b c;
    private a d;
    private Rect e;
    private Rect f;
    private boolean g;
    private boolean h;
    private int i = -1;
    private int j;
    private int k;
    private boolean l;
    private float m;
    private int n;
    private int o;
    private final f p;
    private b q;
    private a r;
    private boolean s;

    /* compiled from: CameraManager */
    public interface a {
        void a(boolean z, boolean z2, float f);
    }

    /* compiled from: CameraManager */
    public interface b {
        void a(boolean z);
    }

    public d(Context context) {
        this.a = context.getApplicationContext();
        b bVar = new b(context);
        this.b = bVar;
        this.p = new f(bVar);
    }

    public void i(SurfaceHolder holder) {
        int i2;
        com.king.zxing.camera.open.b theCamera = this.c;
        if (theCamera == null) {
            theCamera = c.a(this.i);
            if (theCamera != null) {
                this.c = theCamera;
            } else {
                throw new IOException("Camera.open() failed to return object from driver");
            }
        }
        if (!this.g) {
            this.g = true;
            this.b.e(theCamera);
            int i3 = this.j;
            if (i3 > 0 && (i2 = this.k) > 0) {
                p(i3, i2);
                this.j = 0;
                this.k = 0;
            }
        }
        Camera cameraObject = theCamera.a();
        Camera.Parameters parameters = cameraObject.getParameters();
        String parametersFlattened = parameters == null ? null : parameters.flatten();
        try {
            this.b.g(theCamera, false);
        } catch (RuntimeException e2) {
            com.king.zxing.util.b.i("Camera rejected parameters. Setting only minimal safe-mode parameters");
            com.king.zxing.util.b.f("Resetting to saved camera params: " + parametersFlattened);
            if (parametersFlattened != null) {
                Camera.Parameters parameters2 = cameraObject.getParameters();
                parameters2.unflatten(parametersFlattened);
                try {
                    cameraObject.setParameters(parameters2);
                    this.b.g(theCamera, true);
                } catch (RuntimeException e3) {
                    com.king.zxing.util.b.i("Camera rejected even safe-mode parameters! No configuration");
                }
            }
        }
        cameraObject.setPreviewDisplay(holder);
    }

    public synchronized boolean h() {
        return this.c != null;
    }

    public com.king.zxing.camera.open.b f() {
        return this.c;
    }

    public void b() {
        com.king.zxing.camera.open.b bVar = this.c;
        if (bVar != null) {
            bVar.a().release();
            this.c = null;
            this.e = null;
            this.f = null;
        }
        this.s = false;
        b bVar2 = this.q;
        if (bVar2 != null) {
            bVar2.a(false);
        }
    }

    public void r() {
        com.king.zxing.camera.open.b theCamera = this.c;
        if (theCamera != null && !this.h) {
            theCamera.a().startPreview();
            this.h = true;
            this.d = new a(this.a, theCamera.a());
        }
    }

    public void s() {
        a aVar = this.d;
        if (aVar != null) {
            aVar.d();
            this.d = null;
        }
        com.king.zxing.camera.open.b bVar = this.c;
        if (bVar != null && this.h) {
            bVar.a().stopPreview();
            this.p.a((Handler) null, 0);
            this.h = false;
        }
    }

    public synchronized void q(boolean newSetting) {
        com.king.zxing.camera.open.b theCamera = this.c;
        if (!(theCamera == null || newSetting == this.b.d(theCamera.a()))) {
            a aVar = this.d;
            boolean wasAutoFocusManager = aVar != null;
            if (wasAutoFocusManager) {
                aVar.d();
                this.d = null;
            }
            this.s = newSetting;
            this.b.h(theCamera.a(), newSetting);
            if (wasAutoFocusManager) {
                a aVar2 = new a(this.a, theCamera.a());
                this.d = aVar2;
                aVar2.c();
            }
            b bVar = this.q;
            if (bVar != null) {
                bVar.a(newSetting);
            }
        }
    }

    public synchronized void j(Handler handler, int message) {
        com.king.zxing.camera.open.b theCamera = this.c;
        if (theCamera != null && this.h) {
            this.p.a(handler, message);
            theCamera.a().setOneShotPreviewCallback(this.p);
        }
    }

    public synchronized Rect d() {
        if (this.e == null) {
            if (this.c == null) {
                return null;
            }
            Point point = this.b.b();
            if (point == null) {
                return null;
            }
            int width = point.x;
            int height = point.y;
            if (this.l) {
                this.e = new Rect(0, 0, width, height);
            } else {
                int size = (int) (((float) Math.min(width, height)) * this.m);
                int leftOffset = ((width - size) / 2) + this.o;
                int topOffset = ((height - size) / 2) + this.n;
                this.e = new Rect(leftOffset, topOffset, leftOffset + size, topOffset + size);
            }
        }
        return this.e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007c, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.graphics.Rect e() {
        /*
            r7 = this;
            monitor-enter(r7)
            android.graphics.Rect r0 = r7.f     // Catch:{ all -> 0x0081 }
            if (r0 != 0) goto L_0x007d
            android.graphics.Rect r0 = r7.d()     // Catch:{ all -> 0x0081 }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r7)
            return r1
        L_0x000e:
            android.graphics.Rect r2 = new android.graphics.Rect     // Catch:{ all -> 0x0081 }
            r2.<init>(r0)     // Catch:{ all -> 0x0081 }
            com.king.zxing.camera.b r3 = r7.b     // Catch:{ all -> 0x0081 }
            android.graphics.Point r3 = r3.b()     // Catch:{ all -> 0x0081 }
            com.king.zxing.camera.b r4 = r7.b     // Catch:{ all -> 0x0081 }
            android.graphics.Point r4 = r4.c()     // Catch:{ all -> 0x0081 }
            if (r3 == 0) goto L_0x007b
            if (r4 != 0) goto L_0x0024
            goto L_0x007b
        L_0x0024:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0081 }
            r1.<init>()     // Catch:{ all -> 0x0081 }
            java.lang.String r5 = "getFramingRectInPreview cameraResolution:"
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            int r5 = r3.x     // Catch:{ all -> 0x0081 }
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            java.lang.String r5 = ",cameraResolution.y:"
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            int r5 = r3.y     // Catch:{ all -> 0x0081 }
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            java.lang.String r5 = ",screenResolution:"
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            int r5 = r4.x     // Catch:{ all -> 0x0081 }
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            java.lang.String r5 = ","
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            int r5 = r4.y     // Catch:{ all -> 0x0081 }
            r1.append(r5)     // Catch:{ all -> 0x0081 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0081 }
            com.king.zxing.util.b.h(r1)     // Catch:{ all -> 0x0081 }
            int r1 = r2.left     // Catch:{ all -> 0x0081 }
            int r5 = r3.y     // Catch:{ all -> 0x0081 }
            int r1 = r1 * r5
            int r6 = r4.x     // Catch:{ all -> 0x0081 }
            int r1 = r1 / r6
            r2.left = r1     // Catch:{ all -> 0x0081 }
            int r1 = r2.right     // Catch:{ all -> 0x0081 }
            int r1 = r1 * r5
            int r1 = r1 / r6
            r2.right = r1     // Catch:{ all -> 0x0081 }
            int r1 = r2.top     // Catch:{ all -> 0x0081 }
            int r5 = r3.x     // Catch:{ all -> 0x0081 }
            int r1 = r1 * r5
            int r6 = r4.y     // Catch:{ all -> 0x0081 }
            int r1 = r1 / r6
            r2.top = r1     // Catch:{ all -> 0x0081 }
            int r1 = r2.bottom     // Catch:{ all -> 0x0081 }
            int r1 = r1 * r5
            int r1 = r1 / r6
            r2.bottom = r1     // Catch:{ all -> 0x0081 }
            r7.f = r2     // Catch:{ all -> 0x0081 }
            goto L_0x007d
        L_0x007b:
            monitor-exit(r7)
            return r1
        L_0x007d:
            android.graphics.Rect r0 = r7.f     // Catch:{ all -> 0x0081 }
            monitor-exit(r7)
            return r0
        L_0x0081:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.king.zxing.camera.d.e():android.graphics.Rect");
    }

    public void o(boolean fullScreenScan) {
        this.l = fullScreenScan;
    }

    public void m(@FloatRange(from = 0.0d, to = 1.0d) float framingRectRatio) {
        this.m = framingRectRatio;
    }

    public void n(int framingRectVerticalOffset) {
        this.n = framingRectVerticalOffset;
    }

    public void l(int framingRectHorizontalOffset) {
        this.o = framingRectHorizontalOffset;
    }

    public Point c() {
        return this.b.b();
    }

    public Point g() {
        return this.b.c();
    }

    public synchronized void p(int width, int height) {
        if (this.g) {
            Point screenResolution = this.b.c();
            int i2 = screenResolution.x;
            if (width > i2) {
                width = i2;
            }
            int i3 = screenResolution.y;
            if (height > i3) {
                height = i3;
            }
            int leftOffset = (i2 - width) / 2;
            int topOffset = (i3 - height) / 2;
            this.e = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
            com.king.zxing.util.b.a("Calculated manual framing rect: " + this.e);
            this.f = null;
        } else {
            this.j = width;
            this.k = height;
        }
    }

    public PlanarYUVLuminanceSource a(byte[] data, int width, int height) {
        int top;
        int i2 = width;
        int i3 = height;
        if (e() == null) {
            return null;
        }
        if (this.l) {
            return new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
        }
        com.king.zxing.util.b.h("识别的区域比例:" + this.m);
        int size = (int) (((float) Math.min(width, height)) * this.m);
        com.king.zxing.util.b.h("识别的区域sizeMath.min(width,height) * " + this.m + "=" + size);
        int left = ((i2 - size) / 2) + this.o;
        int top2 = ((i3 - size) / 2) + this.n;
        if (top2 < 0) {
            top2 = 0;
        }
        com.king.zxing.util.b.h("裁剪的区域:left:" + left + ",top:" + top2 + ",right:" + size + ",bottom:" + size);
        if (top2 < 0) {
            top = 0;
        } else {
            top = top2;
        }
        com.king.zxing.util.b.h("真实的帧数据：" + data.length + ",摄像头预览尺寸width:" + i2 + ",height:" + i3 + ",识别的区域:" + size);
        return new PlanarYUVLuminanceSource(data, width, height, left, top, size, size, false);
    }

    public void setOnTorchListener(b listener) {
        this.q = listener;
    }

    public void setOnSensorListener(a listener) {
        this.r = listener;
    }

    public void k(boolean tooDark, float ambientLightLux) {
        a aVar = this.r;
        if (aVar != null) {
            aVar.a(this.s, tooDark, ambientLightLux);
        }
    }
}

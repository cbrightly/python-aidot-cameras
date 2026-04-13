package com.king.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.fragment.app.Fragment;
import com.didichuxing.doraemonkit.zxing.decoding.Intents;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.king.zxing.camera.d;
import com.king.zxing.camera.e;
import com.king.zxing.util.b;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/* compiled from: CaptureHelper */
public class i implements SurfaceHolder.Callback {
    private boolean A4;
    private float B4;
    private boolean C4;
    private boolean D4;
    private boolean E4;
    private boolean F4;
    private boolean G4;
    private boolean H4;
    private boolean I4;
    private boolean J4;
    private boolean K4;
    private float L4;
    private int M4;
    private int N4;
    private float O4;
    private float P4;
    private m Q4;
    private boolean R4;
    private ViewfinderView a1;
    private View a2;
    private CaptureActivity c;
    private h d;
    private n f;
    private SurfaceView p0;
    private SurfaceHolder p1;
    private Collection<BarcodeFormat> p2;
    private Map<DecodeHintType, Object> p3;
    private String p4;
    private d q;
    private InactivityTimer x;
    private g y;
    private f z;
    private boolean z4;

    public i(Fragment fragment, SurfaceView surfaceView, ViewfinderView viewfinderView, View ivTorch) {
        this((CaptureActivity) fragment.getActivity(), surfaceView, viewfinderView, ivTorch, ivTorch);
    }

    public i(CaptureActivity activity, SurfaceView surfaceView, ViewfinderView viewfinderView, View ivTorch, View torchTip) {
        this.A4 = true;
        this.C4 = true;
        this.D4 = false;
        this.E4 = false;
        this.F4 = true;
        this.L4 = 0.9f;
        this.O4 = 45.0f;
        this.P4 = 100.0f;
        this.c = activity;
        this.p0 = surfaceView;
        this.a1 = viewfinderView;
        this.a2 = ivTorch;
    }

    public void v() {
        this.p1 = this.p0.getHolder();
        this.z4 = false;
        this.x = new InactivityTimer(this.c);
        this.y = new g(this.c);
        this.z = new f(this.c);
        this.R4 = this.c.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        k();
        this.f = new a(this);
        this.y.g(this.G4);
        this.y.i(this.H4);
        this.z.b(this.O4);
        this.z.a(this.P4);
    }

    /* access modifiers changed from: private */
    /* renamed from: r */
    public /* synthetic */ void s(Result result, Bitmap barcode, float scaleFactor) {
        this.x.c();
        this.y.c();
        z(result, barcode, scaleFactor);
    }

    public void A() {
        this.y.l();
        this.x.e();
        if (this.z4) {
            j(this.p1);
        } else {
            this.p1.addCallback(this);
        }
        this.z.c(this.q);
    }

    public void x() {
        h hVar = this.d;
        if (hVar != null) {
            hVar.d();
            this.d = null;
        }
        this.x.d();
        this.z.d();
        this.y.close();
        this.q.b();
        if (!this.z4) {
            this.p1.removeCallback(this);
        }
        View view = this.a2;
        if (view != null && view.getVisibility() == 0) {
            this.a2.setSelected(false);
            this.a2.setVisibility(4);
            m mVar = this.Q4;
            if (mVar != null) {
                mVar.P(-1);
            }
        }
    }

    public void w() {
        this.x.f();
    }

    public boolean B(MotionEvent event) {
        Camera camera;
        if (!this.A4 || !this.q.h() || (camera = this.q.f().a()) == null || event.getPointerCount() <= 1) {
            return false;
        }
        switch (event.getAction() & 255) {
            case 2:
                float newDistance = b(event);
                float f2 = this.B4;
                if (newDistance > f2 + 6.0f) {
                    i(true, camera);
                } else if (newDistance < f2 - 6.0f) {
                    i(false, camera);
                }
                this.B4 = newDistance;
                break;
            case 5:
                this.B4 = b(event);
                break;
        }
        return true;
    }

    private void k() {
        d dVar = new d(this.c);
        this.q = dVar;
        dVar.o(this.K4);
        this.q.m(this.L4);
        this.q.n(this.M4);
        this.q.l(this.N4);
        View view = this.a2;
        if (view != null && this.R4) {
            view.setOnClickListener(new c(this));
            this.q.setOnSensorListener(new e(this));
            this.q.setOnTorchListener(new b(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: l */
    public /* synthetic */ void m(View view) {
        View view2 = view;
        d dVar = this.q;
        if (dVar != null) {
            dVar.q(!this.a2.isSelected());
        }
        if (this.Q4 != null) {
            this.Q4.P((int) this.a2.isSelected());
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ void o(boolean torch, boolean tooDark, float ambientLightLux) {
        if (tooDark) {
            m mVar = this.Q4;
            if (mVar != null) {
                mVar.P(0);
            }
            if (this.a2.getVisibility() != 0) {
                this.a2.setVisibility(0);
            }
        } else if (!torch) {
            m mVar2 = this.Q4;
            if (mVar2 != null) {
                mVar2.P(-1);
            }
            if (this.a2.getVisibility() == 0) {
                this.a2.setVisibility(4);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: p */
    public /* synthetic */ void q(boolean torch) {
        this.a2.setSelected(torch);
        this.Q4.P(torch ? 2 : 3);
    }

    private void j(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        } else if (this.q.h()) {
            b.i("initCamera() while already open -- late SurfaceView callback?");
        } else {
            try {
                this.q.i(surfaceHolder);
                if (this.d == null) {
                    h hVar = new h(this.c, this.a1, this.f, this.p2, this.p3, this.p4, this.q);
                    this.d = hVar;
                    hVar.i(this.I4);
                    this.d.f(this.J4);
                    this.d.g(this.C4);
                    this.d.h(this.D4);
                }
            } catch (IOException ioe) {
                b.k(ioe);
            } catch (RuntimeException e) {
                b.j("Unexpected error initializing camera", e);
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            b.i("*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!this.z4) {
            this.z4 = true;
            j(holder);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.z4 = false;
    }

    private void i(boolean isZoomIn, Camera camera) {
        b.h("handleZoom CaptureHelper touch setZoom");
        Camera.Parameters params = camera.getParameters();
        if (params.isZoomSupported()) {
            int maxZoom = params.getMaxZoom();
            int zoom = params.getZoom();
            if (isZoomIn && zoom < maxZoom) {
                zoom++;
            } else if (zoom > 0) {
                zoom--;
            }
            params.setZoom(zoom);
            camera.setParameters(params);
            return;
        }
        b.f("zoom not supported");
    }

    private float b(MotionEvent event) {
        float x2 = event.getX(0) - event.getX(1);
        float y2 = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x2 * x2) + (y2 * y2)));
    }

    public void D() {
        h hVar = this.d;
        if (hVar != null) {
            hVar.e();
        }
    }

    public void z(Result result, Bitmap barcode, float scaleFactor) {
        y(result);
    }

    public void y(Result result) {
        h hVar;
        if (result != null) {
            String text = result.getText();
            if (this.E4) {
                m mVar = this.Q4;
                if (mVar != null) {
                    mVar.r0(text);
                }
                if (this.F4) {
                    b.h("restartpreview 333");
                    D();
                }
            } else if (!this.G4 || (hVar = this.d) == null) {
                m mVar2 = this.Q4;
                if (mVar2 == null || !mVar2.r0(text)) {
                    Intent intent = new Intent();
                    intent.putExtra(Intents.Scan.RESULT, text);
                    this.c.setResult(-1, intent);
                    this.c.finish();
                }
            } else {
                hVar.postDelayed(new d(this, text), 100);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t */
    public /* synthetic */ void u(String text) {
        m mVar = this.Q4;
        if (mVar == null || !mVar.r0(text)) {
            Intent intent = new Intent();
            intent.putExtra(Intents.Scan.RESULT, text);
            this.c.setResult(-1, intent);
            this.c.finish();
        }
    }

    public i c(boolean isContinuousScan) {
        this.E4 = isContinuousScan;
        return this;
    }

    public i C(boolean playBeep) {
        this.G4 = playBeep;
        g gVar = this.y;
        if (gVar != null) {
            gVar.g(playBeep);
        }
        return this;
    }

    public i K(boolean vibrate) {
        this.H4 = vibrate;
        g gVar = this.y;
        if (gVar != null) {
            gVar.i(vibrate);
        }
        return this;
    }

    public i d(Collection<BarcodeFormat> decodeFormats) {
        this.p2 = decodeFormats;
        return this;
    }

    public i I(boolean supportVerticalCode) {
        this.I4 = supportVerticalCode;
        h hVar = this.d;
        if (hVar != null) {
            hVar.i(supportVerticalCode);
        }
        return this;
    }

    public i g(e mode) {
        e.put(this.c, mode);
        View view = this.a2;
        if (!(view == null || mode == e.AUTO)) {
            view.setVisibility(4);
        }
        return this;
    }

    public i J(float tooDarkLux) {
        this.O4 = tooDarkLux;
        f fVar = this.z;
        if (fVar != null) {
            fVar.b(tooDarkLux);
        }
        return this;
    }

    public i a(float brightEnoughLux) {
        this.P4 = brightEnoughLux;
        f fVar = this.z;
        if (fVar != null) {
            fVar.b(this.O4);
        }
        return this;
    }

    public i E(boolean returnBitmap) {
        this.J4 = returnBitmap;
        h hVar = this.d;
        if (hVar != null) {
            hVar.f(returnBitmap);
        }
        return this;
    }

    public i G(boolean supportAutoZoom) {
        this.C4 = supportAutoZoom;
        h hVar = this.d;
        if (hVar != null) {
            hVar.g(supportAutoZoom);
        }
        return this;
    }

    public i H(boolean supportLuminanceInvert) {
        this.D4 = supportLuminanceInvert;
        h hVar = this.d;
        if (hVar != null) {
            hVar.h(supportLuminanceInvert);
        }
        return this;
    }

    public i e(@FloatRange(from = 0.0d, to = 1.0d) float framingRectRatio) {
        this.L4 = framingRectRatio;
        d dVar = this.q;
        if (dVar != null) {
            dVar.m(framingRectRatio);
        }
        return this;
    }

    public i f(int framingRectVerticalOffset) {
        this.M4 = framingRectVerticalOffset;
        d dVar = this.q;
        if (dVar != null) {
            dVar.n(framingRectVerticalOffset);
        }
        return this;
    }

    public i F(m callback) {
        this.Q4 = callback;
        return this;
    }

    public d h() {
        return this.q;
    }
}

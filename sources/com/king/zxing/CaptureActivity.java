package com.king.zxing;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.Nullable;
import com.king.zxing.camera.d;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceimpl.camera.R$id;
import com.leedarson.serviceimpl.camera.R$layout;

public class CaptureActivity extends BaseActivity implements m {
    private ViewfinderView a1;
    private i a2;
    private SurfaceView p0;
    private View p1;
    private LDSTextView p2;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        j0();
        this.a2.v();
    }

    public void j0() {
        this.p0 = (SurfaceView) findViewById(a0());
        int viewfinderViewId = f0();
        if (viewfinderViewId != 0) {
            this.a1 = (ViewfinderView) findViewById(viewfinderViewId);
        }
        int ivTorchId = Z();
        int torchTipId = e0();
        if (torchTipId != 0) {
            LDSTextView lDSTextView = (LDSTextView) findViewById(torchTipId);
            this.p2 = lDSTextView;
            lDSTextView.setVisibility(4);
        }
        if (ivTorchId != 0) {
            View findViewById = findViewById(ivTorchId);
            this.p1 = findViewById;
            findViewById.setVisibility(4);
        }
        g0();
    }

    private int e0() {
        return R$id.tv_torch_tip;
    }

    public void g0() {
        i iVar = new i(this, this.p0, this.a1, this.p1, this.p2);
        this.a2 = iVar;
        iVar.F(this);
    }

    public int f0() {
        return R$id.viewfinderView;
    }

    public int a0() {
        return R$id.surfaceView;
    }

    public int Z() {
        return R$id.ivTorch;
    }

    public i Y() {
        return this.a2;
    }

    @Deprecated
    public d X() {
        return this.a2.h();
    }

    public void onResume() {
        super.onResume();
        this.a2.A();
    }

    public void onPause() {
        super.onPause();
        this.a2.x();
    }

    public void onDestroy() {
        super.onDestroy();
        this.a2.w();
    }

    /* access modifiers changed from: protected */
    public int O() {
        return R$layout.zxl_capture;
    }

    /* access modifiers changed from: protected */
    public void init() {
    }

    /* access modifiers changed from: protected */
    public void R() {
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.a2.B(event);
        return super.onTouchEvent(event);
    }

    public boolean r0(String result) {
        return false;
    }

    public String c0() {
        return "";
    }

    public String b0() {
        return "";
    }

    public void P(int status) {
        switch (status) {
            case -1:
                this.p2.setVisibility(4);
                return;
            case 0:
                this.p2.setVisibility(0);
                return;
            case 1:
                this.p2.setVisibility(0);
                return;
            case 2:
                this.p2.setText(b0());
                return;
            case 3:
                this.p2.setText(c0());
                return;
            default:
                return;
        }
    }
}

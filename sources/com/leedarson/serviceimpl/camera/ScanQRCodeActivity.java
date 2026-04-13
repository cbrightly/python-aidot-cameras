package com.leedarson.serviceimpl.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.zxing.DecodeHintType;
import com.king.zxing.CaptureActivity;
import com.king.zxing.ViewfinderView;
import com.king.zxing.j;
import com.leedarson.base.utils.p;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceimpl.camera.view.AddManualView;
import com.leedarson.serviceimpl.camera.view.CameraEnableView;
import com.leedarson.serviceinterface.CameraService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.event.CloseQRScanEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.QRScanResultEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.PictureSelectorActivity;
import com.luck.picture.lib.entity.LocalMedia;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.reactivex.l;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.cache.CacheHandler;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public class ScanQRCodeActivity extends CaptureActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ViewfinderView A4;
    private View B4;
    private LinearLayout C4;
    private LDSTextView D4;
    private LDSTextView E4;
    private CameraEnableView F4;
    private Button G4;
    private View H4;
    private AddManualView I4;
    private View J4;
    private String K4;
    private String L4;
    private String M4;
    private String N4;
    private Button O4;
    private String P4;
    private String Q4;
    private String R4;
    private int S4 = 0;
    io.reactivex.disposables.b T4;
    io.reactivex.disposables.b U4;
    io.reactivex.disposables.b V4;
    private String W4;
    private String X4;
    private String Y4;
    String Z4;
    int a5;
    int b5 = 50;
    String c5;
    int d5 = BaseResp.ERR_MSG_SEND_FAIL;
    int e5 = 600;
    String f5 = "center";
    float g5 = 25.0f;
    String h5;
    int i5 = 0;
    String j5 = "bottom";
    String k5 = "Add Manually";
    int l5 = 0;
    Handler m5 = new Handler(Looper.getMainLooper());
    boolean n5 = false;
    /* access modifiers changed from: private */
    public String o5;
    private boolean p3 = false;
    private LDSTextView p4;
    private i p5 = new i();
    private com.tbruyelle.rxpermissions2.b q5;
    private io.reactivex.disposables.b r5;
    /* access modifiers changed from: private */
    public LDSPermissitonGuideFragment s5;
    private View z4;

    static /* synthetic */ void k0(ScanQRCodeActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7420, new Class[]{ScanQRCodeActivity.class}, Void.TYPE).isSupported) {
            x0.w0();
        }
    }

    static /* synthetic */ void l0(ScanQRCodeActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7421, new Class[]{ScanQRCodeActivity.class}, Void.TYPE).isSupported) {
            x0.J0();
        }
    }

    static /* synthetic */ void m0(ScanQRCodeActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7422, new Class[]{ScanQRCodeActivity.class}, Void.TYPE).isSupported) {
            x0.v0();
        }
    }

    static /* synthetic */ void n0(ScanQRCodeActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7423, new Class[]{ScanQRCodeActivity.class}, Void.TYPE).isSupported) {
            x0.K0();
        }
    }

    static /* synthetic */ void s0(ScanQRCodeActivity x0, String x1, JSONObject x2) {
        Class[] clsArr = {ScanQRCodeActivity.class, String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7424, clsArr, Void.TYPE).isSupported) {
            x0.H0(x1, x2);
        }
    }

    static /* synthetic */ void u0(ScanQRCodeActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7425, new Class[]{ScanQRCodeActivity.class}, Void.TYPE).isSupported) {
            x0.N0();
        }
    }

    public boolean r0(String result) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{result}, this, changeQuickRedirect, false, 7394, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        a.b g2 = timber.log.a.g("ScanQrcode");
        g2.h("onResultCallback: " + result, new Object[0]);
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("result_type", 1);
        bundle.putString("result_string", result);
        resultIntent.putExtras(bundle);
        this.z4.setVisibility(0);
        this.A4.setStopScan(true);
        if (this.n5) {
            setResult(-1, resultIntent);
            finish();
        } else {
            com.king.zxing.util.b.h("发送数据给前端:");
            org.greenrobot.eventbus.c.c().l(new QRScanResultEvent(result));
            com.leedarson.base.utils.c.h().e(PictureSelectorActivity.class);
            com.king.zxing.util.b.h("延迟:" + this.S4 + "ms关闭界面");
            this.V4 = l.F(1).l((long) this.S4, TimeUnit.MILLISECONDS).b0(io.reactivex.android.schedulers.a.a()).J(io.reactivex.android.schedulers.a.a()).Y(new d(this), f.c);
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: E0 */
    public /* synthetic */ void F0(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 7419, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    static /* synthetic */ void G0(Throwable e2) {
        if (!PatchProxy.proxy(new Object[]{e2}, (Object) null, changeQuickRedirect, true, 7418, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            e2.printStackTrace();
        }
    }

    public String c0() {
        return this.X4;
    }

    public String b0() {
        return this.W4;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 7395, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setTitle("QRCode");
            if (this.q5 == null) {
                this.q5 = new com.tbruyelle.rxpermissions2.b(this);
            }
            this.l5 = com.leedarson.base.utils.d.b(this, 100.0f);
            overridePendingTransition(R$anim.slide_in_right, R$anim.slide_in_left);
            org.greenrobot.eventbus.c.c().p(this);
            Y().C(false).E(false).K(true).I(false).d(j.e).e(0.625f).f(-this.l5).g(com.king.zxing.camera.e.AUTO).J(45.0f).a(100.0f).c(false).G(true).H(true);
            z0();
            initView();
            A0();
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7396, new Class[0], Void.TYPE).isSupported) {
            this.m5.removeCallbacks((Runnable) null);
            super.finish();
            if (SharePreferenceUtils.getPrefString(this, "repositoryName", "").equals("C610-Innr")) {
                overridePendingTransition(R$anim.slide_in_right, R$anim.slide_in_left);
            } else {
                overridePendingTransition(R$anim.slide_out_right, R$anim.slide_out_left);
            }
        }
    }

    private void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7397, new Class[0], Void.TYPE).isSupported) {
            try {
                Intent intent = getIntent();
                int intExtra = intent.getIntExtra("manualButtonVisible", this.i5);
                this.i5 = intExtra;
                if (intExtra == 1) {
                    this.j5 = intent.getStringExtra("manualPosition");
                    this.k5 = intent.getStringExtra("manualText");
                }
                this.h5 = intent.getStringExtra("callbackKey");
                this.K4 = intent.getStringExtra("introductionDesc");
                this.L4 = intent.getStringExtra("introductionTitle");
                this.n5 = intent.getBooleanExtra("resultForNative", false);
                this.N4 = intent.getStringExtra("buttonTitle");
                this.Z4 = intent.getStringExtra("contentTitle");
                this.f5 = intent.getStringExtra("textAlign");
                this.X4 = intent.getStringExtra("turnOn");
                this.W4 = intent.getStringExtra("turnOff");
                this.M4 = intent.getStringExtra("title");
                try {
                    this.e5 = Integer.parseInt(intent.getStringExtra("fontWeight"));
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
                String stringExtra = intent.getStringExtra(TypedValues.Custom.S_COLOR);
                this.P4 = intent.getStringExtra("scanLineColor");
                this.Q4 = intent.getStringExtra("borderColor");
                this.R4 = intent.getStringExtra("cornersColor");
                this.c5 = intent.getStringExtra("buttonBackgroundColor");
                try {
                    this.d5 = Integer.parseInt(intent.getStringExtra("transitionDuration"));
                } catch (NumberFormatException e3) {
                    e3.printStackTrace();
                }
                String stringExtra2 = intent.getStringExtra("titleColor");
                String stringExtra3 = intent.getStringExtra("layoutType");
                String stringExtra4 = intent.getStringExtra("titleIconColor");
                String buttonRadius = intent.getStringExtra("buttonRadius");
                this.Y4 = intent.getStringExtra("processingTitle");
                String stringExtra5 = intent.getStringExtra("processingColor");
                String timeout = intent.getStringExtra("timeout");
                try {
                    this.a5 = (int) Float.parseFloat(intent.getStringExtra("buttonWidth"));
                } catch (NumberFormatException e4) {
                    e4.printStackTrace();
                }
                try {
                    this.b5 = (int) Float.parseFloat(intent.getStringExtra("buttonHeight"));
                } catch (NumberFormatException e6) {
                    e6.printStackTrace();
                }
                try {
                    if (!TextUtils.isEmpty(buttonRadius)) {
                        this.g5 = (float) ((int) Float.parseFloat(buttonRadius));
                    }
                } catch (NumberFormatException e7) {
                    e7.printStackTrace();
                }
                if (!TextUtils.isEmpty(timeout)) {
                    this.S4 = (int) Float.parseFloat(timeout);
                }
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        }
    }

    @SuppressLint({"CutPasteId"})
    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7398, new Class[0], Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setDarkMode(this);
            this.I4 = (AddManualView) findViewById(R$id.ll_add_manually);
            this.F4 = (CameraEnableView) findViewById(R$id.view_camera_enable);
            this.B4 = findViewById(R$id.root_view);
            this.C4 = (LinearLayout) findViewById(R$id.introduction_layout);
            this.E4 = (LDSTextView) findViewById(R$id.tv_introduction_desc);
            this.D4 = (LDSTextView) findViewById(R$id.tv_introduction_title);
            this.p4 = (LDSTextView) findViewById(R$id.text_title);
            int i2 = R$id.bottom_txt;
            this.O4 = (Button) findViewById(i2);
            this.A4 = (ViewfinderView) findViewById(R$id.viewfinderView);
            this.z4 = findViewById(R$id.view_progressing);
            this.G4 = (Button) findViewById(i2);
            this.H4 = findViewById(R$id.rl_torch);
            View findViewById = findViewById(R$id.album_layout);
            this.J4 = findViewById;
            findViewById.setOnClickListener(this);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x01e5  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01f0 A[FALL_THROUGH] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0233  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void A0() {
        /*
            r17 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7399(0x1ce7, float:1.0368E-41)
            r2 = r17
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0017
            return
        L_0x0017:
            r1 = r17
            int r2 = r1.i5
            r3 = 1
            if (r2 != r3) goto L_0x002c
            com.leedarson.serviceimpl.camera.view.CameraEnableView r2 = r1.F4
            java.lang.String r4 = r1.k5
            r2.c(r4)
            com.leedarson.serviceimpl.camera.view.CameraEnableView r2 = r1.F4
            java.lang.String r4 = r1.j5
            r2.setPosition(r4)
        L_0x002c:
            int r2 = com.leedarson.serviceimpl.camera.R$id.title_layout
            android.view.View r2 = r1.findViewById(r2)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            r4 = 0
            com.alibaba.android.arouter.launcher.a r5 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SystemService> r6 = com.leedarson.serviceinterface.SystemService.class
            java.lang.Object r5 = r5.g(r6)
            com.leedarson.serviceinterface.SystemService r5 = (com.leedarson.serviceinterface.SystemService) r5
            if (r5 == 0) goto L_0x0048
            int r6 = r5.getStatusBarHeight(r1)
            goto L_0x0049
        L_0x0048:
            r6 = r0
        L_0x0049:
            r4 = r6
            android.view.ViewGroup$LayoutParams r6 = r2.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r6 = (android.widget.RelativeLayout.LayoutParams) r6
            r6.topMargin = r4
            r2.setLayoutParams(r6)
            int r7 = com.leedarson.serviceimpl.camera.R$id.btn_back
            android.view.View r8 = r1.findViewById(r7)
            r8.setOnClickListener(r1)
            int r8 = r1.e5
            r9 = 400(0x190, float:5.6E-43)
            if (r8 < r9) goto L_0x0069
            com.leedarson.base.views.common.LDSTextView r8 = r1.p4
            r8.setLDSTypeface(r3)
        L_0x0069:
            com.leedarson.base.views.common.LDSTextView r8 = r1.p4
            java.lang.String r9 = r1.M4
            r8.setText(r9)
            java.lang.String r8 = r1.N4
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            r9 = 4
            if (r8 != 0) goto L_0x0081
            android.widget.Button r8 = r1.O4
            java.lang.String r10 = r1.N4
            r8.setText(r10)
            goto L_0x0086
        L_0x0081:
            android.widget.Button r8 = r1.O4
            r8.setVisibility(r9)
        L_0x0086:
            com.king.zxing.ViewfinderView r8 = r1.A4
            java.lang.String r10 = r1.P4
            r8.setlaserColor(r10)
            com.king.zxing.ViewfinderView r8 = r1.A4
            java.lang.String r10 = r1.R4
            r8.setCornerColor(r10)
            com.king.zxing.ViewfinderView r8 = r1.A4
            java.lang.String r10 = r1.Q4
            r8.setFrameColor(r10)
            com.king.zxing.ViewfinderView r8 = r1.A4
            r10 = 2
            r8.a(r10)
            com.king.zxing.ViewfinderView r8 = r1.A4
            r8.b(r10)
            int r8 = com.leedarson.serviceimpl.camera.R$id.tv_processing
            android.view.View r8 = r1.findViewById(r8)
            com.leedarson.base.views.common.LDSTextView r8 = (com.leedarson.base.views.common.LDSTextView) r8
            java.lang.String r11 = r1.Y4
            r8.setText(r11)
            int r11 = com.leedarson.serviceimpl.camera.R$id.txtResult
            android.view.View r11 = r1.findViewById(r11)
            com.leedarson.base.views.common.LDSTextView r11 = (com.leedarson.base.views.common.LDSTextView) r11
            java.lang.String r12 = r1.Z4
            r11.setText(r12)
            java.lang.String r12 = r1.Z4
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 == 0) goto L_0x00d2
            int r12 = com.leedarson.serviceimpl.camera.R$id.iv_qrcode_icon
            android.view.View r12 = r1.findViewById(r12)
            r12.setVisibility(r9)
            goto L_0x00db
        L_0x00d2:
            int r9 = com.leedarson.serviceimpl.camera.R$id.iv_qrcode_icon
            android.view.View r9 = r1.findViewById(r9)
            r9.setVisibility(r0)
        L_0x00db:
            int r9 = com.leedarson.serviceimpl.camera.R$id.tv_torch_tip
            android.view.View r9 = r1.findViewById(r9)
            com.leedarson.base.views.common.LDSTextView r9 = (com.leedarson.base.views.common.LDSTextView) r9
            java.lang.String r12 = r1.X4
            r9.setText(r12)
            int r12 = com.leedarson.serviceimpl.camera.R$id.ll_result
            android.view.View r12 = r1.findViewById(r12)
            java.lang.String r13 = "repositoryName"
            java.lang.String r14 = ""
            java.lang.String r13 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r1, r13, r14)
            java.lang.String r14 = "C610-Innr"
            boolean r14 = r13.equals(r14)
            if (r14 == 0) goto L_0x0153
            com.king.zxing.ViewfinderView r14 = r1.A4
            r10 = -13619652(0xffffffffff302e3c, float:-2.3418419E38)
            r14.setMaskColor(r10)
            android.content.res.AssetManager r10 = r1.getAssets()
            java.lang.String r14 = "NeurialGrotesk.ttf"
            android.graphics.Typeface r14 = android.graphics.Typeface.createFromAsset(r10, r14)
            android.widget.LinearLayout r15 = r1.C4
            r15.setVisibility(r0)
            com.leedarson.base.views.common.LDSTextView r15 = r1.E4
            r15.setTypeface(r14)
            com.leedarson.base.views.common.LDSTextView r15 = r1.p4
            android.graphics.Typeface r0 = android.graphics.Typeface.defaultFromStyle(r3)
            r15.setTypeface(r0)
            com.leedarson.base.views.common.LDSTextView r0 = r1.D4
            android.graphics.Typeface r15 = android.graphics.Typeface.defaultFromStyle(r3)
            r0.setTypeface(r15)
            com.leedarson.base.views.common.LDSTextView r0 = r1.D4
            java.lang.String r15 = r1.L4
            r0.setText(r15)
            com.leedarson.base.views.common.LDSTextView r0 = r1.E4
            java.lang.String r15 = r1.K4
            r0.setText(r15)
            r0 = 8
            r12.setVisibility(r0)
            r15 = 0
            r1.y0(r15)
            com.leedarson.serviceimpl.camera.view.AddManualView r15 = r1.I4
            r15.setVisibility(r0)
            com.leedarson.base.views.common.LDSTextView r0 = r1.p4
            android.text.TextPaint r0 = r0.getPaint()
            r0.setFakeBoldText(r3)
        L_0x0151:
            r14 = 0
            goto L_0x01bc
        L_0x0153:
            java.lang.String r0 = "M071-AiDot"
            boolean r0 = r13.equals(r0)
            if (r0 != 0) goto L_0x0163
            java.lang.String r0 = "M071-Linkind"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x0151
        L_0x0163:
            java.lang.String r0 = "android.permission.CAMERA"
            java.lang.String[] r0 = new java.lang.String[]{r0}
            boolean r0 = pub.devrel.easypermissions.EasyPermissions.a(r1, r0)
            if (r0 != 0) goto L_0x017e
            android.content.res.Resources r0 = r1.getResources()
            int r10 = com.leedarson.serviceimpl.camera.R$drawable.bg_qrcode
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeResource(r0, r10)
            com.king.zxing.ViewfinderView r10 = r1.A4
            r10.setMaskBitmap(r0)
        L_0x017e:
            com.king.zxing.ViewfinderView r0 = r1.A4
            int r10 = r1.l5
            int r10 = -r10
            r14 = 0
            r0.setPadding(r14, r10, r14, r14)
            int r0 = r1.l5
            r1.y0(r0)
            android.view.View r0 = r1.H4
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r0 = (android.widget.RelativeLayout.LayoutParams) r0
            android.view.View r10 = r1.H4
            int r15 = r1.l5
            r10.setPadding(r14, r14, r14, r15)
            int r10 = r1.i5
            if (r10 != r3) goto L_0x01b5
            com.leedarson.serviceimpl.camera.view.AddManualView r10 = r1.I4
            r10.setVisibility(r14)
            com.leedarson.serviceimpl.camera.view.AddManualView r10 = r1.I4
            java.lang.String r15 = r1.k5
            r10.setText(r15)
            com.leedarson.serviceimpl.camera.view.AddManualView r10 = r1.I4
            com.leedarson.serviceimpl.camera.ScanQRCodeActivity$a r15 = new com.leedarson.serviceimpl.camera.ScanQRCodeActivity$a
            r15.<init>()
            r10.setClickEvent(r15)
        L_0x01b5:
            android.widget.Button r10 = r1.O4
            r15 = 8
            r10.setVisibility(r15)
        L_0x01bc:
            com.leedarson.base.views.common.LDSTextView r0 = r1.p4
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.widget.RelativeLayout$LayoutParams r0 = (android.widget.RelativeLayout.LayoutParams) r0
            java.lang.String r10 = r1.f5
            int r15 = r10.hashCode()
            r14 = -1
            switch(r15) {
                case -1364013995: goto L_0x01e5;
                case 3317767: goto L_0x01da;
                case 108511772: goto L_0x01cf;
                default: goto L_0x01ce;
            }
        L_0x01ce:
            goto L_0x01f0
        L_0x01cf:
            java.lang.String r15 = "right"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x01ce
            r16 = 2
            goto L_0x01f2
        L_0x01da:
            java.lang.String r15 = "left"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x01ce
            r16 = r3
            goto L_0x01f2
        L_0x01e5:
            java.lang.String r15 = "center"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x01ce
            r16 = 0
            goto L_0x01f2
        L_0x01f0:
            r16 = r14
        L_0x01f2:
            r10 = -2
            switch(r16) {
                case 0: goto L_0x0233;
                case 1: goto L_0x0213;
                case 2: goto L_0x01f7;
                default: goto L_0x01f6;
            }
        L_0x01f6:
            goto L_0x0242
        L_0x01f7:
            com.leedarson.base.views.common.LDSTextView r15 = r1.p4
            android.view.ViewGroup$LayoutParams r15 = r15.getLayoutParams()
            r0 = r15
            android.widget.RelativeLayout$LayoutParams r0 = (android.widget.RelativeLayout.LayoutParams) r0
            r0.width = r14
            r0.height = r10
            r0.addRule(r3, r7)
            com.leedarson.base.views.common.LDSTextView r3 = r1.p4
            r7 = 5
            r3.setGravity(r7)
            com.leedarson.base.views.common.LDSTextView r3 = r1.p4
            r3.setLayoutParams(r0)
            goto L_0x0242
        L_0x0213:
            r0.width = r14
            r0.height = r10
            r0.addRule(r3, r7)
            r3 = 15
            r0.addRule(r3)
            r3 = 1098907648(0x41800000, float:16.0)
            int r3 = com.leedarson.base.utils.d.b(r1, r3)
            r0.leftMargin = r3
            com.leedarson.base.views.common.LDSTextView r3 = r1.p4
            r7 = 3
            r3.setGravity(r7)
            com.leedarson.base.views.common.LDSTextView r3 = r1.p4
            r3.setLayoutParams(r0)
            goto L_0x0242
        L_0x0233:
            r0.width = r10
            r0.height = r10
            r3 = 13
            r0.addRule(r3)
            com.leedarson.base.views.common.LDSTextView r3 = r1.p4
            r3.setLayoutParams(r0)
        L_0x0242:
            com.leedarson.serviceimpl.camera.view.CameraEnableView r3 = r1.F4
            com.leedarson.serviceimpl.camera.ScanQRCodeActivity$b r7 = new com.leedarson.serviceimpl.camera.ScanQRCodeActivity$b
            r7.<init>()
            r3.setOnItemClickListener(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.camera.ScanQRCodeActivity.A0():void");
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7426, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ScanQRCodeActivity.k0(ScanQRCodeActivity.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class b implements CameraEnableView.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void c(View view, int i) {
            if (!PatchProxy.proxy(new Object[]{view, new Integer(i)}, this, changeQuickRedirect, false, 7427, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
                ScanQRCodeActivity.l0(ScanQRCodeActivity.this);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7428, new Class[0], Void.TYPE).isSupported) {
                ScanQRCodeActivity.m0(ScanQRCodeActivity.this);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7429, new Class[0], Void.TYPE).isSupported) {
                ScanQRCodeActivity.k0(ScanQRCodeActivity.this);
            }
        }
    }

    private void y0(int i2) {
        if (!PatchProxy.proxy(new Object[]{new Integer(i2)}, this, changeQuickRedirect, false, 7400, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int topOffset = i2;
            this.G4.setOnClickListener(this);
            float btnRadius = (((float) g.a((float) this.b5)) * this.g5) / 100.0f;
            ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{btnRadius, btnRadius, btnRadius, btnRadius, btnRadius, btnRadius, btnRadius, btnRadius}, (RectF) null, (float[]) null));
            if (!TextUtils.isEmpty(this.c5)) {
                drawable.getPaint().setColor(Color.parseColor(this.c5));
            }
            drawable.getPaint().setAntiAlias(true);
            drawable.getPaint().setStyle(Paint.Style.FILL);
            this.G4.setBackground(drawable);
            ViewGroup.LayoutParams layoutParams = this.G4.getLayoutParams();
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            int x = point.x;
            int y = point.y;
            layoutParams.width = g.a((float) this.a5) > ((int) (((float) x) * 0.8f)) ? (int) (((float) x) * 0.8f) : g.a((float) this.a5);
            layoutParams.height = g.a((float) this.b5);
            this.G4.setLayoutParams(layoutParams);
            LinearLayout llBottom = (LinearLayout) findViewById(R$id.bottom_layout);
            RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) llBottom.getLayoutParams();
            params3.height = (((int) ((((float) y) - (((float) x) * 0.625f)) / 2.0f)) + topOffset) - 20;
            llBottom.setLayoutParams(params3);
            LinearLayout.LayoutParams layoutBottomTex = (LinearLayout.LayoutParams) this.O4.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = layoutParams;
            layoutBottomTex.topMargin = ((int) ((((float) y) - (((float) x) * 0.625f)) / 5.0f)) + topOffset;
            this.O4.setLayoutParams(layoutBottomTex);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7401, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int id = v.getId();
        if (id == R$id.btn_back) {
            v0();
        } else if (id == R$id.bottom_txt) {
            this.z4.setVisibility(0);
            this.A4.setStopScan(true);
            w0();
        } else if (id == R$id.album_layout) {
            K0();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    private void K0() {
        String[] perms;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7402, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            if (EasyPermissions.a(this, perms)) {
                org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(2, "{\"cut\":true,\"type\":[\"image\"]}", (Activity) this));
            } else {
                LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new c(perms));
            }
        }
    }

    public class c implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        c(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 7430, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, ScanQRCodeActivity.this, this.a, "albumDeny", new b(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7431, new Class[0], Void.TYPE).isSupported) {
                ScanQRCodeActivity.n0(ScanQRCodeActivity.this);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Object[] objArr = {new Integer(requestCode), new Integer(resultCode), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7403, new Class[]{cls, cls, Intent.class}, Void.TYPE).isSupported) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.i("zqr", "onActivityResult...");
            if (requestCode == 188 && resultCode == -1 && data != null) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    this.m5.postDelayed(new d(selectList.get(0)), 200);
                }
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LocalMedia c;

        d(LocalMedia localMedia) {
            this.c = localMedia;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7432, new Class[0], Void.TYPE).isSupported) {
                String result = ScanQRCodeActivity.this.L0(this.c.getRealPath());
                ScanQRCodeActivity.this.r0(result != null ? result : JustifyTextView.TWO_CHINESE_BLANK);
            }
        }
    }

    public String L0(String filepath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filepath}, this, changeQuickRedirect, false, 7404, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Log.i("zqr", "uri:" + filepath);
        new Hashtable<>().put(DecodeHintType.CHARACTER_SET, "UTF8");
        String result = com.king.zxing.util.a.c(filepath);
        Log.i("zqr", "result:" + result);
        return result;
    }

    private void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7405, new Class[0], Void.TYPE).isSupported) {
            H0("AddDeviceNativeScanManually", new JSONObject());
            CameraService cameraService = (CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class);
            if (cameraService != null) {
                cameraService.clickButton();
            }
            this.U4 = l.F(1).l((long) this.S4, TimeUnit.MILLISECONDS).Y(new c(this), e.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B0 */
    public /* synthetic */ void C0(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 7417, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    static /* synthetic */ void D0(Throwable e2) {
        if (!PatchProxy.proxy(new Object[]{e2}, (Object) null, changeQuickRedirect, true, 7416, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            e2.printStackTrace();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onReceiveCloseQRScanEvent(CloseQRScanEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 7406, new Class[]{CloseQRScanEvent.class}, Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.T4;
            if (bVar != null && bVar.isDisposed()) {
                this.T4.dispose();
            }
            io.reactivex.disposables.b bVar2 = this.U4;
            if (bVar2 != null && bVar2.isDisposed()) {
                this.U4.dispose();
            }
            io.reactivex.disposables.b bVar3 = this.V4;
            if (bVar3 != null && bVar3.isDisposed()) {
                this.V4.dispose();
            }
            a.b g2 = timber.log.a.g("CZB");
            g2.a("CloseQRScanEvent delay:" + event.delay, new Object[0]);
            this.o5 = event.navigationType;
            if (this.m5 == null) {
                this.m5 = new Handler(Looper.getMainLooper());
            }
            this.m5.removeCallbacks(this.p5);
            this.m5.postDelayed(this.p5, (long) event.delay);
        }
    }

    public final class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7440, new Class[0], Void.TYPE).isSupported) {
                if ("back".equals(ScanQRCodeActivity.this.o5)) {
                    ScanQRCodeActivity.this.finish();
                } else if ("forward".equals(ScanQRCodeActivity.this.o5)) {
                    ScanQRCodeActivity.this.finish();
                    ScanQRCodeActivity.this.overridePendingTransition(R$anim.slide_in_right, R$anim.slide_in_left);
                }
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7407, new Class[0], Void.TYPE).isSupported) {
            super.onBackPressed();
            v0();
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7408, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            x0();
            ViewfinderView viewfinderView = this.A4;
            if (viewfinderView != null) {
                viewfinderView.setStopScan(false);
            }
        }
    }

    private void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7409, new Class[0], Void.TYPE).isSupported) {
            this.A4.setStopScan(true);
            CameraService cameraService = (CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class);
            if (cameraService != null) {
                cameraService.clickBack();
            }
            finish();
        }
    }

    private void I0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7410, new Class[0], Void.TYPE).isSupported) {
            this.r5 = this.q5.l("android.permission.CAMERA").Y(new e(), new f());
        }
    }

    public class e implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7434, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 7433, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("settings_result", permission.b);
                ScanQRCodeActivity.s0(ScanQRCodeActivity.this, "ClickCameraPermissionSystemSettings", jsonObject);
                if (permission.b) {
                    LDSPermissionGuide.c(ScanQRCodeActivity.this.s5);
                    ScanQRCodeActivity.u0(ScanQRCodeActivity.this);
                } else if (permission.c) {
                    timber.log.a.g("CZB").a("拒绝，下次还可询问", new Object[0]);
                    SharePreferenceUtils.setPrefBoolean(ScanQRCodeActivity.this, "camera_deny", true);
                    LDSPermissionGuide.c(ScanQRCodeActivity.this.s5);
                } else {
                    timber.log.a.g("CZB").a("已拒绝且不在提示", new Object[0]);
                    SharePreferenceUtils.setPrefBoolean(ScanQRCodeActivity.this, "camera_deny", true);
                    LDSPermissionGuide.c(ScanQRCodeActivity.this.s5);
                }
            }
        }
    }

    public class f implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7436, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 7435, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                throwable.printStackTrace();
            }
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7411, new Class[0], Void.TYPE).isSupported) {
            boolean b2 = EasyPermissions.a(this, "android.permission.CAMERA");
            try {
                new JSONObject().put("settings_result", b2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (b2) {
                SharePreferenceUtils.setPrefBoolean(this, "camera_deny", false);
                LDSPermissionGuide.c(this.s5);
                this.F4.setVisibility(8);
                this.m5.postDelayed(new g(), CacheHandler.delayTime);
            } else if (SharePreferenceUtils.getPrefBoolean(this, "camera_deny", false)) {
                this.F4.setVisibility(0);
            } else {
                LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.s5;
                if (lDSPermissitonGuideFragment == null || !lDSPermissitonGuideFragment.isAdded()) {
                    this.s5 = LDSPermissionGuide.d(this, new LDSPermissionGuide.CameraGuideParam(this), new h());
                }
            }
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7437, new Class[0], Void.TYPE).isSupported) {
                ScanQRCodeActivity.u0(ScanQRCodeActivity.this);
            }
        }
    }

    public class h implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onCloseClick() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7438, new Class[0], Void.TYPE).isSupported) {
                ScanQRCodeActivity scanQRCodeActivity = ScanQRCodeActivity.this;
                if (scanQRCodeActivity.i5 == 1) {
                    LDSPermissionGuide.c(scanQRCodeActivity.s5);
                } else {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(ScanQRCodeActivity.this.h5, p.a(401, "点击XX关闭").toString()));
                }
            }
        }

        public void onActionClick(LDSPermissitonGuideFragment lDSPermissitonGuideFragment) {
            if (!PatchProxy.proxy(new Object[]{lDSPermissitonGuideFragment}, this, changeQuickRedirect, false, 7439, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                ScanQRCodeActivity.l0(ScanQRCodeActivity.this);
            }
        }
    }

    private void J0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7412, new Class[0], Void.TYPE).isSupported) {
            boolean canRequest = true;
            if (SharePreferenceUtils.getPrefBoolean(this, "camera_deny", false)) {
                if (EasyPermissions.h(this, "android.permission.CAMERA") || EasyPermissions.h(this, "android.permission.CAMERA")) {
                    canRequest = true;
                } else {
                    canRequest = false;
                }
            }
            if (canRequest) {
                I0();
                return;
            }
            H0("ClickCameraPermissionGoSettings", new JSONObject());
            w.K(this);
        }
    }

    private void N0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7413, new Class[0], Void.TYPE).isSupported) {
            this.A4.setMaskBitmap((Bitmap) null);
            Y().A();
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 7414, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
            if (intent != null) {
                timber.log.a.g("CZB").a("继续扫描", new Object[0]);
                this.h5 = intent.getStringExtra("callbackKey");
            }
            this.z4.setVisibility(8);
            io.reactivex.disposables.b bVar = this.V4;
            if (bVar != null && !bVar.isDisposed()) {
                this.V4.dispose();
            }
            N0();
        }
    }

    private void H0(String event, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{event, jsonObject}, this, changeQuickRedirect, false, 7415, clsArr, Void.TYPE).isSupported) {
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null) {
                loggerService.reportSensorsData(event, jsonObject);
            }
        }
    }
}

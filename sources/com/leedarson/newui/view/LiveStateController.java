package com.leedarson.newui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.request.target.j;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.LoadingWaveBoxView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.newui.ai.beans.AiMarkPositionBean;
import com.leedarson.newui.ai.widget.AiMarkLayoutView;
import com.leedarson.newui.repos.beans.AbnormalVersionBean;
import com.leedarson.newui.view.LDSLiveReconnectAndTestBoxTipView;
import com.leedarson.newui.view.LDSLiveReconnectBoxTipView;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.k;
import com.leedarson.view.LDSImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.Calendar;

public class LiveStateController extends RelativeLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView A4;
    private LDSLiveReconnectBoxTipView B4;
    private LDSLiveReconnectAndTestBoxTipView C4;
    private RelativeLayout D4;
    private LDSTextView E4;
    private RelativeLayout F4;
    private ImageView G4;
    protected float H4 = 0.5625f;
    private Calendar I4;
    private boolean J4 = false;
    /* access modifiers changed from: private */
    public String K4 = "";
    private String L4;
    private ImageView a1;
    private int a2 = 0;
    /* access modifiers changed from: private */
    public LDSImageView c;
    private ImageView d;
    private ImageView f;
    private Button p0;
    private LDSTextView p1;
    /* access modifiers changed from: private */
    public e p2;
    private GradientDrawable p3 = new GradientDrawable();
    /* access modifiers changed from: private */
    public int p4;
    /* access modifiers changed from: private */
    public AiMarkLayoutView q;
    private View x;
    private RelativeLayout y;
    private LoadingWaveBoxView z;
    private RelativeLayout z4;

    public interface e {
        void a();

        void b();

        void c();

        void d();

        void e();

        void f();

        void g();

        void h();

        void i();

        void j();
    }

    public LiveStateController(Context context) {
        super(context);
    }

    public LiveStateController(Context context, AttributeSet attrs) {
        super(context, attrs);
        p(context);
    }

    private void p(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5134, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.include_live_state, this, true);
            this.c = (LDSImageView) findViewById(R$id.iv_preview);
            ImageView imageView = (ImageView) findViewById(R$id.iv_turnonoff);
            this.d = imageView;
            imageView.setOnClickListener(this);
            ImageView imageView2 = (ImageView) findViewById(R$id.iv_back);
            this.f = imageView2;
            imageView2.setOnClickListener(this);
            this.x = findViewById(R$id.view_shade);
            this.z = (LoadingWaveBoxView) findViewById(R$id.pb_loading);
            Button button = (Button) findViewById(R$id.state_btn);
            this.p0 = button;
            button.setOnClickListener(this);
            this.y = (RelativeLayout) findViewById(R$id.layout_wakeup);
            this.a1 = (ImageView) findViewById(R$id.img_wakeup);
            this.p1 = (LDSTextView) findViewById(R$id.txt_wakeup_tips);
            this.z4 = (RelativeLayout) findViewById(R$id.rlPlayerLoadingContianer);
            this.a1.setOnClickListener(this);
            this.D4 = (RelativeLayout) findViewById(R$id.player_title_layout);
            this.E4 = (LDSTextView) findViewById(R$id.tv_title);
            this.q = (AiMarkLayoutView) findViewById(R$id.aiMarkLayout);
            this.A4 = (LDSTextView) findViewById(R$id.tv_camera_status);
            LDSLiveReconnectAndTestBoxTipView lDSLiveReconnectAndTestBoxTipView = (LDSLiveReconnectAndTestBoxTipView) findViewById(R$id.reconnect_test_box);
            this.C4 = lDSLiveReconnectAndTestBoxTipView;
            lDSLiveReconnectAndTestBoxTipView.setLiveReconnectClickListener(new a());
            LDSLiveReconnectBoxTipView lDSLiveReconnectBoxTipView = (LDSLiveReconnectBoxTipView) findViewById(R$id.reconnect_box);
            this.B4 = lDSLiveReconnectBoxTipView;
            lDSLiveReconnectBoxTipView.setLiveReconnectClickListener(new b());
            this.F4 = (RelativeLayout) findViewById(R$id.layout_low_battery);
            ImageView imageView3 = (ImageView) findViewById(R$id.img_unavail);
            this.G4 = imageView3;
            imageView3.setOnClickListener(this);
            this.I4 = Calendar.getInstance();
        }
    }

    public class a implements LDSLiveReconnectAndTestBoxTipView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5167, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickReconnect");
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.f();
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5168, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickHelp");
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.b();
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5169, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickReportIssue");
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.c();
                }
            }
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5170, new Class[0], Void.TYPE).isSupported) {
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.g();
                }
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5171, new Class[0], Void.TYPE).isSupported) {
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.e();
                }
            }
        }

        public void i() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5172, new Class[0], Void.TYPE).isSupported) {
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.i();
                }
            }
        }

        public void h() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5173, new Class[0], Void.TYPE).isSupported) {
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.h();
                }
            }
        }
    }

    public class b implements LDSLiveReconnectBoxTipView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5174, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickReconnect");
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.f();
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5175, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickHelp");
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.b();
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5176, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickReportIssue");
                if (LiveStateController.this.p2 != null) {
                    LiveStateController.this.p2.c();
                }
            }
        }
    }

    public int getState() {
        return this.a2;
    }

    public void setOnStateClickListener(e onStateClickListener) {
        this.p2 = onStateClickListener;
    }

    public void q(int style) {
        Object[] objArr = {new Integer(style)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5135, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.p4 = style;
            switch (style) {
                case 1:
                    this.c.setBackgroundColor(getResources().getColor(R$color.bg_card_clicked_color));
                    return;
                default:
                    this.c.setBackground((Drawable) null);
                    return;
            }
        }
    }

    public void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5136, new Class[0], Void.TYPE).isSupported) {
            this.c.setVisibility(8);
            this.q.setVisibility(8);
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5137, new Class[0], Void.TYPE).isSupported) {
            this.a2 = 0;
            this.p0.setVisibility(8);
            this.A4.setVisibility(8);
            this.d.setVisibility(8);
            this.y.setVisibility(8);
            this.F4.setVisibility(8);
            this.x.setVisibility(0);
            this.z.setVisibility(8);
            this.c.setVisibility(0);
            if (this.J4) {
                this.C4.setVisibility(8);
            } else {
                this.B4.setVisibility(8);
            }
            switch (this.p4) {
                case 1:
                    if (!s()) {
                        this.D4.setVisibility(8);
                        return;
                    }
                    this.c.setImageResource(0);
                    this.c.setBackground((Drawable) null);
                    this.D4.setVisibility(0);
                    return;
                default:
                    this.c.setBackground((Drawable) null);
                    return;
            }
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5138, new Class[0], Void.TYPE).isSupported) {
            this.D4.setVisibility(8);
        }
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5139, new Class[0], Void.TYPE).isSupported) {
            this.z.setVisibility(8);
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5140, new Class[0], Void.TYPE).isSupported) {
            this.a2 = 0;
            this.p0.setVisibility(8);
            this.F4.setVisibility(8);
            this.A4.setVisibility(8);
            this.d.setVisibility(8);
            this.D4.setVisibility(8);
            this.y.setVisibility(8);
            this.x.setVisibility(0);
            this.z.c(0, false);
            this.z.setVisibility(0);
            if (this.J4) {
                this.C4.setVisibility(8);
            } else {
                this.B4.setVisibility(8);
            }
        }
    }

    public void setLoadingStepPosition(int stepPosition) {
        if (!PatchProxy.proxy(new Object[]{new Integer(stepPosition)}, this, changeQuickRedirect, false, 5141, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.z.c(stepPosition, false);
        }
    }

    public void u(boolean show, String tips) {
        Object[] objArr = {new Byte(show ? (byte) 1 : 0), tips};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5142, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            this.z.d(show, tips);
        }
    }

    public void setLoadingStepsData(ArrayList<LoadingWaveBoxView.LoadingStepBean> steps) {
        if (!PatchProxy.proxy(new Object[]{steps}, this, changeQuickRedirect, false, 5143, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.z.setConnectStepDatas(steps);
        }
    }

    public void y(String picPath, int holderPicId) {
        if (!PatchProxy.proxy(new Object[]{picPath, new Integer(holderPicId)}, this, changeQuickRedirect, false, 5144, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (holderPicId == 0) {
                try {
                    int _holderId = R$color.lds_black_deep;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            this.c.setImageResource(0);
            this.c.setVisibility(0);
            LDSImageView lDSImageView = this.c;
            int i = R$color.lds_black_deep;
            lDSImageView.setBackgroundResource(i);
            this.q.setVisibility(4);
            this.q.b();
            this.K4 = picPath;
            this.c.b(picPath, 1, new c(), i);
        }
    }

    public class c implements LDSImageView.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b(View view, String url, int width, int height) {
            Object[] objArr = {view, url, new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5177, new Class[]{View.class, String.class, cls, cls}, Void.TYPE).isSupported) {
                if (url.equals(LiveStateController.this.K4)) {
                    LiveStateController.this.c.setVisibility(0);
                    ViewGroup.LayoutParams params = LiveStateController.this.q.getLayoutParams();
                    int viewGroupHeight = LiveStateController.this.getHeight();
                    int viewGroupWidth = LiveStateController.this.getWidth();
                    if (viewGroupWidth <= 0 || viewGroupHeight <= 0) {
                        params.width = width;
                        params.height = height;
                    } else {
                        int calculateHeight = (int) (((float) height) * (((float) viewGroupWidth) / ((float) width)));
                        if (calculateHeight < viewGroupHeight) {
                            params.width = viewGroupWidth;
                            params.height = calculateHeight;
                        } else {
                            params.width = (int) (((float) width) * (((float) viewGroupHeight) / ((float) height)));
                            params.height = viewGroupHeight;
                        }
                    }
                    LiveStateController.this.q.requestLayout();
                    LiveStateController.this.q.setVisibility(0);
                }
            }
        }

        public void a(View targetImg, String url) {
        }
    }

    public void z(String picPath, int holderPicId) {
        if (!PatchProxy.proxy(new Object[]{picPath, new Integer(holderPicId)}, this, changeQuickRedirect, false, 5145, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            int _holderId = holderPicId;
            if (_holderId == 0) {
                _holderId = R$color.lds_black_deep;
            }
            try {
                Context context = getContext();
                if (context == null) {
                    return;
                }
                if (!(context instanceof Activity) || !((Activity) context).isDestroyed()) {
                    ((h) ((h) ((h) ((h) com.bumptech.glide.b.t(context).q(picPath).d0(_holderId)).j(_holderId)).m0(true)).f(i.b)).v0(new d()).H0(this.c);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class d implements com.bumptech.glide.request.e<Drawable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5180, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Drawable) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, j<Drawable> jVar, boolean z) {
            Object[] objArr = {glideException, obj, jVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5178, new Class[]{GlideException.class, Object.class, j.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (LiveStateController.this.p4 == 1) {
                LiveStateController.this.c.setScaleType(ImageView.ScaleType.CENTER);
            }
            return false;
        }

        public boolean a(Drawable drawable, Object obj, j<Drawable> jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Object[] objArr = {drawable, obj, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5179, new Class[]{Drawable.class, Object.class, j.class, com.bumptech.glide.load.a.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (LiveStateController.this.p4 == 1) {
                LiveStateController.this.c.setScaleType(ImageView.ScaleType.FIT_CENTER);
                LiveStateController.this.c.setBackground((Drawable) null);
            }
            return false;
        }
    }

    public void setImageScaleType(ImageView.ScaleType scaleType) {
        if (!PatchProxy.proxy(new Object[]{scaleType}, this, changeQuickRedirect, false, 5146, new Class[]{ImageView.ScaleType.class}, Void.TYPE).isSupported) {
            this.c.setScaleType(scaleType);
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5147, new Class[0], Void.TYPE).isSupported) {
            this.a2 = 1;
            this.y.setVisibility(8);
            this.F4.setVisibility(8);
            this.z.setVisibility(8);
            this.x.setVisibility(0);
            this.A4.setVisibility(8);
            this.d.setVisibility(8);
            this.D4.setVisibility(8);
            this.p0.setVisibility(0);
            this.p3.setColor(getContext().getResources().getColor(R$color.second_color));
            this.p3.setCornerRadius(80.0f);
            this.p0.setBackground(this.p3);
            this.p0.setText(PubUtils.getString(getContext(), R$string.video_reconnect));
            if (this.J4) {
                this.C4.setVisibility(0);
            } else {
                this.B4.setVisibility(0);
            }
        }
    }

    public void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_START_REQ, new Class[0], Void.TYPE).isSupported) {
            w(-1);
        }
    }

    public void w(int code) {
        if (!PatchProxy.proxy(new Object[]{new Integer(code)}, this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_START_RESP, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.a2 != 2) {
                this.a2 = 1;
                this.y.setVisibility(8);
                this.F4.setVisibility(8);
                this.z.setVisibility(8);
                this.x.setVisibility(0);
                this.A4.setVisibility(8);
                this.d.setVisibility(8);
                this.D4.setVisibility(8);
                if (this.J4) {
                    this.C4.i(code, LDSLiveReconnectAndTestBoxTipView.b.UNABLE_CONNECT);
                } else {
                    this.B4.b(LDSLiveReconnectBoxTipView.b.UNABLE_CONNECT);
                }
                try {
                    if (!TextUtils.isEmpty(this.L4)) {
                        int minute = this.I4.get(12);
                        int second = this.I4.get(13);
                        String str = this.L4;
                        String errorCode = String.format("8000%02d%02d%s", new Object[]{Integer.valueOf(minute), Integer.valueOf(second), str.substring(str.length() - 6)});
                        if (this.J4) {
                            this.C4.setErrorCodeText(errorCode);
                        } else {
                            this.B4.setErrorCodeText(errorCode);
                        }
                        com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(BaseApplication.b()).o("info").t("LiveReconnectView").e("ReconnectView").u("userId", Constans.userId).u("deviceId", this.L4);
                        u.p("Error code:" + errorCode).a().b();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (this.J4) {
                    this.C4.setVisibility(0);
                } else {
                    this.B4.setVisibility(0);
                }
            }
        }
    }

    public void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5151, new Class[0], Void.TYPE).isSupported) {
            this.a2 = 2;
            this.y.setVisibility(8);
            this.z.setVisibility(8);
            this.x.setVisibility(0);
            this.F4.setVisibility(8);
            this.p0.setVisibility(8);
            this.d.setVisibility(0);
            x(PubUtils.getString(getContext(), R$string.turned_off));
        }
    }

    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_PAUSED_REQ, new Class[0], Void.TYPE).isSupported) {
            this.a2 = 3;
            this.F4.setVisibility(8);
            this.z.setVisibility(8);
            this.p0.setVisibility(8);
            this.A4.setVisibility(8);
            this.d.setVisibility(8);
            this.D4.setVisibility(8);
            this.x.setVisibility(0);
            this.y.setVisibility(0);
            if (this.J4) {
                this.C4.setVisibility(8);
            } else {
                this.B4.setVisibility(8);
            }
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_PAUSED_RESP, new Class[0], Void.TYPE).isSupported) {
            this.a2 = 4;
            this.z.setVisibility(8);
            this.p0.setVisibility(8);
            this.A4.setVisibility(8);
            this.d.setVisibility(8);
            this.D4.setVisibility(8);
            this.x.setVisibility(0);
            this.y.setVisibility(8);
            if (this.J4) {
                this.C4.setVisibility(8);
            } else {
                this.B4.setVisibility(8);
            }
            this.F4.setVisibility(0);
        }
    }

    private void x(String statusText) {
        if (!PatchProxy.proxy(new Object[]{statusText}, this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_STOP_REQ, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.A4.setVisibility(0);
            this.A4.setText(statusText);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        e eVar;
        e eVar2;
        e eVar3;
        e eVar4;
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_STOP_RESP, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.state_btn && (eVar4 = this.p2) != null) {
            int i = this.a2;
            if (i == 1) {
                eVar4.f();
            } else if (i == 2) {
                eVar4.d();
            }
        } else if (viewId == R$id.img_wakeup && (eVar3 = this.p2) != null) {
            eVar3.j();
        } else if (viewId == R$id.iv_turnonoff && (eVar2 = this.p2) != null) {
            eVar2.d();
        } else if (viewId == R$id.iv_back && (eVar = this.p2) != null) {
            eVar.a();
        } else if (viewId == R$id.img_unavail) {
            k.a();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    public void setAiMarkList(ArrayList<AiMarkPositionBean> marksData) {
        if (!PatchProxy.proxy(new Object[]{marksData}, this, changeQuickRedirect, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_HEARTHEAT_REQ, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.q.a(marksData);
        }
    }

    public void A(int color) {
        Object[] objArr = {new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.CMD_AVIO_CTRL_HEARTHEAT_RESP, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.z4.setBackgroundColor(color);
        }
    }

    public void setAbnormalVersionBean(AbnormalVersionBean bean) {
        if (!PatchProxy.proxy(new Object[]{bean}, this, changeQuickRedirect, false, 5158, new Class[]{AbnormalVersionBean.class}, Void.TYPE).isSupported) {
            this.C4.setAbnormalVersionBean(bean);
        }
    }

    public void setVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5159, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setVisibility(visibility);
        }
    }

    public View getTitleLayout() {
        return this.D4;
    }

    public void setTitleText(String txt) {
        if (!PatchProxy.proxy(new Object[]{txt}, this, changeQuickRedirect, false, 5160, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.E4.setText(txt);
        }
    }

    public void l(boolean isLandscape) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isLandscape ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5161, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.d.getLayoutParams();
            RelativeLayout.LayoutParams txt_layoutParams = (RelativeLayout.LayoutParams) this.A4.getLayoutParams();
            int _24dp = com.leedarson.base.utils.d.b(getContext(), 24.0f);
            int _12dp = com.leedarson.base.utils.d.b(getContext(), 12.0f);
            int _8dp = com.leedarson.base.utils.d.b(getContext(), 8.0f);
            if (isLandscape) {
                layoutParams.setMargins(_24dp, _24dp, _24dp, _24dp);
                txt_layoutParams.setMargins(0, 0, _24dp, _8dp);
                return;
            }
            layoutParams.setMargins(_12dp, _12dp, _12dp, _12dp);
            txt_layoutParams.setMargins(0, 0, _12dp, _8dp);
        }
    }

    private boolean s() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5162, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setDeviceId(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 5163, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.L4 = deviceId;
            LDSLiveReconnectAndTestBoxTipView lDSLiveReconnectAndTestBoxTipView = this.C4;
            if (lDSLiveReconnectAndTestBoxTipView != null) {
                lDSLiveReconnectAndTestBoxTipView.setDeviceid(deviceId);
            }
        }
    }

    public void setIsWebrtc(Boolean isWebrtc) {
        if (!PatchProxy.proxy(new Object[]{isWebrtc}, this, changeQuickRedirect, false, 5164, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.J4 = isWebrtc.booleanValue();
        }
    }

    public boolean r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5165, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LDSLiveReconnectAndTestBoxTipView lDSLiveReconnectAndTestBoxTipView = this.C4;
        return lDSLiveReconnectAndTestBoxTipView != null && lDSLiveReconnectAndTestBoxTipView.g();
    }

    public void t() {
        LDSLiveReconnectAndTestBoxTipView lDSLiveReconnectAndTestBoxTipView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5166, new Class[0], Void.TYPE).isSupported && (lDSLiveReconnectAndTestBoxTipView = this.C4) != null) {
            lDSLiveReconnectAndTestBoxTipView.h();
        }
    }
}

package com.leedarson.newui.pages.sdcard_edit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.g;
import com.leedarson.bean.SDRecord;
import com.leedarson.newui.pages.adapters.SDCardEventEditAdapter;
import com.leedarson.newui.pages.layoutmanager.WrapSaftyContentGridLayoutManager;
import com.leedarson.newui.pages.repos.SDCardEditRequestParamBean;
import com.leedarson.newui.pages.repos.h;
import com.leedarson.newui.pages.repos.i;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.reactivex.e;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SDCardVideoManageAct.kt */
public final class SDCardVideoManageAct extends BaseFragmentActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    @NotNull
    public ArrayList<SDRecord> A4 = new ArrayList<>();
    private final float B4 = 0.5f;
    private int C4 = Color.parseColor("#1F2429");
    @Nullable
    private GridLayoutManager D4;
    @Nullable
    private SDCardEventEditAdapter E4;
    @NotNull
    private final String F4 = "del_tips";
    @NotNull
    private final String G4 = "deleting";
    private int H4;
    @Nullable
    private Dialog I4;
    @Nullable
    private LDSTextView J4;
    @Nullable
    private LDSTextView K4;
    @Nullable
    private LDSTextView L4;
    @NotNull
    private final h M4 = new h();
    @Nullable
    private g N4;
    /* access modifiers changed from: private */
    @NotNull
    public final i a2 = new i();
    @NotNull
    private final kotlin.g p2 = kotlin.i.b(a.INSTANCE);
    @NotNull
    private ArrayList<SDRecord> p3 = new ArrayList<>();
    @Nullable
    private SDCardEditRequestParamBean p4;
    private boolean z4;

    public static final /* synthetic */ void e0(SDCardVideoManageAct $this, String msg) {
        Class[] clsArr = {SDCardVideoManageAct.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, msg}, (Object) null, changeQuickRedirect, true, 4397, clsArr, Void.TYPE).isSupported) {
            $this.b0(msg);
        }
    }

    public static final /* synthetic */ void f0(SDCardVideoManageAct $this, String msg) {
        Class[] clsArr = {SDCardVideoManageAct.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, msg}, (Object) null, changeQuickRedirect, true, 4396, clsArr, Void.TYPE).isSupported) {
            $this.c0(msg);
        }
    }

    public static final /* synthetic */ void k0(SDCardVideoManageAct $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 4395, new Class[]{SDCardVideoManageAct.class}, Void.TYPE).isSupported) {
            $this.A1();
        }
    }

    /* compiled from: SDCardVideoManageAct.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<io.reactivex.disposables.a> {
        public static final a INSTANCE = new a();
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
            super(0);
        }

        @NotNull
        public final io.reactivex.disposables.a invoke() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4398, new Class[0], io.reactivex.disposables.a.class);
            if (proxy.isSupported) {
                return (io.reactivex.disposables.a) proxy.result;
            }
            return new io.reactivex.disposables.a();
        }
    }

    private final io.reactivex.disposables.a n0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4362, new Class[0], io.reactivex.disposables.a.class);
        return proxy.isSupported ? (io.reactivex.disposables.a) proxy.result : (io.reactivex.disposables.a) this.p2.getValue();
    }

    @Nullable
    public final SDCardEventEditAdapter m0() {
        return this.E4;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 4363, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            if (savedInstanceState != null) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").o(268468224).D(this);
                finish();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4364, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4365, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            n0().dispose();
        }
    }

    public void init() {
    }

    public int S() {
        return R$layout.sd_card_video_manage_act;
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4366, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.a n0 = n0();
            i iVar = this.a2;
            Intent intent = getIntent();
            k.d(intent, "intent");
            n0.b(iVar.a(intent).x(new t(this)).o(new o(this)).c(com.leedarson.base.http.observer.l.c()).I(new a(this), new n(this)));
        }
    }

    /* access modifiers changed from: private */
    public static final ArrayList q0(SDCardVideoManageAct this$0, SDCardEditRequestParamBean it) {
        Iterable<Number> $this$forEach$iv;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect2, true, 4376, new Class[]{SDCardVideoManageAct.class, SDCardEditRequestParamBean.class}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        k.e(this$0, "this$0");
        k.e(it, "it");
        this$0.p4 = it;
        this$0.M4.y(it);
        SDCardEditRequestParamBean sDCardEditRequestParamBean = this$0.p4;
        if (!(sDCardEditRequestParamBean == null || ($this$forEach$iv = sDCardEditRequestParamBean.getSdVideoList()) == null)) {
            for (Number longValue : $this$forEach$iv) {
                long _timeSpan = longValue.longValue();
                ArrayList<SDRecord> arrayList = this$0.p3;
                SDRecord $this$initBundleData_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0 = new SDRecord();
                $this$initBundleData_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0.setTimestamp(_timeSpan);
                x xVar = x.a;
                arrayList.add($this$initBundleData_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0);
            }
        }
        return this$0.p3;
    }

    /* access modifiers changed from: private */
    public static final org.reactivestreams.a s0(SDCardVideoManageAct this$0, ArrayList it) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect2, true, 4377, new Class[]{SDCardVideoManageAct.class, ArrayList.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        k.e(this$0, "this$0");
        k.e(it, "it");
        return this$0.a2.e(it);
    }

    /* access modifiers changed from: private */
    public static final void t0(SDCardVideoManageAct this$0, ArrayList it) {
        Class[] clsArr = {SDCardVideoManageAct.class, ArrayList.class};
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4378, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            k.d(it, "it");
            this$0.A4 = it;
            this$0.z0();
        }
    }

    /* access modifiers changed from: private */
    public static final void u0(SDCardVideoManageAct this$0, Throwable th) {
        Class[] clsArr = {SDCardVideoManageAct.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{this$0, th}, (Object) null, changeQuickRedirect, true, 4379, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            this$0.finish();
        }
    }

    private final void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4367, new Class[0], Void.TYPE).isSupported) {
            int i = R$id.iv_back;
            ((ImageView) findViewById(i)).setOnClickListener(new e(this));
            int i2 = R$id.tv_cancel;
            ((LDSTextView) findViewById(i2)).setOnClickListener(new b(this));
            int i3 = R$id.iv_delete;
            ((ImageView) findViewById(i3)).setAlpha(this.B4);
            ((ImageView) findViewById(i3)).setEnabled(false);
            SDCardEditRequestParamBean _that = this.p4;
            if (_that != null) {
                ((LDSTextView) findViewById(R$id.tv_title)).setText(this.a2.c(_that.getStartTime()));
                setTitle("IPC Playback Edit List");
                ((LDSTextView) findViewById(i2)).setText(PubUtils.getString(this, R$string.cancel));
                ((ImageView) findViewById(R$id.iv_edit)).setVisibility(8);
                ((ImageView) findViewById(i)).setVisibility(8);
                ((LDSTextView) findViewById(i2)).setVisibility(0);
                findViewById(R$id.del_line).setVisibility(0);
                ((LinearLayout) findViewById(R$id.del_layout)).setVisibility(0);
            }
            Q0();
            d0 d0Var = d0.a;
            Locale locale = Locale.US;
            String string = PubUtils.getString(this, R$string.selected);
            k.d(string, "getString(this, R.string.selected)");
            String format = String.format(locale, string, Arrays.copyOf(new Object[]{0}, 1));
            k.d(format, "format(locale, format, *args)");
            ((LDSTextView) findViewById(R$id.tv_selected_num)).setText(format);
            this.f.b(this.a2.d().I(new c0(this), z.c));
            ((ImageView) findViewById(i3)).setOnClickListener(new f(this));
            this.f.b(this.M4.n().I(new q(this), s.c));
            this.f.b(this.M4.o().I(new c(this), b0.c));
            this.f.b(this.M4.u().I(new d(this), p.c));
            this.f.b(this.M4.n().I(new i(this), r.c));
            this.f.b(this.M4.q().I(new a0(this), new d0(this)));
            w0();
            v0();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void N0(SDCardVideoManageAct this$0, View view) {
        Class[] clsArr = {SDCardVideoManageAct.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4380, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.l0();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void O0(SDCardVideoManageAct this$0, View view) {
        Class[] clsArr = {SDCardVideoManageAct.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4381, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.l0();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    public static final void P0(SDCardVideoManageAct this$0, Integer it) {
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4382, new Class[]{SDCardVideoManageAct.class, Integer.class}, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            d0 d0Var = d0.a;
            Locale locale = Locale.US;
            String string = PubUtils.getString(this$0, R$string.selected);
            k.d(string, "getString(this, R.string.selected)");
            String format = String.format(locale, string, Arrays.copyOf(new Object[]{it}, 1));
            k.d(format, "format(locale, format, *args)");
            ((LDSTextView) this$0.findViewById(R$id.tv_selected_num)).setText(format);
            int i = R$id.iv_delete;
            ((ImageView) this$0.findViewById(i)).setAlpha((it != null && it.intValue() == 0) ? this$0.B4 : 1.0f);
            ImageView imageView = (ImageView) this$0.findViewById(i);
            if (it == null || it.intValue() != 0) {
                z = true;
            }
            imageView.setEnabled(z);
            k.d(it, "it");
            this$0.H4 = it.intValue();
        }
    }

    /* access modifiers changed from: private */
    public static final void A0(Throwable it) {
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void B0(SDCardVideoManageAct this$0, View view) {
        Class[] clsArr = {SDCardVideoManageAct.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4383, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.w1(this$0.F4);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    public static final void C0(SDCardVideoManageAct this$0, Boolean bool) {
        Class[] clsArr = {SDCardVideoManageAct.class, Boolean.class};
        if (!PatchProxy.proxy(new Object[]{this$0, bool}, (Object) null, changeQuickRedirect, true, 4384, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            this$0.w1(this$0.G4);
        }
    }

    /* access modifiers changed from: private */
    public static final void D0(Throwable it) {
    }

    /* access modifiers changed from: private */
    public static final void E0(SDCardVideoManageAct this$0, String it) {
        Class[] clsArr = {SDCardVideoManageAct.class, String.class};
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4385, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            this$0.a0(it);
        }
    }

    /* access modifiers changed from: private */
    public static final void F0(Throwable it) {
    }

    /* access modifiers changed from: private */
    public static final void G0(SDCardVideoManageAct this$0, String it) {
        Class[] clsArr = {SDCardVideoManageAct.class, String.class};
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4386, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            LDSTextView lDSTextView = this$0.J4;
            if (lDSTextView != null) {
                lDSTextView.setText(it);
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void H0(Throwable it) {
    }

    /* access modifiers changed from: private */
    public static final void I0(SDCardVideoManageAct this$0, Boolean it) {
        Class[] clsArr = {SDCardVideoManageAct.class, Boolean.class};
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4387, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            Dialog dialog = this$0.I4;
            if (dialog != null) {
                k.d(it, "it");
                if (it.booleanValue()) {
                    dialog.show();
                } else {
                    dialog.dismiss();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void J0(Throwable it) {
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00e3 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0130 A[EDGE_INSN: B:74:0x0130->B:50:0x0130 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void K0(com.leedarson.newui.pages.sdcard_edit.SDCardVideoManageAct r20, java.util.ArrayList r21) {
        /*
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r20
            r9 = 1
            r1[r9] = r21
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.newui.pages.sdcard_edit.SDCardVideoManageAct> r0 = com.leedarson.newui.pages.sdcard_edit.SDCardVideoManageAct.class
            r6[r8] = r0
            java.lang.Class<java.util.ArrayList> r0 = java.util.ArrayList.class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r5 = 4388(0x1124, float:6.149E-42)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r20
            r1 = r21
            java.lang.String r2 = "this$0"
            kotlin.jvm.internal.k.e(r0, r2)
            java.lang.String r2 = "it"
            kotlin.jvm.internal.k.d(r1, r2)
            r2 = r1
            r3 = 0
            java.util.LinkedHashMap r4 = new java.util.LinkedHashMap
            r4.<init>()
            r5 = r2
            r6 = 0
            java.util.Iterator r7 = r5.iterator()
        L_0x003f:
            boolean r10 = r7.hasNext()
            if (r10 == 0) goto L_0x0075
            java.lang.Object r10 = r7.next()
            r11 = r10
            com.leedarson.bean.SDRecord r11 = (com.leedarson.bean.SDRecord) r11
            r12 = 0
            long r13 = r11.getTimestamp()
            java.lang.String r15 = "HH"
            java.lang.String r11 = com.leedarson.utils.e.j(r13, r15)
            r12 = r4
            r13 = 0
            java.lang.Object r14 = r12.get(r11)
            if (r14 != 0) goto L_0x006c
            r15 = 0
            java.util.ArrayList r16 = new java.util.ArrayList
            r16.<init>()
            r15 = r16
            r12.put(r11, r15)
            goto L_0x006d
        L_0x006c:
            r15 = r14
        L_0x006d:
            r12 = r15
            java.util.List r12 = (java.util.List) r12
            r12.add(r10)
            goto L_0x003f
        L_0x0075:
            r2 = r4
            r3 = 0
            java.util.Set r4 = r2.entrySet()
            java.util.Iterator r4 = r4.iterator()
        L_0x0081:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0143
            java.lang.Object r5 = r4.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            r6 = r5
            r7 = 0
            java.lang.Object r10 = r6.getValue()
            java.util.List r10 = (java.util.List) r10
            int r10 = r10.size()
            java.util.ArrayList<com.leedarson.bean.SDRecord> r11 = r0.A4
            r12 = 0
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r14 = r11
            r15 = 0
            java.util.Iterator r16 = r14.iterator()
        L_0x00a7:
            boolean r17 = r16.hasNext()
            if (r17 == 0) goto L_0x00ea
            java.lang.Object r8 = r16.next()
            r9 = r8
            com.leedarson.bean.SDRecord r9 = (com.leedarson.bean.SDRecord) r9
            r18 = 0
            r20 = r2
            int r2 = r9.itemType
            if (r2 != 0) goto L_0x00db
            int r2 = r9.groupIndex
            java.lang.Object r19 = r6.getValue()
            java.util.List r19 = (java.util.List) r19
            java.lang.Object r19 = kotlin.collections.y.U(r19)
            r21 = r3
            r3 = r19
            com.leedarson.bean.SDRecord r3 = (com.leedarson.bean.SDRecord) r3
            if (r3 != 0) goto L_0x00d2
        L_0x00d0:
            r2 = 0
            goto L_0x00d7
        L_0x00d2:
            int r3 = r3.groupIndex
            if (r2 != r3) goto L_0x00d0
            r2 = 1
        L_0x00d7:
            if (r2 == 0) goto L_0x00dd
            r2 = 1
            goto L_0x00de
        L_0x00db:
            r21 = r3
        L_0x00dd:
            r2 = 0
        L_0x00de:
            if (r2 == 0) goto L_0x00e3
            r13.add(r8)
        L_0x00e3:
            r2 = r20
            r3 = r21
            r8 = 0
            r9 = 1
            goto L_0x00a7
        L_0x00ea:
            r20 = r2
            r21 = r3
            int r2 = r13.size()
            if (r10 != r2) goto L_0x013b
            java.util.ArrayList<com.leedarson.bean.SDRecord> r3 = r0.A4
            r8 = 0
            java.util.Iterator r9 = r3.iterator()
        L_0x00fd:
            boolean r11 = r9.hasNext()
            if (r11 == 0) goto L_0x012f
            java.lang.Object r11 = r9.next()
            r12 = r11
            com.leedarson.bean.SDRecord r12 = (com.leedarson.bean.SDRecord) r12
            r13 = 0
            int r14 = r12.itemType
            r15 = 1
            if (r14 != r15) goto L_0x012b
            int r14 = r12.groupIndex
            java.lang.Object r15 = r6.getValue()
            java.util.List r15 = (java.util.List) r15
            java.lang.Object r15 = kotlin.collections.y.U(r15)
            com.leedarson.bean.SDRecord r15 = (com.leedarson.bean.SDRecord) r15
            if (r15 != 0) goto L_0x0122
        L_0x0120:
            r15 = 0
            goto L_0x0127
        L_0x0122:
            int r15 = r15.groupIndex
            if (r14 != r15) goto L_0x0120
            r15 = 1
        L_0x0127:
            if (r15 == 0) goto L_0x012b
            r15 = 1
            goto L_0x012c
        L_0x012b:
            r15 = 0
        L_0x012c:
            if (r15 == 0) goto L_0x00fd
            goto L_0x0130
        L_0x012f:
            r11 = 0
        L_0x0130:
            com.leedarson.bean.SDRecord r11 = (com.leedarson.bean.SDRecord) r11
            if (r11 != 0) goto L_0x0136
            goto L_0x013b
        L_0x0136:
            r3 = r11
            r8 = 0
            r1.add(r3)
        L_0x013b:
            r2 = r20
            r3 = r21
            r8 = 0
            r9 = 1
            goto L_0x0081
        L_0x0143:
            r20 = r2
            r21 = r3
            r2 = 1
            r0.z4 = r2
            java.util.ArrayList<com.leedarson.bean.SDRecord> r2 = r0.A4
            r2.removeAll(r1)
            java.util.ArrayList<com.leedarson.bean.SDRecord> r2 = r0.A4
            int r2 = r2.size()
            if (r2 != 0) goto L_0x0160
            java.lang.String r2 = "onSDRecordDeletedListUpdateObser 444444"
            r0.c0(r2)
            r0.l0()
            return
        L_0x0160:
            com.leedarson.newui.pages.adapters.SDCardEventEditAdapter r2 = r0.m0()
            if (r2 != 0) goto L_0x0167
            goto L_0x016a
        L_0x0167:
            r2.notifyDataSetChanged()
        L_0x016a:
            com.leedarson.newui.pages.repos.i r2 = r0.a2
            java.util.ArrayList<com.leedarson.bean.SDRecord> r3 = r0.A4
            r2.k(r3)
            r0.A1()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.pages.sdcard_edit.SDCardVideoManageAct.K0(com.leedarson.newui.pages.sdcard_edit.SDCardVideoManageAct, java.util.ArrayList):void");
    }

    /* access modifiers changed from: private */
    public static final void L0(SDCardVideoManageAct this$0, Throwable it) {
        Class[] clsArr = {SDCardVideoManageAct.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4389, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            this$0.b0(k.l("onSDRecordDeletedListUpdateObser 数据变化、重新更新页面数据时发生了异常 ", it));
        }
    }

    private final void Q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4368, new Class[0], Void.TYPE).isSupported) {
            WrapSaftyContentGridLayoutManager wrapSaftyContentGridLayoutManager = new WrapSaftyContentGridLayoutManager(this, 3);
            this.D4 = wrapSaftyContentGridLayoutManager;
            wrapSaftyContentGridLayoutManager.setSpanSizeLookup(new SDCardVideoManageAct$initViewOfRecyclerView$1(this));
            int i = R$id.rv_events;
            ((RecyclerView) findViewById(i)).setLayoutManager(this.D4);
            ArrayList<SDRecord> arrayList = this.A4;
            SDCardEditRequestParamBean sDCardEditRequestParamBean = this.p4;
            String str = null;
            String valueOf = String.valueOf(sDCardEditRequestParamBean == null ? null : sDCardEditRequestParamBean.getEventStr());
            SDCardEditRequestParamBean sDCardEditRequestParamBean2 = this.p4;
            boolean z = true;
            if (sDCardEditRequestParamBean2 == null || !sDCardEditRequestParamBean2.isWebRTC()) {
                z = false;
            }
            SDCardEditRequestParamBean sDCardEditRequestParamBean3 = this.p4;
            if (z) {
                if (sDCardEditRequestParamBean3 != null) {
                    str = sDCardEditRequestParamBean3.getDeviceId();
                }
            } else if (sDCardEditRequestParamBean3 != null) {
                str = sDCardEditRequestParamBean3.getP2pId();
            }
            SDCardEventEditAdapter $this$initViewOfRecyclerView_u24lambda_u2d28 = new SDCardEventEditAdapter(arrayList, this, valueOf, String.valueOf(str));
            $this$initViewOfRecyclerView_u24lambda_u2d28.b(new b(this));
            x xVar = x.a;
            this.E4 = $this$initViewOfRecyclerView_u24lambda_u2d28;
            ((RecyclerView) findViewById(i)).setAdapter(this.E4);
            SDCardEventEditAdapter sDCardEventEditAdapter = this.E4;
            if (sDCardEventEditAdapter != null) {
                sDCardEventEditAdapter.notifyDataSetChanged();
            }
            this.f.b(this.M4.r().I(new m(this), h.c));
            ((RecyclerView) findViewById(i)).addOnScrollListener(new SDCardVideoManageAct$initViewOfRecyclerView$5(this));
            h hVar = this.M4;
            ArrayList<SDRecord> arrayList2 = this.A4;
            List<SDRecord> subList = arrayList2.subList(0, Math.min(arrayList2.size(), 18));
            k.d(subList, "_recordsFilterAndGroup.subList(\n                0,\n                Math.min(_recordsFilterAndGroup.size, 18)\n            )");
            hVar.t(subList);
        }
    }

    /* compiled from: SDCardVideoManageAct.kt */
    public static final class b implements SDCardEventEditAdapter.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ SDCardVideoManageAct a;

        b(SDCardVideoManageAct $receiver) {
            this.a = $receiver;
        }

        public void b(int position, @NotNull SDRecord itemData) {
            Object[] objArr = {new Integer(position), itemData};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4401, new Class[]{Integer.TYPE, SDRecord.class}, Void.TYPE).isSupported) {
                k.e(itemData, "itemData");
                SDCardVideoManageAct sDCardVideoManageAct = this.a;
                io.reactivex.disposables.a aVar = sDCardVideoManageAct.f;
                e<R> c = sDCardVideoManageAct.a2.n(itemData, this.a.A4, position).c(com.leedarson.base.http.observer.l.c());
                SDCardVideoManageAct sDCardVideoManageAct2 = this.a;
                aVar.b(c.I(new v(sDCardVideoManageAct2), new y(sDCardVideoManageAct2)));
            }
        }

        /* access modifiers changed from: private */
        public static final void g(SDCardVideoManageAct this$0, n it) {
            Class[] clsArr = {SDCardVideoManageAct.class, n.class};
            if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4403, clsArr, Void.TYPE).isSupported) {
                k.e(this$0, "this$0");
                SDCardVideoManageAct.f0(this$0, "onEventItemClickChangeData 数据处理成功");
                SDCardEventEditAdapter m0 = this$0.m0();
                if (m0 != null) {
                    m0.notifyDataSetChanged();
                }
                this$0.a2.k((ArrayList) it.getSecond());
            }
        }

        /* access modifiers changed from: private */
        public static final void h(SDCardVideoManageAct this$0, Throwable it) {
            Class[] clsArr = {SDCardVideoManageAct.class, Throwable.class};
            if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4404, clsArr, Void.TYPE).isSupported) {
                k.e(this$0, "this$0");
                this$0.a0(PubUtils.getString(BaseApplication.b(), R$string.lds_sd_card_delete_limit_tips));
                k.d(it, "it");
                SDCardVideoManageAct.e0(this$0, k.l("onEventItemClickChangeData 数据处理失败  ", it));
            }
        }

        public void a(int position, @NotNull SDRecord itemData) {
            Object[] objArr = {new Integer(position), itemData};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4402, new Class[]{Integer.TYPE, SDRecord.class}, Void.TYPE).isSupported) {
                k.e(itemData, "itemData");
                SDCardVideoManageAct sDCardVideoManageAct = this.a;
                io.reactivex.disposables.a aVar = sDCardVideoManageAct.f;
                e<R> c = sDCardVideoManageAct.a2.l(itemData, this.a.A4, position).c(com.leedarson.base.http.observer.l.c());
                SDCardVideoManageAct sDCardVideoManageAct2 = this.a;
                aVar.b(c.I(new x(sDCardVideoManageAct2), new w(sDCardVideoManageAct2)));
            }
        }

        /* access modifiers changed from: private */
        public static final void i(SDCardVideoManageAct this$0, n it) {
            Class[] clsArr = {SDCardVideoManageAct.class, n.class};
            if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4405, clsArr, Void.TYPE).isSupported) {
                k.e(this$0, "this$0");
                SDCardVideoManageAct.f0(this$0, "onGroupTitleItemClick数据处理成功");
                SDCardEventEditAdapter m0 = this$0.m0();
                if (m0 != null) {
                    m0.notifyDataSetChanged();
                }
                this$0.a2.k((ArrayList) it.getSecond());
            }
        }

        /* access modifiers changed from: private */
        public static final void j(SDCardVideoManageAct this$0, Throwable it) {
            Class[] clsArr = {SDCardVideoManageAct.class, Throwable.class};
            if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4406, clsArr, Void.TYPE).isSupported) {
                k.e(this$0, "this$0");
                this$0.a0(PubUtils.getString(BaseApplication.b(), R$string.lds_sd_card_delete_limit_tips));
                k.d(it, "it");
                SDCardVideoManageAct.e0(this$0, k.l("onGroupTitleItemClick数据处理失败  ", it));
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void S0(SDCardVideoManageAct this$0, Long it) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4390, new Class[]{SDCardVideoManageAct.class, Long.class}, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            int size = this$0.A4.size();
            if (size > 0) {
                do {
                    int i2 = i;
                    i++;
                    long timestamp = this$0.A4.get(i2).getTimestamp() / ((long) 1000);
                    if (it != null && timestamp == it.longValue()) {
                        this$0.c0("###获取图片成功 (锁定到目标) .... time=" + this$0.A4.get(i2).getTimestamp() + "  , time=" + com.leedarson.utils.e.j(this$0.A4.get(i2).getTimestamp(), "HH:mm:ss"));
                        SDCardEventEditAdapter m0 = this$0.m0();
                        if (m0 != null) {
                            m0.notifyItemChanged(i2);
                            return;
                        }
                        return;
                    }
                } while (i < size);
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void U0(Throwable it) {
    }

    private final void A1() {
        int compactLast = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4369, new Class[0], Void.TYPE).isSupported) {
            int i = R$id.rv_events;
            RecyclerView.LayoutManager layoutManager = ((RecyclerView) findViewById(i)).getLayoutManager();
            if (layoutManager != null) {
                int first = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                RecyclerView.LayoutManager layoutManager2 = ((RecyclerView) findViewById(i)).getLayoutManager();
                if (layoutManager2 != null) {
                    int last = ((GridLayoutManager) layoutManager2).findLastVisibleItemPosition();
                    int compactFirst = first < 0 ? 0 : first;
                    if (last >= 0) {
                        compactLast = last;
                    }
                    if (this.A4.size() != 0) {
                        try {
                            h hVar = this.M4;
                            List<SDRecord> subList = this.A4.subList(compactFirst, compactLast);
                            k.d(subList, "_recordsFilterAndGroup.subList(compactFirst, compactLast)");
                            hVar.t(subList);
                        } catch (Exception e) {
                            b0(k.l("onSDRecordDeletedListUpdateObser updateListWhenModify 更新数据时发生了异常...", e));
                        }
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
            }
        }
    }

    private final void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4370, new Class[0], Void.TYPE).isSupported) {
            this.N4 = new g(this);
            this.f.b(this.M4.p().c(com.leedarson.base.http.observer.l.c()).I(new g(this), k.c));
        }
    }

    /* access modifiers changed from: private */
    public static final void x0(SDCardVideoManageAct this$0, Boolean it) {
        g gVar;
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4391, new Class[]{SDCardVideoManageAct.class, Boolean.class}, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            k.d(it, "it");
            if (it.booleanValue()) {
                g gVar2 = this$0.N4;
                if (gVar2 != null) {
                    g $this$initLoadingView_u24lambda_u2d33_u24lambda_u2d31 = gVar2;
                    $this$initLoadingView_u24lambda_u2d33_u24lambda_u2d31.setCancelable(true);
                    $this$initLoadingView_u24lambda_u2d33_u24lambda_u2d31.setCanceledOnTouchOutside(true);
                    gVar2.show();
                    return;
                }
                return;
            }
            g gVar3 = this$0.N4;
            if (gVar3 != null && gVar3.isShowing() && (gVar = this$0.N4) != null) {
                gVar.dismiss();
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void y0(Throwable it) {
    }

    private final void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4371, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(this, R$style.Theme_dialog);
            Dialog $this$initDeleteDialogTip_u24lambda_u2d35 = dialog;
            $this$initDeleteDialogTip_u24lambda_u2d35.setContentView(R$layout.del_dialog_layout);
            this.J4 = (LDSTextView) $this$initDeleteDialogTip_u24lambda_u2d35.findViewById(R$id.tip_content_tv);
            this.K4 = (LDSTextView) $this$initDeleteDialogTip_u24lambda_u2d35.findViewById(R$id.left_btn_tv);
            this.L4 = (LDSTextView) $this$initDeleteDialogTip_u24lambda_u2d35.findViewById(R$id.right_btn_tv);
            x xVar = x.a;
            this.I4 = dialog;
        }
    }

    private final void w1(String flag) {
        LDSTextView lDSTextView;
        String str;
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4372, new Class[]{String.class}, Void.TYPE).isSupported) {
            LDSTextView lDSTextView2 = this.K4;
            if (lDSTextView2 != null) {
                lDSTextView2.setOnClickListener(new l(this));
            }
            View view = null;
            if (k.a(flag, this.F4)) {
                Dialog dialog = this.I4;
                if (dialog != null) {
                    dialog.setCanceledOnTouchOutside(false);
                }
                LDSTextView lDSTextView3 = this.K4;
                if (lDSTextView3 != null) {
                    lDSTextView3.setText(PubUtils.getString(this, R$string.cancel));
                }
                LDSTextView lDSTextView4 = this.L4;
                if (lDSTextView4 != null) {
                    lDSTextView4.setText(PubUtils.getString(this, R$string.confirm));
                }
                LDSTextView lDSTextView5 = this.K4;
                if (lDSTextView5 != null) {
                    lDSTextView5.setVisibility(0);
                }
                Dialog dialog2 = this.I4;
                if (dialog2 != null) {
                    view = dialog2.findViewById(R$id.view_line);
                }
                if (view != null) {
                    view.setVisibility(0);
                }
                LDSTextView lDSTextView6 = this.L4;
                if (lDSTextView6 != null) {
                    lDSTextView6.setOnClickListener(new j(this));
                }
                if (!(this.I4 == null || (lDSTextView = this.J4) == null)) {
                    if (this.H4 > 15) {
                        str = PubUtils.getString(this, R$string.delete_long_event_tip);
                    } else {
                        str = PubUtils.getString(this, R$string.delete_event_tip);
                    }
                    lDSTextView.setText(str);
                }
            } else if (k.a(flag, this.G4)) {
                Dialog dialog3 = this.I4;
                if (dialog3 != null) {
                    dialog3.setCanceledOnTouchOutside(false);
                }
                LDSTextView lDSTextView7 = this.K4;
                if (lDSTextView7 != null) {
                    lDSTextView7.setVisibility(8);
                }
                Dialog dialog4 = this.I4;
                if (dialog4 != null) {
                    view = dialog4.findViewById(R$id.view_line);
                }
                if (view != null) {
                    view.setVisibility(8);
                }
                LDSTextView lDSTextView8 = this.J4;
                if (lDSTextView8 != null) {
                    d0 d0Var = d0.a;
                    Locale locale = Locale.US;
                    String string = PubUtils.getString(getBaseContext(), R$string.lds_deleting);
                    k.d(string, "getString(baseContext, R.string.lds_deleting)");
                    String format = String.format(locale, string, Arrays.copyOf(new Object[]{"0%"}, 1));
                    k.d(format, "format(locale, format, *args)");
                    lDSTextView8.setText(format);
                }
                LDSTextView lDSTextView9 = this.L4;
                if (lDSTextView9 != null) {
                    lDSTextView9.setText(PubUtils.getString(this, R$string.cancel));
                }
                LDSTextView lDSTextView10 = this.L4;
                if (lDSTextView10 != null) {
                    lDSTextView10.setOnClickListener(new u(this));
                }
            }
            Dialog dialog5 = this.I4;
            if (dialog5 != null) {
                dialog5.show();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void x1(SDCardVideoManageAct this$0, View view) {
        Class[] clsArr = {SDCardVideoManageAct.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4392, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        Dialog dialog = this$0.I4;
        if (dialog != null) {
            dialog.dismiss();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void y1(SDCardVideoManageAct this$0, View view) {
        Class[] clsArr = {SDCardVideoManageAct.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4393, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        Dialog dialog = this$0.I4;
        if (dialog != null) {
            dialog.dismiss();
        }
        this$0.M4.k(this$0.A4);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void z1(SDCardVideoManageAct this$0, View view) {
        Class[] clsArr = {SDCardVideoManageAct.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4394, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.M4.z();
        Dialog dialog = this$0.I4;
        if (dialog != null) {
            dialog.dismiss();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private final void c0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4373, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("sdEdit").h("msg  =%s", msg);
        }
    }

    private final void b0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4374, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("sdEdit").c("msg  =%s", msg);
        }
    }

    private final void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4375, new Class[0], Void.TYPE).isSupported) {
            if (this.z4) {
                setResult(-1);
            }
            finish();
        }
    }
}

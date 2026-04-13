package com.leedarson.newui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.EditSDHour2Adapter;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.SDRecord;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;

public class EditSDMinuteActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView A4;
    private LDSTextView B4;
    /* access modifiers changed from: private */
    public boolean C4 = false;
    private RecyclerView D4;
    /* access modifiers changed from: private */
    public Dialog E4 = null;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g F4;
    /* access modifiers changed from: private */
    public LDSTextView G4;
    private LDSTextView H4;
    private LDSTextView I4;
    /* access modifiers changed from: private */
    public EditSDHour2Adapter J4;
    private String K4;
    /* access modifiers changed from: private */
    public String L4;
    private int M4 = 0;
    private View N4;
    private LinearLayout O4;
    /* access modifiers changed from: private */
    public List<SDRecord> P4 = new ArrayList();
    private String Q4;
    /* access modifiers changed from: private */
    public String R4;
    private int S4;
    private View T4;
    private com.leedarson.smartcamera.sdk.a U4;
    private f0 V4;
    private final int W4 = 18;
    long X4;
    long Y4;
    private LDSTextView Z4;
    private RelativeLayout a2;
    private float a5 = 0.5f;
    private boolean b5 = false;
    private boolean c5 = false;
    /* access modifiers changed from: private */
    public Handler d5 = new Handler();
    /* access modifiers changed from: private */
    public boolean e5 = false;
    private boolean f5 = false;
    /* access modifiers changed from: private */
    public boolean g5 = false;
    private int h5 = 180;
    /* access modifiers changed from: private */
    public Runnable i5 = new b();
    List<Long> j5 = new ArrayList();
    private com.leedarson.smartcamera.listener.d k5 = new g();
    /* access modifiers changed from: private */
    public int l5;
    com.leedarson.smartcamera.listener.a m5 = new a();
    private ImageView p2;
    private ImageView p3;
    private ImageView p4;
    private ImageView z4;

    static /* synthetic */ void A0(EditSDMinuteActivity x0, RecyclerView x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 2122, new Class[]{EditSDMinuteActivity.class, RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.I0(x1, x2);
        }
    }

    static /* synthetic */ void j0(EditSDMinuteActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2123, new Class[]{EditSDMinuteActivity.class}, Void.TYPE).isSupported) {
            x0.F0();
        }
    }

    static /* synthetic */ void u0(EditSDMinuteActivity x0, List x1) {
        Class[] clsArr = {EditSDMinuteActivity.class, List.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2124, clsArr, Void.TYPE).isSupported) {
            x0.E0(x1);
        }
    }

    static /* synthetic */ boolean w0(EditSDMinuteActivity x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2119, new Class[]{EditSDMinuteActivity.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.b0();
    }

    static /* synthetic */ void x0(EditSDMinuteActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2120, new Class[]{EditSDMinuteActivity.class}, Void.TYPE).isSupported) {
            x0.c0();
        }
    }

    static /* synthetic */ void z0(EditSDMinuteActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2121, new Class[]{EditSDMinuteActivity.class}, Void.TYPE).isSupported) {
            x0.L0();
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2125, new Class[0], Void.TYPE).isSupported) {
                EditSDMinuteActivity.this.V0("timeoutRunnable");
                boolean unused = EditSDMinuteActivity.this.g5 = true;
                EditSDMinuteActivity.this.a();
                EditSDMinuteActivity editSDMinuteActivity = EditSDMinuteActivity.this;
                int i = R$string.timeout;
                editSDMinuteActivity.showToast(i);
                EditSDMinuteActivity.this.G4.setText(PubUtils.getString(EditSDMinuteActivity.this.getBaseContext(), i));
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2089, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            this.a2 = (RelativeLayout) findViewById(R$id.rl_event_title);
            if (savedInstanceState != null) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").o(268468224).D(this);
                finish();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2090, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2091, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            com.leedarson.base.views.g gVar = this.F4;
            if (gVar != null) {
                gVar.dismiss();
            }
            this.d5.removeCallbacks(this.i5);
            org.greenrobot.eventbus.c.c().r(this);
        }
    }

    public int S() {
        return R$layout.activity_sd_card_minute_edit;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2092, new Class[0], Void.TYPE).isSupported) {
            this.T4 = findViewById(R$id.main_layout);
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.z4 = imageView;
            imageView.setOnClickListener(this);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tv_title);
            this.Z4 = lDSTextView;
            Y(lDSTextView, G0(this.X4), "IPC", "Playback", "Edit List");
            LDSTextView lDSTextView2 = (LDSTextView) findViewById(R$id.tv_cancel);
            this.B4 = lDSTextView2;
            lDSTextView2.setOnClickListener(this);
            this.B4.setText(PubUtils.getString(this, R$string.cancel));
            ImageView imageView2 = (ImageView) findViewById(R$id.iv_delete);
            this.p3 = imageView2;
            imageView2.setOnClickListener(this);
            this.p3.setAlpha(this.a5);
            ImageView imageView3 = (ImageView) findViewById(R$id.iv_edit);
            this.p4 = imageView3;
            imageView3.setOnClickListener(this);
            this.N4 = findViewById(R$id.del_line);
            this.O4 = (LinearLayout) findViewById(R$id.del_layout);
            LDSTextView lDSTextView3 = (LDSTextView) findViewById(R$id.tv_selected_num);
            this.A4 = lDSTextView3;
            lDSTextView3.setOnClickListener(this);
            this.A4.setTextColor(this.x);
            this.F4 = new com.leedarson.base.views.g(this);
            Dialog dialog = new Dialog(this, R$style.Theme_dialog);
            this.E4 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.G4 = (LDSTextView) this.E4.findViewById(R$id.tip_content_tv);
            this.H4 = (LDSTextView) this.E4.findViewById(R$id.left_btn_tv);
            this.I4 = (LDSTextView) this.E4.findViewById(R$id.right_btn_tv);
            RecyclerView recyclerView = (RecyclerView) findViewById(R$id.rv_events);
            this.D4 = recyclerView;
            recyclerView.setHasFixedSize(true);
            this.D4.setLayoutManager(new GridLayoutManager(this, 3));
            if (this.c5) {
                this.J4 = new EditSDHour2Adapter(R$layout.item_sd_card_hour, this.P4, this.L4, this.R4);
            } else {
                this.J4 = new EditSDHour2Adapter(R$layout.item_sd_card_hour, this.P4, this.Q4, this.R4);
            }
            this.D4.setAdapter(this.J4);
            this.J4.setOnItemClickListener(new c());
            this.D4.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 2129, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onScrollStateChanged(recyclerView, newState);
                        EditSDMinuteActivity.A0(EditSDMinuteActivity.this, recyclerView, newState);
                    }
                }
            });
            ImageView imageView4 = (ImageView) findViewById(R$id.iv_all_checkbox);
            this.p2 = imageView4;
            imageView4.setOnClickListener(this);
            X0();
            K0();
            if (this.P4.size() > 18) {
                J0(this.R4, this.P4.subList(0, 18));
            } else {
                String str = this.R4;
                List<SDRecord> list = this.P4;
                J0(str, list.subList(0, list.size()));
            }
            this.p4.performClick();
        }
    }

    public class c implements com.chad.library.adapter.base.listener.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
            if (!PatchProxy.proxy(new Object[]{baseQuickAdapter, view, new Integer(position)}, this, changeQuickRedirect, false, 2128, new Class[]{BaseQuickAdapter.class, View.class, Integer.TYPE}, Void.TYPE).isSupported) {
                if (!EditSDMinuteActivity.this.C4) {
                    return;
                }
                if (EditSDMinuteActivity.w0(EditSDMinuteActivity.this)) {
                    EditSDMinuteActivity.x0(EditSDMinuteActivity.this);
                    return;
                }
                ((SDRecord) EditSDMinuteActivity.this.P4.get(position)).setCheck(!((SDRecord) EditSDMinuteActivity.this.P4.get(position)).isCheck());
                ImageView imageIcon = (ImageView) view.findViewById(R$id.iv_check);
                if (imageIcon != null) {
                    imageIcon.setSelected(((SDRecord) EditSDMinuteActivity.this.P4.get(position)).isCheck());
                }
                EditSDMinuteActivity.z0(EditSDMinuteActivity.this);
            }
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2093, new Class[0], Void.TYPE).isSupported) {
            Intent intent = getIntent();
            this.L4 = intent.getStringExtra("deviceId");
            this.K4 = intent.getStringExtra("selectedDate");
            this.X4 = com.leedarson.utils.e.b(this.K4 + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            this.Y4 = com.leedarson.utils.e.b(this.K4 + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            this.Q4 = intent.getStringExtra("p2pId");
            this.R4 = intent.getStringExtra("eventStr");
            this.S4 = intent.getIntExtra("eventType", 0);
            this.c5 = intent.getBooleanExtra("isWebRTC", false);
            this.f5 = intent.getBooleanExtra("isSupportPreCon", false);
            List<Long> sdVideoList = (List) intent.getSerializableExtra("lists");
            if (sdVideoList == null || sdVideoList.size() <= 0) {
                finish();
                return;
            }
            for (int i2 = 0; i2 < sdVideoList.size(); i2++) {
                SDRecord sdRecord = new SDRecord();
                sdRecord.setTimestamp(sdVideoList.get(i2).longValue());
                this.P4.add(sdRecord);
            }
        }
    }

    private void K0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2094, new Class[0], Void.TYPE).isSupported) {
            if (!this.c5) {
                this.U4 = com.leedarson.manager.a.i().m(this.L4);
            } else if (this.f5) {
                this.V4 = com.leedarson.manager.a.i().j(this.L4);
            } else {
                com.leedarson.manager.a i2 = com.leedarson.manager.a.i();
                this.V4 = i2.j(this.L4 + "-SD");
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2130, new Class[0], Void.TYPE).isSupported) {
                EditSDMinuteActivity.this.F4.setCancelable(true);
                EditSDMinuteActivity.this.F4.setCanceledOnTouchOutside(true);
                EditSDMinuteActivity.this.F4.show();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2095, new Class[0], Void.TYPE).isSupported) {
            if (this.F4 != null) {
                runOnUiThread(new d());
            }
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2131, new Class[0], Void.TYPE).isSupported) {
                if (EditSDMinuteActivity.this.F4.isShowing()) {
                    EditSDMinuteActivity.this.F4.dismiss();
                }
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2096, new Class[0], Void.TYPE).isSupported) {
            if (this.F4 != null) {
                runOnUiThread(new e());
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2097, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.iv_back) {
            F0();
        } else if (viewId == R$id.iv_delete) {
            Y0("del_tips");
        } else if (viewId == R$id.iv_edit) {
            this.C4 = true;
            this.p4.setVisibility(8);
            this.z4.setVisibility(8);
            this.B4.setVisibility(0);
            X0();
            this.M4 = 0;
        } else if (viewId == R$id.tv_cancel) {
            F0();
        } else if (viewId == R$id.iv_all_checkbox || viewId == R$id.tv_selected_num) {
            ImageView imageView = this.p2;
            imageView.setSelected(true ^ imageView.isSelected());
            boolean isAllSelected = this.p2.isSelected();
            L0();
            for (int i2 = 0; i2 < this.P4.size(); i2++) {
                this.P4.get(i2).setCheck(isAllSelected);
            }
            L0();
            this.J4.notifyItemRangeChanged(0, this.P4.size());
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void D0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2098, new Class[0], Void.TYPE).isSupported) {
            this.b5 = true;
            this.j5.clear();
            for (int i2 = 0; i2 < this.P4.size(); i2++) {
                if (this.P4.get(i2).isCheck()) {
                    this.j5.add(Long.valueOf(this.P4.get(i2).getTimestamp()));
                }
            }
            if (this.j5.size() < 50) {
                b();
                this.d5.removeCallbacks(this.i5);
                this.d5.postDelayed(this.i5, 60000);
                this.g5 = false;
                com.leedarson.smartcamera.listener.a deleteRecordRespListener = new f(0);
                try {
                    if (this.c5) {
                        f0 f0Var = this.V4;
                        if (f0Var != null) {
                            f0Var.N0(0, this.S4, this.j5, deleteRecordRespListener);
                            return;
                        }
                        return;
                    }
                    com.leedarson.smartcamera.sdk.a aVar = this.U4;
                    if (aVar != null) {
                        aVar.B0(0, this.S4, this.j5, deleteRecordRespListener);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    showToast(R$string.delete_failed);
                }
            } else {
                Y0("deleting");
                this.l5 = 0;
                List<Long> records = new ArrayList<>();
                for (int i3 = 0; i3 < 50; i3++) {
                    records.add(this.j5.get(i3));
                }
                E0(records);
            }
        }
    }

    public class f implements com.leedarson.smartcamera.listener.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        f(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2132, new Class[0], Void.TYPE).isSupported) {
                EditSDMinuteActivity.this.d5.removeCallbacks(EditSDMinuteActivity.this.i5);
                EditSDMinuteActivity.this.a();
                EditSDMinuteActivity.this.showToast(R$string.delete_success);
                if (this.a == 1) {
                    EditSDMinuteActivity.j0(EditSDMinuteActivity.this);
                    return;
                }
                for (int i = 0; i < EditSDMinuteActivity.this.j5.size(); i++) {
                    int j = 0;
                    while (true) {
                        if (j >= EditSDMinuteActivity.this.P4.size()) {
                            break;
                        } else if (((SDRecord) EditSDMinuteActivity.this.P4.get(j)).getTimestamp() == EditSDMinuteActivity.this.j5.get(i).longValue()) {
                            EditSDMinuteActivity.this.P4.remove(j);
                            EditSDMinuteActivity.this.J4.notifyItemRemoved(j);
                            break;
                        } else {
                            j++;
                        }
                    }
                }
                if (EditSDMinuteActivity.this.P4.size() == 0) {
                    EditSDMinuteActivity.j0(EditSDMinuteActivity.this);
                } else {
                    EditSDMinuteActivity.z0(EditSDMinuteActivity.this);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2133, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                EditSDMinuteActivity.this.showToast(R$string.delete_failed);
            }
        }
    }

    private void F0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2099, new Class[0], Void.TYPE).isSupported) {
            if (this.b5) {
                setResult(-1);
            }
            finish();
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2100, new Class[0], Void.TYPE).isSupported) {
            F0();
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2101, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    private void L0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2102, new Class[0], Void.TYPE).isSupported) {
            this.M4 = 0;
            boolean isAllCheck = true;
            Gson gson = new Gson();
            com.leedarson.base.logger.a.c(this, " checkInfo=" + gson.toJson((Object) this.P4));
            for (int i2 = 0; i2 < this.P4.size(); i2++) {
                if (this.P4.get(i2).isCheck()) {
                    this.M4++;
                } else {
                    isAllCheck = false;
                }
            }
            if (isAllCheck) {
                this.A4.setText(String.format(Locale.US, PubUtils.getString(this, R$string.selected), new Object[]{Integer.valueOf(this.M4)}));
                this.A4.setTextColor(this.x);
                this.p2.setSelected(true);
            } else {
                this.p2.setSelected(false);
                if (this.M4 > 0) {
                    this.A4.setText(String.format(Locale.US, PubUtils.getString(this, R$string.selected), new Object[]{Integer.valueOf(this.M4)}));
                    this.A4.setTextColor(this.x);
                } else {
                    this.A4.setText(String.format(Locale.US, PubUtils.getString(this, R$string.selected), new Object[]{Integer.valueOf(this.M4)}));
                    this.A4.setTextColor(this.x);
                }
            }
            this.p3.setAlpha(this.M4 == 0 ? this.a5 : 1.0f);
        }
    }

    private boolean b0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2103, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        L0();
        if (this.M4 >= this.h5) {
            return true;
        }
        return false;
    }

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2104, new Class[0], Void.TYPE).isSupported) {
            a0(PubUtils.getString(this, R$string.lds_sd_card_delete_limit_tips));
        }
    }

    private void X0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2105, new Class[0], Void.TYPE).isSupported) {
            L0();
            if (this.C4) {
                this.O4.setVisibility(0);
                this.N4.setVisibility(0);
            } else {
                this.O4.setVisibility(8);
                this.N4.setVisibility(8);
            }
            if (!this.C4) {
                for (int i2 = 0; i2 < this.P4.size(); i2++) {
                    this.P4.get(i2).setCheck(false);
                }
                this.p2.setSelected(false);
            }
            ((SimpleItemAnimator) this.D4.getItemAnimator()).setSupportsChangeAnimations(false);
            this.J4.y(this.C4);
        }
    }

    private void I0(RecyclerView recyclerView, int newState) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 2106, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (newState == 0) {
                try {
                    if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                        if (gridLayoutManager.findFirstVisibleItemPosition() - 1 > 0) {
                            i2 = gridLayoutManager.findFirstVisibleItemPosition() - 1;
                        }
                        int first = i2;
                        int last = gridLayoutManager.findLastVisibleItemPosition() - 1;
                        Log.e("EditSDMinuteActivity", "onScrollStateChanged: " + first + "==" + last);
                        J0(this.R4, this.P4.subList(first, last + 1));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void J0(String eventStr, List<SDRecord> recordTimestamps) {
        Class[] clsArr = {String.class, List.class};
        if (!PatchProxy.proxy(new Object[]{eventStr, recordTimestamps}, this, changeQuickRedirect, false, 2107, clsArr, Void.TYPE).isSupported) {
            List<Long> noCacheTimes = new ArrayList<>();
            for (int i2 = 0; i2 < recordTimestamps.size(); i2++) {
                long time = recordTimestamps.get(i2).getTimestamp() / 1000;
                if (this.c5) {
                    String str = this.L4;
                    if (!com.leedarson.smartcamera.utils.d.e(eventStr, str, time + "")) {
                        noCacheTimes.add(Long.valueOf(time));
                    }
                } else {
                    String str2 = this.Q4;
                    if (!com.leedarson.smartcamera.utils.d.e(eventStr, str2, time + "")) {
                        noCacheTimes.add(Long.valueOf(time));
                    }
                }
            }
            if (noCacheTimes.size() <= 0) {
                return;
            }
            if (this.c5) {
                f0 f0Var = this.V4;
                if (f0Var != null) {
                    f0Var.n1(noCacheTimes, this.k5);
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.sdk.a aVar = this.U4;
            if (aVar != null && aVar.a1()) {
                this.U4.T0(eventStr, noCacheTimes, this.k5);
            }
        }
    }

    public class g implements com.leedarson.smartcamera.listener.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void c(int status) {
            Object[] objArr = {new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2134, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                EditSDMinuteActivity editSDMinuteActivity = EditSDMinuteActivity.this;
                editSDMinuteActivity.V0("getThumbnai connectStatusChange:" + status);
            }
        }

        public void a(long time, String path) {
            Object[] objArr = {new Long(time), path};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2135, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
                EditSDMinuteActivity editSDMinuteActivity = EditSDMinuteActivity.this;
                editSDMinuteActivity.V0("getThumbnai:" + path);
                EditSDMinuteActivity.this.n(time);
            }
        }

        public void b(long time, byte[] imgBytes) {
            Object[] objArr = {new Long(time), imgBytes};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2136, new Class[]{Long.TYPE, byte[].class}, Void.TYPE).isSupported) {
                String l0 = EditSDMinuteActivity.this.R4;
                String m0 = EditSDMinuteActivity.this.L4;
                boolean b = com.leedarson.smartcamera.utils.d.b(l0, m0, time + "");
                String l02 = EditSDMinuteActivity.this.R4;
                String m02 = EditSDMinuteActivity.this.L4;
                boolean h = com.leedarson.smartcamera.utils.d.h(l02, m02, time + "", imgBytes);
                EditSDMinuteActivity.this.n(time);
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long c;

        h(long j) {
            this.c = j;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2137, new Class[0], Void.TYPE).isSupported) {
                if (EditSDMinuteActivity.this.J4 != null) {
                    for (int i = 0; i < EditSDMinuteActivity.this.P4.size(); i++) {
                        if (((SDRecord) EditSDMinuteActivity.this.P4.get(i)).getTimestamp() / 1000 == this.c) {
                            EditSDMinuteActivity.this.J4.notifyItemChanged(i);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void n(long time) {
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2108, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            runOnUiThread(new h(time));
        }
    }

    public class i implements com.leedarson.smartcamera.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void onSuccess(List<Long> recordTimestamps) {
            if (!PatchProxy.proxy(new Object[]{recordTimestamps}, this, changeQuickRedirect, false, 2138, new Class[]{List.class}, Void.TYPE).isSupported) {
                EditSDMinuteActivity.this.a();
                EditSDMinuteActivity.this.P4.clear();
                com.leedarson.smartcamera.utils.e.c("TAG", "onSuccess: " + recordTimestamps.size());
                for (int i = 0; i < recordTimestamps.size(); i++) {
                    SDRecord record = new SDRecord();
                    record.setTimestamp(recordTimestamps.get(i).longValue());
                    EditSDMinuteActivity.this.P4.add(record);
                }
                Collections.reverse(EditSDMinuteActivity.this.P4);
                Log.e("EditSDMinuteActivity", "records size 2: " + EditSDMinuteActivity.this.P4.size());
                EditSDMinuteActivity.this.J4.notifyItemRangeChanged(0, EditSDMinuteActivity.this.P4.size());
                EditSDMinuteActivity.z0(EditSDMinuteActivity.this);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void W0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2109(0x83d, float:2.955E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.sdk.a r1 = r0.U4
            if (r1 == 0) goto L_0x0029
            long r2 = r0.X4
            long r4 = r0.Y4
            int r6 = r0.S4
            com.leedarson.newui.EditSDMinuteActivity$i r7 = new com.leedarson.newui.EditSDMinuteActivity$i
            r7.<init>()
            r1.O0(r2, r4, r6, r7)
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.EditSDMinuteActivity.W0():void");
    }

    public String G0(long time) {
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 2110, new Class[]{Long.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).format(new Date(time));
    }

    public void V0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 2111, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("EditSDMinuteActivity").a(msg, new Object[0]);
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 2112, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    public class a implements com.leedarson.smartcamera.listener.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2126, new Class[0], Void.TYPE).isSupported) {
                if (EditSDMinuteActivity.this.e5) {
                    EditSDMinuteActivity editSDMinuteActivity = EditSDMinuteActivity.this;
                    int unused = editSDMinuteActivity.l5 = editSDMinuteActivity.l5 + 50;
                    StringBuilder sb = new StringBuilder();
                    sb.append("delete onSuccess: ");
                    sb.append(EditSDMinuteActivity.this.l5);
                    sb.append("==");
                    EditSDMinuteActivity editSDMinuteActivity2 = EditSDMinuteActivity.this;
                    sb.append(editSDMinuteActivity2.H0(Integer.valueOf(editSDMinuteActivity2.l5), Integer.valueOf(EditSDMinuteActivity.this.j5.size())));
                    Log.d("EditSDMinuteActivity", sb.toString());
                    LDSTextView f0 = EditSDMinuteActivity.this.G4;
                    Locale locale = Locale.US;
                    String string = PubUtils.getString(EditSDMinuteActivity.this.getBaseContext(), R$string.lds_deleting);
                    EditSDMinuteActivity editSDMinuteActivity3 = EditSDMinuteActivity.this;
                    f0.setText(String.format(locale, string, new Object[]{editSDMinuteActivity3.H0(Integer.valueOf(editSDMinuteActivity3.l5), Integer.valueOf(EditSDMinuteActivity.this.j5.size()))}));
                    if (EditSDMinuteActivity.this.l5 >= EditSDMinuteActivity.this.j5.size()) {
                        EditSDMinuteActivity.this.d5.removeCallbacks(EditSDMinuteActivity.this.i5);
                        Log.d("EditSDMinuteActivity", "delete end: ");
                        EditSDMinuteActivity.this.E4.dismiss();
                        EditSDMinuteActivity.this.showToast(R$string.delete_success);
                        for (int i = 0; i < EditSDMinuteActivity.this.j5.size(); i++) {
                            int j = 0;
                            while (true) {
                                if (j >= EditSDMinuteActivity.this.P4.size()) {
                                    break;
                                } else if (((SDRecord) EditSDMinuteActivity.this.P4.get(j)).getTimestamp() == EditSDMinuteActivity.this.j5.get(i).longValue()) {
                                    EditSDMinuteActivity.this.P4.remove(j);
                                    EditSDMinuteActivity.this.J4.notifyItemRemoved(j);
                                    break;
                                } else {
                                    j++;
                                }
                            }
                        }
                        if (EditSDMinuteActivity.this.P4.size() == 0) {
                            EditSDMinuteActivity.j0(EditSDMinuteActivity.this);
                        } else {
                            EditSDMinuteActivity.z0(EditSDMinuteActivity.this);
                        }
                    } else {
                        List<Long> records = new ArrayList<>();
                        int i2 = EditSDMinuteActivity.this.l5;
                        while (true) {
                            if (i2 < (EditSDMinuteActivity.this.l5 + 50 > EditSDMinuteActivity.this.j5.size() ? EditSDMinuteActivity.this.j5.size() : EditSDMinuteActivity.this.l5 + 50)) {
                                records.add(EditSDMinuteActivity.this.j5.get(i2));
                                i2++;
                            } else {
                                EditSDMinuteActivity.u0(EditSDMinuteActivity.this, records);
                                return;
                            }
                        }
                    }
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2127, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("EditSDMinuteActivity", "delete onError: ");
            }
        }
    }

    private void E0(List<Long> delRecords) {
        if (!PatchProxy.proxy(new Object[]{delRecords}, this, changeQuickRedirect, false, 2113, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.e5 = true;
            try {
                if (this.c5) {
                    f0 f0Var = this.V4;
                    if (f0Var != null) {
                        f0Var.N0(0, this.S4, delRecords, this.m5);
                    }
                } else {
                    com.leedarson.smartcamera.sdk.a aVar = this.U4;
                    if (aVar != null) {
                        aVar.B0(0, this.S4, delRecords, this.m5);
                    }
                }
                this.d5.removeCallbacks(this.i5);
                this.d5.postDelayed(this.i5, 60000);
                this.g5 = false;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: N0 */
    public /* synthetic */ void O0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2118, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.E4.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void Y0(String flag) {
        int i2;
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 2114, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.H4.setOnClickListener(new e2(this));
            char c2 = 65535;
            switch (flag.hashCode()) {
                case 814175980:
                    if (flag.equals("del_tips")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 819717032:
                    if (flag.equals("deleting")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.E4.setCanceledOnTouchOutside(false);
                    this.H4.setText(PubUtils.getString(this, R$string.cancel));
                    this.I4.setText(PubUtils.getString(this, R$string.confirm));
                    this.H4.setVisibility(0);
                    this.E4.findViewById(R$id.view_line).setVisibility(0);
                    this.I4.setOnClickListener(new f2(this));
                    if (this.E4 != null && (i2 = this.M4) > 0) {
                        if (i2 < 15) {
                            this.G4.setText(PubUtils.getString(this, R$string.delete_event_tip));
                            break;
                        } else {
                            this.G4.setText(PubUtils.getString(this, R$string.delete_long_event_tip));
                            break;
                        }
                    }
                case 1:
                    this.E4.setCanceledOnTouchOutside(false);
                    this.H4.setVisibility(8);
                    this.E4.findViewById(R$id.view_line).setVisibility(8);
                    this.G4.setText(String.format(Locale.US, PubUtils.getString(getBaseContext(), R$string.lds_deleting), new Object[]{"0%"}));
                    this.I4.setText(PubUtils.getString(this, R$string.cancel));
                    this.I4.setOnClickListener(new g2(this));
                    break;
            }
            this.E4.show();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: P0 */
    public /* synthetic */ void Q0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2117, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.E4.dismiss();
        D0();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: S0 */
    public /* synthetic */ void U0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2116, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.e5 = false;
        this.E4.dismiss();
        if (!this.g5) {
            b();
            W0();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public String H0(Integer number1, Integer number2) {
        Class<Integer> cls = Integer.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{number1, number2}, this, changeQuickRedirect2, false, 2115, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        DecimalFormat df = new DecimalFormat("#");
        if (number2.intValue() == 0) {
            return "0%";
        }
        double n = ((double) number1.intValue()) / ((double) number2.intValue());
        if (n > 1.0d) {
            n = 1.0d;
        }
        return df.format((double) (((float) n) * 100.0f)) + "%";
    }
}

package com.leedarson.newui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.EditEventsAdapter;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.LoadingProgressBar;
import com.leedarson.base.views.LoadingView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.g;
import com.leedarson.event.EventsRefreshEvent;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSPageDataWrapBean;
import com.leedarson.newui.cloud_play_back.repos.w;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.repos.beans.EventListRequestParamsBean;
import com.leedarson.newui.repos.o;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.utils.b;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.f;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.c;

public class EditEventsActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView A4;
    private LDSTextView B4;
    private boolean C4 = false;
    private RecyclerView D4;
    private Dialog E4 = null;
    private g F4;
    private LDSTextView G4;
    private LDSTextView H4;
    private LDSTextView I4;
    private List<EventListItemBean> J4 = new ArrayList();
    private EditEventsAdapter K4;
    private String L4;
    private String M4;
    private String N4;
    private String O4;
    private String P4;
    private int Q4 = 0;
    private final int R4 = 15;
    private int S4 = 1;
    private boolean T4 = false;
    private SmartRefreshLayout U4;
    private RelativeLayout V4;
    private LinearLayout W4;
    private LinearLayout X4;
    private LinearLayout Y4;
    private LoadingProgressBar Z4;
    private View a2;
    private LoadingView a5;
    o b5 = new o();
    w c5 = new w();
    private float d5 = 0.5f;
    ArrayList<EventListItemBean> e5 = new ArrayList<>();
    private ImageView p2;
    private ImageView p3;
    private LDSTextView p4;
    private LDSTextView z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2003, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setTitle("IPC Playback Edit List");
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            if (savedInstanceState != null) {
                a.c().a("/app/main/").o(268468224).D(this);
                finish();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2004, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            PubUtils.setLanguage(this);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2005, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            g gVar = this.F4;
            if (gVar != null) {
                gVar.dismiss();
            }
            c.c().r(this);
        }
    }

    public int S() {
        return R$layout.activity_event_edit;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2006, new Class[0], Void.TYPE).isSupported) {
            this.a5 = (LoadingView) findViewById(R$id.lv_loading);
            this.V4 = (RelativeLayout) findViewById(R$id.rl_events);
            this.W4 = (LinearLayout) findViewById(R$id.ll_no_event);
            this.X4 = (LinearLayout) findViewById(R$id.ll_load_fail);
            this.Y4 = (LinearLayout) findViewById(R$id.del_layout);
            this.a2 = findViewById(R$id.del_line);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tv_try);
            this.B4 = lDSTextView;
            lDSTextView.setOnClickListener(this);
            LDSTextView lDSTextView2 = (LDSTextView) findViewById(R$id.tv_cancel);
            this.z4 = lDSTextView2;
            lDSTextView2.setOnClickListener(this);
            LDSTextView lDSTextView3 = this.z4;
            int i = R$string.cancel;
            lDSTextView3.setText(PubUtils.getString(this, i));
            ImageView imageView = (ImageView) findViewById(R$id.iv_delete);
            this.p3 = imageView;
            imageView.setOnClickListener(this);
            this.p3.setEnabled(false);
            this.p3.setAlpha(this.d5);
            LDSTextView lDSTextView4 = (LDSTextView) findViewById(R$id.tv_selected_num);
            this.p4 = lDSTextView4;
            lDSTextView4.setOnClickListener(this);
            this.p4.setText(String.format(Locale.US, PubUtils.getString(getApplicationContext(), R$string.selected), new Object[]{Integer.valueOf(this.Q4)}));
            this.A4 = (LDSTextView) findViewById(R$id.tv_foot);
            this.Z4 = (LoadingProgressBar) findViewById(R$id.lp_bottom_loading);
            this.F4 = new g(this);
            Dialog dialog = new Dialog(this, R$style.Theme_dialog);
            this.E4 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.E4.setCanceledOnTouchOutside(false);
            LDSTextView lDSTextView5 = (LDSTextView) this.E4.findViewById(R$id.tip_content_tv);
            this.G4 = lDSTextView5;
            lDSTextView5.setText(PubUtils.getString(this, R$string.delete_event_tip));
            this.H4 = (LDSTextView) this.E4.findViewById(R$id.left_btn_tv);
            this.I4 = (LDSTextView) this.E4.findViewById(R$id.right_btn_tv);
            this.H4.setText(PubUtils.getString(this, i));
            this.I4.setText(PubUtils.getString(this, R$string.confirm));
            this.H4.setOnClickListener(this);
            this.I4.setOnClickListener(this);
            RecyclerView recyclerView = (RecyclerView) findViewById(R$id.rv_events);
            this.D4 = recyclerView;
            recyclerView.setHasFixedSize(true);
            this.D4.setLayoutManager(new LinearLayoutManager(this));
            EditEventsAdapter editEventsAdapter = new EditEventsAdapter(this, this.J4);
            this.K4 = editEventsAdapter;
            this.D4.setAdapter(editEventsAdapter);
            this.K4.setOnItemClickListener(new b2(this));
            SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
            this.U4 = smartRefreshLayout;
            smartRefreshLayout.E(new a2(this));
            this.U4.D(new w1(this));
            ImageView imageView2 = (ImageView) findViewById(R$id.iv_all_checkbox);
            this.p2 = imageView2;
            imageView2.setOnClickListener(this);
            e0();
            this.p4.setTextColor(this.x);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: v0 */
    public /* synthetic */ void w0(View view, int i) {
        if (!PatchProxy.proxy(new Object[]{view, new Integer(i)}, this, changeQuickRedirect, false, 2025, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
            f0();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x0 */
    public /* synthetic */ void y0(f refreshlayout) {
        if (!PatchProxy.proxy(new Object[]{refreshlayout}, this, changeQuickRedirect, false, 2024, new Class[]{f.class}, Void.TYPE).isSupported) {
            this.T4 = false;
            this.S4 = 1;
            this.U4.B(true);
            refreshlayout.c(true);
            refreshlayout.a();
            this.Z4.setVisibility(0);
            c0(this.L4, this.S4, 15, this.P4, this.M4);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: z0 */
    public /* synthetic */ void A0(f fVar) {
        if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 2023, new Class[]{f.class}, Void.TYPE).isSupported) {
            this.T4 = true;
            c0(this.L4, this.S4, 15, this.P4, this.M4);
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2007, new Class[0], Void.TYPE).isSupported) {
            Intent intent = getIntent();
            this.N4 = intent.getStringExtra("deviceIds");
            this.L4 = intent.getStringExtra("selectedDate");
            this.P4 = intent.getStringExtra("areaIds");
            this.M4 = intent.getStringExtra("eventCodes");
            this.O4 = intent.getStringExtra("eventType");
        }
    }

    private void e0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2008, new Class[0], Void.TYPE).isSupported) {
            this.a5.setVisibility(0);
            this.Y4.setVisibility(8);
            this.a2.setVisibility(8);
            this.V4.setVisibility(8);
            this.X4.setVisibility(8);
            this.W4.setVisibility(8);
            this.U4.B(false);
            this.U4.c(false);
            this.S4 = 1;
            this.T4 = false;
            this.a5.c();
            c0(this.L4, this.S4, 15, this.P4, this.M4);
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2009, new Class[0], Void.TYPE).isSupported) {
            if (this.F4 != null) {
                runOnUiThread(new v1(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B0 */
    public /* synthetic */ void C0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2022, new Class[0], Void.TYPE).isSupported) {
            this.F4.setCancelable(false);
            this.F4.setCanceledOnTouchOutside(false);
            this.F4.show();
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2010, new Class[0], Void.TYPE).isSupported) {
            if (this.F4 != null) {
                runOnUiThread(new d2(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t0 */
    public /* synthetic */ void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2021, new Class[0], Void.TYPE).isSupported) {
            if (this.F4.isShowing()) {
                this.F4.dismiss();
            }
        }
    }

    private void c0(String str, int i, int i2, String str2, String str3) {
        Class<String> cls = String.class;
        Object[] objArr = {str, new Integer(i), new Integer(i2), str2, str3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2011, new Class[]{cls, cls2, cls2, cls, cls}, Void.TYPE).isSupported) {
            int page = i;
            String areaIds = str2;
            String time = str;
            int i3 = i2;
            String eventCodes = str3;
            long startTime = e.b(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long endTime = e.b(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            EventListRequestParamsBean requestParamsBean = new EventListRequestParamsBean();
            requestParamsBean.pageNum = this.S4;
            ArrayList<String> listEvents = new ArrayList<>();
            requestParamsBean.eventCodes = listEvents;
            requestParamsBean.recordSta = startTime;
            requestParamsBean.recordEnd = endTime;
            if (!com.alibaba.android.arouter.utils.e.b(areaIds)) {
                String[] areaIdsArr = areaIds.split(",");
                ArrayList<String> arrayListArea = new ArrayList<>();
                int i4 = 0;
                while (true) {
                    int page2 = page;
                    if (i4 >= areaIdsArr.length) {
                        break;
                    }
                    arrayListArea.add(areaIdsArr[i4]);
                    i4++;
                    page = page2;
                }
                requestParamsBean.areaIds = arrayListArea;
            }
            if (!com.alibaba.android.arouter.utils.e.b(this.N4)) {
                String[] deviceIdStrArr = this.N4.split(",");
                ArrayList<String> listDevices = new ArrayList<>();
                for (int i5 = 0; i5 < deviceIdStrArr.length; i5++) {
                    if (!com.alibaba.android.arouter.utils.e.b(deviceIdStrArr[i5])) {
                        listDevices.add(deviceIdStrArr[i5]);
                    }
                }
                requestParamsBean.deviceIds = listDevices;
                if ("pet".equals(this.O4)) {
                    if (com.alibaba.android.arouter.utils.e.b(eventCodes)) {
                        eventCodes = "25";
                    } else {
                        eventCodes = "25," + eventCodes;
                    }
                }
                if (!com.alibaba.android.arouter.utils.e.b(eventCodes)) {
                    String[] eventCodesArr = eventCodes.split(",");
                    for (String add : eventCodesArr) {
                        listEvents.add(add);
                    }
                    requestParamsBean.eventCodes = listEvents;
                }
                M(this.b5.e(requestParamsBean).c(l.c()).I(new z1(this), new c2(this)));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m0 */
    public /* synthetic */ void n0(LDSBasePageBean eventListItemResponseBean) {
        if (!PatchProxy.proxy(new Object[]{eventListItemResponseBean}, this, changeQuickRedirect, false, 2020, new Class[]{LDSBasePageBean.class}, Void.TYPE).isSupported) {
            this.U4.q();
            if (this.T4) {
                this.U4.m();
            } else {
                this.U4.a();
                this.J4.clear();
            }
            T t = eventListItemResponseBean.data;
            if (!(((LDSPageDataWrapBean) t).list == null || ((LDSPageDataWrapBean) t).list.size() == 0)) {
                this.S4++;
            }
            T t2 = eventListItemResponseBean.data;
            if (((LDSPageDataWrapBean) t2).list != null) {
                this.J4.addAll(((LDSPageDataWrapBean) t2).list);
            }
            if (((LDSPageDataWrapBean) eventListItemResponseBean.data).total <= this.J4.size()) {
                this.A4.setText(PubUtils.getString(BaseApplication.b(), R$string.no_more));
                this.U4.C(true);
                this.Z4.setVisibility(8);
                this.A4.setVisibility(0);
            } else {
                this.A4.setText(PubUtils.getString(BaseApplication.b(), R$string.loading));
                this.U4.C(false);
                this.Z4.setVisibility(0);
                this.A4.setVisibility(8);
            }
            if (this.J4.size() > 0) {
                this.V4.setVisibility(0);
                this.Y4.setVisibility(0);
                this.a2.setVisibility(0);
                this.W4.setVisibility(8);
            } else {
                this.W4.setVisibility(0);
                this.Y4.setVisibility(8);
                this.a2.setVisibility(8);
                this.V4.setVisibility(8);
            }
            this.a5.setVisibility(8);
            this.X4.setVisibility(8);
            this.U4.B(true);
            this.U4.c(true);
            this.K4.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q0 */
    public /* synthetic */ void s0(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 2019, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            this.U4.q();
            this.a5.setVisibility(8);
            this.V4.setVisibility(8);
            this.W4.setVisibility(8);
            this.Y4.setVisibility(8);
            this.a2.setVisibility(8);
            this.X4.setVisibility(0);
            this.U4.B(true);
            this.U4.c(true);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2012, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.tv_cancel) {
            finish();
        } else if (viewId == R$id.iv_delete) {
            Dialog dialog = this.E4;
            if (dialog != null && this.Q4 > 0) {
                dialog.show();
            }
        } else if (viewId == R$id.left_btn_tv) {
            Dialog dialog2 = this.E4;
            if (dialog2 != null) {
                dialog2.dismiss();
            }
        } else if (viewId == R$id.right_btn_tv) {
            Dialog dialog3 = this.E4;
            if (dialog3 != null) {
                dialog3.dismiss();
            }
            b0();
        } else if (viewId == R$id.tv_try) {
            this.a5.setVisibility(0);
            this.V4.setVisibility(8);
            this.X4.setVisibility(8);
            this.W4.setVisibility(8);
            this.Y4.setVisibility(8);
            this.a2.setVisibility(8);
            e0();
        } else if (viewId == R$id.iv_all_checkbox || viewId == R$id.tv_selected_num) {
            ImageView imageView = this.p2;
            imageView.setSelected(!imageView.isSelected());
            boolean isAllSelected = this.p2.isSelected();
            if (isAllSelected) {
                this.Q4 = this.J4.size();
                this.p4.setText(String.format(Locale.US, PubUtils.getString(getApplicationContext(), R$string.selected), new Object[]{Integer.valueOf(this.Q4)}));
                this.p4.setTextColor(this.x);
                this.p3.setEnabled(true);
                this.p3.setAlpha(1.0f);
            } else {
                this.Q4 = 0;
                this.p4.setText(String.format(Locale.US, PubUtils.getString(getApplicationContext(), R$string.selected), new Object[]{Integer.valueOf(this.Q4)}));
                this.p4.setTextColor(this.x);
                this.p3.setEnabled(false);
                this.p3.setAlpha(this.d5);
            }
            for (int i = 0; i < this.J4.size(); i++) {
                this.J4.get(i).setChecked(isAllSelected);
            }
            this.K4.notifyDataSetChanged();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: package-private */
    public void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2013, new Class[0], Void.TYPE).isSupported) {
            for (int i = 0; i < this.J4.size(); i++) {
                if (this.J4.get(i).isChecked()) {
                    this.e5.add(this.J4.get(i));
                }
            }
            String[] eventUuids = new String[this.e5.size()];
            for (int i2 = 0; i2 < this.e5.size(); i2++) {
                eventUuids[i2] = this.e5.get(i2).getEventUuid();
            }
            b();
            M(this.c5.c(this.N4, eventUuids, this.e5).c(l.c()).I(new x1(this), new y1(this)));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g0 */
    public /* synthetic */ void j0(LDSBaseBean lDSBaseBean) {
        if (!PatchProxy.proxy(new Object[]{lDSBaseBean}, this, changeQuickRedirect, false, 2018, new Class[]{LDSBaseBean.class}, Void.TYPE).isSupported) {
            a();
            showToast(R$string.delete_success);
            for (int i = 0; i < this.e5.size(); i++) {
                int j = 0;
                while (true) {
                    if (j >= this.J4.size()) {
                        break;
                    } else if (this.e5.get(i).getEventUuid().equals(this.J4.get(j).getEventUuid())) {
                        this.J4.remove(j);
                        this.K4.notifyItemRemoved(j);
                        break;
                    } else {
                        j++;
                    }
                }
            }
            f0();
            if (this.J4.size() == 0) {
                e0();
            }
            c.c().l(new EventsRefreshEvent());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k0 */
    public /* synthetic */ void l0(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 2017, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a();
            showToast(R$string.delete_failed);
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2014, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    private void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2015, new Class[0], Void.TYPE).isSupported) {
            this.Q4 = 0;
            boolean isAllCheck = this.J4.size() > 0;
            for (int i = 0; i < this.J4.size(); i++) {
                if (!this.J4.get(i).isChecked()) {
                    isAllCheck = false;
                } else {
                    this.Q4++;
                }
            }
            if (isAllCheck) {
                this.p2.setSelected(true);
            } else {
                this.p2.setSelected(false);
            }
            if (this.Q4 > 0) {
                this.p4.setText(String.format(Locale.US, PubUtils.getString(this, R$string.selected), new Object[]{Integer.valueOf(this.Q4)}));
                this.p4.setTextColor(this.x);
                this.p3.setEnabled(true);
                this.p3.setAlpha(1.0f);
                return;
            }
            this.p4.setText(String.format(Locale.US, PubUtils.getString(getApplicationContext(), R$string.selected), new Object[]{Integer.valueOf(this.Q4)}));
            this.p4.setTextColor(this.x);
            this.p3.setEnabled(false);
            this.p3.setAlpha(this.d5);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 2016, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    public boolean d0() {
        return false;
    }
}

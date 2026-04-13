package com.leedarson.newui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.component.a;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.EditSDHour2Adapter;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.CalendarData;
import com.leedarson.bean.EventBean;
import com.leedarson.bean.FilterBean;
import com.leedarson.newui.view.CustomDayView;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.ui.SpaceItemDecoration;
import com.leedarson.view.WeekCalendar;
import com.leedarson.view.rangeseekbar.RangeSeekBar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class EditSDHourActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public LDSTextView A4;
    private LDSTextView B4;
    private LDSTextView C4;
    private boolean D4 = false;
    /* access modifiers changed from: private */
    public RecyclerView E4;
    private Dialog F4 = null;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g G4;
    private LDSTextView H4;
    private LDSTextView I4;
    private LDSTextView J4;
    /* access modifiers changed from: private */
    public List<EventBean> K4 = new ArrayList();
    /* access modifiers changed from: private */
    public EditSDHour2Adapter L4;
    /* access modifiers changed from: private */
    public String M4;
    /* access modifiers changed from: private */
    public String N4;
    private String O4;
    /* access modifiers changed from: private */
    public int P4 = 0;
    private Context Q4;
    private View R4;
    private LinearLayout S4;
    /* access modifiers changed from: private */
    public WeekCalendar T4;
    /* access modifiers changed from: private */
    public MonthPager U4;
    /* access modifiers changed from: private */
    public com.ldf.calendar.model.a V4;
    private SmartRefreshLayout W4;
    /* access modifiers changed from: private */
    public boolean X4 = false;
    /* access modifiers changed from: private */
    public String Y4;
    /* access modifiers changed from: private */
    public String Z4;
    private RelativeLayout a2;
    private final int a5 = 15;
    /* access modifiers changed from: private */
    public int b5 = 1;
    private Handler c5 = new Handler();
    private List<String> d5 = new ArrayList();
    private List<FilterBean> e5 = new ArrayList();
    private HashMap<String, String> f5 = new HashMap<>();
    /* access modifiers changed from: private */
    public ArrayList<Calendar> g5 = new ArrayList<>();
    /* access modifiers changed from: private */
    public CalendarViewAdapter h5;
    private com.ldf.calendar.interf.c i5;
    /* access modifiers changed from: private */
    public int j5 = MonthPager.c;
    /* access modifiers changed from: private */
    public LDSTextView k5;
    private LDSTextView l5;
    private boolean m5 = false;
    /* access modifiers changed from: private */
    public boolean n5 = false;
    /* access modifiers changed from: private */
    public final com.ldf.calendar.model.a o5 = new com.ldf.calendar.model.a();
    /* access modifiers changed from: private */
    public ImageView p2;
    private ImageView p3;
    private ImageView p4;
    private ImageView z4;

    static /* synthetic */ void A0(EditSDHourActivity x0, String x1) {
        Class[] clsArr = {EditSDHourActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_LIVEVIEWTIMESTAMP_REQ, clsArr, Void.TYPE).isSupported) {
            x0.d1(x1);
        }
    }

    static /* synthetic */ void C0(EditSDHourActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_LIVEVIEWTIMESTAMP_RESP, new Class[]{EditSDHourActivity.class}, Void.TYPE).isSupported) {
            x0.O0();
        }
    }

    static /* synthetic */ void g0(EditSDHourActivity x0, String str, String x2, String str2, int x4, int x5, boolean x6) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, str, x2, str2, new Integer(x4), new Integer(x5), new Byte(x6 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_REQ, new Class[]{EditSDHourActivity.class, cls, cls, cls, cls2, cls2, Boolean.TYPE}, Void.TYPE).isSupported) {
            String x1 = str;
            String x3 = str2;
            x0.U0(x1, x2, x3, x4, x5, x6);
        }
    }

    static /* synthetic */ void k0(EditSDHourActivity x0, String x1) {
        Class[] clsArr = {EditSDHourActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_RESP, clsArr, Void.TYPE).isSupported) {
            x0.f1(x1);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2026, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            this.a2 = (RelativeLayout) findViewById(R$id.rl_title);
            this.Q4 = this;
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2027, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            PubUtils.setLanguage(this);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2028, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            com.leedarson.base.views.g gVar = this.G4;
            if (gVar != null) {
                gVar.dismiss();
            }
        }
    }

    public int S() {
        return R$layout.activity_sd_card_hour_edit;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2029, new Class[0], Void.TYPE).isSupported) {
            b1();
            e1();
            V0();
        }
    }

    private void b1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2030, new Class[0], Void.TYPE).isSupported) {
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.p4 = imageView;
            imageView.setOnClickListener(this);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tv_cancel);
            this.B4 = lDSTextView;
            lDSTextView.setOnClickListener(this);
            this.C4 = (LDSTextView) findViewById(R$id.tv_title);
            ImageView imageView2 = (ImageView) findViewById(R$id.iv_delete);
            this.p3 = imageView2;
            imageView2.setOnClickListener(this);
            ImageView imageView3 = (ImageView) findViewById(R$id.iv_edit);
            this.z4 = imageView3;
            imageView3.setOnClickListener(this);
            this.R4 = findViewById(R$id.del_line);
            this.S4 = (LinearLayout) findViewById(R$id.del_layout);
            this.A4 = (LDSTextView) findViewById(R$id.tv_selected_num);
            this.G4 = new com.leedarson.base.views.g(this);
            Dialog dialog = new Dialog(this, R$style.Theme_dialog);
            this.F4 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.F4.setCanceledOnTouchOutside(false);
            LDSTextView lDSTextView2 = (LDSTextView) this.F4.findViewById(R$id.tip_content_tv);
            this.H4 = lDSTextView2;
            lDSTextView2.setText(PubUtils.getString(this, R$string.delete_event_tip));
            this.I4 = (LDSTextView) this.F4.findViewById(R$id.left_btn_tv);
            this.J4 = (LDSTextView) this.F4.findViewById(R$id.right_btn_tv);
            this.I4.setText(PubUtils.getString(this, R$string.cancel));
            this.J4.setText(PubUtils.getString(this, R$string.confirm));
            this.I4.setOnClickListener(this);
            this.J4.setOnClickListener(this);
            this.T4 = (WeekCalendar) findViewById(R$id.week_calendar);
            this.W4 = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
            RecyclerView recyclerView = (RecyclerView) findViewById(R$id.rv_events);
            this.E4 = recyclerView;
            recyclerView.setHasFixedSize(true);
            this.E4.setLayoutManager(new GridLayoutManager(this, 3));
            this.E4.addItemDecoration(new SpaceItemDecoration(30));
            this.E4.setAdapter(this.L4);
            ImageView imageView4 = (ImageView) findViewById(R$id.iv_all_checkbox);
            this.p2 = imageView4;
            imageView4.setOnClickListener(new j());
            this.T4.setOnDateClickListener(new m());
            this.T4.setOnCurrentMonthDateListener(new n());
            this.T4.setOnYearMouthClickListener(new o());
            this.T4.setOnChangeWeekClickListener(new p());
            this.W4.E(new q());
            this.W4.D(new r());
        }
    }

    public class j implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2054, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            EditSDHourActivity.this.p2.setSelected(!EditSDHourActivity.this.p2.isSelected());
            boolean isAllSelected = EditSDHourActivity.this.p2.isSelected();
            if (isAllSelected) {
                EditSDHourActivity.this.A4.setText(String.format(Locale.US, PubUtils.getString(EditSDHourActivity.this.getApplicationContext(), R$string.selected), new Object[]{Integer.valueOf(EditSDHourActivity.this.K4.size())}));
            } else {
                int unused = EditSDHourActivity.this.P4 = 0;
                EditSDHourActivity.this.A4.setText("");
            }
            for (int i = 0; i < EditSDHourActivity.this.K4.size(); i++) {
                ((EventBean) EditSDHourActivity.this.K4.get(i)).setChecked(isAllSelected);
            }
            EditSDHourActivity.this.L4.notifyDataSetChanged();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class m implements WeekCalendar.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void a(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2075, new Class[]{String.class}, Void.TYPE).isSupported) {
                String time = str;
                try {
                    if (!TextUtils.isEmpty(time)) {
                        String unused = EditSDHourActivity.this.M4 = time;
                        boolean unused2 = EditSDHourActivity.this.X4 = false;
                        String unused3 = EditSDHourActivity.this.Z4 = "";
                        EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                        String unused4 = editSDHourActivity.Y4 = editSDHourActivity.N4;
                        int unused5 = EditSDHourActivity.this.b5 = 1;
                        EditSDHourActivity editSDHourActivity2 = EditSDHourActivity.this;
                        EditSDHourActivity.g0(editSDHourActivity2, editSDHourActivity2.Y4, EditSDHourActivity.this.Z4, time, EditSDHourActivity.this.b5, 15, false);
                        return;
                    }
                    String[] selectedTime = EditSDHourActivity.this.M4.split("-");
                    int year = Integer.parseInt(selectedTime[0]);
                    String month = selectedTime[1];
                    if (month.startsWith("0")) {
                        month = month.substring(1);
                    }
                    String day = selectedTime[2];
                    if (day.startsWith("0")) {
                        day = day.substring(1);
                    }
                    EditSDHourActivity.this.T4.z(year, Integer.parseInt(month), Integer.parseInt(day));
                    EditSDHourActivity.this.T4.y();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

    public class n implements WeekCalendar.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public void a(String year, String month) {
        }
    }

    public class o implements WeekCalendar.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void a(String time) {
            if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 2083, new Class[]{String.class}, Void.TYPE).isSupported) {
                EditSDHourActivity.k0(EditSDHourActivity.this, time);
            }
        }
    }

    public class p implements WeekCalendar.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2084, new Class[0], Void.TYPE).isSupported) {
                EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                editSDHourActivity.P0(editSDHourActivity.N4, EditSDHourActivity.this.T4);
            }
        }
    }

    public class q implements com.scwang.smart.refresh.layout.listener.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        public void b(com.scwang.smart.refresh.layout.api.f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 2085, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
                com.scwang.smart.refresh.layout.api.f fVar2 = fVar;
                int unused = EditSDHourActivity.this.b5 = 1;
                boolean unused2 = EditSDHourActivity.this.X4 = false;
                EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                EditSDHourActivity.g0(editSDHourActivity, editSDHourActivity.Y4, EditSDHourActivity.this.Z4, EditSDHourActivity.this.M4, 1, 15, true);
            }
        }
    }

    public class r implements com.scwang.smart.refresh.layout.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void f(com.scwang.smart.refresh.layout.api.f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 2086, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
                boolean unused = EditSDHourActivity.this.X4 = true;
                EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                EditSDHourActivity.g0(editSDHourActivity, editSDHourActivity.Y4, EditSDHourActivity.this.Z4, EditSDHourActivity.this.M4, EditSDHourActivity.this.b5 + 1, 15, true);
            }
        }
    }

    private void f1(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2031, new Class[]{String.class}, Void.TYPE).isSupported) {
            String str2 = str;
            this.f5.clear();
            Dialog bottomDialog = new Dialog(this.Q4, R$style.BottomDialog);
            View contentView = LayoutInflater.from(this.Q4).inflate(R$layout.month_calendar, (ViewGroup) null);
            bottomDialog.setContentView(contentView);
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.width = getResources().getDisplayMetrics().widthPixels;
            contentView.setLayoutParams(layoutParams);
            ((LDSTextView) bottomDialog.findViewById(R$id.tip_time)).setVisibility(8);
            ((RangeSeekBar) bottomDialog.findViewById(R$id.seekbar_time)).setVisibility(8);
            MonthPager monthPager = (MonthPager) bottomDialog.findViewById(R$id.calendar_month_view);
            this.U4 = monthPager;
            monthPager.setViewHeight(com.ldf.calendar.a.b(this.Q4, 288.0f));
            this.k5 = (LDSTextView) bottomDialog.findViewById(R$id.tv_selected_date);
            com.ldf.calendar.model.a aVar = new com.ldf.calendar.model.a();
            this.V4 = aVar;
            this.k5.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(aVar), TimeUtils.YYYY_MM_DD)));
            W0();
            ((LDSTextView) bottomDialog.findViewById(R$id.tv_Cancle)).setOnClickListener(new s(bottomDialog));
            ((LDSTextView) bottomDialog.findViewById(R$id.tv_Done)).setOnClickListener(new t(bottomDialog));
            bottomDialog.getWindow().setGravity(80);
            bottomDialog.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            bottomDialog.show();
            this.c5.postDelayed(new a(), 1000);
        }
    }

    public class s implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Dialog c;

        s(Dialog dialog) {
            this.c = dialog;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2087, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            this.c.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class t implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Dialog c;

        t(Dialog dialog) {
            this.c = dialog;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2088, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            try {
                if (EditSDHourActivity.this.V4 != null) {
                    Log.d("MonthDateDialog", "CurrentDate = " + EditSDHourActivity.this.V4.year + " - " + EditSDHourActivity.this.V4.month + " - " + EditSDHourActivity.this.V4.day);
                    String unused = EditSDHourActivity.this.Z4 = "";
                    boolean unused2 = EditSDHourActivity.this.X4 = false;
                    EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                    String unused3 = editSDHourActivity.M4 = com.leedarson.utils.e.f(editSDHourActivity.V4);
                    int unused4 = EditSDHourActivity.this.b5 = 1;
                    EditSDHourActivity editSDHourActivity2 = EditSDHourActivity.this;
                    EditSDHourActivity.g0(editSDHourActivity2, editSDHourActivity2.Y4, EditSDHourActivity.this.Z4, EditSDHourActivity.this.M4, EditSDHourActivity.this.b5, 15, false);
                    EditSDHourActivity.this.T4.z(EditSDHourActivity.this.V4.year, EditSDHourActivity.this.V4.month, EditSDHourActivity.this.V4.day);
                    EditSDHourActivity.this.T4.y();
                }
                this.c.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2055, new Class[0], Void.TYPE).isSupported) {
                EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                ArrayList unused = editSDHourActivity.g5 = editSDHourActivity.h5.c();
                EditSDHourActivity editSDHourActivity2 = EditSDHourActivity.this;
                editSDHourActivity2.Q0(editSDHourActivity2.N4, EditSDHourActivity.this.g5);
            }
        }
    }

    private void W0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2032, new Class[0], Void.TYPE).isSupported) {
            X0();
            CalendarViewAdapter calendarViewAdapter = new CalendarViewAdapter(this.Q4, this.i5, a.C0078a.MONTH, a.b.Sunday, new CustomDayView(this.Q4, R$layout.custom_day));
            this.h5 = calendarViewAdapter;
            calendarViewAdapter.m(new b());
            Z0();
        }
    }

    public class b implements CalendarViewAdapter.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(a.C0078a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 2056, new Class[]{a.C0078a.class}, Void.TYPE).isSupported) {
                EditSDHourActivity.this.E4.scrollToPosition(0);
            }
        }
    }

    private void Y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2033, new Class[0], Void.TYPE).isSupported) {
            this.h5.l(this.f5);
            this.h5.g();
        }
    }

    public class c implements com.ldf.calendar.interf.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b(com.ldf.calendar.model.a date) {
            if (!PatchProxy.proxy(new Object[]{date}, this, changeQuickRedirect, false, 2057, new Class[]{com.ldf.calendar.model.a.class}, Void.TYPE).isSupported) {
                if (com.leedarson.utils.e.h(date, EditSDHourActivity.this.o5)) {
                    EditSDHourActivity.this.h5.h(EditSDHourActivity.this.V4);
                    EditSDHourActivity.this.h5.e();
                    return;
                }
                com.ldf.calendar.model.a unused = EditSDHourActivity.this.V4 = date;
                EditSDHourActivity.this.k5.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(EditSDHourActivity.this.V4), TimeUtils.YYYY_MM_DD)));
                Log.d("MonthDateDialog", "CurrentDate = " + EditSDHourActivity.this.V4.year + " - " + EditSDHourActivity.this.V4.month + " - " + EditSDHourActivity.this.V4.day);
            }
        }

        public void a(int offset) {
            Object[] objArr = {new Integer(offset)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2058, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                EditSDHourActivity.this.U4.g(offset);
            }
        }
    }

    private void X0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2034, new Class[0], Void.TYPE).isSupported) {
            this.i5 = new c();
        }
    }

    private void Z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2035, new Class[0], Void.TYPE).isSupported) {
            this.h5.n();
            this.U4.setAdapter(this.h5);
            this.U4.setCurrentItem(MonthPager.c);
            this.U4.setPageTransformer(false, new ViewPager.PageTransformer() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void transformPage(View page, float position) {
                    if (!PatchProxy.proxy(new Object[]{page, new Float(position)}, this, changeQuickRedirect, false, 2059, new Class[]{View.class, Float.TYPE}, Void.TYPE).isSupported) {
                        page.setAlpha((float) Math.sqrt((double) (1.0f - Math.abs(position))));
                    }
                }
            });
            this.U4.addOnPageChangeListener((MonthPager.a) new d());
        }
    }

    public class d implements MonthPager.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2060, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("MonthDateDialog", "onPageSelected position = " + position);
                int unused = EditSDHourActivity.this.j5 = position;
                if (EditSDHourActivity.this.h5.b() == a.C0078a.MONTH) {
                    EditSDHourActivity.this.h5.o(EditSDHourActivity.this.U4.getRowIndex());
                    EditSDHourActivity.this.h5.n();
                }
                EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                ArrayList unused2 = editSDHourActivity.g5 = editSDHourActivity.h5.c();
                if (EditSDHourActivity.this.g5.get(position % EditSDHourActivity.this.g5.size()) != null) {
                    com.ldf.calendar.model.a date = ((Calendar) EditSDHourActivity.this.g5.get(position % EditSDHourActivity.this.g5.size())).getSeedDate();
                    if (com.leedarson.utils.e.h(date, EditSDHourActivity.this.o5)) {
                        EditSDHourActivity.this.h5.h(EditSDHourActivity.this.V4);
                        EditSDHourActivity.this.h5.e();
                        return;
                    }
                    com.ldf.calendar.model.a unused3 = EditSDHourActivity.this.V4 = date;
                    EditSDHourActivity.this.k5.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(EditSDHourActivity.this.V4), TimeUtils.YYYY_MM_DD)));
                    Log.d("MonthDateDialog", "CurrentDate = " + EditSDHourActivity.this.V4.year + " - " + EditSDHourActivity.this.V4.month);
                }
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2036, new Class[0], Void.TYPE).isSupported) {
            Intent intent = getIntent();
            this.N4 = intent.getStringExtra("deviceId");
            this.O4 = intent.getStringExtra("deviceName");
            this.M4 = intent.getStringExtra("selectedDate");
        }
    }

    private void V0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2037, new Class[0], Void.TYPE).isSupported) {
            this.Q4 = this;
            Y(this.C4, this.O4, "IPC", "Edit List");
            P0(this.N4, this.T4);
            b();
            String g2 = com.leedarson.utils.e.g();
            this.M4 = g2;
            U0(this.Y4, this.Z4, g2, 1, 15, false);
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2061, new Class[0], Void.TYPE).isSupported) {
                EditSDHourActivity.this.G4.setCancelable(false);
                EditSDHourActivity.this.G4.setCanceledOnTouchOutside(false);
                EditSDHourActivity.this.G4.show();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2038, new Class[0], Void.TYPE).isSupported) {
            if (this.G4 != null) {
                runOnUiThread(new e());
            }
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2062, new Class[0], Void.TYPE).isSupported) {
                if (EditSDHourActivity.this.G4.isShowing()) {
                    EditSDHourActivity.this.G4.dismiss();
                }
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2039, new Class[0], Void.TYPE).isSupported) {
            if (this.G4 != null) {
                runOnUiThread(new f());
            }
        }
    }

    private void U0(String str, String str2, String str3, int i2, int i3, boolean isRefresh) {
        long endTime;
        JSONObject paramsJson;
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, str3, new Integer(i2), new Integer(i3), new Byte(isRefresh ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2040, new Class[]{cls, cls, cls, cls2, cls2, Boolean.TYPE}, Void.TYPE).isSupported) {
            String str4 = str2;
            int pageNum = i2;
            String str5 = str;
            String time = str3;
            int pageSize = i3;
            long startTime = com.leedarson.utils.e.b(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long endTime2 = com.leedarson.utils.e.b(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            String baseUrl = SharePreferenceUtils.getPrefString(this, "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson2 = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(this, "owner", "");
            boolean z = isRefresh;
            String accessToken = SharePreferenceUtils.getPrefString(this, "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    try {
                        headerJson.put("owner", (Object) owner);
                        headerJson.put("token", (Object) accessToken);
                    } catch (JSONException e2) {
                        e = e2;
                        paramsJson = paramsJson2;
                        endTime = endTime2;
                    }
                }
                headerJson.put("terminal", (Object) "app");
                paramsJson = paramsJson2;
                try {
                    paramsJson.put("startTime", startTime);
                    paramsJson.put("endTime", endTime2);
                    paramsJson.put("pageNum", pageNum);
                    paramsJson.put("pageSize", pageSize);
                    endTime = endTime2;
                    try {
                        paramsJson.put("deviceIds", (Object) this.N4);
                    } catch (JSONException e3) {
                        e = e3;
                    }
                } catch (JSONException e4) {
                    e = e4;
                    endTime = endTime2;
                    e.printStackTrace();
                    timber.log.a.g("getEventRecords").h(headerJson.toString(), new Object[0]);
                    timber.log.a.g("getEventRecords").h(paramsJson.toString(), new Object[0]);
                    String url = baseUrl + "/api/ipc/playback/eventRecords";
                    timber.log.a.g("getEventRecords").h("getEventRecords:request= " + url, new Object[0]);
                    JSONObject jSONObject = paramsJson;
                    JSONObject jSONObject2 = headerJson;
                    String str6 = baseUrl;
                    long j2 = endTime;
                    long j3 = startTime;
                    String str7 = url;
                    b0.b().K(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new g());
                }
            } catch (JSONException e6) {
                e = e6;
                paramsJson = paramsJson2;
                endTime = endTime2;
                e.printStackTrace();
                timber.log.a.g("getEventRecords").h(headerJson.toString(), new Object[0]);
                timber.log.a.g("getEventRecords").h(paramsJson.toString(), new Object[0]);
                String url2 = baseUrl + "/api/ipc/playback/eventRecords";
                timber.log.a.g("getEventRecords").h("getEventRecords:request= " + url2, new Object[0]);
                JSONObject jSONObject3 = paramsJson;
                JSONObject jSONObject22 = headerJson;
                String str62 = baseUrl;
                long j22 = endTime;
                long j32 = startTime;
                String str72 = url2;
                b0.b().K(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url2, headerJson.toString(), paramsJson.toString(), new g());
            }
            timber.log.a.g("getEventRecords").h(headerJson.toString(), new Object[0]);
            timber.log.a.g("getEventRecords").h(paramsJson.toString(), new Object[0]);
            String url22 = baseUrl + "/api/ipc/playback/eventRecords";
            timber.log.a.g("getEventRecords").h("getEventRecords:request= " + url22, new Object[0]);
            JSONObject jSONObject32 = paramsJson;
            JSONObject jSONObject222 = headerJson;
            String str622 = baseUrl;
            long j222 = endTime;
            long j322 = startTime;
            String str722 = url22;
            b0.b().K(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url22, headerJson.toString(), paramsJson.toString(), new g());
        }
    }

    public class g extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2066, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 2063, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventRecords").a("getEventRecords", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2064, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getEventRecords");
                g.c("error=" + e.getMsg(), new Object[0]);
                if (e.getCode() == 21026) {
                    EditSDHourActivity.A0(EditSDHourActivity.this, "getEventRecords");
                } else {
                    EditSDHourActivity.this.a();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2065, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventRecords").c(response, new Object[0]);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("list")) {
                        EditSDHourActivity.this.K4.clear();
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            EditSDHourActivity.this.K4.add((EventBean) new Gson().fromJson(item.toString(), EventBean.class));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EditSDHourActivity.this.a();
                EditSDHourActivity.this.L4.notifyDataSetChanged();
            }
        }
    }

    public void P0(String str, WeekCalendar weekCalendar) {
        JSONObject paramsJson;
        if (!PatchProxy.proxy(new Object[]{str, weekCalendar}, this, changeQuickRedirect, false, 2041, new Class[]{String.class, WeekCalendar.class}, Void.TYPE).isSupported) {
            WeekCalendar weekCalendar2 = weekCalendar;
            String deviceId = str;
            this.N4 = deviceId;
            this.T4 = weekCalendar2;
            List<CalendarData> currentWeekDatas = weekCalendar2.getCurrentWeekDatas();
            CalendarData startDay = currentWeekDatas.get(0);
            CalendarData endDay = currentWeekDatas.get(currentWeekDatas.size() - 1);
            String start = com.leedarson.utils.e.e(startDay);
            String end = com.leedarson.utils.e.e(endDay);
            long startTime = com.leedarson.utils.e.b(start + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long endTime = com.leedarson.utils.e.b(end + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            timber.log.a.g("getEventDates").h("startTime = " + start, new Object[0]);
            timber.log.a.g("getEventDates").h("endTime = " + end, new Object[0]);
            String baseUrl = SharePreferenceUtils.getPrefString(this.Q4, "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson2 = new JSONObject();
            List<CalendarData> list = currentWeekDatas;
            CalendarData calendarData = startDay;
            String owner = SharePreferenceUtils.getPrefString(this.Q4, "owner", "");
            CalendarData calendarData2 = endDay;
            String accessToken = SharePreferenceUtils.getPrefString(this.Q4, "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    try {
                        headerJson.put("owner", (Object) owner);
                        headerJson.put("token", (Object) accessToken);
                    } catch (JSONException e2) {
                        e = e2;
                        paramsJson = paramsJson2;
                    }
                }
                headerJson.put("terminal", (Object) "app");
                paramsJson = paramsJson2;
                try {
                    paramsJson.put("startTime", startTime);
                    paramsJson.put("endTime", endTime);
                    paramsJson.put("deviceIds", (Object) deviceId);
                } catch (JSONException e3) {
                    e = e3;
                }
            } catch (JSONException e4) {
                e = e4;
                paramsJson = paramsJson2;
                e.printStackTrace();
                String str2 = deviceId;
                String str3 = owner;
                timber.log.a.g("getEventDates").h(headerJson.toString(), new Object[0]);
                timber.log.a.g("getEventDates").h(paramsJson.toString(), new Object[0]);
                String url = baseUrl + "/api/ipc/playback/eventDates";
                timber.log.a.g("getEventDates").h("getEventDates:request= " + url, new Object[0]);
                b0.b().K(this.Q4, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new h());
            }
            String str22 = deviceId;
            String str32 = owner;
            timber.log.a.g("getEventDates").h(headerJson.toString(), new Object[0]);
            timber.log.a.g("getEventDates").h(paramsJson.toString(), new Object[0]);
            String url2 = baseUrl + "/api/ipc/playback/eventDates";
            timber.log.a.g("getEventDates").h("getEventDates:request= " + url2, new Object[0]);
            b0.b().K(this.Q4, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url2, headerJson.toString(), paramsJson.toString(), new h());
        }
    }

    public class h extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2070, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 2067, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventDates").a("getEventDates", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2068, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getEventDates");
                g.c("error=" + e.getMsg(), new Object[0]);
                if (e.getCode() == 21026) {
                    EditSDHourActivity.A0(EditSDHourActivity.this, "getEventDates");
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2069, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventDates onSuccess").c(response, new Object[0]);
                EditSDHourActivity.this.S0(response);
            }
        }
    }

    public void Q0(String str, ArrayList<Calendar> arrayList) {
        String str2;
        if (!PatchProxy.proxy(new Object[]{str, arrayList}, this, changeQuickRedirect, false, 2042, new Class[]{String.class, ArrayList.class}, Void.TYPE).isSupported) {
            ArrayList<Calendar> currentMonthCalendars = arrayList;
            String deviceId = str;
            if (currentMonthCalendars != null) {
                this.N4 = deviceId;
                this.g5 = currentMonthCalendars;
                String startTime = null;
                String endTime = null;
                int i2 = 0;
                while (i2 < currentMonthCalendars.size()) {
                    com.ldf.calendar.model.a calendarDate = currentMonthCalendars.get(i2).getSeedDate();
                    if (calendarDate != null) {
                        int year = calendarDate.year;
                        int month = calendarDate.month;
                        Locale locale = Locale.US;
                        Object[] objArr = new Object[2];
                        objArr[0] = Integer.valueOf(year);
                        if (month < 10) {
                            str2 = "0" + month;
                        } else {
                            str2 = "" + month;
                        }
                        objArr[1] = str2;
                        String time = String.format(locale, "%s-%s", objArr);
                        if (com.alibaba.android.arouter.utils.e.b(startTime)) {
                            startTime = time;
                        } else if (Integer.valueOf(time.compareTo(startTime)).intValue() < 0) {
                            startTime = time;
                        }
                        if (com.alibaba.android.arouter.utils.e.b(endTime)) {
                            endTime = time;
                        } else if (Integer.valueOf(time.compareTo(endTime)).intValue() > 0) {
                            endTime = time;
                        }
                        i2++;
                    } else {
                        return;
                    }
                }
                Log.d("MonthDateDialog", "startTime = " + startTime + " , endTime = " + endTime);
                StringBuilder sb = new StringBuilder();
                sb.append(startTime);
                sb.append("-01 00:00:00");
                long start = com.leedarson.utils.e.b(sb.toString(), "yyyy-MM-dd HH:mm:ss");
                long end = com.leedarson.utils.e.b(endTime + "-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
                timber.log.a.g("getEventDates").h("startTime = " + start, new Object[0]);
                timber.log.a.g("getEventDates").h("endTime = " + end, new Object[0]);
                String baseUrl = SharePreferenceUtils.getPrefString(this.Q4, "httpServer", "");
                JSONObject headerJson = new JSONObject();
                JSONObject paramsJson = new JSONObject();
                ArrayList<Calendar> arrayList2 = currentMonthCalendars;
                String owner = SharePreferenceUtils.getPrefString(this.Q4, "owner", "");
                String str3 = startTime;
                String accessToken = SharePreferenceUtils.getPrefString(this.Q4, "accessToken", "");
                try {
                    if (!TextUtils.isEmpty(accessToken)) {
                        headerJson.put("owner", (Object) owner);
                        headerJson.put("token", (Object) accessToken);
                    }
                    headerJson.put("terminal", (Object) "app");
                    paramsJson.put("startTime", start);
                    paramsJson.put("endTime", end);
                    paramsJson.put("deviceIds", (Object) deviceId);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                String str4 = owner;
                timber.log.a.g("getEventDates").h(headerJson.toString(), new Object[0]);
                timber.log.a.g("getEventDates").h(paramsJson.toString(), new Object[0]);
                String url = baseUrl + "/api/ipc/playback/eventDates";
                timber.log.a.g("getEventDates").h("getEventDates:request= " + url, new Object[0]);
                b0.b().K(this.Q4, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new i());
            }
        }
    }

    public class i extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2074, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 2071, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventDates").a("getEventDates", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2072, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getEventDates");
                g.c("error=" + e.getMsg(), new Object[0]);
                if (e.getCode() == 21026) {
                    EditSDHourActivity.A0(EditSDHourActivity.this, "getEventDates");
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2073, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventDates onSuccess").c(response, new Object[0]);
                EditSDHourActivity.this.S0(response);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void S0(java.lang.String r23) {
        /*
            r22 = this;
            java.lang.String r0 = "getEventDates"
            java.lang.String r1 = "yyyy-M-d"
            java.lang.String r2 = "yyyy-MM-dd"
            java.lang.String r3 = "tag"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r12 = 0
            r5[r12] = r23
            com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
            java.lang.Class[] r10 = new java.lang.Class[r4]
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r10[r12] = r6
            java.lang.Class r11 = java.lang.Void.TYPE
            r8 = 0
            r9 = 2043(0x7fb, float:2.863E-42)
            r6 = r22
            com.meituan.robust.PatchProxyResult r5 = com.meituan.robust.PatchProxy.proxy(r5, r6, r7, r8, r9, r10, r11)
            boolean r5 = r5.isSupported
            if (r5 == 0) goto L_0x0026
            return
        L_0x0026:
            r5 = r22
            r6 = r23
            java.util.HashMap<java.lang.String, java.lang.String> r7 = r5.f5     // Catch:{ Exception -> 0x011b }
            r7.clear()     // Catch:{ Exception -> 0x011b }
            java.util.List<java.lang.String> r7 = r5.d5     // Catch:{ Exception -> 0x011b }
            r7.clear()     // Catch:{ Exception -> 0x011b }
            org.json.JSONArray r7 = new org.json.JSONArray     // Catch:{ Exception -> 0x011b }
            r7.<init>((java.lang.String) r6)     // Catch:{ Exception -> 0x011b }
            r8 = 0
        L_0x003a:
            int r9 = r7.length()     // Catch:{ Exception -> 0x011b }
            if (r8 >= r9) goto L_0x0119
            java.lang.Object r9 = r7.get(r8)     // Catch:{ Exception -> 0x011b }
            org.json.JSONObject r9 = (org.json.JSONObject) r9     // Catch:{ Exception -> 0x011b }
            boolean r10 = r9.has(r3)     // Catch:{ Exception -> 0x011b }
            if (r10 == 0) goto L_0x0101
            int r10 = r9.getInt(r3)     // Catch:{ Exception -> 0x011b }
            if (r10 != r4) goto L_0x00f7
            java.lang.String r11 = "startTime"
            long r13 = r9.getLong(r11)     // Catch:{ Exception -> 0x011b }
            java.lang.String r11 = "endTime"
            long r15 = r9.getLong(r11)     // Catch:{ Exception -> 0x011b }
            r17 = r15
            java.lang.String r11 = com.leedarson.utils.e.j(r13, r2)     // Catch:{ Exception -> 0x011b }
            r16 = r5
            r4 = r17
            java.lang.String r17 = com.leedarson.utils.e.j(r4, r2)     // Catch:{ Exception -> 0x00f3 }
            r23 = r17
            java.lang.String r17 = com.leedarson.utils.e.j(r13, r1)     // Catch:{ Exception -> 0x00f3 }
            r18 = r17
            java.lang.String r17 = com.leedarson.utils.e.j(r4, r1)     // Catch:{ Exception -> 0x00f3 }
            r19 = r17
            timber.log.a$b r15 = timber.log.a.g(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r12.<init>()     // Catch:{ Exception -> 0x00f3 }
            r20 = r1
            java.lang.String r1 = "startTime2 = "
            r12.append(r1)     // Catch:{ Exception -> 0x00f3 }
            r1 = r18
            r12.append(r1)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x00f3 }
            r18 = r2
            r21 = r3
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00f3 }
            r15.h(r12, r3)     // Catch:{ Exception -> 0x00f3 }
            timber.log.a$b r2 = timber.log.a.g(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r3.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r12 = "endTime2 = "
            r3.append(r12)     // Catch:{ Exception -> 0x00f3 }
            r12 = r19
            r3.append(r12)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00f3 }
            r19 = r0
            r15 = 0
            java.lang.Object[] r0 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x00f3 }
            r2.h(r3, r0)     // Catch:{ Exception -> 0x00f3 }
            r0 = r23
            boolean r2 = r11.equals(r0)     // Catch:{ Exception -> 0x00f3 }
            if (r2 == 0) goto L_0x00cc
            r2 = r16
            java.util.List<java.lang.String> r3 = r2.d5     // Catch:{ Exception -> 0x00f1 }
            r3.add(r0)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x00d8
        L_0x00cc:
            r2 = r16
            java.util.List<java.lang.String> r3 = r2.d5     // Catch:{ Exception -> 0x00f1 }
            r3.add(r11)     // Catch:{ Exception -> 0x00f1 }
            java.util.List<java.lang.String> r3 = r2.d5     // Catch:{ Exception -> 0x00f1 }
            r3.add(r0)     // Catch:{ Exception -> 0x00f1 }
        L_0x00d8:
            boolean r3 = r1.equals(r12)     // Catch:{ Exception -> 0x00f1 }
            java.lang.String r15 = "0"
            if (r3 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.f5     // Catch:{ Exception -> 0x00f1 }
            r3.put(r12, r15)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x010a
        L_0x00e6:
            java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.f5     // Catch:{ Exception -> 0x00f1 }
            r3.put(r1, r15)     // Catch:{ Exception -> 0x00f1 }
            java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.f5     // Catch:{ Exception -> 0x00f1 }
            r3.put(r12, r15)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x010a
        L_0x00f1:
            r0 = move-exception
            goto L_0x011d
        L_0x00f3:
            r0 = move-exception
            r2 = r16
            goto L_0x011d
        L_0x00f7:
            r19 = r0
            r20 = r1
            r18 = r2
            r21 = r3
            r2 = r5
            goto L_0x010a
        L_0x0101:
            r19 = r0
            r20 = r1
            r18 = r2
            r21 = r3
            r2 = r5
        L_0x010a:
            int r8 = r8 + 1
            r5 = r2
            r2 = r18
            r0 = r19
            r1 = r20
            r3 = r21
            r4 = 1
            r12 = 0
            goto L_0x003a
        L_0x0119:
            r2 = r5
            goto L_0x0120
        L_0x011b:
            r0 = move-exception
            r2 = r5
        L_0x011d:
            r0.printStackTrace()
        L_0x0120:
            java.util.List<com.leedarson.bean.EventBean> r0 = r2.K4
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0134
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.W4
            r1 = 0
            r0.B(r1)
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.W4
            r0.c(r1)
            goto L_0x013f
        L_0x0134:
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.W4
            r1 = 1
            r0.B(r1)
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.W4
            r0.c(r1)
        L_0x013f:
            java.util.List<java.lang.String> r0 = r2.d5
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0155
            com.leedarson.base.views.common.LDSTextView r0 = r2.l5
            android.content.Context r1 = r2.Q4
            int r3 = com.leedarson.R$string.no_events
            java.lang.String r1 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r3)
            r0.setText(r1)
            goto L_0x0161
        L_0x0155:
            com.leedarson.view.WeekCalendar r0 = r2.T4
            java.util.List<java.lang.String> r1 = r2.d5
            r0.setMarkDates(r1)
            com.leedarson.view.WeekCalendar r0 = r2.T4
            r0.y()
        L_0x0161:
            r2.a()
            com.leedarson.adapter.EditSDHour2Adapter r0 = r2.L4
            r0.notifyDataSetChanged()
            com.ldf.calendar.component.CalendarViewAdapter r0 = r2.h5
            if (r0 == 0) goto L_0x0170
            r2.Y0()
        L_0x0170:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.EditSDHourActivity.S0(java.lang.String):void");
    }

    private void d1(String method) {
        if (!PatchProxy.proxy(new Object[]{method}, this, changeQuickRedirect, false, 2044, new Class[]{String.class}, Void.TYPE).isSupported) {
            String base_url = SharePreferenceUtils.getPrefString(this, "httpServer", "");
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(this, "refreshToken", ""));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            JSONObject headerJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(this, "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(this, "accessToken", "");
            try {
                headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(this, "APP_ID", ""));
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("owner", (Object) owner);
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            b0 b2 = b0.b();
            b2.K(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", headerJson.toString(), msgObj.toString(), new k(method));
        }
    }

    public class k extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        k(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2078, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 2076, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("EditSDHourActivity").h("refreshToke onError: ", new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2077, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("EditSDHourActivity");
                g.h("refreshToke onSuccess: " + response, new Object[0]);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString(EditSDHourActivity.this, "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString(EditSDHourActivity.this, "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (this.c.equals("getEventRecords")) {
                    EditSDHourActivity editSDHourActivity = EditSDHourActivity.this;
                    EditSDHourActivity.g0(editSDHourActivity, editSDHourActivity.Y4, EditSDHourActivity.this.Z4, EditSDHourActivity.this.M4, 1, 15, EditSDHourActivity.this.n5);
                } else if (this.c.equals("deleteEvent")) {
                    EditSDHourActivity.C0(EditSDHourActivity.this);
                }
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2045, new Class[]{View.class}, Void.TYPE).isSupported) {
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
            finish();
        } else if (viewId == R$id.iv_delete) {
            Dialog dialog = this.F4;
            if (dialog != null && this.P4 > 0) {
                dialog.show();
            }
        } else if (viewId == R$id.left_btn_tv) {
            Dialog dialog2 = this.F4;
            if (dialog2 != null) {
                dialog2.dismiss();
            }
        } else if (viewId == R$id.right_btn_tv) {
            Dialog dialog3 = this.F4;
            if (dialog3 != null) {
                dialog3.dismiss();
            }
            O0();
        } else if (viewId == R$id.iv_edit) {
            this.D4 = true;
            this.z4.setVisibility(8);
            this.p4.setVisibility(8);
            this.B4.setVisibility(0);
            e1();
            this.P4 = 0;
        } else if (viewId == R$id.tv_cancel) {
            this.D4 = false;
            this.z4.setVisibility(0);
            this.p4.setVisibility(0);
            this.B4.setVisibility(8);
            e1();
            this.P4 = 0;
            c1();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void O0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2046, new Class[0], Void.TYPE).isSupported) {
            b();
            String baseUrl = SharePreferenceUtils.getPrefString(this, "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(this, "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(this, "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                JSONArray ids = new JSONArray();
                for (int i2 = 0; i2 < this.K4.size(); i2++) {
                    if (this.K4.get(i2).isChecked()) {
                        ids.put((Object) this.K4.get(i2).getEventUuid());
                    }
                }
                paramsJson.put("ids", (Object) ids);
                paramsJson.put("deviceId", (Object) this.N4);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/recordDeleteController/deleteEvent";
            timber.log.a.g("deleteEvent").h("deleteEvent:request= " + url, new Object[0]);
            timber.log.a.g("deleteEvent").h("deleteEvent:headerJson= " + headerJson, new Object[0]);
            timber.log.a.g("deleteEvent").h("deleteEvent:paramsJson= " + paramsJson, new Object[0]);
            b0.b().O(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new l());
        }
    }

    public class l extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2082, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 2079, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("deleteEvent").a("deleteEvent", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2080, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("deleteEvent");
                g.c("error=" + e.getMsg(), new Object[0]);
                if (e.getCode() == 21026) {
                    EditSDHourActivity.A0(EditSDHourActivity.this, "deleteEvent");
                }
                EditSDHourActivity.this.showToast(R$string.delete_failed);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2081, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("deleteEvent").c(response, new Object[0]);
                EditSDHourActivity.this.a();
                EditSDHourActivity.this.showToast(R$string.delete_success);
                EditSDHourActivity.this.finish();
            }
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2047, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    private void c1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2048, new Class[0], Void.TYPE).isSupported) {
            boolean isAllCheck = true;
            for (int i2 = 0; i2 < this.K4.size(); i2++) {
                if (!this.K4.get(i2).isChecked()) {
                    isAllCheck = false;
                } else {
                    this.P4++;
                }
            }
            if (isAllCheck) {
                this.p2.setSelected(true);
            } else {
                this.p2.setSelected(false);
            }
            if (this.P4 > 0) {
                this.A4.setText(String.format(Locale.US, PubUtils.getString(this, R$string.selected), new Object[]{Integer.valueOf(this.P4)}));
                return;
            }
            this.A4.setText("");
        }
    }

    private void e1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_STARTFWUPGRADE_RESP, new Class[0], Void.TYPE).isSupported) {
            this.L4.y(this.D4);
            if (this.D4) {
                this.S4.setVisibility(0);
                this.R4.setVisibility(0);
            } else {
                this.S4.setVisibility(8);
                this.R4.setVisibility(8);
            }
            if (!this.D4) {
                for (int i2 = 0; i2 < this.K4.size(); i2++) {
                    this.K4.get(i2).setChecked(false);
                }
                this.L4.notifyDataSetChanged();
                this.p2.setSelected(false);
            }
        }
    }
}

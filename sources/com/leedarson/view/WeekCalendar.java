package com.leedarson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$styleable;
import com.leedarson.adapter.g;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.CalendarData;
import com.leedarson.newui.d6;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.q;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeekCalendar extends LinearLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4;
    /* access modifiers changed from: private */
    public CalendarData B4;
    /* access modifiers changed from: private */
    public CalendarData C4;
    /* access modifiers changed from: private */
    public CalendarData D4;
    /* access modifiers changed from: private */
    public int E4;
    /* access modifiers changed from: private */
    public int F4;
    /* access modifiers changed from: private */
    public int G4;
    private int H4;
    private int I4;
    private int J4;
    private int K4;
    /* access modifiers changed from: private */
    public boolean L4 = false;
    /* access modifiers changed from: private */
    public boolean M4 = false;
    private boolean N4 = true;
    private float O4;
    private float P4;
    /* access modifiers changed from: private */
    public Drawable Q4;
    private Drawable R4;
    private Drawable S4;
    private Drawable T4;
    /* access modifiers changed from: private */
    public List<String> U4 = null;
    private f V4;
    private float W4 = -1.0f;
    private GridView a1 = null;
    /* access modifiers changed from: private */
    public g a2;
    LinearLayout c;
    LDSTextView d;
    LinearLayout f;
    /* access modifiers changed from: private */
    public Context p0;
    private d p1;
    /* access modifiers changed from: private */
    public h p2;
    private e p3;
    private List<CalendarData> p4;
    View q;
    ViewFlipper x;
    ImageView y;
    ImageView z;
    private Map<Integer, List> z4;

    public interface e {
        void a();
    }

    public interface f {
        void a(String str, String str2);
    }

    public interface g {
        void a(String str);
    }

    public interface h {
        void a(String str);
    }

    public WeekCalendar(Context context) {
        super(context);
        u(context, (AttributeSet) null);
    }

    public WeekCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        u(context, attrs);
    }

    private void u(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 11802, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            this.p0 = context;
            ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R$layout.view_calender, this, true);
            this.c = (LinearLayout) findViewById(R$id.iv_previous);
            this.y = (ImageView) findViewById(R$id.pre_btn);
            this.z = (ImageView) findViewById(R$id.next_btn);
            this.d = (LDSTextView) findViewById(R$id.tv_year_mouth);
            this.q = findViewById(R$id.month_layout);
            this.f = (LinearLayout) findViewById(R$id.iv_next);
            this.x = (ViewFlipper) findViewById(R$id.rv_day);
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R$styleable.WeekCalendar);
            this.E4 = typedArray.getColor(R$styleable.WeekCalendar_daysSelectedTextColor, -1);
            this.F4 = typedArray.getColor(R$styleable.WeekCalendar_todayTextColor, -7829368);
            this.G4 = typedArray.getColor(R$styleable.WeekCalendar_weekTextColor, -7829368);
            this.H4 = typedArray.getColor(R$styleable.WeekCalendar_weekBackgroundColor, -1);
            this.I4 = typedArray.getColor(R$styleable.WeekCalendar_monthBackgroundColor, -3355444);
            this.J4 = typedArray.getColor(R$styleable.WeekCalendar_monthTextColor, -1);
            this.K4 = typedArray.getColor(R$styleable.WeekCalendar_daysTextColor, -7829368);
            this.R4 = typedArray.getDrawable(R$styleable.WeekCalendar_nextArrowBg);
            this.S4 = typedArray.getDrawable(R$styleable.WeekCalendar_preArrowBg);
            this.Q4 = typedArray.getDrawable(R$styleable.WeekCalendar_daysSelectedBackground);
            this.T4 = typedArray.getDrawable(R$styleable.WeekCalendar_cornerMarkBg);
            this.L4 = typedArray.getBoolean(R$styleable.WeekCalendar_hideTodayName, false);
            this.M4 = typedArray.getBoolean(R$styleable.WeekCalendar_isCornerMark, false);
            this.O4 = typedArray.getDimension(R$styleable.WeekCalendar_daysTextSize, 14.0f);
            this.P4 = typedArray.getDimension(R$styleable.WeekCalendar_weekTextSize, 14.0f);
            this.N4 = typedArray.getBoolean(R$styleable.WeekCalendar_isShowMonth, true);
            v();
            w();
            typedArray.recycle();
        }
    }

    private void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11803, new Class[0], Void.TYPE).isSupported) {
            this.p4 = new ArrayList();
            getToday();
            CalendarData calendarData = this.B4;
            this.C4 = calendarData;
            this.D4 = calendarData;
            t(calendarData);
            this.A4 = q.f(this.z4, this.C4);
        }
    }

    private void t(CalendarData data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 11804, new Class[]{CalendarData.class}, Void.TYPE).isSupported) {
            List<CalendarData> l = q.l(data);
            this.p4 = l;
            this.z4 = q.m(l);
            f fVar = this.V4;
            if (fVar != null) {
                fVar.a(String.valueOf(data.year), String.valueOf(data.month));
            }
            this.d.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(q(data), TimeUtils.YYYY_MM_DD)));
        }
    }

    public String q(CalendarData data) {
        StringBuilder sb;
        StringBuilder sb2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 11805, new Class[]{CalendarData.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (data == null) {
            return "";
        }
        String sYear = String.valueOf(data.year);
        String sMonth = String.valueOf(data.month);
        String sDay = String.valueOf(data.day);
        Locale locale = Locale.US;
        Object[] objArr = new Object[3];
        objArr[0] = sYear;
        if (2 > sMonth.length()) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(sMonth);
        objArr[1] = sb.toString();
        if (2 > sDay.length()) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(sDay);
        objArr[2] = sb2.toString();
        return String.format(locale, "%s-%s-%s", objArr);
    }

    private void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11806, new Class[0], Void.TYPE).isSupported) {
            if (this.N4) {
                this.q.setVisibility(0);
            } else {
                this.q.setVisibility(8);
            }
            Drawable drawable = this.R4;
            if (drawable != null) {
                this.z.setBackgroundDrawable(drawable);
            }
            Drawable drawable2 = this.S4;
            if (drawable2 != null) {
                this.y.setBackgroundDrawable(drawable2);
            }
            this.q.setBackgroundColor(this.I4);
            this.d.setTextColor(this.J4);
            this.d.setOnClickListener(new a());
            this.c.setOnClickListener(new b());
            this.f.setOnClickListener(new c());
            this.f.setEnabled(false);
            this.z.setImageDrawable(this.p0.getDrawable(R$drawable.ic_events_icon_after_disable));
            this.a1 = o();
            d dVar = new d(this.p0, this.z4.get(Integer.valueOf(this.A4)));
            this.p1 = dVar;
            this.a1.setAdapter(dVar);
            this.x.addView(this.a1, 0);
        }
    }

    public class a extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11822, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (WeekCalendar.this.p2 != null) {
                    WeekCalendar.this.p2.a(WeekCalendar.this.getTheDayOfSelected());
                }
            }
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11823, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            WeekCalendar.this.A(true);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11824, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            WeekCalendar.this.B(true);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private GridView o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11807, new Class[0], GridView.class);
        if (proxy.isSupported) {
            return (GridView) proxy.result;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        GridView gridView = new GridView(this.p0);
        gridView.setNumColumns(7);
        gridView.setGravity(16);
        gridView.setSelector(new ColorDrawable(0));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setLayoutParams(params);
        return gridView;
    }

    public void setMarkDates(List<String> list) {
        this.U4 = list;
    }

    public List<String> getMarkDates() {
        return this.U4;
    }

    public CalendarData getToday() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11808, new Class[0], CalendarData.class);
        if (proxy.isSupported) {
            return (CalendarData) proxy.result;
        }
        String currentDate = new SimpleDateFormat("yyyy-M-d").format(new Date());
        CalendarData calendarData = new CalendarData(Integer.parseInt(currentDate.split("-")[0]), Integer.parseInt(currentDate.split("-")[1]), Integer.parseInt(currentDate.split("-")[2]));
        this.B4 = calendarData;
        return calendarData;
    }

    public void z(int year, int month, int day) {
        Object[] objArr = {new Integer(year), new Integer(month), new Integer(day)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11809, clsArr, Void.TYPE).isSupported) {
            this.C4 = new CalendarData(year, month, day);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11810, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        switch (event.getAction()) {
            case 0:
                this.W4 = event.getRawX();
                break;
            case 2:
                float dx = this.W4 - event.getRawX();
                if (dx > 80.0f) {
                    B(true);
                    return true;
                } else if (dx < -80.0f) {
                    A(true);
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    public void B(boolean isShowNextWeek) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isShowNextWeek ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11811, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            GridView mGridView = o();
            d dVar = new d(this.p0, s(isShowNextWeek));
            this.p1 = dVar;
            mGridView.setAdapter(dVar);
            this.x.addView(mGridView, 1);
            this.x.setInAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_left_in));
            this.x.setOutAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_left_out));
            this.x.showNext();
            this.x.removeViewAt(0);
            p();
            e eVar = this.p3;
            if (eVar != null) {
                eVar.a();
            }
        }
    }

    public void A(boolean isShowLastWeek) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isShowLastWeek ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11812, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            GridView mGridView = o();
            d dVar = new d(this.p0, r(isShowLastWeek));
            this.p1 = dVar;
            mGridView.setAdapter(dVar);
            this.x.addView(mGridView, 1);
            this.x.setInAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_right_in));
            this.x.setOutAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_right_out));
            this.x.showNext();
            this.x.removeViewAt(0);
            p();
            e eVar = this.p3;
            if (eVar != null) {
                eVar.a();
            }
        }
    }

    public List<CalendarData> s(boolean isShowNextWeek) {
        int i = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(isShowNextWeek ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11813, new Class[]{Boolean.TYPE}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        if (this.A4 == this.z4.size() - 1 || !isShowNextWeek) {
            CalendarData calendarData = this.D4;
            if (!calendarData.isNextMonthDay) {
                calendarData = q.e(calendarData);
            }
            this.D4 = calendarData;
            t(calendarData);
            if (q.j(this.D4) == 0 || !isShowNextWeek) {
                i = 0;
            }
            this.A4 = i;
        } else {
            this.A4++;
        }
        return this.z4.get(Integer.valueOf(this.A4));
    }

    public List<CalendarData> r(boolean isShowLastWeek) {
        int i = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(isShowLastWeek ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11814, new Class[]{Boolean.TYPE}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        int i2 = this.A4;
        if (i2 == 0 || !isShowLastWeek) {
            CalendarData calendarData = this.D4;
            if (!calendarData.isLastMonthDay) {
                calendarData = q.d(calendarData);
            }
            this.D4 = calendarData;
            t(calendarData);
            if (isShowLastWeek) {
                int size = this.z4.size();
                if (q.i(this.D4) != 6) {
                    i = 2;
                }
                this.A4 = size - i;
            } else {
                this.A4 = 0;
            }
        } else {
            this.A4 = i2 - 1;
        }
        return this.z4.get(Integer.valueOf(this.A4));
    }

    public List<CalendarData> getCurrentWeekDatas() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11815, new Class[0], List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        return this.z4.get(Integer.valueOf(this.A4));
    }

    public class d extends com.leedarson.adapter.g {
        public static ChangeQuickRedirect changeQuickRedirect;
        List<CalendarData> f;

        public d(Context context, List<CalendarData> datas) {
            super(context, datas);
            this.f = datas;
        }

        public int a() {
            return R$layout.item_calendar;
        }

        public View b(int i, View view, g.a aVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i), view, aVar}, this, changeQuickRedirect, false, 11825, new Class[]{Integer.TYPE, View.class, g.a.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            View convertView = view;
            int position = i;
            g.a viewHolder = aVar;
            CalendarData calendar = (CalendarData) getItem(position);
            LDSTextView dayView = (LDSTextView) viewHolder.a(R$id.tv_calendar_day);
            ImageView corner_mark_iv = (ImageView) viewHolder.a(R$id.corner_mark_iv);
            if (WeekCalendar.this.M4) {
                corner_mark_iv.setVisibility(0);
            } else {
                corner_mark_iv.setVisibility(8);
            }
            if (!WeekCalendar.this.L4) {
                dayView.setText(String.valueOf(calendar.day));
            } else {
                dayView.setText(String.valueOf(calendar.day));
            }
            if (calendar.isSameDay(WeekCalendar.this.C4) && !calendar.isFuture(WeekCalendar.this.B4)) {
                dayView.setTextColor(WeekCalendar.this.E4);
                dayView.setBackground(WeekCalendar.this.Q4);
            } else if (calendar.isFuture(WeekCalendar.this.B4)) {
                dayView.setTextColor(WeekCalendar.this.p0.getResources().getColor(R$color.text_assist_color));
                dayView.setBackground((Drawable) null);
            } else if (calendar.isSameDay(WeekCalendar.this.B4)) {
                dayView.setTextColor(WeekCalendar.this.F4);
                dayView.setBackground((Drawable) null);
            } else {
                dayView.setBackground((Drawable) null);
                dayView.setTextColor(WeekCalendar.this.G4);
            }
            if (WeekCalendar.this.U4 != null) {
                int i2 = 0;
                while (true) {
                    if (i2 >= WeekCalendar.this.U4.size()) {
                        break;
                    }
                    String[] dates = ((String) WeekCalendar.this.U4.get(i2)).split("-");
                    CalendarData cd = new CalendarData();
                    cd.year = Integer.parseInt(dates[0]);
                    cd.month = Integer.parseInt(dates[1]);
                    cd.day = Integer.parseInt(dates[2]);
                    if (calendar.isSameDay(cd)) {
                        corner_mark_iv.setVisibility(0);
                        break;
                    }
                    corner_mark_iv.setVisibility(8);
                    i2++;
                }
            }
            dayView.setOnClickListener(new a(position));
            return convertView;
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ int c;

            a(int i) {
                this.c = i;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11826, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View view2 = view;
                d dVar = d.this;
                CalendarData unused = WeekCalendar.this.C4 = dVar.f.get(this.c);
                d dVar2 = d.this;
                CalendarData unused2 = WeekCalendar.this.D4 = dVar2.f.get(this.c);
                d.this.notifyDataSetChanged();
                if (WeekCalendar.this.a2 != null) {
                    WeekCalendar.this.a2.a(WeekCalendar.this.getTheDayOfSelected());
                }
                WeekCalendar.this.d.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(WeekCalendar.this.getTheDayOfSelected(), TimeUtils.YYYY_MM_DD)));
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    public void setOnCurrentMonthDateListener(f onCurrentMonthDateListener) {
        this.V4 = onCurrentMonthDateListener;
    }

    public void setOnYearMouthClickListener(h listener) {
        this.p2 = listener;
    }

    public void setOnChangeWeekClickListener(e listener) {
        this.p3 = listener;
    }

    public void setOnDateClickListener(g listener) {
        this.a2 = listener;
    }

    public String getTheDayOfSelected() {
        StringBuilder sb;
        StringBuilder sb2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11816, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        CalendarData calendarData = this.C4;
        if (calendarData == null || calendarData.isFuture(this.B4)) {
            return "";
        }
        String sYear = String.valueOf(this.C4.year);
        String sMonth = String.valueOf(this.C4.month);
        String sDay = String.valueOf(this.C4.day);
        Locale locale = Locale.US;
        Object[] objArr = new Object[3];
        objArr[0] = sYear;
        if (2 > sMonth.length()) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(sMonth);
        objArr[1] = sb.toString();
        if (2 > sDay.length()) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(sDay);
        objArr[2] = sb2.toString();
        return String.format(locale, "%s-%s-%s", objArr);
    }

    public boolean x() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11817, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return this.B4.isSameDay(this.D4) && this.B4.isSameDay(this.C4);
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11818, new Class[0], Void.TYPE).isSupported) {
            this.p1.notifyDataSetChanged();
        }
    }

    public boolean C() {
        int i;
        int i2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11820, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (x() && this.A4 == q.f(this.z4, this.C4)) {
            return true;
        }
        int mode = 0;
        CalendarData calendarData = this.D4;
        int i3 = calendarData.year;
        CalendarData calendarData2 = this.C4;
        int i4 = calendarData2.year;
        if (i3 > i4 || (i = calendarData.month) > (i2 = calendarData2.month)) {
            t(calendarData2);
            this.A4 = q.f(this.z4, this.C4);
            mode = 2;
        } else if (i3 < i4 || i < i2) {
            t(calendarData2);
            this.A4 = q.f(this.z4, this.C4);
            mode = 1;
        } else {
            int position = q.f(this.z4, calendarData2);
            int i5 = this.A4;
            if (i5 < position) {
                mode = 1;
            } else if (i5 > position) {
                mode = 2;
            }
            this.A4 = position;
        }
        this.D4 = this.C4;
        GridView mGridView = o();
        d dVar = new d(this.p0, this.z4.get(Integer.valueOf(this.A4)));
        this.p1 = dVar;
        mGridView.setAdapter(dVar);
        this.x.addView(mGridView, 1);
        if (mode == 2) {
            this.x.setInAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_right_in));
            this.x.setOutAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_right_out));
        } else if (mode == 1) {
            this.x.setInAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_left_in));
            this.x.setOutAnimation(AnimationUtils.loadAnimation(this.p0, R$anim.push_left_out));
        } else {
            this.x.setInAnimation((Animation) null);
            this.x.setOutAnimation((Animation) null);
        }
        this.x.showNext();
        this.x.removeViewAt(0);
        p();
        return false;
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11821, new Class[0], Void.TYPE).isSupported) {
            List<CalendarData> currentWeekDatas = getCurrentWeekDatas();
            if (com.leedarson.utils.e.a(com.leedarson.utils.e.e(currentWeekDatas.get(currentWeekDatas.size() - 1)), com.leedarson.utils.e.e(this.B4)) == 1) {
                this.f.setEnabled(false);
                this.z.setImageDrawable(this.p0.getDrawable(R$drawable.ic_events_icon_after_disable));
                return;
            }
            this.f.setEnabled(true);
            this.z.setImageDrawable(this.p0.getDrawable(R$drawable.ic_events_icon_after));
        }
    }
}

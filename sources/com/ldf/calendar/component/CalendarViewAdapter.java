package com.ldf.calendar.component;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.ldf.calendar.component.a;
import com.ldf.calendar.interf.c;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarViewAdapter extends PagerAdapter {
    private static com.ldf.calendar.model.a a = new com.ldf.calendar.model.a();
    private ArrayList<Calendar> b = new ArrayList<>();
    private int c;
    private a.C0078a d = a.C0078a.MONTH;
    private int e = 0;
    private com.ldf.calendar.model.a f;
    private b g;
    private a.b h = a.b.Monday;

    public interface b {
        void a(a.C0078a aVar);
    }

    public CalendarViewAdapter(Context context, c onSelectDateListener, a.C0078a calendarType, a.b weekArrayType, com.ldf.calendar.interf.a dayView) {
        this.d = calendarType;
        this.h = weekArrayType;
        d(context, onSelectDateListener);
        k(dayView);
    }

    private void d(Context context, c onSelectDateListener) {
        j(new com.ldf.calendar.model.a());
        this.f = new com.ldf.calendar.model.a();
        for (int i = 0; i < 3; i++) {
            a calendarAttr = new a();
            calendarAttr.c(a.C0078a.MONTH);
            calendarAttr.f(this.h);
            Calendar calendar = new Calendar(context, onSelectDateListener, calendarAttr);
            calendar.setOnAdapterSelectListener(new a());
            this.b.add(calendar);
        }
    }

    public class a implements com.ldf.calendar.interf.b {
        a() {
        }

        public void b() {
            CalendarViewAdapter.this.a();
        }

        public void a() {
            CalendarViewAdapter.this.e();
        }
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Log.e("ldf", "setPrimaryItem");
        super.setPrimaryItem(container, position, object);
        this.c = position;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("ldf", "instantiateItem");
        if (position < 2) {
            return null;
        }
        ArrayList<Calendar> arrayList = this.b;
        Calendar calendar = arrayList.get(position % arrayList.size());
        if (this.d == a.C0078a.MONTH) {
            com.ldf.calendar.model.a current = this.f.modifyMonth(position - MonthPager.c);
            current.setDay(1);
            calendar.d(current);
        } else {
            com.ldf.calendar.model.a current2 = this.f.modifyWeek(position - MonthPager.c);
            if (this.h == a.b.Sunday) {
                calendar.d(com.ldf.calendar.a.h(current2));
            } else {
                calendar.d(com.ldf.calendar.a.i(current2));
            }
            calendar.g(this.e);
        }
        if (container.getChildCount() == this.b.size()) {
            container.removeView(this.b.get(position % 3));
        }
        if (container.getChildCount() < this.b.size()) {
            container.addView(calendar, 0);
        } else {
            container.addView(calendar, position % 3);
        }
        return calendar;
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isViewFromObject(android.view.View r2, java.lang.Object r3) {
        /*
            r1 = this;
            r0 = r3
            android.view.View r0 = (android.view.View) r0
            if (r2 != r0) goto L_0x0007
            r0 = 1
            goto L_0x0008
        L_0x0007:
            r0 = 0
        L_0x0008:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ldf.calendar.component.CalendarViewAdapter.isViewFromObject(android.view.View, java.lang.Object):boolean");
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container);
    }

    public ArrayList<Calendar> c() {
        return this.b;
    }

    public void a() {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.get(i).a();
        }
    }

    public void e() {
        for (int i = 0; i < this.b.size(); i++) {
            Calendar calendar = this.b.get(i);
            calendar.f();
            if (calendar.getCalendarType() == a.C0078a.WEEK) {
                calendar.g(this.e);
            }
        }
    }

    public void l(HashMap<String, String> markData) {
        com.ldf.calendar.a.r(markData);
    }

    public void n() {
        a.C0078a aVar;
        ArrayList<Calendar> arrayList = this.b;
        if (arrayList != null && arrayList.size() > 0 && this.d != (aVar = a.C0078a.MONTH)) {
            this.g.a(aVar);
            this.d = aVar;
            int i = this.c;
            MonthPager.c = i;
            this.f = this.b.get(i % 3).getSeedDate();
            Calendar v1 = this.b.get(this.c % 3);
            v1.e(aVar);
            v1.d(this.f);
            Calendar v2 = this.b.get((this.c - 1) % 3);
            v2.e(aVar);
            com.ldf.calendar.model.a last = this.f.modifyMonth(-1);
            last.setDay(1);
            v2.d(last);
            Calendar v3 = this.b.get((this.c + 1) % 3);
            v3.e(aVar);
            com.ldf.calendar.model.a next = this.f.modifyMonth(1);
            next.setDay(1);
            v3.d(next);
        }
    }

    public void o(int rowIndex) {
        a.C0078a aVar;
        this.e = rowIndex;
        ArrayList<Calendar> arrayList = this.b;
        if (arrayList != null && arrayList.size() > 0 && this.d != (aVar = a.C0078a.WEEK)) {
            this.g.a(aVar);
            this.d = aVar;
            int i = this.c;
            MonthPager.c = i;
            Calendar v = this.b.get(i % 3);
            this.f = v.getSeedDate();
            this.e = v.getSelectedRowIndex();
            Calendar v1 = this.b.get(this.c % 3);
            v1.e(aVar);
            v1.d(this.f);
            v1.g(rowIndex);
            Calendar v2 = this.b.get((this.c - 1) % 3);
            v2.e(aVar);
            com.ldf.calendar.model.a last = this.f.modifyWeek(-1);
            a.b bVar = this.h;
            a.b bVar2 = a.b.Sunday;
            if (bVar == bVar2) {
                v2.d(com.ldf.calendar.a.h(last));
            } else {
                v2.d(com.ldf.calendar.a.i(last));
            }
            v2.g(rowIndex);
            Calendar v3 = this.b.get((this.c + 1) % 3);
            v3.e(aVar);
            com.ldf.calendar.model.a next = this.f.modifyWeek(1);
            if (this.h == bVar2) {
                v3.d(com.ldf.calendar.a.h(next));
            } else {
                v3.d(com.ldf.calendar.a.i(next));
            }
            v3.g(rowIndex);
        }
    }

    public void h(com.ldf.calendar.model.a date) {
        this.f = date;
        j(date);
        i();
    }

    public void g() {
        i();
    }

    private void i() {
        if (this.d == a.C0078a.WEEK) {
            int i = this.c;
            MonthPager.c = i;
            Calendar v1 = this.b.get(i % 3);
            v1.d(this.f);
            v1.g(this.e);
            Calendar v2 = this.b.get((this.c - 1) % 3);
            com.ldf.calendar.model.a last = this.f.modifyWeek(-1);
            a.b bVar = this.h;
            a.b bVar2 = a.b.Sunday;
            if (bVar == bVar2) {
                v2.d(com.ldf.calendar.a.h(last));
            } else {
                v2.d(com.ldf.calendar.a.i(last));
            }
            v2.g(this.e);
            Calendar v3 = this.b.get((this.c + 1) % 3);
            com.ldf.calendar.model.a next = this.f.modifyWeek(1);
            if (this.h == bVar2) {
                v3.d(com.ldf.calendar.a.h(next));
            } else {
                v3.d(com.ldf.calendar.a.i(next));
            }
            v3.g(this.e);
            return;
        }
        int i2 = this.c;
        MonthPager.c = i2;
        this.b.get(i2 % 3).d(this.f);
        com.ldf.calendar.model.a last2 = this.f.modifyMonth(-1);
        last2.setDay(1);
        this.b.get((this.c - 1) % 3).d(last2);
        com.ldf.calendar.model.a next2 = this.f.modifyMonth(1);
        next2.setDay(1);
        this.b.get((this.c + 1) % 3).d(next2);
    }

    public static void j(com.ldf.calendar.model.a calendarDate) {
        a = calendarDate;
    }

    public static com.ldf.calendar.model.a f() {
        return a;
    }

    public a.C0078a b() {
        return this.d;
    }

    public void k(com.ldf.calendar.interf.a dayRenderer) {
        this.b.get(0).setDayRenderer(dayRenderer);
        this.b.get(1).setDayRenderer(dayRenderer.copy());
        this.b.get(2).setDayRenderer(dayRenderer.copy());
    }

    public void m(b onCalendarTypeChangedListener) {
        this.g = onCalendarTypeChangedListener;
    }
}

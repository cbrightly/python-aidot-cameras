package com.ldf.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ldf.calendar.component.a;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import meshsdk.BaseResp;

/* compiled from: Utils */
public final class a {
    private static HashMap<String, String> a = new HashMap<>();
    private static int b;
    private static boolean c = false;

    public static int g(int year, int month) {
        if (month > 12) {
            month = 1;
            year++;
        } else if (month < 1) {
            month = 12;
            year--;
        }
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0 && year % 100 != 0) || year % BaseResp.ERR_MSG_SEND_FAIL == 0) {
            monthDays[1] = 29;
        }
        try {
            return monthDays[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    public static int k() {
        return Calendar.getInstance().get(1);
    }

    public static int f() {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int d() {
        return Calendar.getInstance().get(5);
    }

    public static int e(int year, int month, a.b type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(c(year, month));
        int week_index = cal.get(7) - 1;
        if (type == a.b.Sunday) {
            return week_index;
        }
        int week_index2 = cal.get(7) + 5;
        if (week_index2 >= 7) {
            return week_index2 - 7;
        }
        return week_index2;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static Date c(int year, int month) {
        Object obj;
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("-");
        if (month > 9) {
            obj = Integer.valueOf(month);
        } else {
            obj = "0" + month;
        }
        sb.append(obj);
        sb.append("-01");
        String dateString = sb.toString();
        Date date = new Date();
        try {
            return new SimpleDateFormat(TimeUtils.YYYY_MM_DD).parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return date;
        }
    }

    public static int b(Context context, float dpi) {
        return (int) ((context.getResources().getDisplayMetrics().density * dpi) + 0.5f);
    }

    public static HashMap<String, String> m() {
        return a;
    }

    public static void r(HashMap<String, String> data) {
        a = data;
    }

    private static int a(int offset, int min, int max) {
        if (offset > max) {
            return max;
        }
        if (offset < min) {
            return min;
        }
        return offset;
    }

    public static int p(View child, int dy, int minOffset, int maxOffset) {
        int initOffset = child.getTop();
        int offset = a(initOffset - dy, minOffset, maxOffset) - initOffset;
        child.offsetTopAndBottom(offset);
        return -offset;
    }

    public static int j(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public static com.ldf.calendar.model.a i(com.ldf.calendar.model.a seedDate) {
        Calendar c2 = Calendar.getInstance();
        String dateString = seedDate.toString();
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        c2.setTime(date);
        if (c2.get(7) != 1) {
            c2.add(5, (7 - c2.get(7)) + 1);
        }
        return new com.ldf.calendar.model.a(c2.get(1), c2.get(2) + 1, c2.get(5));
    }

    public static com.ldf.calendar.model.a h(com.ldf.calendar.model.a seedDate) {
        Calendar c2 = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(seedDate.toString());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        c2.setTime(date);
        c2.add(5, 7 - c2.get(7));
        return new com.ldf.calendar.model.a(c2.get(1), c2.get(2) + 1, c2.get(5));
    }

    public static boolean l() {
        return c;
    }

    public static void s(boolean customScrollToBottom) {
        c = customScrollToBottom;
    }

    public static void q(CoordinatorLayout parent, RecyclerView child, int y, int duration) {
        Scroller scroller = new Scroller(parent.getContext());
        int i = b;
        scroller.startScroll(0, i, 0, y - i, duration);
        ViewCompat.postOnAnimation(child, new C0077a(scroller, child, parent));
    }

    /* renamed from: com.ldf.calendar.a$a  reason: collision with other inner class name */
    /* compiled from: Utils */
    public static final class C0077a implements Runnable {
        final /* synthetic */ Scroller c;
        final /* synthetic */ RecyclerView d;
        final /* synthetic */ CoordinatorLayout f;

        C0077a(Scroller scroller, RecyclerView recyclerView, CoordinatorLayout coordinatorLayout) {
            this.c = scroller;
            this.d = recyclerView;
            this.f = coordinatorLayout;
        }

        public void run() {
            if (this.c.computeScrollOffset()) {
                this.d.offsetTopAndBottom(this.c.getCurrY() - this.d.getTop());
                a.o(this.d.getTop());
                this.f.dispatchDependentViewsChanged(this.d);
                ViewCompat.postOnAnimation(this.d, this);
            }
        }
    }

    public static void o(int y) {
        b = y;
    }

    public static int n() {
        return b;
    }
}

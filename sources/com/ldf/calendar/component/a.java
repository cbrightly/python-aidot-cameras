package com.ldf.calendar.component;

/* compiled from: CalendarAttr */
public class a {
    private b a;
    private C0078a b;
    private int c;
    private int d;

    /* renamed from: com.ldf.calendar.component.a$a  reason: collision with other inner class name */
    /* compiled from: CalendarAttr */
    public enum C0078a {
        WEEK,
        MONTH
    }

    /* compiled from: CalendarAttr */
    public enum b {
        Sunday,
        Monday
    }

    public b b() {
        return this.a;
    }

    public void f(b weekArrayType) {
        this.a = weekArrayType;
    }

    public C0078a a() {
        return this.b;
    }

    public void c(C0078a calendarType) {
        this.b = calendarType;
    }

    public void d(int cellHeight) {
        this.c = cellHeight;
    }

    public void e(int cellWidth) {
        this.d = cellWidth;
    }
}

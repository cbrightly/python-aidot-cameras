package com.ldf.calendar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import com.ldf.calendar.component.a;
import com.ldf.calendar.component.b;
import com.ldf.calendar.interf.c;

@SuppressLint({"ViewConstructor"})
public class Calendar extends View {
    private float a1 = 0.0f;
    private int c;
    private int d;
    private c f;
    private float p0;
    private float p1 = 0.0f;
    private Context q;
    private a x;
    private b y;
    private com.ldf.calendar.interf.b z;

    public Calendar(Context context, c onSelectDateListener, a attr) {
        super(context);
        this.f = onSelectDateListener;
        this.x = attr;
        b(context);
    }

    private void b(Context context) {
        this.q = context;
        this.p0 = (float) com.ldf.calendar.a.j(context);
        c();
    }

    private void c() {
        b bVar = new b(this, this.x, this.q);
        this.y = bVar;
        bVar.setOnSelectDateListener(this.f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.y.b(canvas);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        int i = h / 6;
        this.c = i;
        this.d = w / 7;
        this.x.d(i);
        this.x.e(this.d);
        this.y.l(this.x);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.a1 = event.getX();
                this.p1 = event.getY();
                return true;
            case 1:
                float disX = event.getX() - this.a1;
                float disY = event.getY() - this.p1;
                if (Math.abs(disX) >= this.p0 || Math.abs(disY) >= this.p0) {
                    return true;
                }
                this.z.b();
                this.y.j((int) (this.a1 / ((float) this.d)), (int) (this.p1 / ((float) this.c)));
                this.z.a();
                invalidate();
                return true;
            default:
                return true;
        }
    }

    public a.C0078a getCalendarType() {
        return this.x.a();
    }

    public void e(a.C0078a calendarType) {
        this.x.c(calendarType);
        this.y.l(this.x);
    }

    public int getCellHeight() {
        return this.c;
    }

    public int getSelectedRowIndex() {
        return this.y.f();
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.y.n(selectedRowIndex);
    }

    public void setOnAdapterSelectListener(com.ldf.calendar.interf.b onAdapterSelectListener) {
        this.z = onAdapterSelectListener;
    }

    public void d(com.ldf.calendar.model.a current) {
        this.y.o(current);
    }

    public void g(int rowCount) {
        this.y.q(rowCount);
        invalidate();
    }

    public void f() {
        this.y.p();
    }

    public void a() {
        this.y.a();
    }

    public com.ldf.calendar.model.a getSeedDate() {
        return this.y.e();
    }

    public void setDayRenderer(com.ldf.calendar.interf.a dayRenderer) {
        this.y.m(dayRenderer);
    }
}

package com.ldf.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.ldf.calendar.interf.a;

public abstract class DayView extends RelativeLayout implements a {
    protected Day c;
    protected Context d;
    protected int f;

    public DayView(Context context, int layoutResource) {
        super(context);
        setupLayoutResource(layoutResource);
        this.d = context;
        this.f = layoutResource;
    }

    private void setupLayoutResource(int layoutResource) {
        View inflated = LayoutInflater.from(getContext()).inflate(layoutResource, this);
        inflated.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        inflated.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void c() {
        measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void a(Canvas canvas, Day day) {
        this.c = day;
        c();
        int saveId = canvas.save();
        canvas.translate((float) b(canvas, day), (float) (day.c() * getMeasuredHeight()));
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    private int b(Canvas canvas, Day day) {
        int canvasWidth = canvas.getWidth() / 7;
        return (day.b() * canvasWidth) + ((canvasWidth - getMeasuredWidth()) / 2);
    }
}

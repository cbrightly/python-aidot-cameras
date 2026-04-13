package com.leedarson.newui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.ldf.calendar.component.c;
import com.ldf.calendar.model.a;
import com.ldf.calendar.view.DayView;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

@SuppressLint({"ViewConstructor"})
public class CustomDayView extends DayView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final a p0 = new a();
    private LDSTextView q = ((LDSTextView) findViewById(R$id.date));
    private ImageView x = ((ImageView) findViewById(R$id.maker));
    private View y = findViewById(R$id.selected_background);
    private View z = findViewById(R$id.today_background);

    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4949, new Class[0], Void.TYPE).isSupported) {
            f(this.c.a());
            e(this.c.a(), this.c.d());
            d(this.c.a(), this.c.d());
            super.c();
        }
    }

    private void d(a date, c state) {
        if (!PatchProxy.proxy(new Object[]{date, state}, this, changeQuickRedirect, false, 4950, new Class[]{a.class, c.class}, Void.TYPE).isSupported) {
            Log.d("renderMarker", "Utils.loadMarkData() = " + com.ldf.calendar.a.m().values() + ", state = " + state);
            if (!com.ldf.calendar.a.m().containsKey(date.toString())) {
                this.x.setVisibility(8);
            } else if (state == c.SELECT) {
                this.x.setVisibility(8);
            } else {
                this.x.setVisibility(0);
                if (com.ldf.calendar.a.m().get(date.toString()).equals("0")) {
                    this.x.setEnabled(true);
                } else {
                    this.x.setEnabled(false);
                }
            }
        }
    }

    private void e(a date, c state) {
        if (!PatchProxy.proxy(new Object[]{date, state}, this, changeQuickRedirect, false, 4951, new Class[]{a.class, c.class}, Void.TYPE).isSupported) {
            if (state == c.SELECT && !e.h(date, this.p0)) {
                this.y.setVisibility(0);
                this.q.setTextColor(getResources().getColor(R$color.bg_card_color));
            } else if (state == c.NEXT_MONTH || state == c.PAST_MONTH || e.h(date, this.p0)) {
                this.y.setVisibility(8);
                this.q.setTextColor(this.d.getResources().getColor(R$color.text_assist_color));
            } else {
                this.y.setVisibility(8);
                this.q.setTextColor(this.d.getResources().getColor(R$color.text_primary_color));
                f(date);
            }
        }
    }

    private void f(a date) {
        if (!PatchProxy.proxy(new Object[]{date}, this, changeQuickRedirect, false, 4952, new Class[]{a.class}, Void.TYPE).isSupported) {
            if (date != null) {
                LDSTextView lDSTextView = this.q;
                lDSTextView.setText(date.day + "");
                if (date.equals(this.p0)) {
                    this.q.setTextColor(getResources().getColor(R$color.second_color));
                } else if (e.h(date, this.p0)) {
                    this.q.setTextColor(getResources().getColor(R$color.text_assist_color));
                } else {
                    this.q.setTextColor(getResources().getColor(R$color.text_primary_color));
                }
            }
        }
    }

    public com.ldf.calendar.interf.a copy() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4953, new Class[0], com.ldf.calendar.interf.a.class);
        if (proxy.isSupported) {
            return (com.ldf.calendar.interf.a) proxy.result;
        }
        return new CustomDayView(this.d, this.f);
    }
}

package com.leedarson.newui.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.ldf.calendar.component.c;
import com.ldf.calendar.model.a;
import com.ldf.calendar.view.DayView;
import com.leedarson.R$id;
import com.leedarson.base.views.common.LDSTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ThemeDayView extends DayView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final a p0 = new a();
    private LDSTextView q = ((LDSTextView) findViewById(R$id.date));
    private ImageView x = ((ImageView) findViewById(R$id.maker));
    private View y;
    private View z;

    public ThemeDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        int i = R$id.selected_background;
        this.y = findViewById(i);
        this.y = findViewById(i);
        this.z = findViewById(R$id.today_background);
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5279, new Class[0], Void.TYPE).isSupported) {
            a date = this.c.a();
            c state = this.c.d();
            if (date != null) {
                if (date.equals(this.p0)) {
                    this.q.setText("今");
                    this.z.setVisibility(0);
                } else {
                    LDSTextView lDSTextView = this.q;
                    lDSTextView.setText(date.day + "");
                    this.z.setVisibility(8);
                }
            }
            if (state == c.SELECT) {
                this.y.setVisibility(0);
            } else {
                this.y.setVisibility(8);
            }
            super.c();
        }
    }

    public com.ldf.calendar.interf.a copy() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5281, new Class[0], com.ldf.calendar.interf.a.class);
        if (proxy.isSupported) {
            return (com.ldf.calendar.interf.a) proxy.result;
        }
        return new ThemeDayView(this.d, this.f);
    }
}

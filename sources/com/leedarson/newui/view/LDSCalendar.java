package com.leedarson.newui.view;

import android.view.MotionEvent;
import com.ldf.calendar.view.Calendar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LDSCalendar extends Calendar {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a2;

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5023, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.a2) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    private void setIsClickable(boolean isClickable) {
        this.a2 = isClickable;
    }
}

package com.leedarson.utils;

import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.leedarson.R$id;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: AntiShakeUtils */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean a(@NonNull View target, @IntRange(from = 0) long internalTime) {
        boolean z = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{target, new Long(internalTime)}, (Object) null, changeQuickRedirect, true, 11456, new Class[]{View.class, Long.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        long curTimeStamp = System.currentTimeMillis();
        int i = R$id.last_click_time;
        Object o = target.getTag(i);
        if (o == null) {
            target.setTag(i, Long.valueOf(curTimeStamp));
            return false;
        }
        if (curTimeStamp - ((Long) o).longValue() < internalTime) {
            z = true;
        }
        boolean isInvalid = z;
        if (!isInvalid) {
            target.setTag(i, Long.valueOf(curTimeStamp));
        }
        return isInvalid;
    }
}

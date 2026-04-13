package com.leedarson.base.utils;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: LdsThreadFactory */
public class r implements ThreadFactory {
    public static ChangeQuickRedirect changeQuickRedirect;
    String c;
    AtomicInteger d = new AtomicInteger(0);

    public r(String name) {
        this.c = name;
    }

    public Thread newThread(Runnable r) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{r}, this, changeQuickRedirect, false, TypedValues.PositionType.TYPE_POSITION_TYPE, new Class[]{Runnable.class}, Thread.class);
        if (proxy.isSupported) {
            return (Thread) proxy.result;
        }
        String threadName = this.c + ":" + this.d.incrementAndGet();
        Log.d("LdsThreadFactory", "newThread: " + threadName);
        return new Thread(r, threadName);
    }
}

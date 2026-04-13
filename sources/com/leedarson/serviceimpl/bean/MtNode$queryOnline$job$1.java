package com.leedarson.serviceimpl.bean;

import android.os.Handler;
import com.leedarson.serviceimpl.listener.b;
import com.leedarson.serviceimpl.listener.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.jetbrains.annotations.Nullable;

/* compiled from: MtNode.kt */
public final class MtNode$queryOnline$job$1 implements b {
    public static ChangeQuickRedirect changeQuickRedirect;
    final /* synthetic */ MtNode this$0;

    MtNode$queryOnline$job$1(MtNode $receiver) {
        this.this$0 = $receiver;
    }

    public void onSuccess(long j, @Nullable Object obj) {
        Object[] objArr = {new Long(j), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6191, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
            c globalCallback = this.this$0.getGlobalCallback();
            if (globalCallback != null) {
                globalCallback.a(this.this$0.getMatterAddr(), 1);
            }
            Handler handler = this.this$0.getHandler();
            if (handler != null) {
                handler.removeCallbacks(this.this$0.getOfflineTask());
            }
            Handler handler2 = this.this$0.getHandler();
            if (handler2 != null) {
                handler2.postDelayed(this.this$0.getOfflineTask(), this.this$0.getOFFLINE_TIMEOUT());
            }
            if (!this.this$0.getHasSubscrbe()) {
                this.this$0.subscribe();
            }
        }
    }

    public void onFail(int code, @Nullable Exception ex) {
    }
}

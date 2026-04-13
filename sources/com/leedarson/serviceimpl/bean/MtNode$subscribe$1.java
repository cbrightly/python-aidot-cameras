package com.leedarson.serviceimpl.bean;

import com.leedarson.serviceimpl.listener.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.jetbrains.annotations.Nullable;

/* compiled from: MtNode.kt */
public final class MtNode$subscribe$1 implements b {
    public static ChangeQuickRedirect changeQuickRedirect;
    final /* synthetic */ MtNode this$0;

    MtNode$subscribe$1(MtNode $receiver) {
        this.this$0 = $receiver;
    }

    public void onSuccess(long j, @Nullable Object obj) {
        Object[] objArr = {new Long(j), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6194, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
            this.this$0.setHasSubscrbe(true);
        }
    }

    public void onFail(int code, @Nullable Exception ex) {
    }
}

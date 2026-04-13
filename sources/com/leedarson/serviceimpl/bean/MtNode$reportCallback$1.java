package com.leedarson.serviceimpl.bean;

import chip.devicecontroller.ReportCallback;
import chip.devicecontroller.model.ChipAttributePath;
import chip.devicecontroller.model.NodeState;
import chip.devicecontroller.n12;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MtNode.kt */
public final class MtNode$reportCallback$1 implements ReportCallback {
    public static ChangeQuickRedirect changeQuickRedirect;
    final /* synthetic */ MtNode this$0;

    public /* synthetic */ void onDone() {
        n12.a(this);
    }

    MtNode$reportCallback$1(MtNode $receiver) {
        this.this$0 = $receiver;
    }

    public void onError(@Nullable ChipAttributePath chipAttributePath, @NotNull Exception ex) {
        Class[] clsArr = {ChipAttributePath.class, Exception.class};
        if (!PatchProxy.proxy(new Object[]{chipAttributePath, ex}, this, changeQuickRedirect, false, 6192, clsArr, Void.TYPE).isSupported) {
            k.e(ex, "ex");
            com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("Report error for : ", ex), (String) null, 2, (Object) null);
        }
    }

    public void onReport(@NotNull NodeState nodeState) {
        if (!PatchProxy.proxy(new Object[]{nodeState}, this, changeQuickRedirect, false, 6193, new Class[]{NodeState.class}, Void.TYPE).isSupported) {
            k.e(nodeState, "nodeState");
            com.leedarson.serviceimpl.k.m(com.leedarson.serviceimpl.k.a, k.l("Received wildcard report\n:", MtNode.access$nodeStateToDebugString(this.this$0, nodeState)), (String) null, 2, (Object) null);
            MtNode.access$parseAttr(this.this$0, nodeState);
        }
    }
}

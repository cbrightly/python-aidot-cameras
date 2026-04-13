package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MatterInfo.kt */
public final class NodeInfo {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private String fabricId;
    private int nodeId;

    public NodeInfo(@NotNull String fid, int nid) {
        k.e(fid, "fid");
        this.fabricId = fid;
        this.nodeId = nid;
    }

    @NotNull
    public final String getFabricId() {
        return this.fabricId;
    }

    public final void setFabricId(@NotNull String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6195, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(str, "<set-?>");
            this.fabricId = str;
        }
    }

    public final int getNodeId() {
        return this.nodeId;
    }

    public final void setNodeId(int i) {
        this.nodeId = i;
    }
}

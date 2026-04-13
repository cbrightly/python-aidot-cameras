package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterInfo.kt */
public final class MatterInfoBean {
    @NotNull
    public static final MatterInfoBean INSTANCE = new MatterInfoBean();
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private static String fabricId = "";
    @Nullable
    private static String houseId = "";
    @NotNull
    private static List<MatterDeviceBean> nodes = new ArrayList();

    private MatterInfoBean() {
    }

    @Nullable
    public final String getFabricId() {
        return fabricId;
    }

    public final void setFabricId(@Nullable String str) {
        fabricId = str;
    }

    @Nullable
    public final String getHouseId() {
        return houseId;
    }

    public final void setHouseId(@Nullable String str) {
        houseId = str;
    }

    @NotNull
    public final List<MatterDeviceBean> getNodes() {
        return nodes;
    }

    public final void setNodes(@NotNull List<MatterDeviceBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 6178, new Class[]{List.class}, Void.TYPE).isSupported) {
            k.e(list, "<set-?>");
            nodes = list;
        }
    }
}

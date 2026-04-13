package com.leedarson.newui.multiview.utils;

import com.leedarson.bean.IpcDeviceBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BeanUtils.kt */
public final class BeanUtilsKt {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static final int getItemType(@NotNull IpcDeviceBean $this$getItemType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this$getItemType}, (Object) null, changeQuickRedirect, true, 4262, new Class[]{IpcDeviceBean.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        k.e($this$getItemType, "<this>");
        String str = $this$getItemType.props.liveType;
        if (str == null || str.equals("0")) {
            return 1;
        }
        return 2;
    }

    public static final boolean isAllowAutoPullStream(@NotNull IpcDeviceBean $this$isAllowAutoPullStream) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this$isAllowAutoPullStream}, (Object) null, changeQuickRedirect, true, 4263, new Class[]{IpcDeviceBean.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        k.e($this$isAllowAutoPullStream, "<this>");
        return $this$isAllowAutoPullStream.props.TurnOnOff && !$this$isAllowAutoPullStream.isCriticalBattery();
    }
}

package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.g;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeviceInfoAction.kt */
public final class DeviceInfoAction {
    public static final DeviceInfoAction INSTANCE = new DeviceInfoAction();

    private DeviceInfoAction() {
    }

    @NotNull
    public final Map<String, Object> deviceInfoRes() {
        Map linkedHashMap = new LinkedHashMap();
        Map $this$apply = linkedHashMap;
        $this$apply.put("code", 200);
        Map linkedHashMap2 = new LinkedHashMap();
        Map $this$apply2 = linkedHashMap2;
        $this$apply2.put("deviceName", g.i() + '-' + g.j());
        String m = g.m();
        k.b(m, "DeviceUtils.getUniqueDeviceId()");
        $this$apply2.put("deviceId", m);
        $this$apply2.put("deviceIp", NetworkUtils.c() + ':' + DokitConstant.INSTANCE.getFILE_MANAGER_HTTP_PORT());
        $this$apply.put("data", linkedHashMap2);
        return linkedHashMap;
    }
}

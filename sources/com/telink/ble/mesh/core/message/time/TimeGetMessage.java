package com.telink.ble.mesh.core.message.time;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.lighting.LightingMessage;

public class TimeGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static TimeGetMessage I(int destinationAddress, int appKeyIndex, int rspMax) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12792, new Class[]{cls, cls, cls}, TimeGetMessage.class);
        if (proxy.isSupported) {
            return (TimeGetMessage) proxy.result;
        }
        TimeGetMessage message = new TimeGetMessage(destinationAddress, appKeyIndex);
        message.C(rspMax);
        return message;
    }

    public TimeGetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    public int k() {
        return Opcode.TIME_GET.value;
    }

    public int o() {
        return Opcode.TIME_STATUS.value;
    }
}

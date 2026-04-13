package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class CtlGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static CtlGetMessage I(int destinationAddress, int appKeyIndex, int rspMax) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12685, new Class[]{cls, cls, cls}, CtlGetMessage.class);
        if (proxy.isSupported) {
            return (CtlGetMessage) proxy.result;
        }
        CtlGetMessage message = new CtlGetMessage(destinationAddress, appKeyIndex);
        message.C(rspMax);
        return message;
    }

    public CtlGetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        e(1);
    }

    public int k() {
        return Opcode.LIGHT_CTL_GET.value;
    }

    public int o() {
        return Opcode.LIGHT_CTL_STATUS.value;
    }
}

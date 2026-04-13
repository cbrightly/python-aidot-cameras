package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class HslGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static HslGetMessage I(int destinationAddress, int appKeyIndex, int rspMax) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12704, new Class[]{cls, cls, cls}, HslGetMessage.class);
        if (proxy.isSupported) {
            return (HslGetMessage) proxy.result;
        }
        HslGetMessage message = new HslGetMessage(destinationAddress, appKeyIndex);
        message.C(rspMax);
        return message;
    }

    public HslGetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        e(1);
    }

    public int k() {
        return Opcode.LIGHT_HSL_GET.value;
    }

    public int o() {
        return Opcode.LIGHT_HSL_STATUS.value;
    }
}

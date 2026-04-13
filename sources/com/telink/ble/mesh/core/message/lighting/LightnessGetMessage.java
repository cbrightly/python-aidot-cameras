package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class LightnessGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static LightnessGetMessage I(int destinationAddress, int appKeyIndex, int rspMax) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12719, new Class[]{cls, cls, cls}, LightnessGetMessage.class);
        if (proxy.isSupported) {
            return (LightnessGetMessage) proxy.result;
        }
        LightnessGetMessage message = new LightnessGetMessage(destinationAddress, appKeyIndex);
        message.C(rspMax);
        return message;
    }

    public LightnessGetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        e(1);
    }

    public int k() {
        return Opcode.LIGHTNESS_GET.value;
    }

    public int o() {
        return Opcode.LIGHTNESS_STATUS.value;
    }
}

package com.telink.ble.mesh.core.message.generic;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class OnOffGetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static OnOffGetMessage I(int destinationAddress, int appKeyIndex, int rspMax) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(appKeyIndex), new Integer(rspMax)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12678, new Class[]{cls, cls, cls}, OnOffGetMessage.class);
        if (proxy.isSupported) {
            return (OnOffGetMessage) proxy.result;
        }
        OnOffGetMessage message = new OnOffGetMessage(destinationAddress, appKeyIndex);
        message.C(rspMax);
        return message;
    }

    public OnOffGetMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
        e(1);
    }

    public int k() {
        return Opcode.G_ONOFF_GET.value;
    }

    public int o() {
        return Opcode.G_ONOFF_STATUS.value;
    }

    public byte[] l() {
        return null;
    }
}

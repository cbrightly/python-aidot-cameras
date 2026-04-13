package com.telink.ble.mesh.core.message.rp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.Opcode;

public class LinkCloseMessage extends RemoteProvisionMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;

    public static LinkCloseMessage I(int destinationAddress, int rspMax, byte reason) {
        Object[] objArr = {new Integer(destinationAddress), new Integer(rspMax), new Byte(reason)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12727, new Class[]{cls, cls, Byte.TYPE}, LinkCloseMessage.class);
        if (proxy.isSupported) {
            return (LinkCloseMessage) proxy.result;
        }
        LinkCloseMessage message = new LinkCloseMessage(destinationAddress);
        message.C(rspMax);
        message.t = reason;
        return message;
    }

    public LinkCloseMessage(int destinationAddress) {
        super(destinationAddress);
    }

    public int k() {
        return Opcode.REMOTE_PROV_LINK_CLOSE.value;
    }

    public int o() {
        return Opcode.REMOTE_PROV_LINK_STS.value;
    }

    public byte[] l() {
        return new byte[]{this.t};
    }
}

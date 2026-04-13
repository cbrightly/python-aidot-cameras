package com.leedarson.serviceimpl.ble.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: HeadUtils */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] a(byte b0, byte b1, byte b, byte b2, byte b4, byte b5, byte[] bArr) {
        Object[] objArr = {new Byte(b0), new Byte(b1), new Byte(b), new Byte(b2), new Byte(b4), new Byte(b5), bArr};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Byte.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6324, new Class[]{cls, cls, cls, cls, cls, cls, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte b3 = b;
        byte[] payload = bArr;
        byte b32 = b2;
        byte[] head = new byte[(payload.length + 6)];
        head[0] = b0;
        head[1] = b1;
        head[2] = b32;
        head[3] = b32;
        head[4] = b4;
        head[5] = b5;
        System.arraycopy(payload, 0, head, 6, payload.length);
        return head;
    }
}

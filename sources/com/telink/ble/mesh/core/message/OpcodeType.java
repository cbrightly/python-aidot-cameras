package com.telink.ble.mesh.core.message;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;

public enum OpcodeType {
    SIG_1(1),
    SIG_2(2),
    VENDOR(3);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int length;

    private OpcodeType(int length2) {
        this.length = length2;
    }

    public static OpcodeType getByFirstByte(byte opFst) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(opFst)}, (Object) null, changeQuickRedirect, true, 12440, new Class[]{Byte.TYPE}, OpcodeType.class);
        if (proxy.isSupported) {
            return (OpcodeType) proxy.result;
        }
        if ((MeshUtils.a(7) & opFst) != 0) {
            return (MeshUtils.a(6) & opFst) != 0 ? VENDOR : SIG_2;
        }
        return SIG_1;
    }
}

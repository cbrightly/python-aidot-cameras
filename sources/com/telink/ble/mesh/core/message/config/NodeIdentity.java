package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum NodeIdentity {
    STOPPED(0, "Node Identity for a subnet is stopped"),
    RUNNING(1, "Node Identity for a subnet is running"),
    UNSUPPORTED(2, "Node Identity is not supported"),
    UNKNOWN_ERROR(255, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private NodeIdentity(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static NodeIdentity valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12508, new Class[]{Integer.TYPE}, NodeIdentity.class);
        if (proxy.isSupported) {
            return (NodeIdentity) proxy.result;
        }
        for (NodeIdentity status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum AdditionalInformation {
    No_changes(0, "No changes to node composition data"),
    CHANGES_1(1, "Node composition data changed. The node does not support remote provisioning."),
    CHANGES_2(2, "Node composition data changed, and remote provisioning is supported. The node supports remote provisioning and composition data page 0x80. Page 0x80 contains different composition data than page 0x0."),
    CHANGES_3(3, "Node unprovisioned. The node is unprovisioned after successful application of a verified firmware image."),
    UNKNOWN_ERROR(255, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private AdditionalInformation(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static AdditionalInformation valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12588, new Class[]{Integer.TYPE}, AdditionalInformation.class);
        if (proxy.isSupported) {
            return (AdditionalInformation) proxy.result;
        }
        for (AdditionalInformation status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

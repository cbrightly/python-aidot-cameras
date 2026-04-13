package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum TransferMode {
    NONE(0, "No Active Transfer"),
    PUSH(1, "Push BLOB Transfer Mode"),
    PULL(2, "Pull BLOB Transfer Mode"),
    Prohibited(3, "Prohibited");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final String desc;
    public final int mode;

    private TransferMode(int mode2, String desc2) {
        this.mode = mode2;
        this.desc = desc2;
    }

    public static TransferMode valueOf(int mode2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(mode2)}, (Object) null, changeQuickRedirect, true, 12663, new Class[]{Integer.TYPE}, TransferMode.class);
        if (proxy.isSupported) {
            return (TransferMode) proxy.result;
        }
        for (TransferMode status : values()) {
            if (status.mode == mode2) {
                return status;
            }
        }
        return Prohibited;
    }
}

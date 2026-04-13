package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum UpdatePhase {
    IDLE(0, "Ready to start a Receive Firmware procedure"),
    TRANSFER_ERROR(1, "The Transfer BLOB procedure failed."),
    TRANSFER_ACTIVE(2, "The Receive Firmware procedure is being executed"),
    VERIFICATION_ACTIVE(3, "The Verify Firmware procedure is being executed"),
    VERIFICATION_SUCCESS(4, "The Verify Firmware procedure completed successfully."),
    VERIFICATION_FAILED(5, "The Verify Firmware procedure failed."),
    APPLYING_UPDATE(6, "The Apply New Firmware procedure is being executed."),
    UNKNOWN_ERROR(255, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private UpdatePhase(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static UpdatePhase valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12623, new Class[]{Integer.TYPE}, UpdatePhase.class);
        if (proxy.isSupported) {
            return (UpdatePhase) proxy.result;
        }
        for (UpdatePhase status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

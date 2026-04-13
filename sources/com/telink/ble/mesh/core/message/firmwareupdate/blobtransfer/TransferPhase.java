package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum TransferPhase {
    INACTIVE(0, "The BLOB Transfer Server is awaiting configuration and cannot receive a BLOB."),
    WAITING_FOR_TRANSFER_START(1, "The BLOB Transfer Server is ready to receive the BLOB identified by the Expected BLOB ID."),
    WAITING_FOR_NEXT_BLOCK(2, "The BLOB Transfer Server is waiting for the next block of data."),
    WAITING_FOR_NEXT_CHUNK(3, "The BLOB Transfer Server is waiting for the next chunk of data."),
    COMPLETE(4, "The BLOB was transferred successfully."),
    SUSPENDED(5, "The Initialize and Receive BLOB procedure is paused."),
    UNKNOWN_ERROR(10, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private TransferPhase(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static TransferPhase valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12666, new Class[]{Integer.TYPE}, TransferPhase.class);
        if (proxy.isSupported) {
            return (TransferPhase) proxy.result;
        }
        for (TransferPhase status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

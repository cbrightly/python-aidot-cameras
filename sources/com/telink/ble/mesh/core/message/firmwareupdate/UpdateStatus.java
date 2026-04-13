package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum UpdateStatus {
    SUCCESS(0, "The message was processed successfully"),
    METADATA_CHECK_FAILED(1, "The metadata check failed"),
    INVALID_FIRMWARE_ID(2, "The message contains a Firmware ID value that is not expected"),
    OUT_OF_RESOURCES(3, "Insufficient resources on the node"),
    BLOB_TRANSFER_BUSY(4, "Another BLOB transfer is in progress"),
    INVALID_COMMAND(5, "The operation cannot be performed while the server is in the current phase"),
    TEMPORARILY_UNAVAILABLE(6, "The server cannot start a firmware update"),
    INTERNAL_ERROR(7, "An internal error occurred on the node"),
    UNKNOWN_ERROR(255, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private UpdateStatus(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static UpdateStatus valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12626, new Class[]{Integer.TYPE}, UpdateStatus.class);
        if (proxy.isSupported) {
            return (UpdateStatus) proxy.result;
        }
        for (UpdateStatus status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

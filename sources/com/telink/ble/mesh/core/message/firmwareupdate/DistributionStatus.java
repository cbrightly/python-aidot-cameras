package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum DistributionStatus {
    SUCCESS(0, "The message was processed successfully"),
    OUT_OF_RESOURCES(1, "Insufficient resources on the node"),
    INVALID_APPKEY_INDEX(2, "The AppKey identified by the AppKey Index is not known to the node"),
    NODES_LIST_EMPTY(3, "There are no Updating nodes in the Nodes List state"),
    INVALID_PHASE(4, "The operation cannot be performed while the server is in the current phase"),
    FIRMWARE_NOT_FOUND(5, "The requested firmware image is not stored on the Distributor"),
    BUSY_WITH_TRANSFER(6, "Another upload is in progress"),
    URI_NOT_SUPPORTED(7, "The URI scheme name indicated by the Update URI is not supported"),
    URI_MALFORMED(8, "The format of the Update URI is invalid"),
    DISTRIBUTOR_BUSY(9, "Another firmware image distribution is in progress"),
    INTERNAL_ERROR(10, "An internal error occurred on the node"),
    UNKNOWN_ERROR(255, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private DistributionStatus(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static DistributionStatus valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12591, new Class[]{Integer.TYPE}, DistributionStatus.class);
        if (proxy.isSupported) {
            return (DistributionStatus) proxy.result;
        }
        for (DistributionStatus status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum TransferStatus {
    SUCCESS(0, "The message was processed successfully"),
    INVALID_BLOCK_NUMBER(1, "The Block Number field value is not within the range of blocks being transferred"),
    INVALID_BLOCK_SIZE(2, "The block size is smaller than the size indicated by the Min Block Size Log state or is larger than the size indicated by the Max Block Size Log state."),
    INVALID_CHUNK_SIZE(3, " The chunk size exceeds the size indicated by the Max Chunk Size state, or the number of chunks exceeds the number specified by the Max Total Chunks state."),
    INVALID_STATE(4, "The operation cannot be performed while the server is in the current phase."),
    INVALID_PARAMETER(5, "A parameter value in the message cannot be accepted."),
    WRONG_BLOB_ID(6, "The message contains a BLOB ID value that is not expected."),
    BLOB_TOO_LARGE(7, "There is not enough space available in memory to receive the BLOB."),
    UNSUPPORTED_TRANSFER_MODE(8, "The transfer mode is not supported by the BLOB Transfer Server model."),
    INTERNAL_ERROR(9, "An internal error occurred on the node"),
    UNKNOWN_ERROR(10, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private TransferStatus(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static TransferStatus valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12669, new Class[]{Integer.TYPE}, TransferStatus.class);
        if (proxy.isSupported) {
            return (TransferStatus) proxy.result;
        }
        for (TransferStatus status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}

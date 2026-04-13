package com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;

public class BlobTransferGetMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.BLOB_TRANSFER_GET.value;
    }

    public int o() {
        return Opcode.BLOB_TRANSFER_STATUS.value;
    }
}

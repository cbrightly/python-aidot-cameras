package com.telink.ble.mesh.core.message.firmwaredistribution;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;

public class FDFirmwareDeleteMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.FD_FIRMWARE_DELETE.value;
    }

    public int o() {
        return Opcode.FD_UPLOAD_STATUS.value;
    }
}

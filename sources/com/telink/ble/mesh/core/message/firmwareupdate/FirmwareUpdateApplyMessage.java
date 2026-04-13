package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class FirmwareUpdateApplyMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.FIRMWARE_UPDATE_APPLY.value;
    }

    public int o() {
        return Opcode.FIRMWARE_UPDATE_STATUS.value;
    }
}

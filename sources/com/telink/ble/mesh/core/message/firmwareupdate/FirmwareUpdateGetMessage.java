package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class FirmwareUpdateGetMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.FIRMWARE_UPDATE_GET.value;
    }

    public int o() {
        return Opcode.FIRMWARE_UPDATE_STATUS.value;
    }
}

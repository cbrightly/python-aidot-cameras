package com.telink.ble.mesh.core.message.firmwareupdate;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class FirmwareUpdateInfoGetMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;
    private byte u;

    public int k() {
        return Opcode.FIRMWARE_UPDATE_INFORMATION_GET.value;
    }

    public int o() {
        return Opcode.FIRMWARE_UPDATE_INFORMATION_STATUS.value;
    }

    public byte[] l() {
        return new byte[]{this.t, this.u};
    }
}

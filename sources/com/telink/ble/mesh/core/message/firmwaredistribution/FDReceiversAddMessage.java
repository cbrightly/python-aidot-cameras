package com.telink.ble.mesh.core.message.firmwaredistribution;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;

public class FDReceiversAddMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static class ReceiverEntry {
    }

    public int k() {
        return Opcode.FD_RECEIVERS_ADD.value;
    }

    public int o() {
        return Opcode.FD_RECEIVERS_STATUS.value;
    }
}

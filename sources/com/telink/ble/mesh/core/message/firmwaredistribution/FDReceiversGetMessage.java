package com.telink.ble.mesh.core.message.firmwaredistribution;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatingMessage;

public class FDReceiversGetMessage extends UpdatingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.FD_RECEIVERS_GET.value;
    }

    public int o() {
        return Opcode.FD_RECEIVERS_LIST.value;
    }
}

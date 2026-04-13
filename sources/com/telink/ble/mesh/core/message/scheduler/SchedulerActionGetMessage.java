package com.telink.ble.mesh.core.message.scheduler;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;

public class SchedulerActionGetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte t;

    public int o() {
        return Opcode.SCHD_ACTION_STATUS.value;
    }

    public int k() {
        return Opcode.SCHD_ACTION_GET.value;
    }

    public byte[] l() {
        return new byte[]{this.t};
    }
}

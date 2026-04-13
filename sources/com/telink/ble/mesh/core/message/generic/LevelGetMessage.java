package com.telink.ble.mesh.core.message.generic;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class LevelGetMessage extends GenericMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.G_LEVEL_GET.value;
    }

    public byte[] l() {
        return null;
    }
}

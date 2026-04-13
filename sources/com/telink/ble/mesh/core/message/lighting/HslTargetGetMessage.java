package com.telink.ble.mesh.core.message.lighting;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;

public class HslTargetGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.LIGHT_HSL_TARGET_GET.value;
    }

    public int o() {
        return Opcode.LIGHT_HSL_TARGET_STATUS.value;
    }
}

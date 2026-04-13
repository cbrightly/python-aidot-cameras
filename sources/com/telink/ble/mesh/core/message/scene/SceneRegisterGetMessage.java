package com.telink.ble.mesh.core.message.scene;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.lighting.LightingMessage;

public class SceneRegisterGetMessage extends LightingMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public int k() {
        return Opcode.SCENE_REG_GET.value;
    }

    public int o() {
        return Opcode.SCENE_REG_STATUS.value;
    }
}

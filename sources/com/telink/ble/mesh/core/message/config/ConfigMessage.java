package com.telink.ble.mesh.core.message.config;

import androidx.annotation.IntRange;
import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.networking.AccessType;

public abstract class ConfigMessage extends MeshMessage {
    public static ChangeQuickRedirect changeQuickRedirect;

    public ConfigMessage(@IntRange(from = 1, to = 32767) int destinationAddress) {
        this.i = destinationAddress;
        this.m = 1;
    }

    public AccessType g() {
        return AccessType.DEVICE;
    }
}

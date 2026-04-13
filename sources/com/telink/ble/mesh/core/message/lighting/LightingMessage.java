package com.telink.ble.mesh.core.message.lighting;

import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.networking.AccessType;

public abstract class LightingMessage extends MeshMessage {
    public LightingMessage(int destinationAddress, int appKeyIndex) {
        this.i = destinationAddress;
        this.g = appKeyIndex;
        this.f = AccessType.APPLICATION;
    }
}

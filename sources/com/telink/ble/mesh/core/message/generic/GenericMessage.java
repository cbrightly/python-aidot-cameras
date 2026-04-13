package com.telink.ble.mesh.core.message.generic;

import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.networking.AccessType;

public abstract class GenericMessage extends MeshMessage {
    public GenericMessage(int destinationAddress, int appKeyIndex) {
        this.i = destinationAddress;
        this.g = appKeyIndex;
        this.f = AccessType.APPLICATION;
    }
}

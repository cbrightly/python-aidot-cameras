package com.telink.ble.mesh.core.message.firmwareupdate;

import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.networking.AccessType;

public abstract class UpdatingMessage extends MeshMessage {
    public UpdatingMessage(int destinationAddress, int appKeyIndex) {
        this.i = destinationAddress;
        this.g = appKeyIndex;
        this.f = AccessType.APPLICATION;
    }
}

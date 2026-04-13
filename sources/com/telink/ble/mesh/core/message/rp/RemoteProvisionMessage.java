package com.telink.ble.mesh.core.message.rp;

import com.telink.ble.mesh.core.message.config.ConfigMessage;

public class RemoteProvisionMessage extends ConfigMessage {
    public RemoteProvisionMessage(int destinationAddress) {
        super(destinationAddress);
    }
}

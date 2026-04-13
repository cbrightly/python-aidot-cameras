package com.telink.ble.mesh.core.provisioning.pdu;

public interface ProvisioningStatePDU extends PDU {
    byte getState();
}

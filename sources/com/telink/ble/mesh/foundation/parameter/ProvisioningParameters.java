package com.telink.ble.mesh.foundation.parameter;

import com.telink.ble.mesh.core.ble.UUIDInfo;
import com.telink.ble.mesh.entity.ProvisioningDevice;

public class ProvisioningParameters extends Parameters {
    public ProvisioningParameters(ProvisioningDevice device) {
        h(a(UUIDInfo.d));
        e("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET", device);
    }
}

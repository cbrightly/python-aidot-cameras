package com.telink.ble.mesh.foundation.parameter;

import com.telink.ble.mesh.core.ble.UUIDInfo;
import com.telink.ble.mesh.entity.BindingDevice;

public class BindingParameters extends Parameters {
    public BindingParameters(BindingDevice device) {
        h(a(UUIDInfo.g));
        e("com.telink.ble.com.telink.ble.mesh.light.ACTION_BINDING_TARGET", device);
    }
}

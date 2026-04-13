package com.telink.ble.mesh.foundation.parameter;

import com.telink.ble.mesh.core.ble.UUIDInfo;

public class AutoConnectParameters extends Parameters {
    public boolean b = true;

    public AutoConnectParameters() {
        h(a(UUIDInfo.g));
    }
}

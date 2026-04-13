package com.telink.ble.mesh.core.provisioning;

import com.meituan.robust.ChangeQuickRedirect;

public enum AuthenticationMethod {
    NoOOB((byte) 0),
    StaticOOB((byte) 1);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final byte value;

    private AuthenticationMethod(byte value2) {
        this.value = value2;
    }
}

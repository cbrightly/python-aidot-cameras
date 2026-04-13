package com.telink.ble.mesh.core.access;

import com.meituan.robust.ChangeQuickRedirect;

public enum BindingBearer {
    GattOnly,
    Any,
    Flex;
    
    public static ChangeQuickRedirect changeQuickRedirect;
}

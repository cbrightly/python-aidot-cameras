package com.telink.ble.mesh.foundation.parameter;

import com.meituan.robust.ChangeQuickRedirect;

public enum AutoConnectFilterType {
    NODE_IDENTITY(true, false),
    NETWORK_ID(false, true),
    AUTO(true, true);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final boolean isNetworkIdSupport;
    public final boolean isNodeIdentitySupport;

    private AutoConnectFilterType(boolean isNodeIdentitySupport2, boolean isNetworkIdSupport2) {
        this.isNodeIdentitySupport = isNodeIdentitySupport2;
        this.isNetworkIdSupport = isNetworkIdSupport2;
    }
}

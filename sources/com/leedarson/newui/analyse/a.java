package com.leedarson.newui.analyse;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: LiveFailAnalyse.kt */
public enum a {
    AppNetworkNoProblem,
    AppNetworkUnconnected,
    AppNetworkUnavailable,
    AppNetworkNoReason,
    DeviceNetworkNoProblem,
    DeviceOffline,
    DeviceRssiLow,
    DeviceNetworkNoReason,
    DeviceP2PNoReason,
    PingNoProblem,
    PingError,
    IperfNoProblem;
    
    public static ChangeQuickRedirect changeQuickRedirect;
}

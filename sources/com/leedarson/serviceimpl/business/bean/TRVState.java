package com.leedarson.serviceimpl.business.bean;

import com.meituan.robust.ChangeQuickRedirect;

public enum TRVState {
    IDLE,
    PROVISION,
    AUTO_CONNECT,
    OTA;
    
    public static ChangeQuickRedirect changeQuickRedirect;
}

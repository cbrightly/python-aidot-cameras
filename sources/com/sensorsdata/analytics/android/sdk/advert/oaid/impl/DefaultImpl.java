package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class DefaultImpl implements IRomOAID {
    DefaultImpl() {
    }

    public boolean isSupported() {
        return false;
    }

    public String getRomOAID() {
        return null;
    }
}

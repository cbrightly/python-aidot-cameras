package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;

public final class zzv implements CapabilityApi.CapabilityListener {
    private final String zzbc;
    private final CapabilityApi.CapabilityListener zzbs;

    zzv(CapabilityApi.CapabilityListener capabilityListener, String str) {
        this.zzbs = capabilityListener;
        this.zzbc = str;
    }

    public final void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        this.zzbs.onCapabilityChanged(capabilityInfo);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || zzv.class != obj.getClass()) {
            return false;
        }
        zzv zzv = (zzv) obj;
        if (!this.zzbs.equals(zzv.zzbs)) {
            return false;
        }
        return this.zzbc.equals(zzv.zzbc);
    }

    public final int hashCode() {
        return (this.zzbs.hashCode() * 31) + this.zzbc.hashCode();
    }
}

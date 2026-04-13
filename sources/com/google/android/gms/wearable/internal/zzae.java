package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;

public final class zzae implements CapabilityClient.OnCapabilityChangedListener {
    private final String zzbc;
    private final CapabilityClient.OnCapabilityChangedListener zzby;

    zzae(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        this.zzby = onCapabilityChangedListener;
        this.zzbc = str;
    }

    public final void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        this.zzby.onCapabilityChanged(capabilityInfo);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || zzae.class != obj.getClass()) {
            return false;
        }
        zzae zzae = (zzae) obj;
        if (!this.zzby.equals(zzae.zzby)) {
            return false;
        }
        return this.zzbc.equals(zzae.zzbc);
    }

    public final int hashCode() {
        return (this.zzby.hashCode() * 31) + this.zzbc.hashCode();
    }
}

package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.DataItemAsset;

public final class zzcz implements DataItemAsset {
    private final String zzdm;
    private final String zzdn;

    public zzcz(DataItemAsset dataItemAsset) {
        this.zzdm = dataItemAsset.getId();
        this.zzdn = dataItemAsset.getDataItemKey();
    }

    public final boolean isDataValid() {
        return true;
    }

    public final String getId() {
        return this.zzdm;
    }

    public final String getDataItemKey() {
        return this.zzdn;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataItemAssetEntity[");
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        if (this.zzdm == null) {
            sb.append(",noid");
        } else {
            sb.append(",");
            sb.append(this.zzdm);
        }
        sb.append(", key=");
        sb.append(this.zzdn);
        sb.append("]");
        return sb.toString();
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        return this;
    }
}

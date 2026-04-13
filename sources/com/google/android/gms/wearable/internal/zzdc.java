package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.maps.android.BuildConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzdc implements DataItem {
    private byte[] data;
    private Uri uri;
    private Map<String, DataItemAsset> zzdo;

    public zzdc(DataItem dataItem) {
        this.uri = dataItem.getUri();
        this.data = dataItem.getData();
        HashMap hashMap = new HashMap();
        for (Map.Entry next : dataItem.getAssets().entrySet()) {
            if (next.getKey() != null) {
                hashMap.put((String) next.getKey(), (DataItemAsset) ((DataItemAsset) next.getValue()).freeze());
            }
        }
        this.zzdo = Collections.unmodifiableMap(hashMap);
    }

    public final boolean isDataValid() {
        return true;
    }

    public final Uri getUri() {
        return this.uri;
    }

    public final byte[] getData() {
        return this.data;
    }

    public final Map<String, DataItemAsset> getAssets() {
        return this.zzdo;
    }

    public final DataItem setData(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        boolean isLoggable = Log.isLoggable("DataItem", 3);
        StringBuilder sb = new StringBuilder("DataItemEntity{ ");
        String valueOf = String.valueOf(this.uri);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 4);
        sb2.append("uri=");
        sb2.append(valueOf);
        sb.append(sb2.toString());
        byte[] bArr = this.data;
        String valueOf2 = String.valueOf(bArr == null ? BuildConfig.TRAVIS : Integer.valueOf(bArr.length));
        StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 9);
        sb3.append(", dataSz=");
        sb3.append(valueOf2);
        sb.append(sb3.toString());
        int size = this.zzdo.size();
        StringBuilder sb4 = new StringBuilder(23);
        sb4.append(", numAssets=");
        sb4.append(size);
        sb.append(sb4.toString());
        if (isLoggable && !this.zzdo.isEmpty()) {
            sb.append(", assets=[");
            String str = "";
            for (Map.Entry next : this.zzdo.entrySet()) {
                String str2 = (String) next.getKey();
                String id = ((DataItemAsset) next.getValue()).getId();
                StringBuilder sb5 = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(str2).length() + String.valueOf(id).length());
                sb5.append(str);
                sb5.append(str2);
                sb5.append(": ");
                sb5.append(id);
                sb.append(sb5.toString());
                str = ", ";
            }
            sb.append("]");
        }
        sb.append(" }");
        return sb.toString();
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        return this;
    }
}

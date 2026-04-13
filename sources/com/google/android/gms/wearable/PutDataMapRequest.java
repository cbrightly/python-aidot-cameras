package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.wearable.zze;
import com.google.android.gms.internal.wearable.zzf;
import com.google.android.gms.internal.wearable.zzt;

@VisibleForTesting
public class PutDataMapRequest {
    private final DataMap zzr;
    private final PutDataRequest zzs;

    private PutDataMapRequest(PutDataRequest putDataRequest, DataMap dataMap) {
        this.zzs = putDataRequest;
        DataMap dataMap2 = new DataMap();
        this.zzr = dataMap2;
        if (dataMap != null) {
            dataMap2.putAll(dataMap);
        }
    }

    public static PutDataMapRequest createFromDataMapItem(@NonNull DataMapItem dataMapItem) {
        Asserts.checkNotNull(dataMapItem, "source must not be null");
        return new PutDataMapRequest(PutDataRequest.zza(dataMapItem.getUri()), dataMapItem.getDataMap());
    }

    public static PutDataMapRequest createWithAutoAppendedId(@NonNull String str) {
        Asserts.checkNotNull(str, "pathPrefix must not be null");
        return new PutDataMapRequest(PutDataRequest.createWithAutoAppendedId(str), (DataMap) null);
    }

    public static PutDataMapRequest create(@NonNull String str) {
        Asserts.checkNotNull(str, "path must not be null");
        return new PutDataMapRequest(PutDataRequest.create(str), (DataMap) null);
    }

    public Uri getUri() {
        return this.zzs.getUri();
    }

    public DataMap getDataMap() {
        return this.zzr;
    }

    public PutDataMapRequest setUrgent() {
        this.zzs.setUrgent();
        return this;
    }

    public boolean isUrgent() {
        return this.zzs.isUrgent();
    }

    public PutDataRequest asPutDataRequest() {
        zzf zza = zze.zza(this.zzr);
        this.zzs.setData(zzt.zzb(zza.zzfw));
        int size = zza.zzfx.size();
        int i = 0;
        while (i < size) {
            String num = Integer.toString(i);
            Asset asset = zza.zzfx.get(i);
            if (num == null) {
                String valueOf = String.valueOf(asset);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 26);
                sb.append("asset key cannot be null: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            } else if (asset == null) {
                String valueOf2 = String.valueOf(num);
                throw new IllegalStateException(valueOf2.length() != 0 ? "asset cannot be null: key=".concat(valueOf2) : new String("asset cannot be null: key="));
            } else {
                if (Log.isLoggable(DataMap.TAG, 3)) {
                    String valueOf3 = String.valueOf(asset);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(num).length() + 33 + String.valueOf(valueOf3).length());
                    sb2.append("asPutDataRequest: adding asset: ");
                    sb2.append(num);
                    sb2.append(" ");
                    sb2.append(valueOf3);
                    Log.d(DataMap.TAG, sb2.toString());
                }
                this.zzs.putAsset(num, asset);
                i++;
            }
        }
        return this.zzs;
    }
}

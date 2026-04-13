package com.google.android.libraries.places.internal;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdd {
    public static final /* synthetic */ int zza = 0;
    private static final long zzb = TimeUnit.MINUTES.toMicros(1);
    private final zzcn zzc;
    private final Context zzd;

    zzdd(Context context, zzcn zzcn) {
        this.zzd = context;
        this.zzc = zzcn;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final zzjq zza(@Nullable String str) {
        WifiManager wifiManager = (WifiManager) this.zzd.getSystemService("wifi");
        if (wifiManager == null || !wifiManager.isWifiEnabled()) {
            return zzjq.zzl();
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults == null || scanResults.isEmpty()) {
            return zzjq.zzl();
        }
        zzjq zzo = zzjq.zzo(zzkb.zza(zzdc.zza), scanResults);
        ArrayList arrayList = new ArrayList();
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        int size = zzo.size();
        for (int i = 0; i < size; i++) {
            ScanResult scanResult = (ScanResult) zzo.get(i);
            if (scanResult != null && !TextUtils.isEmpty(scanResult.SSID)) {
                long zza2 = (this.zzc.zza() * 1000) - scanResult.timestamp;
                long j = zzb;
                String str2 = scanResult.SSID;
                if (str2 != null) {
                    boolean z = true;
                    if (str2.indexOf(95) < 0) {
                        z = false;
                    } else {
                        String lowerCase = str2.toLowerCase(Locale.ENGLISH);
                        if (!lowerCase.contains("_nomap") && !lowerCase.contains("_optout")) {
                            z = false;
                        }
                    }
                    if (zza2 <= j && !z) {
                        arrayList.add(new zzdb(connectionInfo, scanResult));
                    }
                } else {
                    throw new IllegalArgumentException("Null SSID.");
                }
            }
        }
        return zzjq.zzj(arrayList);
    }
}

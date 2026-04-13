package com.google.android.libraries.places.internal;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdb {
    @Nullable
    private final String zza;
    private final int zzb;
    private final zzda zzc;
    private final boolean zzd;
    private final int zze;

    public zzdb(@Nullable WifiInfo wifiInfo, ScanResult scanResult) {
        zzda zzda;
        String str = scanResult.BSSID;
        String str2 = scanResult.capabilities;
        int i = scanResult.level;
        int i2 = scanResult.frequency;
        if (TextUtils.isEmpty(str2)) {
            zzda = zzda.OTHER;
        } else {
            String upperCase = str2.toUpperCase();
            if (upperCase.equals("[ESS]") || upperCase.equals("[IBSS]")) {
                zzda = zzda.NONE;
            } else if (upperCase.matches(".*WPA[0-9]*-PSK.*")) {
                zzda = zzda.PSK;
            } else if (upperCase.matches(".*WPA[0-9]*-EAP.*")) {
                zzda = zzda.EAP;
            } else {
                zzda = zzda.OTHER;
            }
        }
        boolean z = false;
        if (wifiInfo != null && !TextUtils.isEmpty(str) && str.equalsIgnoreCase(wifiInfo.getBSSID())) {
            z = true;
        }
        this.zza = str;
        this.zzb = i;
        this.zzc = zzda;
        this.zzd = z;
        this.zze = i2;
    }

    public final int zza() {
        return this.zze;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final zzda zzc() {
        return this.zzc;
    }

    @Nullable
    public final String zzd() {
        return this.zza;
    }

    public final boolean zze() {
        return this.zzd;
    }
}

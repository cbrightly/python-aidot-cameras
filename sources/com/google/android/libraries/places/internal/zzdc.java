package com.google.android.libraries.places.internal;

import android.net.wifi.ScanResult;
import java.util.Comparator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzdc implements Comparator {
    public static final /* synthetic */ zzdc zza = new zzdc();

    private /* synthetic */ zzdc() {
    }

    public final int compare(Object obj, Object obj2) {
        int i = zzdd.zza;
        return ((ScanResult) obj2).level - ((ScanResult) obj).level;
    }
}

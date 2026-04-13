package com.google.android.libraries.places.internal;

import com.google.firebase.analytics.FirebaseAnalytics;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zziz extends zzjb {
    final /* synthetic */ zzja zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zziz(zzja zzja, zzjc zzjc, CharSequence charSequence) {
        super(zzjc, "3.1.0");
        this.zza = zzja;
    }

    /* access modifiers changed from: package-private */
    public final int zzc(int i) {
        return i + 1;
    }

    /* access modifiers changed from: package-private */
    public final int zzd(int i) {
        int length = "3.1.0".length();
        zziy.zzb(i, length, FirebaseAnalytics.Param.INDEX);
        while (i < length) {
            if ("3.1.0".charAt(i) == '.') {
                return i;
            }
            i++;
        }
        return -1;
    }
}

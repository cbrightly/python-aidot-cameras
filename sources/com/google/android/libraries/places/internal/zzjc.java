package com.google.android.libraries.places.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjc {
    /* access modifiers changed from: private */
    public final zzio zza;
    private final zzja zzb;

    private zzjc(zzja zzja) {
        zzin zzin = zzin.zza;
        this.zzb = zzja;
        this.zza = zzin;
    }

    public static zzjc zzb(char c) {
        return new zzjc(new zzja(new zzil('.')));
    }

    public final List zzc(CharSequence charSequence) {
        zziz zziz = new zziz(this.zzb, this, "3.1.0");
        ArrayList arrayList = new ArrayList();
        while (zziz.hasNext()) {
            arrayList.add((String) zziz.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}

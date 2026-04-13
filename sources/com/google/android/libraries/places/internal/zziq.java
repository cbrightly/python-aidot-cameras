package com.google.android.libraries.places.internal;

import java.util.Iterator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zziq extends zzit {
    final /* synthetic */ zzit zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zziq(zzit zzit, zzit zzit2) {
        super(zzit2, (zzis) null);
        this.zza = zzit;
    }

    public final Appendable zza(Appendable appendable, Iterator it) {
        zziy.zzc(it, "parts");
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (next != null) {
                appendable.append(zzit.zzf(next));
                break;
            }
        }
        while (it.hasNext()) {
            Object next2 = it.next();
            if (next2 != null) {
                appendable.append(this.zza.zza);
                appendable.append(zzit.zzf(next2));
            }
        }
        return appendable;
    }
}

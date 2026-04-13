package com.google.android.libraries.places.internal;

import java.io.IOException;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class zzit {
    /* access modifiers changed from: private */
    public final String zza;

    /* synthetic */ zzit(zzit zzit, zzis zzis) {
        this.zza = zzit.zza;
    }

    private zzit(String str) {
        this.zza = str;
    }

    public static zzit zzb(String str) {
        return new zzit(str);
    }

    static final CharSequence zzf(@CheckForNull Object obj) {
        obj.getClass();
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }

    public Appendable zza(Appendable appendable, Iterator it) {
        if (it.hasNext()) {
            appendable.append(zzf(it.next()));
            while (it.hasNext()) {
                appendable.append(this.zza);
                appendable.append(zzf(it.next()));
            }
        }
        return appendable;
    }

    public final zzit zzc() {
        return new zziq(this, this);
    }

    public final String zze(Iterable iterable) {
        Iterator it = iterable.iterator();
        StringBuilder sb = new StringBuilder();
        try {
            zza(sb, it);
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}

package com.google.android.libraries.places.internal;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzir {
    public static final Appendable zza(Appendable appendable, Iterator it, zzit zzit, String str) {
        if (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            appendable.append(zzit.zzf(entry.getKey()));
            appendable.append("=");
            appendable.append(zzit.zzf(entry.getValue()));
            while (it.hasNext()) {
                appendable.append(zzit.zza);
                Map.Entry entry2 = (Map.Entry) it.next();
                appendable.append(zzit.zzf(entry2.getKey()));
                appendable.append("=");
                appendable.append(zzit.zzf(entry2.getValue()));
            }
        }
        return appendable;
    }
}

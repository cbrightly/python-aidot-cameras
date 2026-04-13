package com.google.android.libraries.places.internal;

import java.lang.reflect.InvocationTargetException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzln {
    /* access modifiers changed from: private */
    public static final zzlp zza = zzb(zzlp.zzd);

    private static zzlp zzb(String[] strArr) {
        zzlp zzlp;
        try {
            zzlp = zzlq.zza();
        } catch (NoClassDefFoundError e) {
            zzlp = null;
        }
        if (zzlp != null) {
            return zzlp;
        }
        StringBuilder sb = new StringBuilder();
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            try {
                return (zzlp) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable th) {
                th = th;
                if (th instanceof InvocationTargetException) {
                    th = th.getCause();
                }
                sb.append(10);
                sb.append(str);
                sb.append(": ");
                sb.append(th);
                i++;
            }
        }
        throw new IllegalStateException(sb.insert(0, "No logging platforms found:").toString());
    }
}

package com.google.android.libraries.places.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmr {
    private static final String[] zza;
    private static final zzmv zzb;

    static {
        zzmv zzmw;
        String[] strArr = {"com.google.common.flogger.util.StackWalkerStackGetter", "com.google.common.flogger.util.JavaLangAccessStackGetter"};
        zza = strArr;
        int i = 0;
        while (true) {
            if (i >= 2) {
                zzmw = new zzmw();
                break;
            }
            try {
                zzmw = (zzmv) Class.forName(strArr[i]).asSubclass(zzmv.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable th) {
                zzmw = null;
            }
            if (zzmw != null) {
                break;
            }
            i++;
        }
        zzb = zzmw;
    }

    @NullableDecl
    public static StackTraceElement zza(Class cls, int i) {
        zzms.zza(cls, TypedValues.AttributesType.S_TARGET);
        return zzb.zza(cls, 2);
    }
}

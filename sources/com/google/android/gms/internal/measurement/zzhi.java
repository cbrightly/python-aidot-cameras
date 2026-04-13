package com.google.android.gms.internal.measurement;

import android.os.Binder;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final /* synthetic */ class zzhi {
    public static Object zza(zzhj zzhj) {
        long clearCallingIdentity;
        try {
            return zzhj.zza();
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            Object zza = zzhj.zza();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return zza;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}

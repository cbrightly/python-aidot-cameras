package com.google.android.libraries.places.internal;

import android.os.Build;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzlx extends zzlr {
    private static final AtomicReference zza = new AtomicReference();
    private static final AtomicLong zzb = new AtomicLong();
    private static final ConcurrentLinkedQueue zzc = new ConcurrentLinkedQueue();
    private volatile zzky zzd;

    private zzlx(String str) {
        super(str);
        boolean z;
        String str2 = Build.FINGERPRINT;
        boolean z2 = true;
        boolean z3 = str2 != null ? "robolectric".equals(str2) : true;
        String str3 = Build.HARDWARE;
        if (!"goldfish".equals(str3)) {
            z = "ranchu".equals(str3);
        } else {
            z = true;
        }
        String str4 = Build.TYPE;
        if (!"eng".equals(str4) && !"userdebug".equals(str4)) {
            z2 = false;
        }
        if (z3 || z) {
            this.zzd = new zzls().zza(zza());
        } else {
            this.zzd = z2 ? new zzlz().zzb(false).zza(zza()) : null;
        }
    }

    public static zzky zzb(String str) {
        AtomicReference atomicReference = zza;
        if (atomicReference.get() != null) {
            return ((zzlt) atomicReference.get()).zza(str);
        }
        zzlx zzlx = new zzlx(str.replace('$', '.'));
        zzlv.zza.offer(zzlx);
        if (atomicReference.get() != null) {
            while (true) {
                zzlx zzlx2 = (zzlx) zzlv.zza.poll();
                if (zzlx2 == null) {
                    break;
                }
                zzlx2.zzd = ((zzlt) zza.get()).zza(zzlx2.zza());
            }
            if (((zzlw) zzc.poll()) != null) {
                zzb.getAndDecrement();
                throw null;
            }
        }
        return zzlx;
    }
}

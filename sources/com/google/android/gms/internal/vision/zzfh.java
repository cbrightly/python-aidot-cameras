package com.google.android.gms.internal.vision;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzfh extends zzfd {
    private final zzfg zza = new zzfg();

    zzfh() {
    }

    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 != null) {
            this.zza.zza(th, true).add(th2);
        } else {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
    }

    public final void zza(Throwable th) {
        th.printStackTrace();
        List<Throwable> zza2 = this.zza.zza(th, false);
        if (zza2 != null) {
            synchronized (zza2) {
                for (Throwable printStackTrace : zza2) {
                    System.err.print("Suppressed: ");
                    printStackTrace.printStackTrace();
                }
            }
        }
    }
}

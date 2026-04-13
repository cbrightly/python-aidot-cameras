package com.google.android.libraries.places.internal;

import android.os.Build;
import android.util.Log;
import dalvik.system.VMStack;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzlu extends zzlp {
    /* access modifiers changed from: private */
    public static final boolean zza = zza.zza();
    /* access modifiers changed from: private */
    public static final boolean zzb;
    private static final zzlo zzc = new zzlo() {
        public zzku zza(Class<?> cls, int i) {
            return zzku.zza;
        }

        public String zzb(Class loggerClass) {
            StackTraceElement zza;
            if (zzlu.zza) {
                try {
                    if (loggerClass.equals(zzlu.zzp())) {
                        return VMStack.getStackClass2().getName();
                    }
                } catch (Throwable th) {
                }
            }
            if (!zzlu.zzb || (zza = zzmr.zza(loggerClass, 1)) == null) {
                return null;
            }
            return zza.getClassName();
        }
    };

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public final class zza {
        zza() {
        }

        static boolean zza() {
            return zzlu.zzt();
        }
    }

    static {
        String str = Build.FINGERPRINT;
        boolean z = true;
        if (str != null && !"robolectric".equals(str)) {
            z = false;
        }
        zzb = z;
        Log.class.getName();
    }

    static Class<?> zzp() {
        return VMStack.getStackClass2();
    }

    static String zzq() {
        try {
            return VMStack.getStackClass2().getName();
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zzt() {
        try {
            Class.forName("dalvik.system.VMStack").getMethod("getStackClass2", new Class[0]);
            return zza.class.getName().equals(zzq());
        } catch (Throwable th) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public zzky zze(String className) {
        return zzlx.zzb(className);
    }

    /* access modifiers changed from: protected */
    public zzlo zzh() {
        return zzc;
    }

    /* access modifiers changed from: protected */
    public zzmd zzj() {
        return zzly.zzb();
    }

    /* access modifiers changed from: protected */
    public String zzm() {
        return "platform: Android";
    }
}

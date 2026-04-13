package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzpb;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzeu extends zzgy {
    /* access modifiers changed from: private */
    public char zza = 0;
    /* access modifiers changed from: private */
    public long zzb = -1;
    @GuardedBy("this")
    private String zzc;
    private final zzes zzd = new zzes(this, 6, false, false);
    private final zzes zze = new zzes(this, 6, true, false);
    private final zzes zzf = new zzes(this, 6, false, true);
    private final zzes zzg = new zzes(this, 5, false, false);
    private final zzes zzh = new zzes(this, 5, true, false);
    private final zzes zzi = new zzes(this, 5, false, true);
    private final zzes zzj = new zzes(this, 4, false, false);
    private final zzes zzk = new zzes(this, 3, false, false);
    private final zzes zzl = new zzes(this, 2, false, false);

    zzeu(zzge zzge) {
        super(zzge);
    }

    protected static Object zzn(String str) {
        if (str == null) {
            return null;
        }
        return new zzet(str);
    }

    static String zzo(boolean z, String str, Object obj, Object obj2, Object obj3) {
        String zzp = zzp(z, obj);
        String zzp2 = zzp(z, obj2);
        String zzp3 = zzp(z, obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (str == null) {
            str = str2;
        }
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        String str3 = ", ";
        if (!TextUtils.isEmpty(zzp)) {
            sb.append(str2);
            sb.append(zzp);
            str2 = str3;
        }
        if (!TextUtils.isEmpty(zzp2)) {
            sb.append(str2);
            sb.append(zzp2);
        } else {
            str3 = str2;
        }
        if (!TextUtils.isEmpty(zzp3)) {
            sb.append(str3);
            sb.append(zzp3);
        }
        return sb.toString();
    }

    @VisibleForTesting
    static String zzp(boolean z, Object obj) {
        String className;
        String str = "";
        if (obj == null) {
            return str;
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf((long) ((Integer) obj).intValue());
        }
        int i = 0;
        if (obj instanceof Long) {
            if (!z) {
                return obj.toString();
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return obj.toString();
            }
            char charAt = obj.toString().charAt(0);
            String valueOf = String.valueOf(Math.abs(l.longValue()));
            long round = Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1)));
            long round2 = Math.round(Math.pow(10.0d, (double) valueOf.length()) - 4.0d);
            StringBuilder sb = new StringBuilder();
            if (charAt == '-') {
                str = "-";
            }
            sb.append(str);
            sb.append(round);
            sb.append("...");
            sb.append(str);
            sb.append(round2);
            return sb.toString();
        } else if (obj instanceof Boolean) {
            return obj.toString();
        } else {
            if (obj instanceof Throwable) {
                Throwable th = (Throwable) obj;
                StringBuilder sb2 = new StringBuilder(z ? th.getClass().getName() : th.toString());
                String zzq = zzq(zzge.class.getCanonicalName());
                StackTraceElement[] stackTrace = th.getStackTrace();
                int length = stackTrace.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTrace[i];
                    if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null && zzq(className).equals(zzq)) {
                        sb2.append(": ");
                        sb2.append(stackTraceElement);
                        break;
                    }
                    i++;
                }
                return sb2.toString();
            } else if (obj instanceof zzet) {
                return ((zzet) obj).zza;
            } else {
                if (z) {
                    return "-";
                }
                return obj.toString();
            }
        }
    }

    @VisibleForTesting
    static String zzq(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf != -1) {
            return str.substring(0, lastIndexOf);
        }
        zzpb.zzc();
        if (((Boolean) zzeh.zzay.zza((Object) null)).booleanValue()) {
            return "";
        }
        return str;
    }

    public final zzes zzc() {
        return this.zzk;
    }

    public final zzes zzd() {
        return this.zzd;
    }

    public final zzes zze() {
        return this.zzf;
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return false;
    }

    public final zzes zzh() {
        return this.zze;
    }

    public final zzes zzi() {
        return this.zzj;
    }

    public final zzes zzj() {
        return this.zzl;
    }

    public final zzes zzk() {
        return this.zzg;
    }

    public final zzes zzl() {
        return this.zzi;
    }

    public final zzes zzm() {
        return this.zzh;
    }

    /* access modifiers changed from: protected */
    @EnsuresNonNull({"logTagDoNotUseDirectly"})
    @VisibleForTesting
    public final String zzr() {
        String str;
        synchronized (this) {
            if (this.zzc == null) {
                if (this.zzt.zzy() != null) {
                    this.zzc = this.zzt.zzy();
                } else {
                    this.zzc = this.zzt.zzf().zzn();
                }
            }
            Preconditions.checkNotNull(this.zzc);
            str = this.zzc;
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public final void zzu(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && Log.isLoggable(zzr(), i)) {
            Log.println(i, zzr(), zzo(false, str, obj, obj2, obj3));
        }
        if (!z2 && i >= 5) {
            Preconditions.checkNotNull(str);
            zzgb zzo = this.zzt.zzo();
            if (zzo == null) {
                Log.println(6, zzr(), "Scheduler not set. Not logging error/warn");
            } else if (!zzo.zzy()) {
                Log.println(6, zzr(), "Scheduler not initialized. Not logging error/warn");
            } else {
                if (i >= 9) {
                    i = 8;
                }
                zzo.zzp(new zzer(this, i, str, obj, obj2, obj3));
            }
        }
    }
}

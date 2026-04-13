package com.google.android.gms.internal.clearcut;

import android.content.Context;
import android.util.Log;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.clearcut.zzgw;
import com.google.android.gms.phenotype.Phenotype;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class zzp implements ClearcutLogger.zza {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final zzao zzaq;
    private static final zzao zzar = new zzao(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).zzc("gms:playlog:service:sampling_").zzd("LogSampling__");
    private static final ConcurrentHashMap<String, zzae<zzgw.zza>> zzas = new ConcurrentHashMap<>();
    private static final HashMap<String, zzae<String>> zzat = new HashMap<>();
    @VisibleForTesting
    private static Boolean zzau = null;
    @VisibleForTesting
    private static Long zzav = null;
    @VisibleForTesting
    private static final zzae<Boolean> zzaw;
    private final Context zzh;

    static {
        zzao zzd = new zzao(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).zzc("gms:playlog:service:samplingrules_").zzd("LogSamplingRules__");
        zzaq = zzd;
        zzaw = zzd.zzc("enable_log_sampling_rules", false);
    }

    public zzp(Context context) {
        this.zzh = context;
        if (context != null) {
            zzae.maybeInit(context);
        }
    }

    @VisibleForTesting
    private static long zza(String str, long j) {
        if (str == null || str.isEmpty()) {
            return zzk.zza(ByteBuffer.allocate(8).putLong(j).array());
        }
        byte[] bytes = str.getBytes(UTF_8);
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
        allocate.put(bytes);
        allocate.putLong(j);
        return zzk.zza(allocate.array());
    }

    @VisibleForTesting
    private static zzgw.zza.zzb zza(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(44);
        int i = 0;
        if (indexOf >= 0) {
            i = indexOf + 1;
            str2 = str.substring(0, indexOf);
        } else {
            str2 = "";
        }
        int indexOf2 = str.indexOf(47, i);
        if (indexOf2 <= 0) {
            String valueOf = String.valueOf(str);
            Log.e("LogSamplerImpl", valueOf.length() != 0 ? "Failed to parse the rule: ".concat(valueOf) : new String("Failed to parse the rule: "));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf2));
            long parseLong2 = Long.parseLong(str.substring(indexOf2 + 1));
            if (parseLong >= 0 && parseLong2 >= 0) {
                return (zzgw.zza.zzb) zzgw.zza.zzb.zzfz().zzn(str2).zzr(parseLong).zzs(parseLong2).zzbh();
            }
            StringBuilder sb = new StringBuilder(72);
            sb.append("negative values not supported: ");
            sb.append(parseLong);
            sb.append("/");
            sb.append(parseLong2);
            Log.e("LogSamplerImpl", sb.toString());
            return null;
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(str);
            Log.e("LogSamplerImpl", valueOf2.length() != 0 ? "parseLong() failed while parsing: ".concat(valueOf2) : new String("parseLong() failed while parsing: "), e);
            return null;
        }
    }

    @VisibleForTesting
    private static boolean zzb(long j, long j2, long j3) {
        if (j2 < 0 || j3 <= 0) {
            return true;
        }
        return ((j > 0 ? 1 : (j == 0 ? 0 : -1)) >= 0 ? j % j3 : (((DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE % j3) + 1) + ((j & DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) % j3)) % j3) < j2;
    }

    private static boolean zzc(Context context) {
        if (zzau == null) {
            zzau = Boolean.valueOf(Wrappers.packageManager(context).checkCallingOrSelfPermission("com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
        }
        return zzau.booleanValue();
    }

    @VisibleForTesting
    private static long zzd(Context context) {
        if (zzav == null) {
            long j = 0;
            if (context == null) {
                return 0;
            }
            if (zzc(context)) {
                j = zzy.getLong(context.getContentResolver(), "android_id", 0);
            }
            zzav = Long.valueOf(j);
        }
        return zzav.longValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009d, code lost:
        r3 = zzaq.zza(r1, com.google.android.gms.internal.clearcut.zzgw.zza.zzft(), com.google.android.gms.internal.clearcut.zzq.zzax);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(com.google.android.gms.clearcut.zze r13) {
        /*
            r12 = this;
            com.google.android.gms.internal.clearcut.zzr r0 = r13.zzag
            java.lang.String r1 = r0.zzj
            int r0 = r0.zzk
            com.google.android.gms.internal.clearcut.zzha r13 = r13.zzaa
            r2 = 0
            if (r13 == 0) goto L_0x000e
            int r13 = r13.zzbji
            goto L_0x000f
        L_0x000e:
            r13 = r2
        L_0x000f:
            com.google.android.gms.internal.clearcut.zzae<java.lang.Boolean> r3 = zzaw
            java.lang.Object r3 = r3.get()
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            r4 = 0
            if (r3 != 0) goto L_0x0077
            if (r1 == 0) goto L_0x0027
            boolean r13 = r1.isEmpty()
            if (r13 != 0) goto L_0x0027
            goto L_0x002f
        L_0x0027:
            if (r0 < 0) goto L_0x002e
            java.lang.String r1 = java.lang.String.valueOf(r0)
            goto L_0x002f
        L_0x002e:
            r1 = r4
        L_0x002f:
            if (r1 == 0) goto L_0x00fb
            android.content.Context r13 = r12.zzh
            if (r13 == 0) goto L_0x0056
            boolean r13 = zzc(r13)
            if (r13 != 0) goto L_0x003c
            goto L_0x0056
        L_0x003c:
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.clearcut.zzae<java.lang.String>> r13 = zzat
            java.lang.Object r0 = r13.get(r1)
            com.google.android.gms.internal.clearcut.zzae r0 = (com.google.android.gms.internal.clearcut.zzae) r0
            if (r0 != 0) goto L_0x004f
            com.google.android.gms.internal.clearcut.zzao r0 = zzar
            com.google.android.gms.internal.clearcut.zzae r0 = r0.zza(r1, r4)
            r13.put(r1, r0)
        L_0x004f:
            java.lang.Object r13 = r0.get()
            r4 = r13
            java.lang.String r4 = (java.lang.String) r4
        L_0x0056:
            com.google.android.gms.internal.clearcut.zzgw$zza$zzb r13 = zza((java.lang.String) r4)
            if (r13 == 0) goto L_0x00fb
            java.lang.String r0 = r13.zzfw()
            android.content.Context r1 = r12.zzh
            long r1 = zzd(r1)
            long r3 = zza(r0, r1)
            long r5 = r13.zzfx()
            long r7 = r13.zzfy()
            boolean r13 = zzb(r3, r5, r7)
            return r13
        L_0x0077:
            if (r1 == 0) goto L_0x0080
            boolean r3 = r1.isEmpty()
            if (r3 != 0) goto L_0x0080
            goto L_0x0088
        L_0x0080:
            if (r0 < 0) goto L_0x0087
            java.lang.String r1 = java.lang.String.valueOf(r0)
            goto L_0x0088
        L_0x0087:
            r1 = r4
        L_0x0088:
            if (r1 == 0) goto L_0x00fb
            android.content.Context r0 = r12.zzh
            if (r0 != 0) goto L_0x0093
            java.util.List r0 = java.util.Collections.emptyList()
            goto L_0x00bc
        L_0x0093:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.google.android.gms.internal.clearcut.zzae<com.google.android.gms.internal.clearcut.zzgw$zza>> r0 = zzas
            java.lang.Object r3 = r0.get(r1)
            com.google.android.gms.internal.clearcut.zzae r3 = (com.google.android.gms.internal.clearcut.zzae) r3
            if (r3 != 0) goto L_0x00b2
            com.google.android.gms.internal.clearcut.zzao r3 = zzaq
            com.google.android.gms.internal.clearcut.zzgw$zza r4 = com.google.android.gms.internal.clearcut.zzgw.zza.zzft()
            com.google.android.gms.internal.clearcut.zzan r5 = com.google.android.gms.internal.clearcut.zzq.zzax
            com.google.android.gms.internal.clearcut.zzae r3 = r3.zza(r1, r4, r5)
            java.lang.Object r0 = r0.putIfAbsent(r1, r3)
            com.google.android.gms.internal.clearcut.zzae r0 = (com.google.android.gms.internal.clearcut.zzae) r0
            if (r0 == 0) goto L_0x00b2
            r3 = r0
        L_0x00b2:
            java.lang.Object r0 = r3.get()
            com.google.android.gms.internal.clearcut.zzgw$zza r0 = (com.google.android.gms.internal.clearcut.zzgw.zza) r0
            java.util.List r0 = r0.zzfs()
        L_0x00bc:
            java.util.Iterator r0 = r0.iterator()
        L_0x00c0:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00fb
            java.lang.Object r1 = r0.next()
            com.google.android.gms.internal.clearcut.zzgw$zza$zzb r1 = (com.google.android.gms.internal.clearcut.zzgw.zza.zzb) r1
            boolean r3 = r1.zzfv()
            if (r3 == 0) goto L_0x00de
            int r3 = r1.getEventCode()
            if (r3 == 0) goto L_0x00de
            int r3 = r1.getEventCode()
            if (r3 != r13) goto L_0x00c0
        L_0x00de:
            java.lang.String r3 = r1.zzfw()
            android.content.Context r4 = r12.zzh
            long r4 = zzd(r4)
            long r6 = zza(r3, r4)
            long r8 = r1.zzfx()
            long r10 = r1.zzfy()
            boolean r1 = zzb(r6, r8, r10)
            if (r1 != 0) goto L_0x00c0
            return r2
        L_0x00fb:
            r13 = 1
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzp.zza(com.google.android.gms.clearcut.zze):boolean");
    }
}

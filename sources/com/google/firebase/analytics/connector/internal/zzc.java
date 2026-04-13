package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzix;
import com.google.android.gms.internal.measurement.zzja;
import com.google.android.gms.internal.measurement.zzjb;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.internal.zzhd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public final class zzc {
    public static final /* synthetic */ int zza = 0;
    private static final zzjb zzb = zzjb.zzi("_in", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", FirebaseAnalytics.Event.CAMPAIGN_DETAILS, "_ug", "_iapx", "_exp_set", "_exp_clear", "_exp_activate", "_exp_timeout", "_exp_expire");
    private static final zzja zzc = zzja.zzj("_e", "_f", "_iap", "_s", "_au", "_ui", "_cd");
    private static final zzja zzd = zzja.zzi("auto", "app", "am");
    private static final zzja zze = zzja.zzh("_r", "_dbg");
    private static final zzja zzf;
    private static final zzja zzg = zzja.zzh("^_ltv_[A-Z]{3}$", "^_cc[1-5]{1}$");

    static {
        zzix zzix = new zzix();
        zzix.zza(zzhd.zza);
        zzix.zza(zzhd.zzb);
        zzf = zzix.zzb();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zza(java.lang.String r5, java.lang.String r6, android.os.Bundle r7) {
        /*
            java.lang.String r0 = "_cmp"
            boolean r6 = r0.equals(r6)
            r0 = 1
            if (r6 != 0) goto L_0x000a
            return r0
        L_0x000a:
            boolean r6 = zzd(r5)
            r1 = 0
            if (r6 != 0) goto L_0x0012
            return r1
        L_0x0012:
            if (r7 != 0) goto L_0x0015
            return r1
        L_0x0015:
            com.google.android.gms.internal.measurement.zzja r6 = zze
            int r2 = r6.size()
            r3 = r1
        L_0x001c:
            if (r3 >= r2) goto L_0x002d
            java.lang.Object r4 = r6.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            boolean r4 = r7.containsKey(r4)
            int r3 = r3 + 1
            if (r4 == 0) goto L_0x001c
            return r1
        L_0x002d:
            int r6 = r5.hashCode()
            switch(r6) {
                case 101200: goto L_0x004a;
                case 101230: goto L_0x0040;
                case 3142703: goto L_0x0035;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x0054
        L_0x0035:
            java.lang.String r6 = "fiam"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0034
            r5 = 2
            goto L_0x0055
        L_0x0040:
            java.lang.String r6 = "fdl"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0034
            r5 = r0
            goto L_0x0055
        L_0x004a:
            java.lang.String r6 = "fcm"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0034
            r5 = r1
            goto L_0x0055
        L_0x0054:
            r5 = -1
        L_0x0055:
            java.lang.String r6 = "_cis"
            switch(r5) {
                case 0: goto L_0x0068;
                case 1: goto L_0x0061;
                case 2: goto L_0x005b;
                default: goto L_0x005a;
            }
        L_0x005a:
            return r1
        L_0x005b:
            java.lang.String r5 = "fiam_integration"
            r7.putString(r6, r5)
            return r0
        L_0x0061:
            java.lang.String r5 = "fdl_integration"
            r7.putString(r6, r5)
            return r0
        L_0x0068:
            java.lang.String r5 = "fcm_integration"
            r7.putString(r6, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.analytics.connector.internal.zzc.zza(java.lang.String, java.lang.String, android.os.Bundle):boolean");
    }

    public static boolean zzb(String str, Bundle bundle) {
        if (zzc.contains(str)) {
            return false;
        }
        if (bundle == null) {
            return true;
        }
        zzja zzja = zze;
        int size = zzja.size();
        int i = 0;
        while (i < size) {
            boolean containsKey = bundle.containsKey((String) zzja.get(i));
            i++;
            if (containsKey) {
                return false;
            }
        }
        return true;
    }

    public static boolean zzc(String str) {
        return !zzb.contains(str);
    }

    public static boolean zzd(String str) {
        return !zzd.contains(str);
    }

    public static boolean zze(String str, String str2) {
        if ("_ce1".equals(str2) || "_ce2".equals(str2)) {
            return str.equals("fcm") || str.equals("frc");
        }
        if (Constants.ScionAnalytics.USER_PROPERTY_FIREBASE_LAST_NOTIFICATION.equals(str2)) {
            return str.equals("fcm") || str.equals(AppMeasurement.FIAM_ORIGIN);
        }
        if (zzf.contains(str2)) {
            return false;
        }
        zzja zzja = zzg;
        int size = zzja.size();
        int i = 0;
        while (i < size) {
            boolean matches = str2.matches((String) zzja.get(i));
            i++;
            if (matches) {
                return false;
            }
        }
        return true;
    }
}

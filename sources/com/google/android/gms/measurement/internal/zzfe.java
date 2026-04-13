package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzfe {
    final /* synthetic */ zzfj zza;
    private final String zzb = "default_event_parameters";
    private final Bundle zzc = new Bundle();
    private Bundle zzd;

    public zzfe(zzfj zzfj, String str, Bundle bundle) {
        this.zza = zzfj;
        Preconditions.checkNotEmpty("default_event_parameters");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.os.Bundle zza() {
        /*
            r9 = this;
            android.os.Bundle r0 = r9.zzd
            if (r0 == 0) goto L_0x0006
            goto L_0x00d1
        L_0x0006:
            com.google.android.gms.measurement.internal.zzfj r0 = r9.zza
            android.content.SharedPreferences r0 = r0.zza()
            java.lang.String r1 = r9.zzb
            r2 = 0
            java.lang.String r0 = r0.getString(r1, r2)
            if (r0 == 0) goto L_0x00c9
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ JSONException -> 0x00b7 }
            r1.<init>()     // Catch:{ JSONException -> 0x00b7 }
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ JSONException -> 0x00b7 }
            r2.<init>((java.lang.String) r0)     // Catch:{ JSONException -> 0x00b7 }
            r0 = 0
            r3 = r0
        L_0x0021:
            int r4 = r2.length()     // Catch:{ JSONException -> 0x00b7 }
            if (r3 >= r4) goto L_0x00b4
            org.json.JSONObject r4 = r2.getJSONObject(r3)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            java.lang.String r5 = "n"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            java.lang.String r6 = "t"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            int r7 = r6.hashCode()     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            switch(r7) {
                case 100: goto L_0x0055;
                case 108: goto L_0x004b;
                case 115: goto L_0x0040;
                default: goto L_0x003f;
            }
        L_0x003f:
            goto L_0x005f
        L_0x0040:
            java.lang.String r7 = "s"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x003f
            r7 = r0
            goto L_0x0060
        L_0x004b:
            java.lang.String r7 = "l"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x003f
            r7 = 2
            goto L_0x0060
        L_0x0055:
            java.lang.String r7 = "d"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x003f
            r7 = 1
            goto L_0x0060
        L_0x005f:
            r7 = -1
        L_0x0060:
            java.lang.String r8 = "v"
            switch(r7) {
                case 0: goto L_0x0083;
                case 1: goto L_0x0076;
                case 2: goto L_0x0069;
                default: goto L_0x0066;
            }
        L_0x0066:
            com.google.android.gms.measurement.internal.zzfj r4 = r9.zza     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            goto L_0x008c
        L_0x0069:
            java.lang.String r4 = r4.getString(r8)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            long r6 = java.lang.Long.parseLong(r4)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            r1.putLong(r5, r6)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            goto L_0x00b0
        L_0x0076:
            java.lang.String r4 = r4.getString(r8)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            double r6 = java.lang.Double.parseDouble(r4)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            r1.putDouble(r5, r6)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            goto L_0x00b0
        L_0x0083:
            java.lang.String r4 = r4.getString(r8)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            r1.putString(r5, r4)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            goto L_0x00b0
        L_0x008c:
            com.google.android.gms.measurement.internal.zzge r4 = r4.zzt     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzd()     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            java.lang.String r5 = "Unrecognized persisted bundle type. Type"
            r4.zzb(r5, r6)     // Catch:{ JSONException -> 0x009e, NumberFormatException -> 0x009c }
            goto L_0x00b0
        L_0x009c:
            r4 = move-exception
            goto L_0x009f
        L_0x009e:
            r4 = move-exception
        L_0x009f:
            com.google.android.gms.measurement.internal.zzfj r4 = r9.zza     // Catch:{ JSONException -> 0x00b7 }
            com.google.android.gms.measurement.internal.zzge r4 = r4.zzt     // Catch:{ JSONException -> 0x00b7 }
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()     // Catch:{ JSONException -> 0x00b7 }
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzd()     // Catch:{ JSONException -> 0x00b7 }
            java.lang.String r5 = "Error reading value from SharedPreferences. Value dropped"
            r4.zza(r5)     // Catch:{ JSONException -> 0x00b7 }
        L_0x00b0:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x00b4:
            r9.zzd = r1     // Catch:{ JSONException -> 0x00b7 }
            goto L_0x00c9
        L_0x00b7:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzfj r0 = r9.zza
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()
            java.lang.String r1 = "Error loading bundle from SharedPreferences. Values will be lost"
            r0.zza(r1)
        L_0x00c9:
            android.os.Bundle r0 = r9.zzd
            if (r0 != 0) goto L_0x00d1
            android.os.Bundle r0 = r9.zzc
            r9.zzd = r0
        L_0x00d1:
            android.os.Bundle r0 = r9.zzd
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfe.zza():android.os.Bundle");
    }

    @WorkerThread
    public final void zzb(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        SharedPreferences.Editor edit = this.zza.zza().edit();
        if (bundle.size() == 0) {
            edit.remove(this.zzb);
        } else {
            String str = this.zzb;
            JSONArray jSONArray = new JSONArray();
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj != null) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("n", (Object) str2);
                        jSONObject.put("v", (Object) obj.toString());
                        if (obj instanceof String) {
                            jSONObject.put("t", (Object) "s");
                        } else if (obj instanceof Long) {
                            jSONObject.put("t", (Object) "l");
                        } else if (obj instanceof Double) {
                            jSONObject.put("t", (Object) "d");
                        } else {
                            this.zza.zzt.zzaA().zzd().zzb("Cannot serialize bundle value to SharedPreferences. Type", obj.getClass());
                        }
                        jSONArray.put((Object) jSONObject);
                    } catch (JSONException e) {
                        this.zza.zzt.zzaA().zzd().zzb("Cannot serialize bundle value to SharedPreferences", e);
                    }
                }
            }
            edit.putString(str, jSONArray.toString());
        }
        edit.apply();
        this.zzd = bundle;
    }
}

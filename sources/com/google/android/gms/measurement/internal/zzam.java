package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzop;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.e;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzam extends zzkt {
    /* access modifiers changed from: private */
    public static final String[] zza = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;", "current_session_count", "ALTER TABLE events ADD COLUMN current_session_count INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zzb = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    /* access modifiers changed from: private */
    public static final String[] zzc = {SharePreferenceUtils.KEY_APP_VERSION, "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;", "dynamite_version", "ALTER TABLE apps ADD COLUMN dynamite_version INTEGER;", "safelisted_events", "ALTER TABLE apps ADD COLUMN safelisted_events TEXT;", "ga_app_id", "ALTER TABLE apps ADD COLUMN ga_app_id TEXT;", "config_last_modified_time", "ALTER TABLE apps ADD COLUMN config_last_modified_time TEXT;", "e_tag", "ALTER TABLE apps ADD COLUMN e_tag TEXT;", "session_stitching_token", "ALTER TABLE apps ADD COLUMN session_stitching_token TEXT;", "sgtm_upload_enabled", "ALTER TABLE apps ADD COLUMN sgtm_upload_enabled INTEGER;", "target_os_version", "ALTER TABLE apps ADD COLUMN target_os_version INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zzd = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zze = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zzg = {"session_scoped", "ALTER TABLE event_filters ADD COLUMN session_scoped BOOLEAN;"};
    /* access modifiers changed from: private */
    public static final String[] zzh = {"session_scoped", "ALTER TABLE property_filters ADD COLUMN session_scoped BOOLEAN;"};
    /* access modifiers changed from: private */
    public static final String[] zzi = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzal zzj;
    /* access modifiers changed from: private */
    public final zzkp zzk = new zzkp(this.zzt.zzax());

    zzam(zzlg zzlg) {
        super(zzlg);
        this.zzt.zzf();
        this.zzj = new zzal(this, this.zzt.zzaw(), "google_app_measurement.db");
    }

    @WorkerThread
    static final void zzV(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty("value");
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put("value", (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put("value", (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put("value", (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003a  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzZ(java.lang.String r4, java.lang.String[] r5) {
        /*
            r3 = this;
            android.database.sqlite.SQLiteDatabase r0 = r3.zzh()
            r1 = 0
            android.database.Cursor r1 = r0.rawQuery(r4, r5)     // Catch:{ SQLiteException -> 0x0026, all -> 0x0024 }
            boolean r5 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
            if (r5 == 0) goto L_0x0018
            r5 = 0
            long r4 = r1.getLong(r5)     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
            r1.close()
            return r4
        L_0x0018:
            android.database.sqlite.SQLiteException r5 = new android.database.sqlite.SQLiteException     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
            java.lang.String r0 = "Database returned empty set"
            r5.<init>(r0)     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
            throw r5     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
        L_0x0020:
            r4 = move-exception
            goto L_0x0038
        L_0x0022:
            r5 = move-exception
            goto L_0x0027
        L_0x0024:
            r4 = move-exception
            goto L_0x0038
        L_0x0026:
            r5 = move-exception
        L_0x0027:
            com.google.android.gms.measurement.internal.zzge r0 = r3.zzt     // Catch:{ all -> 0x0037 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0037 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x0037 }
            java.lang.String r2 = "Database error"
            r0.zzc(r2, r4, r5)     // Catch:{ all -> 0x0037 }
            throw r5     // Catch:{ all -> 0x0037 }
        L_0x0037:
            r4 = move-exception
        L_0x0038:
            if (r1 == 0) goto L_0x003d
            r1.close()
        L_0x003d:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzZ(java.lang.String, java.lang.String[]):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzaa(java.lang.String r3, java.lang.String[] r4, long r5) {
        /*
            r2 = this;
            android.database.sqlite.SQLiteDatabase r0 = r2.zzh()
            r1 = 0
            android.database.Cursor r1 = r0.rawQuery(r3, r4)     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
            boolean r4 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x001e }
            if (r4 == 0) goto L_0x0018
            r4 = 0
            long r3 = r1.getLong(r4)     // Catch:{ SQLiteException -> 0x001e }
            r1.close()
            return r3
        L_0x0018:
            r1.close()
            return r5
        L_0x001c:
            r3 = move-exception
            goto L_0x0033
        L_0x001e:
            r4 = move-exception
            goto L_0x0023
        L_0x0020:
            r3 = move-exception
            goto L_0x0033
        L_0x0022:
            r4 = move-exception
        L_0x0023:
            com.google.android.gms.measurement.internal.zzge r5 = r2.zzt     // Catch:{ all -> 0x001c }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ all -> 0x001c }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ all -> 0x001c }
            java.lang.String r6 = "Database error"
            r5.zzc(r6, r3, r4)     // Catch:{ all -> 0x001c }
            throw r4     // Catch:{ all -> 0x001c }
        L_0x0033:
            if (r1 == 0) goto L_0x0038
            r1.close()
        L_0x0038:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzaa(java.lang.String, java.lang.String[], long):long");
    }

    @WorkerThread
    public final void zzA(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzW();
        try {
            zzh().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzd("Error deleting user property. appId", zzeu.zzn(str), this.zzt.zzj().zzf(str2), e);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0332, code lost:
        if (r3.zzk() == false) goto L_0x033d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0334, code lost:
        r0 = java.lang.Boolean.valueOf(r3.zzi());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x033d, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x033e, code lost:
        r11.put("session_scoped", r0);
        r11.put("data", r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0352, code lost:
        if (zzh().insertWithOnConflict("property_filters", (java.lang.String) null, r11, 5) != -1) goto L_0x0368;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0354, code lost:
        r1.zzt.zzaA().zzd().zzb("Failed to insert property filter (got -1). appId", com.google.android.gms.measurement.internal.zzeu.zzn(r24));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0368, code lost:
        r0 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x036c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:?, code lost:
        r1.zzt.zzaA().zzd().zzc("Error storing property filter. appId", com.google.android.gms.measurement.internal.zzeu.zzn(r24), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x03b7, code lost:
        r3 = r17;
        r7 = r21;
        r3 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x017f, code lost:
        r11 = r0.zzh().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x018b, code lost:
        if (r11.hasNext() == false) goto L_0x01b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0197, code lost:
        if (((com.google.android.gms.internal.measurement.zzet) r11.next()).zzj() != false) goto L_0x0187;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0199, code lost:
        r1.zzt.zzaA().zzk().zzc("Property filter with no ID. Audience definition ignored. appId, audienceId", com.google.android.gms.measurement.internal.zzeu.zzn(r24), java.lang.Integer.valueOf(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01b2, code lost:
        r11 = r0.zzg().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01c8, code lost:
        if (r11.hasNext() == false) goto L_0x02a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r12 = (com.google.android.gms.internal.measurement.zzek) r11.next();
        zzW();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01e4, code lost:
        if (r12.zzg().isEmpty() == false) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01e6, code lost:
        r0 = r1.zzt.zzaA().zzk();
        r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r24);
        r11 = java.lang.Integer.valueOf(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01fe, code lost:
        if (r12.zzp() == false) goto L_0x020b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0200, code lost:
        r20 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x020b, code lost:
        r20 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x020d, code lost:
        r0.zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", r9, r11, java.lang.String.valueOf(r20));
        r21 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0218, code lost:
        r3 = r12.zzbx();
        r21 = r7;
        r7 = new android.content.ContentValues();
        r7.put("app_id", r2);
        r7.put("audience_id", java.lang.Integer.valueOf(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0231, code lost:
        if (r12.zzp() == false) goto L_0x023c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0233, code lost:
        r9 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x023c, code lost:
        r9 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x023d, code lost:
        r7.put("filter_id", r9);
        r7.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.KEY_CHANNEL_EVENT_NAME, r12.zzg());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x024d, code lost:
        if (r12.zzq() == false) goto L_0x0258;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x024f, code lost:
        r9 = java.lang.Boolean.valueOf(r12.zzn());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0258, code lost:
        r9 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0259, code lost:
        r7.put("session_scoped", r9);
        r7.put("data", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x026d, code lost:
        if (zzh().insertWithOnConflict("event_filters", (java.lang.String) null, r7, 5) != -1) goto L_0x0288;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x026f, code lost:
        r1.zzt.zzaA().zzd().zzb("Failed to insert event filter (got -1). appId", com.google.android.gms.measurement.internal.zzeu.zzn(r24));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0282, code lost:
        r3 = r25;
        r7 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0288, code lost:
        r3 = r25;
        r7 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x02a4, code lost:
        r21 = r7;
        r0 = r0.zzh().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x02b2, code lost:
        if (r0.hasNext() == false) goto L_0x03b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02b4, code lost:
        r3 = (com.google.android.gms.internal.measurement.zzet) r0.next();
        zzW();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x02ce, code lost:
        if (r3.zze().isEmpty() == false) goto L_0x02fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x02d0, code lost:
        r0 = r1.zzt.zzaA().zzk();
        r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r24);
        r9 = java.lang.Integer.valueOf(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x02e8, code lost:
        if (r3.zzj() == false) goto L_0x02f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x02ea, code lost:
        r3 = java.lang.Integer.valueOf(r3.zza());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x02f3, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x02f4, code lost:
        r0.zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", r8, r9, java.lang.String.valueOf(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x02fd, code lost:
        r7 = r3.zzbx();
        r11 = new android.content.ContentValues();
        r11.put("app_id", r2);
        r11.put("audience_id", java.lang.Integer.valueOf(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0314, code lost:
        if (r3.zzj() == false) goto L_0x031f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0316, code lost:
        r12 = java.lang.Integer.valueOf(r3.zza());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x031f, code lost:
        r12 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0320, code lost:
        r11.put("filter_id", r12);
        r22 = r0;
        r11.put("property_name", r3.zze());
     */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzB(java.lang.String r24, java.util.List r25) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
            java.lang.String r4 = "app_id=? and audience_id=?"
            java.lang.String r0 = "app_id=?"
            java.lang.String r5 = "event_filters"
            java.lang.String r6 = "property_filters"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r25)
            r8 = 0
        L_0x0012:
            int r9 = r25.size()
            if (r8 >= r9) goto L_0x00df
            java.lang.Object r9 = r3.get(r8)
            com.google.android.gms.internal.measurement.zzei r9 = (com.google.android.gms.internal.measurement.zzei) r9
            com.google.android.gms.internal.measurement.zzkx r9 = r9.zzbB()
            com.google.android.gms.internal.measurement.zzeh r9 = (com.google.android.gms.internal.measurement.zzeh) r9
            int r11 = r9.zza()
            if (r11 == 0) goto L_0x00a0
            r11 = 0
        L_0x002b:
            int r12 = r9.zza()
            if (r11 >= r12) goto L_0x009d
            com.google.android.gms.internal.measurement.zzek r12 = r9.zze(r11)
            com.google.android.gms.internal.measurement.zzkx r12 = r12.zzbB()
            com.google.android.gms.internal.measurement.zzej r12 = (com.google.android.gms.internal.measurement.zzej) r12
            com.google.android.gms.internal.measurement.zzkx r13 = r12.zzav()
            com.google.android.gms.internal.measurement.zzej r13 = (com.google.android.gms.internal.measurement.zzej) r13
            java.lang.String r14 = r12.zze()
            java.lang.String r14 = com.google.android.gms.measurement.internal.zzhb.zzb(r14)
            if (r14 == 0) goto L_0x0050
            r13.zzb(r14)
            r14 = 1
            goto L_0x0051
        L_0x0050:
            r14 = 0
        L_0x0051:
            r15 = 0
        L_0x0052:
            int r7 = r12.zza()
            if (r15 >= r7) goto L_0x0088
            com.google.android.gms.internal.measurement.zzem r7 = r12.zzd(r15)
            java.lang.String r10 = r7.zze()
            r16 = r12
            java.lang.String[] r12 = com.google.android.gms.measurement.internal.zzhc.zza
            r17 = r4
            java.lang.String[] r4 = com.google.android.gms.measurement.internal.zzhc.zzb
            java.lang.String r4 = com.google.android.gms.measurement.internal.zzip.zzb(r10, r12, r4)
            if (r4 == 0) goto L_0x0081
            com.google.android.gms.internal.measurement.zzkx r7 = r7.zzbB()
            com.google.android.gms.internal.measurement.zzel r7 = (com.google.android.gms.internal.measurement.zzel) r7
            r7.zza(r4)
            com.google.android.gms.internal.measurement.zzlb r4 = r7.zzaD()
            com.google.android.gms.internal.measurement.zzem r4 = (com.google.android.gms.internal.measurement.zzem) r4
            r13.zzc(r15, r4)
            r14 = 1
        L_0x0081:
            int r15 = r15 + 1
            r12 = r16
            r4 = r17
            goto L_0x0052
        L_0x0088:
            r17 = r4
            if (r14 == 0) goto L_0x0098
            r9.zzc(r11, r13)
            com.google.android.gms.internal.measurement.zzlb r4 = r9.zzaD()
            com.google.android.gms.internal.measurement.zzei r4 = (com.google.android.gms.internal.measurement.zzei) r4
            r3.set(r8, r4)
        L_0x0098:
            int r11 = r11 + 1
            r4 = r17
            goto L_0x002b
        L_0x009d:
            r17 = r4
            goto L_0x00a2
        L_0x00a0:
            r17 = r4
        L_0x00a2:
            int r4 = r9.zzb()
            if (r4 == 0) goto L_0x00d9
            r4 = 0
        L_0x00a9:
            int r7 = r9.zzb()
            if (r4 >= r7) goto L_0x00d9
            com.google.android.gms.internal.measurement.zzet r7 = r9.zzf(r4)
            java.lang.String r10 = r7.zze()
            java.lang.String[] r11 = com.google.android.gms.measurement.internal.zzhd.zza
            java.lang.String[] r12 = com.google.android.gms.measurement.internal.zzhd.zzb
            java.lang.String r10 = com.google.android.gms.measurement.internal.zzip.zzb(r10, r11, r12)
            if (r10 == 0) goto L_0x00d6
            com.google.android.gms.internal.measurement.zzkx r7 = r7.zzbB()
            com.google.android.gms.internal.measurement.zzes r7 = (com.google.android.gms.internal.measurement.zzes) r7
            r7.zza(r10)
            r9.zzd(r4, r7)
            com.google.android.gms.internal.measurement.zzlb r7 = r9.zzaD()
            com.google.android.gms.internal.measurement.zzei r7 = (com.google.android.gms.internal.measurement.zzei) r7
            r3.set(r8, r7)
        L_0x00d6:
            int r4 = r4 + 1
            goto L_0x00a9
        L_0x00d9:
            int r8 = r8 + 1
            r4 = r17
            goto L_0x0012
        L_0x00df:
            r17 = r4
            r23.zzW()
            r23.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r25)
            android.database.sqlite.SQLiteDatabase r4 = r23.zzh()
            r4.beginTransaction()
            r23.zzW()     // Catch:{ all -> 0x04a2 }
            r23.zzg()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)     // Catch:{ all -> 0x04a2 }
            android.database.sqlite.SQLiteDatabase r7 = r23.zzh()     // Catch:{ all -> 0x04a2 }
            r8 = 1
            java.lang.String[] r9 = new java.lang.String[r8]     // Catch:{ all -> 0x04a2 }
            r8 = 0
            r9[r8] = r2     // Catch:{ all -> 0x04a2 }
            r7.delete(r6, r0, r9)     // Catch:{ all -> 0x04a2 }
            r8 = 1
            java.lang.String[] r9 = new java.lang.String[r8]     // Catch:{ all -> 0x04a2 }
            r8 = 0
            r9[r8] = r2     // Catch:{ all -> 0x04a2 }
            r7.delete(r5, r0, r9)     // Catch:{ all -> 0x04a2 }
            java.util.Iterator r7 = r25.iterator()     // Catch:{ all -> 0x04a2 }
        L_0x0117:
            boolean r0 = r7.hasNext()     // Catch:{ all -> 0x04a2 }
            if (r0 == 0) goto L_0x03bf
            java.lang.Object r0 = r7.next()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.internal.measurement.zzei r0 = (com.google.android.gms.internal.measurement.zzei) r0     // Catch:{ all -> 0x04a2 }
            r23.zzW()     // Catch:{ all -> 0x04a2 }
            r23.zzg()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ all -> 0x04a2 }
            boolean r10 = r0.zzk()     // Catch:{ all -> 0x04a2 }
            if (r10 != 0) goto L_0x0149
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = "Audience with no ID. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            r0.zzb(r8, r9)     // Catch:{ all -> 0x04a2 }
            goto L_0x0117
        L_0x0149:
            int r10 = r0.zza()     // Catch:{ all -> 0x04a2 }
            java.util.List r11 = r0.zzg()     // Catch:{ all -> 0x04a2 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ all -> 0x04a2 }
        L_0x0155:
            boolean r12 = r11.hasNext()     // Catch:{ all -> 0x04a2 }
            if (r12 == 0) goto L_0x017f
            java.lang.Object r12 = r11.next()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.internal.measurement.zzek r12 = (com.google.android.gms.internal.measurement.zzek) r12     // Catch:{ all -> 0x04a2 }
            boolean r12 = r12.zzp()     // Catch:{ all -> 0x04a2 }
            if (r12 != 0) goto L_0x0155
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = "Event filter with no ID. Audience definition ignored. appId, audienceId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            r0.zzc(r8, r9, r10)     // Catch:{ all -> 0x04a2 }
            goto L_0x0117
        L_0x017f:
            java.util.List r11 = r0.zzh()     // Catch:{ all -> 0x04a2 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ all -> 0x04a2 }
        L_0x0187:
            boolean r12 = r11.hasNext()     // Catch:{ all -> 0x04a2 }
            if (r12 == 0) goto L_0x01b2
            java.lang.Object r12 = r11.next()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.internal.measurement.zzet r12 = (com.google.android.gms.internal.measurement.zzet) r12     // Catch:{ all -> 0x04a2 }
            boolean r12 = r12.zzj()     // Catch:{ all -> 0x04a2 }
            if (r12 != 0) goto L_0x0187
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = "Property filter with no ID. Audience definition ignored. appId, audienceId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            r0.zzc(r8, r9, r10)     // Catch:{ all -> 0x04a2 }
            goto L_0x0117
        L_0x01b2:
            java.util.List r11 = r0.zzg()     // Catch:{ all -> 0x04a2 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ all -> 0x04a2 }
        L_0x01ba:
            boolean r12 = r11.hasNext()     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = "data"
            java.lang.String r13 = "session_scoped"
            java.lang.String r14 = "filter_id"
            java.lang.String r9 = "audience_id"
            java.lang.String r15 = "app_id"
            if (r12 == 0) goto L_0x02a4
            java.lang.Object r12 = r11.next()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.internal.measurement.zzek r12 = (com.google.android.gms.internal.measurement.zzek) r12     // Catch:{ all -> 0x04a2 }
            r23.zzW()     // Catch:{ all -> 0x04a2 }
            r23.zzg()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r12)     // Catch:{ all -> 0x04a2 }
            java.lang.String r21 = r12.zzg()     // Catch:{ all -> 0x04a2 }
            boolean r21 = r21.isEmpty()     // Catch:{ all -> 0x04a2 }
            if (r21 == 0) goto L_0x0218
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = "Event filter had no event name. Audience definition ignored. appId, audienceId, filterId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            boolean r13 = r12.zzp()     // Catch:{ all -> 0x04a2 }
            if (r13 == 0) goto L_0x020b
            int r12 = r12.zzb()     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x04a2 }
            r20 = r12
            goto L_0x020d
        L_0x020b:
            r20 = 0
        L_0x020d:
            java.lang.String r12 = java.lang.String.valueOf(r20)     // Catch:{ all -> 0x04a2 }
            r0.zzd(r8, r9, r11, r12)     // Catch:{ all -> 0x04a2 }
            r21 = r7
            goto L_0x0380
        L_0x0218:
            byte[] r3 = r12.zzbx()     // Catch:{ all -> 0x04a2 }
            r21 = r7
            android.content.ContentValues r7 = new android.content.ContentValues     // Catch:{ all -> 0x04a2 }
            r7.<init>()     // Catch:{ all -> 0x04a2 }
            r7.put(r15, r2)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            r7.put(r9, r15)     // Catch:{ all -> 0x04a2 }
            boolean r9 = r12.zzp()     // Catch:{ all -> 0x04a2 }
            if (r9 == 0) goto L_0x023c
            int r9 = r12.zzb()     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x04a2 }
            goto L_0x023d
        L_0x023c:
            r9 = 0
        L_0x023d:
            r7.put(r14, r9)     // Catch:{ all -> 0x04a2 }
            java.lang.String r9 = "event_name"
            java.lang.String r14 = r12.zzg()     // Catch:{ all -> 0x04a2 }
            r7.put(r9, r14)     // Catch:{ all -> 0x04a2 }
            boolean r9 = r12.zzq()     // Catch:{ all -> 0x04a2 }
            if (r9 == 0) goto L_0x0258
            boolean r9 = r12.zzn()     // Catch:{ all -> 0x04a2 }
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)     // Catch:{ all -> 0x04a2 }
            goto L_0x0259
        L_0x0258:
            r9 = 0
        L_0x0259:
            r7.put(r13, r9)     // Catch:{ all -> 0x04a2 }
            r7.put(r8, r3)     // Catch:{ all -> 0x04a2 }
            android.database.sqlite.SQLiteDatabase r3 = r23.zzh()     // Catch:{ SQLiteException -> 0x028e }
            r8 = 5
            r9 = 0
            long r7 = r3.insertWithOnConflict(r5, r9, r7, r8)     // Catch:{ SQLiteException -> 0x028e }
            r12 = -1
            int r3 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r3 != 0) goto L_0x0288
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzt     // Catch:{ SQLiteException -> 0x028e }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ SQLiteException -> 0x028e }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ SQLiteException -> 0x028e }
            java.lang.String r7 = "Failed to insert event filter (got -1). appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ SQLiteException -> 0x028e }
            r3.zzb(r7, r8)     // Catch:{ SQLiteException -> 0x028e }
            r3 = r25
            r7 = r21
            goto L_0x01ba
        L_0x0288:
            r3 = r25
            r7 = r21
            goto L_0x01ba
        L_0x028e:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x04a2 }
            java.lang.String r7 = "Error storing event filter. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            r3.zzc(r7, r8, r0)     // Catch:{ all -> 0x04a2 }
            goto L_0x0380
        L_0x02a4:
            r21 = r7
            java.util.List r0 = r0.zzh()     // Catch:{ all -> 0x04a2 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x04a2 }
        L_0x02ae:
            boolean r3 = r0.hasNext()     // Catch:{ all -> 0x04a2 }
            if (r3 == 0) goto L_0x03b7
            java.lang.Object r3 = r0.next()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.internal.measurement.zzet r3 = (com.google.android.gms.internal.measurement.zzet) r3     // Catch:{ all -> 0x04a2 }
            r23.zzW()     // Catch:{ all -> 0x04a2 }
            r23.zzg()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)     // Catch:{ all -> 0x04a2 }
            java.lang.String r7 = r3.zze()     // Catch:{ all -> 0x04a2 }
            boolean r7 = r7.isEmpty()     // Catch:{ all -> 0x04a2 }
            if (r7 == 0) goto L_0x02fd
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x04a2 }
            java.lang.String r7 = "Property filter had no property name. Audience definition ignored. appId, audienceId, filterId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            boolean r11 = r3.zzj()     // Catch:{ all -> 0x04a2 }
            if (r11 == 0) goto L_0x02f3
            int r3 = r3.zza()     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x04a2 }
            goto L_0x02f4
        L_0x02f3:
            r3 = 0
        L_0x02f4:
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x04a2 }
            r0.zzd(r7, r8, r9, r3)     // Catch:{ all -> 0x04a2 }
            goto L_0x0380
        L_0x02fd:
            byte[] r7 = r3.zzbx()     // Catch:{ all -> 0x04a2 }
            android.content.ContentValues r11 = new android.content.ContentValues     // Catch:{ all -> 0x04a2 }
            r11.<init>()     // Catch:{ all -> 0x04a2 }
            r11.put(r15, r2)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            r11.put(r9, r12)     // Catch:{ all -> 0x04a2 }
            boolean r12 = r3.zzj()     // Catch:{ all -> 0x04a2 }
            if (r12 == 0) goto L_0x031f
            int r12 = r3.zza()     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x04a2 }
            goto L_0x0320
        L_0x031f:
            r12 = 0
        L_0x0320:
            r11.put(r14, r12)     // Catch:{ all -> 0x04a2 }
            java.lang.String r12 = "property_name"
            r22 = r0
            java.lang.String r0 = r3.zze()     // Catch:{ all -> 0x04a2 }
            r11.put(r12, r0)     // Catch:{ all -> 0x04a2 }
            boolean r0 = r3.zzk()     // Catch:{ all -> 0x04a2 }
            if (r0 == 0) goto L_0x033d
            boolean r0 = r3.zzi()     // Catch:{ all -> 0x04a2 }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x04a2 }
            goto L_0x033e
        L_0x033d:
            r0 = 0
        L_0x033e:
            r11.put(r13, r0)     // Catch:{ all -> 0x04a2 }
            r11.put(r8, r7)     // Catch:{ all -> 0x04a2 }
            android.database.sqlite.SQLiteDatabase r0 = r23.zzh()     // Catch:{ SQLiteException -> 0x036c }
            r3 = 0
            r7 = 5
            long r11 = r0.insertWithOnConflict(r6, r3, r11, r7)     // Catch:{ SQLiteException -> 0x036c }
            r18 = -1
            int r0 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1))
            if (r0 != 0) goto L_0x0368
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x036c }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x036c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteException -> 0x036c }
            java.lang.String r3 = "Failed to insert property filter (got -1). appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ SQLiteException -> 0x036c }
            r0.zzb(r3, r7)     // Catch:{ SQLiteException -> 0x036c }
            goto L_0x0380
        L_0x0368:
            r0 = r22
            goto L_0x02ae
        L_0x036c:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x04a2 }
            java.lang.String r7 = "Error storing property filter. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            r3.zzc(r7, r8, r0)     // Catch:{ all -> 0x04a2 }
        L_0x0380:
            r23.zzW()     // Catch:{ all -> 0x04a2 }
            r23.zzg()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)     // Catch:{ all -> 0x04a2 }
            android.database.sqlite.SQLiteDatabase r0 = r23.zzh()     // Catch:{ all -> 0x04a2 }
            r3 = 2
            java.lang.String[] r7 = new java.lang.String[r3]     // Catch:{ all -> 0x04a2 }
            r3 = 0
            r7[r3] = r2     // Catch:{ all -> 0x04a2 }
            java.lang.String r3 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            r8 = 1
            r7[r8] = r3     // Catch:{ all -> 0x04a2 }
            r3 = r17
            r0.delete(r6, r3, r7)     // Catch:{ all -> 0x04a2 }
            r7 = 2
            java.lang.String[] r7 = new java.lang.String[r7]     // Catch:{ all -> 0x04a2 }
            r8 = 0
            r7[r8] = r2     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x04a2 }
            r9 = 1
            r7[r9] = r8     // Catch:{ all -> 0x04a2 }
            r0.delete(r5, r3, r7)     // Catch:{ all -> 0x04a2 }
            r17 = r3
            r7 = r21
            r3 = r25
            goto L_0x0117
        L_0x03b7:
            r3 = r17
            r7 = r21
            r3 = r25
            goto L_0x0117
        L_0x03bf:
            r3 = 0
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x04a2 }
            r0.<init>()     // Catch:{ all -> 0x04a2 }
            java.util.Iterator r5 = r25.iterator()     // Catch:{ all -> 0x04a2 }
        L_0x03c9:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x04a2 }
            if (r6 == 0) goto L_0x03e9
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.internal.measurement.zzei r6 = (com.google.android.gms.internal.measurement.zzei) r6     // Catch:{ all -> 0x04a2 }
            boolean r7 = r6.zzk()     // Catch:{ all -> 0x04a2 }
            if (r7 == 0) goto L_0x03e4
            int r6 = r6.zza()     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x04a2 }
            goto L_0x03e5
        L_0x03e4:
            r9 = r3
        L_0x03e5:
            r0.add(r9)     // Catch:{ all -> 0x04a2 }
            goto L_0x03c9
        L_0x03e9:
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)     // Catch:{ all -> 0x04a2 }
            r23.zzW()     // Catch:{ all -> 0x04a2 }
            r23.zzg()     // Catch:{ all -> 0x04a2 }
            android.database.sqlite.SQLiteDatabase r3 = r23.zzh()     // Catch:{ all -> 0x04a2 }
            java.lang.String r5 = "select count(1) from audience_filter_values where app_id=?"
            r6 = 1
            java.lang.String[] r7 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x0487 }
            r6 = 0
            r7[r6] = r2     // Catch:{ SQLiteException -> 0x0487 }
            long r5 = r1.zzZ(r5, r7)     // Catch:{ SQLiteException -> 0x0487 }
            com.google.android.gms.measurement.internal.zzge r7 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzag r7 = r7.zzf()     // Catch:{ all -> 0x04a2 }
            r8 = 2000(0x7d0, float:2.803E-42)
            com.google.android.gms.measurement.internal.zzeg r9 = com.google.android.gms.measurement.internal.zzeh.zzF     // Catch:{ all -> 0x04a2 }
            int r7 = r7.zze(r2, r9)     // Catch:{ all -> 0x04a2 }
            int r7 = java.lang.Math.min(r8, r7)     // Catch:{ all -> 0x04a2 }
            r8 = 0
            int r7 = java.lang.Math.max(r8, r7)     // Catch:{ all -> 0x04a2 }
            long r8 = (long) r7     // Catch:{ all -> 0x04a2 }
            int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r5 > 0) goto L_0x0420
            goto L_0x049b
        L_0x0420:
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ all -> 0x04a2 }
            r5.<init>()     // Catch:{ all -> 0x04a2 }
            r8 = 0
        L_0x0426:
            int r6 = r0.size()     // Catch:{ all -> 0x04a2 }
            if (r8 >= r6) goto L_0x0442
            java.lang.Object r6 = r0.get(r8)     // Catch:{ all -> 0x04a2 }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ all -> 0x04a2 }
            if (r6 == 0) goto L_0x049b
            int r6 = r6.intValue()     // Catch:{ all -> 0x04a2 }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ all -> 0x04a2 }
            r5.add(r6)     // Catch:{ all -> 0x04a2 }
            int r8 = r8 + 1
            goto L_0x0426
        L_0x0442:
            java.lang.String r0 = ","
            java.lang.String r0 = android.text.TextUtils.join(r0, r5)     // Catch:{ all -> 0x04a2 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x04a2 }
            r5.<init>()     // Catch:{ all -> 0x04a2 }
            java.lang.String r6 = "("
            r5.append(r6)     // Catch:{ all -> 0x04a2 }
            r5.append(r0)     // Catch:{ all -> 0x04a2 }
            java.lang.String r0 = ")"
            r5.append(r0)     // Catch:{ all -> 0x04a2 }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x04a2 }
            java.lang.String r5 = "audience_filter_values"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x04a2 }
            r6.<init>()     // Catch:{ all -> 0x04a2 }
            java.lang.String r8 = "audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in "
            r6.append(r8)     // Catch:{ all -> 0x04a2 }
            r6.append(r0)     // Catch:{ all -> 0x04a2 }
            java.lang.String r0 = " order by rowid desc limit -1 offset ?)"
            r6.append(r0)     // Catch:{ all -> 0x04a2 }
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x04a2 }
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ all -> 0x04a2 }
            r8 = 0
            r6[r8] = r2     // Catch:{ all -> 0x04a2 }
            java.lang.String r2 = java.lang.Integer.toString(r7)     // Catch:{ all -> 0x04a2 }
            r7 = 1
            r6[r7] = r2     // Catch:{ all -> 0x04a2 }
            r3.delete(r5, r0, r6)     // Catch:{ all -> 0x04a2 }
            goto L_0x049b
        L_0x0487:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzt     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x04a2 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x04a2 }
            java.lang.String r5 = "Database error querying filters. appId"
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzeu.zzn(r24)     // Catch:{ all -> 0x04a2 }
            r3.zzc(r5, r2, r0)     // Catch:{ all -> 0x04a2 }
        L_0x049b:
            r4.setTransactionSuccessful()     // Catch:{ all -> 0x04a2 }
            r4.endTransaction()
            return
        L_0x04a2:
            r0 = move-exception
            r4.endTransaction()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzB(java.lang.String, java.util.List):void");
    }

    @WorkerThread
    public final void zzC() {
        zzW();
        zzh().setTransactionSuccessful();
    }

    @WorkerThread
    public final void zzD(zzh zzh2) {
        Preconditions.checkNotNull(zzh2);
        zzg();
        zzW();
        String zzu = zzh2.zzu();
        Preconditions.checkNotNull(zzu);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzu);
        contentValues.put("app_instance_id", zzh2.zzv());
        contentValues.put("gmp_app_id", zzh2.zzz());
        contentValues.put("resettable_device_id_hash", zzh2.zzB());
        contentValues.put("last_bundle_index", Long.valueOf(zzh2.zzo()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzh2.zzp()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzh2.zzn()));
        contentValues.put(SharePreferenceUtils.KEY_APP_VERSION, zzh2.zzx());
        contentValues.put("app_store", zzh2.zzw());
        contentValues.put("gmp_version", Long.valueOf(zzh2.zzm()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzh2.zzj()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzh2.zzal()));
        contentValues.put("day", Long.valueOf(zzh2.zzi()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzh2.zzg()));
        contentValues.put("daily_events_count", Long.valueOf(zzh2.zzf()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzh2.zzd()));
        contentValues.put("config_fetched_time", Long.valueOf(zzh2.zzc()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzh2.zzl()));
        contentValues.put("app_version_int", Long.valueOf(zzh2.zzb()));
        contentValues.put("firebase_instance_id", zzh2.zzy());
        contentValues.put("daily_error_events_count", Long.valueOf(zzh2.zze()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzh2.zzh()));
        contentValues.put("health_monitor_sample", zzh2.zzA());
        zzh2.zza();
        contentValues.put("android_id", 0L);
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzh2.zzak()));
        contentValues.put("admob_app_id", zzh2.zzs());
        contentValues.put("dynamite_version", Long.valueOf(zzh2.zzk()));
        contentValues.put("session_stitching_token", zzh2.zzC());
        contentValues.put("sgtm_upload_enabled", Boolean.valueOf(zzh2.zzan()));
        contentValues.put("target_os_version", Long.valueOf(zzh2.zzq()));
        List zzD = zzh2.zzD();
        if (zzD != null) {
            if (zzD.isEmpty()) {
                this.zzt.zzaA().zzk().zzb("Safelisted events should not be an empty list. appId", zzu);
            } else {
                contentValues.put("safelisted_events", TextUtils.join(",", zzD));
            }
        }
        zzop.zzc();
        if (this.zzt.zzf().zzs((String) null, zzeh.zzak) && !contentValues.containsKey("safelisted_events")) {
            contentValues.put("safelisted_events", (String) null);
        }
        try {
            SQLiteDatabase zzh3 = zzh();
            if (((long) zzh3.update("apps", contentValues, "app_id = ?", new String[]{zzu})) == 0 && zzh3.insertWithOnConflict("apps", (String) null, contentValues, 5) == -1) {
                this.zzt.zzaA().zzd().zzb("Failed to insert/update app (got -1). appId", zzeu.zzn(zzu));
            }
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzc("Error storing app. appId", zzeu.zzn(zzu), e);
        }
    }

    @WorkerThread
    public final void zzE(zzas zzas) {
        long j;
        Preconditions.checkNotNull(zzas);
        zzg();
        zzW();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzas.zza);
        contentValues.put("name", zzas.zzb);
        contentValues.put("lifetime_count", Long.valueOf(zzas.zzc));
        contentValues.put("current_bundle_count", Long.valueOf(zzas.zzd));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzas.zzf));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzas.zzg));
        contentValues.put("last_bundled_day", zzas.zzh);
        contentValues.put("last_sampled_complex_event_id", zzas.zzi);
        contentValues.put("last_sampling_rate", zzas.zzj);
        contentValues.put("current_session_count", Long.valueOf(zzas.zze));
        Boolean bool = zzas.zzk;
        if (bool == null || !bool.booleanValue()) {
            j = null;
        } else {
            j = 1L;
        }
        contentValues.put("last_exempt_from_sampling", j);
        try {
            if (zzh().insertWithOnConflict("events", (String) null, contentValues, 5) == -1) {
                this.zzt.zzaA().zzd().zzb("Failed to insert/update event aggregates (got -1). appId", zzeu.zzn(zzas.zza));
            }
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzc("Error storing event aggregates. appId", zzeu.zzn(zzas.zza), e);
        }
    }

    public final boolean zzF() {
        return zzZ("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzG() {
        return zzZ("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    public final boolean zzH() {
        return zzZ("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public final boolean zzI() {
        Context zzaw = this.zzt.zzaw();
        this.zzt.zzf();
        return zzaw.getDatabasePath("google_app_measurement.db").exists();
    }

    public final boolean zzJ(String str, Long l, long j, zzft zzft) {
        zzg();
        zzW();
        Preconditions.checkNotNull(zzft);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        byte[] zzbx = zzft.zzbx();
        this.zzt.zzaA().zzj().zzc("Saving complex main event, appId, data size", this.zzt.zzj().zzd(str), Integer.valueOf(zzbx.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("event_id", l);
        contentValues.put("children_to_process", Long.valueOf(j));
        contentValues.put("main_event", zzbx);
        try {
            if (zzh().insertWithOnConflict("main_event_params", (String) null, contentValues, 5) != -1) {
                return true;
            }
            this.zzt.zzaA().zzd().zzb("Failed to insert complex main event (got -1). appId", zzeu.zzn(str));
            return false;
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzc("Error storing complex main event. appId", zzeu.zzn(str), e);
            return false;
        }
    }

    @WorkerThread
    public final boolean zzK(zzac zzac) {
        Preconditions.checkNotNull(zzac);
        zzg();
        zzW();
        String str = zzac.zza;
        Preconditions.checkNotNull(str);
        if (zzp(str, zzac.zzc.zzb) == null) {
            long zzZ = zzZ("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{str});
            this.zzt.zzf();
            if (zzZ >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("origin", zzac.zzb);
        contentValues.put("name", zzac.zzc.zzb);
        zzV(contentValues, "value", Preconditions.checkNotNull(zzac.zzc.zza()));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.valueOf(zzac.zze));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, zzac.zzf);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.valueOf(zzac.zzh));
        contentValues.put("timed_out_event", this.zzt.zzv().zzap(zzac.zzg));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.valueOf(zzac.zzd));
        contentValues.put("triggered_event", this.zzt.zzv().zzap(zzac.zzi));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.valueOf(zzac.zzc.zzc));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.valueOf(zzac.zzj));
        contentValues.put("expired_event", this.zzt.zzv().zzap(zzac.zzk));
        try {
            if (zzh().insertWithOnConflict("conditional_properties", (String) null, contentValues, 5) == -1) {
                this.zzt.zzaA().zzd().zzb("Failed to insert/update conditional user property (got -1)", zzeu.zzn(str));
            }
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzc("Error storing conditional user property", zzeu.zzn(str), e);
        }
        return true;
    }

    @WorkerThread
    public final boolean zzL(zzll zzll) {
        Preconditions.checkNotNull(zzll);
        zzg();
        zzW();
        if (zzp(zzll.zza, zzll.zzc) == null) {
            if (zzlo.zzak(zzll.zzc)) {
                if (zzZ("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzll.zza}) >= ((long) this.zzt.zzf().zzf(zzll.zza, zzeh.zzG, 25, 100))) {
                    return false;
                }
            } else if (!"_npa".equals(zzll.zzc)) {
                long zzZ = zzZ("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzll.zza, zzll.zzb});
                this.zzt.zzf();
                if (zzZ >= 25) {
                    return false;
                }
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzll.zza);
        contentValues.put("origin", zzll.zzb);
        contentValues.put("name", zzll.zzc);
        contentValues.put("set_timestamp", Long.valueOf(zzll.zzd));
        zzV(contentValues, "value", zzll.zze);
        try {
            if (zzh().insertWithOnConflict("user_attributes", (String) null, contentValues, 5) == -1) {
                this.zzt.zzaA().zzd().zzb("Failed to insert/update user property (got -1). appId", zzeu.zzn(zzll.zza));
            }
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzc("Error storing user property. appId", zzeu.zzn(zzll.zza), e);
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.String[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.String[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: java.lang.String[]} */
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x023a  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:125:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzU(java.lang.String r21, long r22, long r24, com.google.android.gms.measurement.internal.zzld r26) {
        /*
            r20 = this;
            r1 = r20
            r2 = r26
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r26)
            r20.zzg()
            r20.zzW()
            r3 = 0
            android.database.sqlite.SQLiteDatabase r0 = r20.zzh()     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r5 = ""
            r13 = -1
            r15 = 2
            r12 = 1
            r11 = 0
            if (r4 == 0) goto L_0x007c
            int r4 = (r24 > r13 ? 1 : (r24 == r13 ? 0 : -1))
            if (r4 == 0) goto L_0x0032
            java.lang.String[] r6 = new java.lang.String[r15]     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r7 = java.lang.String.valueOf(r24)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r6[r11] = r7     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r7 = java.lang.String.valueOf(r22)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r6[r12] = r7     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            goto L_0x003a
        L_0x0032:
            java.lang.String[] r6 = new java.lang.String[r12]     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r7 = java.lang.String.valueOf(r22)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r6[r11] = r7     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
        L_0x003a:
            if (r4 == 0) goto L_0x003f
            java.lang.String r5 = "rowid <= ? and "
            goto L_0x0040
        L_0x003f:
        L_0x0040:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r4.<init>()     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r7 = "select app_id, metadata_fingerprint from raw_events where "
            r4.append(r7)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r4.append(r5)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r5 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;"
            r4.append(r5)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r4 = r4.toString()     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            android.database.Cursor r4 = r0.rawQuery(r4, r6)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            boolean r5 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x0079, all -> 0x0075 }
            if (r5 != 0) goto L_0x0064
            r4.close()
            return
        L_0x0064:
            java.lang.String r3 = r4.getString(r11)     // Catch:{ SQLiteException -> 0x0079, all -> 0x0075 }
            java.lang.String r5 = r4.getString(r12)     // Catch:{ SQLiteException -> 0x0079, all -> 0x0075 }
            r4.close()     // Catch:{ SQLiteException -> 0x0079, all -> 0x0075 }
            r16 = r4
            r17 = r5
            goto L_0x00c5
        L_0x0075:
            r0 = move-exception
            r3 = r4
            goto L_0x0240
        L_0x0079:
            r0 = move-exception
            goto L_0x0225
        L_0x007c:
            int r4 = (r24 > r13 ? 1 : (r24 == r13 ? 0 : -1))
            if (r4 == 0) goto L_0x008b
            java.lang.String[] r6 = new java.lang.String[r15]     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r6[r11] = r3     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r7 = java.lang.String.valueOf(r24)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r6[r12] = r7     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            goto L_0x008f
        L_0x008b:
            java.lang.String[] r6 = new java.lang.String[]{r3}     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
        L_0x008f:
            if (r4 == 0) goto L_0x0094
            java.lang.String r5 = " and rowid <= ?"
            goto L_0x0095
        L_0x0094:
        L_0x0095:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r4.<init>()     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r7 = "select metadata_fingerprint from raw_events where app_id = ?"
            r4.append(r7)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            r4.append(r5)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r5 = " order by rowid limit 1;"
            r4.append(r5)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            java.lang.String r4 = r4.toString()     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            android.database.Cursor r4 = r0.rawQuery(r4, r6)     // Catch:{ SQLiteException -> 0x0223, all -> 0x0221 }
            boolean r5 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x021f }
            if (r5 != 0) goto L_0x00b9
            r4.close()
            return
        L_0x00b9:
            java.lang.String r5 = r4.getString(r11)     // Catch:{ SQLiteException -> 0x021f }
            r4.close()     // Catch:{ SQLiteException -> 0x021f }
            r16 = r4
            r17 = r5
        L_0x00c5:
            java.lang.String r5 = "raw_events_metadata"
            java.lang.String r4 = "metadata"
            java.lang.String[] r6 = new java.lang.String[]{r4}     // Catch:{ SQLiteException -> 0x021b, all -> 0x0217 }
            java.lang.String r7 = "app_id = ? and metadata_fingerprint = ?"
            java.lang.String[] r8 = new java.lang.String[r15]     // Catch:{ SQLiteException -> 0x021b, all -> 0x0217 }
            r8[r11] = r3     // Catch:{ SQLiteException -> 0x021b, all -> 0x0217 }
            r8[r12] = r17     // Catch:{ SQLiteException -> 0x021b, all -> 0x0217 }
            r9 = 0
            r10 = 0
            java.lang.String r18 = "rowid"
            java.lang.String r19 = "2"
            r4 = r0
            r15 = r11
            r11 = r18
            r12 = r19
            android.database.Cursor r12 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ SQLiteException -> 0x021b, all -> 0x0217 }
            boolean r4 = r12.moveToFirst()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            if (r4 != 0) goto L_0x0102
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            java.lang.String r2 = "Raw event metadata record is missing. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r0.zzb(r2, r4)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r12.close()
            return
        L_0x0102:
            byte[] r4 = r12.getBlob(r15)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            com.google.android.gms.internal.measurement.zzgc r5 = com.google.android.gms.internal.measurement.zzgd.zzu()     // Catch:{ IOException -> 0x01ed }
            com.google.android.gms.internal.measurement.zzmh r4 = com.google.android.gms.measurement.internal.zzli.zzl(r5, r4)     // Catch:{ IOException -> 0x01ed }
            com.google.android.gms.internal.measurement.zzgc r4 = (com.google.android.gms.internal.measurement.zzgc) r4     // Catch:{ IOException -> 0x01ed }
            com.google.android.gms.internal.measurement.zzlb r4 = r4.zzaD()     // Catch:{ IOException -> 0x01ed }
            com.google.android.gms.internal.measurement.zzgd r4 = (com.google.android.gms.internal.measurement.zzgd) r4     // Catch:{ IOException -> 0x01ed }
            boolean r5 = r12.moveToNext()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            if (r5 == 0) goto L_0x0130
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzk()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            java.lang.String r6 = "Get multiple raw event metadata records, expected one. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r5.zzb(r6, r7)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
        L_0x0130:
            r12.close()     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r2.zza = r4     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            int r4 = (r24 > r13 ? 1 : (r24 == r13 ? 0 : -1))
            r13 = 3
            if (r4 == 0) goto L_0x0150
            java.lang.String r4 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?"
            java.lang.String[] r5 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r5[r15] = r3     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r14 = 1
            r5[r14] = r17     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            java.lang.String r6 = java.lang.String.valueOf(r24)     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r7 = 2
            r5[r7] = r6     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r7 = r4
            r8 = r5
            goto L_0x015c
        L_0x0150:
            r14 = 1
            java.lang.String r4 = "app_id = ? and metadata_fingerprint = ?"
            r5 = 2
            java.lang.String[] r6 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r6[r15] = r3     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r6[r14] = r17     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r7 = r4
            r8 = r6
        L_0x015c:
            java.lang.String r5 = "raw_events"
            java.lang.String r4 = "rowid"
            java.lang.String r6 = "name"
            java.lang.String r9 = "timestamp"
            java.lang.String r10 = "data"
            java.lang.String[] r6 = new java.lang.String[]{r4, r6, r9, r10}     // Catch:{ SQLiteException -> 0x0211, all -> 0x020b }
            r9 = 0
            r10 = 0
            java.lang.String r11 = "rowid"
            r16 = 0
            r4 = r0
            r17 = r12
            r12 = r16
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ SQLiteException -> 0x0209, all -> 0x0207 }
            boolean r0 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            if (r0 == 0) goto L_0x01d1
        L_0x0180:
            long r5 = r4.getLong(r15)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            byte[] r0 = r4.getBlob(r13)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.internal.measurement.zzfs r7 = com.google.android.gms.internal.measurement.zzft.zze()     // Catch:{ IOException -> 0x01b2 }
            com.google.android.gms.internal.measurement.zzmh r0 = com.google.android.gms.measurement.internal.zzli.zzl(r7, r0)     // Catch:{ IOException -> 0x01b2 }
            com.google.android.gms.internal.measurement.zzfs r0 = (com.google.android.gms.internal.measurement.zzfs) r0     // Catch:{ IOException -> 0x01b2 }
            java.lang.String r7 = r4.getString(r14)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            r0.zzi(r7)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            r7 = 2
            long r8 = r4.getLong(r7)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            r0.zzm(r8)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.internal.measurement.zzlb r0 = r0.zzaD()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.internal.measurement.zzft r0 = (com.google.android.gms.internal.measurement.zzft) r0     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            boolean r0 = r2.zza(r5, r0)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            if (r0 != 0) goto L_0x01c7
            r4.close()
            return
        L_0x01b2:
            r0 = move-exception
            r7 = 2
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            java.lang.String r6 = "Data loss. Failed to merge raw event. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            r5.zzc(r6, r8, r0)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
        L_0x01c7:
            boolean r0 = r4.moveToNext()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            if (r0 != 0) goto L_0x0180
            r4.close()
            return
        L_0x01d1:
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            java.lang.String r2 = "Raw event data disappeared while in transaction. appId"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            r0.zzb(r2, r5)     // Catch:{ SQLiteException -> 0x01eb, all -> 0x01e8 }
            r4.close()
            return
        L_0x01e8:
            r0 = move-exception
            r3 = r4
            goto L_0x0240
        L_0x01eb:
            r0 = move-exception
            goto L_0x0225
        L_0x01ed:
            r0 = move-exception
            r17 = r12
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ SQLiteException -> 0x0209, all -> 0x0207 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x0209, all -> 0x0207 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x0209, all -> 0x0207 }
            java.lang.String r4 = "Data loss. Failed to merge raw event metadata. appId"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x0209, all -> 0x0207 }
            r2.zzc(r4, r5, r0)     // Catch:{ SQLiteException -> 0x0209, all -> 0x0207 }
            r17.close()
            return
        L_0x0207:
            r0 = move-exception
            goto L_0x020e
        L_0x0209:
            r0 = move-exception
            goto L_0x0214
        L_0x020b:
            r0 = move-exception
            r17 = r12
        L_0x020e:
            r3 = r17
            goto L_0x0240
        L_0x0211:
            r0 = move-exception
            r17 = r12
        L_0x0214:
            r4 = r17
            goto L_0x0225
        L_0x0217:
            r0 = move-exception
            r3 = r16
            goto L_0x0240
        L_0x021b:
            r0 = move-exception
            r4 = r16
            goto L_0x0225
        L_0x021f:
            r0 = move-exception
            goto L_0x0225
        L_0x0221:
            r0 = move-exception
            goto L_0x0240
        L_0x0223:
            r0 = move-exception
            r4 = r3
        L_0x0225:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x023e }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x023e }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x023e }
            java.lang.String r5 = "Data loss. Error selecting raw event. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x023e }
            r2.zzc(r5, r3, r0)     // Catch:{ all -> 0x023e }
            if (r4 == 0) goto L_0x023d
            r4.close()
        L_0x023d:
            return
        L_0x023e:
            r0 = move-exception
            r3 = r4
        L_0x0240:
            if (r3 == 0) goto L_0x0245
            r3.close()
        L_0x0245:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzU(java.lang.String, long, long, com.google.android.gms.measurement.internal.zzld):void");
    }

    @WorkerThread
    public final int zza(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzW();
        try {
            return zzh().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzd("Error deleting conditional property", zzeu.zzn(str), this.zzt.zzj().zzf(str2), e);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zzb() {
        return false;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    @VisibleForTesting
    public final long zzc(String str, String str2) {
        long j;
        SQLiteException e;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty("first_open_count");
        zzg();
        zzW();
        SQLiteDatabase zzh2 = zzh();
        zzh2.beginTransaction();
        try {
            j = zzaa("select " + "first_open_count" + " from app2 where app_id=?", new String[]{str}, -1);
            if (j == -1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("first_open_count", 0);
                contentValues.put("previous_install_count", 0);
                if (zzh2.insertWithOnConflict("app2", (String) null, contentValues, 5) == -1) {
                    this.zzt.zzaA().zzd().zzc("Failed to insert column (got -1). appId", zzeu.zzn(str), "first_open_count");
                    zzh2.endTransaction();
                    return -1;
                }
                j = 0;
            }
            try {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("app_id", str);
                contentValues2.put("first_open_count", Long.valueOf(1 + j));
                if (((long) zzh2.update("app2", contentValues2, "app_id = ?", new String[]{str})) == 0) {
                    this.zzt.zzaA().zzd().zzc("Failed to update column (got 0). appId", zzeu.zzn(str), "first_open_count");
                    zzh2.endTransaction();
                    return -1;
                }
                zzh2.setTransactionSuccessful();
                zzh2.endTransaction();
                return j;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    this.zzt.zzaA().zzd().zzd("Error inserting column. appId", zzeu.zzn(str), "first_open_count", e);
                    zzh2.endTransaction();
                    return j;
                } catch (Throwable th) {
                    zzh2.endTransaction();
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            j = 0;
            e = e3;
            this.zzt.zzaA().zzd().zzd("Error inserting column. appId", zzeu.zzn(str), "first_open_count", e);
            zzh2.endTransaction();
            return j;
        }
    }

    @WorkerThread
    public final long zzd() {
        return zzaa("select max(bundle_end_timestamp) from queue", (String[]) null, 0);
    }

    @WorkerThread
    public final long zze() {
        return zzaa("select max(timestamp) from raw_events", (String[]) null, 0);
    }

    public final long zzf(String str) {
        Preconditions.checkNotEmpty(str);
        return zzaa("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final SQLiteDatabase zzh() {
        zzg();
        try {
            return this.zzj.getWritableDatabase();
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzk().zzb("Error opening database", e);
            throw e;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00de  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.os.Bundle zzi(java.lang.String r8) {
        /*
            r7 = this;
            r7.zzg()
            r7.zzW()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r7.zzh()     // Catch:{ SQLiteException -> 0x00c3, all -> 0x00c1 }
            java.lang.String r2 = "select parameters from default_event_params where app_id=?"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x00c3, all -> 0x00c1 }
            r4 = 0
            r3[r4] = r8     // Catch:{ SQLiteException -> 0x00c3, all -> 0x00c1 }
            android.database.Cursor r1 = r1.rawQuery(r2, r3)     // Catch:{ SQLiteException -> 0x00c3, all -> 0x00c1 }
            boolean r2 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            if (r2 != 0) goto L_0x0030
            com.google.android.gms.measurement.internal.zzge r8 = r7.zzt     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzj()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            java.lang.String r2 = "Default event parameters not found"
            r8.zza(r2)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r1.close()
            return r0
        L_0x0030:
            byte[] r2 = r1.getBlob(r4)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            com.google.android.gms.internal.measurement.zzfs r3 = com.google.android.gms.internal.measurement.zzft.zze()     // Catch:{ IOException -> 0x00a4 }
            com.google.android.gms.internal.measurement.zzmh r2 = com.google.android.gms.measurement.internal.zzli.zzl(r3, r2)     // Catch:{ IOException -> 0x00a4 }
            com.google.android.gms.internal.measurement.zzfs r2 = (com.google.android.gms.internal.measurement.zzfs) r2     // Catch:{ IOException -> 0x00a4 }
            com.google.android.gms.internal.measurement.zzlb r2 = r2.zzaD()     // Catch:{ IOException -> 0x00a4 }
            com.google.android.gms.internal.measurement.zzft r2 = (com.google.android.gms.internal.measurement.zzft) r2     // Catch:{ IOException -> 0x00a4 }
            com.google.android.gms.measurement.internal.zzlg r8 = r7.zzf     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r8.zzu()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            java.util.List r8 = r2.zzi()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            android.os.Bundle r2 = new android.os.Bundle     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r2.<init>()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
        L_0x0057:
            boolean r3 = r8.hasNext()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            if (r3 == 0) goto L_0x009f
            java.lang.Object r3 = r8.next()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            com.google.android.gms.internal.measurement.zzfx r3 = (com.google.android.gms.internal.measurement.zzfx) r3     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            java.lang.String r4 = r3.zzg()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            boolean r5 = r3.zzu()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            if (r5 == 0) goto L_0x0075
            double r5 = r3.zza()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r2.putDouble(r4, r5)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            goto L_0x0057
        L_0x0075:
            boolean r5 = r3.zzv()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            if (r5 == 0) goto L_0x0083
            float r3 = r3.zzb()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r2.putFloat(r4, r3)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            goto L_0x0057
        L_0x0083:
            boolean r5 = r3.zzy()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            if (r5 == 0) goto L_0x0091
            java.lang.String r3 = r3.zzh()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r2.putString(r4, r3)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            goto L_0x0057
        L_0x0091:
            boolean r5 = r3.zzw()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            if (r5 == 0) goto L_0x0057
            long r5 = r3.zzd()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r2.putLong(r4, r5)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            goto L_0x0057
        L_0x009f:
            r1.close()
            return r2
        L_0x00a4:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzge r3 = r7.zzt     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            java.lang.String r4 = "Failed to retrieve default event parameters. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r8)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r3.zzc(r4, r8, r2)     // Catch:{ SQLiteException -> 0x00bf, all -> 0x00bc }
            r1.close()
            return r0
        L_0x00bc:
            r8 = move-exception
            r0 = r1
            goto L_0x00dc
        L_0x00bf:
            r8 = move-exception
            goto L_0x00c5
        L_0x00c1:
            r8 = move-exception
            goto L_0x00dc
        L_0x00c3:
            r8 = move-exception
            r1 = r0
        L_0x00c5:
            com.google.android.gms.measurement.internal.zzge r2 = r7.zzt     // Catch:{ all -> 0x00da }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x00da }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x00da }
            java.lang.String r3 = "Error selecting default event parameters"
            r2.zzb(r3, r8)     // Catch:{ all -> 0x00da }
            if (r1 == 0) goto L_0x00d9
            r1.close()
        L_0x00d9:
            return r0
        L_0x00da:
            r8 = move-exception
            r0 = r1
        L_0x00dc:
            if (r0 == 0) goto L_0x00e1
            r0.close()
        L_0x00e1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzi(java.lang.String):android.os.Bundle");
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x023f  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0247  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzh zzj(java.lang.String r38) {
        /*
            r37 = this;
            r1 = r37
            r2 = r38
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r38)
            r37.zzg()
            r37.zzW()
            r3 = 0
            android.database.sqlite.SQLiteDatabase r4 = r37.zzh()     // Catch:{ SQLiteException -> 0x0228, all -> 0x0226 }
            java.lang.String r5 = "apps"
            java.lang.String r6 = "app_instance_id"
            java.lang.String r7 = "gmp_app_id"
            java.lang.String r8 = "resettable_device_id_hash"
            java.lang.String r9 = "last_bundle_index"
            java.lang.String r10 = "last_bundle_start_timestamp"
            java.lang.String r11 = "last_bundle_end_timestamp"
            java.lang.String r12 = "app_version"
            java.lang.String r13 = "app_store"
            java.lang.String r14 = "gmp_version"
            java.lang.String r15 = "dev_cert_hash"
            java.lang.String r16 = "measurement_enabled"
            java.lang.String r17 = "day"
            java.lang.String r18 = "daily_public_events_count"
            java.lang.String r19 = "daily_events_count"
            java.lang.String r20 = "daily_conversions_count"
            java.lang.String r21 = "config_fetched_time"
            java.lang.String r22 = "failed_config_fetch_time"
            java.lang.String r23 = "app_version_int"
            java.lang.String r24 = "firebase_instance_id"
            java.lang.String r25 = "daily_error_events_count"
            java.lang.String r26 = "daily_realtime_events_count"
            java.lang.String r27 = "health_monitor_sample"
            java.lang.String r28 = "android_id"
            java.lang.String r29 = "adid_reporting_enabled"
            java.lang.String r30 = "admob_app_id"
            java.lang.String r31 = "dynamite_version"
            java.lang.String r32 = "safelisted_events"
            java.lang.String r33 = "ga_app_id"
            java.lang.String r34 = "session_stitching_token"
            java.lang.String r35 = "sgtm_upload_enabled"
            java.lang.String r36 = "target_os_version"
            java.lang.String[] r6 = new java.lang.String[]{r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36}     // Catch:{ SQLiteException -> 0x0228, all -> 0x0226 }
            java.lang.String r7 = "app_id=?"
            r0 = 1
            java.lang.String[] r8 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0228, all -> 0x0226 }
            r12 = 0
            r8[r12] = r2     // Catch:{ SQLiteException -> 0x0228, all -> 0x0226 }
            r9 = 0
            r10 = 0
            r11 = 0
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ SQLiteException -> 0x0228, all -> 0x0226 }
            boolean r5 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x0224 }
            if (r5 != 0) goto L_0x0071
            r4.close()
            return r3
        L_0x0071:
            com.google.android.gms.measurement.internal.zzh r5 = new com.google.android.gms.measurement.internal.zzh     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzlg r6 = r1.zzf     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzge r6 = r6.zzq()     // Catch:{ SQLiteException -> 0x0224 }
            r5.<init>(r6, r2)     // Catch:{ SQLiteException -> 0x0224 }
            java.lang.String r6 = r4.getString(r12)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzI(r6)     // Catch:{ SQLiteException -> 0x0224 }
            java.lang.String r6 = r4.getString(r0)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzX(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 2
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzaf(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 3
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzab(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 4
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzac(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 5
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzaa(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 6
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzK(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 7
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzJ(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 8
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzY(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 9
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzT(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 10
            boolean r7 = r4.isNull(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r7 != 0) goto L_0x00de
            int r6 = r4.getInt(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r6 == 0) goto L_0x00dc
            r6 = r0
            goto L_0x00df
        L_0x00dc:
            r6 = r12
            goto L_0x00df
        L_0x00de:
            r6 = r0
        L_0x00df:
            r5.zzad(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 11
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzS(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 12
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzQ(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 13
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzP(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 14
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzN(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 15
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzM(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 16
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzV(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 17
            boolean r7 = r4.isNull(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r7 == 0) goto L_0x0124
            r6 = -2147483648(0xffffffff80000000, double:NaN)
            goto L_0x012a
        L_0x0124:
            int r6 = r4.getInt(r6)     // Catch:{ SQLiteException -> 0x0224 }
            long r6 = (long) r6     // Catch:{ SQLiteException -> 0x0224 }
        L_0x012a:
            r5.zzL(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 18
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzW(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 19
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzO(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 20
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzR(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 21
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzZ(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 23
            boolean r7 = r4.isNull(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r7 != 0) goto L_0x0163
            int r6 = r4.getInt(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r6 == 0) goto L_0x0161
            r6 = r0
            goto L_0x0164
        L_0x0161:
            r6 = r12
            goto L_0x0164
        L_0x0163:
            r6 = r0
        L_0x0164:
            r5.zzH(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 24
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzG(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 25
            boolean r7 = r4.isNull(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r7 == 0) goto L_0x017b
            r6 = 0
            goto L_0x0180
        L_0x017b:
            long r6 = r4.getLong(r6)     // Catch:{ SQLiteException -> 0x0224 }
        L_0x0180:
            r5.zzU(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r6 = 26
            boolean r7 = r4.isNull(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r7 != 0) goto L_0x019d
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            java.lang.String r7 = ","
            r8 = -1
            java.lang.String[] r6 = r6.split(r7, r8)     // Catch:{ SQLiteException -> 0x0224 }
            java.util.List r6 = java.util.Arrays.asList(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzag(r6)     // Catch:{ SQLiteException -> 0x0224 }
        L_0x019d:
            com.google.android.gms.internal.measurement.zzqr.zzc()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzge r6 = r1.zzt     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzag r6 = r6.zzf()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzeg r7 = com.google.android.gms.measurement.internal.zzeh.zzao     // Catch:{ SQLiteException -> 0x0224 }
            boolean r6 = r6.zzs(r2, r7)     // Catch:{ SQLiteException -> 0x0224 }
            if (r6 != 0) goto L_0x01bc
            com.google.android.gms.measurement.internal.zzge r6 = r1.zzt     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzag r6 = r6.zzf()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzeg r7 = com.google.android.gms.measurement.internal.zzeh.zzam     // Catch:{ SQLiteException -> 0x0224 }
            boolean r6 = r6.zzs(r3, r7)     // Catch:{ SQLiteException -> 0x0224 }
            if (r6 == 0) goto L_0x01c5
        L_0x01bc:
            r6 = 28
            java.lang.String r6 = r4.getString(r6)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzah(r6)     // Catch:{ SQLiteException -> 0x0224 }
        L_0x01c5:
            com.google.android.gms.internal.measurement.zzra.zzc()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzge r6 = r1.zzt     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzag r6 = r6.zzf()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzeg r7 = com.google.android.gms.measurement.internal.zzeh.zzaq     // Catch:{ SQLiteException -> 0x0224 }
            boolean r6 = r6.zzs(r3, r7)     // Catch:{ SQLiteException -> 0x0224 }
            if (r6 == 0) goto L_0x01e9
            r6 = 29
            boolean r7 = r4.isNull(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r7 != 0) goto L_0x01e5
            int r6 = r4.getInt(r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r6 == 0) goto L_0x01e5
            goto L_0x01e6
        L_0x01e5:
            r0 = r12
        L_0x01e6:
            r5.zzai(r0)     // Catch:{ SQLiteException -> 0x0224 }
        L_0x01e9:
            com.google.android.gms.internal.measurement.zzpw.zzc()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzag r0 = r0.zzf()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzeg r6 = com.google.android.gms.measurement.internal.zzeh.zzaE     // Catch:{ SQLiteException -> 0x0224 }
            boolean r0 = r0.zzs(r3, r6)     // Catch:{ SQLiteException -> 0x0224 }
            if (r0 == 0) goto L_0x0203
            r0 = 30
            long r6 = r4.getLong(r0)     // Catch:{ SQLiteException -> 0x0224 }
            r5.zzaj(r6)     // Catch:{ SQLiteException -> 0x0224 }
        L_0x0203:
            r5.zzE()     // Catch:{ SQLiteException -> 0x0224 }
            boolean r0 = r4.moveToNext()     // Catch:{ SQLiteException -> 0x0224 }
            if (r0 == 0) goto L_0x021f
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x0224 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteException -> 0x0224 }
            java.lang.String r6 = "Got multiple records for app, expected one. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r38)     // Catch:{ SQLiteException -> 0x0224 }
            r0.zzb(r6, r7)     // Catch:{ SQLiteException -> 0x0224 }
        L_0x021f:
            r4.close()
            return r5
        L_0x0224:
            r0 = move-exception
            goto L_0x022a
        L_0x0226:
            r0 = move-exception
            goto L_0x0245
        L_0x0228:
            r0 = move-exception
            r4 = r3
        L_0x022a:
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ all -> 0x0243 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ all -> 0x0243 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ all -> 0x0243 }
            java.lang.String r6 = "Error querying app. appId"
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzeu.zzn(r38)     // Catch:{ all -> 0x0243 }
            r5.zzc(r6, r2, r0)     // Catch:{ all -> 0x0243 }
            if (r4 == 0) goto L_0x0242
            r4.close()
        L_0x0242:
            return r3
        L_0x0243:
            r0 = move-exception
            r3 = r4
        L_0x0245:
            if (r3 == 0) goto L_0x024a
            r3.close()
        L_0x024a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzj(java.lang.String):com.google.android.gms.measurement.internal.zzh");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x012f  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzac zzk(java.lang.String r31, java.lang.String r32) {
        /*
            r30 = this;
            r1 = r30
            r8 = r32
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r31)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r32)
            r30.zzg()
            r30.zzW()
            r9 = 0
            android.database.sqlite.SQLiteDatabase r10 = r30.zzh()     // Catch:{ SQLiteException -> 0x0106, all -> 0x0104 }
            java.lang.String r11 = "conditional_properties"
            java.lang.String r12 = "origin"
            java.lang.String r13 = "value"
            java.lang.String r14 = "active"
            java.lang.String r15 = "trigger_event_name"
            java.lang.String r16 = "trigger_timeout"
            java.lang.String r17 = "timed_out_event"
            java.lang.String r18 = "creation_timestamp"
            java.lang.String r19 = "triggered_event"
            java.lang.String r20 = "triggered_timestamp"
            java.lang.String r21 = "time_to_live"
            java.lang.String r22 = "expired_event"
            java.lang.String[] r12 = new java.lang.String[]{r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22}     // Catch:{ SQLiteException -> 0x0106, all -> 0x0104 }
            java.lang.String r13 = "app_id=? and name=?"
            r0 = 2
            java.lang.String[] r14 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0106, all -> 0x0104 }
            r2 = 0
            r14[r2] = r31     // Catch:{ SQLiteException -> 0x0106, all -> 0x0104 }
            r3 = 1
            r14[r3] = r8     // Catch:{ SQLiteException -> 0x0106, all -> 0x0104 }
            r15 = 0
            r16 = 0
            r17 = 0
            android.database.Cursor r10 = r10.query(r11, r12, r13, r14, r15, r16, r17)     // Catch:{ SQLiteException -> 0x0106, all -> 0x0104 }
            boolean r4 = r10.moveToFirst()     // Catch:{ SQLiteException -> 0x0102 }
            if (r4 != 0) goto L_0x0056
            r10.close()
            return r9
        L_0x0056:
            java.lang.String r4 = r10.getString(r2)     // Catch:{ SQLiteException -> 0x0102 }
            if (r4 != 0) goto L_0x005f
            java.lang.String r4 = ""
        L_0x005f:
            r17 = r4
            java.lang.Object r6 = r1.zzq(r10, r3)     // Catch:{ SQLiteException -> 0x0102 }
            int r0 = r10.getInt(r0)     // Catch:{ SQLiteException -> 0x0102 }
            if (r0 == 0) goto L_0x006e
            r21 = r3
            goto L_0x0070
        L_0x006e:
            r21 = r2
        L_0x0070:
            r0 = 3
            java.lang.String r22 = r10.getString(r0)     // Catch:{ SQLiteException -> 0x0102 }
            r0 = 4
            long r24 = r10.getLong(r0)     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzlg r0 = r1.zzf     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzli r0 = r0.zzu()     // Catch:{ SQLiteException -> 0x0102 }
            r2 = 5
            byte[] r2 = r10.getBlob(r2)     // Catch:{ SQLiteException -> 0x0102 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzaw> r3 = com.google.android.gms.measurement.internal.zzaw.CREATOR     // Catch:{ SQLiteException -> 0x0102 }
            android.os.Parcelable r0 = r0.zzh(r2, r3)     // Catch:{ SQLiteException -> 0x0102 }
            r23 = r0
            com.google.android.gms.measurement.internal.zzaw r23 = (com.google.android.gms.measurement.internal.zzaw) r23     // Catch:{ SQLiteException -> 0x0102 }
            r0 = 6
            long r19 = r10.getLong(r0)     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzlg r0 = r1.zzf     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzli r0 = r0.zzu()     // Catch:{ SQLiteException -> 0x0102 }
            r2 = 7
            byte[] r2 = r10.getBlob(r2)     // Catch:{ SQLiteException -> 0x0102 }
            android.os.Parcelable r0 = r0.zzh(r2, r3)     // Catch:{ SQLiteException -> 0x0102 }
            r26 = r0
            com.google.android.gms.measurement.internal.zzaw r26 = (com.google.android.gms.measurement.internal.zzaw) r26     // Catch:{ SQLiteException -> 0x0102 }
            r0 = 8
            long r4 = r10.getLong(r0)     // Catch:{ SQLiteException -> 0x0102 }
            r0 = 9
            long r27 = r10.getLong(r0)     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzlg r0 = r1.zzf     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzli r0 = r0.zzu()     // Catch:{ SQLiteException -> 0x0102 }
            r2 = 10
            byte[] r2 = r10.getBlob(r2)     // Catch:{ SQLiteException -> 0x0102 }
            android.os.Parcelable r0 = r0.zzh(r2, r3)     // Catch:{ SQLiteException -> 0x0102 }
            r29 = r0
            com.google.android.gms.measurement.internal.zzaw r29 = (com.google.android.gms.measurement.internal.zzaw) r29     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzlj r18 = new com.google.android.gms.measurement.internal.zzlj     // Catch:{ SQLiteException -> 0x0102 }
            r2 = r18
            r3 = r32
            r7 = r17
            r2.<init>(r3, r4, r6, r7)     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzac r0 = new com.google.android.gms.measurement.internal.zzac     // Catch:{ SQLiteException -> 0x0102 }
            r15 = r0
            r16 = r31
            r15.<init>(r16, r17, r18, r19, r21, r22, r23, r24, r26, r27, r29)     // Catch:{ SQLiteException -> 0x0102 }
            boolean r2 = r10.moveToNext()     // Catch:{ SQLiteException -> 0x0102 }
            if (r2 == 0) goto L_0x00fd
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x0102 }
            java.lang.String r3 = "Got multiple records for conditional property, expected one"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r31)     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ SQLiteException -> 0x0102 }
            com.google.android.gms.measurement.internal.zzep r5 = r5.zzj()     // Catch:{ SQLiteException -> 0x0102 }
            java.lang.String r5 = r5.zzf(r8)     // Catch:{ SQLiteException -> 0x0102 }
            r2.zzc(r3, r4, r5)     // Catch:{ SQLiteException -> 0x0102 }
        L_0x00fd:
            r10.close()
            return r0
        L_0x0102:
            r0 = move-exception
            goto L_0x0108
        L_0x0104:
            r0 = move-exception
            goto L_0x012d
        L_0x0106:
            r0 = move-exception
            r10 = r9
        L_0x0108:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x012b }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x012b }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x012b }
            java.lang.String r3 = "Error querying conditional property"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r31)     // Catch:{ all -> 0x012b }
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ all -> 0x012b }
            com.google.android.gms.measurement.internal.zzep r5 = r5.zzj()     // Catch:{ all -> 0x012b }
            java.lang.String r5 = r5.zzf(r8)     // Catch:{ all -> 0x012b }
            r2.zzd(r3, r4, r5, r0)     // Catch:{ all -> 0x012b }
            if (r10 == 0) goto L_0x012a
            r10.close()
        L_0x012a:
            return r9
        L_0x012b:
            r0 = move-exception
            r9 = r10
        L_0x012d:
            if (r9 == 0) goto L_0x0132
            r9.close()
        L_0x0132:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzk(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzac");
    }

    @WorkerThread
    public final zzak zzl(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return zzm(j, str, 1, false, false, z3, false, z5);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x011b  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzak zzm(long r16, java.lang.String r18, long r19, boolean r21, boolean r22, boolean r23, boolean r24, boolean r25) {
        /*
            r15 = this;
            r1 = r15
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r18)
            r15.zzg()
            r15.zzW()
            r0 = 1
            java.lang.String[] r2 = new java.lang.String[r0]
            r3 = 0
            r2[r3] = r18
            com.google.android.gms.measurement.internal.zzak r4 = new com.google.android.gms.measurement.internal.zzak
            r4.<init>()
            r5 = 0
            android.database.sqlite.SQLiteDatabase r14 = r15.zzh()     // Catch:{ SQLiteException -> 0x00fe, all -> 0x00fc }
            java.lang.String r7 = "apps"
            java.lang.String r8 = "day"
            java.lang.String r9 = "daily_events_count"
            java.lang.String r10 = "daily_public_events_count"
            java.lang.String r11 = "daily_conversions_count"
            java.lang.String r12 = "daily_error_events_count"
            java.lang.String r13 = "daily_realtime_events_count"
            java.lang.String[] r8 = new java.lang.String[]{r8, r9, r10, r11, r12, r13}     // Catch:{ SQLiteException -> 0x00fe, all -> 0x00fc }
            java.lang.String r9 = "app_id=?"
            java.lang.String[] r10 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x00fe, all -> 0x00fc }
            r10[r3] = r18     // Catch:{ SQLiteException -> 0x00fe, all -> 0x00fc }
            r11 = 0
            r12 = 0
            r13 = 0
            r6 = r14
            android.database.Cursor r5 = r6.query(r7, r8, r9, r10, r11, r12, r13)     // Catch:{ SQLiteException -> 0x00fe, all -> 0x00fc }
            boolean r6 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x00fa }
            if (r6 != 0) goto L_0x0057
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x00fa }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x00fa }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r2 = "Not updating daily counts, app is not known. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r18)     // Catch:{ SQLiteException -> 0x00fa }
            r0.zzb(r2, r3)     // Catch:{ SQLiteException -> 0x00fa }
            r5.close()
            return r4
        L_0x0057:
            long r6 = r5.getLong(r3)     // Catch:{ SQLiteException -> 0x00fa }
            int r3 = (r6 > r16 ? 1 : (r6 == r16 ? 0 : -1))
            if (r3 != 0) goto L_0x0082
            long r6 = r5.getLong(r0)     // Catch:{ SQLiteException -> 0x00fa }
            r4.zzb = r6     // Catch:{ SQLiteException -> 0x00fa }
            r0 = 2
            long r6 = r5.getLong(r0)     // Catch:{ SQLiteException -> 0x00fa }
            r4.zza = r6     // Catch:{ SQLiteException -> 0x00fa }
            r0 = 3
            long r6 = r5.getLong(r0)     // Catch:{ SQLiteException -> 0x00fa }
            r4.zzc = r6     // Catch:{ SQLiteException -> 0x00fa }
            r0 = 4
            long r6 = r5.getLong(r0)     // Catch:{ SQLiteException -> 0x00fa }
            r4.zzd = r6     // Catch:{ SQLiteException -> 0x00fa }
            r0 = 5
            long r6 = r5.getLong(r0)     // Catch:{ SQLiteException -> 0x00fa }
            r4.zze = r6     // Catch:{ SQLiteException -> 0x00fa }
        L_0x0082:
            if (r21 == 0) goto L_0x008a
            long r6 = r4.zzb     // Catch:{ SQLiteException -> 0x00fa }
            long r6 = r6 + r19
            r4.zzb = r6     // Catch:{ SQLiteException -> 0x00fa }
        L_0x008a:
            if (r22 == 0) goto L_0x0092
            long r6 = r4.zza     // Catch:{ SQLiteException -> 0x00fa }
            long r6 = r6 + r19
            r4.zza = r6     // Catch:{ SQLiteException -> 0x00fa }
        L_0x0092:
            if (r23 == 0) goto L_0x009a
            long r6 = r4.zzc     // Catch:{ SQLiteException -> 0x00fa }
            long r6 = r6 + r19
            r4.zzc = r6     // Catch:{ SQLiteException -> 0x00fa }
        L_0x009a:
            if (r24 == 0) goto L_0x00a2
            long r6 = r4.zzd     // Catch:{ SQLiteException -> 0x00fa }
            long r6 = r6 + r19
            r4.zzd = r6     // Catch:{ SQLiteException -> 0x00fa }
        L_0x00a2:
            if (r25 == 0) goto L_0x00aa
            long r6 = r4.zze     // Catch:{ SQLiteException -> 0x00fa }
            long r6 = r6 + r19
            r4.zze = r6     // Catch:{ SQLiteException -> 0x00fa }
        L_0x00aa:
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ SQLiteException -> 0x00fa }
            r0.<init>()     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "day"
            java.lang.Long r6 = java.lang.Long.valueOf(r16)     // Catch:{ SQLiteException -> 0x00fa }
            r0.put(r3, r6)     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "daily_public_events_count"
            long r6 = r4.zza     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SQLiteException -> 0x00fa }
            r0.put(r3, r6)     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "daily_events_count"
            long r6 = r4.zzb     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SQLiteException -> 0x00fa }
            r0.put(r3, r6)     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "daily_conversions_count"
            long r6 = r4.zzc     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SQLiteException -> 0x00fa }
            r0.put(r3, r6)     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "daily_error_events_count"
            long r6 = r4.zzd     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SQLiteException -> 0x00fa }
            r0.put(r3, r6)     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "daily_realtime_events_count"
            long r6 = r4.zze     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SQLiteException -> 0x00fa }
            r0.put(r3, r6)     // Catch:{ SQLiteException -> 0x00fa }
            java.lang.String r3 = "apps"
            java.lang.String r6 = "app_id=?"
            r14.update(r3, r0, r6, r2)     // Catch:{ SQLiteException -> 0x00fa }
            r5.close()
            return r4
        L_0x00fa:
            r0 = move-exception
            goto L_0x00ff
        L_0x00fc:
            r0 = move-exception
            goto L_0x0119
        L_0x00fe:
            r0 = move-exception
        L_0x00ff:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x0118 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0118 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0118 }
            java.lang.String r3 = "Error updating daily counts. appId"
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzeu.zzn(r18)     // Catch:{ all -> 0x0118 }
            r2.zzc(r3, r6, r0)     // Catch:{ all -> 0x0118 }
            if (r5 == 0) goto L_0x0117
            r5.close()
        L_0x0117:
            return r4
        L_0x0118:
            r0 = move-exception
        L_0x0119:
            if (r5 == 0) goto L_0x011e
            r5.close()
        L_0x011e:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzm(long, java.lang.String, long, boolean, boolean, boolean, boolean, boolean):com.google.android.gms.measurement.internal.zzak");
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x015a  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzas zzn(java.lang.String r28, java.lang.String r29) {
        /*
            r27 = this;
            r1 = r27
            r15 = r29
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r28)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r29)
            r27.zzg()
            r27.zzW()
            java.util.ArrayList r0 = new java.util.ArrayList
            java.lang.String r2 = "lifetime_count"
            java.lang.String r3 = "current_bundle_count"
            java.lang.String r4 = "last_fire_timestamp"
            java.lang.String r5 = "last_bundled_timestamp"
            java.lang.String r6 = "last_bundled_day"
            java.lang.String r7 = "last_sampled_complex_event_id"
            java.lang.String r8 = "last_sampling_rate"
            java.lang.String r9 = "last_exempt_from_sampling"
            java.lang.String r10 = "current_session_count"
            java.lang.String[] r2 = new java.lang.String[]{r2, r3, r4, r5, r6, r7, r8, r9, r10}
            java.util.List r2 = java.util.Arrays.asList(r2)
            r0.<init>(r2)
            r19 = 0
            android.database.sqlite.SQLiteDatabase r2 = r27.zzh()     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            java.lang.String r3 = "events"
            r10 = 0
            java.lang.String[] r4 = new java.lang.String[r10]     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            java.lang.Object[] r0 = r0.toArray(r4)     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            r4 = r0
            java.lang.String[] r4 = (java.lang.String[]) r4     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            java.lang.String r5 = "app_id=? and name=?"
            r0 = 2
            java.lang.String[] r6 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            r6[r10] = r28     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            r11 = 1
            r6[r11] = r15     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r13 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x012d, all -> 0x012b }
            boolean r2 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            if (r2 != 0) goto L_0x005c
            r13.close()
            return r19
        L_0x005c:
            long r5 = r13.getLong(r10)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            long r7 = r13.getLong(r11)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            long r16 = r13.getLong(r0)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r0 = 3
            boolean r2 = r13.isNull(r0)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r3 = 0
            if (r2 == 0) goto L_0x0075
            r20 = r3
            goto L_0x007a
        L_0x0075:
            long r20 = r13.getLong(r0)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
        L_0x007a:
            r0 = 4
            boolean r2 = r13.isNull(r0)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            if (r2 == 0) goto L_0x0084
            r0 = r19
            goto L_0x008d
        L_0x0084:
            long r22 = r13.getLong(r0)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            java.lang.Long r0 = java.lang.Long.valueOf(r22)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
        L_0x008d:
            r2 = 5
            boolean r9 = r13.isNull(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            if (r9 == 0) goto L_0x0097
            r18 = r19
            goto L_0x00a2
        L_0x0097:
            long r22 = r13.getLong(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            java.lang.Long r2 = java.lang.Long.valueOf(r22)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r18 = r2
        L_0x00a2:
            r2 = 6
            boolean r9 = r13.isNull(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            if (r9 == 0) goto L_0x00ac
            r22 = r19
            goto L_0x00b7
        L_0x00ac:
            long r22 = r13.getLong(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            java.lang.Long r2 = java.lang.Long.valueOf(r22)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r22 = r2
        L_0x00b7:
            r2 = 7
            boolean r9 = r13.isNull(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            if (r9 != 0) goto L_0x00d2
            long r23 = r13.getLong(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r25 = 1
            int r2 = (r23 > r25 ? 1 : (r23 == r25 ? 0 : -1))
            if (r2 != 0) goto L_0x00ca
            r10 = r11
            goto L_0x00cb
        L_0x00ca:
        L_0x00cb:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r10)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r23 = r2
            goto L_0x00d4
        L_0x00d2:
            r23 = r19
        L_0x00d4:
            r2 = 8
            boolean r9 = r13.isNull(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            if (r9 == 0) goto L_0x00df
            r9 = r3
            goto L_0x00e5
        L_0x00df:
            long r2 = r13.getLong(r2)     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r9 = r2
        L_0x00e5:
            com.google.android.gms.measurement.internal.zzas r24 = new com.google.android.gms.measurement.internal.zzas     // Catch:{ SQLiteException -> 0x0125, all -> 0x011f }
            r2 = r24
            r3 = r28
            r4 = r29
            r11 = r16
            r25 = r13
            r13 = r20
            r15 = r0
            r16 = r18
            r17 = r22
            r18 = r23
            r2.<init>(r3, r4, r5, r7, r9, r11, r13, r15, r16, r17, r18)     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
            boolean r0 = r25.moveToNext()     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
            if (r0 == 0) goto L_0x0116
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
            java.lang.String r2 = "Got multiple records for event aggregates, expected one. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r28)     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
            r0.zzb(r2, r3)     // Catch:{ SQLiteException -> 0x011d, all -> 0x011b }
        L_0x0116:
            r25.close()
            return r24
        L_0x011b:
            r0 = move-exception
            goto L_0x0122
        L_0x011d:
            r0 = move-exception
            goto L_0x0128
        L_0x011f:
            r0 = move-exception
            r25 = r13
        L_0x0122:
            r19 = r25
            goto L_0x0158
        L_0x0125:
            r0 = move-exception
            r25 = r13
        L_0x0128:
            r13 = r25
            goto L_0x0130
        L_0x012b:
            r0 = move-exception
            goto L_0x0158
        L_0x012d:
            r0 = move-exception
            r13 = r19
        L_0x0130:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x0155 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0155 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0155 }
            java.lang.String r3 = "Error querying events. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r28)     // Catch:{ all -> 0x0155 }
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ all -> 0x0155 }
            com.google.android.gms.measurement.internal.zzep r5 = r5.zzj()     // Catch:{ all -> 0x0155 }
            r6 = r29
            java.lang.String r5 = r5.zzd(r6)     // Catch:{ all -> 0x0155 }
            r2.zzd(r3, r4, r5, r0)     // Catch:{ all -> 0x0155 }
            if (r13 == 0) goto L_0x0154
            r13.close()
        L_0x0154:
            return r19
        L_0x0155:
            r0 = move-exception
            r19 = r13
        L_0x0158:
            if (r19 == 0) goto L_0x015d
            r19.close()
        L_0x015d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzn(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzas");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ac  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzll zzp(java.lang.String r20, java.lang.String r21) {
        /*
            r19 = this;
            r1 = r19
            r9 = r21
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r20)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r21)
            r19.zzg()
            r19.zzW()
            r10 = 0
            android.database.sqlite.SQLiteDatabase r11 = r19.zzh()     // Catch:{ SQLiteException -> 0x0083, all -> 0x0081 }
            java.lang.String r12 = "user_attributes"
            java.lang.String r0 = "set_timestamp"
            java.lang.String r2 = "value"
            java.lang.String r3 = "origin"
            java.lang.String[] r13 = new java.lang.String[]{r0, r2, r3}     // Catch:{ SQLiteException -> 0x0083, all -> 0x0081 }
            java.lang.String r14 = "app_id=? and name=?"
            r0 = 2
            java.lang.String[] r15 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0083, all -> 0x0081 }
            r2 = 0
            r15[r2] = r20     // Catch:{ SQLiteException -> 0x0083, all -> 0x0081 }
            r3 = 1
            r15[r3] = r9     // Catch:{ SQLiteException -> 0x0083, all -> 0x0081 }
            r16 = 0
            r17 = 0
            r18 = 0
            android.database.Cursor r11 = r11.query(r12, r13, r14, r15, r16, r17, r18)     // Catch:{ SQLiteException -> 0x0083, all -> 0x0081 }
            boolean r4 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x007f }
            if (r4 != 0) goto L_0x0043
            r11.close()
            return r10
        L_0x0043:
            long r6 = r11.getLong(r2)     // Catch:{ SQLiteException -> 0x007f }
            java.lang.Object r8 = r1.zzq(r11, r3)     // Catch:{ SQLiteException -> 0x007f }
            if (r8 != 0) goto L_0x0052
            r11.close()
            return r10
        L_0x0052:
            java.lang.String r4 = r11.getString(r0)     // Catch:{ SQLiteException -> 0x007f }
            com.google.android.gms.measurement.internal.zzll r0 = new com.google.android.gms.measurement.internal.zzll     // Catch:{ SQLiteException -> 0x007f }
            r2 = r0
            r3 = r20
            r5 = r21
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ SQLiteException -> 0x007f }
            boolean r2 = r11.moveToNext()     // Catch:{ SQLiteException -> 0x007f }
            if (r2 == 0) goto L_0x007a
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ SQLiteException -> 0x007f }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x007f }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x007f }
            java.lang.String r3 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r20)     // Catch:{ SQLiteException -> 0x007f }
            r2.zzb(r3, r4)     // Catch:{ SQLiteException -> 0x007f }
        L_0x007a:
            r11.close()
            return r0
        L_0x007f:
            r0 = move-exception
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            goto L_0x00aa
        L_0x0083:
            r0 = move-exception
            r11 = r10
        L_0x0085:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x00a8 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x00a8 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = "Error querying user property. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r20)     // Catch:{ all -> 0x00a8 }
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ all -> 0x00a8 }
            com.google.android.gms.measurement.internal.zzep r5 = r5.zzj()     // Catch:{ all -> 0x00a8 }
            java.lang.String r5 = r5.zzf(r9)     // Catch:{ all -> 0x00a8 }
            r2.zzd(r3, r4, r5, r0)     // Catch:{ all -> 0x00a8 }
            if (r11 == 0) goto L_0x00a7
            r11.close()
        L_0x00a7:
            return r10
        L_0x00a8:
            r0 = move-exception
            r10 = r11
        L_0x00aa:
            if (r10 == 0) goto L_0x00af
            r10.close()
        L_0x00af:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzp(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzll");
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final Object zzq(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                this.zzt.zzaA().zzd().zza("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                this.zzt.zzaA().zzd().zza("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                this.zzt.zzaA().zzd().zzb("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0040  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzr() {
        /*
            r6 = this;
            android.database.sqlite.SQLiteDatabase r0 = r6.zzh()
            r1 = 0
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch:{ SQLiteException -> 0x0022, all -> 0x0020 }
            boolean r2 = r0.moveToFirst()     // Catch:{ SQLiteException -> 0x001e }
            if (r2 == 0) goto L_0x001a
            r2 = 0
            java.lang.String r1 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x001e }
            r0.close()
            return r1
        L_0x001a:
            r0.close()
            return r1
        L_0x001e:
            r2 = move-exception
            goto L_0x0025
        L_0x0020:
            r0 = move-exception
            goto L_0x003e
        L_0x0022:
            r0 = move-exception
            r2 = r0
            r0 = r1
        L_0x0025:
            com.google.android.gms.measurement.internal.zzge r3 = r6.zzt     // Catch:{ all -> 0x003a }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x003a }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x003a }
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zzb(r4, r2)     // Catch:{ all -> 0x003a }
            if (r0 == 0) goto L_0x0039
            r0.close()
        L_0x0039:
            return r1
        L_0x003a:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x003e:
            if (r1 == 0) goto L_0x0043
            r1.close()
        L_0x0043:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzr():java.lang.String");
    }

    @WorkerThread
    public final List zzs(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzW();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat(e.ANY_MARKER));
            sb.append(" and name glob ?");
        }
        return zzt(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0134  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List zzt(java.lang.String r28, java.lang.String[] r29) {
        /*
            r27 = this;
            r1 = r27
            r27.zzg()
            r27.zzW()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r10 = "1001"
            r11 = 0
            android.database.sqlite.SQLiteDatabase r2 = r27.zzh()     // Catch:{ SQLiteException -> 0x0117, all -> 0x0115 }
            java.lang.String r3 = "conditional_properties"
            java.lang.String r12 = "app_id"
            java.lang.String r13 = "origin"
            java.lang.String r14 = "name"
            java.lang.String r15 = "value"
            java.lang.String r16 = "active"
            java.lang.String r17 = "trigger_event_name"
            java.lang.String r18 = "trigger_timeout"
            java.lang.String r19 = "timed_out_event"
            java.lang.String r20 = "creation_timestamp"
            java.lang.String r21 = "triggered_event"
            java.lang.String r22 = "triggered_timestamp"
            java.lang.String r23 = "time_to_live"
            java.lang.String r24 = "expired_event"
            java.lang.String[] r4 = new java.lang.String[]{r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24}     // Catch:{ SQLiteException -> 0x0117, all -> 0x0115 }
            java.lang.String r9 = "rowid"
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ SQLiteException -> 0x0117, all -> 0x0115 }
            r5.zzf()     // Catch:{ SQLiteException -> 0x0117, all -> 0x0115 }
            r7 = 0
            r8 = 0
            r5 = r28
            r6 = r29
            android.database.Cursor r11 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0117, all -> 0x0115 }
            boolean r2 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x0113 }
            if (r2 == 0) goto L_0x010e
        L_0x0052:
            int r2 = r0.size()     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzt     // Catch:{ SQLiteException -> 0x0113 }
            r3.zzf()     // Catch:{ SQLiteException -> 0x0113 }
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 < r3) goto L_0x0079
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x0113 }
            java.lang.String r4 = "Read more than the max allowed conditional properties, ignoring extra"
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ SQLiteException -> 0x0113 }
            r5.zzf()     // Catch:{ SQLiteException -> 0x0113 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ SQLiteException -> 0x0113 }
            r2.zzb(r4, r3)     // Catch:{ SQLiteException -> 0x0113 }
            goto L_0x0109
        L_0x0079:
            r2 = 0
            java.lang.String r13 = r11.getString(r2)     // Catch:{ SQLiteException -> 0x0113 }
            r3 = 1
            java.lang.String r14 = r11.getString(r3)     // Catch:{ SQLiteException -> 0x0113 }
            r4 = 2
            java.lang.String r5 = r11.getString(r4)     // Catch:{ SQLiteException -> 0x0113 }
            r4 = 3
            java.lang.Object r8 = r1.zzq(r11, r4)     // Catch:{ SQLiteException -> 0x0113 }
            r4 = 4
            int r4 = r11.getInt(r4)     // Catch:{ SQLiteException -> 0x0113 }
            if (r4 == 0) goto L_0x0098
            r18 = r3
            goto L_0x009a
        L_0x0098:
            r18 = r2
        L_0x009a:
            r2 = 5
            java.lang.String r19 = r11.getString(r2)     // Catch:{ SQLiteException -> 0x0113 }
            r2 = 6
            long r21 = r11.getLong(r2)     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzlg r2 = r1.zzf     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzli r2 = r2.zzu()     // Catch:{ SQLiteException -> 0x0113 }
            r3 = 7
            byte[] r3 = r11.getBlob(r3)     // Catch:{ SQLiteException -> 0x0113 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzaw> r4 = com.google.android.gms.measurement.internal.zzaw.CREATOR     // Catch:{ SQLiteException -> 0x0113 }
            android.os.Parcelable r2 = r2.zzh(r3, r4)     // Catch:{ SQLiteException -> 0x0113 }
            r20 = r2
            com.google.android.gms.measurement.internal.zzaw r20 = (com.google.android.gms.measurement.internal.zzaw) r20     // Catch:{ SQLiteException -> 0x0113 }
            r2 = 8
            long r16 = r11.getLong(r2)     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzlg r2 = r1.zzf     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzli r2 = r2.zzu()     // Catch:{ SQLiteException -> 0x0113 }
            r3 = 9
            byte[] r3 = r11.getBlob(r3)     // Catch:{ SQLiteException -> 0x0113 }
            android.os.Parcelable r2 = r2.zzh(r3, r4)     // Catch:{ SQLiteException -> 0x0113 }
            r23 = r2
            com.google.android.gms.measurement.internal.zzaw r23 = (com.google.android.gms.measurement.internal.zzaw) r23     // Catch:{ SQLiteException -> 0x0113 }
            r2 = 10
            long r6 = r11.getLong(r2)     // Catch:{ SQLiteException -> 0x0113 }
            r2 = 11
            long r24 = r11.getLong(r2)     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzlg r2 = r1.zzf     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzli r2 = r2.zzu()     // Catch:{ SQLiteException -> 0x0113 }
            r3 = 12
            byte[] r3 = r11.getBlob(r3)     // Catch:{ SQLiteException -> 0x0113 }
            android.os.Parcelable r2 = r2.zzh(r3, r4)     // Catch:{ SQLiteException -> 0x0113 }
            r26 = r2
            com.google.android.gms.measurement.internal.zzaw r26 = (com.google.android.gms.measurement.internal.zzaw) r26     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzlj r15 = new com.google.android.gms.measurement.internal.zzlj     // Catch:{ SQLiteException -> 0x0113 }
            r4 = r15
            r9 = r14
            r4.<init>(r5, r6, r8, r9)     // Catch:{ SQLiteException -> 0x0113 }
            com.google.android.gms.measurement.internal.zzac r2 = new com.google.android.gms.measurement.internal.zzac     // Catch:{ SQLiteException -> 0x0113 }
            r12 = r2
            r12.<init>(r13, r14, r15, r16, r18, r19, r20, r21, r23, r24, r26)     // Catch:{ SQLiteException -> 0x0113 }
            r0.add(r2)     // Catch:{ SQLiteException -> 0x0113 }
            boolean r2 = r11.moveToNext()     // Catch:{ SQLiteException -> 0x0113 }
            if (r2 != 0) goto L_0x0052
        L_0x0109:
            r11.close()
            return r0
        L_0x010e:
            r11.close()
            return r0
        L_0x0113:
            r0 = move-exception
            goto L_0x0118
        L_0x0115:
            r0 = move-exception
            goto L_0x0132
        L_0x0117:
            r0 = move-exception
        L_0x0118:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x0131 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0131 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0131 }
            java.lang.String r3 = "Error querying conditional user property value"
            r2.zzb(r3, r0)     // Catch:{ all -> 0x0131 }
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ all -> 0x0131 }
            if (r11 == 0) goto L_0x0130
            r11.close()
        L_0x0130:
            return r0
        L_0x0131:
            r0 = move-exception
        L_0x0132:
            if (r11 == 0) goto L_0x0137
            r11.close()
        L_0x0137:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzt(java.lang.String, java.lang.String[]):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ad  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List zzu(java.lang.String r14) {
        /*
            r13 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14)
            r13.zzg()
            r13.zzW()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r9 = "1000"
            r10 = 0
            android.database.sqlite.SQLiteDatabase r1 = r13.zzh()     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            java.lang.String r2 = "user_attributes"
            java.lang.String r3 = "name"
            java.lang.String r4 = "origin"
            java.lang.String r5 = "set_timestamp"
            java.lang.String r6 = "value"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5, r6}     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            java.lang.String r4 = "app_id=?"
            r11 = 1
            java.lang.String[] r5 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            r12 = 0
            r5[r12] = r14     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            java.lang.String r8 = "rowid"
            com.google.android.gms.measurement.internal.zzge r6 = r13.zzt     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            r6.zzf()     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            r6 = 0
            r7 = 0
            android.database.Cursor r10 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x008c, all -> 0x008a }
            boolean r1 = r10.moveToFirst()     // Catch:{ SQLiteException -> 0x0088 }
            if (r1 == 0) goto L_0x0083
        L_0x0041:
            java.lang.String r5 = r10.getString(r12)     // Catch:{ SQLiteException -> 0x0088 }
            java.lang.String r1 = r10.getString(r11)     // Catch:{ SQLiteException -> 0x0088 }
            if (r1 != 0) goto L_0x004e
            java.lang.String r1 = ""
        L_0x004e:
            r4 = r1
            r1 = 2
            long r6 = r10.getLong(r1)     // Catch:{ SQLiteException -> 0x0088 }
            r1 = 3
            java.lang.Object r8 = r13.zzq(r10, r1)     // Catch:{ SQLiteException -> 0x0088 }
            if (r8 != 0) goto L_0x006f
            com.google.android.gms.measurement.internal.zzge r1 = r13.zzt     // Catch:{ SQLiteException -> 0x0088 }
            com.google.android.gms.measurement.internal.zzeu r1 = r1.zzaA()     // Catch:{ SQLiteException -> 0x0088 }
            com.google.android.gms.measurement.internal.zzes r1 = r1.zzd()     // Catch:{ SQLiteException -> 0x0088 }
            java.lang.String r2 = "Read invalid user property value, ignoring it. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r14)     // Catch:{ SQLiteException -> 0x0088 }
            r1.zzb(r2, r3)     // Catch:{ SQLiteException -> 0x0088 }
            goto L_0x0079
        L_0x006f:
            com.google.android.gms.measurement.internal.zzll r1 = new com.google.android.gms.measurement.internal.zzll     // Catch:{ SQLiteException -> 0x0088 }
            r2 = r1
            r3 = r14
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ SQLiteException -> 0x0088 }
            r0.add(r1)     // Catch:{ SQLiteException -> 0x0088 }
        L_0x0079:
            boolean r1 = r10.moveToNext()     // Catch:{ SQLiteException -> 0x0088 }
            if (r1 != 0) goto L_0x0041
            r10.close()
            return r0
        L_0x0083:
            r10.close()
            return r0
        L_0x0088:
            r0 = move-exception
            goto L_0x008d
        L_0x008a:
            r14 = move-exception
            goto L_0x00ab
        L_0x008c:
            r0 = move-exception
        L_0x008d:
            com.google.android.gms.measurement.internal.zzge r1 = r13.zzt     // Catch:{ all -> 0x00aa }
            com.google.android.gms.measurement.internal.zzeu r1 = r1.zzaA()     // Catch:{ all -> 0x00aa }
            com.google.android.gms.measurement.internal.zzes r1 = r1.zzd()     // Catch:{ all -> 0x00aa }
            java.lang.String r2 = "Error querying user properties. appId"
            java.lang.Object r14 = com.google.android.gms.measurement.internal.zzeu.zzn(r14)     // Catch:{ all -> 0x00aa }
            r1.zzc(r2, r14, r0)     // Catch:{ all -> 0x00aa }
            java.util.List r14 = java.util.Collections.emptyList()     // Catch:{ all -> 0x00aa }
            if (r10 == 0) goto L_0x00a9
            r10.close()
        L_0x00a9:
            return r14
        L_0x00aa:
            r14 = move-exception
        L_0x00ab:
            if (r10 == 0) goto L_0x00b0
            r10.close()
        L_0x00b0:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzu(java.lang.String):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0107, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0109, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x010b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x010c, code lost:
        r14 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x010e, code lost:
        r15 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0129, code lost:
        r12.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0130, code lost:
        r12.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0109 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0015] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0130  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List zzv(java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            r17 = this;
            r1 = r17
            r0 = r20
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r18)
            r17.zzg()
            r17.zzW()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r11 = "1001"
            r12 = 0
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x010b, all -> 0x0109 }
            r13 = 3
            r3.<init>(r13)     // Catch:{ SQLiteException -> 0x010b, all -> 0x0109 }
            r14 = r18
            r3.add(r14)     // Catch:{ SQLiteException -> 0x0107, all -> 0x0109 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0107, all -> 0x0109 }
            java.lang.String r5 = "app_id=?"
            r4.<init>(r5)     // Catch:{ SQLiteException -> 0x0107, all -> 0x0109 }
            boolean r5 = android.text.TextUtils.isEmpty(r19)     // Catch:{ SQLiteException -> 0x0107, all -> 0x0109 }
            if (r5 != 0) goto L_0x0038
            r15 = r19
            r3.add(r15)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r5 = " and origin=?"
            r4.append(r5)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            goto L_0x003a
        L_0x0038:
            r15 = r19
        L_0x003a:
            boolean r5 = android.text.TextUtils.isEmpty(r20)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            if (r5 != 0) goto L_0x0059
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            r5.<init>()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            r5.append(r0)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r6 = "*"
            r5.append(r6)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            r3.add(r5)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r5 = " and name glob ?"
            r4.append(r5)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
        L_0x0059:
            int r5 = r3.size()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.Object[] r3 = r3.toArray(r5)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            r7 = r3
            java.lang.String[] r7 = (java.lang.String[]) r7     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            android.database.sqlite.SQLiteDatabase r3 = r17.zzh()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r5 = "user_attributes"
            java.lang.String r6 = "name"
            java.lang.String r8 = "set_timestamp"
            java.lang.String r9 = "value"
            java.lang.String r10 = "origin"
            java.lang.String[] r6 = new java.lang.String[]{r6, r8, r9, r10}     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r8 = r4.toString()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            java.lang.String r10 = "rowid"
            com.google.android.gms.measurement.internal.zzge r4 = r1.zzt     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            r4.zzf()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            r9 = 0
            r16 = 0
            r4 = r5
            r5 = r6
            r6 = r8
            r8 = r9
            r9 = r16
            android.database.Cursor r12 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0109 }
            boolean r3 = r12.moveToFirst()     // Catch:{ SQLiteException -> 0x0103 }
            if (r3 != 0) goto L_0x009d
            r12.close()
            return r2
        L_0x009d:
            int r3 = r2.size()     // Catch:{ SQLiteException -> 0x0103 }
            com.google.android.gms.measurement.internal.zzge r4 = r1.zzt     // Catch:{ SQLiteException -> 0x0103 }
            r4.zzf()     // Catch:{ SQLiteException -> 0x0103 }
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r3 < r4) goto L_0x00c3
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteException -> 0x0103 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x0103 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteException -> 0x0103 }
            java.lang.String r3 = "Read more than the max allowed user properties, ignoring excess"
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzt     // Catch:{ SQLiteException -> 0x0103 }
            r5.zzf()     // Catch:{ SQLiteException -> 0x0103 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ SQLiteException -> 0x0103 }
            r0.zzb(r3, r4)     // Catch:{ SQLiteException -> 0x0103 }
            goto L_0x00fe
        L_0x00c3:
            r3 = 0
            java.lang.String r7 = r12.getString(r3)     // Catch:{ SQLiteException -> 0x0103 }
            r3 = 1
            long r8 = r12.getLong(r3)     // Catch:{ SQLiteException -> 0x0103 }
            r3 = 2
            java.lang.Object r10 = r1.zzq(r12, r3)     // Catch:{ SQLiteException -> 0x0103 }
            java.lang.String r15 = r12.getString(r13)     // Catch:{ SQLiteException -> 0x0103 }
            if (r10 != 0) goto L_0x00ec
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzt     // Catch:{ SQLiteException -> 0x0103 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ SQLiteException -> 0x0103 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ SQLiteException -> 0x0103 }
            java.lang.String r4 = "(2)Read invalid user property value, ignoring it"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r18)     // Catch:{ SQLiteException -> 0x0103 }
            r3.zzd(r4, r5, r15, r0)     // Catch:{ SQLiteException -> 0x0103 }
            goto L_0x00f8
        L_0x00ec:
            com.google.android.gms.measurement.internal.zzll r3 = new com.google.android.gms.measurement.internal.zzll     // Catch:{ SQLiteException -> 0x0103 }
            r4 = r3
            r5 = r18
            r6 = r15
            r4.<init>(r5, r6, r7, r8, r10)     // Catch:{ SQLiteException -> 0x0103 }
            r2.add(r3)     // Catch:{ SQLiteException -> 0x0103 }
        L_0x00f8:
            boolean r3 = r12.moveToNext()     // Catch:{ SQLiteException -> 0x0103 }
            if (r3 != 0) goto L_0x009d
        L_0x00fe:
            r12.close()
            return r2
        L_0x0103:
            r0 = move-exception
            goto L_0x0110
        L_0x0105:
            r0 = move-exception
            goto L_0x0110
        L_0x0107:
            r0 = move-exception
            goto L_0x010e
        L_0x0109:
            r0 = move-exception
            goto L_0x012e
        L_0x010b:
            r0 = move-exception
            r14 = r18
        L_0x010e:
            r15 = r19
        L_0x0110:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x012d }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x012d }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x012d }
            java.lang.String r3 = "(2)Error querying user properties"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r18)     // Catch:{ all -> 0x012d }
            r2.zzd(r3, r4, r15, r0)     // Catch:{ all -> 0x012d }
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ all -> 0x012d }
            if (r12 == 0) goto L_0x012c
            r12.close()
        L_0x012c:
            return r0
        L_0x012d:
            r0 = move-exception
        L_0x012e:
            if (r12 == 0) goto L_0x0133
            r12.close()
        L_0x0133:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzv(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    @WorkerThread
    public final void zzw() {
        zzW();
        zzh().beginTransaction();
    }

    @WorkerThread
    public final void zzx() {
        zzW();
        zzh().endTransaction();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final void zzy(List list) {
        zzg();
        zzW();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzI()) {
            String str = "(" + TextUtils.join(",", list) + ")";
            if (zzZ("SELECT COUNT(1) FROM queue WHERE rowid IN " + str + " AND retry_count =  2147483647 LIMIT 1", (String[]) null) > 0) {
                this.zzt.zzaA().zzk().zza("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                zzh().execSQL("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN " + str + " AND (retry_count IS NULL OR retry_count < 2147483647)");
            } catch (SQLiteException e) {
                this.zzt.zzaA().zzd().zzb("Error incrementing retry count. error", e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzz() {
        zzg();
        zzW();
        if (zzI()) {
            long zza2 = this.zzf.zzs().zza.zza();
            long elapsedRealtime = this.zzt.zzax().elapsedRealtime();
            long abs = Math.abs(elapsedRealtime - zza2);
            this.zzt.zzf();
            if (abs > ((Long) zzeh.zzy.zza((Object) null)).longValue()) {
                this.zzf.zzs().zza.zzb(elapsedRealtime);
                zzg();
                zzW();
                if (zzI()) {
                    SQLiteDatabase zzh2 = zzh();
                    this.zzt.zzf();
                    int delete = zzh2.delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(this.zzt.zzax().currentTimeMillis()), String.valueOf(zzag.zzA())});
                    if (delete > 0) {
                        this.zzt.zzaA().zzj().zzb("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }
}

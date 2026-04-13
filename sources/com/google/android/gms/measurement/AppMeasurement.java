package com.google.android.gms.measurement;

import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzge;
import com.google.android.gms.measurement.internal.zzha;
import com.google.android.gms.measurement.internal.zzhe;
import com.google.android.gms.measurement.internal.zzhf;
import com.google.android.gms.measurement.internal.zzik;
import com.google.android.gms.measurement.internal.zzip;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ShowFirstParty
@KeepForSdk
@Deprecated
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public class AppMeasurement {
    @ShowFirstParty
    @NonNull
    @KeepForSdk
    public static final String CRASH_ORIGIN = "crash";
    @ShowFirstParty
    @NonNull
    @KeepForSdk
    public static final String FCM_ORIGIN = "fcm";
    @ShowFirstParty
    @NonNull
    @KeepForSdk
    public static final String FIAM_ORIGIN = "fiam";
    private static volatile AppMeasurement zza;
    private final zzd zzb;

    @ShowFirstParty
    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
    public interface EventInterceptor extends zzhe {
        @WorkerThread
        @ShowFirstParty
        @KeepForSdk
        void interceptEvent(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle, long j);
    }

    @ShowFirstParty
    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
    public interface OnEventListener extends zzhf {
        @WorkerThread
        @ShowFirstParty
        @KeepForSdk
        void onEvent(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle, long j);
    }

    public AppMeasurement(zzge zzge) {
        this.zzb = new zza(zzge);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038 A[SYNTHETIC, Splitter:B:17:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0040  */
    @com.google.android.gms.common.internal.ShowFirstParty
    @androidx.annotation.Keep
    @androidx.annotation.RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @androidx.annotation.NonNull
    @com.google.android.gms.common.annotation.KeepForSdk
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.measurement.AppMeasurement getInstance(@androidx.annotation.NonNull android.content.Context r14) {
        /*
            com.google.android.gms.measurement.AppMeasurement r0 = zza
            if (r0 != 0) goto L_0x0060
            java.lang.Class<com.google.android.gms.measurement.AppMeasurement> r0 = com.google.android.gms.measurement.AppMeasurement.class
            monitor-enter(r0)
            com.google.android.gms.measurement.AppMeasurement r1 = zza     // Catch:{ all -> 0x005d }
            if (r1 != 0) goto L_0x005b
            r1 = 0
            java.lang.String r2 = "com.google.firebase.analytics.FirebaseAnalytics"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x0034 }
            java.lang.String r3 = "getScionFrontendApiImplementation"
            r4 = 2
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x0032 }
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r7 = 0
            r5[r7] = r6     // Catch:{ Exception -> 0x0032 }
            java.lang.Class<android.os.Bundle> r6 = android.os.Bundle.class
            r8 = 1
            r5[r8] = r6     // Catch:{ Exception -> 0x0032 }
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r5)     // Catch:{ Exception -> 0x0032 }
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0032 }
            r3[r7] = r14     // Catch:{ Exception -> 0x0032 }
            r3[r8] = r1     // Catch:{ Exception -> 0x0032 }
            java.lang.Object r2 = r2.invoke(r1, r3)     // Catch:{ Exception -> 0x0032 }
            com.google.android.gms.measurement.internal.zzik r2 = (com.google.android.gms.measurement.internal.zzik) r2     // Catch:{ Exception -> 0x0032 }
            goto L_0x0036
        L_0x0032:
            r2 = move-exception
            goto L_0x0035
        L_0x0034:
            r2 = move-exception
        L_0x0035:
            r2 = r1
        L_0x0036:
            if (r2 == 0) goto L_0x0040
            com.google.android.gms.measurement.AppMeasurement r14 = new com.google.android.gms.measurement.AppMeasurement     // Catch:{ all -> 0x005d }
            r14.<init>((com.google.android.gms.measurement.internal.zzik) r2)     // Catch:{ all -> 0x005d }
            zza = r14     // Catch:{ all -> 0x005d }
            goto L_0x005b
        L_0x0040:
            com.google.android.gms.internal.measurement.zzcl r13 = new com.google.android.gms.internal.measurement.zzcl     // Catch:{ all -> 0x005d }
            r3 = 0
            r5 = 0
            r7 = 1
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r2 = r13
            r2.<init>(r3, r5, r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x005d }
            com.google.android.gms.measurement.internal.zzge r14 = com.google.android.gms.measurement.internal.zzge.zzp(r14, r13, r1)     // Catch:{ all -> 0x005d }
            com.google.android.gms.measurement.AppMeasurement r1 = new com.google.android.gms.measurement.AppMeasurement     // Catch:{ all -> 0x005d }
            r1.<init>((com.google.android.gms.measurement.internal.zzge) r14)     // Catch:{ all -> 0x005d }
            zza = r1     // Catch:{ all -> 0x005d }
        L_0x005b:
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            goto L_0x0060
        L_0x005d:
            r14 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            throw r14
        L_0x0060:
            com.google.android.gms.measurement.AppMeasurement r14 = zza
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.AppMeasurement.getInstance(android.content.Context):com.google.android.gms.measurement.AppMeasurement");
    }

    @Keep
    public void beginAdUnitExposure(@Size(min = 1) @NonNull String adUnitId) {
        this.zzb.zzp(adUnitId);
    }

    @ShowFirstParty
    @KeepForSdk
    @Keep
    public void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String userPropertyName, @NonNull String clearEventName, @NonNull Bundle clearEventParams) {
        this.zzb.zzq(userPropertyName, clearEventName, clearEventParams);
    }

    @Keep
    public void endAdUnitExposure(@Size(min = 1) @NonNull String adUnitId) {
        this.zzb.zzr(adUnitId);
    }

    @Keep
    public long generateEventId() {
        return this.zzb.zzb();
    }

    @NonNull
    @Keep
    public String getAppInstanceId() {
        return this.zzb.zzh();
    }

    @NonNull
    @KeepForSdk
    public Boolean getBoolean() {
        return this.zzb.zzc();
    }

    @WorkerThread
    @ShowFirstParty
    @Keep
    @NonNull
    @KeepForSdk
    public List<ConditionalUserProperty> getConditionalUserProperties(@NonNull String origin, @Size(max = 23, min = 1) @NonNull String propertyNamePrefix) {
        int i;
        List<Bundle> zzm = this.zzb.zzm(origin, propertyNamePrefix);
        if (zzm == null) {
            i = 0;
        } else {
            i = zzm.size();
        }
        ArrayList arrayList = new ArrayList(i);
        for (Bundle conditionalUserProperty : zzm) {
            arrayList.add(new ConditionalUserProperty(conditionalUserProperty));
        }
        return arrayList;
    }

    @NonNull
    @Keep
    public String getCurrentScreenClass() {
        return this.zzb.zzi();
    }

    @NonNull
    @Keep
    public String getCurrentScreenName() {
        return this.zzb.zzj();
    }

    @NonNull
    @KeepForSdk
    public Double getDouble() {
        return this.zzb.zzd();
    }

    @NonNull
    @Keep
    public String getGmpAppId() {
        return this.zzb.zzk();
    }

    @NonNull
    @KeepForSdk
    public Integer getInteger() {
        return this.zzb.zze();
    }

    @NonNull
    @KeepForSdk
    public Long getLong() {
        return this.zzb.zzf();
    }

    @WorkerThread
    @ShowFirstParty
    @Keep
    @KeepForSdk
    public int getMaxUserProperties(@Size(min = 1) @NonNull String origin) {
        return this.zzb.zza(origin);
    }

    @NonNull
    @KeepForSdk
    public String getString() {
        return this.zzb.zzl();
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    @Keep
    @NonNull
    @VisibleForTesting
    public Map<String, Object> getUserProperties(@NonNull String origin, @Size(max = 24, min = 1) @NonNull String propertyNamePrefix, boolean includeInternal) {
        return this.zzb.zzo(origin, propertyNamePrefix, includeInternal);
    }

    @ShowFirstParty
    @Keep
    public void logEventInternal(@NonNull String origin, @NonNull String name, @NonNull Bundle params) {
        this.zzb.zzs(origin, name, params);
    }

    @ShowFirstParty
    @KeepForSdk
    public void logEventInternalNoInterceptor(@NonNull String origin, @NonNull String name, @NonNull Bundle params, long timestampInMillis) {
        this.zzb.zzt(origin, name, params, timestampInMillis);
    }

    @ShowFirstParty
    @KeepForSdk
    public void registerOnMeasurementEventListener(@NonNull OnEventListener listener) {
        this.zzb.zzu(listener);
    }

    @ShowFirstParty
    @KeepForSdk
    @Keep
    public void setConditionalUserProperty(@NonNull ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        zzd zzd = this.zzb;
        Bundle bundle = new Bundle();
        String str = conditionalUserProperty.mAppId;
        if (str != null) {
            bundle.putString("app_id", str);
        }
        String str2 = conditionalUserProperty.mOrigin;
        if (str2 != null) {
            bundle.putString("origin", str2);
        }
        String str3 = conditionalUserProperty.mName;
        if (str3 != null) {
            bundle.putString("name", str3);
        }
        Object obj = conditionalUserProperty.mValue;
        if (obj != null) {
            zzha.zzb(bundle, obj);
        }
        String str4 = conditionalUserProperty.mTriggerEventName;
        if (str4 != null) {
            bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, str4);
        }
        bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, conditionalUserProperty.mTriggerTimeout);
        String str5 = conditionalUserProperty.mTimedOutEventName;
        if (str5 != null) {
            bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, str5);
        }
        Bundle bundle2 = conditionalUserProperty.mTimedOutEventParams;
        if (bundle2 != null) {
            bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, bundle2);
        }
        String str6 = conditionalUserProperty.mTriggeredEventName;
        if (str6 != null) {
            bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, str6);
        }
        Bundle bundle3 = conditionalUserProperty.mTriggeredEventParams;
        if (bundle3 != null) {
            bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, bundle3);
        }
        bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, conditionalUserProperty.mTimeToLive);
        String str7 = conditionalUserProperty.mExpiredEventName;
        if (str7 != null) {
            bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str7);
        }
        Bundle bundle4 = conditionalUserProperty.mExpiredEventParams;
        if (bundle4 != null) {
            bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle4);
        }
        bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, conditionalUserProperty.mCreationTimestamp);
        bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, conditionalUserProperty.mActive);
        bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, conditionalUserProperty.mTriggeredTimestamp);
        zzd.zzv(bundle);
    }

    @WorkerThread
    @ShowFirstParty
    @KeepForSdk
    public void setEventInterceptor(@NonNull EventInterceptor interceptor) {
        this.zzb.zzw(interceptor);
    }

    @ShowFirstParty
    @KeepForSdk
    public void unregisterOnMeasurementEventListener(@NonNull OnEventListener listener) {
        this.zzb.zzx(listener);
    }

    public AppMeasurement(zzik zzik) {
        this.zzb = new zzb(zzik);
    }

    @WorkerThread
    @ShowFirstParty
    @NonNull
    @KeepForSdk
    public Map<String, Object> getUserProperties(boolean includeInternal) {
        return this.zzb.zzn(includeInternal);
    }

    @ShowFirstParty
    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
    public static class ConditionalUserProperty {
        @ShowFirstParty
        @KeepForSdk
        @Keep
        public boolean mActive;
        @ShowFirstParty
        @Keep
        @NonNull
        @KeepForSdk
        public String mAppId;
        @ShowFirstParty
        @KeepForSdk
        @Keep
        public long mCreationTimestamp;
        @NonNull
        @Keep
        public String mExpiredEventName;
        @NonNull
        @Keep
        public Bundle mExpiredEventParams;
        @ShowFirstParty
        @Keep
        @NonNull
        @KeepForSdk
        public String mName;
        @ShowFirstParty
        @Keep
        @NonNull
        @KeepForSdk
        public String mOrigin;
        @ShowFirstParty
        @KeepForSdk
        @Keep
        public long mTimeToLive;
        @NonNull
        @Keep
        public String mTimedOutEventName;
        @NonNull
        @Keep
        public Bundle mTimedOutEventParams;
        @ShowFirstParty
        @Keep
        @NonNull
        @KeepForSdk
        public String mTriggerEventName;
        @ShowFirstParty
        @KeepForSdk
        @Keep
        public long mTriggerTimeout;
        @NonNull
        @Keep
        public String mTriggeredEventName;
        @NonNull
        @Keep
        public Bundle mTriggeredEventParams;
        @ShowFirstParty
        @KeepForSdk
        @Keep
        public long mTriggeredTimestamp;
        @ShowFirstParty
        @Keep
        @NonNull
        @KeepForSdk
        public Object mValue;

        @KeepForSdk
        public ConditionalUserProperty() {
        }

        @VisibleForTesting
        ConditionalUserProperty(@NonNull Bundle bundle) {
            Class<Long> cls = Long.class;
            Class<String> cls2 = String.class;
            Preconditions.checkNotNull(bundle);
            this.mAppId = (String) zzha.zza(bundle, "app_id", cls2, (Object) null);
            this.mOrigin = (String) zzha.zza(bundle, "origin", cls2, (Object) null);
            this.mName = (String) zzha.zza(bundle, "name", cls2, (Object) null);
            this.mValue = zzha.zza(bundle, "value", Object.class, (Object) null);
            this.mTriggerEventName = (String) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, cls2, (Object) null);
            this.mTriggerTimeout = ((Long) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, cls, 0L)).longValue();
            this.mTimedOutEventName = (String) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, cls2, (Object) null);
            this.mTimedOutEventParams = (Bundle) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, (Object) null);
            this.mTriggeredEventName = (String) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, cls2, (Object) null);
            this.mTriggeredEventParams = (Bundle) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, (Object) null);
            this.mTimeToLive = ((Long) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, cls, 0L)).longValue();
            this.mExpiredEventName = (String) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, cls2, (Object) null);
            this.mExpiredEventParams = (Bundle) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, (Object) null);
            this.mActive = ((Boolean) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.class, false)).booleanValue();
            this.mCreationTimestamp = ((Long) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, cls, 0L)).longValue();
            this.mTriggeredTimestamp = ((Long) zzha.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, cls, 0L)).longValue();
        }

        @KeepForSdk
        public ConditionalUserProperty(@NonNull ConditionalUserProperty other) {
            Preconditions.checkNotNull(other);
            this.mAppId = other.mAppId;
            this.mOrigin = other.mOrigin;
            this.mCreationTimestamp = other.mCreationTimestamp;
            this.mName = other.mName;
            Object obj = other.mValue;
            if (obj != null) {
                Object zza = zzip.zza(obj);
                this.mValue = zza;
                if (zza == null) {
                    this.mValue = other.mValue;
                }
            }
            this.mActive = other.mActive;
            this.mTriggerEventName = other.mTriggerEventName;
            this.mTriggerTimeout = other.mTriggerTimeout;
            this.mTimedOutEventName = other.mTimedOutEventName;
            Bundle bundle = other.mTimedOutEventParams;
            if (bundle != null) {
                this.mTimedOutEventParams = new Bundle(bundle);
            }
            this.mTriggeredEventName = other.mTriggeredEventName;
            Bundle bundle2 = other.mTriggeredEventParams;
            if (bundle2 != null) {
                this.mTriggeredEventParams = new Bundle(bundle2);
            }
            this.mTriggeredTimestamp = other.mTriggeredTimestamp;
            this.mTimeToLive = other.mTimeToLive;
            this.mExpiredEventName = other.mExpiredEventName;
            Bundle bundle3 = other.mExpiredEventParams;
            if (bundle3 != null) {
                this.mExpiredEventParams = new Bundle(bundle3);
            }
        }
    }
}

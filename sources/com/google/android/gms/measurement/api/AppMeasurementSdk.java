package com.google.android.gms.measurement.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.internal.measurement.zzef;
import com.google.android.gms.measurement.internal.zzhe;
import com.google.android.gms.measurement.internal.zzhf;
import java.util.List;
import java.util.Map;

@ShowFirstParty
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public class AppMeasurementSdk {
    private final zzef zza;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
    public static final class ConditionalUserProperty {
        @NonNull
        @KeepForSdk
        public static final String ACTIVE = "active";
        @NonNull
        @KeepForSdk
        public static final String CREATION_TIMESTAMP = "creation_timestamp";
        @NonNull
        @KeepForSdk
        public static final String EXPIRED_EVENT_NAME = "expired_event_name";
        @NonNull
        @KeepForSdk
        public static final String EXPIRED_EVENT_PARAMS = "expired_event_params";
        @NonNull
        @KeepForSdk
        public static final String NAME = "name";
        @NonNull
        @KeepForSdk
        public static final String ORIGIN = "origin";
        @NonNull
        @KeepForSdk
        public static final String TIMED_OUT_EVENT_NAME = "timed_out_event_name";
        @NonNull
        @KeepForSdk
        public static final String TIMED_OUT_EVENT_PARAMS = "timed_out_event_params";
        @NonNull
        @KeepForSdk
        public static final String TIME_TO_LIVE = "time_to_live";
        @NonNull
        @KeepForSdk
        public static final String TRIGGERED_EVENT_NAME = "triggered_event_name";
        @NonNull
        @KeepForSdk
        public static final String TRIGGERED_EVENT_PARAMS = "triggered_event_params";
        @NonNull
        @KeepForSdk
        public static final String TRIGGERED_TIMESTAMP = "triggered_timestamp";
        @NonNull
        @KeepForSdk
        public static final String TRIGGER_EVENT_NAME = "trigger_event_name";
        @NonNull
        @KeepForSdk
        public static final String TRIGGER_TIMEOUT = "trigger_timeout";
        @NonNull
        @KeepForSdk
        public static final String VALUE = "value";

        private ConditionalUserProperty() {
        }
    }

    @ShowFirstParty
    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
    public interface EventInterceptor extends zzhe {
        @WorkerThread
        @ShowFirstParty
        @KeepForSdk
        void interceptEvent(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle, long j);
    }

    @ShowFirstParty
    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
    public interface OnEventListener extends zzhf {
        @WorkerThread
        @ShowFirstParty
        @KeepForSdk
        void onEvent(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle, long j);
    }

    public AppMeasurementSdk(zzef zzef) {
        this.zza = zzef;
    }

    @ShowFirstParty
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @NonNull
    @KeepForSdk
    public static AppMeasurementSdk getInstance(@NonNull Context context) {
        return zzef.zzg(context, (String) null, (String) null, (String) null, (Bundle) null).zzd();
    }

    @KeepForSdk
    public void beginAdUnitExposure(@Size(min = 1) @NonNull String adUnitId) {
        this.zza.zzv(adUnitId);
    }

    @KeepForSdk
    public void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String userPropertyName, @Nullable String clearEventName, @Nullable Bundle clearEventParams) {
        this.zza.zzw(userPropertyName, clearEventName, clearEventParams);
    }

    @KeepForSdk
    public void endAdUnitExposure(@Size(min = 1) @NonNull String adUnitId) {
        this.zza.zzx(adUnitId);
    }

    @KeepForSdk
    public long generateEventId() {
        return this.zza.zzb();
    }

    @KeepForSdk
    @Nullable
    public String getAppIdOrigin() {
        return this.zza.zzk();
    }

    @KeepForSdk
    @Nullable
    public String getAppInstanceId() {
        return this.zza.zzm();
    }

    @WorkerThread
    @NonNull
    @KeepForSdk
    public List<Bundle> getConditionalUserProperties(@Nullable String origin, @Size(max = 23, min = 1) @Nullable String propertyNamePrefix) {
        return this.zza.zzq(origin, propertyNamePrefix);
    }

    @KeepForSdk
    @Nullable
    public String getCurrentScreenClass() {
        return this.zza.zzn();
    }

    @KeepForSdk
    @Nullable
    public String getCurrentScreenName() {
        return this.zza.zzo();
    }

    @KeepForSdk
    @Nullable
    public String getGmpAppId() {
        return this.zza.zzp();
    }

    @WorkerThread
    @KeepForSdk
    public int getMaxUserProperties(@Size(min = 1) @NonNull String origin) {
        return this.zza.zza(origin);
    }

    @WorkerThread
    @NonNull
    @KeepForSdk
    public Map<String, Object> getUserProperties(@Nullable String origin, @Size(max = 24, min = 1) @Nullable String propertyNamePrefix, boolean includeInternal) {
        return this.zza.zzr(origin, propertyNamePrefix, includeInternal);
    }

    @KeepForSdk
    public void logEvent(@NonNull String origin, @NonNull String name, @NonNull Bundle params) {
        this.zza.zzz(origin, name, params);
    }

    @KeepForSdk
    public void logEventNoInterceptor(@NonNull String origin, @NonNull String name, @NonNull Bundle params, long timestampInMillis) {
        this.zza.zzA(origin, name, params, timestampInMillis);
    }

    @KeepForSdk
    @Nullable
    public void performAction(@NonNull Bundle bundle) {
        this.zza.zzc(bundle, false);
    }

    @KeepForSdk
    @Nullable
    public Bundle performActionWithResponse(@NonNull Bundle bundle) {
        return this.zza.zzc(bundle, true);
    }

    @ShowFirstParty
    @KeepForSdk
    public void registerOnMeasurementEventListener(@NonNull OnEventListener listener) {
        this.zza.zzC(listener);
    }

    @KeepForSdk
    public void setConditionalUserProperty(@NonNull Bundle conditionalUserProperty) {
        this.zza.zzE(conditionalUserProperty);
    }

    @KeepForSdk
    public void setConsent(@NonNull Bundle consentMap) {
        this.zza.zzF(consentMap);
    }

    @KeepForSdk
    public void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String screenName, @Size(max = 36, min = 1) @Nullable String screenClassOverride) {
        this.zza.zzH(activity, screenName, screenClassOverride);
    }

    @WorkerThread
    @ShowFirstParty
    @KeepForSdk
    public void setEventInterceptor(@NonNull EventInterceptor interceptor) {
        this.zza.zzK(interceptor);
    }

    @KeepForSdk
    public void setMeasurementEnabled(@Nullable Boolean enabled) {
        this.zza.zzL(enabled);
    }

    @KeepForSdk
    public void setUserProperty(@NonNull String origin, @NonNull String name, @NonNull Object value) {
        this.zza.zzO(origin, name, value, true);
    }

    @ShowFirstParty
    @KeepForSdk
    public void unregisterOnMeasurementEventListener(@NonNull OnEventListener listener) {
        this.zza.zzP(listener);
    }

    public final void zza(boolean z) {
        this.zza.zzI(z);
    }

    @KeepForSdk
    public void setMeasurementEnabled(boolean enabled) {
        this.zza.zzL(Boolean.valueOf(enabled));
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @NonNull
    @KeepForSdk
    public static AppMeasurementSdk getInstance(@NonNull Context context, @NonNull String logTag, @NonNull String origin, @Nullable String customAppId, @NonNull Bundle extraParameters) {
        return zzef.zzg(context, logTag, origin, customAppId, extraParameters).zzd();
    }
}

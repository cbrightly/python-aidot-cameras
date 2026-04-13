package com.google.firebase.analytics.connector;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzef;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzha;
import com.google.android.gms.measurement.internal.zzip;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.analytics.connector.internal.zza;
import com.google.firebase.analytics.connector.internal.zzc;
import com.google.firebase.analytics.connector.internal.zze;
import com.google.firebase.analytics.connector.internal.zzg;
import com.google.firebase.events.Event;
import com.google.firebase.events.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public class AnalyticsConnectorImpl implements AnalyticsConnector {
    private static volatile AnalyticsConnector zzc;
    @VisibleForTesting
    final AppMeasurementSdk zza;
    @VisibleForTesting
    final Map zzb = new ConcurrentHashMap();

    AnalyticsConnectorImpl(AppMeasurementSdk appMeasurementSdk) {
        Preconditions.checkNotNull(appMeasurementSdk);
        this.zza = appMeasurementSdk;
    }

    @NonNull
    @KeepForSdk
    public static AnalyticsConnector getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    static /* synthetic */ void zza(Event event) {
        boolean z = ((DataCollectionDefaultChange) event.getPayload()).enabled;
        synchronized (AnalyticsConnectorImpl.class) {
            ((AnalyticsConnectorImpl) Preconditions.checkNotNull(zzc)).zza.zza(z);
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzc(@NonNull String str) {
        return !str.isEmpty() && this.zzb.containsKey(str) && this.zzb.get(str) != null;
    }

    @KeepForSdk
    public void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String userPropertyName, @NonNull String clearEventName, @NonNull Bundle clearEventParams) {
        if (clearEventName == null || zzc.zzb(clearEventName, clearEventParams)) {
            this.zza.clearConditionalUserProperty(userPropertyName, clearEventName, clearEventParams);
        }
    }

    @WorkerThread
    @NonNull
    @KeepForSdk
    public List<AnalyticsConnector.ConditionalUserProperty> getConditionalUserProperties(@NonNull String origin, @Size(max = 23, min = 1) @NonNull String propertyNamePrefix) {
        Class<Long> cls = Long.class;
        Class<String> cls2 = String.class;
        ArrayList arrayList = new ArrayList();
        for (Bundle next : this.zza.getConditionalUserProperties(origin, propertyNamePrefix)) {
            int i = zzc.zza;
            Preconditions.checkNotNull(next);
            AnalyticsConnector.ConditionalUserProperty conditionalUserProperty = new AnalyticsConnector.ConditionalUserProperty();
            conditionalUserProperty.origin = (String) Preconditions.checkNotNull((String) zzha.zza(next, "origin", cls2, (Object) null));
            conditionalUserProperty.name = (String) Preconditions.checkNotNull((String) zzha.zza(next, "name", cls2, (Object) null));
            conditionalUserProperty.value = zzha.zza(next, "value", Object.class, (Object) null);
            conditionalUserProperty.triggerEventName = (String) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, cls2, (Object) null);
            conditionalUserProperty.triggerTimeout = ((Long) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, cls, 0L)).longValue();
            conditionalUserProperty.timedOutEventName = (String) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, cls2, (Object) null);
            conditionalUserProperty.timedOutEventParams = (Bundle) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, (Object) null);
            conditionalUserProperty.triggeredEventName = (String) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, cls2, (Object) null);
            conditionalUserProperty.triggeredEventParams = (Bundle) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, (Object) null);
            conditionalUserProperty.timeToLive = ((Long) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, cls, 0L)).longValue();
            conditionalUserProperty.expiredEventName = (String) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, cls2, (Object) null);
            conditionalUserProperty.expiredEventParams = (Bundle) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, (Object) null);
            conditionalUserProperty.active = ((Boolean) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.class, false)).booleanValue();
            conditionalUserProperty.creationTimestamp = ((Long) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, cls, 0L)).longValue();
            conditionalUserProperty.triggeredTimestamp = ((Long) zzha.zza(next, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, cls, 0L)).longValue();
            arrayList.add(conditionalUserProperty);
        }
        return arrayList;
    }

    @WorkerThread
    @KeepForSdk
    public int getMaxUserProperties(@Size(min = 1) @NonNull String origin) {
        return this.zza.getMaxUserProperties(origin);
    }

    @WorkerThread
    @NonNull
    @KeepForSdk
    public Map<String, Object> getUserProperties(boolean includeInternal) {
        return this.zza.getUserProperties((String) null, (String) null, includeInternal);
    }

    @KeepForSdk
    public void logEvent(@NonNull String origin, @NonNull String name, @NonNull Bundle params) {
        if (params == null) {
            params = new Bundle();
        }
        if (zzc.zzd(origin) && zzc.zzb(name, params) && zzc.zza(origin, name, params)) {
            if ("clx".equals(origin) && "_ae".equals(name)) {
                params.putLong("_r", 1);
            }
            this.zza.logEvent(origin, name, params);
        }
    }

    @WorkerThread
    @NonNull
    @KeepForSdk
    public AnalyticsConnector.AnalyticsConnectorHandle registerAnalyticsConnectorListener(@NonNull final String origin, @NonNull AnalyticsConnector.AnalyticsConnectorListener listener) {
        Object obj;
        Preconditions.checkNotNull(listener);
        if (!zzc.zzd(origin) || zzc(origin)) {
            return null;
        }
        AppMeasurementSdk appMeasurementSdk = this.zza;
        if (AppMeasurement.FIAM_ORIGIN.equals(origin)) {
            obj = new zze(appMeasurementSdk, listener);
        } else {
            obj = "clx".equals(origin) ? new zzg(appMeasurementSdk, listener) : null;
        }
        if (obj == null) {
            return null;
        }
        this.zzb.put(origin, obj);
        return new AnalyticsConnector.AnalyticsConnectorHandle() {
            @KeepForSdk
            public void registerEventNames(Set<String> eventNames) {
                if (AnalyticsConnectorImpl.this.zzc(origin) && origin.equals(AppMeasurement.FIAM_ORIGIN) && eventNames != null && !eventNames.isEmpty()) {
                    ((zza) AnalyticsConnectorImpl.this.zzb.get(origin)).zzb(eventNames);
                }
            }

            public final void unregister() {
                if (AnalyticsConnectorImpl.this.zzc(origin)) {
                    AnalyticsConnector.AnalyticsConnectorListener zza2 = ((zza) AnalyticsConnectorImpl.this.zzb.get(origin)).zza();
                    if (zza2 != null) {
                        zza2.onMessageTriggered(0, (Bundle) null);
                    }
                    AnalyticsConnectorImpl.this.zzb.remove(origin);
                }
            }

            @KeepForSdk
            public void unregisterEventNames() {
                if (AnalyticsConnectorImpl.this.zzc(origin) && origin.equals(AppMeasurement.FIAM_ORIGIN)) {
                    ((zza) AnalyticsConnectorImpl.this.zzb.get(origin)).zzc();
                }
            }
        };
    }

    @KeepForSdk
    public void setConditionalUserProperty(@NonNull AnalyticsConnector.ConditionalUserProperty conditionalUserProperty) {
        String str;
        int i = zzc.zza;
        if (conditionalUserProperty != null && (str = conditionalUserProperty.origin) != null && !str.isEmpty()) {
            Object obj = conditionalUserProperty.value;
            if ((obj == null || zzip.zza(obj) != null) && zzc.zzd(str) && zzc.zze(str, conditionalUserProperty.name)) {
                String str2 = conditionalUserProperty.expiredEventName;
                if (str2 == null || (zzc.zzb(str2, conditionalUserProperty.expiredEventParams) && zzc.zza(str, conditionalUserProperty.expiredEventName, conditionalUserProperty.expiredEventParams))) {
                    String str3 = conditionalUserProperty.triggeredEventName;
                    if (str3 == null || (zzc.zzb(str3, conditionalUserProperty.triggeredEventParams) && zzc.zza(str, conditionalUserProperty.triggeredEventName, conditionalUserProperty.triggeredEventParams))) {
                        String str4 = conditionalUserProperty.timedOutEventName;
                        if (str4 == null || (zzc.zzb(str4, conditionalUserProperty.timedOutEventParams) && zzc.zza(str, conditionalUserProperty.timedOutEventName, conditionalUserProperty.timedOutEventParams))) {
                            AppMeasurementSdk appMeasurementSdk = this.zza;
                            Bundle bundle = new Bundle();
                            String str5 = conditionalUserProperty.origin;
                            if (str5 != null) {
                                bundle.putString("origin", str5);
                            }
                            String str6 = conditionalUserProperty.name;
                            if (str6 != null) {
                                bundle.putString("name", str6);
                            }
                            Object obj2 = conditionalUserProperty.value;
                            if (obj2 != null) {
                                zzha.zzb(bundle, obj2);
                            }
                            String str7 = conditionalUserProperty.triggerEventName;
                            if (str7 != null) {
                                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, str7);
                            }
                            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, conditionalUserProperty.triggerTimeout);
                            String str8 = conditionalUserProperty.timedOutEventName;
                            if (str8 != null) {
                                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, str8);
                            }
                            Bundle bundle2 = conditionalUserProperty.timedOutEventParams;
                            if (bundle2 != null) {
                                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, bundle2);
                            }
                            String str9 = conditionalUserProperty.triggeredEventName;
                            if (str9 != null) {
                                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, str9);
                            }
                            Bundle bundle3 = conditionalUserProperty.triggeredEventParams;
                            if (bundle3 != null) {
                                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, bundle3);
                            }
                            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, conditionalUserProperty.timeToLive);
                            String str10 = conditionalUserProperty.expiredEventName;
                            if (str10 != null) {
                                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str10);
                            }
                            Bundle bundle4 = conditionalUserProperty.expiredEventParams;
                            if (bundle4 != null) {
                                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle4);
                            }
                            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, conditionalUserProperty.creationTimestamp);
                            bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, conditionalUserProperty.active);
                            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, conditionalUserProperty.triggeredTimestamp);
                            appMeasurementSdk.setConditionalUserProperty(bundle);
                        }
                    }
                }
            }
        }
    }

    @KeepForSdk
    public void setUserProperty(@NonNull String origin, @NonNull String name, @NonNull Object value) {
        if (zzc.zzd(origin) && zzc.zze(origin, name)) {
            this.zza.setUserProperty(origin, name, value);
        }
    }

    @NonNull
    @KeepForSdk
    public static AnalyticsConnector getInstance(@NonNull FirebaseApp app) {
        return (AnalyticsConnector) app.get(AnalyticsConnector.class);
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @NonNull
    @KeepForSdk
    public static AnalyticsConnector getInstance(@NonNull FirebaseApp app, @NonNull Context context, @NonNull Subscriber subscriber) {
        Preconditions.checkNotNull(app);
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzc == null) {
            synchronized (AnalyticsConnectorImpl.class) {
                if (zzc == null) {
                    Bundle bundle = new Bundle(1);
                    if (app.isDefaultApp()) {
                        subscriber.subscribe(DataCollectionDefaultChange.class, zza.zza, zzb.zza);
                        bundle.putBoolean("dataCollectionDefaultEnabled", app.isDataCollectionDefaultEnabled());
                    }
                    zzc = new AnalyticsConnectorImpl(zzef.zzg(context, (String) null, (String) null, (String) null, bundle).zzd());
                }
            }
        }
        return zzc;
    }
}

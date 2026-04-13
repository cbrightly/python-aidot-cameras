package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.measurement.zzcb;
import com.google.android.gms.internal.measurement.zzcf;
import com.google.android.gms.internal.measurement.zzci;
import com.google.android.gms.internal.measurement.zzck;
import com.google.android.gms.internal.measurement.zzcl;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@DynamiteApi
/* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.2.2 */
public class AppMeasurementDynamiteService extends zzcb {
    @VisibleForTesting
    zzge zza = null;
    @GuardedBy("listenerMap")
    private final Map zzb = new ArrayMap();

    @EnsuresNonNull({"scion"})
    private final void zzb() {
        if (this.zza == null) {
            throw new IllegalStateException("Attempting to perform action before initialize.");
        }
    }

    private final void zzc(zzcf zzcf, String str) {
        zzb();
        this.zza.zzv().zzW(zzcf, str);
    }

    public void beginAdUnitExposure(@NonNull String adUnitId, long timestamp) {
        zzb();
        this.zza.zzd().zzd(adUnitId, timestamp);
    }

    public void clearConditionalUserProperty(@NonNull String userPropertyName, @NonNull String clearEventName, @NonNull Bundle clearEventParams) {
        zzb();
        this.zza.zzq().zzA(userPropertyName, clearEventName, clearEventParams);
    }

    public void clearMeasurementEnabled(long j) {
        zzb();
        this.zza.zzq().zzU((Boolean) null);
    }

    public void endAdUnitExposure(@NonNull String adUnitId, long timestamp) {
        zzb();
        this.zza.zzd().zze(adUnitId, timestamp);
    }

    public void generateEventId(zzcf receiver) {
        zzb();
        long zzq = this.zza.zzv().zzq();
        zzb();
        this.zza.zzv().zzV(receiver, zzq);
    }

    public void getAppInstanceId(zzcf receiver) {
        zzb();
        this.zza.zzaB().zzp(new zzi(this, receiver));
    }

    public void getCachedAppInstanceId(zzcf receiver) {
        zzb();
        zzc(receiver, this.zza.zzq().zzo());
    }

    public void getConditionalUserProperties(String origin, String propertyNamePrefix, zzcf receiver) {
        zzb();
        this.zza.zzaB().zzp(new zzm(this, receiver, origin, propertyNamePrefix));
    }

    public void getCurrentScreenClass(zzcf receiver) {
        zzb();
        zzc(receiver, this.zza.zzq().zzp());
    }

    public void getCurrentScreenName(zzcf receiver) {
        zzb();
        zzc(receiver, this.zza.zzq().zzq());
    }

    public void getGmpAppId(zzcf receiver) {
        String str;
        zzb();
        zzij zzq = this.zza.zzq();
        if (zzq.zzt.zzw() != null) {
            str = zzq.zzt.zzw();
        } else {
            try {
                str = zzip.zzc(zzq.zzt.zzaw(), "google_app_id", zzq.zzt.zzz());
            } catch (IllegalStateException e) {
                zzq.zzt.zzaA().zzd().zzb("getGoogleAppId failed with exception", e);
                str = null;
            }
        }
        zzc(receiver, str);
    }

    public void getMaxUserProperties(String origin, zzcf receiver) {
        zzb();
        this.zza.zzq().zzh(origin);
        zzb();
        this.zza.zzv().zzU(receiver, 25);
    }

    public void getSessionId(zzcf receiver) {
        zzb();
        zzij zzq = this.zza.zzq();
        zzq.zzt.zzaB().zzp(new zzhx(zzq, receiver));
    }

    public void getTestFlag(zzcf receiver, int type) {
        zzb();
        switch (type) {
            case 0:
                this.zza.zzv().zzW(receiver, this.zza.zzq().zzr());
                return;
            case 1:
                this.zza.zzv().zzV(receiver, this.zza.zzq().zzm().longValue());
                return;
            case 2:
                zzlo zzv = this.zza.zzv();
                double doubleValue = this.zza.zzq().zzj().doubleValue();
                Bundle bundle = new Bundle();
                bundle.putDouble("r", doubleValue);
                try {
                    receiver.zze(bundle);
                    return;
                } catch (RemoteException e) {
                    zzv.zzt.zzaA().zzk().zzb("Error returning double value to wrapper", e);
                    return;
                }
            case 3:
                this.zza.zzv().zzU(receiver, this.zza.zzq().zzl().intValue());
                return;
            case 4:
                this.zza.zzv().zzQ(receiver, this.zza.zzq().zzi().booleanValue());
                return;
            default:
                return;
        }
    }

    public void getUserProperties(String origin, String propertyNamePrefix, boolean getInternal, zzcf receiver) {
        zzb();
        this.zza.zzaB().zzp(new zzk(this, receiver, origin, propertyNamePrefix, getInternal));
    }

    public void initForTests(@NonNull Map map) {
        zzb();
    }

    public void initialize(IObjectWrapper context, zzcl params, long timestamp) {
        zzge zzge = this.zza;
        if (zzge == null) {
            this.zza = zzge.zzp((Context) Preconditions.checkNotNull((Context) ObjectWrapper.unwrap(context)), params, Long.valueOf(timestamp));
        } else {
            zzge.zzaA().zzk().zza("Attempting to initialize multiple times");
        }
    }

    public void isDataCollectionEnabled(zzcf receiver) {
        zzb();
        this.zza.zzaB().zzp(new zzn(this, receiver));
    }

    /* Debug info: failed to restart local var, previous not found, register: 10 */
    public void logEvent(@NonNull String origin, @NonNull String name, @NonNull Bundle params, boolean isInternal, boolean allowInterceptor, long timestamp) {
        zzb();
        this.zza.zzq().zzE(origin, name, params, isInternal, allowInterceptor, timestamp);
    }

    public void logEventAndBundle(String packageName, String eventName, Bundle params, zzcf receiver, long timestamp) {
        zzb();
        Preconditions.checkNotEmpty(eventName);
        (params != null ? new Bundle(params) : new Bundle()).putString("_o", "app");
        this.zza.zzaB().zzp(new zzj(this, receiver, new zzaw(eventName, new zzau(params), "app", timestamp), packageName));
    }

    public void logHealthData(int priority, @NonNull String key, @NonNull IObjectWrapper context1, @NonNull IObjectWrapper context2, @NonNull IObjectWrapper context3) {
        Object obj;
        Object obj2;
        Object obj3;
        zzb();
        if (context1 == null) {
            obj = null;
        } else {
            obj = ObjectWrapper.unwrap(context1);
        }
        if (context2 == null) {
            obj2 = null;
        } else {
            obj2 = ObjectWrapper.unwrap(context2);
        }
        if (context3 == null) {
            obj3 = null;
        } else {
            obj3 = ObjectWrapper.unwrap(context3);
        }
        this.zza.zzaA().zzu(priority, true, false, key, obj, obj2, obj3);
    }

    public void onActivityCreated(@NonNull IObjectWrapper activity, @NonNull Bundle savedInstanceState, long j) {
        zzb();
        zzii zzii = this.zza.zzq().zza;
        if (zzii != null) {
            this.zza.zzq().zzB();
            zzii.onActivityCreated((Activity) ObjectWrapper.unwrap(activity), savedInstanceState);
        }
    }

    public void onActivityDestroyed(@NonNull IObjectWrapper activity, long j) {
        zzb();
        zzii zzii = this.zza.zzq().zza;
        if (zzii != null) {
            this.zza.zzq().zzB();
            zzii.onActivityDestroyed((Activity) ObjectWrapper.unwrap(activity));
        }
    }

    public void onActivityPaused(@NonNull IObjectWrapper activity, long j) {
        zzb();
        zzii zzii = this.zza.zzq().zza;
        if (zzii != null) {
            this.zza.zzq().zzB();
            zzii.onActivityPaused((Activity) ObjectWrapper.unwrap(activity));
        }
    }

    public void onActivityResumed(@NonNull IObjectWrapper activity, long j) {
        zzb();
        zzii zzii = this.zza.zzq().zza;
        if (zzii != null) {
            this.zza.zzq().zzB();
            zzii.onActivityResumed((Activity) ObjectWrapper.unwrap(activity));
        }
    }

    public void onActivitySaveInstanceState(IObjectWrapper activity, zzcf receiver, long j) {
        zzb();
        zzii zzii = this.zza.zzq().zza;
        Bundle bundle = new Bundle();
        if (zzii != null) {
            this.zza.zzq().zzB();
            zzii.onActivitySaveInstanceState((Activity) ObjectWrapper.unwrap(activity), bundle);
        }
        try {
            receiver.zze(bundle);
        } catch (RemoteException e) {
            this.zza.zzaA().zzk().zzb("Error returning bundle value to wrapper", e);
        }
    }

    public void onActivityStarted(@NonNull IObjectWrapper activity, long j) {
        zzb();
        if (this.zza.zzq().zza != null) {
            this.zza.zzq().zzB();
            Activity activity2 = (Activity) ObjectWrapper.unwrap(activity);
        }
    }

    public void onActivityStopped(@NonNull IObjectWrapper activity, long j) {
        zzb();
        if (this.zza.zzq().zza != null) {
            this.zza.zzq().zzB();
            Activity activity2 = (Activity) ObjectWrapper.unwrap(activity);
        }
    }

    public void performAction(Bundle bundle, zzcf receiver, long j) {
        zzb();
        receiver.zze((Bundle) null);
    }

    public void registerOnMeasurementEventListener(zzci listenerProxy) {
        zzhf zzhf;
        zzb();
        synchronized (this.zzb) {
            zzhf = (zzhf) this.zzb.get(Integer.valueOf(listenerProxy.zzd()));
            if (zzhf == null) {
                zzhf = new zzp(this, listenerProxy);
                this.zzb.put(Integer.valueOf(listenerProxy.zzd()), zzhf);
            }
        }
        this.zza.zzq().zzJ(zzhf);
    }

    public void resetAnalyticsData(long timestamp) {
        zzb();
        this.zza.zzq().zzK(timestamp);
    }

    public void setConditionalUserProperty(@NonNull Bundle conditionalUserProperty, long timestamp) {
        zzb();
        if (conditionalUserProperty == null) {
            this.zza.zzaA().zzd().zza("Conditional user property must not be null");
        } else {
            this.zza.zzq().zzQ(conditionalUserProperty, timestamp);
        }
    }

    public void setConsent(@NonNull Bundle consentMap, long timestamp) {
        zzb();
        zzij zzq = this.zza.zzq();
        zzq.zzt.zzaB().zzq(new zzhi(zzq, consentMap, timestamp));
    }

    public void setConsentThirdParty(@NonNull Bundle consentMap, long timestamp) {
        zzb();
        this.zza.zzq().zzR(consentMap, -20, timestamp);
    }

    public void setCurrentScreen(@NonNull IObjectWrapper activity, @NonNull String screenName, @NonNull String screenClassOverride, long j) {
        zzb();
        this.zza.zzs().zzw((Activity) ObjectWrapper.unwrap(activity), screenName, screenClassOverride);
    }

    public void setDataCollectionEnabled(boolean enabled) {
        zzb();
        zzij zzq = this.zza.zzq();
        zzq.zza();
        zzq.zzt.zzaB().zzp(new zzig(zzq, enabled));
    }

    public void setDefaultEventParameters(@NonNull Bundle parameters) {
        Bundle bundle;
        zzb();
        zzij zzq = this.zza.zzq();
        if (parameters == null) {
            bundle = null;
        } else {
            bundle = new Bundle(parameters);
        }
        zzq.zzt.zzaB().zzp(new zzhj(zzq, bundle));
    }

    public void setEventInterceptor(zzci interceptor) {
        zzb();
        zzo zzo = new zzo(this, interceptor);
        if (this.zza.zzaB().zzs()) {
            this.zza.zzq().zzT(zzo);
        } else {
            this.zza.zzaB().zzp(new zzl(this, zzo));
        }
    }

    public void setInstanceIdProvider(zzck zzck) {
        zzb();
    }

    public void setMeasurementEnabled(boolean enabled, long j) {
        zzb();
        this.zza.zzq().zzU(Boolean.valueOf(enabled));
    }

    public void setMinimumSessionDuration(long j) {
        zzb();
    }

    public void setSessionTimeoutDuration(long milliseconds) {
        zzb();
        zzij zzq = this.zza.zzq();
        zzq.zzt.zzaB().zzp(new zzhn(zzq, milliseconds));
    }

    public void setUserId(@NonNull String id, long timestamp) {
        zzb();
        zzij zzq = this.zza.zzq();
        if (id == null || !TextUtils.isEmpty(id)) {
            zzq.zzt.zzaB().zzp(new zzhk(zzq, id));
            zzq.zzX((String) null, "_id", id, true, timestamp);
            return;
        }
        zzq.zzt.zzaA().zzk().zza("User ID must be non-empty or null");
    }

    public void setUserProperty(@NonNull String origin, @NonNull String name, @NonNull IObjectWrapper value, boolean isInternal, long timestamp) {
        zzb();
        this.zza.zzq().zzX(origin, name, ObjectWrapper.unwrap(value), isInternal, timestamp);
    }

    public void unregisterOnMeasurementEventListener(zzci listenerProxy) {
        zzhf zzhf;
        zzb();
        synchronized (this.zzb) {
            zzhf = (zzhf) this.zzb.remove(Integer.valueOf(listenerProxy.zzd()));
        }
        if (zzhf == null) {
            zzhf = new zzp(this, listenerProxy);
        }
        this.zza.zzq().zzZ(zzhf);
    }
}

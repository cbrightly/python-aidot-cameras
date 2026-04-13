package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzca extends zzbm implements zzcc {
    zzca(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    public final void beginAdUnitExposure(String str, long j) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeLong(j);
        zzc(23, zza);
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzbo.zzd(zza, bundle);
        zzc(9, zza);
    }

    public final void clearMeasurementEnabled(long j) {
        Parcel zza = zza();
        zza.writeLong(j);
        zzc(43, zza);
    }

    public final void endAdUnitExposure(String str, long j) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeLong(j);
        zzc(24, zza);
    }

    public final void generateEventId(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(22, zza);
    }

    public final void getAppInstanceId(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(20, zza);
    }

    public final void getCachedAppInstanceId(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(19, zza);
    }

    public final void getConditionalUserProperties(String str, String str2, zzcf zzcf) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzbo.zze(zza, zzcf);
        zzc(10, zza);
    }

    public final void getCurrentScreenClass(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(17, zza);
    }

    public final void getCurrentScreenName(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(16, zza);
    }

    public final void getGmpAppId(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(21, zza);
    }

    public final void getMaxUserProperties(String str, zzcf zzcf) {
        Parcel zza = zza();
        zza.writeString(str);
        zzbo.zze(zza, zzcf);
        zzc(6, zza);
    }

    public final void getSessionId(zzcf zzcf) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zzc(46, zza);
    }

    public final void getTestFlag(zzcf zzcf, int i) {
        Parcel zza = zza();
        zzbo.zze(zza, zzcf);
        zza.writeInt(i);
        zzc(38, zza);
    }

    public final void getUserProperties(String str, String str2, boolean z, zzcf zzcf) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        int i = zzbo.zza;
        zza.writeInt(z ? 1 : 0);
        zzbo.zze(zza, zzcf);
        zzc(5, zza);
    }

    public final void initForTests(Map map) {
        throw null;
    }

    public final void initialize(IObjectWrapper iObjectWrapper, zzcl zzcl, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zzbo.zzd(zza, zzcl);
        zza.writeLong(j);
        zzc(1, zza);
    }

    public final void isDataCollectionEnabled(zzcf zzcf) {
        throw null;
    }

    public final void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzbo.zzd(zza, bundle);
        zza.writeInt(z ? 1 : 0);
        zza.writeInt(z2 ? 1 : 0);
        zza.writeLong(j);
        zzc(2, zza);
    }

    public final void logEventAndBundle(String str, String str2, Bundle bundle, zzcf zzcf, long j) {
        throw null;
    }

    public final void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) {
        Parcel zza = zza();
        zza.writeInt(5);
        zza.writeString(str);
        zzbo.zze(zza, iObjectWrapper);
        zzbo.zze(zza, iObjectWrapper2);
        zzbo.zze(zza, iObjectWrapper3);
        zzc(33, zza);
    }

    public final void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zzbo.zzd(zza, bundle);
        zza.writeLong(j);
        zzc(27, zza);
    }

    public final void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zza.writeLong(j);
        zzc(28, zza);
    }

    public final void onActivityPaused(IObjectWrapper iObjectWrapper, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zza.writeLong(j);
        zzc(29, zza);
    }

    public final void onActivityResumed(IObjectWrapper iObjectWrapper, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zza.writeLong(j);
        zzc(30, zza);
    }

    public final void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzcf zzcf, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zzbo.zze(zza, zzcf);
        zza.writeLong(j);
        zzc(31, zza);
    }

    public final void onActivityStarted(IObjectWrapper iObjectWrapper, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zza.writeLong(j);
        zzc(25, zza);
    }

    public final void onActivityStopped(IObjectWrapper iObjectWrapper, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zza.writeLong(j);
        zzc(26, zza);
    }

    public final void performAction(Bundle bundle, zzcf zzcf, long j) {
        Parcel zza = zza();
        zzbo.zzd(zza, bundle);
        zzbo.zze(zza, zzcf);
        zza.writeLong(j);
        zzc(32, zza);
    }

    public final void registerOnMeasurementEventListener(zzci zzci) {
        Parcel zza = zza();
        zzbo.zze(zza, zzci);
        zzc(35, zza);
    }

    public final void resetAnalyticsData(long j) {
        Parcel zza = zza();
        zza.writeLong(j);
        zzc(12, zza);
    }

    public final void setConditionalUserProperty(Bundle bundle, long j) {
        Parcel zza = zza();
        zzbo.zzd(zza, bundle);
        zza.writeLong(j);
        zzc(8, zza);
    }

    public final void setConsent(Bundle bundle, long j) {
        Parcel zza = zza();
        zzbo.zzd(zza, bundle);
        zza.writeLong(j);
        zzc(44, zza);
    }

    public final void setConsentThirdParty(Bundle bundle, long j) {
        Parcel zza = zza();
        zzbo.zzd(zza, bundle);
        zza.writeLong(j);
        zzc(45, zza);
    }

    public final void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j) {
        Parcel zza = zza();
        zzbo.zze(zza, iObjectWrapper);
        zza.writeString(str);
        zza.writeString(str2);
        zza.writeLong(j);
        zzc(15, zza);
    }

    public final void setDataCollectionEnabled(boolean z) {
        Parcel zza = zza();
        int i = zzbo.zza;
        zza.writeInt(z ? 1 : 0);
        zzc(39, zza);
    }

    public final void setDefaultEventParameters(Bundle bundle) {
        Parcel zza = zza();
        zzbo.zzd(zza, bundle);
        zzc(42, zza);
    }

    public final void setEventInterceptor(zzci zzci) {
        Parcel zza = zza();
        zzbo.zze(zza, zzci);
        zzc(34, zza);
    }

    public final void setInstanceIdProvider(zzck zzck) {
        throw null;
    }

    public final void setMeasurementEnabled(boolean z, long j) {
        Parcel zza = zza();
        int i = zzbo.zza;
        zza.writeInt(z ? 1 : 0);
        zza.writeLong(j);
        zzc(11, zza);
    }

    public final void setMinimumSessionDuration(long j) {
        throw null;
    }

    public final void setSessionTimeoutDuration(long j) {
        Parcel zza = zza();
        zza.writeLong(j);
        zzc(14, zza);
    }

    public final void setUserId(String str, long j) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeLong(j);
        zzc(7, zza);
    }

    public final void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j) {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zzbo.zze(zza, iObjectWrapper);
        zza.writeInt(z ? 1 : 0);
        zza.writeLong(j);
        zzc(4, zza);
    }

    public final void unregisterOnMeasurementEventListener(zzci zzci) {
        Parcel zza = zza();
        zzbo.zze(zza, zzci);
        zzc(36, zza);
    }
}

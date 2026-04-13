package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public interface zzcc extends IInterface {
    void beginAdUnitExposure(String str, long j);

    void clearConditionalUserProperty(String str, String str2, Bundle bundle);

    void clearMeasurementEnabled(long j);

    void endAdUnitExposure(String str, long j);

    void generateEventId(zzcf zzcf);

    void getAppInstanceId(zzcf zzcf);

    void getCachedAppInstanceId(zzcf zzcf);

    void getConditionalUserProperties(String str, String str2, zzcf zzcf);

    void getCurrentScreenClass(zzcf zzcf);

    void getCurrentScreenName(zzcf zzcf);

    void getGmpAppId(zzcf zzcf);

    void getMaxUserProperties(String str, zzcf zzcf);

    void getSessionId(zzcf zzcf);

    void getTestFlag(zzcf zzcf, int i);

    void getUserProperties(String str, String str2, boolean z, zzcf zzcf);

    void initForTests(Map map);

    void initialize(IObjectWrapper iObjectWrapper, zzcl zzcl, long j);

    void isDataCollectionEnabled(zzcf zzcf);

    void logEvent(String str, String str2, Bundle bundle, boolean z, boolean z2, long j);

    void logEventAndBundle(String str, String str2, Bundle bundle, zzcf zzcf, long j);

    void logHealthData(int i, String str, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3);

    void onActivityCreated(IObjectWrapper iObjectWrapper, Bundle bundle, long j);

    void onActivityDestroyed(IObjectWrapper iObjectWrapper, long j);

    void onActivityPaused(IObjectWrapper iObjectWrapper, long j);

    void onActivityResumed(IObjectWrapper iObjectWrapper, long j);

    void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, zzcf zzcf, long j);

    void onActivityStarted(IObjectWrapper iObjectWrapper, long j);

    void onActivityStopped(IObjectWrapper iObjectWrapper, long j);

    void performAction(Bundle bundle, zzcf zzcf, long j);

    void registerOnMeasurementEventListener(zzci zzci);

    void resetAnalyticsData(long j);

    void setConditionalUserProperty(Bundle bundle, long j);

    void setConsent(Bundle bundle, long j);

    void setConsentThirdParty(Bundle bundle, long j);

    void setCurrentScreen(IObjectWrapper iObjectWrapper, String str, String str2, long j);

    void setDataCollectionEnabled(boolean z);

    void setDefaultEventParameters(Bundle bundle);

    void setEventInterceptor(zzci zzci);

    void setInstanceIdProvider(zzck zzck);

    void setMeasurementEnabled(boolean z, long j);

    void setMinimumSessionDuration(long j);

    void setSessionTimeoutDuration(long j);

    void setUserId(String str, long j);

    void setUserProperty(String str, String str2, IObjectWrapper iObjectWrapper, boolean z, long j);

    void unregisterOnMeasurementEventListener(zzci zzci);
}

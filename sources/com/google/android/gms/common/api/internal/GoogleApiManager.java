package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArraySet;
import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.MethodInvocation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.RootTelemetryConfigManager;
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.TelemetryData;
import com.google.android.gms.common.internal.TelemetryLogging;
import com.google.android.gms.common.internal.TelemetryLoggingClient;
import com.google.android.gms.common.internal.zal;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.internal.base.zau;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

@ShowFirstParty
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class GoogleApiManager implements Handler.Callback {
    @NonNull
    public static final Status zaa = new Status(4, "Sign-out occurred while this API call was in progress.");
    /* access modifiers changed from: private */
    public static final Status zab = new Status(4, "The user must be signed in to make this API call.");
    /* access modifiers changed from: private */
    public static final Object zac = new Object();
    @GuardedBy("lock")
    @Nullable
    private static GoogleApiManager zad;
    /* access modifiers changed from: private */
    public long zae = 10000;
    /* access modifiers changed from: private */
    public boolean zaf = false;
    @Nullable
    private TelemetryData zag;
    @Nullable
    private TelemetryLoggingClient zah;
    /* access modifiers changed from: private */
    public final Context zai;
    /* access modifiers changed from: private */
    public final GoogleApiAvailability zaj;
    /* access modifiers changed from: private */
    public final zal zak;
    private final AtomicInteger zal = new AtomicInteger(1);
    private final AtomicInteger zam = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public final Map zan = new ConcurrentHashMap(5, 0.75f, 1);
    /* access modifiers changed from: private */
    @GuardedBy("lock")
    @Nullable
    public zaae zao = null;
    /* access modifiers changed from: private */
    @GuardedBy("lock")
    public final Set zap = new ArraySet();
    private final Set zaq = new ArraySet();
    /* access modifiers changed from: private */
    @NotOnlyInitialized
    public final Handler zar;
    /* access modifiers changed from: private */
    public volatile boolean zas = true;

    @KeepForSdk
    private GoogleApiManager(Context context, Looper looper, GoogleApiAvailability apiAvailability) {
        this.zai = context;
        zau zau = new zau(looper, this);
        this.zar = zau;
        this.zaj = apiAvailability;
        this.zak = new zal(apiAvailability);
        if (DeviceProperties.isAuto(context)) {
            this.zas = false;
        }
        zau.sendMessage(zau.obtainMessage(6));
    }

    @KeepForSdk
    public static void reportSignOut() {
        synchronized (zac) {
            GoogleApiManager googleApiManager = zad;
            if (googleApiManager != null) {
                googleApiManager.zam.incrementAndGet();
                Handler handler = googleApiManager.zar;
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(10));
            }
        }
    }

    /* access modifiers changed from: private */
    public static Status zaF(ApiKey apiKey, ConnectionResult connectionResult) {
        String zaa2 = apiKey.zaa();
        String valueOf = String.valueOf(connectionResult);
        return new Status(connectionResult, "API: " + zaa2 + " is not available on this device. Connection failed with: " + valueOf);
    }

    @ResultIgnorabilityUnspecified
    @WorkerThread
    private final zabq zaG(GoogleApi googleApi) {
        ApiKey apiKey = googleApi.getApiKey();
        zabq zabq = (zabq) this.zan.get(apiKey);
        if (zabq == null) {
            zabq = new zabq(this, googleApi);
            this.zan.put(apiKey, zabq);
        }
        if (zabq.zaz()) {
            this.zaq.add(apiKey);
        }
        zabq.zao();
        return zabq;
    }

    @WorkerThread
    private final TelemetryLoggingClient zaH() {
        if (this.zah == null) {
            this.zah = TelemetryLogging.getClient(this.zai);
        }
        return this.zah;
    }

    @WorkerThread
    private final void zaI() {
        TelemetryData telemetryData = this.zag;
        if (telemetryData != null) {
            if (telemetryData.zaa() > 0 || zaD()) {
                zaH().log(telemetryData);
            }
            this.zag = null;
        }
    }

    private final void zaJ(TaskCompletionSource taskCompletionSource, int i, GoogleApi googleApi) {
        zacd zaa2;
        if (i != 0 && (zaa2 = zacd.zaa(this, i, googleApi.getApiKey())) != null) {
            Task task = taskCompletionSource.getTask();
            Handler handler = this.zar;
            handler.getClass();
            task.addOnCompleteListener((Executor) new zabk(handler), zaa2);
        }
    }

    @NonNull
    public static GoogleApiManager zaj() {
        GoogleApiManager googleApiManager;
        synchronized (zac) {
            Preconditions.checkNotNull(zad, "Must guarantee manager is non-null before using getInstance");
            googleApiManager = zad;
        }
        return googleApiManager;
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    public static GoogleApiManager zak(@NonNull Context context) {
        GoogleApiManager googleApiManager;
        synchronized (zac) {
            if (zad == null) {
                zad = new GoogleApiManager(context.getApplicationContext(), GmsClientSupervisor.getOrStartHandlerThread().getLooper(), GoogleApiAvailability.getInstance());
            }
            googleApiManager = zad;
        }
        return googleApiManager;
    }

    @WorkerThread
    public final boolean handleMessage(@NonNull Message message) {
        int i = message.what;
        long j = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
        zabq zabq = null;
        switch (i) {
            case 1:
                if (true == ((Boolean) message.obj).booleanValue()) {
                    j = 10000;
                }
                this.zae = j;
                this.zar.removeMessages(12);
                for (ApiKey obtainMessage : this.zan.keySet()) {
                    Handler handler = this.zar;
                    handler.sendMessageDelayed(handler.obtainMessage(12, obtainMessage), this.zae);
                }
                break;
            case 2:
                zal zal2 = (zal) message.obj;
                Iterator it = zal2.zab().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else {
                        ApiKey apiKey = (ApiKey) it.next();
                        zabq zabq2 = (zabq) this.zan.get(apiKey);
                        if (zabq2 == null) {
                            zal2.zac(apiKey, new ConnectionResult(13), (String) null);
                            break;
                        } else if (zabq2.zay()) {
                            zal2.zac(apiKey, ConnectionResult.RESULT_SUCCESS, zabq2.zaf().getEndpointPackageName());
                        } else {
                            ConnectionResult zad2 = zabq2.zad();
                            if (zad2 != null) {
                                zal2.zac(apiKey, zad2, (String) null);
                            } else {
                                zabq2.zat(zal2);
                                zabq2.zao();
                            }
                        }
                    }
                }
            case 3:
                for (zabq zabq3 : this.zan.values()) {
                    zabq3.zan();
                    zabq3.zao();
                }
                break;
            case 4:
            case 8:
            case 13:
                zach zach = (zach) message.obj;
                zabq zabq4 = (zabq) this.zan.get(zach.zac.getApiKey());
                if (zabq4 == null) {
                    zabq4 = zaG(zach.zac);
                }
                if (zabq4.zaz() && this.zam.get() != zach.zab) {
                    zach.zaa.zad(zaa);
                    zabq4.zav();
                    break;
                } else {
                    zabq4.zap(zach.zaa);
                    break;
                }
            case 5:
                int i2 = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                Iterator it2 = this.zan.values().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        zabq zabq5 = (zabq) it2.next();
                        if (zabq5.zab() == i2) {
                            zabq = zabq5;
                        }
                    }
                }
                if (zabq != null) {
                    if (connectionResult.getErrorCode() != 13) {
                        zabq.zaD(zaF(zabq.zad, connectionResult));
                        break;
                    } else {
                        String errorString = this.zaj.getErrorString(connectionResult.getErrorCode());
                        String errorMessage = connectionResult.getErrorMessage();
                        zabq.zaD(new Status(17, "Error resolution was canceled by the user, original error message: " + errorString + ": " + errorMessage));
                        break;
                    }
                } else {
                    Log.wtf("GoogleApiManager", "Could not find API instance " + i2 + " while trying to fail enqueued calls.", new Exception());
                    break;
                }
            case 6:
                if (this.zai.getApplicationContext() instanceof Application) {
                    BackgroundDetector.initialize((Application) this.zai.getApplicationContext());
                    BackgroundDetector.getInstance().addListener(new zabl(this));
                    if (!BackgroundDetector.getInstance().readCurrentStateIfPossible(true)) {
                        this.zae = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
                        break;
                    }
                }
                break;
            case 7:
                zaG((GoogleApi) message.obj);
                break;
            case 9:
                if (this.zan.containsKey(message.obj)) {
                    ((zabq) this.zan.get(message.obj)).zau();
                    break;
                }
                break;
            case 10:
                for (ApiKey remove : this.zaq) {
                    zabq zabq6 = (zabq) this.zan.remove(remove);
                    if (zabq6 != null) {
                        zabq6.zav();
                    }
                }
                this.zaq.clear();
                break;
            case 11:
                if (this.zan.containsKey(message.obj)) {
                    ((zabq) this.zan.get(message.obj)).zaw();
                    break;
                }
                break;
            case 12:
                if (this.zan.containsKey(message.obj)) {
                    ((zabq) this.zan.get(message.obj)).zaA();
                    break;
                }
                break;
            case 14:
                zaaf zaaf = (zaaf) message.obj;
                ApiKey zaa2 = zaaf.zaa();
                if (this.zan.containsKey(zaa2)) {
                    zaaf.zab().setResult(Boolean.valueOf(((zabq) this.zan.get(zaa2)).zaN(false)));
                    break;
                } else {
                    zaaf.zab().setResult(false);
                    break;
                }
            case 15:
                zabs zabs = (zabs) message.obj;
                if (this.zan.containsKey(zabs.zaa)) {
                    zabq.zal((zabq) this.zan.get(zabs.zaa), zabs);
                    break;
                }
                break;
            case 16:
                zabs zabs2 = (zabs) message.obj;
                if (this.zan.containsKey(zabs2.zaa)) {
                    zabq.zam((zabq) this.zan.get(zabs2.zaa), zabs2);
                    break;
                }
                break;
            case 17:
                zaI();
                break;
            case 18:
                zace zace = (zace) message.obj;
                if (zace.zac != 0) {
                    TelemetryData telemetryData = this.zag;
                    if (telemetryData != null) {
                        List zab2 = telemetryData.zab();
                        if (telemetryData.zaa() != zace.zab || (zab2 != null && zab2.size() >= zace.zad)) {
                            this.zar.removeMessages(17);
                            zaI();
                        } else {
                            this.zag.zac(zace.zaa);
                        }
                    }
                    if (this.zag == null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(zace.zaa);
                        this.zag = new TelemetryData(zace.zab, arrayList);
                        Handler handler2 = this.zar;
                        handler2.sendMessageDelayed(handler2.obtainMessage(17), zace.zac);
                        break;
                    }
                } else {
                    zaH().log(new TelemetryData(zace.zab, Arrays.asList(new MethodInvocation[]{zace.zaa})));
                    break;
                }
                break;
            case 19:
                this.zaf = false;
                break;
            default:
                Log.w("GoogleApiManager", "Unknown message id: " + i);
                return false;
        }
        return true;
    }

    public final void zaA(@NonNull zaae zaae) {
        synchronized (zac) {
            if (this.zao != zaae) {
                this.zao = zaae;
                this.zap.clear();
            }
            this.zap.addAll(zaae.zaa());
        }
    }

    /* access modifiers changed from: package-private */
    public final void zaB(@NonNull zaae zaae) {
        synchronized (zac) {
            if (this.zao == zaae) {
                this.zao = null;
                this.zap.clear();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zaD() {
        if (this.zaf) {
            return false;
        }
        RootTelemetryConfiguration config = RootTelemetryConfigManager.getInstance().getConfig();
        if (config != null && !config.getMethodInvocationTelemetryEnabled()) {
            return false;
        }
        int zaa2 = this.zak.zaa(this.zai, 203400000);
        if (zaa2 == -1 || zaa2 == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @ResultIgnorabilityUnspecified
    public final boolean zaE(ConnectionResult connectionResult, int i) {
        return this.zaj.zah(this.zai, connectionResult, i);
    }

    public final int zaa() {
        return this.zal.getAndIncrement();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zabq zai(ApiKey apiKey) {
        return (zabq) this.zan.get(apiKey);
    }

    @NonNull
    public final Task zam(@NonNull Iterable iterable) {
        zal zal2 = new zal(iterable);
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(2, zal2));
        return zal2.zaa();
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    public final Task zan(@NonNull GoogleApi googleApi) {
        zaaf zaaf = new zaaf(googleApi.getApiKey());
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(14, zaaf));
        return zaaf.zab().getTask();
    }

    @NonNull
    public final Task zao(@NonNull GoogleApi googleApi, @NonNull RegisterListenerMethod registerListenerMethod, @NonNull UnregisterListenerMethod unregisterListenerMethod, @NonNull Runnable runnable) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zaJ(taskCompletionSource, registerListenerMethod.zaa(), googleApi);
        zaf zaf2 = new zaf(new zaci(registerListenerMethod, unregisterListenerMethod, runnable), taskCompletionSource);
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(8, new zach(zaf2, this.zam.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    @NonNull
    public final Task zap(@NonNull GoogleApi googleApi, @NonNull ListenerHolder.ListenerKey listenerKey, int i) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zaJ(taskCompletionSource, i, googleApi);
        zah zah2 = new zah(listenerKey, taskCompletionSource);
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(13, new zach(zah2, this.zam.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final void zau(@NonNull GoogleApi googleApi, int i, @NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        zae zae2 = new zae(i, apiMethodImpl);
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(4, new zach(zae2, this.zam.get(), googleApi)));
    }

    public final void zav(@NonNull GoogleApi googleApi, int i, @NonNull TaskApiCall taskApiCall, @NonNull TaskCompletionSource taskCompletionSource, @NonNull StatusExceptionMapper statusExceptionMapper) {
        zaJ(taskCompletionSource, taskApiCall.zaa(), googleApi);
        zag zag2 = new zag(i, taskApiCall, taskCompletionSource, statusExceptionMapper);
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(4, new zach(zag2, this.zam.get(), googleApi)));
    }

    /* access modifiers changed from: package-private */
    public final void zaw(MethodInvocation methodInvocation, int i, long j, int i2) {
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(18, new zace(methodInvocation, i, j, i2)));
    }

    public final void zax(@NonNull ConnectionResult connectionResult, int i) {
        if (!zaE(connectionResult, i)) {
            Handler handler = this.zar;
            handler.sendMessage(handler.obtainMessage(5, i, 0, connectionResult));
        }
    }

    public final void zay() {
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(3));
    }

    public final void zaz(@NonNull GoogleApi googleApi) {
        Handler handler = this.zar;
        handler.sendMessage(handler.obtainMessage(7, googleApi));
    }
}

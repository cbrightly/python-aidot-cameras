package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.maps.android.BuildConfig;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class BaseGmsClient<T extends IInterface> {
    @KeepForSdk
    public static final int CONNECT_STATE_CONNECTED = 4;
    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    @NonNull
    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    @NonNull
    @KeepForSdk
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", "service_googleme"};
    @NonNull
    @KeepForSdk
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    private static final Feature[] zze = new Feature[0];
    @Nullable
    private volatile String zzA;
    /* access modifiers changed from: private */
    @Nullable
    public ConnectionResult zzB;
    /* access modifiers changed from: private */
    public boolean zzC;
    @Nullable
    private volatile zzk zzD;
    @VisibleForTesting
    zzv zza;
    final Handler zzb;
    @NonNull
    @VisibleForTesting
    protected ConnectionProgressReportCallbacks zzc;
    @NonNull
    @VisibleForTesting
    protected AtomicInteger zzd;
    private int zzf;
    private long zzg;
    private long zzh;
    private int zzi;
    private long zzj;
    @Nullable
    private volatile String zzk;
    private final Context zzl;
    private final Looper zzm;
    private final GmsClientSupervisor zzn;
    private final GoogleApiAvailabilityLight zzo;
    private final Object zzp;
    /* access modifiers changed from: private */
    public final Object zzq;
    /* access modifiers changed from: private */
    @GuardedBy("serviceBrokerLock")
    @Nullable
    public IGmsServiceBroker zzr;
    @GuardedBy("lock")
    @Nullable
    private IInterface zzs;
    /* access modifiers changed from: private */
    public final ArrayList zzt;
    @GuardedBy("lock")
    @Nullable
    private zze zzu;
    @GuardedBy("lock")
    private int zzv;
    /* access modifiers changed from: private */
    @Nullable
    public final BaseConnectionCallbacks zzw;
    /* access modifiers changed from: private */
    @Nullable
    public final BaseOnConnectionFailedListener zzx;
    private final int zzy;
    @Nullable
    private final String zzz;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public interface BaseConnectionCallbacks {
        @KeepForSdk
        public static final int CAUSE_DEAD_OBJECT_EXCEPTION = 3;
        @KeepForSdk
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        @KeepForSdk
        void onConnected(@Nullable Bundle bundle);

        @KeepForSdk
        void onConnectionSuspended(int i);
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public interface BaseOnConnectionFailedListener {
        @KeepForSdk
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public interface ConnectionProgressReportCallbacks {
        @KeepForSdk
        void onReportServiceBinding(@NonNull ConnectionResult connectionResult);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        @KeepForSdk
        public LegacyClientCallbackAdapter() {
        }

        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService((IAccountAccessor) null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzx != null) {
                BaseGmsClient.this.zzx.onConnectionFailed(connectionResult);
            }
        }
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public interface SignOutCallbacks {
        @KeepForSdk
        void onSignOutComplete();
    }

    @KeepForSdk
    @VisibleForTesting
    protected BaseGmsClient(@NonNull Context context, @NonNull Handler handler, @NonNull GmsClientSupervisor supervisor, @NonNull GoogleApiAvailabilityLight apiAvailability, int gCoreServiceId, @Nullable BaseConnectionCallbacks connectedListener, @Nullable BaseOnConnectionFailedListener connectionFailedListener) {
        this.zzk = null;
        this.zzp = new Object();
        this.zzq = new Object();
        this.zzt = new ArrayList();
        this.zzv = 1;
        this.zzB = null;
        this.zzC = false;
        this.zzD = null;
        this.zzd = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzb = handler;
        this.zzm = handler.getLooper();
        Preconditions.checkNotNull(supervisor, "Supervisor must not be null");
        this.zzn = supervisor;
        Preconditions.checkNotNull(apiAvailability, "API availability must not be null");
        this.zzo = apiAvailability;
        this.zzy = gCoreServiceId;
        this.zzw = connectedListener;
        this.zzx = connectionFailedListener;
        this.zzz = null;
    }

    static /* bridge */ /* synthetic */ void zzj(BaseGmsClient baseGmsClient, zzk zzk2) {
        RootTelemetryConfiguration rootTelemetryConfiguration;
        baseGmsClient.zzD = zzk2;
        if (baseGmsClient.usesClientTelemetry()) {
            ConnectionTelemetryConfiguration connectionTelemetryConfiguration = zzk2.zzd;
            RootTelemetryConfigManager instance = RootTelemetryConfigManager.getInstance();
            if (connectionTelemetryConfiguration == null) {
                rootTelemetryConfiguration = null;
            } else {
                rootTelemetryConfiguration = connectionTelemetryConfiguration.zza();
            }
            instance.zza(rootTelemetryConfiguration);
        }
    }

    static /* bridge */ /* synthetic */ void zzk(BaseGmsClient baseGmsClient, int i) {
        int i2;
        int i3;
        synchronized (baseGmsClient.zzp) {
            i2 = baseGmsClient.zzv;
        }
        if (i2 == 3) {
            baseGmsClient.zzC = true;
            i3 = 5;
        } else {
            i3 = 4;
        }
        Handler handler = baseGmsClient.zzb;
        handler.sendMessage(handler.obtainMessage(i3, baseGmsClient.zzd.get(), 16));
    }

    static /* bridge */ /* synthetic */ boolean zzn(BaseGmsClient baseGmsClient, int i, int i2, IInterface iInterface) {
        synchronized (baseGmsClient.zzp) {
            if (baseGmsClient.zzv != i) {
                return false;
            }
            baseGmsClient.zzp(i2, iInterface);
            return true;
        }
    }

    static /* bridge */ /* synthetic */ boolean zzo(BaseGmsClient baseGmsClient) {
        if (baseGmsClient.zzC || TextUtils.isEmpty(baseGmsClient.getServiceDescriptor()) || TextUtils.isEmpty(baseGmsClient.getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(baseGmsClient.getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public final void zzp(int i, @Nullable IInterface iInterface) {
        zzv zzv2;
        zzv zzv3;
        boolean z = false;
        if ((i == 4) == (iInterface != null)) {
            z = true;
        }
        Preconditions.checkArgument(z);
        synchronized (this.zzp) {
            this.zzv = i;
            this.zzs = iInterface;
            switch (i) {
                case 1:
                    zze zze2 = this.zzu;
                    if (zze2 != null) {
                        GmsClientSupervisor gmsClientSupervisor = this.zzn;
                        String zzb2 = this.zza.zzb();
                        Preconditions.checkNotNull(zzb2);
                        gmsClientSupervisor.zzb(zzb2, this.zza.zza(), 4225, zze2, zze(), this.zza.zzc());
                        this.zzu = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    zze zze3 = this.zzu;
                    if (!(zze3 == null || (zzv3 = this.zza) == null)) {
                        Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + zzv3.zzb() + " on " + zzv3.zza());
                        GmsClientSupervisor gmsClientSupervisor2 = this.zzn;
                        String zzb3 = this.zza.zzb();
                        Preconditions.checkNotNull(zzb3);
                        gmsClientSupervisor2.zzb(zzb3, this.zza.zza(), 4225, zze3, zze(), this.zza.zzc());
                        this.zzd.incrementAndGet();
                    }
                    zze zze4 = new zze(this, this.zzd.get());
                    this.zzu = zze4;
                    if (this.zzv != 3 || getLocalStartServiceAction() == null) {
                        zzv2 = new zzv(getStartServicePackage(), getStartServiceAction(), false, 4225, getUseDynamicLookup());
                    } else {
                        zzv2 = new zzv(getContext().getPackageName(), getLocalStartServiceAction(), true, 4225, false);
                    }
                    this.zza = zzv2;
                    if (zzv2.zzc()) {
                        if (getMinApkVersion() < 17895000) {
                            throw new IllegalStateException("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat(String.valueOf(this.zza.zzb())));
                        }
                    }
                    GmsClientSupervisor gmsClientSupervisor3 = this.zzn;
                    String zzb4 = this.zza.zzb();
                    Preconditions.checkNotNull(zzb4);
                    if (!gmsClientSupervisor3.zzc(new zzo(zzb4, this.zza.zza(), 4225, this.zza.zzc()), zze4, zze(), getBindServiceExecutor())) {
                        Log.w("GmsClient", "unable to connect to service: " + this.zza.zzb() + " on " + this.zza.zza());
                        zzl(16, (Bundle) null, this.zzd.get());
                        break;
                    }
                    break;
                case 4:
                    Preconditions.checkNotNull(iInterface);
                    onConnectedLocked(iInterface);
                    break;
            }
        }
    }

    @KeepForSdk
    public void checkAvailabilityAndConnect() {
        int isGooglePlayServicesAvailable = this.zzo.isGooglePlayServicesAvailable(this.zzl, getMinApkVersion());
        if (isGooglePlayServicesAvailable != 0) {
            zzp(1, (IInterface) null);
            triggerNotAvailable(new LegacyClientCallbackAdapter(), isGooglePlayServicesAvailable, (PendingIntent) null);
            return;
        }
        connect(new LegacyClientCallbackAdapter());
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @KeepForSdk
    public void connect(@NonNull ConnectionProgressReportCallbacks callbacks) {
        Preconditions.checkNotNull(callbacks, "Connection progress callbacks cannot be null.");
        this.zzc = callbacks;
        zzp(2, (IInterface) null);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    @Nullable
    public abstract T createServiceInterface(@NonNull IBinder iBinder);

    @KeepForSdk
    public void disconnect() {
        this.zzd.incrementAndGet();
        synchronized (this.zzt) {
            int size = this.zzt.size();
            for (int i = 0; i < size; i++) {
                ((zzc) this.zzt.get(i)).zzf();
            }
            this.zzt.clear();
        }
        synchronized (this.zzq) {
            this.zzr = null;
        }
        zzp(1, (IInterface) null);
    }

    @KeepForSdk
    public void dump(@NonNull String prefix, @NonNull FileDescriptor fileDescriptor, @NonNull PrintWriter writer, @NonNull String[] strArr) {
        int i;
        IInterface iInterface;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzp) {
            i = this.zzv;
            iInterface = this.zzs;
        }
        synchronized (this.zzq) {
            iGmsServiceBroker = this.zzr;
        }
        writer.append(prefix).append("mConnectState=");
        switch (i) {
            case 1:
                writer.print("DISCONNECTED");
                break;
            case 2:
                writer.print("REMOTE_CONNECTING");
                break;
            case 3:
                writer.print("LOCAL_CONNECTING");
                break;
            case 4:
                writer.print("CONNECTED");
                break;
            case 5:
                writer.print("DISCONNECTING");
                break;
            default:
                writer.print(LDNetUtil.NETWORKTYPE_INVALID);
                break;
        }
        writer.append(" mService=");
        if (iInterface == null) {
            writer.append(BuildConfig.TRAVIS);
        } else {
            writer.append(getServiceDescriptor()).append("@").append(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        writer.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            writer.println(BuildConfig.TRAVIS);
        } else {
            writer.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzh > 0) {
            PrintWriter append = writer.append(prefix).append("lastConnectedTime=");
            long j = this.zzh;
            String format = simpleDateFormat.format(new Date(j));
            append.println(j + " " + format);
        }
        if (this.zzg > 0) {
            writer.append(prefix).append("lastSuspendedCause=");
            int i2 = this.zzf;
            switch (i2) {
                case 1:
                    writer.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case 2:
                    writer.append("CAUSE_NETWORK_LOST");
                    break;
                case 3:
                    writer.append("CAUSE_DEAD_OBJECT_EXCEPTION");
                    break;
                default:
                    writer.append(String.valueOf(i2));
                    break;
            }
            PrintWriter append2 = writer.append(" lastSuspendedTime=");
            long j2 = this.zzg;
            String format2 = simpleDateFormat.format(new Date(j2));
            append2.println(j2 + " " + format2);
        }
        if (this.zzj > 0) {
            writer.append(prefix).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzi));
            PrintWriter append3 = writer.append(" lastFailedTime=");
            long j3 = this.zzj;
            String format3 = simpleDateFormat.format(new Date(j3));
            append3.println(j3 + " " + format3);
        }
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public boolean enableLocalFallback() {
        return false;
    }

    @KeepForSdk
    @Nullable
    public Account getAccount() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public Feature[] getApiFeatures() {
        return zze;
    }

    @KeepForSdk
    @Nullable
    public final Feature[] getAvailableFeatures() {
        zzk zzk2 = this.zzD;
        if (zzk2 == null) {
            return null;
        }
        return zzk2.zzb;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    @Nullable
    public Executor getBindServiceExecutor() {
        return null;
    }

    @KeepForSdk
    @Nullable
    public Bundle getConnectionHint() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public final Context getContext() {
        return this.zzl;
    }

    @NonNull
    @KeepForSdk
    public String getEndpointPackageName() {
        zzv zzv2;
        if (isConnected() && (zzv2 = this.zza) != null) {
            return zzv2.zza();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }

    @KeepForSdk
    public int getGCoreServiceId() {
        return this.zzy;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    @KeepForSdk
    @Nullable
    public String getLastDisconnectMessage() {
        return this.zzk;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    @Nullable
    public String getLocalStartServiceAction() {
        return null;
    }

    @NonNull
    @KeepForSdk
    public final Looper getLooper() {
        return this.zzm;
    }

    @KeepForSdk
    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    @WorkerThread
    @KeepForSdk
    public void getRemoteService(@Nullable IAccountAccessor authedAccountAccessor, @NonNull Set<Scope> scopes) {
        Set<Scope> set = scopes;
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        int i = this.zzy;
        String str = this.zzA;
        int i2 = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        Scope[] scopeArr = GetServiceRequest.zza;
        Bundle bundle = new Bundle();
        Feature[] featureArr = GetServiceRequest.zzb;
        GetServiceRequest getServiceRequest = r3;
        GetServiceRequest getServiceRequest2 = new GetServiceRequest(6, i, i2, (String) null, (IBinder) null, scopeArr, bundle, (Account) null, featureArr, featureArr, true, 0, false, str);
        GetServiceRequest getServiceRequest3 = getServiceRequest;
        getServiceRequest3.zzf = this.zzl.getPackageName();
        getServiceRequest3.zzi = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest3.zzh = (Scope[]) set.toArray(new Scope[0]);
        }
        if (requiresSignIn()) {
            Account account = getAccount();
            if (account == null) {
                account = new Account("<<default account>>", AccountType.GOOGLE);
            }
            getServiceRequest3.zzj = account;
            if (authedAccountAccessor != null) {
                getServiceRequest3.zzg = authedAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest3.zzj = getAccount();
        }
        getServiceRequest3.zzk = zze;
        getServiceRequest3.zzl = getApiFeatures();
        if (usesClientTelemetry()) {
            getServiceRequest3.zzo = true;
        }
        try {
            synchronized (this.zzq) {
                IGmsServiceBroker iGmsServiceBroker = this.zzr;
                if (iGmsServiceBroker != null) {
                    iGmsServiceBroker.getService(new zzd(this, this.zzd.get()), getServiceRequest3);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            triggerConnectionSuspended(3);
        } catch (SecurityException e2) {
            throw e2;
        } catch (RemoteException | RuntimeException e3) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e3);
            onPostInitHandler(8, (IBinder) null, (Bundle) null, this.zzd.get());
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public Set<Scope> getScopes() {
        return Collections.emptySet();
    }

    @NonNull
    @KeepForSdk
    public final T getService() {
        T t;
        synchronized (this.zzp) {
            if (this.zzv != 5) {
                checkConnected();
                t = this.zzs;
                Preconditions.checkNotNull(t, "Client is connected but service is null");
            } else {
                throw new DeadObjectException();
            }
        }
        return t;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public abstract String getServiceDescriptor();

    @NonNull
    @KeepForSdk
    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public abstract String getStartServiceAction();

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public String getStartServicePackage() {
        return "com.google.android.gms";
    }

    @KeepForSdk
    @Nullable
    public ConnectionTelemetryConfiguration getTelemetryConfiguration() {
        zzk zzk2 = this.zzD;
        if (zzk2 == null) {
            return null;
        }
        return zzk2.zzd;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public boolean getUseDynamicLookup() {
        return getMinApkVersion() >= 211700000;
    }

    @KeepForSdk
    public boolean hasConnectionInfo() {
        return this.zzD != null;
    }

    @KeepForSdk
    public boolean isConnected() {
        boolean z;
        synchronized (this.zzp) {
            z = this.zzv == 4;
        }
        return z;
    }

    @KeepForSdk
    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzp) {
            int i = this.zzv;
            z = true;
            if (i != 2) {
                if (i != 3) {
                    z = false;
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    @KeepForSdk
    public void onConnectedLocked(@NonNull T t) {
        this.zzh = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    @KeepForSdk
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        this.zzi = result.getErrorCode();
        this.zzj = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    @KeepForSdk
    public void onConnectionSuspended(int cause) {
        this.zzf = cause;
        this.zzg = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public void onPostInitHandler(int statusCode, @Nullable IBinder service, @Nullable Bundle resolution, int disconnectCount) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(1, disconnectCount, -1, new zzf(this, statusCode, service, resolution)));
    }

    @KeepForSdk
    public void onUserSignOut(@NonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    @KeepForSdk
    public boolean providesSignIn() {
        return false;
    }

    @KeepForSdk
    public boolean requiresAccount() {
        return false;
    }

    @KeepForSdk
    public boolean requiresGooglePlayServices() {
        return true;
    }

    @KeepForSdk
    public boolean requiresSignIn() {
        return false;
    }

    @KeepForSdk
    public void setAttributionTag(@NonNull String str) {
        this.zzA = str;
    }

    @KeepForSdk
    public void triggerConnectionSuspended(int cause) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzd.get(), cause));
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    @VisibleForTesting
    public void triggerNotAvailable(@NonNull ConnectionProgressReportCallbacks callbacks, int errorCode, @Nullable PendingIntent resolution) {
        Preconditions.checkNotNull(callbacks, "Connection progress callbacks cannot be null.");
        this.zzc = callbacks;
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(3, this.zzd.get(), errorCode, resolution));
    }

    @KeepForSdk
    public boolean usesClientTelemetry() {
        return false;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zze() {
        String str = this.zzz;
        return str == null ? this.zzl.getClass().getName() : str;
    }

    /* access modifiers changed from: protected */
    public final void zzl(int i, @Nullable Bundle bundle, int i2) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzg(this, i, (Bundle) null)));
    }

    @KeepForSdk
    @Nullable
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzq) {
            IGmsServiceBroker iGmsServiceBroker = this.zzr;
            if (iGmsServiceBroker == null) {
                return null;
            }
            IBinder asBinder = iGmsServiceBroker.asBinder();
            return asBinder;
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected BaseGmsClient(@androidx.annotation.NonNull android.content.Context r10, @androidx.annotation.NonNull android.os.Looper r11, int r12, @androidx.annotation.Nullable com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks r13, @androidx.annotation.Nullable com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener r14, @androidx.annotation.Nullable java.lang.String r15) {
        /*
            r9 = this;
            com.google.android.gms.common.internal.GmsClientSupervisor r3 = com.google.android.gms.common.internal.GmsClientSupervisor.getInstance(r10)
            com.google.android.gms.common.GoogleApiAvailabilityLight r4 = com.google.android.gms.common.GoogleApiAvailabilityLight.getInstance()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r13)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r14)
            r0 = r9
            r1 = r10
            r2 = r11
            r5 = r12
            r6 = r13
            r7 = r14
            r8 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.BaseGmsClient.<init>(android.content.Context, android.os.Looper, int, com.google.android.gms.common.internal.BaseGmsClient$BaseConnectionCallbacks, com.google.android.gms.common.internal.BaseGmsClient$BaseOnConnectionFailedListener, java.lang.String):void");
    }

    @KeepForSdk
    public void disconnect(@NonNull String message) {
        this.zzk = message;
        disconnect();
    }

    @KeepForSdk
    @VisibleForTesting
    protected BaseGmsClient(@NonNull Context context, @NonNull Looper looper, @NonNull GmsClientSupervisor supervisor, @NonNull GoogleApiAvailabilityLight apiAvailability, int gCoreServiceId, @Nullable BaseConnectionCallbacks connectedListener, @Nullable BaseOnConnectionFailedListener connectionFailedListener, @Nullable String realClientName) {
        this.zzk = null;
        this.zzp = new Object();
        this.zzq = new Object();
        this.zzt = new ArrayList();
        this.zzv = 1;
        this.zzB = null;
        this.zzC = false;
        this.zzD = null;
        this.zzd = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzm = looper;
        Preconditions.checkNotNull(supervisor, "Supervisor must not be null");
        this.zzn = supervisor;
        Preconditions.checkNotNull(apiAvailability, "API availability must not be null");
        this.zzo = apiAvailability;
        this.zzb = new zzb(this, looper);
        this.zzy = gCoreServiceId;
        this.zzw = connectedListener;
        this.zzx = connectionFailedListener;
        this.zzz = realClientName;
    }
}

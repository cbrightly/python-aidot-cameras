package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.internal.zaj;
import com.google.android.gms.common.internal.zak;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zabe extends GoogleApiClient implements zabz {
    @VisibleForTesting
    final Queue zaa = new LinkedList();
    @Nullable
    @VisibleForTesting
    zabx zab;
    final Map zac;
    Set zad;
    final ClientSettings zae;
    final Map zaf;
    final Api.AbstractClientBuilder zag;
    @Nullable
    Set zah;
    final zadc zai;
    private final Lock zaj;
    private final zak zak;
    @Nullable
    private zaca zal = null;
    private final int zam;
    /* access modifiers changed from: private */
    public final Context zan;
    private final Looper zao;
    private volatile boolean zap;
    private long zaq;
    private long zar;
    private final zabc zas;
    private final GoogleApiAvailability zat;
    private final ListenerHolders zau;
    private final ArrayList zav;
    private Integer zaw;
    private final zaj zax;

    public zabe(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, Api.AbstractClientBuilder abstractClientBuilder, Map map, List list, List list2, Map map2, int i, int i2, ArrayList arrayList) {
        long j;
        Looper looper2 = looper;
        int i3 = i;
        if (true != ClientLibraryUtils.isPackageSide()) {
            j = 120000;
        } else {
            j = 10000;
        }
        this.zaq = j;
        this.zar = KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS;
        this.zad = new HashSet();
        this.zau = new ListenerHolders();
        this.zaw = null;
        this.zah = null;
        zaay zaay = new zaay(this);
        this.zax = zaay;
        this.zan = context;
        this.zaj = lock;
        this.zak = new zak(looper, zaay);
        this.zao = looper2;
        this.zas = new zabc(this, looper);
        this.zat = googleApiAvailability;
        this.zam = i3;
        if (i3 >= 0) {
            this.zaw = Integer.valueOf(i2);
        }
        this.zaf = map;
        this.zac = map2;
        this.zav = arrayList;
        this.zai = new zadc();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.zak.zaf((GoogleApiClient.ConnectionCallbacks) it.next());
        }
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            this.zak.zag((GoogleApiClient.OnConnectionFailedListener) it2.next());
        }
        this.zae = clientSettings;
        this.zag = abstractClientBuilder;
    }

    public static int zad(Iterable iterable, boolean z) {
        Iterator it = iterable.iterator();
        boolean z2 = false;
        boolean z3 = false;
        while (it.hasNext()) {
            Api.Client client = (Api.Client) it.next();
            z2 |= client.requiresSignIn();
            z3 |= client.providesSignIn();
        }
        if (z2) {
            return (!z3 || !z) ? 1 : 2;
        }
        return 3;
    }

    static String zag(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    static /* bridge */ /* synthetic */ void zai(zabe zabe) {
        zabe.zaj.lock();
        try {
            if (zabe.zap) {
                zabe.zan();
            }
        } finally {
            zabe.zaj.unlock();
        }
    }

    static /* bridge */ /* synthetic */ void zaj(zabe zabe) {
        zabe.zaj.lock();
        try {
            if (zabe.zak()) {
                zabe.zan();
            }
        } finally {
            zabe.zaj.unlock();
        }
    }

    private final void zal(int i) {
        Integer num = this.zaw;
        if (num == null) {
            this.zaw = Integer.valueOf(i);
        } else if (num.intValue() != i) {
            throw new IllegalStateException("Cannot use sign-in mode: " + zag(i) + ". Mode was already set to " + zag(this.zaw.intValue()));
        }
        if (this.zal == null) {
            boolean z = false;
            boolean z2 = false;
            for (Api.Client client : this.zac.values()) {
                z |= client.requiresSignIn();
                z2 |= client.providesSignIn();
            }
            switch (this.zaw.intValue()) {
                case 1:
                    if (!z) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (z2) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case 2:
                    if (z) {
                        this.zal = zaaa.zag(this.zan, this, this.zaj, this.zao, this.zat, this.zac, this.zae, this.zaf, this.zag, this.zav);
                        return;
                    }
                    break;
            }
            this.zal = new zabi(this.zan, this, this.zaj, this.zao, this.zat, this.zac, this.zae, this.zaf, this.zag, this.zav, this);
        }
    }

    /* access modifiers changed from: private */
    public final void zam(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean z) {
        Common.zaa.zaa(googleApiClient).setResultCallback(new zabb(this, statusPendingResult, z, googleApiClient));
    }

    @GuardedBy("mLock")
    private final void zan() {
        this.zak.zab();
        ((zaca) Preconditions.checkNotNull(this.zal)).zaq();
    }

    @ResultIgnorabilityUnspecified
    public final ConnectionResult blockingConnect() {
        boolean z = true;
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zaj.lock();
        try {
            if (this.zam >= 0) {
                if (this.zaw == null) {
                    z = false;
                }
                Preconditions.checkState(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else {
                Integer num = this.zaw;
                if (num == null) {
                    this.zaw = Integer.valueOf(zad(this.zac.values(), false));
                } else if (num.intValue() == 2) {
                    throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            zal(((Integer) Preconditions.checkNotNull(this.zaw)).intValue());
            this.zak.zab();
            return ((zaca) Preconditions.checkNotNull(this.zal)).zab();
        } finally {
            this.zaj.unlock();
        }
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        Preconditions.checkState(isConnected(), "GoogleApiClient is not connected yet.");
        Integer num = this.zaw;
        boolean z = true;
        if (num != null && num.intValue() == 2) {
            z = false;
        }
        Preconditions.checkState(z, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        StatusPendingResult statusPendingResult = new StatusPendingResult((GoogleApiClient) this);
        if (this.zac.containsKey(Common.CLIENT_KEY)) {
            zam(this, statusPendingResult, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            zaaz zaaz = new zaaz(this, atomicReference, statusPendingResult);
            zaba zaba = new zaba(this, statusPendingResult);
            GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this.zan);
            builder.addApi(Common.API);
            builder.addConnectionCallbacks(zaaz);
            builder.addOnConnectionFailedListener(zaba);
            builder.setHandler(this.zas);
            GoogleApiClient build = builder.build();
            atomicReference.set(build);
            build.connect();
        }
        return statusPendingResult;
    }

    public final void connect() {
        this.zaj.lock();
        try {
            int i = 2;
            boolean z = false;
            if (this.zam >= 0) {
                Preconditions.checkState(this.zaw != null, "Sign-in mode should have been set explicitly by auto-manage.");
            } else {
                Integer num = this.zaw;
                if (num == null) {
                    this.zaw = Integer.valueOf(zad(this.zac.values(), false));
                } else if (num.intValue() == 2) {
                    throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            int intValue = ((Integer) Preconditions.checkNotNull(this.zaw)).intValue();
            this.zaj.lock();
            if (intValue == 3 || intValue == 1) {
                i = intValue;
                z = true;
            } else if (intValue == 2) {
                z = true;
            } else {
                i = intValue;
            }
            Preconditions.checkArgument(z, "Illegal sign-in mode: " + i);
            zal(i);
            zan();
        } catch (Throwable th) {
            throw th;
        } finally {
            this.zaj.unlock();
        }
    }

    public final void disconnect() {
        Lock lock;
        this.zaj.lock();
        try {
            this.zai.zab();
            zaca zaca = this.zal;
            if (zaca != null) {
                zaca.zar();
            }
            this.zau.zab();
            for (BaseImplementation.ApiMethodImpl apiMethodImpl : this.zaa) {
                apiMethodImpl.zan((zadb) null);
                apiMethodImpl.cancel();
            }
            this.zaa.clear();
            if (this.zal == null) {
                lock = this.zaj;
            } else {
                zak();
                this.zak.zaa();
                lock = this.zaj;
            }
            lock.unlock();
        } catch (Throwable th) {
            this.zaj.unlock();
            throw th;
        }
    }

    public final void dump(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.zan);
        printWriter.append(str).append("mResuming=").print(this.zap);
        printWriter.append(" mWorkQueue.size()=").print(this.zaa.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zai.zab.size());
        zaca zaca = this.zal;
        if (zaca != null) {
            zaca.zas(str, fileDescriptor, printWriter, strArr);
        }
    }

    @ResultIgnorabilityUnspecified
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        String str;
        Lock lock;
        Api<?> api = t.getApi();
        boolean containsKey = this.zac.containsKey(t.getClientKey());
        if (api != null) {
            str = api.zad();
        } else {
            str = "the API";
        }
        Preconditions.checkArgument(containsKey, "GoogleApiClient is not configured to use " + str + " required for this call.");
        this.zaj.lock();
        try {
            zaca zaca = this.zal;
            if (zaca == null) {
                this.zaa.add(t);
                lock = this.zaj;
            } else {
                t = zaca.zae(t);
                lock = this.zaj;
            }
            lock.unlock();
            return t;
        } catch (Throwable th) {
            this.zaj.unlock();
            throw th;
        }
    }

    @ResultIgnorabilityUnspecified
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        Lock lock;
        Api<?> api = t.getApi();
        boolean containsKey = this.zac.containsKey(t.getClientKey());
        String zad2 = api != null ? api.zad() : "the API";
        Preconditions.checkArgument(containsKey, "GoogleApiClient is not configured to use " + zad2 + " required for this call.");
        this.zaj.lock();
        try {
            zaca zaca = this.zal;
            if (zaca != null) {
                if (this.zap) {
                    this.zaa.add(t);
                    while (!this.zaa.isEmpty()) {
                        BaseImplementation.ApiMethodImpl apiMethodImpl = (BaseImplementation.ApiMethodImpl) this.zaa.remove();
                        this.zai.zaa(apiMethodImpl);
                        apiMethodImpl.setFailedResult(Status.RESULT_INTERNAL_ERROR);
                    }
                    lock = this.zaj;
                } else {
                    t = zaca.zaf(t);
                    lock = this.zaj;
                }
                lock.unlock();
                return t;
            }
            throw new IllegalStateException("GoogleApiClient is not connected yet.");
        } catch (Throwable th) {
            this.zaj.unlock();
            throw th;
        }
    }

    @NonNull
    public final <C extends Api.Client> C getClient(@NonNull Api.AnyClientKey<C> anyClientKey) {
        C c = (Api.Client) this.zac.get(anyClientKey);
        Preconditions.checkNotNull(c, "Appropriate Api was not requested.");
        return c;
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        ConnectionResult connectionResult;
        Lock lock;
        this.zaj.lock();
        try {
            if (!isConnected()) {
                if (!this.zap) {
                    throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
                }
            }
            if (this.zac.containsKey(api.zab())) {
                ConnectionResult zad2 = ((zaca) Preconditions.checkNotNull(this.zal)).zad(api);
                if (zad2 != null) {
                    return zad2;
                }
                if (this.zap) {
                    connectionResult = ConnectionResult.RESULT_SUCCESS;
                    lock = this.zaj;
                } else {
                    Log.w("GoogleApiClientImpl", zaf());
                    String zad3 = api.zad();
                    Log.wtf("GoogleApiClientImpl", zad3 + " requested in getConnectionResult is not connected but is not present in the failed  connections map", new Exception());
                    connectionResult = new ConnectionResult(8, (PendingIntent) null);
                    lock = this.zaj;
                }
                lock.unlock();
                return connectionResult;
            }
            String zad4 = api.zad();
            throw new IllegalArgumentException(zad4 + " was never registered with GoogleApiClient");
        } finally {
            this.zaj.unlock();
        }
    }

    public final Context getContext() {
        return this.zan;
    }

    public final Looper getLooper() {
        return this.zao;
    }

    public final boolean hasApi(@NonNull Api<?> api) {
        return this.zac.containsKey(api.zab());
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        Api.Client client;
        if (isConnected() && (client = (Api.Client) this.zac.get(api.zab())) != null && client.isConnected()) {
            return true;
        }
        return false;
    }

    public final boolean isConnected() {
        zaca zaca = this.zal;
        return zaca != null && zaca.zaw();
    }

    public final boolean isConnecting() {
        zaca zaca = this.zal;
        return zaca != null && zaca.zax();
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zak.zaj(connectionCallbacks);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zak.zak(onConnectionFailedListener);
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        zaca zaca = this.zal;
        return zaca != null && zaca.zay(signInConnectionListener);
    }

    public final void maybeSignOut() {
        zaca zaca = this.zal;
        if (zaca != null) {
            zaca.zau();
        }
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zak.zaf(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zak.zag(onConnectionFailedListener);
    }

    public final <L> ListenerHolder<L> registerListener(@NonNull L l) {
        this.zaj.lock();
        try {
            return this.zau.zaa(l, this.zao, "NO_TYPE");
        } finally {
            this.zaj.unlock();
        }
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
        if (this.zam >= 0) {
            zak.zaa(lifecycleActivity).zae(this.zam);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zak.zah(connectionCallbacks);
    }

    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zak.zai(onConnectionFailedListener);
    }

    @GuardedBy("mLock")
    public final void zaa(ConnectionResult connectionResult) {
        if (!this.zat.isPlayServicesPossiblyUpdating(this.zan, connectionResult.getErrorCode())) {
            zak();
        }
        if (!this.zap) {
            this.zak.zac(connectionResult);
            this.zak.zaa();
        }
    }

    @GuardedBy("mLock")
    public final void zab(@Nullable Bundle bundle) {
        while (!this.zaa.isEmpty()) {
            execute((BaseImplementation.ApiMethodImpl) this.zaa.remove());
        }
        this.zak.zad(bundle);
    }

    /* access modifiers changed from: package-private */
    public final String zaf() {
        StringWriter stringWriter = new StringWriter();
        dump("", (FileDescriptor) null, new PrintWriter(stringWriter), (String[]) null);
        return stringWriter.toString();
    }

    /* access modifiers changed from: package-private */
    @ResultIgnorabilityUnspecified
    @GuardedBy("mLock")
    public final boolean zak() {
        if (!this.zap) {
            return false;
        }
        this.zap = false;
        this.zas.removeMessages(2);
        this.zas.removeMessages(1);
        zabx zabx = this.zab;
        if (zabx != null) {
            zabx.zab();
            this.zab = null;
        }
        return true;
    }

    public final void zao(zada zada) {
        this.zaj.lock();
        try {
            if (this.zah == null) {
                this.zah = new HashSet();
            }
            this.zah.add(zada);
        } finally {
            this.zaj.unlock();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
        if (r3 == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zap(com.google.android.gms.common.api.internal.zada r3) {
        /*
            r2 = this;
            java.util.concurrent.locks.Lock r0 = r2.zaj
            r0.lock()
            java.util.Set r0 = r2.zah     // Catch:{ all -> 0x0057 }
            java.lang.String r1 = "GoogleApiClientImpl"
            if (r0 != 0) goto L_0x0016
            java.lang.String r3 = "Attempted to remove pending transform when no transforms are registered."
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ all -> 0x0057 }
            r0.<init>()     // Catch:{ all -> 0x0057 }
            android.util.Log.wtf(r1, r3, r0)     // Catch:{ all -> 0x0057 }
            goto L_0x004a
        L_0x0016:
            boolean r3 = r0.remove(r3)     // Catch:{ all -> 0x0057 }
            if (r3 != 0) goto L_0x0027
            java.lang.String r3 = "Failed to remove pending transform - this may lead to memory leaks!"
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ all -> 0x0057 }
            r0.<init>()     // Catch:{ all -> 0x0057 }
            android.util.Log.wtf(r1, r3, r0)     // Catch:{ all -> 0x0057 }
            goto L_0x004a
        L_0x0027:
            java.util.concurrent.locks.Lock r3 = r2.zaj     // Catch:{ all -> 0x0057 }
            r3.lock()     // Catch:{ all -> 0x0057 }
            java.util.Set r3 = r2.zah     // Catch:{ all -> 0x0050 }
            if (r3 != 0) goto L_0x0036
            java.util.concurrent.locks.Lock r3 = r2.zaj     // Catch:{ all -> 0x0057 }
            r3.unlock()     // Catch:{ all -> 0x0057 }
            goto L_0x0043
        L_0x0036:
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0050 }
            r3 = r3 ^ 1
            java.util.concurrent.locks.Lock r0 = r2.zaj     // Catch:{ all -> 0x0057 }
            r0.unlock()     // Catch:{ all -> 0x0057 }
            if (r3 != 0) goto L_0x004a
        L_0x0043:
            com.google.android.gms.common.api.internal.zaca r3 = r2.zal     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x004a
            r3.zat()     // Catch:{ all -> 0x0057 }
        L_0x004a:
            java.util.concurrent.locks.Lock r3 = r2.zaj
            r3.unlock()
            return
        L_0x0050:
            r3 = move-exception
            java.util.concurrent.locks.Lock r0 = r2.zaj     // Catch:{ all -> 0x0057 }
            r0.unlock()     // Catch:{ all -> 0x0057 }
            throw r3     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r3 = move-exception
            java.util.concurrent.locks.Lock r0 = r2.zaj
            r0.unlock()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zabe.zap(com.google.android.gms.common.api.internal.zada):void");
    }

    @GuardedBy("mLock")
    public final void zac(int i, boolean z) {
        if (i == 1) {
            if (!z) {
                if (this.zap) {
                    i = 1;
                } else {
                    this.zap = true;
                    if (this.zab == null && !ClientLibraryUtils.isPackageSide()) {
                        try {
                            this.zab = this.zat.zac(this.zan.getApplicationContext(), new zabd(this));
                        } catch (SecurityException e) {
                        }
                    }
                    zabc zabc = this.zas;
                    zabc.sendMessageDelayed(zabc.obtainMessage(1), this.zaq);
                    zabc zabc2 = this.zas;
                    zabc2.sendMessageDelayed(zabc2.obtainMessage(2), this.zar);
                }
            }
            i = 1;
        }
        for (BasePendingResult forceFailureUnlessReady : (BasePendingResult[]) this.zai.zab.toArray(new BasePendingResult[0])) {
            forceFailureUnlessReady.forceFailureUnlessReady(zadc.zaa);
        }
        this.zak.zae(i);
        this.zak.zaa();
        if (i == 2) {
            zan();
        }
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        this.zaj.lock();
        try {
            Integer num = this.zaw;
            if (num == null) {
                this.zaw = Integer.valueOf(zad(this.zac.values(), false));
            } else if (num.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zal(((Integer) Preconditions.checkNotNull(this.zaw)).intValue());
            this.zak.zab();
            return ((zaca) Preconditions.checkNotNull(this.zal)).zac(j, timeUnit);
        } finally {
            this.zaj.unlock();
        }
    }

    public final void connect(int i) {
        this.zaj.lock();
        boolean z = true;
        if (!(i == 3 || i == 1)) {
            if (i == 2) {
                i = 2;
            } else {
                z = false;
            }
        }
        try {
            Preconditions.checkArgument(z, "Illegal sign-in mode: " + i);
            zal(i);
            zan();
        } finally {
            this.zaj.unlock();
        }
    }
}

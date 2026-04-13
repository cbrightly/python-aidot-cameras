package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.work.PeriodicWorkRequest;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.zap;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zabq implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, zau {
    final /* synthetic */ GoogleApiManager zaa;
    private final Queue zab = new LinkedList();
    /* access modifiers changed from: private */
    @NotOnlyInitialized
    public final Api.Client zac;
    /* access modifiers changed from: private */
    public final ApiKey zad;
    private final zaad zae;
    private final Set zaf = new HashSet();
    private final Map zag = new HashMap();
    private final int zah;
    @Nullable
    private final zact zai;
    private boolean zaj;
    private final List zak = new ArrayList();
    @Nullable
    private ConnectionResult zal = null;
    private int zam = 0;

    @WorkerThread
    public zabq(GoogleApiManager googleApiManager, GoogleApi googleApi) {
        this.zaa = googleApiManager;
        Api.Client zab2 = googleApi.zab(googleApiManager.zar.getLooper(), this);
        this.zac = zab2;
        this.zad = googleApi.getApiKey();
        this.zae = new zaad();
        this.zah = googleApi.zaa();
        if (zab2.requiresSignIn()) {
            this.zai = googleApi.zac(googleApiManager.zai, googleApiManager.zar);
        } else {
            this.zai = null;
        }
    }

    @WorkerThread
    @Nullable
    private final Feature zaB(@Nullable Feature[] featureArr) {
        if (featureArr == null || featureArr.length == 0) {
            return null;
        }
        Feature[] availableFeatures = this.zac.getAvailableFeatures();
        if (availableFeatures == null) {
            availableFeatures = new Feature[0];
        }
        ArrayMap arrayMap = new ArrayMap(availableFeatures.length);
        for (Feature feature : availableFeatures) {
            arrayMap.put(feature.getName(), Long.valueOf(feature.getVersion()));
        }
        for (Feature feature2 : featureArr) {
            Long l = (Long) arrayMap.get(feature2.getName());
            if (l == null || l.longValue() < feature2.getVersion()) {
                return feature2;
            }
        }
        return null;
    }

    @WorkerThread
    private final void zaC(ConnectionResult connectionResult) {
        String str;
        for (zal zal2 : this.zaf) {
            if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
                str = this.zac.getEndpointPackageName();
            } else {
                str = null;
            }
            zal2.zac(this.zad, connectionResult, str);
        }
        this.zaf.clear();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zaD(Status status) {
        Preconditions.checkHandlerThread(this.zaa.zar);
        zaE(status, (Exception) null, false);
    }

    @WorkerThread
    private final void zaE(@Nullable Status status, @Nullable Exception exc, boolean z) {
        boolean z2;
        Preconditions.checkHandlerThread(this.zaa.zar);
        boolean z3 = false;
        if (status != null) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (exc == null) {
            z3 = true;
        }
        if (z2 != z3) {
            Iterator it = this.zab.iterator();
            while (it.hasNext()) {
                zai zai2 = (zai) it.next();
                if (!z || zai2.zac == 2) {
                    if (status != null) {
                        zai2.zad(status);
                    } else {
                        zai2.zae(exc);
                    }
                    it.remove();
                }
            }
            return;
        }
        throw new IllegalArgumentException("Status XOR exception should be null");
    }

    @WorkerThread
    private final void zaF() {
        ArrayList arrayList = new ArrayList(this.zab);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            zai zai2 = (zai) arrayList.get(i);
            if (this.zac.isConnected()) {
                if (zaL(zai2)) {
                    this.zab.remove(zai2);
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zaG() {
        zan();
        zaC(ConnectionResult.RESULT_SUCCESS);
        zaK();
        Iterator it = this.zag.values().iterator();
        while (it.hasNext()) {
            zaci zaci = (zaci) it.next();
            if (zaB(zaci.zaa.getRequiredFeatures()) != null) {
                it.remove();
            } else {
                try {
                    zaci.zaa.registerListener(this.zac, new TaskCompletionSource());
                } catch (DeadObjectException e) {
                    onConnectionSuspended(3);
                    this.zac.disconnect("DeadObjectException thrown while calling register listener method.");
                } catch (RemoteException e2) {
                    it.remove();
                }
            }
        }
        zaF();
        zaI();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zaH(int i) {
        zan();
        this.zaj = true;
        this.zae.zae(i, this.zac.getLastDisconnectMessage());
        GoogleApiManager googleApiManager = this.zaa;
        googleApiManager.zar.sendMessageDelayed(Message.obtain(googleApiManager.zar, 9, this.zad), KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        GoogleApiManager googleApiManager2 = this.zaa;
        googleApiManager2.zar.sendMessageDelayed(Message.obtain(googleApiManager2.zar, 11, this.zad), 120000);
        this.zaa.zak.zac();
        for (zaci zaci : this.zag.values()) {
            zaci.zac.run();
        }
    }

    private final void zaI() {
        this.zaa.zar.removeMessages(12, this.zad);
        GoogleApiManager googleApiManager = this.zaa;
        googleApiManager.zar.sendMessageDelayed(googleApiManager.zar.obtainMessage(12, this.zad), this.zaa.zae);
    }

    @WorkerThread
    private final void zaJ(zai zai2) {
        zai2.zag(this.zae, zaz());
        try {
            zai2.zaf(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.zac.disconnect("DeadObjectException thrown while running ApiCallRunner.");
        }
    }

    @WorkerThread
    private final void zaK() {
        if (this.zaj) {
            this.zaa.zar.removeMessages(11, this.zad);
            this.zaa.zar.removeMessages(9, this.zad);
            this.zaj = false;
        }
    }

    @WorkerThread
    private final boolean zaL(zai zai2) {
        if (!(zai2 instanceof zac)) {
            zaJ(zai2);
            return true;
        }
        zac zac2 = (zac) zai2;
        Feature zaB = zaB(zac2.zab(this));
        if (zaB == null) {
            zaJ(zai2);
            return true;
        }
        String name = this.zac.getClass().getName();
        String name2 = zaB.getName();
        long version = zaB.getVersion();
        Log.w("GoogleApiManager", name + " could not execute call because it requires feature (" + name2 + ", " + version + ").");
        if (!this.zaa.zas || !zac2.zaa(this)) {
            zac2.zae(new UnsupportedApiCallException(zaB));
            return true;
        }
        zabs zabs = new zabs(this.zad, zaB, (zabr) null);
        int indexOf = this.zak.indexOf(zabs);
        if (indexOf >= 0) {
            zabs zabs2 = (zabs) this.zak.get(indexOf);
            this.zaa.zar.removeMessages(15, zabs2);
            GoogleApiManager googleApiManager = this.zaa;
            googleApiManager.zar.sendMessageDelayed(Message.obtain(googleApiManager.zar, 15, zabs2), KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
            return false;
        }
        this.zak.add(zabs);
        GoogleApiManager googleApiManager2 = this.zaa;
        googleApiManager2.zar.sendMessageDelayed(Message.obtain(googleApiManager2.zar, 15, zabs), KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        GoogleApiManager googleApiManager3 = this.zaa;
        googleApiManager3.zar.sendMessageDelayed(Message.obtain(googleApiManager3.zar, 16, zabs), 120000);
        ConnectionResult connectionResult = new ConnectionResult(2, (PendingIntent) null);
        if (zaM(connectionResult)) {
            return false;
        }
        this.zaa.zaE(connectionResult, this.zah);
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        return false;
     */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zaM(@androidx.annotation.NonNull com.google.android.gms.common.ConnectionResult r4) {
        /*
            r3 = this;
            java.lang.Object r0 = com.google.android.gms.common.api.internal.GoogleApiManager.zac
            monitor-enter(r0)
            com.google.android.gms.common.api.internal.GoogleApiManager r1 = r3.zaa     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.internal.zaae r2 = r1.zao     // Catch:{ all -> 0x002a }
            if (r2 == 0) goto L_0x0027
            java.util.Set r1 = r1.zap     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.internal.ApiKey r2 = r3.zad     // Catch:{ all -> 0x002a }
            boolean r1 = r1.contains(r2)     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0027
            com.google.android.gms.common.api.internal.GoogleApiManager r1 = r3.zaa     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.internal.zaae r1 = r1.zao     // Catch:{ all -> 0x002a }
            int r2 = r3.zah     // Catch:{ all -> 0x002a }
            r1.zah(r4, r2)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            r4 = 1
            return r4
        L_0x0027:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            r4 = 0
            return r4
        L_0x002a:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zabq.zaM(com.google.android.gms.common.ConnectionResult):boolean");
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final boolean zaN(boolean z) {
        Preconditions.checkHandlerThread(this.zaa.zar);
        if (!this.zac.isConnected() || this.zag.size() != 0) {
            return false;
        }
        if (this.zae.zag()) {
            if (z) {
                zaI();
            }
            return false;
        }
        this.zac.disconnect("Timing out service connection.");
        return true;
    }

    static /* bridge */ /* synthetic */ void zal(zabq zabq, zabs zabs) {
        if (!zabq.zak.contains(zabs) || zabq.zaj) {
            return;
        }
        if (!zabq.zac.isConnected()) {
            zabq.zao();
        } else {
            zabq.zaF();
        }
    }

    static /* bridge */ /* synthetic */ void zam(zabq zabq, zabs zabs) {
        Feature[] zab2;
        if (zabq.zak.remove(zabs)) {
            zabq.zaa.zar.removeMessages(15, zabs);
            zabq.zaa.zar.removeMessages(16, zabs);
            Feature zaa2 = zabs.zab;
            ArrayList arrayList = new ArrayList(zabq.zab.size());
            for (zai zai2 : zabq.zab) {
                if ((zai2 instanceof zac) && (zab2 = ((zac) zai2).zab(zabq)) != null && ArrayUtils.contains((T[]) zab2, zaa2)) {
                    arrayList.add(zai2);
                }
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zai zai3 = (zai) arrayList.get(i);
                zabq.zab.remove(zai3);
                zai3.zae(new UnsupportedApiCallException(zaa2));
            }
        }
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zaa.zar.getLooper()) {
            zaG();
        } else {
            this.zaa.zar.post(new zabm(this));
        }
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zar(connectionResult, (Exception) null);
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zaa.zar.getLooper()) {
            zaH(i);
        } else {
            this.zaa.zar.post(new zabn(this, i));
        }
    }

    @ResultIgnorabilityUnspecified
    @WorkerThread
    public final boolean zaA() {
        return zaN(true);
    }

    public final void zaa(ConnectionResult connectionResult, Api api, boolean z) {
        throw null;
    }

    public final int zab() {
        return this.zah;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final int zac() {
        return this.zam;
    }

    @WorkerThread
    @Nullable
    public final ConnectionResult zad() {
        Preconditions.checkHandlerThread(this.zaa.zar);
        return this.zal;
    }

    public final Api.Client zaf() {
        return this.zac;
    }

    public final Map zah() {
        return this.zag;
    }

    @WorkerThread
    public final void zan() {
        Preconditions.checkHandlerThread(this.zaa.zar);
        this.zal = null;
    }

    @WorkerThread
    public final void zao() {
        Preconditions.checkHandlerThread(this.zaa.zar);
        if (!this.zac.isConnected() && !this.zac.isConnecting()) {
            try {
                GoogleApiManager googleApiManager = this.zaa;
                int zab2 = googleApiManager.zak.zab(googleApiManager.zai, this.zac);
                if (zab2 != 0) {
                    ConnectionResult connectionResult = new ConnectionResult(zab2, (PendingIntent) null);
                    String name = this.zac.getClass().getName();
                    String obj = connectionResult.toString();
                    Log.w("GoogleApiManager", "The service for " + name + " is not available: " + obj);
                    zar(connectionResult, (Exception) null);
                    return;
                }
                GoogleApiManager googleApiManager2 = this.zaa;
                Api.Client client = this.zac;
                zabu zabu = new zabu(googleApiManager2, client, this.zad);
                if (client.requiresSignIn()) {
                    ((zact) Preconditions.checkNotNull(this.zai)).zae(zabu);
                }
                try {
                    this.zac.connect(zabu);
                } catch (SecurityException e) {
                    zar(new ConnectionResult(10), e);
                }
            } catch (IllegalStateException e2) {
                zar(new ConnectionResult(10), e2);
            }
        }
    }

    @WorkerThread
    public final void zap(zai zai2) {
        Preconditions.checkHandlerThread(this.zaa.zar);
        if (!this.zac.isConnected()) {
            this.zab.add(zai2);
            ConnectionResult connectionResult = this.zal;
            if (connectionResult == null || !connectionResult.hasResolution()) {
                zao();
            } else {
                zar(this.zal, (Exception) null);
            }
        } else if (zaL(zai2)) {
            zaI();
        } else {
            this.zab.add(zai2);
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zaq() {
        this.zam++;
    }

    @WorkerThread
    public final void zar(@NonNull ConnectionResult connectionResult, @Nullable Exception exc) {
        Preconditions.checkHandlerThread(this.zaa.zar);
        zact zact = this.zai;
        if (zact != null) {
            zact.zaf();
        }
        zan();
        this.zaa.zak.zac();
        zaC(connectionResult);
        if ((this.zac instanceof zap) && connectionResult.getErrorCode() != 24) {
            this.zaa.zaf = true;
            GoogleApiManager googleApiManager = this.zaa;
            googleApiManager.zar.sendMessageDelayed(googleApiManager.zar.obtainMessage(19), PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS);
        }
        if (connectionResult.getErrorCode() == 4) {
            zaD(GoogleApiManager.zab);
        } else if (this.zab.isEmpty()) {
            this.zal = connectionResult;
        } else if (exc != null) {
            Preconditions.checkHandlerThread(this.zaa.zar);
            zaE((Status) null, exc, false);
        } else if (this.zaa.zas) {
            zaE(GoogleApiManager.zaF(this.zad, connectionResult), (Exception) null, true);
            if (!this.zab.isEmpty() && !zaM(connectionResult) && !this.zaa.zaE(connectionResult, this.zah)) {
                if (connectionResult.getErrorCode() == 18) {
                    this.zaj = true;
                }
                if (this.zaj) {
                    GoogleApiManager googleApiManager2 = this.zaa;
                    googleApiManager2.zar.sendMessageDelayed(Message.obtain(googleApiManager2.zar, 9, this.zad), KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
                    return;
                }
                zaD(GoogleApiManager.zaF(this.zad, connectionResult));
            }
        } else {
            zaD(GoogleApiManager.zaF(this.zad, connectionResult));
        }
    }

    @WorkerThread
    public final void zas(@NonNull ConnectionResult connectionResult) {
        Preconditions.checkHandlerThread(this.zaa.zar);
        Api.Client client = this.zac;
        String name = client.getClass().getName();
        String valueOf = String.valueOf(connectionResult);
        client.disconnect("onSignInFailed for " + name + " with " + valueOf);
        zar(connectionResult, (Exception) null);
    }

    @WorkerThread
    public final void zat(zal zal2) {
        Preconditions.checkHandlerThread(this.zaa.zar);
        this.zaf.add(zal2);
    }

    @WorkerThread
    public final void zau() {
        Preconditions.checkHandlerThread(this.zaa.zar);
        if (this.zaj) {
            zao();
        }
    }

    @WorkerThread
    public final void zav() {
        Preconditions.checkHandlerThread(this.zaa.zar);
        zaD(GoogleApiManager.zaa);
        this.zae.zaf();
        for (ListenerHolder.ListenerKey zah2 : (ListenerHolder.ListenerKey[]) this.zag.keySet().toArray(new ListenerHolder.ListenerKey[0])) {
            zap(new zah(zah2, new TaskCompletionSource()));
        }
        zaC(new ConnectionResult(4));
        if (this.zac.isConnected()) {
            this.zac.onUserSignOut(new zabp(this));
        }
    }

    @WorkerThread
    public final void zaw() {
        Status status;
        Preconditions.checkHandlerThread(this.zaa.zar);
        if (this.zaj) {
            zaK();
            GoogleApiManager googleApiManager = this.zaa;
            if (googleApiManager.zaj.isGooglePlayServicesAvailable(googleApiManager.zai) == 18) {
                status = new Status(21, "Connection timed out waiting for Google Play services update to complete.");
            } else {
                status = new Status(22, "API failed to connect while resuming due to an unknown error.");
            }
            zaD(status);
            this.zac.disconnect("Timing out connection while resuming.");
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zay() {
        return this.zac.isConnected();
    }

    public final boolean zaz() {
        return this.zac.requiresSignIn();
    }
}

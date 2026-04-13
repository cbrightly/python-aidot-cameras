package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zab;
import com.google.android.gms.common.internal.zav;
import com.google.android.gms.signin.internal.zak;
import com.google.android.gms.signin.zae;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaaw implements zabf {
    /* access modifiers changed from: private */
    public final zabi zaa;
    /* access modifiers changed from: private */
    public final Lock zab;
    /* access modifiers changed from: private */
    public final Context zac;
    /* access modifiers changed from: private */
    public final GoogleApiAvailabilityLight zad;
    @Nullable
    private ConnectionResult zae;
    private int zaf;
    private int zag = 0;
    private int zah;
    private final Bundle zai = new Bundle();
    private final Set zaj = new HashSet();
    /* access modifiers changed from: private */
    @Nullable
    public zae zak;
    private boolean zal;
    /* access modifiers changed from: private */
    public boolean zam;
    private boolean zan;
    /* access modifiers changed from: private */
    @Nullable
    public IAccountAccessor zao;
    private boolean zap;
    private boolean zaq;
    /* access modifiers changed from: private */
    @Nullable
    public final ClientSettings zar;
    private final Map zas;
    @Nullable
    private final Api.AbstractClientBuilder zat;
    private final ArrayList zau = new ArrayList();

    public zaaw(zabi zabi, @Nullable ClientSettings clientSettings, Map map, GoogleApiAvailabilityLight googleApiAvailabilityLight, @Nullable Api.AbstractClientBuilder abstractClientBuilder, Lock lock, Context context) {
        this.zaa = zabi;
        this.zar = clientSettings;
        this.zas = map;
        this.zad = googleApiAvailabilityLight;
        this.zat = abstractClientBuilder;
        this.zab = lock;
        this.zac = context;
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaA() {
        this.zam = false;
        this.zaa.zag.zad = Collections.emptySet();
        for (Api.AnyClientKey anyClientKey : this.zaj) {
            if (!this.zaa.zab.containsKey(anyClientKey)) {
                this.zaa.zab.put(anyClientKey, new ConnectionResult(17, (PendingIntent) null));
            }
        }
    }

    @GuardedBy("mLock")
    private final void zaB(boolean z) {
        zae zae2 = this.zak;
        if (zae2 != null) {
            if (zae2.isConnected() && z) {
                zae2.zaa();
            }
            zae2.disconnect();
            ClientSettings clientSettings = (ClientSettings) Preconditions.checkNotNull(this.zar);
            this.zao = null;
        }
    }

    @GuardedBy("mLock")
    private final void zaC() {
        Bundle bundle;
        this.zaa.zai();
        zabj.zaa().execute(new zaak(this));
        zae zae2 = this.zak;
        if (zae2 != null) {
            if (this.zap) {
                zae2.zac((IAccountAccessor) Preconditions.checkNotNull(this.zao), this.zaq);
            }
            zaB(false);
        }
        for (Api.AnyClientKey anyClientKey : this.zaa.zab.keySet()) {
            ((Api.Client) Preconditions.checkNotNull((Api.Client) this.zaa.zaa.get(anyClientKey))).disconnect();
        }
        if (this.zai.isEmpty()) {
            bundle = null;
        } else {
            bundle = this.zai;
        }
        this.zaa.zah.zab(bundle);
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaD(ConnectionResult connectionResult) {
        zaz();
        zaB(!connectionResult.hasResolution());
        this.zaa.zak(connectionResult);
        this.zaa.zah.zaa(connectionResult);
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaE(ConnectionResult connectionResult, Api api, boolean z) {
        int priority = api.zac().getPriority();
        if ((!z || connectionResult.hasResolution() || this.zad.getErrorResolutionIntent(connectionResult.getErrorCode()) != null) && (this.zae == null || priority < this.zaf)) {
            this.zae = connectionResult;
            this.zaf = priority;
        }
        this.zaa.zab.put(api.zab(), connectionResult);
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaF() {
        if (this.zah == 0) {
            if (!this.zam || this.zan) {
                ArrayList arrayList = new ArrayList();
                this.zag = 1;
                this.zah = this.zaa.zaa.size();
                for (Api.AnyClientKey anyClientKey : this.zaa.zaa.keySet()) {
                    if (!this.zaa.zab.containsKey(anyClientKey)) {
                        arrayList.add((Api.Client) this.zaa.zaa.get(anyClientKey));
                    } else if (zaH()) {
                        zaC();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zau.add(zabj.zaa().submit(new zaap(this, arrayList)));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zaG(int i) {
        if (this.zag == i) {
            return true;
        }
        Log.w("GACConnecting", this.zaa.zag.zaf());
        Log.w("GACConnecting", "Unexpected callback in ".concat(toString()));
        int i2 = this.zah;
        Log.w("GACConnecting", "mRemainingConnections=" + i2);
        String zaJ = zaJ(this.zag);
        Log.e("GACConnecting", "GoogleApiClient connecting is in step " + zaJ + " but received callback for step " + zaJ(i), new Exception());
        zaD(new ConnectionResult(8, (PendingIntent) null));
        return false;
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zaH() {
        int i = this.zah - 1;
        this.zah = i;
        if (i > 0) {
            return false;
        }
        if (i < 0) {
            Log.w("GACConnecting", this.zaa.zag.zaf());
            Log.wtf("GACConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zaD(new ConnectionResult(8, (PendingIntent) null));
            return false;
        }
        ConnectionResult connectionResult = this.zae;
        if (connectionResult == null) {
            return true;
        }
        this.zaa.zaf = this.zaf;
        zaD(connectionResult);
        return false;
    }

    /* access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zaI(ConnectionResult connectionResult) {
        return this.zal && !connectionResult.hasResolution();
    }

    private static final String zaJ(int i) {
        switch (i) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            default:
                return "STEP_GETTING_REMOTE_SERVICE";
        }
    }

    static /* bridge */ /* synthetic */ Set zao(zaaw zaaw) {
        ClientSettings clientSettings = zaaw.zar;
        if (clientSettings == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map zad2 = zaaw.zar.zad();
        for (Api api : zad2.keySet()) {
            if (!zaaw.zaa.zab.containsKey(api.zab())) {
                hashSet.addAll(((zab) zad2.get(api)).zaa);
            }
        }
        return hashSet;
    }

    static /* bridge */ /* synthetic */ void zar(zaaw zaaw, zak zak2) {
        if (zaaw.zaG(0)) {
            ConnectionResult zaa2 = zak2.zaa();
            if (zaa2.isSuccess()) {
                zav zav = (zav) Preconditions.checkNotNull(zak2.zab());
                ConnectionResult zaa3 = zav.zaa();
                if (!zaa3.isSuccess()) {
                    String valueOf = String.valueOf(String.valueOf(zaa3));
                    Log.wtf("GACConnecting", "Sign-in succeeded with resolve account failure: ".concat(valueOf), new Exception());
                    zaaw.zaD(zaa3);
                    return;
                }
                zaaw.zan = true;
                zaaw.zao = (IAccountAccessor) Preconditions.checkNotNull(zav.zab());
                zaaw.zap = zav.zac();
                zaaw.zaq = zav.zad();
                zaaw.zaF();
            } else if (zaaw.zaI(zaa2)) {
                zaaw.zaA();
                zaaw.zaF();
            } else {
                zaaw.zaD(zaa2);
            }
        }
    }

    private final void zaz() {
        ArrayList arrayList = this.zau;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Future) arrayList.get(i)).cancel(true);
        }
        this.zau.clear();
    }

    public final BaseImplementation.ApiMethodImpl zaa(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        this.zaa.zag.zaa.add(apiMethodImpl);
        return apiMethodImpl;
    }

    public final BaseImplementation.ApiMethodImpl zab(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    /* JADX WARNING: type inference failed for: r0v13, types: [com.google.android.gms.common.api.Api$Client, com.google.android.gms.signin.zae] */
    @GuardedBy("mLock")
    public final void zad() {
        boolean z;
        this.zaa.zab.clear();
        this.zam = false;
        this.zae = null;
        this.zag = 0;
        this.zal = true;
        this.zan = false;
        this.zap = false;
        HashMap hashMap = new HashMap();
        boolean z2 = false;
        for (Api api : this.zas.keySet()) {
            Api.Client client = (Api.Client) Preconditions.checkNotNull((Api.Client) this.zaa.zaa.get(api.zab()));
            if (api.zac().getPriority() == 1) {
                z = true;
            } else {
                z = false;
            }
            z2 |= z;
            boolean booleanValue = ((Boolean) this.zas.get(api)).booleanValue();
            if (client.requiresSignIn()) {
                this.zam = true;
                if (booleanValue) {
                    this.zaj.add(api.zab());
                } else {
                    this.zal = false;
                }
            }
            hashMap.put(client, new zaal(this, api, booleanValue));
        }
        if (z2) {
            this.zam = false;
        }
        if (this.zam) {
            Preconditions.checkNotNull(this.zar);
            Preconditions.checkNotNull(this.zat);
            this.zar.zae(Integer.valueOf(System.identityHashCode(this.zaa.zag)));
            zaat zaat = new zaat(this, (zaas) null);
            Api.AbstractClientBuilder abstractClientBuilder = this.zat;
            Context context = this.zac;
            Looper looper = this.zaa.zag.getLooper();
            ClientSettings clientSettings = this.zar;
            this.zak = abstractClientBuilder.buildClient(context, looper, clientSettings, clientSettings.zaa(), (GoogleApiClient.ConnectionCallbacks) zaat, (GoogleApiClient.OnConnectionFailedListener) zaat);
        }
        this.zah = this.zaa.zaa.size();
        this.zau.add(zabj.zaa().submit(new zaao(this, hashMap)));
    }

    public final void zae() {
    }

    @GuardedBy("mLock")
    public final void zag(@Nullable Bundle bundle) {
        if (zaG(1)) {
            if (bundle != null) {
                this.zai.putAll(bundle);
            }
            if (zaH()) {
                zaC();
            }
        }
    }

    @GuardedBy("mLock")
    public final void zah(ConnectionResult connectionResult, Api api, boolean z) {
        if (zaG(1)) {
            zaE(connectionResult, api, z);
            if (zaH()) {
                zaC();
            }
        }
    }

    @GuardedBy("mLock")
    public final void zai(int i) {
        zaD(new ConnectionResult(8, (PendingIntent) null));
    }

    @GuardedBy("mLock")
    public final boolean zaj() {
        zaz();
        zaB(true);
        this.zaa.zak((ConnectionResult) null);
        return true;
    }
}

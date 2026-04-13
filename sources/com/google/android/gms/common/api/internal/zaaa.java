package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zap;
import com.google.android.gms.internal.base.zau;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaaa implements zaca {
    private final Context zaa;
    private final zabe zab;
    private final Looper zac;
    /* access modifiers changed from: private */
    public final zabi zad;
    /* access modifiers changed from: private */
    public final zabi zae;
    private final Map zaf;
    private final Set zag = Collections.newSetFromMap(new WeakHashMap());
    @Nullable
    private final Api.Client zah;
    @Nullable
    private Bundle zai;
    /* access modifiers changed from: private */
    @Nullable
    public ConnectionResult zaj = null;
    /* access modifiers changed from: private */
    @Nullable
    public ConnectionResult zak = null;
    /* access modifiers changed from: private */
    public boolean zal = false;
    /* access modifiers changed from: private */
    public final Lock zam;
    @GuardedBy("mLock")
    private int zan = 0;

    private zaaa(Context context, zabe zabe, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map map, Map map2, ClientSettings clientSettings, Api.AbstractClientBuilder abstractClientBuilder, @Nullable Api.Client client, ArrayList arrayList, ArrayList arrayList2, Map map3, Map map4) {
        this.zaa = context;
        this.zab = zabe;
        this.zam = lock;
        this.zac = looper;
        this.zah = client;
        Context context2 = context;
        zabe zabe2 = zabe;
        Lock lock2 = lock;
        Looper looper2 = looper;
        GoogleApiAvailabilityLight googleApiAvailabilityLight2 = googleApiAvailabilityLight;
        zabi zabi = r3;
        zabi zabi2 = new zabi(context2, zabe2, lock2, looper2, googleApiAvailabilityLight2, map2, (ClientSettings) null, map4, (Api.AbstractClientBuilder) null, arrayList2, new zax(this, (zaw) null));
        this.zad = zabi;
        this.zae = new zabi(context2, zabe2, lock2, looper2, googleApiAvailabilityLight2, map, clientSettings, map3, abstractClientBuilder, arrayList, new zaz(this, (zay) null));
        ArrayMap arrayMap = new ArrayMap();
        for (Api.AnyClientKey put : map2.keySet()) {
            arrayMap.put(put, this.zad);
        }
        for (Api.AnyClientKey put2 : map.keySet()) {
            arrayMap.put(put2, this.zae);
        }
        this.zaf = Collections.unmodifiableMap(arrayMap);
    }

    @GuardedBy("mLock")
    private final void zaB() {
        for (SignInConnectionListener onComplete : this.zag) {
            onComplete.onComplete();
        }
        this.zag.clear();
    }

    @GuardedBy("mLock")
    private final boolean zaC() {
        ConnectionResult connectionResult = this.zak;
        return connectionResult != null && connectionResult.getErrorCode() == 4;
    }

    private final boolean zaD(BaseImplementation.ApiMethodImpl apiMethodImpl) {
        zabi zabi = (zabi) this.zaf.get(apiMethodImpl.getClientKey());
        Preconditions.checkNotNull(zabi, "GoogleApiClient is not configured to use the API required for this call.");
        return zabi.equals(this.zae);
    }

    private static boolean zaE(@Nullable ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    public static zaaa zag(Context context, zabe zabe, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map map, ClientSettings clientSettings, Map map2, Api.AbstractClientBuilder abstractClientBuilder, ArrayList arrayList) {
        Map map3 = map2;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Api.Client client = null;
        for (Map.Entry entry : map.entrySet()) {
            Api.Client client2 = (Api.Client) entry.getValue();
            if (true == client2.providesSignIn()) {
                client = client2;
            }
            if (client2.requiresSignIn()) {
                arrayMap.put((Api.AnyClientKey) entry.getKey(), client2);
            } else {
                arrayMap2.put((Api.AnyClientKey) entry.getKey(), client2);
            }
        }
        Preconditions.checkState(!arrayMap.isEmpty(), "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            Api.AnyClientKey zab2 = api.zab();
            if (arrayMap.containsKey(zab2)) {
                arrayMap3.put(api, (Boolean) map3.get(api));
            } else if (arrayMap2.containsKey(zab2)) {
                arrayMap4.put(api, (Boolean) map3.get(api));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            zat zat = (zat) arrayList.get(i);
            if (arrayMap3.containsKey(zat.zaa)) {
                arrayList2.add(zat);
            } else if (arrayMap4.containsKey(zat.zaa)) {
                arrayList3.add(zat);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zaaa(context, zabe, lock, looper, googleApiAvailabilityLight, arrayMap, arrayMap2, clientSettings, abstractClientBuilder, client, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    static /* bridge */ /* synthetic */ void zan(zaaa zaaa, int i, boolean z) {
        zaaa.zab.zac(i, z);
        zaaa.zak = null;
        zaaa.zaj = null;
    }

    static /* bridge */ /* synthetic */ void zao(zaaa zaaa, Bundle bundle) {
        Bundle bundle2 = zaaa.zai;
        if (bundle2 == null) {
            zaaa.zai = bundle;
        } else if (bundle != null) {
            bundle2.putAll(bundle);
        }
    }

    static /* bridge */ /* synthetic */ void zap(zaaa zaaa) {
        ConnectionResult connectionResult;
        if (zaE(zaaa.zaj)) {
            if (zaE(zaaa.zak) || zaaa.zaC()) {
                switch (zaaa.zan) {
                    case 1:
                        break;
                    case 2:
                        ((zabe) Preconditions.checkNotNull(zaaa.zab)).zab(zaaa.zai);
                        break;
                    default:
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        break;
                }
                zaaa.zaB();
                zaaa.zan = 0;
                return;
            }
            ConnectionResult connectionResult2 = zaaa.zak;
            if (connectionResult2 == null) {
                return;
            }
            if (zaaa.zan == 1) {
                zaaa.zaB();
                return;
            }
            zaaa.zaA(connectionResult2);
            zaaa.zad.zar();
        } else if (zaaa.zaj == null || !zaE(zaaa.zak)) {
            ConnectionResult connectionResult3 = zaaa.zaj;
            if (connectionResult3 != null && (connectionResult = zaaa.zak) != null) {
                if (zaaa.zae.zaf < zaaa.zad.zaf) {
                    connectionResult3 = connectionResult;
                }
                zaaa.zaA(connectionResult3);
            }
        } else {
            zaaa.zae.zar();
            zaaa.zaA((ConnectionResult) Preconditions.checkNotNull(zaaa.zaj));
        }
    }

    @Nullable
    private final PendingIntent zaz() {
        if (this.zah == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zaa, System.identityHashCode(this.zab), this.zah.getSignInIntent(), zap.zaa | 134217728);
    }

    @GuardedBy("mLock")
    public final ConnectionResult zab() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    public final ConnectionResult zac(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult zad(@NonNull Api api) {
        if (!Objects.equal(this.zaf.get(api.zab()), this.zae)) {
            return this.zad.zad(api);
        }
        if (zaC()) {
            return new ConnectionResult(4, zaz());
        }
        return this.zae.zad(api);
    }

    @GuardedBy("mLock")
    public final BaseImplementation.ApiMethodImpl zae(@NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        if (!zaD(apiMethodImpl)) {
            this.zad.zae(apiMethodImpl);
            return apiMethodImpl;
        } else if (zaC()) {
            apiMethodImpl.setFailedResult(new Status(4, (String) null, zaz()));
            return apiMethodImpl;
        } else {
            this.zae.zae(apiMethodImpl);
            return apiMethodImpl;
        }
    }

    @GuardedBy("mLock")
    public final BaseImplementation.ApiMethodImpl zaf(@NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        if (!zaD(apiMethodImpl)) {
            return this.zad.zaf(apiMethodImpl);
        }
        if (!zaC()) {
            return this.zae.zaf(apiMethodImpl);
        }
        apiMethodImpl.setFailedResult(new Status(4, (String) null, zaz()));
        return apiMethodImpl;
    }

    @GuardedBy("mLock")
    public final void zaq() {
        this.zan = 2;
        this.zal = false;
        this.zak = null;
        this.zaj = null;
        this.zad.zaq();
        this.zae.zaq();
    }

    @GuardedBy("mLock")
    public final void zar() {
        this.zak = null;
        this.zaj = null;
        this.zan = 0;
        this.zad.zar();
        this.zae.zar();
        zaB();
    }

    public final void zas(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zae.zas(String.valueOf(str).concat(JustifyTextView.TWO_CHINESE_BLANK), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zad.zas(String.valueOf(str).concat(JustifyTextView.TWO_CHINESE_BLANK), fileDescriptor, printWriter, strArr);
    }

    @GuardedBy("mLock")
    public final void zat() {
        this.zad.zat();
        this.zae.zat();
    }

    public final void zau() {
        this.zam.lock();
        try {
            boolean zax = zax();
            this.zae.zar();
            this.zak = new ConnectionResult(4);
            if (zax) {
                new zau(this.zac).post(new zav(this));
            } else {
                zaB();
            }
        } finally {
            this.zam.unlock();
        }
    }

    public final boolean zaw() {
        this.zam.lock();
        try {
            boolean z = false;
            if (this.zad.zaw()) {
                if (this.zae.zaw() || zaC()) {
                    z = true;
                } else if (this.zan == 1) {
                    z = true;
                }
            }
            return z;
        } finally {
            this.zam.unlock();
        }
    }

    public final boolean zax() {
        boolean z;
        this.zam.lock();
        try {
            if (this.zan == 2) {
                z = true;
            } else {
                z = false;
            }
            return z;
        } finally {
            this.zam.unlock();
        }
    }

    public final boolean zay(SignInConnectionListener signInConnectionListener) {
        this.zam.lock();
        try {
            if ((zax() || zaw()) && !this.zae.zaw()) {
                this.zag.add(signInConnectionListener);
                if (this.zan == 0) {
                    this.zan = 1;
                }
                this.zak = null;
                this.zae.zaq();
                return true;
            }
            this.zam.unlock();
            return false;
        } finally {
            this.zam.unlock();
        }
    }

    @GuardedBy("mLock")
    private final void zaA(ConnectionResult connectionResult) {
        switch (this.zan) {
            case 1:
                break;
            case 2:
                this.zab.zaa(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zaB();
        this.zan = 0;
    }
}

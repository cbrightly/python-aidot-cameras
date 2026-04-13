package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zabi implements zaca, zau {
    final Map zaa;
    final Map zab = new HashMap();
    @Nullable
    final ClientSettings zac;
    final Map zad;
    @Nullable
    final Api.AbstractClientBuilder zae;
    int zaf;
    final zabe zag;
    final zabz zah;
    /* access modifiers changed from: private */
    public final Lock zai;
    private final Condition zaj;
    private final Context zak;
    private final GoogleApiAvailabilityLight zal;
    private final zabh zam;
    /* access modifiers changed from: private */
    @NotOnlyInitialized
    public volatile zabf zan;
    @Nullable
    private ConnectionResult zao = null;

    public zabi(Context context, zabe zabe, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map map, @Nullable ClientSettings clientSettings, Map map2, @Nullable Api.AbstractClientBuilder abstractClientBuilder, ArrayList arrayList, zabz zabz) {
        this.zak = context;
        this.zai = lock;
        this.zal = googleApiAvailabilityLight;
        this.zaa = map;
        this.zac = clientSettings;
        this.zad = map2;
        this.zae = abstractClientBuilder;
        this.zag = zabe;
        this.zah = zabz;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((zat) arrayList.get(i)).zaa(this);
        }
        this.zam = new zabh(this, looper);
        this.zaj = lock.newCondition();
        this.zan = new zaax(this);
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zai.lock();
        try {
            this.zan.zag(bundle);
        } finally {
            this.zai.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zai.lock();
        try {
            this.zan.zai(i);
        } finally {
            this.zai.unlock();
        }
    }

    public final void zaa(@NonNull ConnectionResult connectionResult, @NonNull Api api, boolean z) {
        this.zai.lock();
        try {
            this.zan.zah(connectionResult, api, z);
        } finally {
            this.zai.unlock();
        }
    }

    @GuardedBy("mLock")
    public final ConnectionResult zab() {
        zaq();
        while (this.zan instanceof zaaw) {
            try {
                this.zaj.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, (PendingIntent) null);
            }
        }
        if (this.zan instanceof zaaj) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zao;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, (PendingIntent) null);
    }

    @GuardedBy("mLock")
    public final ConnectionResult zac(long j, TimeUnit timeUnit) {
        zaq();
        long nanos = timeUnit.toNanos(j);
        while (this.zan instanceof zaaw) {
            if (nanos <= 0) {
                try {
                    zar();
                    return new ConnectionResult(14, (PendingIntent) null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, (PendingIntent) null);
                }
            } else {
                nanos = this.zaj.awaitNanos(nanos);
            }
        }
        if (this.zan instanceof zaaj) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zao;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, (PendingIntent) null);
    }

    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult zad(@NonNull Api api) {
        Api.AnyClientKey zab2 = api.zab();
        if (!this.zaa.containsKey(zab2)) {
            return null;
        }
        if (((Api.Client) this.zaa.get(zab2)).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        if (this.zab.containsKey(zab2)) {
            return (ConnectionResult) this.zab.get(zab2);
        }
        return null;
    }

    @GuardedBy("mLock")
    public final BaseImplementation.ApiMethodImpl zae(@NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        apiMethodImpl.zak();
        this.zan.zaa(apiMethodImpl);
        return apiMethodImpl;
    }

    @GuardedBy("mLock")
    public final BaseImplementation.ApiMethodImpl zaf(@NonNull BaseImplementation.ApiMethodImpl apiMethodImpl) {
        apiMethodImpl.zak();
        return this.zan.zab(apiMethodImpl);
    }

    /* access modifiers changed from: package-private */
    public final void zai() {
        this.zai.lock();
        try {
            this.zag.zak();
            this.zan = new zaaj(this);
            this.zan.zad();
            this.zaj.signalAll();
        } finally {
            this.zai.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zaj() {
        this.zai.lock();
        try {
            this.zan = new zaaw(this, this.zac, this.zad, this.zal, this.zae, this.zai, this.zak);
            this.zan.zad();
            this.zaj.signalAll();
        } finally {
            this.zai.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zak(@Nullable ConnectionResult connectionResult) {
        this.zai.lock();
        try {
            this.zao = connectionResult;
            this.zan = new zaax(this);
            this.zan.zad();
            this.zaj.signalAll();
        } finally {
            this.zai.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zal(zabg zabg) {
        this.zam.sendMessage(this.zam.obtainMessage(1, zabg));
    }

    /* access modifiers changed from: package-private */
    public final void zam(RuntimeException runtimeException) {
        this.zam.sendMessage(this.zam.obtainMessage(2, runtimeException));
    }

    @GuardedBy("mLock")
    public final void zaq() {
        this.zan.zae();
    }

    @GuardedBy("mLock")
    public final void zar() {
        if (this.zan.zaj()) {
            this.zab.clear();
        }
    }

    public final void zas(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.append(str).append("mState=").println(this.zan);
        for (Api api : this.zad.keySet()) {
            String valueOf = String.valueOf(str);
            printWriter.append(str).append(api.zad()).println(":");
            ((Api.Client) Preconditions.checkNotNull((Api.Client) this.zaa.get(api.zab()))).dump(valueOf.concat(JustifyTextView.TWO_CHINESE_BLANK), fileDescriptor, printWriter, strArr);
        }
    }

    @GuardedBy("mLock")
    public final void zat() {
        if (this.zan instanceof zaaj) {
            ((zaaj) this.zan).zaf();
        }
    }

    public final void zau() {
    }

    public final boolean zaw() {
        return this.zan instanceof zaaj;
    }

    public final boolean zax() {
        return this.zan instanceof zaaw;
    }

    public final boolean zay(SignInConnectionListener signInConnectionListener) {
        return false;
    }
}

package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executor;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class GmsClient<T extends IInterface> extends BaseGmsClient<T> implements Api.Client, zaj {
    @Nullable
    private static volatile Executor zaa;
    private final ClientSettings zab;
    private final Set zac;
    @Nullable
    private final Account zad;

    @KeepForSdk
    @VisibleForTesting
    protected GmsClient(@NonNull Context context, @NonNull Handler handler, int gCoreServiceId, @NonNull ClientSettings clientSettings) {
        super(context, handler, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), gCoreServiceId, (BaseGmsClient.BaseConnectionCallbacks) null, (BaseGmsClient.BaseOnConnectionFailedListener) null);
        this.zab = (ClientSettings) Preconditions.checkNotNull(clientSettings);
        this.zad = clientSettings.getAccount();
        this.zac = zaa(clientSettings.getAllRequestedScopes());
    }

    private final Set zaa(@NonNull Set set) {
        Set<Scope> validateScopes = validateScopes(set);
        for (Scope contains : validateScopes) {
            if (!set.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return validateScopes;
    }

    @Nullable
    public final Account getAccount() {
        return this.zad;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final Executor getBindServiceExecutor() {
        return null;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public final ClientSettings getClientSettings() {
        return this.zab;
    }

    @NonNull
    @KeepForSdk
    public Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public final Set<Scope> getScopes() {
        return this.zac;
    }

    @NonNull
    @KeepForSdk
    public Set<Scope> getScopesForConnectionlessNonSignIn() {
        return requiresSignIn() ? this.zac : Collections.emptySet();
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public Set<Scope> validateScopes(@NonNull Set<Scope> set) {
        return set;
    }

    @KeepForSdk
    protected GmsClient(@NonNull Context context, @NonNull Looper looper, int gCoreServiceId, @NonNull ClientSettings clientSettings) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), gCoreServiceId, clientSettings, (ConnectionCallbacks) null, (OnConnectionFailedListener) null);
    }

    @KeepForSdk
    @Deprecated
    protected GmsClient(@NonNull Context context, @NonNull Looper looper, int gCoreServiceId, @NonNull ClientSettings clientSettings, @NonNull GoogleApiClient.ConnectionCallbacks connectedListener, @NonNull GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        this(context, looper, gCoreServiceId, clientSettings, (ConnectionCallbacks) connectedListener, (OnConnectionFailedListener) connectionFailedListener);
    }

    @KeepForSdk
    protected GmsClient(@NonNull Context context, @NonNull Looper looper, int gCoreServiceId, @NonNull ClientSettings clientSettings, @NonNull ConnectionCallbacks connectedListener, @NonNull OnConnectionFailedListener connectionFailedListener) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), gCoreServiceId, clientSettings, (ConnectionCallbacks) Preconditions.checkNotNull(connectedListener), (OnConnectionFailedListener) Preconditions.checkNotNull(connectionFailedListener));
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @com.google.android.gms.common.util.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected GmsClient(@androidx.annotation.NonNull android.content.Context r11, @androidx.annotation.NonNull android.os.Looper r12, @androidx.annotation.NonNull com.google.android.gms.common.internal.GmsClientSupervisor r13, @androidx.annotation.NonNull com.google.android.gms.common.GoogleApiAvailability r14, int r15, @androidx.annotation.NonNull com.google.android.gms.common.internal.ClientSettings r16, @androidx.annotation.Nullable com.google.android.gms.common.api.internal.ConnectionCallbacks r17, @androidx.annotation.Nullable com.google.android.gms.common.api.internal.OnConnectionFailedListener r18) {
        /*
            r10 = this;
            r9 = r10
            r0 = r17
            r1 = r18
            r2 = 0
            if (r0 != 0) goto L_0x000a
            r6 = r2
            goto L_0x0010
        L_0x000a:
            com.google.android.gms.common.internal.zah r3 = new com.google.android.gms.common.internal.zah
            r3.<init>(r0)
            r6 = r3
        L_0x0010:
            if (r1 != 0) goto L_0x0014
            r7 = r2
            goto L_0x001a
        L_0x0014:
            com.google.android.gms.common.internal.zai r0 = new com.google.android.gms.common.internal.zai
            r0.<init>(r1)
            r7 = r0
        L_0x001a:
            java.lang.String r8 = r16.zac()
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r13
            r4 = r14
            r5 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r16
            r9.zab = r0
            android.accounts.Account r1 = r16.getAccount()
            r9.zad = r1
            java.util.Set r0 = r16.getAllRequestedScopes()
            java.util.Set r0 = r10.zaa(r0)
            r9.zac = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.GmsClient.<init>(android.content.Context, android.os.Looper, com.google.android.gms.common.internal.GmsClientSupervisor, com.google.android.gms.common.GoogleApiAvailability, int, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.common.api.internal.ConnectionCallbacks, com.google.android.gms.common.api.internal.OnConnectionFailedListener):void");
    }
}

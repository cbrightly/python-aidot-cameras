package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class Api<O extends ApiOptions> {
    private final AbstractClientBuilder zaa;
    private final ClientKey zab;
    private final String zac;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static abstract class AbstractClientBuilder<T extends Client, O> extends BaseClientBuilder<T, O> {
        @NonNull
        @KeepForSdk
        @Deprecated
        public T buildClient(@NonNull Context context, @NonNull Looper looper, @NonNull ClientSettings commonSettings, @NonNull O apiOptions, @NonNull GoogleApiClient.ConnectionCallbacks connectedListener, @NonNull GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
            return buildClient(context, looper, commonSettings, apiOptions, (ConnectionCallbacks) connectedListener, (OnConnectionFailedListener) connectionFailedListener);
        }

        @NonNull
        @KeepForSdk
        public T buildClient(@NonNull Context context, @NonNull Looper looper, @NonNull ClientSettings clientSettings, @NonNull O o, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            throw new UnsupportedOperationException("buildClient must be implemented");
        }
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface AnyClient {
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static class AnyClientKey<C extends AnyClient> {
    }

    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface ApiOptions {
        @NonNull
        public static final NoOptions NO_OPTIONS = new NoOptions((zaa) null);

        /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
        public interface HasAccountOptions extends HasOptions, NotRequiredOptions {
            @NonNull
            Account getAccount();
        }

        /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
        public interface HasGoogleSignInAccountOptions extends HasOptions {
            @Nullable
            GoogleSignInAccount getGoogleSignInAccount();
        }

        /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
        public interface HasOptions extends ApiOptions {
        }

        /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() {
            }

            /* synthetic */ NoOptions(zaa zaa) {
            }
        }

        /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
        public interface NotRequiredOptions extends ApiOptions {
        }

        /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
        public interface Optional extends HasOptions, NotRequiredOptions {
        }
    }

    @KeepForSdk
    @VisibleForTesting
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static abstract class BaseClientBuilder<T extends AnyClient, O> {
        @KeepForSdk
        public static final int API_PRIORITY_GAMES = 1;
        @KeepForSdk
        public static final int API_PRIORITY_OTHER = Integer.MAX_VALUE;
        @KeepForSdk
        public static final int API_PRIORITY_PLUS = 2;

        @NonNull
        @KeepForSdk
        public List<Scope> getImpliedScopes(@Nullable O o) {
            return Collections.emptyList();
        }

        @KeepForSdk
        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface Client extends AnyClient {
        @KeepForSdk
        void connect(@NonNull BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks);

        @KeepForSdk
        void disconnect();

        @KeepForSdk
        void disconnect(@NonNull String str);

        @KeepForSdk
        void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr);

        @NonNull
        @KeepForSdk
        Feature[] getAvailableFeatures();

        @NonNull
        @KeepForSdk
        String getEndpointPackageName();

        @KeepForSdk
        @Nullable
        String getLastDisconnectMessage();

        @KeepForSdk
        int getMinApkVersion();

        @KeepForSdk
        void getRemoteService(@Nullable IAccountAccessor iAccountAccessor, @Nullable Set<Scope> set);

        @NonNull
        @KeepForSdk
        Feature[] getRequiredFeatures();

        @NonNull
        @KeepForSdk
        Set<Scope> getScopesForConnectionlessNonSignIn();

        @KeepForSdk
        @Nullable
        IBinder getServiceBrokerBinder();

        @NonNull
        @KeepForSdk
        Intent getSignInIntent();

        @KeepForSdk
        boolean isConnected();

        @KeepForSdk
        boolean isConnecting();

        @KeepForSdk
        void onUserSignOut(@NonNull BaseGmsClient.SignOutCallbacks signOutCallbacks);

        @KeepForSdk
        boolean providesSignIn();

        @KeepForSdk
        boolean requiresAccount();

        @KeepForSdk
        boolean requiresGooglePlayServices();

        @KeepForSdk
        boolean requiresSignIn();
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static final class ClientKey<C extends Client> extends AnyClientKey<C> {
    }

    @KeepForSdk
    public <C extends Client> Api(@NonNull String name, @NonNull AbstractClientBuilder<C, O> clientBuilder, @NonNull ClientKey<C> clientKey) {
        Preconditions.checkNotNull(clientBuilder, "Cannot construct an Api with a null ClientBuilder");
        Preconditions.checkNotNull(clientKey, "Cannot construct an Api with a null ClientKey");
        this.zac = name;
        this.zaa = clientBuilder;
        this.zab = clientKey;
    }

    @NonNull
    public final AbstractClientBuilder zaa() {
        return this.zaa;
    }

    @NonNull
    public final AnyClientKey zab() {
        return this.zab;
    }

    @NonNull
    public final BaseClientBuilder zac() {
        return this.zaa;
    }

    @NonNull
    public final String zad() {
        return this.zac;
    }
}

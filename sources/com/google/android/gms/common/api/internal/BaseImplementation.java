package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class BaseImplementation {

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface ResultHolder<R> {
        @KeepForSdk
        void setFailedResult(@NonNull Status status);

        @KeepForSdk
        void setResult(@NonNull R r);
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static abstract class ApiMethodImpl<R extends Result, A extends Api.AnyClient> extends BasePendingResult<R> implements ResultHolder<R> {
        @KeepForSdk
        @Nullable
        private final Api<?> mApi;
        @KeepForSdk
        private final Api.AnyClientKey<A> mClientKey;

        @KeepForSdk
        @Deprecated
        protected ApiMethodImpl(@NonNull Api.AnyClientKey<A> clientKey, @NonNull GoogleApiClient apiClient) {
            super((GoogleApiClient) Preconditions.checkNotNull(apiClient, "GoogleApiClient must not be null"));
            this.mClientKey = (Api.AnyClientKey) Preconditions.checkNotNull(clientKey);
            this.mApi = null;
        }

        @KeepForSdk
        private void setFailedResult(@NonNull RemoteException e) {
            setFailedResult(new Status(8, e.getLocalizedMessage(), (PendingIntent) null));
        }

        /* access modifiers changed from: protected */
        @KeepForSdk
        public abstract void doExecute(@NonNull A a);

        @KeepForSdk
        @Nullable
        public final Api<?> getApi() {
            return this.mApi;
        }

        @NonNull
        @KeepForSdk
        public final Api.AnyClientKey<A> getClientKey() {
            return this.mClientKey;
        }

        /* access modifiers changed from: protected */
        @KeepForSdk
        public void onSetFailedResult(@NonNull R r) {
        }

        @KeepForSdk
        public final void run(@NonNull A client) {
            try {
                doExecute(client);
            } catch (DeadObjectException e) {
                setFailedResult((RemoteException) e);
                throw e;
            } catch (RemoteException e2) {
                setFailedResult(e2);
            }
        }

        @KeepForSdk
        public /* bridge */ /* synthetic */ void setResult(@NonNull Object obj) {
            super.setResult((Result) obj);
        }

        @KeepForSdk
        protected ApiMethodImpl(@NonNull Api<?> api, @NonNull GoogleApiClient apiClient) {
            super((GoogleApiClient) Preconditions.checkNotNull(apiClient, "GoogleApiClient must not be null"));
            Preconditions.checkNotNull(api, "Api must not be null");
            this.mClientKey = api.zab();
            this.mApi = api;
        }

        @KeepForSdk
        public final void setFailedResult(@NonNull Status status) {
            Preconditions.checkArgument(!status.isSuccess(), "Failed result must not be success");
            Result createFailedResult = createFailedResult(status);
            setResult(createFailedResult);
            onSetFailedResult(createFailedResult);
        }

        @VisibleForTesting
        @KeepForSdk
        protected ApiMethodImpl(@NonNull BasePendingResult.CallbackHandler<R> handler) {
            super(handler);
            this.mClientKey = new Api.AnyClientKey<>();
            this.mApi = null;
        }
    }
}

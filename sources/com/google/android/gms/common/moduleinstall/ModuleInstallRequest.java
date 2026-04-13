package com.google.android.gms.common.moduleinstall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class ModuleInstallRequest {
    private final List zaa;
    @Nullable
    private final InstallStatusListener zab;
    @Nullable
    private final Executor zac;

    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static class Builder {
        private final List zaa = new ArrayList();
        @Nullable
        private InstallStatusListener zab;
        @Nullable
        private Executor zac;

        @NonNull
        @CanIgnoreReturnValue
        public Builder addApi(@NonNull OptionalModuleApi api) {
            this.zaa.add(api);
            return this;
        }

        @NonNull
        public ModuleInstallRequest build() {
            return new ModuleInstallRequest(this.zaa, this.zab, this.zac, true, (zac) null);
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder setListener(@NonNull InstallStatusListener listener) {
            return setListener(listener, (Executor) null);
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder setListener(@NonNull InstallStatusListener installStatusListener, @Nullable Executor executor) {
            this.zab = installStatusListener;
            this.zac = executor;
            return this;
        }
    }

    /* synthetic */ ModuleInstallRequest(List list, InstallStatusListener installStatusListener, Executor executor, boolean z, zac zac2) {
        Preconditions.checkNotNull(list, "APIs must not be null.");
        Preconditions.checkArgument(!list.isEmpty(), "APIs must not be empty.");
        if (executor != null) {
            Preconditions.checkNotNull(installStatusListener, "Listener must not be null when listener executor is set.");
        }
        this.zaa = list;
        this.zab = installStatusListener;
        this.zac = executor;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    @NonNull
    public List<OptionalModuleApi> getApis() {
        return this.zaa;
    }

    @Nullable
    public InstallStatusListener getListener() {
        return this.zab;
    }

    @Nullable
    public Executor getListenerExecutor() {
        return this.zac;
    }
}

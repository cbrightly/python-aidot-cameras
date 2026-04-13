package com.google.chip.chiptool.provisioning;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProvisionNetworkType.kt */
public enum ProvisionNetworkType {
    THREAD,
    WIFI;
    
    @NotNull
    public static final Companion Companion = null;

    static {
        Companion = new Companion((DefaultConstructorMarker) null);
    }

    /* compiled from: ProvisionNetworkType.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @Nullable
        public final ProvisionNetworkType fromName(@Nullable String name) {
            ProvisionNetworkType provisionNetworkType = ProvisionNetworkType.THREAD;
            if (k.a(name, provisionNetworkType.name())) {
                return provisionNetworkType;
            }
            ProvisionNetworkType provisionNetworkType2 = ProvisionNetworkType.WIFI;
            if (k.a(name, provisionNetworkType2.name())) {
                return provisionNetworkType2;
            }
            return null;
        }
    }
}

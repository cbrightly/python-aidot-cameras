package com.google.chip.chiptool;

import android.content.Context;
import chip.devicecontroller.ChipDeviceController;
import chip.platform.AndroidBleManager;
import chip.platform.AndroidChipPlatform;
import chip.platform.ChipMdnsCallbackImpl;
import chip.platform.DiagnosticDataProviderImpl;
import chip.platform.NsdManagerServiceBrowser;
import chip.platform.NsdManagerServiceResolver;
import chip.platform.PreferencesConfigurationManager;
import chip.platform.PreferencesKeyValueStoreManager;
import kotlin.coroutines.d;
import kotlin.coroutines.i;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ChipClient.kt */
public final class ChipClient {
    @NotNull
    public static final ChipClient INSTANCE = new ChipClient();
    @NotNull
    private static final String TAG = "ChipClient";
    private static AndroidChipPlatform androidPlatform;
    private static ChipDeviceController chipDeviceController;

    private ChipClient() {
    }

    @NotNull
    public final ChipDeviceController getDeviceController(@NotNull Context context) {
        k.e(context, "context");
        getAndroidChipPlatform(context);
        if (chipDeviceController == null) {
            chipDeviceController = new ChipDeviceController();
        }
        ChipDeviceController chipDeviceController2 = chipDeviceController;
        if (chipDeviceController2 != null) {
            return chipDeviceController2;
        }
        k.t("chipDeviceController");
        throw null;
    }

    @NotNull
    public final AndroidChipPlatform getAndroidChipPlatform(@Nullable Context context) {
        if (androidPlatform == null && context != null) {
            ChipDeviceController.loadJni();
            androidPlatform = new AndroidChipPlatform(new AndroidBleManager(), new PreferencesKeyValueStoreManager(context), new PreferencesConfigurationManager(context), new NsdManagerServiceResolver(context), new NsdManagerServiceBrowser(context), new ChipMdnsCallbackImpl(), new DiagnosticDataProviderImpl(context));
        }
        AndroidChipPlatform androidChipPlatform = androidPlatform;
        if (androidChipPlatform != null) {
            return androidChipPlatform;
        }
        k.t("androidPlatform");
        throw null;
    }

    @Nullable
    public final Object getConnectedDevicePointer(@NotNull Context context, long nodeId, @NotNull d<? super Long> $completion) {
        i continuation = new i(b.c($completion));
        INSTANCE.getDeviceController(context).getConnectedDevicePointer(nodeId, new ChipClient$getConnectedDevicePointer$2$1(continuation));
        Object a = continuation.a();
        if (a == c.d()) {
            h.c($completion);
        }
        return a;
    }
}

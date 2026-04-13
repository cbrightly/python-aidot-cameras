package com.google.chip.chiptool;

import chip.devicecontroller.GetConnectedDeviceCallbackJni;
import com.leedarson.serviceimpl.k;
import kotlin.coroutines.d;
import kotlin.o;
import org.jetbrains.annotations.NotNull;

/* compiled from: ChipClient.kt */
public final class ChipClient$getConnectedDevicePointer$2$1 implements GetConnectedDeviceCallbackJni.GetConnectedDeviceCallback {
    final /* synthetic */ d<Long> $continuation;

    ChipClient$getConnectedDevicePointer$2$1(d<? super Long> $continuation2) {
        this.$continuation = $continuation2;
    }

    public void onDeviceConnected(long devicePointer) {
        k.a.l("ChipClient", "Got connected device pointer");
        d<Long> dVar = this.$continuation;
        Long valueOf = Long.valueOf(devicePointer);
        o.a aVar = o.Companion;
        dVar.resumeWith(o.m17constructorimpl(valueOf));
    }

    public void onConnectionFailure(long nodeId, @NotNull Exception error) {
        kotlin.jvm.internal.k.e(error, "error");
        k.a.d("ChipClient", kotlin.jvm.internal.k.l("Unable to get connected device with nodeId ", Long.valueOf(nodeId)), error);
        d<Long> dVar = this.$continuation;
        o.a aVar = o.Companion;
        dVar.resumeWith(o.m17constructorimpl(-1L));
    }
}

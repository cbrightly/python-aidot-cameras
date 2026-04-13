package com.google.chip.chiptool.bluetooth;

import chiptool.bluetooth.BLEProxy;
import com.leedarson.serviceimpl.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u00022\b\u0010\u0001\u001a\u0004\u0018\u00010\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"", "it", "Lkotlin/x;", "<anonymous>", "(Ljava/lang/Throwable;)V"}, k = 3, mv = {1, 5, 1})
/* compiled from: BluetoothManager.kt */
public final class BluetoothManager$connect$2$1 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
    final /* synthetic */ BLEProxy $bleProxy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BluetoothManager$connect$2$1(BLEProxy bLEProxy) {
        super(1);
        this.$bleProxy = bLEProxy;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return x.a;
    }

    public final void invoke(@Nullable Throwable it) {
        k.h(k.a, "continuation.invokeOnCancellation", (String) null, 2, (Object) null);
        this.$bleProxy.cancel();
    }
}

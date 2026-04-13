package com.google.chip.chiptool.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.content.Context;
import chiptool.bluetooth.BLEProxy;
import com.google.chip.chiptool.ChipClient;
import kotlin.o;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.Nullable;

/* compiled from: BluetoothManager.kt */
public final class BluetoothManager$getBLEProxyListener$1 implements BLEProxy.BleProxyListener {
    final /* synthetic */ Context $context;
    final /* synthetic */ n<BluetoothGatt> $continuation;
    @Nullable
    private final n<BluetoothGatt> coroutineContinuation;
    final /* synthetic */ BluetoothManager this$0;

    BluetoothManager$getBLEProxyListener$1(n<? super BluetoothGatt> $continuation2, BluetoothManager $receiver, Context $context2) {
        this.$continuation = $continuation2;
        this.this$0 = $receiver;
        this.$context = $context2;
        this.coroutineContinuation = $continuation2;
    }

    public void onConnected(@Nullable BluetoothGatt gatt) {
        this.this$0.bleGatt = gatt;
        this.this$0.connectionId = ChipClient.INSTANCE.getAndroidChipPlatform(this.$context).getBLEManager().addConnection(this.this$0.bleGatt);
        n<BluetoothGatt> nVar = this.coroutineContinuation;
        boolean z = false;
        if (nVar != null && nVar.isActive()) {
            z = true;
        }
        if (z) {
            n<BluetoothGatt> nVar2 = this.coroutineContinuation;
            o.a aVar = o.Companion;
            nVar2.resumeWith(o.m17constructorimpl(gatt));
        }
    }

    public void onConnectRetryFail() {
        n<BluetoothGatt> nVar = this.coroutineContinuation;
        boolean z = false;
        if (nVar != null && nVar.isActive()) {
            z = true;
        }
        if (z) {
            n<BluetoothGatt> nVar2 = this.coroutineContinuation;
            o.a aVar = o.Companion;
            nVar2.resumeWith(o.m17constructorimpl((Object) null));
        }
    }

    public void onDisconnectedWhenWorking() {
    }
}

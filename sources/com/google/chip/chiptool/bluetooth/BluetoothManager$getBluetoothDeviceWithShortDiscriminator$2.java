package com.google.chip.chiptool.bluetooth;

import com.telink.ble.mesh.entity.AdvertisingDevice;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.b;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.s;
import kotlinx.coroutines.channels.u;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/o0;", "Lcom/telink/ble/mesh/entity/AdvertisingDevice;", "<anonymous>", "(Lkotlinx/coroutines/o0;)Lcom/telink/ble/mesh/entity/AdvertisingDevice;"}, k = 3, mv = {1, 5, 1})
@f(c = "com.google.chip.chiptool.bluetooth.BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2", f = "BluetoothManager.kt", l = {191}, m = "invokeSuspend")
/* compiled from: BluetoothManager.kt */
public final class BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super AdvertisingDevice>, Object> {
    final /* synthetic */ int $discriminator;
    final /* synthetic */ boolean $hasShortDis;
    int label;
    final /* synthetic */ BluetoothManager this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2(BluetoothManager bluetoothManager, int i, boolean z, d<? super BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2> dVar) {
        super(2, dVar);
        this.this$0 = bluetoothManager;
        this.$discriminator = i;
        this.$hasShortDis = z;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        return new BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2(this.this$0, this.$discriminator, this.$hasShortDis, dVar);
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super AdvertisingDevice> dVar) {
        return ((BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    @l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Lkotlinx/coroutines/channels/u;", "Lcom/telink/ble/mesh/entity/AdvertisingDevice;", "Lkotlin/x;", "<anonymous>", "(Lkotlinx/coroutines/channels/u;)V"}, k = 3, mv = {1, 5, 1})
    @f(c = "com.google.chip.chiptool.bluetooth.BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2$1", f = "BluetoothManager.kt", l = {186}, m = "invokeSuspend")
    /* renamed from: com.google.chip.chiptool.bluetooth.BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2$1  reason: invalid class name */
    /* compiled from: BluetoothManager.kt */
    public static final class AnonymousClass1 extends kotlin.coroutines.jvm.internal.l implements p<u<? super AdvertisingDevice>, d<? super x>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            AnonymousClass1 r0 = new AnonymousClass1(bluetoothManager, i, z, dVar);
            r0.L$0 = obj;
            return r0;
        }

        @Nullable
        public final Object invoke(@NotNull u<? super AdvertisingDevice> uVar, @Nullable d<? super x> dVar) {
            return ((AnonymousClass1) create(uVar, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    u $this$callbackFlow = (u) this.L$0;
                    final BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2$1$scannerObserver$1 scannerObserver = new BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2$1$scannerObserver$1(z, i, bluetoothManager, $this$callbackFlow);
                    bluetoothManager.logd(k.l("Starting Bluetooth scan,with short discriminator:", b.c(i)));
                    com.leedarson.serviceimpl.base.c.k().x(scannerObserver);
                    AnonymousClass1 r4 = new a<x>() {
                        public final void invoke() {
                            com.leedarson.serviceimpl.base.c.k().B(scannerObserver);
                            Thread.sleep(500);
                        }
                    };
                    this.label = 1;
                    if (s.a($this$callbackFlow, r4, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        Object d = c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                final BluetoothManager bluetoothManager = this.this$0;
                final int i = this.$discriminator;
                final boolean z = this.$hasShortDis;
                kotlinx.coroutines.flow.c e = e.e(new AnonymousClass1((d<? super AnonymousClass1>) null));
                this.label = 1;
                Object l = e.l(e, this);
                if (l == d) {
                    return d;
                }
                return l;
            case 1:
                kotlin.p.b($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}

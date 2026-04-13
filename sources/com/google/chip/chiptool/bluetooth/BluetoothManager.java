package com.google.chip.chiptool.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import chip.platform.BleCallback;
import chiptool.bluetooth.BLEProxy;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.chip.chiptool.ChipClient;
import com.leedarson.serviceimpl.k;
import com.leedarson.serviceimpl.listener.a;
import com.leedarson.serviceimpl.listener.d;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.collections.y;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import kotlinx.coroutines.z2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BluetoothManager.kt */
public final class BluetoothManager implements BleCallback {
    @NotNull
    private static final String CHIP_UUID = "0000FFF6-0000-1000-8000-00805F9B34FB";
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    @NotNull
    private static final String TAG = "chip.BluetoothManager";
    /* access modifiers changed from: private */
    @Nullable
    public BluetoothGatt bleGatt;
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    /* access modifiers changed from: private */
    public int connectionId;
    @Nullable
    private a ldsGattListener;
    @Nullable
    private d provisionCallback;

    @Nullable
    public final d getProvisionCallback() {
        return this.provisionCallback;
    }

    public final void setProvisionCallback(@Nullable d dVar) {
        this.provisionCallback = dVar;
    }

    @Nullable
    public final a getLdsGattListener() {
        return this.ldsGattListener;
    }

    public final void setLdsGattListener(@Nullable a aVar) {
        this.ldsGattListener = aVar;
    }

    public final int getConnectionId() {
        return this.connectionId;
    }

    /* access modifiers changed from: private */
    public final byte[] getServiceData(int discriminator) {
        int versionDiscriminator = ((0 & 15) << 12) | (discriminator & 4095);
        int[] $this$map$iv = {0, versionDiscriminator, versionDiscriminator >> 8};
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        for (int item$iv$iv : $this$map$iv) {
            destination$iv$iv.add(Byte.valueOf((byte) item$iv$iv));
        }
        return y.y0(destination$iv$iv);
    }

    public static /* synthetic */ Object getBluetoothDevice$default(BluetoothManager bluetoothManager, Context context, int i, int i2, kotlin.coroutines.d dVar, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return bluetoothManager.getBluetoothDevice(context, i, i2, dVar);
    }

    @Nullable
    public final Object getBluetoothDevice(@NotNull Context context, int discriminator, int hasShortDis, @NotNull kotlin.coroutines.d<? super AdvertisingDevice> $completion) {
        if (!this.bluetoothAdapter.isEnabled()) {
            this.bluetoothAdapter.enable();
        }
        if (this.bluetoothAdapter.getBluetoothLeScanner() == null) {
            Log.e(TAG, "No bluetooth scanner found");
            return null;
        }
        boolean z = true;
        if (hasShortDis != 1) {
            z = false;
        }
        return getBluetoothDeviceWithShortDiscriminator(context, discriminator, z, $completion);
    }

    @Nullable
    public final Object getBluetoothDeviceWithShortDiscriminator(@NotNull Context context, int discriminator, boolean hasShortDis, @NotNull kotlin.coroutines.d<? super AdvertisingDevice> $completion) {
        if (!this.bluetoothAdapter.isEnabled()) {
            this.bluetoothAdapter.enable();
        }
        if (this.bluetoothAdapter.getBluetoothLeScanner() != null) {
            return z2.c(60000, new BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2(this, discriminator, hasShortDis, (kotlin.coroutines.d<? super BluetoothManager$getBluetoothDeviceWithShortDiscriminator$2>) null), $completion);
        }
        Log.e(TAG, "No bluetooth scanner found");
        return null;
    }

    @Nullable
    public final Object connect(@NotNull Context context, @NotNull BluetoothDevice device, @NotNull kotlin.coroutines.d<? super BluetoothGatt> $completion) {
        o cancellable$iv = new o(b.c($completion), 1);
        cancellable$iv.w();
        n continuation = cancellable$iv;
        BluetoothGattCallback bluetoothGattCallback = getBluetoothGattCallback(context, continuation);
        logd("Connecting");
        BLEProxy bleProxy = new BLEProxy(context, bluetoothGattCallback, device);
        bleProxy.connectGatt(5, getBLEProxyListener(context, continuation));
        ChipClient.INSTANCE.getAndroidChipPlatform(context).getBLEManager().setBleCallback(this);
        continuation.f(new BluetoothManager$connect$2$1(bleProxy));
        Object t = cancellable$iv.t();
        if (t == c.d()) {
            h.c($completion);
        }
        return t;
    }

    /* access modifiers changed from: private */
    public final BLEProxy.BleProxyListener getBLEProxyListener(Context context, n<? super BluetoothGatt> continuation) {
        return new BluetoothManager$getBLEProxyListener$1(continuation, this, context);
    }

    /* access modifiers changed from: private */
    public final BluetoothGattCallback getBluetoothGattCallback(Context context, n<? super BluetoothGatt> continuation) {
        return new BluetoothManager$getBluetoothGattCallback$1(context, continuation, this);
    }

    /* compiled from: BluetoothManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public void onCloseBleComplete(int connId) {
        this.connectionId = 0;
        logd("onCloseBleComplete");
        a aVar = this.ldsGattListener;
        if (aVar != null) {
            aVar.b();
        }
    }

    public void onNotifyChipConnectionClosed(int connId) {
        BluetoothGatt bluetoothGatt = this.bleGatt;
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
        }
        refreshCache();
        this.connectionId = 0;
        logd("onNotifyChipConnectionClosed");
        d dVar = this.provisionCallback;
        if (dVar != null) {
            dVar.b();
        }
    }

    /* access modifiers changed from: private */
    public final void logd(String msg) {
        k.a.a(msg, TAG);
    }

    public final boolean refreshCache() {
        if (Build.VERSION.SDK_INT >= 27) {
            return false;
        }
        try {
            BluetoothGatt localBluetoothGatt = this.bleGatt;
            Method localMethod = null;
            if (localBluetoothGatt != null) {
                Class<?> cls = localBluetoothGatt.getClass();
                if (cls != null) {
                    localMethod = cls.getMethod("refresh", new Class[0]);
                }
            }
            if (localMethod != null) {
                Object invoke = localMethod.invoke(localBluetoothGatt, new Object[0]);
                if (invoke != null) {
                    return ((Boolean) invoke).booleanValue();
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
            }
        } catch (Exception localException) {
            logd(kotlin.jvm.internal.k.l("An exception occurs while refreshing device:", localException));
        }
        return false;
    }

    public final void setExternalGattListener(@NotNull a listener) {
        kotlin.jvm.internal.k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.ldsGattListener = listener;
    }
}

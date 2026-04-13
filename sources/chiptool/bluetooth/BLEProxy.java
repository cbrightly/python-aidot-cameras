package chiptool.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import chip.platform.BleConnectionCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import meshsdk.cache.CacheHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BLEProxy.kt */
public final class BLEProxy {
    private final long CONNECT_DELAY = CacheHandler.delayTime;
    private final long CONNECT_TIMEOUT = 10000;
    private final long SERVICE_TIMEOUT = 1000;
    @NotNull
    private final String TAG = "BLEProxy";
    private final int WHAT_CONNECT;
    private final int WHAT_CONNECT_TIMEOUT = 1;
    private final int WHAT_SERVICE_DISCOVER = 2;
    private final int WHAT_SERVICE_DISCOVER_TIMEOUT = 3;
    public String bleMac;
    @NotNull
    private final BluetoothDevice bluetoothDevice;
    /* access modifiers changed from: private */
    @Nullable
    public BluetoothGatt bluetoothGatt;
    /* access modifiers changed from: private */
    @NotNull
    public final Runnable connectTask;
    /* access modifiers changed from: private */
    @NotNull
    public final Runnable connectTimeoutTask;
    @NotNull
    private final Context ctx;
    private int currentCount;
    /* access modifiers changed from: private */
    @NotNull
    public ActionMode currentMode = ActionMode.CONNECT;
    @Nullable
    private final BluetoothGattCallback externalCallback;
    /* access modifiers changed from: private */
    @NotNull
    public Handler handler;
    /* access modifiers changed from: private */
    @Nullable
    public BleProxyListener proxyListener;
    private int retryCount;
    /* access modifiers changed from: private */
    @NotNull
    public final Runnable serviceDiscoverTask;
    /* access modifiers changed from: private */
    @NotNull
    public final Runnable serviceDiscoverTimeoutTask;
    private int serviceRetryCount;

    /* compiled from: BLEProxy.kt */
    public enum ActionMode {
        CONNECT,
        WORKING
    }

    /* compiled from: BLEProxy.kt */
    public interface BleProxyListener {
        void onConnectRetryFail();

        void onConnected(@Nullable BluetoothGatt bluetoothGatt);

        void onDisconnectedWhenWorking();
    }

    public BLEProxy(@NotNull Context ctx2, @Nullable BluetoothGattCallback externalCallback2, @NotNull BluetoothDevice bluetoothDevice2) {
        k.e(ctx2, "ctx");
        k.e(bluetoothDevice2, "bluetoothDevice");
        this.ctx = ctx2;
        this.externalCallback = externalCallback2;
        this.bluetoothDevice = bluetoothDevice2;
        String address = bluetoothDevice2.getAddress();
        k.d(address, "bluetoothDevice.address");
        setBleMac(address);
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        Looper mainLooper = Looper.getMainLooper();
        k.c(mainLooper);
        this.handler = new Handler(this, mainLooper) {
            final /* synthetic */ BLEProxy this$0;

            {
                this.this$0 = $receiver;
            }

            public void handleMessage(@NotNull Message msg) {
                k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
                super.handleMessage(msg);
                int i = msg.what;
                if (i == this.this$0.getWHAT_CONNECT_TIMEOUT()) {
                    this.this$0.connectTimeoutTask.run();
                } else if (i == this.this$0.getWHAT_CONNECT()) {
                    this.this$0.connectTask.run();
                } else if (i == this.this$0.getWHAT_SERVICE_DISCOVER()) {
                    this.this$0.serviceDiscoverTask.run();
                } else if (i == this.this$0.getWHAT_SERVICE_DISCOVER_TIMEOUT()) {
                    this.this$0.serviceDiscoverTimeoutTask.run();
                }
            }
        };
        this.connectTask = new a(this);
        this.connectTimeoutTask = new d(this);
        this.serviceDiscoverTask = new b(this);
        this.serviceDiscoverTimeoutTask = new c(this);
    }

    @NotNull
    public final Context getCtx() {
        return this.ctx;
    }

    @Nullable
    public final BluetoothGattCallback getExternalCallback() {
        return this.externalCallback;
    }

    @NotNull
    public final BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public final int getWHAT_CONNECT() {
        return this.WHAT_CONNECT;
    }

    public final int getWHAT_CONNECT_TIMEOUT() {
        return this.WHAT_CONNECT_TIMEOUT;
    }

    public final int getWHAT_SERVICE_DISCOVER() {
        return this.WHAT_SERVICE_DISCOVER;
    }

    public final int getWHAT_SERVICE_DISCOVER_TIMEOUT() {
        return this.WHAT_SERVICE_DISCOVER_TIMEOUT;
    }

    @NotNull
    public final String getTAG() {
        return this.TAG;
    }

    public final long getSERVICE_TIMEOUT() {
        return this.SERVICE_TIMEOUT;
    }

    public final long getCONNECT_TIMEOUT() {
        return this.CONNECT_TIMEOUT;
    }

    public final long getCONNECT_DELAY() {
        return this.CONNECT_DELAY;
    }

    @NotNull
    public final String getBleMac() {
        String str = this.bleMac;
        if (str != null) {
            return str;
        }
        k.t("bleMac");
        throw null;
    }

    public final void setBleMac(@NotNull String str) {
        k.e(str, "<set-?>");
        this.bleMac = str;
    }

    public final void connectGatt(int count, @NotNull BleProxyListener listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.retryCount = count;
        this.proxyListener = listener;
        this.currentMode = ActionMode.CONNECT;
        connect();
    }

    private final void connect() {
        com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
        kVar.a("第:" + (this.currentCount + 1) + "次连接，设置超时时间为：" + (this.CONNECT_TIMEOUT * ((long) (this.currentCount + 1))) + ",connect..." + getBleMac(), this.TAG);
        this.handler.removeMessages(this.WHAT_CONNECT_TIMEOUT);
        this.handler.sendEmptyMessageDelayed(this.WHAT_CONNECT_TIMEOUT, this.CONNECT_TIMEOUT * ((long) (this.currentCount + 1)));
        new BleConnectionCompat(this.ctx).connectGatt(this.bluetoothDevice, false, new BLEProxy$connect$1(this));
    }

    /* access modifiers changed from: private */
    public final void onDisconnect(String reason) {
        BluetoothGatt bluetoothGatt2 = this.bluetoothGatt;
        if (bluetoothGatt2 != null) {
            bluetoothGatt2.disconnect();
        }
        BluetoothGatt bluetoothGatt3 = this.bluetoothGatt;
        if (bluetoothGatt3 != null) {
            bluetoothGatt3.close();
        }
        refreshCache();
        ActionMode actionMode = this.currentMode;
        ActionMode actionMode2 = ActionMode.CONNECT;
        if (actionMode == actionMode2) {
            int i = this.currentCount;
            if (i < this.retryCount) {
                this.currentCount = i + 1;
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                kVar.c(reason + " ,current retry:" + this.currentCount + ",reconnecting BLE " + getBleMac() + "...", this.TAG);
                this.handler.removeMessages(this.WHAT_CONNECT);
                this.handler.sendEmptyMessageDelayed(this.WHAT_CONNECT, this.CONNECT_DELAY);
                return;
            }
            com.leedarson.serviceimpl.k.a.c(k.l(reason, ",BLE 达到重连次数上限"), this.TAG);
            BleProxyListener bleProxyListener = this.proxyListener;
            if (bleProxyListener != null) {
                bleProxyListener.onConnectRetryFail();
                return;
            }
            return;
        }
        this.currentMode = actionMode2;
        BleProxyListener bleProxyListener2 = this.proxyListener;
        if (bleProxyListener2 != null) {
            bleProxyListener2.onDisconnectedWhenWorking();
        }
    }

    /* access modifiers changed from: private */
    public final void startServicesDiscovering() {
        com.leedarson.serviceimpl.k.a.a("startServicesDiscovering ", this.TAG);
        this.serviceDiscoverTask.run();
    }

    public final void cancel() {
        this.handler.removeCallbacksAndMessages((Object) null);
        BluetoothGatt bluetoothGatt2 = this.bluetoothGatt;
        if (bluetoothGatt2 != null) {
            bluetoothGatt2.disconnect();
        }
        BluetoothGatt bluetoothGatt3 = this.bluetoothGatt;
        if (bluetoothGatt3 != null) {
            bluetoothGatt3.close();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: connectTask$lambda-0  reason: not valid java name */
    public static final void m4connectTask$lambda0(BLEProxy this$0) {
        k.e(this$0, "this$0");
        this$0.connect();
    }

    /* access modifiers changed from: private */
    /* renamed from: connectTimeoutTask$lambda-1  reason: not valid java name */
    public static final void m5connectTimeoutTask$lambda1(BLEProxy this$0) {
        k.e(this$0, "this$0");
        this$0.onDisconnect("BLE 连接超时");
    }

    /* access modifiers changed from: private */
    /* renamed from: serviceDiscoverTask$lambda-2  reason: not valid java name */
    public static final void m6serviceDiscoverTask$lambda2(BLEProxy this$0) {
        k.e(this$0, "this$0");
        com.leedarson.serviceimpl.k.a.a("discoverServices Runnable task...", this$0.getTAG());
        BluetoothGatt bluetoothGatt2 = this$0.bluetoothGatt;
        if (bluetoothGatt2 != null) {
            bluetoothGatt2.discoverServices();
        }
        this$0.handler.removeMessages(this$0.getWHAT_SERVICE_DISCOVER_TIMEOUT());
        this$0.handler.sendEmptyMessageDelayed(this$0.getWHAT_SERVICE_DISCOVER_TIMEOUT(), this$0.getSERVICE_TIMEOUT());
    }

    public final int getServiceRetryCount() {
        return this.serviceRetryCount;
    }

    public final void setServiceRetryCount(int i) {
        this.serviceRetryCount = i;
    }

    /* access modifiers changed from: private */
    /* renamed from: serviceDiscoverTimeoutTask$lambda-3  reason: not valid java name */
    public static final void m7serviceDiscoverTimeoutTask$lambda3(BLEProxy this$0) {
        k.e(this$0, "this$0");
        com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("discover service timeout ,retry count:", Integer.valueOf(this$0.getServiceRetryCount())), (String) null, 2, (Object) null);
        if (this$0.getServiceRetryCount() >= 2) {
            this$0.onDisconnect("BLE Service discovery 失败达到上限:2");
            return;
        }
        this$0.setServiceRetryCount(this$0.getServiceRetryCount() + 1);
        this$0.startServicesDiscovering();
    }

    public final boolean refreshCache() {
        if (Build.VERSION.SDK_INT >= 27) {
            return false;
        }
        try {
            BluetoothGatt localBluetoothGatt = this.bluetoothGatt;
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
        } catch (Exception e) {
        }
        return false;
    }
}

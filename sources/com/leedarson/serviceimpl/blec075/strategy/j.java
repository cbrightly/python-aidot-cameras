package com.leedarson.serviceimpl.blec075.strategy;

import android.bluetooth.BluetoothGatt;
import android.os.Handler;
import android.os.Looper;
import com.clj.fastble.bluetooth.d;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.e;
import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import meshsdk.cache.CacheHandler;
import timber.log.a;

/* compiled from: ConnectRetryStrategy */
public abstract class j {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String a = "ConnectRetry";
    private int b;
    /* access modifiers changed from: private */
    public int c = 0;
    /* access modifiers changed from: private */
    public Handler d;
    /* access modifiers changed from: private */
    public BleDevice e;
    /* access modifiers changed from: private */
    public b f = new b(this, (a) null);
    private c g = new c(this, (a) null);
    private String h = "";
    private long i = System.currentTimeMillis();
    private long j = 10000;
    io.reactivex.disposables.b k;
    private long l = CacheHandler.delayTime;

    public abstract void n(BleDevice bleDevice);

    public abstract void o(BleDevice bleDevice, com.clj.fastble.exception.a aVar);

    public abstract void p(int i2, String str);

    public abstract void q(BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i2);

    public abstract void r(boolean z, BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i2);

    public abstract void s(BleDevice bleDevice);

    static /* synthetic */ void b(j x0, com.clj.fastble.exception.a x1) {
        Class[] clsArr = {j.class, com.clj.fastble.exception.a.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6576, clsArr, Void.TYPE).isSupported) {
            x0.t(x1);
        }
    }

    public j(int maxRetroCount, String taskId) {
        this.b = maxRetroCount;
        this.d = new Handler(Looper.getMainLooper());
        this.h = taskId;
        this.i = System.currentTimeMillis();
    }

    public void v(long limit_time_ms) {
        this.j = limit_time_ms;
    }

    public void h(BleDevice bleDevice) {
        if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6568, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            this.e = bleDevice;
            a.b g2 = timber.log.a.g(this.a);
            g2.a("LdsConnectDevice ConnectRetry connect rssi:" + bleDevice.e() + ",timeout:" + 8000 + "  retryTime=" + this.c + "   mac=" + this.e.c() + "  advData=" + this.e.h(), new Object[0]);
            com.clj.fastble.a.o().G((long) 8000);
            com.clj.fastble.a.o().b(bleDevice, new a(bleDevice));
        }
    }

    /* compiled from: ConnectRetryStrategy */
    public class a extends com.clj.fastble.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ BleDevice a;

        a(BleDevice bleDevice) {
            this.a = bleDevice;
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6577, new Class[0], Void.TYPE).isSupported) {
                j.this.s(this.a);
            }
        }

        public void b(BleDevice bleDevice, com.clj.fastble.exception.a exception) {
            Class[] clsArr = {BleDevice.class, com.clj.fastble.exception.a.class};
            if (!PatchProxy.proxy(new Object[]{bleDevice, exception}, this, changeQuickRedirect, false, 6578, clsArr, Void.TYPE).isSupported) {
                j.b(j.this, exception);
            }
        }

        public void c(BleDevice bleDevice, BluetoothGatt gatt, int status) {
            Object[] objArr = {bleDevice, gatt, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6579, new Class[]{BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                j.this.d.removeCallbacks(j.this.f);
                j.this.q(bleDevice, gatt, status);
            }
        }

        public void d(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            Object[] objArr = {new Byte(isActiveDisConnected ? (byte) 1 : 0), device, gatt, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6580, new Class[]{Boolean.TYPE, BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                j.this.r(isActiveDisConnected, device, gatt, status);
            }
        }

        public void a(int code, String desc) {
            Object[] objArr = {new Integer(code), desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6581, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                j.this.p(code, desc);
            }
        }
    }

    /* compiled from: ConnectRetryStrategy */
    public final class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        /* synthetic */ b(j x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6583, new Class[0], Void.TYPE).isSupported) {
                if (j.this.e != null) {
                    j.this.d.removeCallbacks(j.this.f);
                    j.b(j.this, new e("ConnectRetryStrategy.ConnectTimeoutTask"));
                }
            }
        }
    }

    private void t(com.clj.fastble.exception.a exception) {
        if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6569, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
            if (exception instanceof e) {
                a.b g2 = timber.log.a.g(this.a);
                g2.c("LdsConnectDevice ConnectRetry onConnect timeout, mac:" + this.e.c() + ",hasRetry :" + this.c + "   bleDevice.getName=" + this.e.b() + "  exception:=" + exception.toString(), new Object[0]);
            } else {
                a.b g3 = timber.log.a.g(this.a);
                g3.c("LdsConnectDevice onConnectFail ,mac:" + this.e.c() + ",hasRetry :" + this.c + ",ex:" + exception.toString(), new Object[0]);
            }
            i(this.e);
            long diffWithStartConnectTime = System.currentTimeMillis() - this.i;
            int i2 = this.c;
            if (i2 < this.b || diffWithStartConnectTime < this.j) {
                int i3 = i2 + 1;
                this.c = i3;
                if (i3 < 2) {
                    a("we start  to reconnect ..... taskId=" + this.h + "   diffWithStartTime=" + diffWithStartConnectTime + "  currentRetry=" + this.c + "  maxRetryCount=" + this.b + ",afterDelay=" + (this.l + ((long) (this.c * 1000))));
                    this.d.postDelayed(this.g, this.l + ((long) (this.c * 1000)));
                    return;
                }
                io.reactivex.disposables.b bVar = this.k;
                if (bVar != null && !bVar.isDisposed()) {
                    this.k.dispose();
                }
                a("we start to rescan to connnect ..... taskId=" + this.h + "   diffWithStartTime=" + diffWithStartConnectTime + "  currentRetry=" + this.c + "  maxRetryCount=" + this.b);
                this.k = new i().j(this.h, "", 5, "connectRetryStrategy").c(l.c()).I(new f(this), new e(this));
                return;
            }
            o(this.e, exception);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k(BleDevice _bleDevice) {
        if (!PatchProxy.proxy(new Object[]{_bleDevice}, this, changeQuickRedirect, false, 6575, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            if (_bleDevice == null || _bleDevice.a() == null) {
                String tip = "we found this target device fail taskId=" + this.h + "  reconnect task will be executed after 2000ms currentRetry=" + this.c + "  maxRetry=" + this.b;
                a(tip);
                p(200, tip);
                this.d.postDelayed(this.g, this.l + ((long) (this.c * 1000)));
                return;
            }
            this.e = _bleDevice;
            n(_bleDevice);
            String tip2 = "we found this target device taskId=" + this.h + "  reconnect task will be executed after 500ms currentRetry=" + this.c + "  maxRetry=" + this.b;
            a(tip2);
            p(200, tip2);
            this.d.postDelayed(this.g, (long) ((this.c * 500) + 500));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public /* synthetic */ void m(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 6574, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            String tip = "we found this target device fail taskId=" + this.h + "  reconnect task will be executed after 2000ms currentRetry=" + this.c + "  maxRetry=" + this.b;
            a(tip);
            p(200, tip);
            this.d.postDelayed(this.g, this.l + ((long) (this.c * 1000)));
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6570, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.i("LdsConnectDevice RetryStrategy: " + msg, new Object[0]);
        }
    }

    /* compiled from: ConnectRetryStrategy */
    public final class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private c() {
        }

        /* synthetic */ c(j x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6584, new Class[0], Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(j.this.a);
                g.a("reconnect , mac:" + j.this.e.c() + ",currentRetry :" + j.this.c, new Object[0]);
                j jVar = j.this;
                jVar.h(jVar.e);
            }
        }
    }

    private void i(BleDevice bleDevice) {
        if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6572, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.a).a("断开且释放连接:%s", bleDevice.c());
            d bleBluetooth = com.clj.fastble.a.o().q().e(bleDevice);
            if (bleBluetooth != null) {
                bleBluetooth.K();
            }
            BluetoothGatt bluetoothGatt = com.clj.fastble.a.o().i(bleDevice);
            if (bluetoothGatt != null) {
                boolean tempRefreshResult = u(bluetoothGatt);
                a.b g2 = timber.log.a.g(this.a);
                g2.a("LdsConnectDevice  111   开始准备释放gatt资源-强制释放  =" + tempRefreshResult + "  bluetoothGatt=" + bluetoothGatt, new Object[0]);
                bluetoothGatt.disconnect();
                bluetoothGatt.close();
                bluetoothGatt = null;
            }
            BluetoothGatt bluetoothGatt2 = bleDevice.p0;
            if (bluetoothGatt2 != null) {
                boolean tempRefreshResult2 = u(bluetoothGatt2);
                a.b g3 = timber.log.a.g(this.a);
                g3.a("LdsConnectDevice  222 开始准备释放gatt资源-强制释放  =" + tempRefreshResult2 + "  bluetoothGatt=" + bluetoothGatt, new Object[0]);
                bleDevice.p0.disconnect();
                bleDevice.p0.close();
                bleDevice.p0 = null;
            }
        }
    }

    private boolean u(BluetoothGatt mBluetoothGatt) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mBluetoothGatt}, this, changeQuickRedirect, false, 6573, new Class[]{BluetoothGatt.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            a.b g2 = timber.log.a.g(this.a);
            g2.a("LdsConnectDevice clear gatt cache" + mBluetoothGatt, new Object[0]);
            BluetoothGatt localBlueToothGatt = mBluetoothGatt;
            Method localMethod = localBlueToothGatt.getClass().getMethod("refresh", new Class[0]);
            if (localMethod != null) {
                localMethod.setAccessible(true);
                Boolean bool = Boolean.valueOf(((Boolean) localMethod.invoke(localBlueToothGatt, new Object[0])).booleanValue());
                localMethod.setAccessible(false);
                return bool.booleanValue();
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
            e2.printStackTrace();
        }
        return false;
    }
}

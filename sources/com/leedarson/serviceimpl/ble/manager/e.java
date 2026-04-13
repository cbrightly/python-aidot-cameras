package com.leedarson.serviceimpl.ble.manager;

import android.bluetooth.BluetoothGatt;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.blec075.strategy.j;
import com.leedarson.serviceimpl.blec075.strategy.k;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import timber.log.a;

/* compiled from: MessagePool */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final Object a = new Object();
    private String b;
    /* access modifiers changed from: private */
    public LinkedList<f> c;
    private AtomicBoolean d;
    private HashMap<String, BleC075Service.CommonBleCallback> e;
    /* access modifiers changed from: private */
    public Handler f;

    static /* synthetic */ void a(e x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6287, new Class[]{e.class}, Void.TYPE).isSupported) {
            x0.j();
        }
    }

    static /* synthetic */ void b(e x0, String x1) {
        Class[] clsArr = {e.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6288, clsArr, Void.TYPE).isSupported) {
            x0.g(x1);
        }
    }

    static /* synthetic */ void c(e x0, String x1) {
        Class[] clsArr = {e.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6289, clsArr, Void.TYPE).isSupported) {
            x0.l(x1);
        }
    }

    static /* synthetic */ void d(e x0, f x1, BleC075Service.CommonBleCallback x2, int x3) {
        Object[] objArr = {x0, x1, x2, new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6290, new Class[]{e.class, f.class, BleC075Service.CommonBleCallback.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.m(x1, x2, x3);
        }
    }

    public e(String mac, HandlerThread thread) {
        this.b = mac;
        this.c = new LinkedList<>();
        this.d = new AtomicBoolean(false);
        g("isWaiting.create  default=false");
        this.e = new HashMap<>();
        this.f = new a(thread.getLooper());
    }

    /* compiled from: MessagePool */
    public class a extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6291, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    e.a(e.this);
                }
            }
        }
    }

    public void i(f msgWrap, BleC075Service.CommonBleCallback callback) {
        Class[] clsArr = {f.class, BleC075Service.CommonBleCallback.class};
        if (!PatchProxy.proxy(new Object[]{msgWrap, callback}, this, changeQuickRedirect, false, 6279, clsArr, Void.TYPE).isSupported) {
            msgWrap.h = callback;
            this.c.add(msgWrap);
            g("postMessage 加入队列 messateType=" + msgWrap.i + ",mac" + msgWrap.a + "isWaiting" + this.d.get());
            j();
        }
    }

    private void j() {
        f wrap;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6280, new Class[0], Void.TYPE).isSupported) {
            synchronized (this.a) {
                if (!this.d.get() && (wrap = this.c.poll()) != null) {
                    this.d.compareAndSet(false, true);
                    int i = wrap.i;
                    if (i == 1) {
                        n(wrap, wrap.h);
                    } else if (i == 2) {
                        k(wrap, wrap.h);
                    } else if (i == 3) {
                        h(wrap, wrap.h);
                    }
                }
            }
        }
    }

    private void l(String bzRef) {
        if (!PatchProxy.proxy(new Object[]{bzRef}, this, changeQuickRedirect, false, 6281, new Class[]{String.class}, Void.TYPE).isSupported) {
            g("bzRef=" + bzRef);
            this.d.set(false);
            f nextMessage = this.c.peek();
            if (nextMessage != null) {
                long j = nextMessage.k;
                if (j != -1) {
                    this.f.sendEmptyMessageDelayed(0, j);
                    return;
                }
            }
            this.f.sendEmptyMessageDelayed(0, 100);
        }
    }

    private void k(f wrap, BleC075Service.CommonBleCallback commonBleCallback) {
        Class[] clsArr = {f.class, BleC075Service.CommonBleCallback.class};
        if (!PatchProxy.proxy(new Object[]{wrap, commonBleCallback}, this, changeQuickRedirect, false, 6282, clsArr, Void.TYPE).isSupported) {
            g("read");
            com.clj.fastble.a.o().F(wrap.b, wrap.e.toString(), wrap.f.toString(), new b(wrap, commonBleCallback));
        }
    }

    /* compiled from: MessagePool */
    public class b extends com.clj.fastble.callback.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f a;
        final /* synthetic */ BleC075Service.CommonBleCallback b;

        b(f fVar, BleC075Service.CommonBleCallback commonBleCallback) {
            this.a = fVar;
            this.b = commonBleCallback;
        }

        public void b(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6292, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "read success mac:" + this.a.a + h.b(data));
                BleC075Service.CommonBleCallback commonBleCallback = this.b;
                if (commonBleCallback != null) {
                    f fVar = this.a;
                    commonBleCallback.onReadSuccess(data, fVar.g, fVar.a);
                }
                e.c(e.this, "read.onReadSuccess");
            }
        }

        public void a(com.clj.fastble.exception.a exception) {
            if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6293, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                int statusCode = BleC075ServiceImpl.d;
                if (exception instanceof com.clj.fastble.exception.c) {
                    statusCode = ((com.clj.fastble.exception.c) exception).getGattStatus();
                } else if (exception instanceof com.clj.fastble.exception.d) {
                    statusCode = BleC075ServiceImpl.c;
                }
                boolean isConnect = com.clj.fastble.a.o().A(this.a.b);
                e eVar = e.this;
                e.b(eVar, "read fail mac:" + this.a.a + exception.toString() + ",isConnect:" + isConnect + ",isRetryWhenInterrupt:" + this.a.b());
                if (isConnect || !this.a.b() || !k.b().a(this.a.a)) {
                    BleC075Service.CommonBleCallback commonBleCallback = this.b;
                    if (commonBleCallback != null) {
                        Exception exc = new Exception(exception.getDescription());
                        f fVar = this.a;
                        commonBleCallback.onReadFailure(exc, fVar.g, fVar.a, statusCode);
                    }
                    e eVar2 = e.this;
                    e.c(eVar2, "read.onReadFailure  exception=" + exception.toString());
                    return;
                }
                e.d(e.this, this.a, this.b, statusCode);
            }
        }
    }

    /* compiled from: MessagePool */
    public class c extends j {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f m;
        final /* synthetic */ BleC075Service.CommonBleCallback n;
        final /* synthetic */ int o;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(int maxRetroCount, String taskId, f fVar, BleC075Service.CommonBleCallback commonBleCallback, int i) {
            super(maxRetroCount, taskId);
            this.m = fVar;
            this.n = commonBleCallback;
            this.o = i;
        }

        public void s(BleDevice bleDevice) {
        }

        public void o(BleDevice bleDevice, com.clj.fastble.exception.a exception) {
            BleC075Service.CommonBleCallback commonBleCallback;
            if (!PatchProxy.proxy(new Object[]{bleDevice, exception}, this, changeQuickRedirect, false, 6294, new Class[]{BleDevice.class, com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "消息发送 消息断线重连失败 retryWhenInterrupt : onBleConnectFail" + bleDevice.c() + ",exception:" + exception.getDescription());
                try {
                    int i = this.m.i;
                    if (i == 1) {
                        BleC075Service.CommonBleCallback commonBleCallback2 = this.n;
                        if (commonBleCallback2 != null) {
                            Exception exc = new Exception(exception.getDescription() + ",BLE重连失败，写指令最终失败");
                            f fVar = this.m;
                            commonBleCallback2.onWriteFailure(exc, fVar.g, fVar.a, this.o);
                        }
                    } else if (i == 2 && (commonBleCallback = this.n) != null) {
                        Exception exc2 = new Exception(exception.getDescription() + ",BLE重连失败，读指令最终失败");
                        f fVar2 = this.m;
                        commonBleCallback.onReadFailure(exc2, fVar2.g, fVar2.a, this.o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    timber.log.a.c("GhuntBle:e===" + e.getMessage(), new Object[0]);
                }
                e.c(e.this, "retryWhenInterrupt.onBleConnectFail");
            }
        }

        public void q(BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i) {
            if (!PatchProxy.proxy(new Object[]{bleDevice, bluetoothGatt, new Integer(i)}, this, changeQuickRedirect, false, 6295, new Class[]{BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "消息发送 消息断线重连成功 retryWhenInterrupt : onBleConnectSuccess" + bleDevice.c());
                try {
                    com.clj.fastble.a.o().H(bleDevice, 512, new a());
                } catch (Exception e) {
                    e.printStackTrace();
                    timber.log.a.c("GhuntBle:e===" + e.getMessage(), new Object[0]);
                    e eVar2 = e.this;
                    e.c(eVar2, "retryWhenInterrupt.Exception  e=" + e.toString());
                }
            }
        }

        /* compiled from: MessagePool */
        public class a extends com.clj.fastble.callback.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSetMTUFailure(com.clj.fastble.exception.a aVar) {
                if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 6299, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                    e.this.f.postDelayed(new a(this, c.this.m), 200);
                }
            }

            /* access modifiers changed from: private */
            /* renamed from: c */
            public /* synthetic */ void d(f wrap) {
                if (!PatchProxy.proxy(new Object[]{wrap}, this, changeQuickRedirect, false, 6302, new Class[]{f.class}, Void.TYPE).isSupported) {
                    wrap.a();
                    e.this.c.addFirst(wrap);
                    e.c(e.this, "retryWhenInterrupt.onBleConnectSuccess");
                }
            }

            public void onMtuChanged(int i) {
                Object[] objArr = {new Integer(i)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6300, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    e.this.f.postDelayed(new b(this, c.this.m), 200);
                }
            }

            /* access modifiers changed from: private */
            /* renamed from: a */
            public /* synthetic */ void b(f wrap) {
                if (!PatchProxy.proxy(new Object[]{wrap}, this, changeQuickRedirect, false, 6301, new Class[]{f.class}, Void.TYPE).isSupported) {
                    wrap.a();
                    e.this.c.addFirst(wrap);
                    e.c(e.this, "retryWhenInterrupt.onBleConnectSuccess");
                }
            }
        }

        public void r(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i) {
            BleC075Service.CommonBleCallback commonBleCallback;
            Object[] objArr = {new Byte(isActiveDisConnected ? (byte) 1 : 0), bleDevice, bluetoothGatt, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6296, new Class[]{Boolean.TYPE, BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                e.b(e.this, "消息发送 重连接发现  onBleDisConnected");
                try {
                    int i2 = this.m.i;
                    if (i2 == 1) {
                        BleC075Service.CommonBleCallback commonBleCallback2 = this.n;
                        if (commonBleCallback2 != null) {
                            Exception exc = new Exception("isActiveDisConnected=" + isActiveDisConnected + ",onBleDisConnected 连接断开，写指令最终失败");
                            f fVar = this.m;
                            commonBleCallback2.onWriteFailure(exc, fVar.g, fVar.a, this.o);
                        }
                    } else if (i2 == 2 && (commonBleCallback = this.n) != null) {
                        Exception exc2 = new Exception("isActiveDisConnected=" + isActiveDisConnected + ",onBleDisConnected 连接断开，读指令最终失败");
                        f fVar2 = this.m;
                        commonBleCallback.onReadFailure(exc2, fVar2.g, fVar2.a, this.o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    timber.log.a.c("GhuntBle:e===" + e.getMessage(), new Object[0]);
                }
                e.c(e.this, "retryWhenInterrupt.onBleDisConnected");
            }
        }

        public void p(int code, String desc) {
            Object[] objArr = {new Integer(code), desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6297, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "消息发送 重连发现   onBleConnectStateChange=  code=" + code + ",desc=" + desc);
            }
        }

        public void n(BleDevice bleDevice) {
            if (!PatchProxy.proxy(new Object[]{bleDevice}, this, changeQuickRedirect, false, 6298, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "发送消息重新更新对象： onBleBleDeviceUpdate=" + bleDevice);
            }
        }
    }

    private void m(f wrap, BleC075Service.CommonBleCallback commonBleCallback, int lastErrorStatus) {
        Object[] objArr = {wrap, commonBleCallback, new Integer(lastErrorStatus)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6283, new Class[]{f.class, BleC075Service.CommonBleCallback.class, Integer.TYPE}, Void.TYPE).isSupported) {
            new c(1, wrap.a, wrap, commonBleCallback, lastErrorStatus).h(wrap.b);
        }
    }

    private void n(f msgWrap, BleC075Service.CommonBleCallback commonBleCallback) {
        Class[] clsArr = {f.class, BleC075Service.CommonBleCallback.class};
        if (!PatchProxy.proxy(new Object[]{msgWrap, commonBleCallback}, this, changeQuickRedirect, false, 6284, clsArr, Void.TYPE).isSupported) {
            g("write dataLength=");
            com.clj.fastble.a.o().N(msgWrap.b, msgWrap.e.toString(), msgWrap.f.toString(), msgWrap.c, false, new d(commonBleCallback, msgWrap));
        }
    }

    /* compiled from: MessagePool */
    public class d extends com.clj.fastble.callback.h {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ BleC075Service.CommonBleCallback a;
        final /* synthetic */ f b;

        d(BleC075Service.CommonBleCallback commonBleCallback, f fVar) {
            this.a = commonBleCallback;
            this.b = fVar;
        }

        public void b(int current, int total, byte[] justWrite) {
            Object[] objArr = {new Integer(current), new Integer(total), justWrite};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6303, new Class[]{cls, cls, byte[].class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "write success, current: " + current + " total: " + total + " justWrite: " + com.clj.fastble.utils.c.a(justWrite, true));
                BleC075Service.CommonBleCallback commonBleCallback = this.a;
                if (commonBleCallback != null) {
                    f fVar = this.b;
                    commonBleCallback.onWriteSuccess(current, total, justWrite, fVar.g, fVar.a);
                }
                e.c(e.this, "write.onWriteSuccess");
            }
        }

        public void a(com.clj.fastble.exception.a exception) {
            if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6304, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                int statusCode = BleC075ServiceImpl.b;
                if (exception instanceof com.clj.fastble.exception.c) {
                    statusCode = ((com.clj.fastble.exception.c) exception).getGattStatus();
                } else if (exception instanceof com.clj.fastble.exception.d) {
                    statusCode = BleC075ServiceImpl.a;
                }
                boolean isConnect = com.clj.fastble.a.o().A(this.b.b);
                e eVar = e.this;
                e.b(eVar, "write fail mac:" + this.b.a + exception.toString() + ",isConnect:" + isConnect + ",isRetryWhenInterrupt:" + this.b.b());
                if (isConnect || !this.b.b()) {
                    BleC075Service.CommonBleCallback commonBleCallback = this.a;
                    if (commonBleCallback != null) {
                        Exception exc = new Exception(exception.getDescription());
                        f fVar = this.b;
                        commonBleCallback.onWriteFailure(exc, fVar.g, fVar.a, statusCode);
                    }
                    e.c(e.this, "write.onWriteFailure");
                    return;
                }
                e.d(e.this, this.b, this.a, statusCode);
            }
        }
    }

    private void h(f msgWrap, BleC075Service.CommonBleCallback commonBleCallback) {
        Class[] clsArr = {f.class, BleC075Service.CommonBleCallback.class};
        if (!PatchProxy.proxy(new Object[]{msgWrap, commonBleCallback}, this, changeQuickRedirect, false, 6285, clsArr, Void.TYPE).isSupported) {
            g("notify");
            com.clj.fastble.a.o().D(msgWrap.b, msgWrap.e.toString(), msgWrap.f.toString(), new C0125e(msgWrap, commonBleCallback));
        }
    }

    /* renamed from: com.leedarson.serviceimpl.ble.manager.e$e  reason: collision with other inner class name */
    /* compiled from: MessagePool */
    public class C0125e extends com.clj.fastble.callback.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f a;
        final /* synthetic */ BleC075Service.CommonBleCallback b;

        C0125e(f fVar, BleC075Service.CommonBleCallback commonBleCallback) {
            this.a = fVar;
            this.b = commonBleCallback;
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6305, new Class[0], Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "notify success mac:" + this.a.a);
                BleC075Service.CommonBleCallback commonBleCallback = this.b;
                if (commonBleCallback != null) {
                    f fVar = this.a;
                    commonBleCallback.onNotifySuccess(fVar.g, fVar.a);
                }
                e.c(e.this, "notify.onNotifySuccess");
            }
        }

        public void b(com.clj.fastble.exception.a exception) {
            if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6306, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "notify fail mac:" + this.a.a + exception.getDescription());
                int statusCode = exception instanceof com.clj.fastble.exception.c ? ((com.clj.fastble.exception.c) exception).getGattStatus() : BleC075ServiceImpl.e;
                BleC075Service.CommonBleCallback commonBleCallback = this.b;
                if (commonBleCallback != null) {
                    Exception exc = new Exception(exception.getDescription());
                    f fVar = this.a;
                    commonBleCallback.onNotifyFail(exc, fVar.g, fVar.a, statusCode);
                }
                e.c(e.this, "notify.onNotifyFailure");
            }
        }

        public void a(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6307, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.b(eVar, "notify recv mac:" + this.a.a + ", " + h.b(data));
                BleC075Service.CommonBleCallback commonBleCallback = this.b;
                if (commonBleCallback != null) {
                    f fVar = this.a;
                    commonBleCallback.onCharacteristicChanged(data, fVar.g, fVar.a);
                }
                e.c(e.this, "notify.onCharacteristicChanged");
            }
        }
    }

    /* compiled from: MessagePool */
    public static class f {
        public static ChangeQuickRedirect changeQuickRedirect;
        public String a;
        public BleDevice b;
        public byte[] c;
        public String d;
        public UUID e;
        public UUID f;
        public String g;
        public BleC075Service.CommonBleCallback h;
        public int i;
        private boolean j = false;
        public long k = -1;

        public f(String mac, BleDevice fastBleDevice, byte[] data, String encryptKey, UUID serviceUUID, UUID characterUUID, String jsbridgeCallbackKey, BleC075Service.CommonBleCallback callback, int type, boolean retryWhenInterrupt, long waitTime_ms) {
            this.a = mac;
            this.b = fastBleDevice;
            this.c = data;
            this.d = encryptKey;
            this.e = serviceUUID;
            this.f = characterUUID;
            this.g = jsbridgeCallbackKey;
            this.h = callback;
            this.i = type;
            this.j = retryWhenInterrupt;
            this.k = waitTime_ms;
        }

        public void a() {
            this.j = false;
        }

        public boolean b() {
            return this.j;
        }
    }

    private void g(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6286, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("BleC075ServiceImpl#Pool");
            g.m("MessagePool=" + msg, new Object[0]);
        }
    }
}

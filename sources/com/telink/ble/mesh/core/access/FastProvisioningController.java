package com.telink.ble.mesh.core.access;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.work.PeriodicWorkRequest;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.fastpv.MeshAddressStatusMessage;
import com.telink.ble.mesh.core.message.fastpv.MeshConfirmRequestMessage;
import com.telink.ble.mesh.core.message.fastpv.MeshGetAddressMessage;
import com.telink.ble.mesh.core.message.fastpv.MeshProvisionCompleteMessage;
import com.telink.ble.mesh.core.message.fastpv.MeshSetAddressMessage;
import com.telink.ble.mesh.core.message.fastpv.MeshSetNetInfoMessage;
import com.telink.ble.mesh.core.message.fastpv.ResetNetworkMessage;
import com.telink.ble.mesh.entity.FastProvisioningConfiguration;
import com.telink.ble.mesh.entity.FastProvisioningDevice;
import com.telink.ble.mesh.foundation.MeshConfiguration;
import com.telink.ble.mesh.util.MeshLogger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import meshsdk.MeshLog;
import meshsdk.ctrl.GroupCtrlAdapter;

public class FastProvisioningController {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "FastProv";
    private int b;
    private Handler c;
    private AccessBridge d;
    private FastProvisioningConfiguration e;
    /* access modifiers changed from: private */
    public ArrayList<FastProvisioningDevice> f = new ArrayList<>();
    /* access modifiers changed from: private */
    public int g;
    private MeshConfiguration h;
    /* access modifiers changed from: private */
    public boolean i = false;
    /* access modifiers changed from: private */
    public int j = 0;
    public String k = "";
    private Runnable l = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12183, new Class[0], Void.TYPE).isSupported) {
                FastProvisioningController.a(FastProvisioningController.this, false, "fast provision timeout");
            }
        }
    };
    private Runnable m = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12184, new Class[0], Void.TYPE).isSupported) {
                FastProvisioningController.b(FastProvisioningController.this);
                FastProvisioningController.c(FastProvisioningController.this);
            }
        }
    };
    private Runnable n = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12185, new Class[0], Void.TYPE).isSupported) {
                if (FastProvisioningController.this.f.size() > 0) {
                    int unused = FastProvisioningController.this.g = -1;
                    FastProvisioningController.f(FastProvisioningController.this);
                    return;
                }
                FastProvisioningController.a(FastProvisioningController.this, false, "no device found");
            }
        }
    };
    private Runnable o = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12186, new Class[0], Void.TYPE).isSupported) {
                if (FastProvisioningController.this.i) {
                    FastProvisioningController.i(FastProvisioningController.this);
                    if (FastProvisioningController.this.j > 5) {
                        FastProvisioningController.a(FastProvisioningController.this, false, "confirm check retry max");
                    } else {
                        FastProvisioningController.j(FastProvisioningController.this);
                    }
                } else {
                    FastProvisioningController.a(FastProvisioningController.this, true, "confirm check success");
                }
            }
        }
    };

    static /* synthetic */ void a(FastProvisioningController x0, boolean x1, String x2) {
        Object[] objArr = {x0, new Byte(x1 ? (byte) 1 : 0), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12177, new Class[]{FastProvisioningController.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.t(x1, x2);
        }
    }

    static /* synthetic */ void b(FastProvisioningController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12178, new Class[]{FastProvisioningController.class}, Void.TYPE).isSupported) {
            x0.A();
        }
    }

    static /* synthetic */ void c(FastProvisioningController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12179, new Class[]{FastProvisioningController.class}, Void.TYPE).isSupported) {
            x0.F();
        }
    }

    static /* synthetic */ void f(FastProvisioningController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12180, new Class[]{FastProvisioningController.class}, Void.TYPE).isSupported) {
            x0.E();
        }
    }

    static /* synthetic */ int i(FastProvisioningController x0) {
        int i2 = x0.j;
        x0.j = i2 + 1;
        return i2;
    }

    static /* synthetic */ void j(FastProvisioningController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12181, new Class[]{FastProvisioningController.class}, Void.TYPE).isSupported) {
            x0.D();
        }
    }

    static /* synthetic */ void k(FastProvisioningController x0, int x1, String x2, Object x3) {
        Object[] objArr = {x0, new Integer(x1), x2, x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12182, new Class[]{FastProvisioningController.class, Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
            x0.x(x1, x2, x3);
        }
    }

    public FastProvisioningController(HandlerThread handlerThread) {
        this.c = new Handler(handlerThread.getLooper());
    }

    public void y(AccessBridge accessBridge) {
        this.d = accessBridge;
    }

    public FastProvisioningConfiguration n() {
        return this.e;
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12157, new Class[0], Void.TYPE).isSupported) {
            r("begin");
            this.c.removeCallbacks(this.l);
            this.c.postDelayed(this.l, PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS);
            z();
        }
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12158, new Class[0], Void.TYPE).isSupported) {
            this.b = 16;
            Handler handler = this.c;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            this.f.clear();
        }
    }

    private void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12160, new Class[0], Void.TYPE).isSupported) {
            if (v(ResetNetworkMessage.I(65535, this.h.b(), this.e.h()))) {
                x(17, "reset provisioner network", (Object) null);
                this.c.removeCallbacks(this.m);
                this.c.postDelayed(this.m, (long) (this.e.h() + 500));
                return;
            }
            t(false, "reset command send err");
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12161, new Class[0], Void.TYPE).isSupported) {
            x(18, "com.telink.ble.mesh get address", (Object) null);
            v(MeshGetAddressMessage.I(65535, this.e.b(), 10, this.e.i()));
        }
    }

    private void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12162, new Class[0], Void.TYPE).isSupported) {
            this.c.removeCallbacks(this.n);
            this.c.postDelayed(this.n, 3500);
        }
    }

    private void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12163, new Class[0], Void.TYPE).isSupported) {
            this.g++;
            int size = this.f.size();
            int i2 = this.g;
            if (size > i2) {
                FastProvisioningDevice provisioningDevice = this.f.get(i2);
                if (provisioningDevice != null) {
                    r(String.format(Locale.US, "com.telink.ble.mesh set next address: mac -- %s originAddress -- %04X newAddress -- %04X index -- %02d", new Object[]{e.a(provisioningDevice.b()), Integer.valueOf(provisioningDevice.d()), Integer.valueOf(provisioningDevice.c()), Integer.valueOf(this.g)}));
                    x(19, "com.telink.ble.mesh set address", provisioningDevice);
                    v(MeshSetAddressMessage.I(provisioningDevice.d(), this.e.b(), e.h(provisioningDevice.b()), provisioningDevice.c()));
                    return;
                }
                r("provisioning device not found");
                return;
            }
            r("all device set address complete");
            this.j = 0;
            D();
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12164, new Class[0], Void.TYPE).isSupported) {
            x(20, "com.telink.ble.mesh set net info", (Object) null);
            v(MeshSetNetInfoMessage.I(65535, this.e.b(), o()));
            C();
        }
    }

    private void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12165, new Class[0], Void.TYPE).isSupported) {
            x(21, "fast provision confirming", (Object) null);
            this.i = false;
            v(MeshConfirmRequestMessage.I(65535, this.e.b()));
            this.c.postDelayed(this.o, GroupCtrlAdapter.RETRY_TIMEOUT);
        }
    }

    private void t(final boolean success, String desc) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12166, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            r("complete: " + desc + " success?" + success);
            B();
            this.c.postDelayed(new Runnable() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12187, new Class[0], Void.TYPE).isSupported) {
                        FastProvisioningController.this.m();
                        FastProvisioningController.k(FastProvisioningController.this, success ? 25 : 24, "fast provision complete", (Object) null);
                    }
                }
            }, (long) (this.e.h() + 500));
        }
    }

    private void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12167, new Class[0], Void.TYPE).isSupported) {
            v(MeshProvisionCompleteMessage.I(65535, this.e.b(), this.e.h()));
        }
    }

    private boolean v(MeshMessage meshMessage) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshMessage}, this, changeQuickRedirect, false, 12168, new Class[]{MeshMessage.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        r("com.telink.ble.mesh message prepared: " + meshMessage.getClass().getSimpleName() + String.format(Locale.US, " opcode: 0x%04X -- dst: 0x%04X", new Object[]{Integer.valueOf(meshMessage.k()), Integer.valueOf(meshMessage.j())}));
        AccessBridge accessBridge = this.d;
        if (accessBridge == null) {
            return false;
        }
        boolean isMessageSent = accessBridge.j(meshMessage, 2);
        if (!isMessageSent) {
            r("message send error");
        }
        return isMessageSent;
    }

    private int q(int pid) {
        Object[] objArr = {new Integer(pid)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12169, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int elementCount = this.e.e(pid);
        if (elementCount != 0) {
            int address = this.e.g();
            if (MeshUtils.r(address)) {
                this.e.j(elementCount);
                return address;
            }
            s("invalid address", 3);
        } else {
            s("pid not found", 3);
        }
        return 0;
    }

    public void u(boolean success, int opcode, int i2, int i3) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), new Integer(opcode), new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12170, new Class[]{Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            if (opcode == Opcode.VD_MESH_ADDR_SET.value && this.b == 19 && !success) {
                E();
            }
        }
    }

    public void w(NotificationMessage message) {
        FastProvisioningDevice device;
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 12171, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            Opcode opcode = Opcode.valueOf(message.a());
            r("message notification: " + opcode);
            if (opcode != null) {
                switch (AnonymousClass6.a[opcode.ordinal()]) {
                    case 1:
                        if (this.b == 18) {
                            MeshAddressStatusMessage statusMessage = (MeshAddressStatusMessage) message.d();
                            int originAddress = message.c();
                            int pid = statusMessage.d();
                            r("device address notify: " + e.a(statusMessage.c()));
                            int newAddress = q(pid);
                            if (newAddress != 0) {
                                FastProvisioningDevice fastProvisioningDevice = new FastProvisioningDevice(originAddress, newAddress, pid, this.e.e(pid), statusMessage.c());
                                if (!this.f.contains(fastProvisioningDevice)) {
                                    this.f.add(fastProvisioningDevice);
                                    A();
                                    return;
                                }
                                r("provisioning device exists: " + e.a(statusMessage.c()));
                                return;
                            }
                            return;
                        }
                        return;
                    case 2:
                        if (this.b == 19 && (device = p(message.c())) != null) {
                            x(22, "device set address success", device);
                            E();
                            return;
                        }
                        return;
                    case 3:
                        if (this.b == 21) {
                            this.i = true;
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* renamed from: com.telink.ble.mesh.core.access.FastProvisioningController$6  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Opcode.values().length];
            a = iArr;
            try {
                iArr[Opcode.VD_MESH_ADDR_GET_STS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Opcode.VD_MESH_ADDR_SET_STS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Opcode.VD_MESH_PROV_CONFIRM_STS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private FastProvisioningDevice p(int meshAddress) {
        Object[] objArr = {new Integer(meshAddress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12172, new Class[]{Integer.TYPE}, FastProvisioningDevice.class);
        if (proxy.isSupported) {
            return (FastProvisioningDevice) proxy.result;
        }
        Iterator<FastProvisioningDevice> it = this.f.iterator();
        while (it.hasNext()) {
            FastProvisioningDevice device = it.next();
            if (device.d() == meshAddress) {
                return device;
            }
        }
        return null;
    }

    private void x(int state, String desc, Object obj) {
        Object[] objArr = {new Integer(state), desc, obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12173, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
            this.b = state;
            AccessBridge accessBridge = this.d;
            if (accessBridge != null) {
                accessBridge.g(state, desc, 4, obj);
            }
        }
    }

    private void r(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12174, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i(logMessage + "  FastProvisioningController.macAddress=" + this.k);
        }
    }

    private void s(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 12175, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage + "  FastProvisioningController.macAddress=" + this.k, "FastProv", level);
        }
    }

    public byte[] o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12176, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        MeshConfiguration meshConfiguration = this.h;
        byte[] netKey = meshConfiguration.b;
        int netKeyIndex = meshConfiguration.a;
        int appKeyIndex = meshConfiguration.b();
        byte[] appKey = this.h.a();
        if (appKey != null) {
            int ivIndex = this.h.d;
            byte[] pvData = new byte[25];
            System.arraycopy(netKey, 0, pvData, 0, 16);
            pvData[16] = (byte) (netKeyIndex & 255);
            pvData[17] = (byte) ((netKeyIndex >> 8) & 255);
            pvData[18] = 0;
            pvData[19] = (byte) ((ivIndex >> 24) & 255);
            pvData[20] = (byte) ((ivIndex >> 16) & 255);
            pvData[21] = (byte) ((ivIndex >> 8) & 255);
            pvData[22] = (byte) (ivIndex & 255);
            ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
            return ByteBuffer.allocate(44).order(byteOrder).put(pvData).put(MeshUtils.l((netKeyIndex & 4095) | ((appKeyIndex & 4095) << 12), 3, byteOrder)).put(appKey).array();
        }
        throw new RuntimeException("app key not found!");
    }
}

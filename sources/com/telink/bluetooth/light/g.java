package com.telink.bluetooth.light;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.c;
import com.telink.util.f;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* compiled from: LightPeripheral */
public class g extends c {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected final Map<UUID, byte[]> A = new HashMap();
    public boolean B;
    private byte[] C;
    private byte[] D;
    private byte[] E;
    private int F;
    private a G;
    private String H;
    private int I = -1;
    private int J = 0;
    protected final Map<String, Object> z = new HashMap();

    /* compiled from: LightPeripheral */
    public interface a {
        void a(g gVar);

        void b(g gVar);

        void c(g gVar);

        void d(g gVar, List<BluetoothGattService> list);

        void e(g gVar, byte[] bArr, UUID uuid, UUID uuid2, Object obj);
    }

    public g(BluetoothDevice device, byte[] scanRecord, int rssi, byte[] meshName, int meshAddress) {
        super(device, scanRecord, rssi);
        Z(meshName);
        Y(meshAddress);
    }

    public byte[] P() {
        return this.C;
    }

    public void Z(byte[] value) {
        if (!PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 13757, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            this.H = f.a(value);
            this.C = value;
        }
    }

    public String Q() {
        return this.H;
    }

    public void a0(byte[] password) {
        this.D = password;
    }

    public void X(byte[] value) {
        this.E = value;
    }

    public int O() {
        return this.F;
    }

    public void Y(int value) {
        this.F = value;
        this.I = value;
    }

    public int T() {
        return this.I;
    }

    public int R() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13758, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : K("com.telink.bluetooth.light.ADV_MESH_UUID");
    }

    public int U() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13759, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : K("com.telink.bluetooth.light.ADV_PRODUCT_UUID");
    }

    public void V(String key, Object value) {
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 13761, clsArr, Void.TYPE).isSupported) {
            this.z.put(key, value);
        }
    }

    public int K(String key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 13764, new Class[]{String.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : ((Integer) this.z.get(key)).intValue();
    }

    public void W(UUID characteristicUUID, byte[] value) {
        Class[] clsArr = {UUID.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{characteristicUUID, value}, this, changeQuickRedirect, false, 13767, clsArr, Void.TYPE).isSupported) {
            this.A.put(characteristicUUID, value);
        }
    }

    public byte[] L(UUID characteristicUUID) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{characteristicUUID}, this, changeQuickRedirect, false, 13768, new Class[]{UUID.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (this.A.containsKey(characteristicUUID)) {
            return this.A.get(characteristicUUID);
        }
        return null;
    }

    public String M(UUID characteristicUUID) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{characteristicUUID}, this, changeQuickRedirect, false, 13769, new Class[]{UUID.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        byte[] value = L(characteristicUUID);
        if (value != null) {
            return new String(value);
        }
        return null;
    }

    public String N() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13770, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Log.e("chongbinwei", "getFirmwareRevision");
        return M(k.CHARACTERISTIC_FIRMWARE.getValue());
    }

    public String S() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13771, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Log.e("chongbinwei", "getFirmwareRevision");
        String firmwareRevision = M(k.NEW_CHARACTERISTIC_FIRMWARE.getValue());
        if (TextUtils.isEmpty(firmwareRevision)) {
            return firmwareRevision;
        }
        return firmwareRevision.substring(0, firmwareRevision.length() - 1);
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13773, new Class[0], Void.TYPE).isSupported) {
            super.m();
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13776, new Class[0], Void.TYPE).isSupported) {
            super.x();
            a aVar = this.G;
            if (aVar != null) {
                aVar.b(this);
            }
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13777, new Class[0], Void.TYPE).isSupported) {
            super.y();
            a aVar = this.G;
            if (aVar != null) {
                aVar.c(this);
            }
        }
    }

    public void B(List<BluetoothGattService> services) {
        if (!PatchProxy.proxy(new Object[]{services}, this, changeQuickRedirect, false, 13778, new Class[]{List.class}, Void.TYPE).isSupported) {
            super.B(services);
            a aVar = this.G;
            if (aVar != null) {
                aVar.d(this, services);
            }
        }
    }

    public void z(byte[] data, UUID serviceUUID, UUID characteristicUUID, Object tag) {
        if (!PatchProxy.proxy(new Object[]{data, serviceUUID, characteristicUUID, tag}, this, changeQuickRedirect, false, 13779, new Class[]{byte[].class, UUID.class, UUID.class, Object.class}, Void.TYPE).isSupported) {
            super.z(data, serviceUUID, characteristicUUID, tag);
            a aVar = this.G;
            if (aVar != null) {
                aVar.e(this, data, serviceUUID, characteristicUUID, tag);
            }
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13780, new Class[0], Void.TYPE).isSupported) {
            super.A();
            a aVar = this.G;
            if (aVar != null) {
                aVar.a(this);
            }
        }
    }
}

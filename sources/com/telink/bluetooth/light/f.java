package com.telink.bluetooth.light;

import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.a;
import com.telink.bluetooth.light.g;
import com.telink.bluetooth.light.h;
import com.telink.crypto.AES;
import com.telink.util.c;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: LightController */
public final class f extends com.telink.util.d<Integer> implements g.a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final a.C0214a A = new e();
    private final a.C0214a B = new i();
    private final a.C0214a C = new C0220f();
    private final m D = new m();
    /* access modifiers changed from: private */
    public g E;
    /* access modifiers changed from: private */
    public byte[] F;
    private int G = Integer.MAX_VALUE;
    private Random H = new SecureRandom();
    /* access modifiers changed from: private */
    public AtomicBoolean I = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public byte[] J;
    /* access modifiers changed from: private */
    public byte[] K;
    /* access modifiers changed from: private */
    public byte[] L;
    /* access modifiers changed from: private */
    public byte[] M;
    /* access modifiers changed from: private */
    public byte[] N;
    /* access modifiers changed from: private */
    public byte[] O;
    private Context P;
    private int Q = 15;
    private AtomicBoolean R = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public AtomicBoolean S = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public boolean T = false;
    private int U = 0;
    /* access modifiers changed from: private */
    public final byte[] n = new byte[8];
    private final byte[] o = new byte[8];
    /* access modifiers changed from: private */
    public final Handler p = new Handler();
    /* access modifiers changed from: private */
    public final Runnable q = new c();
    private final Runnable r = new p();
    /* access modifiers changed from: private */
    public final Runnable s = new n();
    private final a.C0214a t = new h();
    private final a.C0214a u = new o();
    private final a.C0214a v = new k();
    private final Runnable w = new b();
    private final a.C0214a x = new j();
    private final a.C0214a y = new d();
    private final a.C0214a z = new l();

    static /* synthetic */ boolean E(f x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13723, new Class[]{f.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.j0();
    }

    static /* synthetic */ boolean F(f x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13724, new Class[]{f.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.g0();
    }

    static /* synthetic */ void H(f x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13725, new Class[]{f.class}, Void.TYPE).isSupported) {
            x0.i0();
        }
    }

    static /* synthetic */ void I(f x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13726, new Class[]{f.class}, Void.TYPE).isSupported) {
            x0.a0();
        }
    }

    static /* synthetic */ void J(f x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13727, new Class[]{f.class}, Void.TYPE).isSupported) {
            x0.h0();
        }
    }

    static /* synthetic */ byte[] v(f x0, byte[] x1, byte[] x2, byte[] x3, byte[] x4, byte[] x5) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1, x2, x3, x4, x5}, (Object) null, changeQuickRedirect, true, 13722, new Class[]{f.class, cls, cls, cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return x0.R(x1, x2, x3, x4, x5);
    }

    public void h(com.telink.util.c<Integer> event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13684, new Class[]{com.telink.util.c.class}, Void.TYPE).isSupported) {
            super.h(event.c(c.a.Background));
        }
    }

    public g N() {
        return this.E;
    }

    public boolean S() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13685, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.I.get();
    }

    public synchronized void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13687, new Class[0], Void.TYPE).isSupported) {
            this.I.set(false);
            this.p.removeCallbacks(this.q);
            this.p.removeCallbacksAndMessages((Object) null);
            a0();
            if (this.E != null) {
                com.telink.bluetooth.d.a("LightController.disconnect:" + this.E.t() + "--" + this.E.u());
                this.E.m();
            }
            this.F = null;
            this.G = 0;
            this.J = null;
            this.K = null;
            this.N = null;
            this.O = null;
            this.P = null;
        }
    }

    public void Y(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] longTermKey;
        Class<byte[]> cls = byte[].class;
        if (!PatchProxy.proxy(new Object[]{bArr, bArr2, bArr3}, this, changeQuickRedirect, false, 13689, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
            byte[] password = bArr2;
            byte[] meshName = bArr;
            byte[] longTermKey2 = bArr3;
            com.telink.bluetooth.d.a("prepare update mesh info");
            if (!this.I.get()) {
                h(new g(11, "not login"));
                return;
            }
            this.E.B = false;
            this.N = meshName;
            this.O = password;
            if (longTermKey2 == null) {
                longTermKey = h.a().b();
            } else {
                longTermKey = longTermKey2;
            }
            this.M = longTermKey;
            if (!Z()) {
                this.p.removeCallbacksAndMessages((Object) null);
                try {
                    byte[] nn = AES.b(this.F, meshName);
                    byte[] pwd = AES.b(this.F, password);
                    byte[] ltk = AES.b(this.F, longTermKey);
                    com.telink.util.a.e(nn, 0, nn.length - 1);
                    com.telink.util.a.e(pwd, 0, pwd.length - 1);
                    com.telink.util.a.e(ltk, 0, ltk.length - 1);
                    byte[] nnData = new byte[17];
                    nnData[0] = i.BLE_GATT_OP_PAIR_NETWORK_NAME.getValue();
                    System.arraycopy(nn, 0, nnData, 1, nn.length);
                    byte[] pwdData = new byte[17];
                    pwdData[0] = i.BLE_GATT_OP_PAIR_PASS.getValue();
                    System.arraycopy(pwd, 0, pwdData, 1, pwd.length);
                    byte[] ltkData = new byte[17];
                    ltkData[0] = i.BLE_GATT_OP_PAIR_LTK.getValue();
                    System.arraycopy(ltk, 0, ltkData, 1, ltk.length);
                    h manufacture = h.a();
                    UUID serviceUUID = manufacture.e(h.c.SERVICE);
                    UUID pairUUID = manufacture.e(h.c.PAIR);
                    a.b bVar = a.b.WRITE;
                    com.telink.bluetooth.a nnCmd = new com.telink.bluetooth.a(serviceUUID, pairUUID, bVar, nnData, 101);
                    com.telink.bluetooth.a pwdCmd = new com.telink.bluetooth.a(serviceUUID, pairUUID, bVar, pwdData, 102);
                    UUID uuid = serviceUUID;
                    UUID uuid2 = pairUUID;
                    com.telink.bluetooth.a aVar = new com.telink.bluetooth.a(uuid, uuid2, bVar, ltkData, 103);
                    com.telink.bluetooth.a aVar2 = new com.telink.bluetooth.a(uuid, uuid2, a.b.READ, (byte[]) null, 104);
                    e0(this.u, nnCmd);
                    pwdCmd.f = 200;
                    e0(this.u, pwdCmd);
                    aVar.f = 200;
                    e0(this.u, aVar);
                    aVar2.f = 200;
                    e0(this.u, aVar2);
                } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e2) {
                    h(new g(11, e2.getMessage()));
                }
            }
        }
    }

    public boolean Z() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13690, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int newAddress = this.E.T();
        int oldAddress = this.E.O();
        com.telink.bluetooth.d.a("mesh address -->" + newAddress + " : " + oldAddress);
        if (newAddress == oldAddress) {
            return false;
        }
        L(this.v, 107);
        com.telink.bluetooth.d.a("prepare update mesh address -->" + this.E.u() + " src : " + Integer.toHexString(oldAddress) + " new : " + Integer.toHexString(newAddress));
        d0(this.x, (byte) -32, 0, new byte[]{(byte) (newAddress & 255), (byte) ((newAddress >> 8) & 255)}, true, 105, 0);
        this.p.postDelayed(this.r, GroupCtrlAdapter.RETRY_TIMEOUT);
        return true;
    }

    private void L(a.C0214a callback, Object tag) {
        Class[] clsArr = {a.C0214a.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{callback, tag}, this, changeQuickRedirect, false, 13691, clsArr, Void.TYPE).isSupported) {
            if (this.I.get()) {
                h manufacture = h.a();
                UUID serviceUUID = manufacture.e(h.c.SERVICE);
                UUID characteristicUUID = manufacture.e(h.c.NOTIFY);
                com.telink.bluetooth.a enableNotifyCmd = com.telink.bluetooth.a.a();
                enableNotifyCmd.c = a.b.ENABLE_NOTIFY;
                enableNotifyCmd.a = serviceUUID;
                enableNotifyCmd.b = characteristicUUID;
                enableNotifyCmd.e = tag;
                e0(callback, enableNotifyCmd);
            }
        }
    }

    public int O() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13698, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.D.j();
    }

    private void a0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13699, new Class[0], Void.TYPE).isSupported) {
            this.p.removeCallbacksAndMessages((Object) null);
            this.p.removeCallbacks(this.s);
            this.D.b();
        }
    }

    private void i0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13700, new Class[0], Void.TYPE).isSupported) {
            if (m.a(this.D)) {
                h(new g(73));
            }
        }
    }

    private boolean g0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13701, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean isLast = false;
        h manufacture = h.a();
        UUID serviceUUID = manufacture.e(h.c.SERVICE);
        UUID characteristicUUID = manufacture.e(h.c.OTA);
        com.telink.bluetooth.a cmd = com.telink.bluetooth.a.a();
        cmd.a = serviceUUID;
        cmd.b = characteristicUUID;
        cmd.c = a.b.WRITE_NO_RESPONSE;
        if (this.D.k()) {
            cmd.d = this.D.g();
            cmd.e = Integer.valueOf(TypedValues.PositionType.TYPE_TRANSITION_EASING);
        } else {
            cmd.d = this.D.f();
            cmd.e = Integer.valueOf(TypedValues.PositionType.TYPE_DRAWPATH);
            isLast = true;
        }
        e0(this.z, cmd);
        return isLast;
    }

    private boolean j0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13702, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int sectionSize = h.a().d();
        int sendTotal = this.D.h() * 16;
        com.telink.bluetooth.d.a("ota onCommandSampled byte length : " + sendTotal);
        if (sendTotal <= 0 || sendTotal % sectionSize != 0) {
            return false;
        }
        com.telink.bluetooth.d.a("onCommandSampled ota read packet " + this.D.h());
        h manufacture = h.a();
        UUID serviceUUID = manufacture.e(h.c.SERVICE);
        UUID characteristicUUID = manufacture.e(h.c.OTA);
        com.telink.bluetooth.a cmd = com.telink.bluetooth.a.a();
        cmd.a = serviceUUID;
        cmd.b = characteristicUUID;
        cmd.c = a.b.READ;
        cmd.e = Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_WIDTH);
        e0(this.z, cmd);
        return true;
    }

    private void h0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13703, new Class[0], Void.TYPE).isSupported) {
            h manufacture = h.a();
            UUID serviceUUID = manufacture.e(h.c.SERVICE);
            UUID characteristicUUID = manufacture.e(h.c.OTA);
            com.telink.bluetooth.a cmd = com.telink.bluetooth.a.a();
            cmd.a = serviceUUID;
            cmd.b = characteristicUUID;
            cmd.c = a.b.READ;
            cmd.e = Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_HEIGHT);
            cmd.f = 0;
            e0(this.z, cmd);
        }
    }

    private boolean f0(a.C0214a aVar, byte[] bArr, boolean z2, Object obj, int i2) {
        Object[] objArr = {aVar, bArr, new Byte(z2 ? (byte) 1 : 0), obj, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13704, new Class[]{a.C0214a.class, byte[].class, cls, Object.class, Integer.TYPE}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] commandData = bArr;
        Object tag = obj;
        a.C0214a callback = aVar;
        boolean noResponse = z2;
        int delay = i2;
        byte[] sk = this.F;
        int sn = this.G;
        com.telink.bluetooth.d.a("LightController#sendCommand#NoEncrypt: " + com.telink.util.a.a(commandData, ":"));
        byte[] data = AES.c(sk, P(this.E.v(), sn), commandData);
        h manufacture = h.a();
        UUID serviceUUID = manufacture.e(h.c.SERVICE);
        UUID characteristicUUID = manufacture.e(h.c.COMMAND);
        com.telink.bluetooth.a command = com.telink.bluetooth.a.a();
        command.c = noResponse ? a.b.WRITE_NO_RESPONSE : a.b.WRITE;
        command.d = data;
        command.a = serviceUUID;
        command.b = characteristicUUID;
        command.e = tag;
        command.f = delay;
        return e0(callback, command);
    }

    private boolean e0(a.C0214a callback, com.telink.bluetooth.a cmd) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback, cmd}, this, changeQuickRedirect2, false, 13705, new Class[]{a.C0214a.class, com.telink.bluetooth.a.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean success = true;
        if (!this.I.get()) {
            success = false;
        }
        this.E.H(callback, cmd);
        return success;
    }

    public boolean d0(a.C0214a aVar, byte opcode, int i2, byte[] bArr, boolean z2, Object obj, int i3) {
        Object[] objArr = {aVar, new Byte(opcode), new Integer(i2), bArr, new Byte(z2 ? (byte) 1 : 0), obj, new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13706, new Class[]{a.C0214a.class, Byte.TYPE, cls, byte[].class, cls2, Object.class, cls}, cls2);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Object tag = obj;
        byte[] params = bArr;
        a.C0214a callback = aVar;
        int address = i2;
        int delay = i3;
        boolean noResponse = z2;
        int sn = M();
        byte[] command = new byte[20];
        int offset = 0 + 1;
        command[0] = (byte) (sn & 255);
        int offset2 = offset + 1;
        command[offset] = (byte) ((sn >> 8) & 255);
        int offset3 = offset2 + 1;
        command[offset2] = (byte) ((sn >> 16) & 255);
        int offset4 = offset3 + 1;
        command[offset3] = 0;
        int offset5 = offset4 + 1;
        command[offset4] = 0;
        int offset6 = offset5 + 1;
        command[offset5] = (byte) (address & 255);
        int offset7 = offset6 + 1;
        command[offset6] = (byte) ((address >> 8) & 255);
        int offset8 = offset7 + 1;
        command[offset7] = (byte) (opcode | 192);
        int vendorId = h.a().g();
        int offset9 = offset8 + 1;
        command[offset8] = (byte) ((vendorId >> 8) & 255);
        int offset10 = offset9 + 1;
        command[offset9] = (byte) (vendorId & 255);
        if (params != null) {
            System.arraycopy(params, 0, command, offset10, params.length);
        }
        return f0(callback, command, noResponse, tag, delay);
    }

    public boolean b0(byte opcode, int address, byte[] params, boolean noResponse, int delay) {
        Object[] objArr = {new Byte(opcode), new Integer(address), params, new Byte(noResponse ? (byte) 1 : 0), new Integer(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13707, new Class[]{Byte.TYPE, cls, byte[].class, cls2, cls}, cls2);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return c0(opcode, address, params, noResponse, 1000, delay);
    }

    public boolean c0(byte b2, int address, byte[] bArr, boolean z2, Object obj, int delay) {
        byte b3 = b2;
        Object[] objArr = {new Byte(b2), new Integer(address), bArr, new Byte(z2 ? (byte) 1 : 0), obj, new Integer(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13708, new Class[]{Byte.TYPE, cls, byte[].class, cls2, Object.class, cls}, cls2);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean noResponse = z2;
        byte opcode = b2;
        byte[] params = bArr;
        Object tag = obj;
        return d0(this.x, opcode, address, params, noResponse, tag, delay);
    }

    public boolean V() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13709, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.e("tel1", "requestFirmware light.getFirmwareRevision() = " + this.E.N());
        UUID serviceUUID = k.SERVICE_DEVICE_INFORMATION.getValue();
        UUID characteristicUUID = k.CHARACTERISTIC_FIRMWARE.getValue();
        com.telink.bluetooth.a cmd = com.telink.bluetooth.a.a();
        cmd.a = serviceUUID;
        cmd.b = characteristicUUID;
        cmd.c = a.b.READ;
        return e0(this.A, cmd);
    }

    public boolean X() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13710, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.e("tel1", "requestNewFirmware light.getFirmwareRevision() = " + this.E.N());
        UUID serviceUUID = k.NEW_SERVICE_DEVICE_INFORMATION.getValue();
        UUID characteristicUUID = k.NEW_CHARACTERISTIC_FIRMWARE.getValue();
        com.telink.bluetooth.a cmd = com.telink.bluetooth.a.a();
        cmd.a = serviceUUID;
        cmd.b = characteristicUUID;
        cmd.c = a.b.READ;
        return e0(this.B, cmd);
    }

    public boolean W() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13711, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.e("tel1", " requestFirmwareID  ");
        UUID serviceUUID = k.SERVICE_DEVICEFW_INFORMATION.getValue();
        UUID characteristicUUID = k.CHARACTERISTICFW_FIRMWARE.getValue();
        com.telink.bluetooth.a cmd = com.telink.bluetooth.a.a();
        cmd.a = serviceUUID;
        cmd.b = characteristicUUID;
        cmd.c = a.b.READ;
        return e0(this.C, cmd);
    }

    private int M() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13712, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.G > 16777215) {
            this.G = Math.round(((float) Math.random()) * ((float) (ViewCompat.MEASURED_SIZE_MASK - 1))) + 1;
        }
        int i2 = this.G + 1;
        this.G = i2;
        return i2;
    }

    private byte[] P(byte[] meshAddress, int sn) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshAddress, new Integer(sn)}, this, changeQuickRedirect, false, 13714, new Class[]{byte[].class, Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] ivm = new byte[8];
        System.arraycopy(meshAddress, 0, ivm, 0, meshAddress.length);
        ivm[4] = 1;
        ivm[5] = (byte) (sn & 255);
        ivm[6] = (byte) ((sn >> 8) & 255);
        ivm[7] = (byte) ((sn >> 16) & 255);
        return ivm;
    }

    private byte[] Q(byte[] macAddress) {
        byte[] ivs = new byte[8];
        ivs[0] = macAddress[0];
        ivs[1] = macAddress[1];
        ivs[2] = macAddress[2];
        return ivs;
    }

    private byte[] R(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bArr, bArr2, bArr3, bArr4, bArr5}, this, changeQuickRedirect2, false, 13715, new Class[]{cls, cls, cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] password = bArr2;
        byte[] rands = bArr4;
        byte[] meshName = bArr;
        byte[] randm = bArr3;
        byte[] sk = bArr5;
        byte[] key = new byte[16];
        System.arraycopy(rands, 0, key, 0, rands.length);
        byte[] plaintext = new byte[16];
        for (int i2 = 0; i2 < 16; i2++) {
            plaintext[i2] = (byte) (meshName[i2] ^ password[i2]);
        }
        byte[] encrypted = AES.b(key, plaintext);
        byte[] result = new byte[16];
        System.arraycopy(rands, 0, result, 0, rands.length);
        System.arraycopy(encrypted, 8, result, 8, 8);
        com.telink.util.a.e(result, 8, 15);
        if (!com.telink.util.a.c(result, sk)) {
            return null;
        }
        System.arraycopy(randm, 0, key, 0, randm.length);
        System.arraycopy(rands, 0, key, 8, rands.length);
        byte[] sessionKey = AES.b(plaintext, key);
        com.telink.util.a.e(sessionKey, 0, sessionKey.length - 1);
        return sessionKey;
    }

    /* compiled from: LightController */
    public final class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13728, new Class[0], Void.TYPE).isSupported) {
                if (!f.this.I.get()) {
                    com.telink.bluetooth.d.a("LightController.Connection Timeout N");
                    f.this.K();
                    boolean unused = f.this.T = true;
                }
            }
        }
    }

    public void b(g gVar) {
        if (!PatchProxy.proxy(new Object[]{gVar}, this, changeQuickRedirect, false, 13716, new Class[]{g.class}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("LightController#onConnect");
            com.telink.bluetooth.d.a("mDelayHandler#attCheckRunnable");
            this.p.postDelayed(this.w, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
            this.T = false;
            this.R.set(false);
        }
    }

    public void c(g light) {
        if (!PatchProxy.proxy(new Object[]{light}, this, changeQuickRedirect, false, 13717, new Class[]{g.class}, Void.TYPE).isSupported) {
            com.telink.bluetooth.d.a("LightController.onDisconnect");
            K();
            if (this.T) {
                h(new g(91, " onAttError " + light.u()));
            } else if (this.R.get()) {
                h(new g(90, " onConnect " + light.u()));
                this.R.set(false);
            }
            if (T() && this.T) {
                this.T = false;
                this.p.removeCallbacks(this.w);
                this.U++;
                com.telink.bluetooth.d.a("fail count:" + this.U);
                if (this.U >= 3) {
                    com.telink.bluetooth.d.a("LightController.onDisconnect.CONNECT_FAILURE_N");
                    h(new g(5, " onDisconnect " + light.u()));
                }
            }
            h(new g(4, " onDisconnect " + light.u()));
        }
    }

    public void d(g gVar, List<BluetoothGattService> services) {
        Class[] clsArr = {g.class, List.class};
        if (!PatchProxy.proxy(new Object[]{gVar, services}, this, changeQuickRedirect, false, 13718, clsArr, Void.TYPE).isSupported) {
            if (!T() || services.size() != 0) {
                this.p.removeCallbacks(this.w);
                h(new g(3));
                return;
            }
            K();
        }
    }

    public void e(g light, byte[] bArr, UUID uuid, UUID uuid2, Object tag) {
        Class[] clsArr = {g.class, byte[].class, UUID.class, UUID.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{light, bArr, uuid, uuid2, tag}, this, changeQuickRedirect, false, 13719, clsArr, Void.TYPE).isSupported) {
            byte[] data = bArr;
            UUID uuid3 = uuid2;
            UUID uuid4 = uuid;
            byte[] nonce = Q(light.v());
            System.arraycopy(data, 0, nonce, 3, 5);
            byte[] result = AES.a(this.F, nonce, data);
            com.telink.bluetooth.d.a("Notify Data --> " + com.telink.util.a.a(result, ","));
            U(data, tag);
            h(new g(22, result));
        }
    }

    private void U(byte[] data, Object tag) {
        int meshAddress;
        Class[] clsArr = {byte[].class, Object.class};
        if (!PatchProxy.proxy(new Object[]{data, tag}, this, changeQuickRedirect, false, 13720, clsArr, Void.TYPE).isSupported) {
            if (tag.equals(107) && data.length >= 20) {
                int position = 7 + 1;
                int opcode = data[7] & 255;
                int position2 = position + 1;
                int i2 = position2 + 1;
                if (((data[position] & 255) << 8) + (data[position2] & 255) == h.a().g() && opcode == 225 && (meshAddress = (data[10] & 255) + (data[11] << 8)) != this.E.O()) {
                    this.E.Y(meshAddress);
                    com.telink.bluetooth.d.a("Device Address Update Success --> old : " + Integer.toHexString(this.E.O()) + " new: " + Integer.toHexString(meshAddress));
                    Y(this.N, this.O, this.M);
                }
            }
        }
    }

    public void a(g gVar) {
        if (!PatchProxy.proxy(new Object[]{gVar}, this, changeQuickRedirect, false, 13721, new Class[]{g.class}, Void.TYPE).isSupported) {
            h(new g(60));
        }
    }

    /* compiled from: LightController */
    public final class g extends com.telink.util.c<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;
        private Object d;

        public g(Integer type, Object args) {
            super((Object) null, type);
            this.d = args;
        }

        public g(Integer type) {
            super((Object) null, type);
        }

        public Object d() {
            return this.d;
        }
    }

    /* compiled from: LightController */
    public final class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13729, new Class[0], Void.TYPE).isSupported) {
                if (!f.this.I.get()) {
                    com.telink.bluetooth.d.a("LightController.Connection Timeout");
                    f.this.K();
                    f fVar = f.this;
                    fVar.h(new g(4, "connection timeout"));
                }
            }
        }
    }

    /* compiled from: LightController */
    public final class h implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private h() {
        }

        public void a(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, Object obj) {
            if (!PatchProxy.proxy(new Object[]{cVar, command, obj}, this, changeQuickRedirect, false, 13736, new Class[]{com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class}, Void.TYPE).isSupported) {
                com.telink.bluetooth.c cVar2 = cVar;
                Object response = obj;
                if (command.e.equals(2)) {
                    byte[] data = (byte[]) response;
                    if (data[0] == i.BLE_GATT_OP_PAIR_ENC_FAIL.getValue()) {
                        f.this.K();
                        f fVar = f.this;
                        fVar.h(new g(1, "encryption is not correct"));
                        f fVar2 = f.this;
                        fVar2.h(new g(94, "LOGIN_ERROR_REPORT_CHECK"));
                        return;
                    }
                    byte[] sk = new byte[16];
                    byte[] rands = new byte[8];
                    System.arraycopy(data, 1, sk, 0, 16);
                    System.arraycopy(data, 1, rands, 0, 8);
                    try {
                        f fVar3 = f.this;
                        try {
                            byte[] unused = fVar3.F = f.v(fVar3, fVar3.J, f.this.K, f.this.n, rands, sk);
                            if (f.this.F == null) {
                                f.this.K();
                                f fVar4 = f.this;
                                fVar4.h(new g(1, "sessionKey invalid"));
                                f fVar5 = f.this;
                                fVar5.h(new g(94, "LOGIN_ERROR_REPORT_CHECK"));
                                return;
                            }
                            f.this.I.set(true);
                            f.this.p.removeCallbacks(f.this.q);
                            f.this.p.removeCallbacksAndMessages((Object) null);
                            f fVar6 = f.this;
                            fVar6.h(new g(0));
                        } catch (Exception e) {
                            e = e;
                            f.this.K();
                            f fVar7 = f.this;
                            fVar7.h(new g(1, e.getMessage()));
                            f fVar8 = f.this;
                            fVar8.h(new g(94, "LOGIN_ERROR_REPORT_CHECK"));
                        }
                    } catch (Exception e2) {
                        e = e2;
                        byte[] bArr = rands;
                        f.this.K();
                        f fVar72 = f.this;
                        fVar72.h(new g(1, e.getMessage()));
                        f fVar82 = f.this;
                        fVar82.h(new g(94, "LOGIN_ERROR_REPORT_CHECK"));
                    }
                }
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, String reason) {
            if (!PatchProxy.proxy(new Object[]{cVar, command, reason}, this, changeQuickRedirect, false, 13737, new Class[]{com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class}, Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("login command error  : " + reason);
                if (command.e.equals(1)) {
                    f fVar = f.this;
                    fVar.h(new g(92, "LOGIN_ERROR_REPORT_WRITE"));
                    f.this.S.set(true);
                } else if (command.e.equals(2) && !f.this.S.get()) {
                    f fVar2 = f.this;
                    fVar2.h(new g(93, "LOGIN_ERROR_REPORT_READ"));
                }
                f.this.K();
                f fVar3 = f.this;
                fVar3.h(new g(1, reason));
            }
        }

        public boolean b(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cVar, command}, this, changeQuickRedirect, false, 13738, new Class[]{com.telink.bluetooth.c.class, com.telink.bluetooth.a.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (command.e.equals(1)) {
                f fVar = f.this;
                fVar.h(new g(92, "LOGIN_ERROR_REPORT_WRITE"));
                f.this.S.set(true);
            } else if (command.e.equals(2) && !f.this.S.get()) {
                f fVar2 = f.this;
                fVar2.h(new g(93, "LOGIN_ERROR_REPORT_READ"));
            }
            return false;
        }
    }

    /* compiled from: LightController */
    public final class p implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private p() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13756, new Class[0], Void.TYPE).isSupported) {
                f fVar = f.this;
                fVar.h(new g(11, "set device address timeout"));
            }
        }
    }

    /* compiled from: LightController */
    public final class o implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private o() {
        }

        public void a(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command, Object response) {
            if (!PatchProxy.proxy(new Object[]{peripheral, command, response}, this, changeQuickRedirect, false, 13754, new Class[]{com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class}, Void.TYPE).isSupported) {
                if (command.e.equals(104)) {
                    byte[] data = (byte[]) response;
                    if (data[0] == i.BLE_GATT_OP_PAIR_CONFIRM.getValue()) {
                        try {
                            byte[] sk = new byte[16];
                            for (int i = 0; i < 16; i++) {
                                sk[i] = (byte) ((f.this.N[i] ^ f.this.O[i]) ^ f.this.M[i]);
                            }
                            byte[] sk2 = com.telink.util.a.d(AES.b(f.this.F, sk));
                            byte[] sk1 = new byte[16];
                            System.arraycopy(data, 1, sk1, 0, 16);
                            if (!com.telink.util.a.c(sk2, sk1)) {
                                f.this.E.B = false;
                                f fVar = f.this;
                                fVar.h(new g(11, "set mesh failure"));
                                return;
                            }
                            f fVar2 = f.this;
                            byte[] unused = fVar2.J = fVar2.N;
                            f fVar3 = f.this;
                            byte[] unused2 = fVar3.K = fVar3.O;
                            f fVar4 = f.this;
                            byte[] unused3 = fVar4.L = fVar4.M;
                            g light = (g) peripheral;
                            light.Z(f.this.J);
                            light.a0(f.this.O);
                            light.X(f.this.M);
                            light.B = true;
                            f fVar5 = f.this;
                            fVar5.h(new g(10));
                        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                            f.this.E.B = false;
                            f fVar6 = f.this;
                            fVar6.h(new g(11, "set mesh failure"));
                        }
                    } else {
                        f.this.E.B = false;
                        f fVar7 = f.this;
                        fVar7.h(new g(11, "set mesh failure"));
                    }
                }
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a aVar, String reason) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, aVar, reason}, this, changeQuickRedirect, false, 13755, clsArr, Void.TYPE).isSupported) {
                f fVar = f.this;
                fVar.h(new g(11, reason));
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* compiled from: LightController */
    public final class k implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private k() {
        }

        public void a(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command, Object data) {
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, String reason) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, command, reason}, this, changeQuickRedirect, false, 13743, clsArr, Void.TYPE).isSupported) {
                if (command.e.equals(106) || command.e.equals(107)) {
                    f fVar = f.this;
                    fVar.h(new g(11, "set address fail"));
                    return;
                }
                f fVar2 = f.this;
                fVar2.h(new g(21, reason));
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* compiled from: LightController */
    public final class d implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private d() {
        }

        public void a(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, Object obj) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{cVar, command, obj}, this, changeQuickRedirect, false, 13730, clsArr, Void.TYPE).isSupported) {
                if (command.e.equals(402)) {
                    f fVar = f.this;
                    fVar.h(new g(40));
                }
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a aVar, String reason) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, aVar, reason}, this, changeQuickRedirect, false, 13731, clsArr, Void.TYPE).isSupported) {
                f fVar = f.this;
                fVar.h(new g(41, reason));
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* compiled from: LightController */
    public final class l implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private l() {
        }

        public void a(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, Object obj) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{cVar, command, obj}, this, changeQuickRedirect, false, 13744, clsArr, Void.TYPE).isSupported) {
                if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_TRANSITION_EASING))) {
                    int delay = h.a().c();
                    if (delay > 0) {
                        f.this.p.postDelayed(f.this.s, (long) delay);
                    } else if (!f.E(f.this)) {
                        f.F(f.this);
                    }
                    f.H(f.this);
                } else if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_WIDTH))) {
                    com.telink.bluetooth.d.a("read response : " + com.telink.util.a.b((byte[]) obj));
                    f.F(f.this);
                } else if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_HEIGHT))) {
                    com.telink.bluetooth.d.a("last read packet response : " + com.telink.util.a.b((byte[]) obj));
                    f.I(f.this);
                    f.H(f.this);
                    f fVar = f.this;
                    fVar.h(new g(71));
                } else if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_DRAWPATH))) {
                    f.J(f.this);
                }
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, String str) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, command, str}, this, changeQuickRedirect, false, 13745, clsArr, Void.TYPE).isSupported) {
                com.telink.bluetooth.d.a("error packet : " + com.telink.util.a.a(command.d, ":"));
                if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_HEIGHT))) {
                    com.telink.bluetooth.d.a("last read packet response error : ");
                    f.I(f.this);
                    f.H(f.this);
                    f fVar = f.this;
                    fVar.h(new g(71));
                    return;
                }
                f.I(f.this);
                f fVar2 = f.this;
                fVar2.h(new g(72));
            }
        }

        public boolean b(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cVar, command}, this, changeQuickRedirect, false, 13746, new Class[]{com.telink.bluetooth.c.class, com.telink.bluetooth.a.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_HEIGHT))) {
                com.telink.bluetooth.d.a("last read packet response timeout : ");
                f.I(f.this);
                f.H(f.this);
                f fVar = f.this;
                fVar.h(new g(71));
                return false;
            } else if (command.e.equals(Integer.valueOf(TypedValues.PositionType.TYPE_PERCENT_WIDTH))) {
                f.F(f.this);
                return false;
            } else {
                com.telink.bluetooth.d.a("timeout : " + com.telink.util.a.a(command.d, ":"));
                return false;
            }
        }
    }

    /* compiled from: LightController */
    public final class e implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private e() {
        }

        public void a(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command, Object obj) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{peripheral, command, obj}, this, changeQuickRedirect, false, 13732, clsArr, Void.TYPE).isSupported) {
                g light = (g) peripheral;
                light.W(command.b, (byte[]) obj);
                String firmwareRevision = light.N();
                Log.e("tel1", " light : " + light);
                if (TextUtils.isEmpty(firmwareRevision)) {
                    Log.e("tel1", " light.getFirmwareRevision() : " + light.N());
                    f.this.X();
                    return;
                }
                Log.e("tel1", "command.characteristicUUID" + command.b);
                f fVar = f.this;
                fVar.h(new g(80));
                f.this.W();
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a aVar, String str) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, aVar, str}, this, changeQuickRedirect, false, 13733, clsArr, Void.TYPE).isSupported) {
                Log.e("tel1", " firmwareRevision error ");
                f.this.X();
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* compiled from: LightController */
    public final class i implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private i() {
        }

        public void a(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command, Object obj) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{peripheral, command, obj}, this, changeQuickRedirect, false, 13739, clsArr, Void.TYPE).isSupported) {
                g light = (g) peripheral;
                light.W(command.b, (byte[]) obj);
                f fVar = f.this;
                fVar.h(new g(80));
                f.this.W();
                String firmwareRevision = light.S();
                Log.e("tel1", "new firmwareRevision : " + firmwareRevision);
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a aVar, String str) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, aVar, str}, this, changeQuickRedirect, false, 13740, clsArr, Void.TYPE).isSupported) {
                f fVar = f.this;
                fVar.h(new g(81));
                Log.e("tel1", "new firmwareRevision error ");
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* renamed from: com.telink.bluetooth.light.f$f  reason: collision with other inner class name */
    /* compiled from: LightController */
    public final class C0220f implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private C0220f() {
        }

        public void a(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command, Object obj) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{peripheral, command, obj}, this, changeQuickRedirect, false, 13734, clsArr, Void.TYPE).isSupported) {
                g light = (g) peripheral;
                light.W(command.b, (byte[]) obj);
                Log.e("weicb", "success light = " + light);
                f fVar = f.this;
                fVar.h(new g(82));
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a aVar, String str) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, aVar, str}, this, changeQuickRedirect, false, 13735, clsArr, Void.TYPE).isSupported) {
                f fVar = f.this;
                fVar.h(new g(83));
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* compiled from: LightController */
    public final class j implements a.C0214a {
        public static ChangeQuickRedirect changeQuickRedirect;

        private j() {
        }

        public void a(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, Object obj) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{cVar, command, obj}, this, changeQuickRedirect, false, 13741, clsArr, Void.TYPE).isSupported) {
                f fVar = f.this;
                fVar.h(new g(50, command));
            }
        }

        public void c(com.telink.bluetooth.c cVar, com.telink.bluetooth.a command, String str) {
            Class[] clsArr = {com.telink.bluetooth.c.class, com.telink.bluetooth.a.class, String.class};
            if (!PatchProxy.proxy(new Object[]{cVar, command, str}, this, changeQuickRedirect, false, 13742, clsArr, Void.TYPE).isSupported) {
                if (command.e.equals(105)) {
                    f fVar = f.this;
                    fVar.h(new g(11, "set address fail"));
                    return;
                }
                f fVar2 = f.this;
                fVar2.h(new g(51, command));
            }
        }

        public boolean b(com.telink.bluetooth.c peripheral, com.telink.bluetooth.a command) {
            return false;
        }
    }

    /* compiled from: LightController */
    public final class n implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private n() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13753, new Class[0], Void.TYPE).isSupported) {
                if (!f.E(f.this)) {
                    f.F(f.this);
                }
            }
        }
    }

    /* compiled from: LightController */
    public final class m {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int a;
        private int b;
        private byte[] c;
        private int d;

        private m() {
            this.b = -1;
        }

        static /* synthetic */ boolean a(m x0) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13752, new Class[]{m.class}, Boolean.TYPE);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.l();
        }

        public void b() {
            this.d = 0;
            this.a = 0;
            this.b = -1;
            this.c = null;
        }

        public boolean k() {
            int i = this.a;
            return i > 0 && this.b + 1 < i;
        }

        public int h() {
            return this.b + 1;
        }

        public byte[] g() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13748, new Class[0], byte[].class);
            if (proxy.isSupported) {
                return (byte[]) proxy.result;
            }
            int index = h();
            byte[] packet = i(index);
            this.b = index;
            return packet;
        }

        public byte[] i(int index) {
            int packetSize;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(index)}, this, changeQuickRedirect, false, 13749, new Class[]{Integer.TYPE}, byte[].class);
            if (proxy.isSupported) {
                return (byte[]) proxy.result;
            }
            byte[] bArr = this.c;
            int length = bArr.length;
            if (length <= 16) {
                packetSize = length;
            } else if (index + 1 == this.a) {
                packetSize = length - (index * 16);
            } else {
                packetSize = 16;
            }
            int packetSize2 = packetSize + 4;
            byte[] packet = new byte[packetSize2];
            System.arraycopy(bArr, index * 16, packet, 2, packetSize2 - 4);
            e(packet, index);
            int crc = c(packet);
            d(packet, crc);
            com.telink.bluetooth.d.a("ota packet ---> index : " + index + " total : " + this.a + " crc : " + crc + " content : " + com.telink.util.a.a(packet, ":"));
            return packet;
        }

        public byte[] f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13750, new Class[0], byte[].class);
            if (proxy.isSupported) {
                return (byte[]) proxy.result;
            }
            byte[] packet = new byte[4];
            int index = h();
            e(packet, index);
            int crc = c(packet);
            d(packet, crc);
            com.telink.bluetooth.d.a("ota check packet ---> index : " + index + " crc : " + crc + " content : " + com.telink.util.a.a(packet, ":"));
            return packet;
        }

        public void e(byte[] packet, int index) {
            packet[0] = (byte) (index & 255);
            packet[0 + 1] = (byte) ((index >> 8) & 255);
        }

        public void d(byte[] packet, int crc) {
            int offset = packet.length - 2;
            packet[offset] = (byte) (crc & 255);
            packet[offset + 1] = (byte) ((crc >> 8) & 255);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: byte} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int c(byte[] r10) {
            /*
                r9 = this;
                int r0 = r10.length
                r1 = 2
                int r0 = r0 - r1
                short[] r1 = new short[r1]
                r1 = {0, -24575} // fill-array
                r2 = 65535(0xffff, float:9.1834E-41)
                r3 = 0
            L_0x000c:
                if (r3 >= r0) goto L_0x002b
                byte r4 = r10[r3]
                r5 = 0
            L_0x0011:
                r6 = 8
                if (r5 >= r6) goto L_0x0028
                int r6 = r2 >> 1
                r7 = r2 ^ r4
                r7 = r7 & 1
                short r7 = r1[r7]
                r8 = 65535(0xffff, float:9.1834E-41)
                r7 = r7 & r8
                r2 = r6 ^ r7
                int r4 = r4 >> 1
                int r5 = r5 + 1
                goto L_0x0011
            L_0x0028:
                int r3 = r3 + 1
                goto L_0x000c
            L_0x002b:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.telink.bluetooth.light.f.m.c(byte[]):int");
        }

        private boolean l() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13751, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            int progress = (int) Math.floor((double) ((((float) h()) / ((float) this.a)) * 100.0f));
            if (progress == this.d) {
                return false;
            }
            this.d = progress;
            return true;
        }

        public int j() {
            return this.d;
        }
    }

    private boolean T() {
        return Build.VERSION.SDK_INT == 24;
    }
}

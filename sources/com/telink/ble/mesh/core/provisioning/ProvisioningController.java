package com.telink.ble.mesh.core.provisioning;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.Encipher;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningCapabilityPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningConfirmPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningDataPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningInvitePDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningPubKeyPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningRandomPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningRecordRequestPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningRecordResponsePDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningRecordsListPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningStartPDU;
import com.telink.ble.mesh.core.provisioning.pdu.ProvisioningStatePDU;
import com.telink.ble.mesh.entity.ProvisioningDevice;
import com.telink.ble.mesh.util.MeshLogger;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Locale;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;

public class ProvisioningController {
    private static final byte[] a = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String b = "Provisioning";
    private int c = 4096;
    private Handler d;
    private ProvisioningBridge e;
    private int f = -1;
    private int g = 0;
    private byte[] h;
    private byte[] i;
    private ProvisioningRecordsListPDU j;
    private ProvisioningInvitePDU k;
    private ProvisioningStartPDU l;
    private ProvisioningCapabilityPDU m;
    private ProvisioningPubKeyPDU n;
    private ProvisioningPubKeyPDU o;
    private KeyPair p;
    private byte[] q;
    private byte[] r;
    private byte[] s;
    private byte[] t;
    private ProvisioningDevice u;
    public String v = "";
    private Runnable w = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12964, new Class[0], Void.TYPE).isSupported) {
                ProvisioningController.a(ProvisioningController.this, "provisioning timeout");
            }
        }
    };

    static /* synthetic */ void a(ProvisioningController x0, String x1) {
        Class[] clsArr = {ProvisioningController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12963, clsArr, Void.TYPE).isSupported) {
            x0.q(x1);
        }
    }

    public ProvisioningController(HandlerThread handlerThread) {
        this.d = new Handler(handlerThread.getLooper());
    }

    public void G(ProvisioningBridge provisioningBridge) {
        this.e = provisioningBridge;
    }

    public ProvisioningDevice j() {
        return this.u;
    }

    public void b(@NonNull ProvisioningDevice device) {
        if (!PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 12931, new Class[]{ProvisioningDevice.class}, Void.TYPE).isSupported) {
            l("begin -- " + e.a(device.f()));
            this.u = device;
            this.d.removeCallbacks(this.w);
            this.d.postDelayed(this.w, 60000);
            x();
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12932, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.d;
            if (handler != null) {
                handler.removeCallbacks(this.w);
            }
            this.c = 4096;
        }
    }

    public void B(byte[] provisioningPdu) {
        if (!PatchProxy.proxy(new Object[]{provisioningPdu}, this, changeQuickRedirect, false, 12933, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c == 4096) {
                m("received notification when idle", 3);
                return;
            }
            l("provisioning pdu received: " + e.b(provisioningPdu, ""));
            byte provisioningPduType = provisioningPdu[0];
            byte[] provisioningData = new byte[(provisioningPdu.length - 1)];
            System.arraycopy(provisioningPdu, 1, provisioningData, 0, provisioningData.length);
            switch (provisioningPduType) {
                case 1:
                    n(provisioningData);
                    return;
                case 3:
                    s(provisioningData);
                    return;
                case 5:
                    o(provisioningData);
                    return;
                case 6:
                    t(provisioningData);
                    return;
                case 8:
                    r();
                    return;
                case 9:
                    q("failed notification received");
                    return;
                case 11:
                    v(provisioningData);
                    return;
                case 13:
                    u(provisioningData);
                    return;
                default:
                    return;
            }
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12934, new Class[0], Void.TYPE).isSupported) {
            H(4107, "Provision Success");
            p();
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12935, new Class[0], Void.TYPE).isSupported) {
            this.c = 4096;
            this.d.removeCallbacks(this.w);
        }
    }

    private byte[] h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12936, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!this.m.c() || this.u.b() == null) {
            return a;
        }
        return this.u.b();
    }

    private void H(int state, String desc) {
        Object[] objArr = {new Integer(state), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12937, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            l("provisioning state update: state -- " + state + " desc -- " + desc);
            this.c = state;
            ProvisioningBridge provisioningBridge = this.e;
            if (provisioningBridge != null) {
                provisioningBridge.e(state, desc);
            }
        }
    }

    private void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12939, new Class[0], Void.TYPE).isSupported) {
            l(String.format(Locale.US, "Record Request recordID=%04X offset=%04X", new Object[]{Integer.valueOf(this.f), Integer.valueOf(this.g)}));
            E(new ProvisioningRecordRequestPDU(this.f, this.g, 20));
        }
    }

    private void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12940, new Class[0], Void.TYPE).isSupported) {
            this.k = new ProvisioningInvitePDU((byte) 0);
            H(4097, "Invite");
            E(this.k);
        }
    }

    private void A(boolean isStaticOOB) {
        boolean z = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(isStaticOOB ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12941, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            ProvisioningStartPDU b2 = ProvisioningStartPDU.b(isStaticOOB);
            this.l = b2;
            if (this.m.d != 1 || this.i == null) {
                z = false;
            }
            b2.c(z);
            H(FragmentTransaction.TRANSIT_FRAGMENT_FADE, "Start - use static oob?" + isStaticOOB);
            E(this.l);
        }
    }

    private void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12942, new Class[0], Void.TYPE).isSupported) {
            ProvisioningPubKeyPDU pubKeyPDU = k();
            H(4100, "Send Public Key");
            E(pubKeyPDU);
        }
    }

    public void n(byte[] capData) {
        byte[] bArr;
        if (!PatchProxy.proxy(new Object[]{capData}, this, changeQuickRedirect, false, 12943, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c != 4097) {
                m(" capability received when not inviting", 3);
                return;
            }
            H(4098, "Capability Received");
            ProvisioningCapabilityPDU b2 = ProvisioningCapabilityPDU.b(capData);
            this.m = b2;
            this.u.n(b2);
            boolean useStaticOOB = this.m.c();
            if (useStaticOOB && this.u.b() == null) {
                if (this.u.j()) {
                    useStaticOOB = false;
                } else {
                    q("authValue not found when device static oob supported!");
                    return;
                }
            }
            A(useStaticOOB);
            z();
            if (this.m.d == 1 && (bArr = this.i) != null) {
                s(bArr);
            }
        }
    }

    private void s(byte[] pubKeyData) {
        if (!PatchProxy.proxy(new Object[]{pubKeyData}, this, changeQuickRedirect, false, 12944, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c != 4100) {
                m(" pub key received when not pub key sent", 3);
                return;
            }
            H(4101, "Public Key received");
            l("pub key received: " + e.b(pubKeyData, ""));
            this.o = ProvisioningPubKeyPDU.b(pubKeyData);
            byte[] h2 = Encipher.h(pubKeyData, this.p.getPrivate());
            this.q = h2;
            if (h2 == null) {
                q("invalid public key");
                return;
            }
            l("get secret: " + e.b(this.q, ""));
            C();
        }
    }

    private void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12945, new Class[0], Void.TYPE).isSupported) {
            ProvisioningConfirmPDU confirmPDU = new ProvisioningConfirmPDU(i());
            H(4102, "Send confirm");
            E(confirmPDU);
        }
    }

    private byte[] i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12946, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] confirmationKey = Encipher.m(this.q, Encipher.l(f()), Encipher.j);
        this.r = e.e(16);
        byte[] authValue = h();
        byte[] bArr = this.r;
        byte[] confirmData = new byte[(bArr.length + authValue.length)];
        System.arraycopy(bArr, 0, confirmData, 0, bArr.length);
        System.arraycopy(authValue, 0, confirmData, this.r.length, authValue.length);
        return Encipher.b(confirmData, confirmationKey);
    }

    private void o(byte[] confirm) {
        if (!PatchProxy.proxy(new Object[]{confirm}, this, changeQuickRedirect, false, 12947, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c != 4102) {
                m(" confirm received when not confirm sent", 3);
                return;
            }
            H(4103, "Confirm received");
            this.t = confirm;
            F();
        }
    }

    private void t(byte[] random) {
        if (!PatchProxy.proxy(new Object[]{random}, this, changeQuickRedirect, false, 12948, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c != 4104) {
                m(" random received when not random sent", 3);
                return;
            }
            H(4105, "Random received");
            this.s = random;
            if (c(random)) {
                D();
            } else {
                q("device confirm check err!");
            }
        }
    }

    private void u(byte[] recordsData) {
        if (!PatchProxy.proxy(new Object[]{recordsData}, this, changeQuickRedirect, false, 12949, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c != 16) {
                m("record list received when not record list get", 3);
                return;
            }
            ProvisioningRecordsListPDU b2 = ProvisioningRecordsListPDU.b(recordsData);
            this.j = b2;
            if (b2.c.size() < 2) {
                q("Device Certificate not found");
                return;
            }
            this.f = this.j.c.get(1).intValue();
            this.g = 0;
            this.h = null;
            this.i = null;
            H(17, "Record Request");
            y();
        }
    }

    private void v(byte[] recordResponseData) {
        byte[] bArr;
        if (!PatchProxy.proxy(new Object[]{recordResponseData}, this, changeQuickRedirect, false, 12950, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (this.c != 17) {
                m("record response received when not record request", 3);
                return;
            }
            ProvisioningRecordResponsePDU responsePDU = ProvisioningRecordResponsePDU.b(recordResponseData);
            l(responsePDU.toString());
            if (responsePDU.b != 0 || (bArr = responsePDU.f) == null) {
                q("record response error");
                return;
            }
            e(bArr);
            if (this.h.length >= responsePDU.e) {
                w();
                return;
            }
            this.g += responsePDU.f.length;
            y();
        }
    }

    private void e(byte[] newRecordData) {
        if (!PatchProxy.proxy(new Object[]{newRecordData}, this, changeQuickRedirect, false, 12951, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            byte[] bArr = this.h;
            if (bArr == null) {
                this.h = newRecordData;
                return;
            }
            byte[] re = new byte[(bArr.length + newRecordData.length)];
            System.arraycopy(bArr, 0, re, 0, bArr.length);
            System.arraycopy(newRecordData, 0, re, this.h.length, newRecordData.length);
            this.h = re;
        }
    }

    private void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12952, new Class[0], Void.TYPE).isSupported) {
            l("complete record: " + e.a(this.h));
            byte[] e2 = Encipher.e(this.h);
            this.i = e2;
            if (e2 == null || e2.length != 64) {
                q("certificate record check error");
                return;
            }
            l("public key in record: " + e.a(this.i));
            x();
        }
    }

    private void q(String desc) {
        if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 12953, new Class[]{String.class}, Void.TYPE).isSupported) {
            H(4108, desc);
            p();
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12954, new Class[0], Void.TYPE).isSupported) {
            ProvisioningDataPDU provisioningDataPDU = new ProvisioningDataPDU(g());
            H(4106, "Send provisioning data");
            E(provisioningDataPDU);
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12955, new Class[0], Void.TYPE).isSupported) {
            ProvisioningRandomPDU randomPDU = new ProvisioningRandomPDU(this.r);
            H(4104, "Send random");
            E(randomPDU);
        }
    }

    private ProvisioningPubKeyPDU k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12956, new Class[0], ProvisioningPubKeyPDU.class);
        if (proxy.isSupported) {
            return (ProvisioningPubKeyPDU) proxy.result;
        }
        KeyPair j2 = Encipher.j();
        this.p = j2;
        if (j2 != null) {
            BCECPublicKey publicKey = (BCECPublicKey) j2.getPublic();
            byte[] x = publicKey.getQ().q().e();
            byte[] y = publicKey.getQ().r().e();
            ProvisioningPubKeyPDU provisioningPubKeyPDU = new ProvisioningPubKeyPDU();
            this.n = provisioningPubKeyPDU;
            provisioningPubKeyPDU.a = x;
            provisioningPubKeyPDU.b = y;
            l("get key x: " + e.b(x, ":"));
            l("get key y: " + e.b(y, ":"));
            return this.n;
        }
        throw new RuntimeException("key pair generate err");
    }

    public byte[] f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12957, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] inviteData = this.k.a();
        byte[] capData = this.m.a();
        byte[] startData = this.l.a();
        byte[] provisionerPubKey = this.n.a();
        byte[] devicePubKey = this.o.a();
        ByteBuffer buffer = ByteBuffer.allocate(inviteData.length + capData.length + startData.length + provisionerPubKey.length + devicePubKey.length);
        buffer.put(inviteData).put(capData).put(startData).put(provisionerPubKey).put(devicePubKey);
        return buffer.array();
    }

    private boolean c(byte[] random) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{random}, this, changeQuickRedirect, false, 12958, new Class[]{byte[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] confirmationKey = Encipher.m(this.q, Encipher.l(f()), Encipher.j);
        byte[] authenticationValue = h();
        ByteBuffer buffer = ByteBuffer.allocate(random.length + authenticationValue.length);
        buffer.put(random);
        buffer.put(authenticationValue);
        if (Arrays.equals(Encipher.b(buffer.array(), confirmationKey), this.t)) {
            l("Confirmation values check pass");
            return true;
        }
        m("Confirmation values check err", 3);
        return false;
    }

    private byte[] g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12959, new Class[0], byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] confirmationSalt = Encipher.l(f());
        ByteBuffer saltBuffer = ByteBuffer.allocate(confirmationSalt.length + this.r.length + this.s.length);
        saltBuffer.put(confirmationSalt);
        saltBuffer.put(this.r);
        saltBuffer.put(this.s);
        byte[] provisioningSalt = Encipher.l(saltBuffer.array());
        byte[] t2 = Encipher.b(this.q, provisioningSalt);
        byte[] sessionKey = Encipher.b(Encipher.k, t2);
        byte[] nonce = Encipher.m(this.q, provisioningSalt, Encipher.l);
        ByteBuffer nonceBuffer = ByteBuffer.allocate(nonce.length - 3);
        nonceBuffer.put(nonce, 3, nonceBuffer.limit());
        byte[] sessionNonce = nonceBuffer.array();
        this.u.o(Encipher.b(Encipher.m, t2));
        l("11111- create device key: " + e.b(this.u.e(), ":"));
        l("provisioning data prepare: " + this.u.toString());
        byte[] provisioningData = this.u.a();
        l("unencrypted provision data: " + e.b(provisioningData, ":"));
        byte[] enData = Encipher.d(provisioningData, sessionKey, sessionNonce, 8, true);
        l("encrypted provision data: " + e.b(enData, ":"));
        return enData;
    }

    private void E(ProvisioningStatePDU pdu) {
        byte[] re;
        if (!PatchProxy.proxy(new Object[]{pdu}, this, changeQuickRedirect, false, 12960, new Class[]{ProvisioningStatePDU.class}, Void.TYPE).isSupported) {
            byte[] data = pdu.a();
            if (data == null || data.length == 0) {
                re = new byte[]{pdu.getState()};
            } else {
                re = new byte[(data.length + 1)];
                re[0] = pdu.getState();
                System.arraycopy(data, 0, re, 1, data.length);
            }
            if (this.e != null) {
                l("pdu prepared: " + e.b(data, ":"));
                this.e.b((byte) 3, re, "");
            }
        }
    }

    private void l(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12961, new Class[]{String.class}, Void.TYPE).isSupported) {
            m(logMessage + "  macAddress" + this.v, 2);
        }
    }

    private void m(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 12962, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage, "Provisioning", level);
        }
    }
}

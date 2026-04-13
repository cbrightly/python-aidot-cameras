package org.spongycastle.crypto.tls;

import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.tls.SessionParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;

public abstract class TlsProtocol {
    protected static final Integer a = Integers.b(65281);
    protected static final Integer b = Integers.b(35);
    protected boolean A;
    protected boolean B;
    protected boolean C;
    private ByteQueue c;
    private ByteQueue d;
    private ByteQueue e;
    RecordStream f;
    private TlsInputStream g;
    private TlsOutputStream h;
    private volatile boolean i;
    private volatile boolean j;
    private volatile boolean k;
    private volatile boolean l;
    private volatile int m;
    private byte[] n;
    protected TlsSession o;
    protected SessionParameters p;
    protected SecurityParameters q;
    protected Certificate r;
    protected int[] s;
    protected short[] t;
    protected Hashtable u;
    protected Hashtable v;
    protected short w;
    protected boolean x;
    protected boolean y;
    protected boolean z;

    /* access modifiers changed from: protected */
    public abstract TlsContext l();

    /* access modifiers changed from: package-private */
    public abstract AbstractTlsContext m();

    /* access modifiers changed from: protected */
    public abstract TlsPeer p();

    /* access modifiers changed from: protected */
    public abstract void w(short s2, ByteArrayInputStream byteArrayInputStream);

    /* access modifiers changed from: protected */
    public void q(short alertLevel, short alertDescription) {
        p().s(alertLevel, alertDescription);
        if (alertLevel == 1) {
            r(alertDescription);
        } else {
            v();
            throw new TlsFatalAlertReceived(alertDescription);
        }
    }

    /* access modifiers changed from: protected */
    public void r(short alertDescription) {
        if (alertDescription != 0) {
            return;
        }
        if (this.k) {
            t(false);
            return;
        }
        throw new TlsFatalAlert(40);
    }

    /* access modifiers changed from: protected */
    public void s() {
    }

    /* access modifiers changed from: protected */
    public void t(boolean user_canceled) {
        if (!this.i) {
            this.i = true;
            if (user_canceled && !this.k) {
                G(90, "User canceled handshake");
            }
            G(0, "Connection closed");
            this.f.p();
            if (!this.k) {
                e();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void u(short alertDescription, String message, Throwable cause) {
        if (!this.i) {
            F(alertDescription, message, cause);
            v();
        }
    }

    /* access modifiers changed from: protected */
    public void v() {
        this.i = true;
        this.j = true;
        x();
        this.f.p();
        if (!this.k) {
            e();
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        short s2 = this.q.l;
        if (s2 < 0) {
            return;
        }
        if (MaxFragmentLength.a(s2)) {
            this.f.s(1 << (this.q.l + 8));
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void d(boolean expected) {
        if (expected != this.y) {
            throw new TlsFatalAlert(10);
        }
    }

    /* access modifiers changed from: protected */
    public void e() {
        byte[] bArr = this.n;
        if (bArr != null) {
            Arrays.F(bArr, (byte) 0);
            this.n = null;
        }
        this.q.a();
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.x = false;
        this.y = false;
        this.z = false;
        this.A = false;
        this.B = false;
    }

    /* access modifiers changed from: protected */
    public void g() {
        try {
            this.w = 16;
            this.d.j();
            this.e.j();
            this.f.e();
            this.l = !TlsUtils.S(l());
            if (!this.k) {
                this.k = true;
                if (this.C) {
                    this.g = new TlsInputStream(this);
                    this.h = new TlsOutputStream(this);
                }
            }
            if (this.o != null) {
                if (this.p == null) {
                    this.p = new SessionParameters.Builder().b(this.q.b()).c(this.q.d()).d(this.q.e()).f(this.r).e(this.q.f()).g(this.q.h()).h(this.v).a();
                    this.o = new TlsSessionImpl(this.o.a(), this.p);
                }
                m().h(this.o);
            }
            p().v();
        } finally {
            e();
        }
    }

    /* access modifiers changed from: protected */
    public void E(short protocol, byte[] buf, int off, int len) {
        switch (protocol) {
            case 20:
                A(buf, off, len);
                return;
            case 21:
                this.d.a(buf, off, len);
                y();
                return;
            case 22:
                if (this.e.b() > 0) {
                    this.e.a(buf, off, len);
                    C(this.e);
                    return;
                }
                ByteQueue tmpQueue = new ByteQueue(buf, off, len);
                C(tmpQueue);
                int remaining = tmpQueue.b();
                if (remaining > 0) {
                    this.e.a(buf, (off + len) - remaining, remaining);
                    return;
                }
                return;
            case 23:
                if (this.k) {
                    this.c.a(buf, off, len);
                    z();
                    return;
                }
                throw new TlsFatalAlert(10);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    private void C(ByteQueue queue) {
        while (queue.b() >= 4) {
            byte[] beginning = new byte[4];
            boolean z2 = false;
            queue.e(beginning, 0, 4, 0);
            short type = TlsUtils.r0(beginning, 0);
            int length = TlsUtils.o0(beginning, 1);
            int totalLength = length + 4;
            if (queue.b() >= totalLength) {
                if (this.w == 16 || type == 20) {
                    z2 = true;
                }
                d(z2);
                switch (type) {
                    case 0:
                        break;
                    case 20:
                        TlsContext ctx = l();
                        if (this.n == null && ctx.f().e() != null) {
                            this.n = i(true ^ ctx.g());
                            break;
                        }
                }
                queue.c(this.f.i(), totalLength);
                queue.g(4);
                w(type, queue.f(length));
            } else {
                return;
            }
        }
    }

    private void z() {
    }

    private void y() {
        while (this.d.b() >= 2) {
            byte[] alert = this.d.i(2, 0);
            q((short) alert[0], (short) alert[1]);
        }
    }

    private void A(byte[] buf, int off, int len) {
        int i2 = 0;
        while (i2 < len) {
            if (TlsUtils.r0(buf, off + i2) != 1) {
                throw new TlsFatalAlert(50);
            } else if (this.y || this.d.b() > 0 || this.e.b() > 0) {
                throw new TlsFatalAlert(10);
            } else {
                this.f.o();
                this.y = true;
                s();
                i2++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int a() {
        return this.c.b();
    }

    /* access modifiers changed from: protected */
    public int H(byte[] buf, int offset, int len) {
        if (len < 1) {
            return 0;
        }
        while (this.c.b() == 0) {
            if (!this.i) {
                L();
            } else if (this.j) {
                throw new IOException("Cannot read application data on failed TLS connection");
            } else if (this.k) {
                return -1;
            } else {
                throw new IllegalStateException("Cannot read application data until initial handshake completed.");
            }
        }
        int len2 = Math.min(len, this.c.b());
        this.c.h(buf, offset, len2, 0);
        return len2;
    }

    /* access modifiers changed from: protected */
    public void L() {
        try {
            if (!this.f.n()) {
                if (!this.k) {
                    throw new TlsFatalAlert(40);
                }
                v();
                throw new TlsNoCloseNotifyException();
            }
        } catch (TlsFatalAlertReceived e2) {
            throw e2;
        } catch (TlsFatalAlert e3) {
            u(e3.getAlertDescription(), "Failed to read record", e3);
            throw e3;
        } catch (IOException e4) {
            u(80, "Failed to read record", e4);
            throw e4;
        } catch (RuntimeException e5) {
            u(80, "Failed to read record", e5);
            throw new TlsFatalAlert(80, e5);
        }
    }

    /* access modifiers changed from: protected */
    public void M(short type, byte[] buf, int offset, int len) {
        try {
            this.f.w(type, buf, offset, len);
        } catch (TlsFatalAlert e2) {
            u(e2.getAlertDescription(), "Failed to write record", e2);
            throw e2;
        } catch (IOException e3) {
            u(80, "Failed to write record", e3);
            throw e3;
        } catch (RuntimeException e4) {
            u(80, "Failed to write record", e4);
            throw new TlsFatalAlert(80, e4);
        }
    }

    /* access modifiers changed from: protected */
    public void R(byte[] buf, int offset, int len) {
        if (!this.i) {
            while (len > 0) {
                if (this.l) {
                    switch (this.m) {
                        case 1:
                            break;
                        case 2:
                            this.l = false;
                            break;
                        default:
                            M(23, buf, offset, 1);
                            offset++;
                            len--;
                            break;
                    }
                    M(23, TlsUtils.a, 0, 0);
                }
                if (len > 0) {
                    int toWrite = Math.min(len, this.f.j());
                    M(23, buf, offset, toWrite);
                    offset += toWrite;
                    len -= toWrite;
                }
            }
            return;
        }
        throw new IOException("Cannot write application data on closed/failed TLS connection");
    }

    /* access modifiers changed from: protected */
    public void T(byte[] buf, int off, int len) {
        if (len >= 4) {
            if (TlsUtils.r0(buf, off) != 0) {
                this.f.i().write(buf, off, len);
            }
            int total = 0;
            do {
                int toWrite = Math.min(len - total, this.f.j());
                M(22, buf, off + total, toWrite);
                total += toWrite;
            } while (total < len);
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void x() {
        SessionParameters sessionParameters = this.p;
        if (sessionParameters != null) {
            sessionParameters.a();
            this.p = null;
        }
        TlsSession tlsSession = this.o;
        if (tlsSession != null) {
            tlsSession.invalidate();
            this.o = null;
        }
    }

    /* access modifiers changed from: protected */
    public void B(ByteArrayInputStream buf) {
        byte[] bArr = this.n;
        if (bArr != null) {
            byte[] verify_data = TlsUtils.f0(bArr.length, buf);
            c(buf);
            if (!Arrays.u(this.n, verify_data)) {
                throw new TlsFatalAlert(51);
            }
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void F(short alertDescription, String message, Throwable cause) {
        p().p(2, alertDescription, message, cause);
        try {
            this.f.w(21, new byte[]{2, (byte) alertDescription}, 0, 2);
        } catch (Exception e2) {
        }
    }

    /* access modifiers changed from: protected */
    public void G(short alertDescription, String message) {
        p().p(1, alertDescription, message, (Throwable) null);
        M(21, new byte[]{1, (byte) alertDescription}, 0, 2);
    }

    /* access modifiers changed from: protected */
    public void N(Certificate certificate) {
        if (certificate == null) {
            certificate = Certificate.a;
        }
        if (certificate.c() && !l().g()) {
            ProtocolVersion serverVersion = l().b();
            if (serverVersion.j()) {
                G(41, serverVersion.toString() + " client didn't provide credentials");
                return;
            }
        }
        HandshakeMessage message = new HandshakeMessage(this, 11);
        certificate.a(message);
        message.a();
    }

    /* access modifiers changed from: protected */
    public void O() {
        byte[] message = {1};
        M(20, message, 0, message.length);
        this.f.q();
    }

    /* access modifiers changed from: protected */
    public void P() {
        byte[] verify_data = i(l().g());
        HandshakeMessage message = new HandshakeMessage(20, verify_data.length);
        message.write(verify_data);
        message.a();
    }

    /* access modifiers changed from: protected */
    public void Q(Vector supplementalData) {
        HandshakeMessage message = new HandshakeMessage(this, 23);
        V(message, supplementalData);
        message.a();
    }

    /* access modifiers changed from: protected */
    public byte[] i(boolean isServer) {
        TlsContext context = l();
        return TlsUtils.g(context, isServer ? "server finished" : "client finished", n(context, this.f.h(), isServer ? TlsUtils.g : TlsUtils.f));
    }

    public void f() {
        t(true);
    }

    /* access modifiers changed from: protected */
    public void k() {
        this.f.f();
    }

    /* access modifiers changed from: protected */
    public short D(Hashtable clientExtensions, Hashtable serverExtensions, short alertDescription) {
        short maxFragmentLength = TlsExtensionsUtils.k(serverExtensions);
        if (maxFragmentLength < 0 || (MaxFragmentLength.a(maxFragmentLength) && (this.x || maxFragmentLength == TlsExtensionsUtils.k(clientExtensions)))) {
            return maxFragmentLength;
        }
        throw new TlsFatalAlert(alertDescription);
    }

    /* access modifiers changed from: protected */
    public void K() {
        if (!TlsUtils.P(l())) {
            G(100, "Renegotiation not supported");
            return;
        }
        throw new TlsFatalAlert(40);
    }

    protected static void c(ByteArrayInputStream buf) {
        if (buf.available() > 0) {
            throw new TlsFatalAlert(50);
        }
    }

    protected static byte[] h(byte[] renegotiated_connection) {
        return TlsUtils.r(renegotiated_connection);
    }

    protected static void j(TlsContext context, TlsKeyExchange keyExchange) {
        byte[] pre_master_secret = keyExchange.l();
        try {
            context.f().f = TlsUtils.e(context, pre_master_secret);
        } finally {
            if (pre_master_secret != null) {
                Arrays.F(pre_master_secret, (byte) 0);
            }
        }
    }

    protected static byte[] n(TlsContext context, TlsHandshakeHash handshakeHash, byte[] sslSender) {
        Digest d2 = handshakeHash.f();
        if (sslSender != null && TlsUtils.P(context)) {
            d2.update(sslSender, 0, sslSender.length);
        }
        byte[] bs = new byte[d2.e()];
        d2.c(bs, 0);
        return bs;
    }

    protected static Hashtable I(ByteArrayInputStream input) {
        if (input.available() < 1) {
            return null;
        }
        byte[] extBytes = TlsUtils.g0(input);
        c(input);
        ByteArrayInputStream buf = new ByteArrayInputStream(extBytes);
        Hashtable extensions = new Hashtable();
        while (buf.available() > 0) {
            if (extensions.put(Integers.b(TlsUtils.k0(buf)), TlsUtils.g0(buf)) != null) {
                throw new TlsFatalAlert(47);
            }
        }
        return extensions;
    }

    protected static Vector J(ByteArrayInputStream input) {
        byte[] supp_data = TlsUtils.h0(input);
        c(input);
        ByteArrayInputStream buf = new ByteArrayInputStream(supp_data);
        Vector supplementalData = new Vector();
        while (buf.available() > 0) {
            supplementalData.addElement(new SupplementalDataEntry(TlsUtils.k0(buf), TlsUtils.g0(buf)));
        }
        return supplementalData;
    }

    protected static void S(OutputStream output, Hashtable extensions) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        U(buf, extensions, true);
        U(buf, extensions, false);
        TlsUtils.A0(buf.toByteArray(), output);
    }

    protected static void U(OutputStream output, Hashtable extensions, boolean selectEmpty) {
        Enumeration keys = extensions.keys();
        while (keys.hasMoreElements()) {
            Integer key = (Integer) keys.nextElement();
            int extension_type = key.intValue();
            byte[] extension_data = (byte[]) extensions.get(key);
            if (selectEmpty == (extension_data.length == 0)) {
                TlsUtils.h(extension_type);
                TlsUtils.D0(extension_type, output);
                TlsUtils.A0(extension_data, output);
            }
        }
    }

    protected static void V(OutputStream output, Vector supplementalData) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int i2 = 0; i2 < supplementalData.size(); i2++) {
            SupplementalDataEntry entry = (SupplementalDataEntry) supplementalData.elementAt(i2);
            int supp_data_type = entry.b();
            TlsUtils.h(supp_data_type);
            TlsUtils.D0(supp_data_type, buf);
            TlsUtils.A0(entry.a(), buf);
        }
        TlsUtils.B0(buf.toByteArray(), output);
    }

    protected static int o(TlsContext context, int ciphersuite) {
        boolean isTLSv12 = TlsUtils.U(context);
        switch (ciphersuite) {
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 156:
            case Opcodes.IFLE:
            case Opcodes.IF_ICMPNE:
            case Opcodes.IF_ICMPGE:
            case 164:
            case 166:
            case 168:
            case 170:
            case 172:
            case 186:
            case Opcodes.NEW:
            case 188:
            case 189:
            case 190:
            case 191:
            case Opcodes.CHECKCAST:
            case Opcodes.INSTANCEOF:
            case 194:
            case 195:
            case 196:
            case 197:
            case 49187:
            case 49189:
            case 49191:
            case 49193:
            case 49195:
            case 49197:
            case 49199:
            case 49201:
            case 49266:
            case 49268:
            case 49270:
            case 49272:
            case 49274:
            case 49276:
            case 49278:
            case 49280:
            case 49282:
            case 49284:
            case 49286:
            case 49288:
            case 49290:
            case 49292:
            case 49294:
            case 49296:
            case 49298:
            case 49308:
            case 49309:
            case 49310:
            case 49311:
            case 49312:
            case 49313:
            case 49314:
            case 49315:
            case 49316:
            case 49317:
            case 49318:
            case 49319:
            case 49320:
            case 49321:
            case 49322:
            case 49323:
            case 49324:
            case 49325:
            case 49326:
            case 49327:
            case 52392:
            case 52393:
            case 52394:
            case 52395:
            case 52396:
            case 52397:
            case 52398:
            case MotionEventCompat.ACTION_POINTER_INDEX_MASK:
            case 65281:
            case 65282:
            case 65283:
            case 65284:
            case 65285:
            case 65296:
            case 65297:
            case 65298:
            case 65299:
            case 65300:
            case 65301:
                if (isTLSv12) {
                    return 1;
                }
                throw new TlsFatalAlert(47);
            case 157:
            case Opcodes.IF_ICMPEQ:
            case Opcodes.IF_ICMPLT:
            case Opcodes.IF_ICMPGT:
            case Opcodes.IF_ACMPEQ:
            case Opcodes.GOTO:
            case Opcodes.RET:
            case 171:
            case 173:
            case 49188:
            case 49190:
            case 49192:
            case 49194:
            case 49196:
            case 49198:
            case 49200:
            case 49202:
            case 49267:
            case 49269:
            case 49271:
            case 49273:
            case 49275:
            case 49277:
            case 49279:
            case 49281:
            case 49283:
            case 49285:
            case 49287:
            case 49289:
            case 49291:
            case 49293:
            case 49295:
            case 49297:
            case 49299:
                if (isTLSv12) {
                    return 2;
                }
                throw new TlsFatalAlert(47);
            case 175:
            case Opcodes.RETURN:
            case 179:
            case Opcodes.PUTFIELD:
            case Opcodes.INVOKESPECIAL:
            case Opcodes.INVOKEINTERFACE:
            case 49208:
            case 49211:
            case 49301:
            case 49303:
            case 49305:
            case 49307:
                if (isTLSv12) {
                    return 2;
                }
                return 0;
            default:
                if (isTLSv12) {
                    return 1;
                }
                return 0;
        }
    }

    public class HandshakeMessage extends ByteArrayOutputStream {
        HandshakeMessage(TlsProtocol this$0, short handshakeType) {
            this(handshakeType, 60);
        }

        HandshakeMessage(short handshakeType, int length) {
            super(length + 4);
            TlsUtils.L0(handshakeType, this);
            this.count += 3;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int length = this.count - 4;
            TlsUtils.i(length);
            TlsUtils.G0(length, this.buf, 1);
            TlsProtocol.this.T(this.buf, 0, this.count);
            this.buf = null;
        }
    }
}

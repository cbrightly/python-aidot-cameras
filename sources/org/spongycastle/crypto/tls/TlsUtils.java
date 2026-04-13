package org.spongycastle.crypto.tls;

import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Shorts;
import org.spongycastle.util.Strings;
import org.spongycastle.util.io.Streams;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class TlsUtils {
    public static final byte[] a = new byte[0];
    public static final short[] b = new short[0];
    public static final int[] c = new int[0];
    public static final long[] d = new long[0];
    public static final Integer e = Integers.b(13);
    static final byte[] f = {67, 76, 78, 84};
    static final byte[] g = {83, 82, 86, 82};
    static final byte[][] h = u();

    public static void k(short i) {
        if (!a0(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void j(int i) {
        if (!Z(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void h(int i) {
        if (!X(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void i(int i) {
        if (!Y(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static boolean a0(short i) {
        return (i & 255) == i;
    }

    public static boolean Z(int i) {
        return (i & 255) == i;
    }

    public static boolean X(int i) {
        return (65535 & i) == i;
    }

    public static boolean Y(int i) {
        return (16777215 & i) == i;
    }

    public static boolean P(TlsContext context) {
        return context.b().j();
    }

    public static boolean R(ProtocolVersion version) {
        return ProtocolVersion.c.h(version.c());
    }

    public static boolean S(TlsContext context) {
        return R(context.b());
    }

    public static boolean T(ProtocolVersion version) {
        return ProtocolVersion.d.h(version.c());
    }

    public static boolean U(TlsContext context) {
        return T(context.b());
    }

    public static void L0(short i, OutputStream output) {
        output.write(i);
    }

    public static void J0(int i, OutputStream output) {
        output.write(i);
    }

    public static void M0(short i, byte[] buf, int offset) {
        buf[offset] = (byte) i;
    }

    public static void K0(int i, byte[] buf, int offset) {
        buf[offset] = (byte) i;
    }

    public static void D0(int i, OutputStream output) {
        output.write(i >>> 8);
        output.write(i);
    }

    public static void E0(int i, byte[] buf, int offset) {
        buf[offset] = (byte) (i >>> 8);
        buf[offset + 1] = (byte) i;
    }

    public static void F0(int i, OutputStream output) {
        output.write((byte) (i >>> 16));
        output.write((byte) (i >>> 8));
        output.write((byte) i);
    }

    public static void G0(int i, byte[] buf, int offset) {
        buf[offset] = (byte) (i >>> 16);
        buf[offset + 1] = (byte) (i >>> 8);
        buf[offset + 2] = (byte) i;
    }

    public static void H0(long i, OutputStream output) {
        output.write((byte) ((int) (i >>> 24)));
        output.write((byte) ((int) (i >>> 16)));
        output.write((byte) ((int) (i >>> 8)));
        output.write((byte) ((int) i));
    }

    public static void I0(long i, byte[] buf, int offset) {
        buf[offset] = (byte) ((int) (i >>> 56));
        buf[offset + 1] = (byte) ((int) (i >>> 48));
        buf[offset + 2] = (byte) ((int) (i >>> 40));
        buf[offset + 3] = (byte) ((int) (i >>> 32));
        buf[offset + 4] = (byte) ((int) (i >>> 24));
        buf[offset + 5] = (byte) ((int) (i >>> 16));
        buf[offset + 6] = (byte) ((int) (i >>> 8));
        buf[offset + 7] = (byte) ((int) i);
    }

    public static void C0(byte[] buf, OutputStream output) {
        j(buf.length);
        J0(buf.length, output);
        output.write(buf);
    }

    public static void A0(byte[] buf, OutputStream output) {
        h(buf.length);
        D0(buf.length, output);
        output.write(buf);
    }

    public static void B0(byte[] buf, OutputStream output) {
        i(buf.length);
        F0(buf.length, output);
        output.write(buf);
    }

    public static void N0(short[] uints, OutputStream output) {
        for (short L0 : uints) {
            L0(L0, output);
        }
    }

    public static void O0(short[] uints, byte[] buf, int offset) {
        for (short M0 : uints) {
            M0(M0, buf, offset);
            offset++;
        }
    }

    public static void P0(short[] uints, OutputStream output) {
        j(uints.length);
        J0(uints.length, output);
        N0(uints, output);
    }

    public static void Q0(short[] uints, byte[] buf, int offset) {
        j(uints.length);
        K0(uints.length, buf, offset);
        O0(uints, buf, offset + 1);
    }

    public static byte[] r(byte[] buf) {
        j(buf.length);
        return Arrays.S(buf, (byte) buf.length);
    }

    public static byte[] t(short[] uints) {
        byte[] result = new byte[(uints.length + 1)];
        Q0(uints, result, 0);
        return result;
    }

    public static short q0(InputStream input) {
        int i = input.read();
        if (i >= 0) {
            return (short) i;
        }
        throw new EOFException();
    }

    public static short r0(byte[] buf, int offset) {
        return (short) (buf[offset] & 255);
    }

    public static int k0(InputStream input) {
        int i1 = input.read();
        int i2 = input.read();
        if (i2 >= 0) {
            return (i1 << 8) | i2;
        }
        throw new EOFException();
    }

    public static int l0(byte[] buf, int offset) {
        return ((buf[offset] & 255) << 8) | (buf[offset + 1] & 255);
    }

    public static int n0(InputStream input) {
        int i1 = input.read();
        int i2 = input.read();
        int i3 = input.read();
        if (i3 >= 0) {
            return (i1 << 16) | (i2 << 8) | i3;
        }
        throw new EOFException();
    }

    public static int o0(byte[] buf, int offset) {
        int offset2 = offset + 1;
        return ((buf[offset] & 255) << MappingData.PATH) | ((buf[offset2] & 255) << 8) | (buf[offset2 + 1] & 255);
    }

    public static long p0(InputStream input) {
        int i1 = input.read();
        int i2 = input.read();
        int i3 = input.read();
        int i4 = input.read();
        if (i4 >= 0) {
            return ((long) ((i1 << 24) | (i2 << 16) | (i3 << 8) | i4)) & 4294967295L;
        }
        throw new EOFException();
    }

    public static byte[] d0(int length, InputStream input) {
        if (length < 1) {
            return a;
        }
        byte[] buf = new byte[length];
        int read = Streams.c(input, buf);
        if (read == 0) {
            return null;
        }
        if (read == length) {
            return buf;
        }
        throw new EOFException();
    }

    public static byte[] f0(int length, InputStream input) {
        if (length < 1) {
            return a;
        }
        byte[] buf = new byte[length];
        if (length == Streams.c(input, buf)) {
            return buf;
        }
        throw new EOFException();
    }

    public static byte[] i0(InputStream input) {
        return f0(q0(input), input);
    }

    public static byte[] g0(InputStream input) {
        return f0(k0(input), input);
    }

    public static byte[] h0(InputStream input) {
        return f0(n0(input), input);
    }

    public static short[] s0(int count, InputStream input) {
        short[] uints = new short[count];
        for (int i = 0; i < count; i++) {
            uints[i] = q0(input);
        }
        return uints;
    }

    public static int[] m0(int count, InputStream input) {
        int[] uints = new int[count];
        for (int i = 0; i < count; i++) {
            uints[i] = k0(input);
        }
        return uints;
    }

    public static ProtocolVersion u0(byte[] buf, int offset) {
        return ProtocolVersion.b(buf[offset] & 255, buf[offset + 1] & 255);
    }

    public static ProtocolVersion t0(InputStream input) {
        int i1 = input.read();
        int i2 = input.read();
        if (i2 >= 0) {
            return ProtocolVersion.b(i1, i2);
        }
        throw new EOFException();
    }

    public static int v0(byte[] buf, int offset) {
        return (buf[offset] << 8) | buf[offset + 1];
    }

    public static ASN1Primitive c0(byte[] encoding) {
        ASN1InputStream asn1 = new ASN1InputStream(encoding);
        ASN1Primitive result = asn1.r();
        if (result == null) {
            throw new TlsFatalAlert(50);
        } else if (asn1.r() == null) {
            return result;
        } else {
            throw new TlsFatalAlert(50);
        }
    }

    public static ASN1Primitive e0(byte[] encoding) {
        ASN1Primitive result = c0(encoding);
        if (Arrays.b(result.getEncoded("DER"), encoding)) {
            return result;
        }
        throw new TlsFatalAlert(50);
    }

    public static void R0(ProtocolVersion version, OutputStream output) {
        output.write(version.d());
        output.write(version.e());
    }

    public static void S0(ProtocolVersion version, byte[] buf, int offset) {
        buf[offset] = (byte) version.d();
        buf[offset + 1] = (byte) version.e();
    }

    public static Vector v() {
        Vector v = new Vector(4);
        v.addElement(Shorts.a(0));
        v.addElement(Shorts.a(1));
        v.addElement(Shorts.a(2));
        v.addElement(Shorts.a(3));
        return v;
    }

    public static Vector y() {
        return y0(new SignatureAndHashAlgorithm(2, 2));
    }

    public static Vector z() {
        return y0(new SignatureAndHashAlgorithm(2, 3));
    }

    public static Vector A() {
        return y0(new SignatureAndHashAlgorithm(2, 1));
    }

    public static SignatureAndHashAlgorithm J(TlsContext context, TlsSignerCredentials signerCredentials) {
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = null;
        if (!U(context) || (signatureAndHashAlgorithm = signerCredentials.c()) != null) {
            return signatureAndHashAlgorithm;
        }
        throw new TlsFatalAlert(80);
    }

    public static byte[] C(Hashtable extensions, Integer extensionType) {
        if (extensions == null) {
            return null;
        }
        return (byte[]) extensions.get(extensionType);
    }

    public static boolean L(Hashtable extensions, Integer extensionType, short alertDescription) {
        byte[] extension_data = C(extensions, extensionType);
        if (extension_data == null) {
            return false;
        }
        if (extension_data.length == 0) {
            return true;
        }
        throw new TlsFatalAlert(alertDescription);
    }

    public static boolean Q(ProtocolVersion clientVersion) {
        return ProtocolVersion.d.h(clientVersion.c());
    }

    public static Vector I(Hashtable extensions) {
        byte[] extensionData = C(extensions, e);
        if (extensionData == null) {
            return null;
        }
        return j0(extensionData);
    }

    public static Vector j0(byte[] extensionData) {
        if (extensionData != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
            Vector supported_signature_algorithms = b0(false, buf);
            TlsProtocol.c(buf);
            return supported_signature_algorithms;
        }
        throw new IllegalArgumentException("'extensionData' cannot be null");
    }

    public static void s(Vector supportedSignatureAlgorithms, boolean allowAnonymous, OutputStream output) {
        if (supportedSignatureAlgorithms == null || supportedSignatureAlgorithms.size() < 1 || supportedSignatureAlgorithms.size() >= 32768) {
            throw new IllegalArgumentException("'supportedSignatureAlgorithms' must have length from 1 to (2^15 - 1)");
        }
        int length = supportedSignatureAlgorithms.size() * 2;
        h(length);
        D0(length, output);
        int i = 0;
        while (i < supportedSignatureAlgorithms.size()) {
            SignatureAndHashAlgorithm entry = (SignatureAndHashAlgorithm) supportedSignatureAlgorithms.elementAt(i);
            if (allowAnonymous || entry.c() != 0) {
                entry.a(output);
                i++;
            } else {
                throw new IllegalArgumentException("SignatureAlgorithm.anonymous MUST NOT appear in the signature_algorithms extension");
            }
        }
    }

    public static Vector b0(boolean allowAnonymous, InputStream input) {
        int length = k0(input);
        if (length < 2 || (length & 1) != 0) {
            throw new TlsFatalAlert(50);
        }
        int count = length / 2;
        Vector supportedSignatureAlgorithms = new Vector(count);
        int i = 0;
        while (i < count) {
            SignatureAndHashAlgorithm entry = SignatureAndHashAlgorithm.d(input);
            if (allowAnonymous || entry.c() != 0) {
                supportedSignatureAlgorithms.addElement(entry);
                i++;
            } else {
                throw new TlsFatalAlert(47);
            }
        }
        return supportedSignatureAlgorithms;
    }

    public static void z0(Vector supportedSignatureAlgorithms, SignatureAndHashAlgorithm signatureAlgorithm) {
        if (supportedSignatureAlgorithms == null || supportedSignatureAlgorithms.size() < 1 || supportedSignatureAlgorithms.size() >= 32768) {
            throw new IllegalArgumentException("'supportedSignatureAlgorithms' must have length from 1 to (2^15 - 1)");
        } else if (signatureAlgorithm != null) {
            if (signatureAlgorithm.c() != 0) {
                int i = 0;
                while (i < supportedSignatureAlgorithms.size()) {
                    SignatureAndHashAlgorithm entry = (SignatureAndHashAlgorithm) supportedSignatureAlgorithms.elementAt(i);
                    if (entry.b() != signatureAlgorithm.b() || entry.c() != signatureAlgorithm.c()) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
            throw new TlsFatalAlert(47);
        } else {
            throw new IllegalArgumentException("'signatureAlgorithm' cannot be null");
        }
    }

    public static byte[] a(TlsContext context, byte[] secret, String asciiLabel, byte[] seed, int size) {
        if (!context.b().j()) {
            byte[] label = Strings.f(asciiLabel);
            byte[] labelSeed = m(label, seed);
            int prfAlgorithm = context.f().g();
            if (prfAlgorithm == 0) {
                return b(secret, label, labelSeed, size);
            }
            byte[] buf = new byte[size];
            N(p(prfAlgorithm), secret, labelSeed, buf);
            return buf;
        }
        throw new IllegalStateException("No PRF available for SSLv3 session");
    }

    static byte[] b(byte[] secret, byte[] label, byte[] labelSeed, int size) {
        int s_half = (secret.length + 1) / 2;
        byte[] s1 = new byte[s_half];
        byte[] s2 = new byte[s_half];
        System.arraycopy(secret, 0, s1, 0, s_half);
        System.arraycopy(secret, secret.length - s_half, s2, 0, s_half);
        byte[] b1 = new byte[size];
        byte[] b2 = new byte[size];
        N(o(1), s1, labelSeed, b1);
        N(o(2), s2, labelSeed, b2);
        for (int i = 0; i < size; i++) {
            b1[i] = (byte) (b1[i] ^ b2[i]);
        }
        return b1;
    }

    static byte[] m(byte[] a2, byte[] b2) {
        byte[] c2 = new byte[(a2.length + b2.length)];
        System.arraycopy(a2, 0, c2, 0, a2.length);
        System.arraycopy(b2, 0, c2, a2.length, b2.length);
        return c2;
    }

    static void N(Digest digest, byte[] secret, byte[] seed, byte[] out) {
        HMac mac = new HMac(digest);
        mac.a(new KeyParameter(secret));
        byte[] a2 = seed;
        int size = digest.e();
        int iterations = ((out.length + size) - 1) / size;
        byte[] buf = new byte[mac.e()];
        byte[] buf2 = new byte[mac.e()];
        for (int i = 0; i < iterations; i++) {
            mac.update(a2, 0, a2.length);
            mac.c(buf, 0);
            a2 = buf;
            mac.update(a2, 0, a2.length);
            mac.update(seed, 0, seed.length);
            mac.c(buf2, 0);
            System.arraycopy(buf2, 0, out, size * i, Math.min(size, out.length - (size * i)));
        }
    }

    static void x0(Certificate c2, int keyUsageBits) {
        KeyUsage ku;
        Extensions exts = c2.r().f();
        if (exts != null && (ku = KeyUsage.e(exts)) != null && (ku.f()[0] & 255 & keyUsageBits) != keyUsageBits) {
            throw new TlsFatalAlert(46);
        }
    }

    static byte[] c(TlsContext context, int size) {
        SecurityParameters securityParameters = context.f();
        byte[] master_secret = securityParameters.e();
        byte[] seed = m(securityParameters.i(), securityParameters.c());
        if (P(context)) {
            return d(master_secret, seed, size);
        }
        return a(context, master_secret, "key expansion", seed, size);
    }

    static byte[] d(byte[] master_secret, byte[] random, int size) {
        Digest md5 = o(1);
        Digest sha1 = o(2);
        int md5Size = md5.e();
        byte[] shatmp = new byte[sha1.e()];
        byte[] tmp = new byte[(size + md5Size)];
        int i = 0;
        int pos = 0;
        while (pos < size) {
            byte[] ssl3Const = h[i];
            sha1.update(ssl3Const, 0, ssl3Const.length);
            sha1.update(master_secret, 0, master_secret.length);
            sha1.update(random, 0, random.length);
            sha1.c(shatmp, 0);
            md5.update(master_secret, 0, master_secret.length);
            md5.update(shatmp, 0, shatmp.length);
            md5.c(tmp, pos);
            pos += md5Size;
            i++;
        }
        return Arrays.B(tmp, 0, size);
    }

    static byte[] e(TlsContext context, byte[] pre_master_secret) {
        byte[] seed;
        SecurityParameters securityParameters = context.f();
        if (securityParameters.o) {
            seed = securityParameters.j();
        } else {
            seed = m(securityParameters.c(), securityParameters.i());
        }
        if (P(context)) {
            return f(pre_master_secret, seed);
        }
        return a(context, pre_master_secret, securityParameters.o ? "extended master secret" : "master secret", seed, 48);
    }

    static byte[] f(byte[] pre_master_secret, byte[] random) {
        Digest md5 = o(1);
        Digest sha1 = o(2);
        int md5Size = md5.e();
        byte[] shatmp = new byte[sha1.e()];
        byte[] rval = new byte[(md5Size * 3)];
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            byte[] ssl3Const = h[i];
            sha1.update(ssl3Const, 0, ssl3Const.length);
            sha1.update(pre_master_secret, 0, pre_master_secret.length);
            sha1.update(random, 0, random.length);
            sha1.c(shatmp, 0);
            md5.update(pre_master_secret, 0, pre_master_secret.length);
            md5.update(shatmp, 0, shatmp.length);
            md5.c(rval, pos);
            pos += md5Size;
        }
        return rval;
    }

    static byte[] g(TlsContext context, String asciiLabel, byte[] handshakeHash) {
        if (P(context)) {
            return handshakeHash;
        }
        SecurityParameters securityParameters = context.f();
        return a(context, securityParameters.e(), asciiLabel, handshakeHash, securityParameters.k());
    }

    public static Digest o(short hashAlgorithm) {
        switch (hashAlgorithm) {
            case 1:
                return new MD5Digest();
            case 2:
                return new SHA1Digest();
            case 3:
                return new SHA224Digest();
            case 4:
                return new SHA256Digest();
            case 5:
                return new SHA384Digest();
            case 6:
                return new SHA512Digest();
            default:
                throw new IllegalArgumentException("unknown HashAlgorithm");
        }
    }

    public static Digest n(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm == null) {
            return new CombinedHash();
        }
        return o(signatureAndHashAlgorithm.b());
    }

    public static Digest l(short hashAlgorithm, Digest hash) {
        switch (hashAlgorithm) {
            case 1:
                return new MD5Digest((MD5Digest) hash);
            case 2:
                return new SHA1Digest((SHA1Digest) hash);
            case 3:
                return new SHA224Digest((SHA224Digest) hash);
            case 4:
                return new SHA256Digest((SHA256Digest) hash);
            case 5:
                return new SHA384Digest((SHA384Digest) hash);
            case 6:
                return new SHA512Digest((SHA512Digest) hash);
            default:
                throw new IllegalArgumentException("unknown HashAlgorithm");
        }
    }

    public static Digest p(int prfAlgorithm) {
        switch (prfAlgorithm) {
            case 0:
                return new CombinedHash();
            default:
                return o(D(prfAlgorithm));
        }
    }

    public static short D(int prfAlgorithm) {
        switch (prfAlgorithm) {
            case 0:
                throw new IllegalArgumentException("legacy PRF not a valid algorithm");
            case 1:
                return 4;
            case 2:
                return 5;
            default:
                throw new IllegalArgumentException("unknown PRFAlgorithm");
        }
    }

    public static ASN1ObjectIdentifier H(short hashAlgorithm) {
        switch (hashAlgorithm) {
            case 1:
                return PKCSObjectIdentifiers.t0;
            case 2:
                return X509ObjectIdentifiers.L2;
            case 3:
                return NISTObjectIdentifiers.f;
            case 4:
                return NISTObjectIdentifiers.c;
            case 5:
                return NISTObjectIdentifiers.d;
            case 6:
                return NISTObjectIdentifiers.e;
            default:
                throw new IllegalArgumentException("unknown HashAlgorithm");
        }
    }

    static short x(Certificate clientCertificate, Certificate serverCertificate) {
        if (clientCertificate.c()) {
            return -1;
        }
        Certificate x509Cert = clientCertificate.b(0);
        try {
            AsymmetricKeyParameter publicKey = PublicKeyFactory.a(x509Cert.q());
            if (publicKey.a()) {
                throw new TlsFatalAlert(80);
            } else if (publicKey instanceof RSAKeyParameters) {
                x0(x509Cert, 128);
                return 1;
            } else if (publicKey instanceof DSAPublicKeyParameters) {
                x0(x509Cert, 128);
                return 2;
            } else if (publicKey instanceof ECPublicKeyParameters) {
                x0(x509Cert, 128);
                return 64;
            } else {
                throw new TlsFatalAlert(43);
            }
        } catch (Exception e2) {
            throw new TlsFatalAlert(43, e2);
        }
    }

    static void w0(TlsHandshakeHash handshakeHash, Vector supportedSignatureAlgorithms) {
        if (supportedSignatureAlgorithms != null) {
            for (int i = 0; i < supportedSignatureAlgorithms.size(); i++) {
                short hashAlgorithm = ((SignatureAndHashAlgorithm) supportedSignatureAlgorithms.elementAt(i)).b();
                if (!HashAlgorithm.c(hashAlgorithm)) {
                    handshakeHash.h(hashAlgorithm);
                }
            }
        }
    }

    public static boolean M(short clientCertificateType) {
        switch (clientCertificateType) {
            case 1:
            case 2:
            case 64:
                return true;
            default:
                return false;
        }
    }

    public static TlsSigner q(short clientCertificateType) {
        switch (clientCertificateType) {
            case 1:
                return new TlsRSASigner();
            case 2:
                return new TlsDSSSigner();
            case 64:
                return new TlsECDSASigner();
            default:
                throw new IllegalArgumentException("'clientCertificateType' is not a type with signing capability");
        }
    }

    private static byte[][] u() {
        byte[][] arr = new byte[10][];
        for (int i = 0; i < 10; i++) {
            byte[] b2 = new byte[(i + 1)];
            Arrays.F(b2, (byte) (i + 65));
            arr[i] = b2;
        }
        return arr;
    }

    private static Vector y0(Object obj) {
        Vector v = new Vector(1);
        v.addElement(obj);
        return v;
    }

    public static int w(int ciphersuite) {
        switch (B(ciphersuite)) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                return 1;
            case 10:
            case 11:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 103:
            case 104:
                return 2;
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public static int B(int ciphersuite) {
        switch (ciphersuite) {
            case 1:
                return 0;
            case 2:
            case 44:
            case 45:
            case 46:
            case 49153:
            case 49158:
            case 49163:
            case 49168:
            case 49173:
            case 49209:
                return 0;
            case 4:
            case 24:
                return 2;
            case 5:
            case 138:
            case 142:
            case 146:
            case 49154:
            case 49159:
            case 49164:
            case 49169:
            case 49174:
            case 49203:
                return 2;
            case 10:
            case 13:
            case 16:
            case 19:
            case 22:
            case 27:
            case NeedPermissionEvent.PER_GET_LOCATION_BLE:
            case 143:
            case 147:
            case 49155:
            case 49160:
            case 49165:
            case 49170:
            case 49175:
            case 49178:
            case 49179:
            case 49180:
            case 49204:
                return 7;
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 60:
            case 62:
            case 63:
            case 64:
            case 103:
            case 108:
            case NeedPermissionEvent.PER_ANDROID_S_BLE:
            case IjkMediaMeta.FF_PROFILE_H264_HIGH_444 /*144*/:
            case Opcodes.LCMP:
            case 174:
            case Opcodes.GETSTATIC:
            case Opcodes.INVOKEVIRTUAL:
            case 49156:
            case 49161:
            case 49166:
            case 49171:
            case 49176:
            case 49181:
            case 49182:
            case 49183:
            case 49187:
            case 49189:
            case 49191:
            case 49193:
            case 49205:
            case 49207:
                return 8;
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 61:
            case 104:
            case 105:
            case 106:
            case 107:
            case 109:
            case NeedPermissionEvent.PER_ANDROID_NOTIFICATION:
            case 145:
            case Opcodes.FCMPL:
            case 175:
            case 179:
            case Opcodes.INVOKESPECIAL:
            case 49157:
            case 49162:
            case 49167:
            case 49172:
            case 49177:
            case 49184:
            case 49185:
            case 49186:
            case 49188:
            case 49190:
            case 49192:
            case 49194:
            case 49206:
            case 49208:
                return 9;
            case 59:
            case Opcodes.ARETURN:
            case 180:
            case Opcodes.INVOKESTATIC:
            case 49210:
                return 0;
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 186:
            case Opcodes.NEW:
            case 188:
            case 189:
            case 190:
            case 191:
            case 49266:
            case 49268:
            case 49270:
            case 49272:
            case 49300:
            case 49302:
            case 49304:
            case 49306:
                return 12;
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV:
            case Opcodes.CHECKCAST:
            case Opcodes.INSTANCEOF:
            case 194:
            case 195:
            case 196:
            case 197:
            case 49267:
            case 49269:
            case 49271:
            case 49273:
            case 49301:
            case 49303:
            case 49305:
            case 49307:
                return 13;
            case 150:
            case Opcodes.DCMPL:
            case 152:
            case Opcodes.IFEQ:
            case Opcodes.IFNE:
            case 155:
                return 14;
            case 156:
            case Opcodes.IFLE:
            case Opcodes.IF_ICMPNE:
            case Opcodes.IF_ICMPGE:
            case 164:
            case 166:
            case 168:
            case 170:
            case 172:
            case 49195:
            case 49197:
            case 49199:
            case 49201:
                return 10;
            case 157:
            case Opcodes.IF_ICMPEQ:
            case Opcodes.IF_ICMPLT:
            case Opcodes.IF_ICMPGT:
            case Opcodes.IF_ACMPEQ:
            case Opcodes.GOTO:
            case Opcodes.RET:
            case 171:
            case 173:
            case 49196:
            case 49198:
            case 49200:
            case 49202:
                return 11;
            case Opcodes.RETURN:
            case Opcodes.PUTFIELD:
            case Opcodes.INVOKEINTERFACE:
            case 49211:
                return 0;
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
                return 19;
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
                return 20;
            case 49308:
            case 49310:
            case 49316:
            case 49318:
            case 49324:
                return 15;
            case 49309:
            case 49311:
            case 49317:
            case 49319:
            case 49325:
                return 17;
            case 49312:
            case 49314:
            case 49320:
            case 49322:
            case 49326:
                return 16;
            case 49313:
            case 49315:
            case 49321:
            case 49323:
            case 49327:
                return 18;
            case 52392:
            case 52393:
            case 52394:
            case 52395:
            case 52396:
            case 52397:
            case 52398:
                return 21;
            case MotionEventCompat.ACTION_POINTER_INDEX_MASK:
            case 65282:
            case 65284:
            case 65296:
            case 65298:
            case 65300:
                return 103;
            case 65281:
            case 65283:
            case 65285:
            case 65297:
            case 65299:
            case 65301:
                return 104;
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public static int E(int ciphersuite) {
        switch (ciphersuite) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 10:
            case 47:
            case 53:
            case 59:
            case 60:
            case 61:
            case 65:
            case 132:
            case 150:
            case 156:
            case 157:
            case 186:
            case Opcodes.CHECKCAST:
            case 49274:
            case 49275:
            case 49308:
            case 49309:
            case 49312:
            case 49313:
                return 1;
            case 13:
            case 48:
            case 54:
            case 62:
            case 66:
            case 104:
            case 133:
            case Opcodes.DCMPL:
            case 164:
            case Opcodes.IF_ACMPEQ:
            case Opcodes.NEW:
            case Opcodes.INSTANCEOF:
            case 49282:
            case 49283:
                return 7;
            case 16:
            case 49:
            case 55:
            case 63:
            case 67:
            case 105:
            case 134:
            case 152:
            case Opcodes.IF_ICMPNE:
            case Opcodes.IF_ICMPLT:
            case 188:
            case 194:
            case 49278:
            case 49279:
                return 9;
            case 19:
            case 50:
            case 56:
            case 64:
            case 68:
            case 106:
            case 135:
            case Opcodes.IFEQ:
            case Opcodes.IF_ICMPGE:
            case Opcodes.IF_ICMPGT:
            case 189:
            case 195:
            case 49280:
            case 49281:
                return 3;
            case 22:
            case 51:
            case 57:
            case 69:
            case 103:
            case 107:
            case 136:
            case Opcodes.IFNE:
            case Opcodes.IFLE:
            case Opcodes.IF_ICMPEQ:
            case 190:
            case 196:
            case 49276:
            case 49277:
            case 49310:
            case 49311:
            case 49314:
            case 49315:
            case 52394:
            case MotionEventCompat.ACTION_POINTER_INDEX_MASK:
            case 65281:
                return 5;
            case 24:
            case 27:
            case 52:
            case 58:
            case 70:
            case 108:
            case 109:
            case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV:
            case 155:
            case 166:
            case Opcodes.GOTO:
            case 191:
            case 197:
            case 49284:
            case 49285:
                return 11;
            case 44:
            case 138:
            case NeedPermissionEvent.PER_GET_LOCATION_BLE:
            case NeedPermissionEvent.PER_ANDROID_S_BLE:
            case NeedPermissionEvent.PER_ANDROID_NOTIFICATION:
            case 168:
            case Opcodes.RET:
            case 174:
            case 175:
            case Opcodes.ARETURN:
            case Opcodes.RETURN:
            case 49294:
            case 49295:
            case 49300:
            case 49301:
            case 49316:
            case 49317:
            case 49320:
            case 49321:
            case 52395:
            case 65296:
            case 65297:
                return 13;
            case 45:
            case 142:
            case 143:
            case IjkMediaMeta.FF_PROFILE_H264_HIGH_444 /*144*/:
            case 145:
            case 170:
            case 171:
            case Opcodes.GETSTATIC:
            case 179:
            case 180:
            case Opcodes.PUTFIELD:
            case 49296:
            case 49297:
            case 49302:
            case 49303:
            case 49318:
            case 49319:
            case 49322:
            case 49323:
            case 52397:
            case 65298:
            case 65299:
                return 14;
            case 46:
            case 146:
            case 147:
            case Opcodes.LCMP:
            case Opcodes.FCMPL:
            case 172:
            case 173:
            case Opcodes.INVOKEVIRTUAL:
            case Opcodes.INVOKESPECIAL:
            case Opcodes.INVOKESTATIC:
            case Opcodes.INVOKEINTERFACE:
            case 49298:
            case 49299:
            case 49304:
            case 49305:
            case 52398:
                return 15;
            case 49153:
            case 49154:
            case 49155:
            case 49156:
            case 49157:
            case 49189:
            case 49190:
            case 49197:
            case 49198:
            case 49268:
            case 49269:
            case 49288:
            case 49289:
                return 16;
            case 49158:
            case 49159:
            case 49160:
            case 49161:
            case 49162:
            case 49187:
            case 49188:
            case 49195:
            case 49196:
            case 49266:
            case 49267:
            case 49286:
            case 49287:
            case 49324:
            case 49325:
            case 49326:
            case 49327:
            case 52393:
            case 65284:
            case 65285:
                return 17;
            case 49163:
            case 49164:
            case 49165:
            case 49166:
            case 49167:
            case 49193:
            case 49194:
            case 49201:
            case 49202:
            case 49272:
            case 49273:
            case 49292:
            case 49293:
                return 18;
            case 49168:
            case 49169:
            case 49170:
            case 49171:
            case 49172:
            case 49191:
            case 49192:
            case 49199:
            case 49200:
            case 49270:
            case 49271:
            case 49290:
            case 49291:
            case 52392:
            case 65282:
            case 65283:
                return 19;
            case 49173:
            case 49174:
            case 49175:
            case 49176:
            case 49177:
                return 20;
            case 49178:
            case 49181:
            case 49184:
                return 21;
            case 49179:
            case 49182:
            case 49185:
                return 23;
            case 49180:
            case 49183:
            case 49186:
                return 22;
            case 49203:
            case 49204:
            case 49205:
            case 49206:
            case 49207:
            case 49208:
            case 49209:
            case 49210:
            case 49211:
            case 49306:
            case 49307:
            case 52396:
            case 65300:
            case 65301:
                return 24;
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public static int F(int ciphersuite) {
        switch (ciphersuite) {
            case 1:
            case 4:
            case 24:
                return 1;
            case 2:
            case 5:
            case 10:
            case 13:
            case 16:
            case 19:
            case 22:
            case 27:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV:
            case 138:
            case NeedPermissionEvent.PER_GET_LOCATION_BLE:
            case NeedPermissionEvent.PER_ANDROID_S_BLE:
            case NeedPermissionEvent.PER_ANDROID_NOTIFICATION:
            case 142:
            case 143:
            case IjkMediaMeta.FF_PROFILE_H264_HIGH_444 /*144*/:
            case 145:
            case 146:
            case 147:
            case Opcodes.LCMP:
            case Opcodes.FCMPL:
            case 150:
            case Opcodes.DCMPL:
            case 152:
            case Opcodes.IFEQ:
            case Opcodes.IFNE:
            case 155:
            case 49153:
            case 49154:
            case 49155:
            case 49156:
            case 49157:
            case 49158:
            case 49159:
            case 49160:
            case 49161:
            case 49162:
            case 49163:
            case 49164:
            case 49165:
            case 49166:
            case 49167:
            case 49168:
            case 49169:
            case 49170:
            case 49171:
            case 49172:
            case 49173:
            case 49174:
            case 49175:
            case 49176:
            case 49177:
            case 49178:
            case 49179:
            case 49180:
            case 49181:
            case 49182:
            case 49183:
            case 49184:
            case 49185:
            case 49186:
            case 49203:
            case 49204:
            case 49205:
            case 49206:
            case 49209:
                return 2;
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
            case 174:
            case Opcodes.ARETURN:
            case Opcodes.GETSTATIC:
            case 180:
            case Opcodes.INVOKEVIRTUAL:
            case Opcodes.INVOKESTATIC:
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
            case 49207:
            case 49210:
            case 49266:
            case 49268:
            case 49270:
            case 49272:
            case 49300:
            case 49302:
            case 49304:
            case 49306:
                return 3;
            case 156:
            case 157:
            case Opcodes.IFLE:
            case Opcodes.IF_ICMPEQ:
            case Opcodes.IF_ICMPNE:
            case Opcodes.IF_ICMPLT:
            case Opcodes.IF_ICMPGE:
            case Opcodes.IF_ICMPGT:
            case 164:
            case Opcodes.IF_ACMPEQ:
            case 166:
            case Opcodes.GOTO:
            case 168:
            case Opcodes.RET:
            case 170:
            case 171:
            case 172:
            case 173:
            case 49195:
            case 49196:
            case 49197:
            case 49198:
            case 49199:
            case 49200:
            case 49201:
            case 49202:
            case 49274:
            case 49275:
            case 49276:
            case 49277:
            case 49278:
            case 49279:
            case 49280:
            case 49281:
            case 49282:
            case 49283:
            case 49284:
            case 49285:
            case 49286:
            case 49287:
            case 49288:
            case 49289:
            case 49290:
            case 49291:
            case 49292:
            case 49293:
            case 49294:
            case 49295:
            case 49296:
            case 49297:
            case 49298:
            case 49299:
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
                return 0;
            case 175:
            case Opcodes.RETURN:
            case 179:
            case Opcodes.PUTFIELD:
            case Opcodes.INVOKESPECIAL:
            case Opcodes.INVOKEINTERFACE:
            case 49188:
            case 49190:
            case 49192:
            case 49194:
            case 49208:
            case 49211:
            case 49267:
            case 49269:
            case 49271:
            case 49273:
            case 49301:
            case 49303:
            case 49305:
            case 49307:
                return 4;
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public static ProtocolVersion G(int ciphersuite) {
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
            case 157:
            case Opcodes.IFLE:
            case Opcodes.IF_ICMPEQ:
            case Opcodes.IF_ICMPNE:
            case Opcodes.IF_ICMPLT:
            case Opcodes.IF_ICMPGE:
            case Opcodes.IF_ICMPGT:
            case 164:
            case Opcodes.IF_ACMPEQ:
            case 166:
            case Opcodes.GOTO:
            case 168:
            case Opcodes.RET:
            case 170:
            case 171:
            case 172:
            case 173:
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
            case 49188:
            case 49189:
            case 49190:
            case 49191:
            case 49192:
            case 49193:
            case 49194:
            case 49195:
            case 49196:
            case 49197:
            case 49198:
            case 49199:
            case 49200:
            case 49201:
            case 49202:
            case 49266:
            case 49267:
            case 49268:
            case 49269:
            case 49270:
            case 49271:
            case 49272:
            case 49273:
            case 49274:
            case 49275:
            case 49276:
            case 49277:
            case 49278:
            case 49279:
            case 49280:
            case 49281:
            case 49282:
            case 49283:
            case 49284:
            case 49285:
            case 49286:
            case 49287:
            case 49288:
            case 49289:
            case 49290:
            case 49291:
            case 49292:
            case 49293:
            case 49294:
            case 49295:
            case 49296:
            case 49297:
            case 49298:
            case 49299:
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
                return ProtocolVersion.d;
            default:
                return ProtocolVersion.a;
        }
    }

    public static boolean O(int ciphersuite) {
        return 1 == w(ciphersuite);
    }

    public static boolean V(int cipherSuite, Vector sigAlgs) {
        try {
            switch (E(cipherSuite)) {
                case 3:
                case 4:
                case 22:
                    return sigAlgs.contains(Shorts.a(2));
                case 5:
                case 6:
                case 19:
                case 23:
                    return sigAlgs.contains(Shorts.a(1));
                case 11:
                case 12:
                case 20:
                    return sigAlgs.contains(Shorts.a(0));
                case 17:
                    return sigAlgs.contains(Shorts.a(3));
                default:
                    return true;
            }
        } catch (IOException e2) {
            return true;
        }
    }

    public static boolean W(int cipherSuite, ProtocolVersion serverVersion) {
        return G(cipherSuite).h(serverVersion.c());
    }

    public static Vector K(Vector sigHashAlgs) {
        if (sigHashAlgs == null) {
            return v();
        }
        Vector v = new Vector(4);
        v.addElement(Shorts.a(0));
        for (int i = 0; i < sigHashAlgs.size(); i++) {
            Short sigAlg = Shorts.a(((SignatureAndHashAlgorithm) sigHashAlgs.elementAt(i)).c());
            if (!v.contains(sigAlg)) {
                v.addElement(sigAlg);
            }
        }
        return v;
    }
}

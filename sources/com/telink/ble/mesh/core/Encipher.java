package com.telink.ble.mesh.core;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.util.MeshLogger;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.KeyAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.AESLightEngine;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.modes.CCMBlockCipher;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.jce.ECNamedCurveTable;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.jce.spec.ECPublicKeySpec;
import org.spongycastle.util.BigIntegers;

public final class Encipher {
    private static final byte[] a = "smk2".getBytes();
    private static final byte[] b = "smk3".getBytes();
    private static final byte[] c = "smk4".getBytes();
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final byte[] d = {105, 100, 54, 52, 1};
    private static final byte[] e = {105, 100, 54, 1};
    private static final byte[] f = "nkik".getBytes();
    private static final byte[] g = "nkbk".getBytes();
    private static final byte[] h = "id128".getBytes();
    private static final byte[] i = {0, 0, 0, 0, 0, 0};
    public static final byte[] j = "prck".getBytes();
    public static final byte[] k = "prsk".getBytes();
    public static final byte[] l = "prsn".getBytes();
    public static final byte[] m = "prdk".getBytes();
    private static final byte[] n = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static KeyPair j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 12096, new Class[0], KeyPair.class);
        if (proxy.isSupported) {
            return (KeyPair) proxy.result;
        }
        try {
            ECNamedCurveParameterSpec ecParamSpec = ECNamedCurveTable.a("P-256");
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
            generator.initialize(ecParamSpec);
            return generator.generateKeyPair();
        } catch (Exception e2) {
            MeshLogger.d("generate key pair err!");
            return null;
        }
    }

    public static byte[] h(byte[] xy, PrivateKey provisionerPrivateKey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{xy, provisionerPrivateKey}, (Object) null, changeQuickRedirect, true, 12097, new Class[]{byte[].class, PrivateKey.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        try {
            BigInteger x = BigIntegers.d(xy, 0, 32);
            BigInteger y = BigIntegers.d(xy, 32, 32);
            ECParameterSpec ecParameters = ECNamedCurveTable.a("secp256r1");
            ECPublicKeySpec keySpec = new ECPublicKeySpec(ecParameters.a().E(x, y), ecParameters);
            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
            keyAgreement.init(provisionerPrivateKey);
            keyAgreement.doPhase((ECPublicKey) KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME).generatePublic(keySpec), true);
            return keyAgreement.generateSecret();
        } catch (IllegalArgumentException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] b(byte[] content, byte[] key) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{content, key}, (Object) null, changeQuickRedirect, true, 12098, new Class[]{cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        CipherParameters cipherParameters = new KeyParameter(key);
        CMac mac = new CMac(new AESEngine());
        mac.a(cipherParameters);
        mac.update(content, 0, content.length);
        byte[] re = new byte[16];
        mac.c(re, 0);
        return re;
    }

    public static byte[] l(byte[] m2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{m2}, (Object) null, changeQuickRedirect, true, 12099, new Class[]{byte[].class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : b(m2, n);
    }

    public static byte[] d(byte[] data, byte[] k2, byte[] n2, int micSize, boolean encrypt) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data, k2, n2, new Integer(micSize), new Byte(encrypt ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 12100, new Class[]{cls, cls, cls, Integer.TYPE, Boolean.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] result = new byte[(data.length + (encrypt ? micSize : -micSize))];
        CCMBlockCipher ccmBlockCipher = new CCMBlockCipher(new AESEngine());
        ccmBlockCipher.a(encrypt, new AEADParameters(new KeyParameter(k2), micSize * 8, n2));
        ccmBlockCipher.d(data, 0, data.length, result, data.length);
        try {
            ccmBlockCipher.c(result, 0);
            return result;
        } catch (InvalidCipherTextException e2) {
            return null;
        }
    }

    public static byte[] a(byte[] data, byte[] key) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data, key}, (Object) null, changeQuickRedirect, true, 12101, new Class[]{cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] encrypted = new byte[data.length];
        CipherParameters cipherParameters = new KeyParameter(key);
        AESLightEngine engine = new AESLightEngine();
        engine.a(true, cipherParameters);
        engine.f(data, 0, encrypted, 0);
        return encrypted;
    }

    public static byte[] m(byte[] ecdh, byte[] salt, byte[] text) {
        Class<byte[]> cls = byte[].class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ecdh, salt, text}, (Object) null, changeQuickRedirect2, true, 12102, new Class[]{cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return b(text, b(ecdh, salt));
    }

    public static byte[][] c(byte[] netKey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{netKey}, (Object) null, changeQuickRedirect, true, 12103, new Class[]{byte[].class}, byte[][].class);
        if (proxy.isSupported) {
            return (byte[][]) proxy.result;
        }
        return n(netKey, new byte[]{0});
    }

    public static byte[][] n(byte[] data, byte[] p) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data, p}, (Object) null, changeQuickRedirect, true, 12104, new Class[]{cls, cls}, byte[][].class);
        if (proxy.isSupported) {
            return (byte[][]) proxy.result;
        }
        byte[] t = b(data, l(a));
        byte[] t0 = new byte[0];
        ByteBuffer inputBufferT0 = ByteBuffer.allocate(t0.length + p.length + 1);
        inputBufferT0.put(t0);
        inputBufferT0.put(p);
        inputBufferT0.put((byte) 1);
        byte[] t1 = b(inputBufferT0.array(), t);
        ByteBuffer inputBufferT1 = ByteBuffer.allocate(t1.length + p.length + 1);
        inputBufferT1.put(t1);
        inputBufferT1.put(p);
        inputBufferT1.put((byte) 2);
        byte[] t2 = b(inputBufferT1.array(), t);
        ByteBuffer inputBufferT2 = ByteBuffer.allocate(t2.length + p.length + 1);
        inputBufferT2.put(t2);
        inputBufferT2.put(p);
        inputBufferT2.put((byte) 3);
        return new byte[][]{t1, t2, b(inputBufferT2.array(), t)};
    }

    public static byte[] o(byte[] n2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{n2}, (Object) null, changeQuickRedirect, true, 12105, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] result = b(d, b(n2, l(b)));
        byte[] networkId = new byte[8];
        System.arraycopy(result, result.length - networkId.length, networkId, 0, networkId.length);
        return networkId;
    }

    public static byte p(byte[] n2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{n2}, (Object) null, changeQuickRedirect, true, 12106, new Class[]{byte[].class}, Byte.TYPE);
        if (proxy.isSupported) {
            return ((Byte) proxy.result).byteValue();
        }
        return (byte) (b(e, b(n2, l(c)))[15] & 63);
    }

    public static byte[] k(byte[] identityKey, byte[] random, int src) {
        Class<byte[]> cls = byte[].class;
        Object[] objArr = {identityKey, random, new Integer(src)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 12107, new Class[]{cls, cls, Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bArr = i;
        ByteBuffer bufferHashInput = ByteBuffer.allocate(bArr.length + random.length + 2).order(ByteOrder.BIG_ENDIAN);
        bufferHashInput.put(bArr);
        bufferHashInput.put(random);
        bufferHashInput.putShort((short) src);
        byte[] hash = a(bufferHashInput.array(), identityKey);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(hash, 8, 8);
        return buffer.array();
    }

    public static byte[] i(byte[] networkKey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkKey}, (Object) null, changeQuickRedirect, true, 12108, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] salt = l(f);
        byte[] bArr = h;
        ByteBuffer buffer = ByteBuffer.allocate(bArr.length + 1);
        buffer.put(bArr);
        buffer.put((byte) 1);
        return m(networkKey, salt, buffer.array());
    }

    public static byte[] g(byte[] networkKey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkKey}, (Object) null, changeQuickRedirect, true, 12109, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] salt = l(g);
        byte[] bArr = h;
        ByteBuffer buffer = ByteBuffer.allocate(bArr.length + 1);
        buffer.put(bArr);
        buffer.put((byte) 1);
        return m(networkKey, salt, buffer.array());
    }

    public static byte[] f(byte[] bArr, byte[] bArr2) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bArr, bArr2}, (Object) null, changeQuickRedirect, true, 12110, new Class[]{cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] data = bArr;
        byte[] key = bArr2;
        byte[] iv = new byte[4];
        System.arraycopy(data, 0, iv, 0, 4);
        byte[] mic = new byte[2];
        System.arraycopy(data, data.length - 2, mic, 0, 2);
        int len = (data.length - 4) - 2;
        byte[] status = new byte[len];
        System.arraycopy(data, 4, status, 0, len);
        byte[] e2 = new byte[16];
        byte[] r = new byte[16];
        System.arraycopy(iv, 0, r, 1, 4);
        for (int i2 = 0; i2 < len; i2++) {
            if ((i2 & 15) == 0) {
                e2 = a(r, key);
                r[0] = (byte) (r[0] + 1);
            }
            status[i2] = (byte) (status[i2] ^ e2[i2 & 15]);
        }
        byte[] r2 = new byte[16];
        System.arraycopy(iv, 0, r2, 0, 4);
        r2[4] = (byte) len;
        byte[] r3 = a(r2, key);
        for (int i3 = 0; i3 < len; i3++) {
            int i4 = i3 & 15;
            r3[i4] = (byte) (r3[i4] ^ status[i3]);
            if ((i3 & 15) == 15 || i3 == len - 1) {
                r3 = a(r3, key);
            }
        }
        for (int i5 = 0; i5 < 2; i5++) {
            if (mic[i5] != r3[i5]) {
                return null;
            }
        }
        System.arraycopy(status, 0, data, 4, status.length);
        return data;
    }

    public static byte[] e(byte[] cerData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cerData}, (Object) null, changeQuickRedirect, true, 12111, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        try {
            X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(cerData));
            if (certificate.getVersion() != 3) {
                MeshLogger.a("version check err");
                return null;
            } else if (certificate.getSerialNumber().intValue() != 4096) {
                MeshLogger.a("serial number check err");
                return null;
            } else {
                certificate.checkValidity();
                certificate.getVersion();
                certificate.getSubjectAlternativeNames();
                certificate.getExtendedKeyUsage();
                Signature verifier = Signature.getInstance(certificate.getSigAlgName(), BouncyCastleProvider.PROVIDER_NAME);
                verifier.initVerify(certificate);
                verifier.update(certificate.getTBSCertificate());
                boolean result = verifier.verify(certificate.getSignature());
                java.security.interfaces.ECPublicKey pk = (java.security.interfaces.ECPublicKey) certificate.getPublicKey();
                byte[] keyX = pk.getW().getAffineX().toByteArray();
                if (keyX.length > 32) {
                    byte[] x = new byte[32];
                    System.arraycopy(keyX, 1, x, 0, 32);
                    keyX = x;
                }
                byte[] keyY = pk.getW().getAffineY().toByteArray();
                if (keyY.length > 32) {
                    byte[] y = new byte[32];
                    System.arraycopy(keyY, 1, y, 0, 32);
                    keyY = y;
                }
                byte[] pubKeyKey = new byte[(keyX.length + keyY.length)];
                System.arraycopy(keyX, 0, pubKeyKey, 0, keyX.length);
                System.arraycopy(keyY, 0, pubKeyKey, keyX.length, keyY.length);
                if (result) {
                    System.out.println("signature validation pass");
                    return pubKeyKey;
                }
                System.out.println("signature validation failed");
                return null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}

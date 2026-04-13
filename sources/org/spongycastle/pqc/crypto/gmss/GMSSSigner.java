package org.spongycastle.pqc.crypto.gmss;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.MessageSigner;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.spongycastle.util.Arrays;

public class GMSSSigner implements MessageSigner {
    private GMSSUtil a;
    private byte[] b;
    private Digest c;
    private int d;
    private int e;
    private Digest f;
    private WinternitzOTSignature g;
    private GMSSDigestProvider h;
    private int[] i;
    private byte[][][] j;
    private byte[][] k;
    private GMSSParameters l;
    private GMSSRandom m;
    GMSSKeyParameters n;
    private SecureRandom o;

    public void a(boolean forSigning, CipherParameters param) {
        if (!forSigning) {
            this.n = (GMSSPublicKeyParameters) param;
            e();
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.o = rParam.b();
            this.n = (GMSSPrivateKeyParameters) rParam.a();
            d();
        } else {
            this.o = new SecureRandom();
            this.n = (GMSSPrivateKeyParameters) param;
            d();
        }
    }

    private void d() {
        int i2;
        this.c.reset();
        GMSSPrivateKeyParameters gmssPrivateKey = (GMSSPrivateKeyParameters) this.n;
        if (gmssPrivateKey.l()) {
            throw new IllegalStateException("Private key already used");
        } else if (gmssPrivateKey.f(0) < gmssPrivateKey.i(0)) {
            GMSSParameters b2 = gmssPrivateKey.b();
            this.l = b2;
            this.e = b2.c();
            byte[] seed = gmssPrivateKey.e()[this.e - 1];
            int i3 = this.d;
            byte[] bArr = new byte[i3];
            byte[] dummy = new byte[i3];
            System.arraycopy(seed, 0, dummy, 0, i3);
            this.g = new WinternitzOTSignature(this.m.c(dummy), this.h.get(), this.l.d()[this.e - 1]);
            byte[][][] helpCurrentAuthPaths = gmssPrivateKey.d();
            this.j = new byte[this.e][][];
            int j2 = 0;
            while (true) {
                i2 = this.e;
                if (j2 >= i2) {
                    break;
                }
                byte[][][] bArr2 = this.j;
                int length = helpCurrentAuthPaths[j2].length;
                int[] iArr = new int[2];
                iArr[1] = this.d;
                iArr[0] = length;
                bArr2[j2] = (byte[][]) Array.newInstance(byte.class, iArr);
                for (int i4 = 0; i4 < helpCurrentAuthPaths[j2].length; i4++) {
                    System.arraycopy(helpCurrentAuthPaths[j2][i4], 0, this.j[j2][i4], 0, this.d);
                }
                j2++;
            }
            this.i = new int[i2];
            System.arraycopy(gmssPrivateKey.g(), 0, this.i, 0, this.e);
            this.k = new byte[(this.e - 1)][];
            for (int i5 = 0; i5 < this.e - 1; i5++) {
                byte[] helpSubtreeRootSig = gmssPrivateKey.j(i5);
                byte[][] bArr3 = this.k;
                bArr3[i5] = new byte[helpSubtreeRootSig.length];
                System.arraycopy(helpSubtreeRootSig, 0, bArr3[i5], 0, helpSubtreeRootSig.length);
            }
            gmssPrivateKey.m();
        } else {
            throw new IllegalStateException("No more signatures can be generated");
        }
    }

    public byte[] b(byte[] message) {
        byte[] bArr = new byte[this.d];
        byte[] otsSig = this.g.c(message);
        byte[] authPathBytes = this.a.b(this.j[this.e - 1]);
        byte[] indexBytes = this.a.c(this.i[this.e - 1]);
        byte[] gmssSigFirstPart = new byte[(indexBytes.length + otsSig.length + authPathBytes.length)];
        System.arraycopy(indexBytes, 0, gmssSigFirstPart, 0, indexBytes.length);
        System.arraycopy(otsSig, 0, gmssSigFirstPart, indexBytes.length, otsSig.length);
        System.arraycopy(authPathBytes, 0, gmssSigFirstPart, indexBytes.length + otsSig.length, authPathBytes.length);
        byte[] gmssSigNextPart = new byte[0];
        for (int i2 = (this.e - 1) - 1; i2 >= 0; i2--) {
            byte[] authPathBytes2 = this.a.b(this.j[i2]);
            byte[] indexBytes2 = this.a.c(this.i[i2]);
            byte[] helpGmssSig = new byte[gmssSigNextPart.length];
            System.arraycopy(gmssSigNextPart, 0, helpGmssSig, 0, gmssSigNextPart.length);
            gmssSigNextPart = new byte[(helpGmssSig.length + indexBytes2.length + this.k[i2].length + authPathBytes2.length)];
            System.arraycopy(helpGmssSig, 0, gmssSigNextPart, 0, helpGmssSig.length);
            System.arraycopy(indexBytes2, 0, gmssSigNextPart, helpGmssSig.length, indexBytes2.length);
            byte[][] bArr2 = this.k;
            System.arraycopy(bArr2[i2], 0, gmssSigNextPart, helpGmssSig.length + indexBytes2.length, bArr2[i2].length);
            System.arraycopy(authPathBytes2, 0, gmssSigNextPart, helpGmssSig.length + indexBytes2.length + this.k[i2].length, authPathBytes2.length);
        }
        byte[] gmssSig = new byte[(gmssSigFirstPart.length + gmssSigNextPart.length)];
        System.arraycopy(gmssSigFirstPart, 0, gmssSig, 0, gmssSigFirstPart.length);
        System.arraycopy(gmssSigNextPart, 0, gmssSig, gmssSigFirstPart.length, gmssSigNextPart.length);
        return gmssSig;
    }

    private void e() {
        this.c.reset();
        GMSSPublicKeyParameters gmssPublicKey = (GMSSPublicKeyParameters) this.n;
        this.b = gmssPublicKey.c();
        GMSSParameters b2 = gmssPublicKey.b();
        this.l = b2;
        this.e = b2.c();
    }

    public boolean c(byte[] message, byte[] signature) {
        byte[] message2;
        int i2;
        byte[] bArr = signature;
        boolean success = false;
        this.f.reset();
        char c2 = 1;
        int j2 = this.e - 1;
        int nextEntry = 0;
        byte[] help = message;
        while (j2 >= 0) {
            WinternitzOTSVerify otsVerify = new WinternitzOTSVerify(this.h.get(), this.l.d()[j2]);
            int otsSigLength = otsVerify.c();
            byte[] message3 = help;
            int index = this.a.a(bArr, nextEntry);
            int nextEntry2 = nextEntry + 4;
            byte[] otsSig = new byte[otsSigLength];
            System.arraycopy(bArr, nextEntry2, otsSig, 0, otsSigLength);
            nextEntry = nextEntry2 + otsSigLength;
            byte[] otsPublicKey = otsVerify.a(message3, otsSig);
            if (otsPublicKey == null) {
                System.err.println("OTS Public Key is null in GMSSSignature.verify");
                return false;
            }
            int i3 = this.l.a()[j2];
            boolean success2 = success;
            int[] iArr = new int[2];
            iArr[c2] = this.d;
            iArr[0] = i3;
            byte[][] authPath = (byte[][]) Array.newInstance(byte.class, iArr);
            for (byte[] arraycopy : authPath) {
                System.arraycopy(bArr, nextEntry, arraycopy, 0, this.d);
                nextEntry += this.d;
            }
            byte[] help2 = new byte[this.d];
            help = otsPublicKey;
            int count = (1 << authPath.length) + index;
            int i4 = 0;
            while (i4 < authPath.length) {
                int i5 = this.d;
                byte[] dest = new byte[(i5 << 1)];
                if (count % 2 == 0) {
                    i2 = 0;
                    System.arraycopy(help, 0, dest, 0, i5);
                    byte[] bArr2 = authPath[i4];
                    message2 = message3;
                    int i6 = this.d;
                    System.arraycopy(bArr2, 0, dest, i6, i6);
                    count /= 2;
                } else {
                    message2 = message3;
                    i2 = 0;
                    System.arraycopy(authPath[i4], 0, dest, 0, i5);
                    System.arraycopy(help, 0, dest, this.d, help.length);
                    count = (count - 1) / 2;
                }
                this.c.update(dest, i2, dest.length);
                help = new byte[this.c.e()];
                this.c.c(help, i2);
                i4++;
                message3 = message2;
                byte[] bArr3 = signature;
            }
            j2--;
            bArr = signature;
            success = success2;
            c2 = 1;
        }
        boolean success3 = success;
        if (Arrays.b(this.b, help)) {
            return true;
        }
        return success3;
    }
}

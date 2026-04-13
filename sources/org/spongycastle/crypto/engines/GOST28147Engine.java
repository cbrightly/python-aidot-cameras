package org.spongycastle.crypto.engines;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import java.util.Enumeration;
import java.util.Hashtable;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class GOST28147Engine implements BlockCipher {
    private static byte[] a = {4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3, 14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11, 7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3, 6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2, 4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14, 13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12, 1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12};
    private static byte[] b = {4, 2, 15, 5, 9, 1, 0, 8, 14, 3, 11, 12, 13, 7, 10, 6, 12, 9, 15, 14, 8, 1, 3, 10, 2, 7, 4, 13, 6, 0, 11, 5, 13, 8, 14, 12, 7, 3, 9, 10, 1, 5, 2, 4, 6, 15, 0, 11, 14, 9, 11, 2, 5, 15, 7, 1, 0, 13, 12, 6, 10, 4, 3, 8, 3, 14, 5, 9, 6, 8, 0, 13, 10, 11, 7, 12, 2, 1, 15, 4, 8, 15, 6, 11, 1, 9, 12, 5, 13, 3, 7, 10, 0, 14, 2, 4, 9, 11, 12, 0, 3, 6, 7, 5, 4, 8, 14, 15, 1, 10, 2, 13, 12, 6, 5, 2, 11, 0, 9, 13, 3, 14, 7, 10, 15, 4, 1, 8};
    private static byte[] c = {9, 6, 3, 2, 8, 11, 1, 7, 10, 4, 14, 15, 12, 0, 13, 5, 3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1, 14, 4, 6, 2, 11, 3, 13, 8, 12, 15, 5, 10, 0, 7, 1, 9, 14, 7, 10, 12, 13, 1, 3, 9, 0, 2, 11, 4, 15, 8, 5, 6, 11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6, 3, 10, 13, 12, 1, 2, 0, 11, 7, 5, 9, 4, 8, 15, 14, 6, 1, 13, 2, 9, 7, 10, 6, 0, 8, 12, 4, 5, 15, 3, 11, 14, 11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4};
    private static byte[] d = {8, 4, 11, 1, 3, 5, 0, 9, 2, 14, 10, 12, 13, 6, 7, 15, 0, 1, 2, 10, 4, 13, 5, 12, 9, 7, 3, 15, 11, 8, 6, 14, 14, 12, 0, 10, 9, 2, 13, 11, 7, 5, 8, 15, 3, 6, 1, 4, 7, 5, 0, 13, 11, 6, 1, 2, 3, 10, 12, 15, 4, 14, 9, 8, 2, 7, 12, 15, 9, 5, 10, 11, 1, 4, 0, 13, 6, 8, 14, 3, 8, 3, 2, 6, 4, 13, 14, 11, 12, 1, 7, 15, 10, 0, 9, 5, 5, 2, 10, 11, 9, 1, 12, 3, 7, 4, 13, 0, 6, 15, 8, 14, 0, 4, 11, 14, 8, 3, 7, 1, 10, 2, 9, 6, 15, 13, 5, 12};
    private static byte[] e = {1, 11, 12, 2, 9, 13, 0, 15, 4, 5, 8, 14, 10, 7, 6, 3, 0, 1, 7, 13, 11, 4, 5, 2, 8, 14, 15, 12, 9, 10, 6, 3, 8, 2, 5, 0, 4, 9, 15, 10, 3, 7, 12, 13, 6, 14, 1, 11, 3, 6, 0, 1, 5, 13, 10, 8, 11, 2, 9, 7, 14, 15, 12, 4, 8, 13, 11, 0, 4, 5, 1, 2, 9, 3, 12, 14, 6, 15, 10, 7, 12, 9, 11, 1, 8, 14, 2, 4, 7, 3, 6, 5, 10, 0, 15, 13, 10, 9, 6, 8, 13, 14, 2, 0, 15, 3, 5, 11, 4, 1, 12, 7, 7, 4, 0, 5, 10, 2, 15, 14, 12, 6, 1, 11, 13, 9, 3, 8};
    private static byte[] f = {15, 12, 2, 10, 6, 4, 5, 0, 7, 9, 14, 13, 1, 11, 8, 3, 11, 6, 3, 4, 12, 15, 14, 2, 7, 13, 8, 0, 5, 10, 9, 1, 1, 12, 11, 0, 15, 14, 6, 5, 10, 13, 4, 8, 9, 3, 7, 2, 1, 5, 14, 12, 10, 7, 0, 13, 6, 2, 11, 4, 9, 3, 15, 8, 0, 12, 8, 9, 13, 2, 10, 11, 7, 3, 6, 5, 4, 14, 15, 1, 8, 0, 15, 3, 2, 5, 14, 11, 1, 10, 4, 7, 12, 9, 13, 6, 3, 0, 6, 15, 1, 14, 9, 2, 13, 8, 12, 4, 11, 10, 5, 7, 1, 10, 6, 8, 15, 11, 0, 4, 12, 3, 5, 9, 7, 13, 2, 14};
    private static byte[] g = {4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3, 14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11, 7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3, 6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2, 4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14, 13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12, 1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12};
    private static byte[] h = {10, 4, 5, 6, 8, 1, 3, 7, 13, 12, 14, 0, 9, 2, 11, 15, 5, 15, 4, 0, 2, 13, 11, 9, 1, 7, 6, 3, 12, 14, 10, 8, 7, 15, 12, 14, 9, 4, 1, 0, 3, 11, 5, 2, 6, 10, 8, 13, 4, 10, 7, 12, 0, 15, 2, 8, 14, 1, 6, 5, 13, 11, 9, 3, 7, 6, 4, 11, 9, 12, 2, 10, 1, 8, 0, 14, 15, 13, 3, 5, 7, 6, 2, 4, 13, 9, 15, 0, 10, 1, 5, 11, 8, 14, 12, 3, 13, 14, 4, 1, 7, 0, 5, 10, 3, 12, 8, 15, 6, 2, 9, 11, 1, 3, 10, 9, 5, 11, 4, 15, 8, 6, 7, 14, 13, 0, 2, 12};
    private static Hashtable i = new Hashtable();
    private int[] j = null;
    private boolean k;
    private byte[] l = a;

    static {
        g("Default", a);
        g("E-TEST", b);
        g("E-A", c);
        g("E-B", d);
        g("E-C", e);
        g("E-D", f);
        g("D-TEST", g);
        g("D-A", h);
    }

    private static void g(String sBoxName, byte[] sBox) {
        i.put(Strings.l(sBoxName), sBox);
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithSBox) {
            ParametersWithSBox param = (ParametersWithSBox) params;
            byte[] sBox = param.b();
            if (sBox.length == a.length) {
                this.l = Arrays.h(sBox);
                if (param.a() != null) {
                    this.j = i(forEncryption, ((KeyParameter) param.a()).a());
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("invalid S-box passed to GOST28147 init");
        } else if (params instanceof KeyParameter) {
            this.j = i(forEncryption, ((KeyParameter) params).a());
        } else if (params != null) {
            throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + params.getClass().getName());
        }
    }

    public String b() {
        return "GOST28147";
    }

    public int c() {
        return 8;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        int[] iArr = this.j;
        if (iArr == null) {
            throw new IllegalStateException("GOST28147 engine not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 <= out.length) {
            d(iArr, in, inOff, out, outOff);
            return 8;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }

    private int[] i(boolean forEncryption, byte[] userKey) {
        this.k = forEncryption;
        if (userKey.length == 32) {
            int[] key = new int[8];
            for (int i2 = 0; i2 != 8; i2++) {
                key[i2] = h(userKey, i2 * 4);
            }
            return key;
        }
        throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
    }

    private int e(int n1, int key) {
        int cm = key + n1;
        byte[] bArr = this.l;
        int om = (bArr[((cm >> 0) & 15) + 0] << 0) + (bArr[((cm >> 4) & 15) + 16] << 4) + (bArr[((cm >> 8) & 15) + 32] << 8) + (bArr[((cm >> 12) & 15) + 48] << 12) + (bArr[((cm >> 16) & 15) + 64] << MappingData.PATH) + (bArr[((cm >> 20) & 15) + 80] << 20) + (bArr[((cm >> 24) & 15) + 96] << 24) + (bArr[((cm >> 28) & 15) + 112] << 28);
        return (om << 11) | (om >>> 21);
    }

    private void d(int[] workingKey, byte[] in, int inOff, byte[] out, int outOff) {
        int N1 = h(in, inOff);
        int N2 = h(in, inOff + 4);
        if (this.k) {
            for (int k2 = 0; k2 < 3; k2++) {
                for (int j2 = 0; j2 < 8; j2++) {
                    int tmp = N1;
                    N1 = N2 ^ e(N1, workingKey[j2]);
                    N2 = tmp;
                }
            }
            for (int j3 = 7; j3 > 0; j3--) {
                int tmp2 = N1;
                N1 = N2 ^ e(N1, workingKey[j3]);
                N2 = tmp2;
            }
        } else {
            for (int j4 = 0; j4 < 8; j4++) {
                int tmp3 = N1;
                N1 = N2 ^ e(N1, workingKey[j4]);
                N2 = tmp3;
            }
            int k3 = 0;
            while (k3 < 3) {
                int j5 = 7;
                while (j5 >= 0 && (k3 != 2 || j5 != 0)) {
                    int tmp4 = N1;
                    N1 = N2 ^ e(N1, workingKey[j5]);
                    N2 = tmp4;
                    j5--;
                }
                k3++;
            }
        }
        l(N1, out, outOff);
        l(N2 ^ e(N1, workingKey[0]), out, outOff + 4);
    }

    private int h(byte[] in, int inOff) {
        return ((in[inOff + 3] << 24) & ViewCompat.MEASURED_STATE_MASK) + ((in[inOff + 2] << MappingData.PATH) & 16711680) + ((in[inOff + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (in[inOff] & 255);
    }

    private void l(int num, byte[] out, int outOff) {
        out[outOff + 3] = (byte) (num >>> 24);
        out[outOff + 2] = (byte) (num >>> 16);
        out[outOff + 1] = (byte) (num >>> 8);
        out[outOff] = (byte) num;
    }

    public static byte[] j(String sBoxName) {
        byte[] sBox = (byte[]) i.get(Strings.l(sBoxName));
        if (sBox != null) {
            return Arrays.h(sBox);
        }
        throw new IllegalArgumentException("Unknown S-Box - possible types: \"Default\", \"E-Test\", \"E-A\", \"E-B\", \"E-C\", \"E-D\", \"D-Test\", \"D-A\".");
    }

    public static String k(byte[] sBox) {
        Enumeration en = i.keys();
        while (en.hasMoreElements()) {
            String name = (String) en.nextElement();
            if (Arrays.b((byte[]) i.get(name), sBox)) {
                return name;
            }
        }
        throw new IllegalArgumentException("SBOX provided did not map to a known one");
    }
}

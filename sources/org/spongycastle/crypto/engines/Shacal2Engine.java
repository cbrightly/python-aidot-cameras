package org.spongycastle.crypto.engines;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class Shacal2Engine implements BlockCipher {
    private static final int[] a = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private boolean b = false;
    private int[] c = null;

    public void reset() {
    }

    public String b() {
        return "Shacal2";
    }

    public int c() {
        return 32;
    }

    public void a(boolean _forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.b = _forEncryption;
            this.c = new int[64];
            j(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("only simple KeyParameter expected.");
    }

    public void j(byte[] kb) {
        if (kb.length == 0 || kb.length > 64 || kb.length < 16 || kb.length % 8 != 0) {
            throw new IllegalArgumentException("Shacal2-key must be 16 - 64 bytes and multiple of 8");
        }
        e(kb, this.c, 0, 0);
        for (int i = 16; i < 64; i++) {
            int[] iArr = this.c;
            iArr[i] = ((((iArr[i - 2] >>> 17) | (iArr[i - 2] << -17)) ^ ((iArr[i - 2] >>> 19) | (iArr[i - 2] << -19))) ^ (iArr[i - 2] >>> 10)) + iArr[i - 7] + ((((iArr[i - 15] >>> 7) | (iArr[i - 15] << -7)) ^ ((iArr[i - 15] >>> 18) | (iArr[i - 15] << -18))) ^ (iArr[i - 15] >>> 3)) + iArr[i - 16];
        }
    }

    private void h(byte[] in, int inOffset, byte[] out, int outOffset) {
        int[] block = new int[8];
        d(in, block, inOffset, 0);
        for (int i = 0; i < 64; i++) {
            int tmp = ((((block[4] >>> 6) | (block[4] << -6)) ^ ((block[4] >>> 11) | (block[4] << -11))) ^ ((block[4] >>> 25) | (block[4] << -25))) + ((block[4] & block[5]) ^ ((~block[4]) & block[6])) + block[7] + a[i] + this.c[i];
            block[7] = block[6];
            block[6] = block[5];
            block[5] = block[4];
            block[4] = block[3] + tmp;
            block[3] = block[2];
            block[2] = block[1];
            block[1] = block[0];
            block[0] = ((((block[0] >>> 2) | (block[0] << -2)) ^ ((block[0] >>> 13) | (block[0] << -13))) ^ ((block[0] >>> 22) | (block[0] << -22))) + tmp + ((block[2] & block[3]) ^ ((block[0] & block[2]) ^ (block[0] & block[3])));
        }
        i(block, out, outOffset);
    }

    private void g(byte[] in, int inOffset, byte[] out, int outOffset) {
        int[] block = new int[8];
        d(in, block, inOffset, 0);
        for (int i = 63; i > -1; i--) {
            int tmp = (block[0] - ((((block[1] >>> 2) | (block[1] << -2)) ^ ((block[1] >>> 13) | (block[1] << -13))) ^ ((block[1] >>> 22) | (block[1] << -22)))) - (((block[1] & block[2]) ^ (block[1] & block[3])) ^ (block[2] & block[3]));
            block[0] = block[1];
            block[1] = block[2];
            block[2] = block[3];
            block[3] = block[4] - tmp;
            block[4] = block[5];
            block[5] = block[6];
            block[6] = block[7];
            block[7] = (((tmp - a[i]) - this.c[i]) - ((((block[4] >>> 6) | (block[4] << -6)) ^ ((block[4] >>> 11) | (block[4] << -11))) ^ ((block[4] >>> 25) | (block[4] << -25)))) - (((~block[4]) & block[6]) ^ (block[5] & block[4]));
        }
        i(block, out, outOffset);
    }

    public int f(byte[] in, int inOffset, byte[] out, int outOffset) {
        if (this.c == null) {
            throw new IllegalStateException("Shacal2 not initialised");
        } else if (inOffset + 32 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOffset + 32 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.b) {
            h(in, inOffset, out, outOffset);
            return 32;
        } else {
            g(in, inOffset, out, outOffset);
            return 32;
        }
    }

    private void d(byte[] bytes, int[] block, int bytesPos, int blockPos) {
        int i = blockPos;
        while (i < 8) {
            int bytesPos2 = bytesPos + 1;
            int bytesPos3 = bytesPos2 + 1;
            byte b2 = ((bytes[bytesPos] & 255) << 24) | ((bytes[bytesPos2] & 255) << MappingData.PATH);
            int bytesPos4 = bytesPos3 + 1;
            block[i] = b2 | ((bytes[bytesPos3] & 255) << 8) | (bytes[bytesPos4] & 255);
            i++;
            bytesPos = bytesPos4 + 1;
        }
    }

    private void e(byte[] bytes, int[] block, int bytesPos, int blockPos) {
        int i = blockPos;
        while (i < bytes.length / 4) {
            int bytesPos2 = bytesPos + 1;
            int bytesPos3 = bytesPos2 + 1;
            byte b2 = ((bytes[bytesPos] & 255) << 24) | ((bytes[bytesPos2] & 255) << MappingData.PATH);
            int bytesPos4 = bytesPos3 + 1;
            byte b3 = b2 | ((bytes[bytesPos3] & 255) << 8);
            block[i] = b3 | (bytes[bytesPos4] & 255);
            i++;
            bytesPos = bytesPos4 + 1;
        }
    }

    private void i(int[] block, byte[] out, int pos) {
        for (int i = 0; i < block.length; i++) {
            int pos2 = pos + 1;
            out[pos] = (byte) (block[i] >>> 24);
            int pos3 = pos2 + 1;
            out[pos2] = (byte) (block[i] >>> 16);
            int pos4 = pos3 + 1;
            out[pos3] = (byte) (block[i] >>> 8);
            pos = pos4 + 1;
            out[pos4] = (byte) block[i];
        }
    }
}

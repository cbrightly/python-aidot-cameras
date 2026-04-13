package org.spongycastle.pqc.math.linearalgebra;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.Random;
import meshsdk.model.json.MeshStorage;
import net.sqlcipher.database.SQLiteDatabase;

public class GF2Polynomial {
    private static Random a = new Random();
    private static final boolean[] b = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private static final short[] c = {0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
    private static final int[] d = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, SQLiteDatabase.CREATE_IF_NECESSARY, 536870912, 1073741824, Integer.MIN_VALUE, 0};
    private static final int[] e = {0, 1, 3, 7, 15, 31, 63, NeedPermissionEvent.PER_IPC_SPEAK_PERM, 255, 511, 1023, 2047, 4095, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_EVENT_REPORT, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, ViewCompat.MEASURED_SIZE_MASK, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1};
    private int f;
    private int g;
    private int[] h;

    public GF2Polynomial(int length) {
        int l = length;
        l = l < 1 ? 1 : l;
        int i = ((l - 1) >> 5) + 1;
        this.g = i;
        this.h = new int[i];
        this.f = l;
    }

    public GF2Polynomial(int length, String value) {
        int l = length;
        l = l < 1 ? 1 : l;
        int i = ((l - 1) >> 5) + 1;
        this.g = i;
        this.h = new int[i];
        this.f = l;
        if (value.equalsIgnoreCase("ZERO")) {
            f();
        } else if (value.equalsIgnoreCase("ONE")) {
            d();
        } else if (value.equalsIgnoreCase("RANDOM")) {
            m();
        } else if (value.equalsIgnoreCase("X")) {
            e();
        } else if (value.equalsIgnoreCase("ALL")) {
            c();
        } else {
            throw new IllegalArgumentException("Error: GF2Polynomial was called using " + value + " as value!");
        }
    }

    public GF2Polynomial(int length, int[] bs) {
        int leng = length;
        leng = leng < 1 ? 1 : leng;
        int i = ((leng - 1) >> 5) + 1;
        this.g = i;
        this.h = new int[i];
        this.f = leng;
        System.arraycopy(bs, 0, this.h, 0, Math.min(i, bs.length));
        y();
    }

    public GF2Polynomial(GF2Polynomial b2) {
        this.f = b2.f;
        this.g = b2.g;
        this.h = IntUtils.a(b2.h);
    }

    public Object clone() {
        return new GF2Polynomial(this);
    }

    public String v(int radix) {
        char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        String[] BIN_CHARS = {MeshStorage.Defaults.ADDRESS_INVALID, "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
        String res = new String();
        if (radix == 16) {
            for (int i = this.g - 1; i >= 0; i--) {
                res = ((((((((res + HEX_CHARS[(this.h[i] >>> 28) & 15]) + HEX_CHARS[(this.h[i] >>> 24) & 15]) + HEX_CHARS[(this.h[i] >>> 20) & 15]) + HEX_CHARS[(this.h[i] >>> 16) & 15]) + HEX_CHARS[(this.h[i] >>> 12) & 15]) + HEX_CHARS[(this.h[i] >>> 8) & 15]) + HEX_CHARS[(this.h[i] >>> 4) & 15]) + HEX_CHARS[this.h[i] & 15]) + " ";
            }
        } else {
            for (int i2 = this.g - 1; i2 >= 0; i2--) {
                res = ((((((((res + BIN_CHARS[(this.h[i2] >>> 28) & 15]) + BIN_CHARS[(this.h[i2] >>> 24) & 15]) + BIN_CHARS[(this.h[i2] >>> 20) & 15]) + BIN_CHARS[(this.h[i2] >>> 16) & 15]) + BIN_CHARS[(this.h[i2] >>> 12) & 15]) + BIN_CHARS[(this.h[i2] >>> 8) & 15]) + BIN_CHARS[(this.h[i2] >>> 4) & 15]) + BIN_CHARS[this.h[i2] & 15]) + " ";
            }
        }
        return res;
    }

    public void d() {
        for (int i = 1; i < this.g; i++) {
            this.h[i] = 0;
        }
        this.h[0] = 1;
    }

    public void e() {
        for (int i = 1; i < this.g; i++) {
            this.h[i] = 0;
        }
        this.h[0] = 2;
    }

    public void c() {
        for (int i = 0; i < this.g; i++) {
            this.h[i] = -1;
        }
        y();
    }

    public void f() {
        for (int i = 0; i < this.g; i++) {
            this.h[i] = 0;
        }
    }

    public void m() {
        for (int i = 0; i < this.g; i++) {
            this.h[i] = a.nextInt();
        }
        y();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GF2Polynomial)) {
            return false;
        }
        GF2Polynomial otherPol = (GF2Polynomial) other;
        if (this.f != otherPol.f) {
            return false;
        }
        for (int i = 0; i < this.g; i++) {
            if (this.h[i] != otherPol.h[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.f + this.h.hashCode();
    }

    public boolean l() {
        if (this.f == 0) {
            return true;
        }
        for (int i = 0; i < this.g; i++) {
            if (this.h[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean k() {
        for (int i = 1; i < this.g; i++) {
            if (this.h[i] != 0) {
                return false;
            }
        }
        if (this.h[0] != 1) {
            return false;
        }
        return true;
    }

    public void b(GF2Polynomial b2) {
        h(b2.f);
        x(b2);
    }

    public GF2Polynomial a(GF2Polynomial b2) {
        return w(b2);
    }

    public void u(GF2Polynomial b2) {
        h(b2.f);
        x(b2);
    }

    public GF2Polynomial o(GF2Polynomial g2) {
        GF2Polynomial a2 = new GF2Polynomial(this);
        GF2Polynomial b2 = new GF2Polynomial(g2);
        if (!b2.l()) {
            a2.n();
            b2.n();
            int i = a2.f;
            int i2 = b2.f;
            if (i < i2) {
                return a2;
            }
            for (int i3 = i - i2; i3 >= 0; i3 = a2.f - b2.f) {
                a2.u(b2.s(i3));
                a2.n();
            }
            return a2;
        }
        throw new RuntimeException();
    }

    public GF2Polynomial i(GF2Polynomial g2) {
        if (l() && g2.l()) {
            throw new ArithmeticException("Both operands of gcd equal zero.");
        } else if (l()) {
            return new GF2Polynomial(g2);
        } else {
            if (g2.l()) {
                return new GF2Polynomial(this);
            }
            GF2Polynomial a2 = new GF2Polynomial(this);
            GF2Polynomial b2 = new GF2Polynomial(g2);
            while (!b2.l()) {
                GF2Polynomial c2 = a2.o(b2);
                a2 = b2;
                b2 = c2;
            }
            return a2;
        }
    }

    public boolean j() {
        if (l()) {
            return false;
        }
        GF2Polynomial f2 = new GF2Polynomial(this);
        f2.n();
        int i = f2.f;
        int d2 = i - 1;
        GF2Polynomial u = new GF2Polynomial(i, "X");
        for (int i2 = 1; i2 <= (d2 >> 1); i2++) {
            u.t();
            u = u.o(f2);
            GF2Polynomial dummy = u.a(new GF2Polynomial(32, "X"));
            if (dummy.l() || !f2.i(dummy).k()) {
                return false;
            }
        }
        return true;
    }

    public void n() {
        int[] iArr;
        int i = this.g;
        while (true) {
            i--;
            iArr = this.h;
            if (iArr[i] != 0 || i <= 0) {
                int h2 = iArr[i];
                int j = 0;
            }
        }
        int h22 = iArr[i];
        int j2 = 0;
        while (h22 != 0) {
            h22 >>>= 1;
            j2++;
        }
        this.f = (i << 5) + j2;
        this.g = i + 1;
    }

    public void h(int i) {
        if (this.f < i) {
            this.f = i;
            int k = ((i - 1) >>> 5) + 1;
            int i2 = this.g;
            if (i2 < k) {
                int[] iArr = this.h;
                if (iArr.length >= k) {
                    for (int j = this.g; j < k; j++) {
                        this.h[j] = 0;
                    }
                    this.g = k;
                    return;
                }
                int[] bs = new int[k];
                System.arraycopy(iArr, 0, bs, 0, i2);
                this.g = k;
                this.h = null;
                this.h = bs;
            }
        }
    }

    public void t() {
        if (!l()) {
            int length = this.h.length;
            int i = this.g;
            if (length >= (i << 1)) {
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    int[] iArr = this.h;
                    short[] sArr = c;
                    iArr[(i2 << 1) + 1] = sArr[(iArr[i2] & 16711680) >>> 16] | (sArr[(iArr[i2] & ViewCompat.MEASURED_STATE_MASK) >>> 24] << 16);
                    iArr[i2 << 1] = (sArr[(iArr[i2] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8] << 16) | sArr[iArr[i2] & 255];
                }
                this.g <<= 1;
                this.f = (this.f << 1) - 1;
                return;
            }
            int[] result = new int[(i << 1)];
            int i3 = 0;
            while (true) {
                int i4 = this.g;
                if (i3 < i4) {
                    short[] sArr2 = c;
                    int[] iArr2 = this.h;
                    result[i3 << 1] = sArr2[iArr2[i3] & 255] | (sArr2[(iArr2[i3] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8] << 16);
                    result[(i3 << 1) + 1] = (sArr2[(iArr2[i3] & ViewCompat.MEASURED_STATE_MASK) >>> 24] << 16) | sArr2[(iArr2[i3] & 16711680) >>> 16];
                    i3++;
                } else {
                    this.h = null;
                    this.h = result;
                    this.g = i4 << 1;
                    this.f = (this.f << 1) - 1;
                    return;
                }
            }
        }
    }

    public GF2Polynomial w(GF2Polynomial b2) {
        GF2Polynomial result;
        int k = Math.min(this.g, b2.g);
        if (this.f >= b2.f) {
            result = new GF2Polynomial(this);
            for (int i = 0; i < k; i++) {
                int[] iArr = result.h;
                iArr[i] = iArr[i] ^ b2.h[i];
            }
        } else {
            result = new GF2Polynomial(b2);
            for (int i2 = 0; i2 < k; i2++) {
                int[] iArr2 = result.h;
                iArr2[i2] = iArr2[i2] ^ this.h[i2];
            }
        }
        result.y();
        return result;
    }

    public void x(GF2Polynomial b2) {
        for (int i = 0; i < Math.min(this.g, b2.g); i++) {
            int[] iArr = this.h;
            iArr[i] = iArr[i] ^ b2.h[i];
        }
        y();
    }

    private void y() {
        int i = this.f;
        if ((i & 31) != 0) {
            int[] iArr = this.h;
            int i2 = this.g - 1;
            iArr[i2] = e[i & 31] & iArr[i2];
        }
    }

    public void q(int i) {
        if (i < 0 || i > this.f - 1) {
            throw new RuntimeException();
        }
        int[] iArr = this.h;
        int i2 = i >>> 5;
        iArr[i2] = iArr[i2] | d[i & 31];
    }

    public void p(int i) {
        if (i < 0) {
            throw new RuntimeException();
        } else if (i <= this.f - 1) {
            int[] iArr = this.h;
            int i2 = i >>> 5;
            iArr[i2] = iArr[i2] & (~d[i & 31]);
        }
    }

    public GF2Polynomial r() {
        GF2Polynomial result = new GF2Polynomial(this.f + 1, this.h);
        for (int i = result.g - 1; i >= 1; i--) {
            int[] iArr = result.h;
            iArr[i] = iArr[i] << 1;
            iArr[i] = iArr[i] | (iArr[i - 1] >>> 31);
        }
        int[] iArr2 = result.h;
        iArr2[0] = iArr2[0] << 1;
        return result;
    }

    public GF2Polynomial s(int k) {
        GF2Polynomial result = new GF2Polynomial(this.f + k, this.h);
        if (k >= 32) {
            result.g(k >>> 5);
        }
        int remaining = k & 31;
        if (remaining != 0) {
            for (int i = result.g - 1; i >= 1; i--) {
                int[] iArr = result.h;
                iArr[i] = iArr[i] << remaining;
                iArr[i] = iArr[i] | (iArr[i - 1] >>> (32 - remaining));
            }
            int[] iArr2 = result.h;
            iArr2[0] = iArr2[0] << remaining;
        }
        return result;
    }

    private void g(int b2) {
        int i = this.g;
        int[] iArr = this.h;
        if (i <= iArr.length) {
            for (int i2 = i - 1; i2 >= b2; i2--) {
                int[] iArr2 = this.h;
                iArr2[i2] = iArr2[i2 - b2];
            }
            for (int i3 = 0; i3 < b2; i3++) {
                this.h[i3] = 0;
            }
            return;
        }
        int[] result = new int[i];
        System.arraycopy(iArr, 0, result, b2, i - b2);
        this.h = null;
        this.h = result;
    }
}

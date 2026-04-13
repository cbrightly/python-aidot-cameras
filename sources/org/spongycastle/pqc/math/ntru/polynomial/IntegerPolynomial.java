package org.spongycastle.pqc.math.ntru.polynomial;

import com.google.android.libraries.places.api.net.PlacesStatusCodes;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import meshsdk.util.LDSModel;
import org.spongycastle.pqc.math.ntru.euclid.BigIntEuclidean;
import org.spongycastle.pqc.math.ntru.util.ArrayEncoder;
import org.spongycastle.pqc.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class IntegerPolynomial implements Polynomial {
    private static final int[] a = {4507, 4513, 4517, 4519, 4523, 4547, 4549, 4561, 4567, 4583, 4591, 4597, 4603, 4621, 4637, 4639, 4643, 4649, 4651, 4657, 4663, 4673, 4679, 4691, 4703, 4721, 4723, 4729, 4733, 4751, 4759, 4783, 4787, 4789, 4793, 4799, 4801, 4813, 4817, 4831, 4861, LDSModel.MODEL_HSL_CTRL, 4877, 4889, 4903, 4909, 4919, 4931, 4933, 4937, 4943, 4951, 4957, 4967, 4969, 4973, 4987, 4993, 4999, 5003, 5009, 5011, 5021, 5023, 5039, 5051, 5059, 5077, 5081, 5087, 5099, 5101, 5107, 5113, 5119, 5147, AVIOCTRLDEFs.CMD_AVIO_CTRL_LIVE_PLAY_PAUSED_RESP, 5167, 5171, 5179, 5189, 5197, 5209, 5227, 5231, 5233, 5237, 5261, 5273, 5279, 5281, 5297, 5303, 5309, 5323, 5333, 5347, 5351, 5381, 5387, 5393, 5399, 5407, 5413, 5417, 5419, 5431, 5437, 5441, 5443, 5449, 5471, 5477, 5479, 5483, 5501, 5503, 5507, 5519, 5521, 5527, 5531, 5557, 5563, 5569, 5573, 5581, 5591, 5623, 5639, 5641, 5647, 5651, 5653, 5657, 5659, 5669, 5683, 5689, 5693, 5701, 5711, 5717, 5737, 5741, 5743, 5749, 5779, 5783, 5791, 5801, 5807, 5813, 5821, 5827, 5839, 5843, 5849, 5851, 5857, 5861, 5867, 5869, 5879, 5881, 5897, 5903, 5923, 5927, 5939, 5953, 5981, 5987, 6007, 6011, 6029, 6037, 6043, 6047, 6053, 6067, 6073, 6079, 6089, 6091, 6101, 6113, 6121, 6131, 6133, 6143, 6151, 6163, 6173, 6197, 6199, 6203, 6211, 6217, 6221, 6229, 6247, 6257, 6263, 6269, 6271, 6277, 6287, 6299, 6301, 6311, 6317, 6323, 6329, 6337, 6343, 6353, 6359, 6361, 6367, 6373, 6379, 6389, 6397, 6421, 6427, 6449, 6451, 6469, 6473, 6481, 6491, 6521, 6529, 6547, 6551, 6553, 6563, 6569, 6571, 6577, 6581, 6599, 6607, 6619, 6637, 6653, 6659, 6661, 6673, 6679, 6689, 6691, 6701, 6703, 6709, 6719, 6733, 6737, 6761, 6763, 6779, 6781, 6791, 6793, 6803, 6823, 6827, 6829, 6833, 6841, 6857, 6863, 6869, 6871, 6883, 6899, 6907, 6911, 6917, 6947, 6949, 6959, 6961, 6967, 6971, 6977, 6983, 6991, 6997, 7001, 7013, 7019, 7027, 7039, 7043, 7057, 7069, 7079, 7103, 7109, 7121, 7127, 7129, 7151, 7159, 7177, 7187, 7193, 7207, 7211, 7213, 7219, 7229, 7237, 7243, 7247, 7253, 7283, 7297, 7307, 7309, 7321, 7331, 7333, 7349, 7351, 7369, 7393, 7411, 7417, 7433, 7451, 7457, 7459, 7477, 7481, 7487, 7489, 7499, 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559, 7561, 7573, 7577, 7583, 7589, 7591, 7603, 7607, 7621, 7639, 7643, 7649, 7669, 7673, 7681, 7687, 7691, 7699, 7703, 7717, 7723, 7727, 7741, 7753, 7757, 7759, 7789, 7793, 7817, 7823, 7829, 7841, 7853, 7867, 7873, 7877, 7879, 7883, 7901, 7907, 7919, 7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017, 8039, 8053, 8059, 8069, 8081, 8087, 8089, 8093, 8101, 8111, 8117, 8123, 8147, 8161, 8167, 8171, 8179, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_EVENT_REPORT, 8209, 8219, 8221, 8231, 8233, 8237, 8243, 8263, 8269, 8273, 8287, 8291, 8293, 8297, 8311, 8317, 8329, 8353, 8363, 8369, 8377, 8387, 8389, 8419, 8423, 8429, 8431, 8443, 8447, 8461, 8467, 8501, 8513, 8521, 8527, 8537, 8539, 8543, 8563, 8573, 8581, 8597, 8599, 8609, 8623, 8627, 8629, 8641, 8647, 8663, 8669, 8677, 8681, 8689, 8693, 8699, 8707, 8713, 8719, 8731, 8737, 8741, 8747, 8753, 8761, 8779, 8783, 8803, 8807, 8819, 8821, 8831, 8837, 8839, 8849, 8861, 8863, 8867, 8887, 8893, 8923, 8929, 8933, 8941, 8951, 8963, 8969, 8971, 8999, 9001, 9007, PlacesStatusCodes.REQUEST_DENIED, PlacesStatusCodes.NOT_FOUND, 9029, 9041, 9043, 9049, 9059, 9067, 9091, 9103, 9109, 9127, 9133, 9137, 9151, 9157, 9161, 9173, 9181, 9187, 9199, 9203, 9209, 9221, 9227, 9239, 9241, 9257, 9277, 9281, 9283, 9293, 9311, 9319, 9323, 9337, 9341, 9343, 9349, 9371, 9377, 9391, 9397, 9403, 9413, 9419, 9421, 9431, 9433, 9437, 9439, 9461, 9463, 9467, 9473, 9479, 9491, 9497, 9511, 9521, 9533, 9539, 9547, 9551, 9587, 9601, 9613, 9619, 9623, 9629, 9631, 9643, 9649, 9661, 9677, 9679, 9689, 9697, 9719, 9721, 9733, 9739, 9743, 9749, 9767, 9769, 9781, 9787, 9791, 9803, 9811, 9817, 9829, 9833, 9839, 9851, 9857, 9859, 9871, 9883, 9887, 9901, 9907, 9923, 9929, 9931, 9941, 9949, 9967, 9973};
    /* access modifiers changed from: private */
    public static final List b = new ArrayList();
    public int[] c;

    static {
        int i = 0;
        while (true) {
            int[] iArr = a;
            if (i != iArr.length) {
                b.add(BigInteger.valueOf((long) iArr[i]));
                i++;
            } else {
                return;
            }
        }
    }

    public IntegerPolynomial(int N) {
        this.c = new int[N];
    }

    public IntegerPolynomial(int[] coeffs) {
        this.c = coeffs;
    }

    public IntegerPolynomial(BigIntPolynomial p) {
        this.c = new int[p.b.length];
        int i = 0;
        while (true) {
            BigInteger[] bigIntegerArr = p.b;
            if (i < bigIntegerArr.length) {
                this.c[i] = bigIntegerArr[i].intValue();
                i++;
            } else {
                return;
            }
        }
    }

    public static IntegerPolynomial t(byte[] data, int N) {
        return new IntegerPolynomial(ArrayEncoder.a(data, N));
    }

    public static IntegerPolynomial s(byte[] data, int N, int q) {
        return new IntegerPolynomial(ArrayEncoder.b(data, N, q));
    }

    public byte[] Q() {
        return ArrayEncoder.c(this.c);
    }

    public byte[] P(int q) {
        return ArrayEncoder.d(this.c, q);
    }

    public IntegerPolynomial c(IntegerPolynomial poly2, int modulus) {
        IntegerPolynomial c2 = e(poly2);
        c2.w(modulus);
        return c2;
    }

    public IntegerPolynomial e(IntegerPolynomial poly2) {
        int[] iArr;
        int N = this.c.length;
        if (poly2.c.length == N) {
            IntegerPolynomial c2 = E(poly2);
            if (c2.c.length > N) {
                int k = N;
                while (true) {
                    iArr = c2.c;
                    if (k >= iArr.length) {
                        break;
                    }
                    int i = k - N;
                    iArr[i] = iArr[i] + iArr[k];
                    k++;
                }
                c2.c = Arrays.y(iArr, N);
            }
            return c2;
        }
        throw new IllegalArgumentException("Number of coefficients must be the same");
    }

    public BigIntPolynomial b(BigIntPolynomial poly2) {
        return new BigIntPolynomial(this).g(poly2);
    }

    private IntegerPolynomial E(IntegerPolynomial poly2) {
        IntegerPolynomial integerPolynomial = poly2;
        int[] a2 = this.c;
        int[] b2 = integerPolynomial.c;
        int n = integerPolynomial.c.length;
        if (n <= 32) {
            int cn = (n * 2) - 1;
            IntegerPolynomial c2 = new IntegerPolynomial(new int[cn]);
            for (int k = 0; k < cn; k++) {
                for (int i = Math.max(0, (k - n) + 1); i <= Math.min(k, n - 1); i++) {
                    int[] iArr = c2.c;
                    iArr[k] = iArr[k] + (b2[i] * a2[k - i]);
                }
            }
            return c2;
        }
        int n1 = n / 2;
        IntegerPolynomial a1 = new IntegerPolynomial(Arrays.y(a2, n1));
        IntegerPolynomial a22 = new IntegerPolynomial(Arrays.C(a2, n1, n));
        IntegerPolynomial b1 = new IntegerPolynomial(Arrays.y(b2, n1));
        IntegerPolynomial b22 = new IntegerPolynomial(Arrays.C(b2, n1, n));
        IntegerPolynomial A = (IntegerPolynomial) a1.clone();
        A.h(a22);
        IntegerPolynomial B = (IntegerPolynomial) b1.clone();
        B.h(b22);
        IntegerPolynomial c1 = a1.E(b1);
        IntegerPolynomial c22 = a22.E(b22);
        IntegerPolynomial c3 = A.E(B);
        c3.M(c1);
        c3.M(c22);
        IntegerPolynomial c4 = new IntegerPolynomial((n * 2) - 1);
        int i2 = 0;
        while (true) {
            int[] iArr2 = c1.c;
            int[] a3 = a2;
            if (i2 >= iArr2.length) {
                break;
            }
            c4.c[i2] = iArr2[i2];
            i2++;
            a2 = a3;
        }
        int i3 = 0;
        while (true) {
            int[] iArr3 = c3.c;
            if (i3 >= iArr3.length) {
                break;
            }
            int[] iArr4 = c4.c;
            int i4 = n1 + i3;
            iArr4[i4] = iArr4[i4] + iArr3[i3];
            i3++;
        }
        int i5 = 0;
        while (true) {
            int[] iArr5 = c22.c;
            if (i5 >= iArr5.length) {
                return c4;
            }
            int[] iArr6 = c4.c;
            int i6 = (n1 * 2) + i5;
            iArr6[i6] = iArr6[i6] + iArr5[i5];
            i5++;
        }
    }

    public IntegerPolynomial v(int q) {
        int N = this.c.length;
        int k = 0;
        IntegerPolynomial b2 = new IntegerPolynomial(N + 1);
        b2.c[0] = 1;
        IntegerPolynomial c2 = new IntegerPolynomial(N + 1);
        IntegerPolynomial f = new IntegerPolynomial(N + 1);
        f.c = Arrays.y(this.c, N + 1);
        f.A(2);
        IntegerPolynomial g = new IntegerPolynomial(N + 1);
        int[] iArr = g.c;
        iArr[0] = 1;
        iArr[N] = 1;
        while (true) {
            if (f.c[0] == 0) {
                for (int i = 1; i <= N; i++) {
                    int[] iArr2 = f.c;
                    iArr2[i - 1] = iArr2[i];
                    int[] iArr3 = c2.c;
                    iArr3[(N + 1) - i] = iArr3[N - i];
                }
                f.c[N] = 0;
                c2.c[0] = 0;
                k++;
                if (f.r()) {
                    return null;
                }
            } else if (!f.q()) {
                if (f.n() < g.n()) {
                    IntegerPolynomial temp = f;
                    f = g;
                    g = temp;
                    IntegerPolynomial temp2 = b2;
                    b2 = c2;
                    c2 = temp2;
                }
                f.i(g, 2);
                b2.i(c2, 2);
            } else if (b2.c[N] != 0) {
                return null;
            } else {
                IntegerPolynomial Fq = new IntegerPolynomial(N);
                int k2 = k % N;
                for (int i2 = N - 1; i2 >= 0; i2--) {
                    int j = i2 - k2;
                    if (j < 0) {
                        j += N;
                    }
                    Fq.c[j] = b2.c[i2];
                }
                return x(Fq, q);
            }
        }
    }

    private IntegerPolynomial x(IntegerPolynomial Fq, int q) {
        if (!Util.d() || q != 2048) {
            int v = 2;
            while (v < q) {
                v *= 2;
                int[] iArr = Fq.c;
                IntegerPolynomial temp = new IntegerPolynomial(Arrays.y(iArr, iArr.length));
                temp.C(v);
                temp.N(c(Fq, v).c(Fq, v), v);
                Fq = temp;
            }
            return Fq;
        }
        LongPolynomial2 thisLong = new LongPolynomial2(this);
        LongPolynomial2 FqLong = new LongPolynomial2(Fq);
        int v2 = 2;
        while (v2 < q) {
            v2 *= 2;
            LongPolynomial2 temp2 = (LongPolynomial2) FqLong.clone();
            temp2.c(v2 - 1);
            temp2.f(thisLong.b(FqLong).b(FqLong), v2 - 1);
            FqLong = temp2;
        }
        return FqLong.g();
    }

    public IntegerPolynomial u() {
        int N = this.c.length;
        int k = 0;
        IntegerPolynomial b2 = new IntegerPolynomial(N + 1);
        b2.c[0] = 1;
        IntegerPolynomial c2 = new IntegerPolynomial(N + 1);
        IntegerPolynomial f = new IntegerPolynomial(N + 1);
        f.c = Arrays.y(this.c, N + 1);
        f.A(3);
        IntegerPolynomial g = new IntegerPolynomial(N + 1);
        int[] iArr = g.c;
        iArr[0] = -1;
        iArr[N] = 1;
        while (true) {
            if (f.c[0] == 0) {
                for (int i = 1; i <= N; i++) {
                    int[] iArr2 = f.c;
                    iArr2[i - 1] = iArr2[i];
                    int[] iArr3 = c2.c;
                    iArr3[(N + 1) - i] = iArr3[N - i];
                }
                f.c[N] = 0;
                c2.c[0] = 0;
                k++;
                if (f.r()) {
                    return null;
                }
            } else if (!f.p()) {
                if (f.n() < g.n()) {
                    IntegerPolynomial temp = f;
                    f = g;
                    g = temp;
                    IntegerPolynomial temp2 = b2;
                    b2 = c2;
                    c2 = temp2;
                }
                if (f.c[0] == g.c[0]) {
                    f.N(g, 3);
                    b2.N(c2, 3);
                } else {
                    f.i(g, 3);
                    b2.i(c2, 3);
                }
            } else if (b2.c[N] != 0) {
                return null;
            } else {
                IntegerPolynomial Fp = new IntegerPolynomial(N);
                int k2 = k % N;
                for (int i2 = N - 1; i2 >= 0; i2--) {
                    int j = i2 - k2;
                    if (j < 0) {
                        j += N;
                    }
                    Fp.c[j] = f.c[0] * b2.c[i2];
                }
                Fp.o(3);
                return Fp;
            }
        }
    }

    public Resultant H() {
        int N = this.c.length;
        LinkedList<ModularResultant> modResultants = new LinkedList<>();
        BigInteger pProd = Constants.b;
        BigInteger res = Constants.b;
        int numEqual = 1;
        PrimeGenerator primes = new PrimeGenerator();
        while (true) {
            BigInteger prime = primes.a();
            ModularResultant crr = G(prime.intValue());
            modResultants.add(crr);
            BigInteger temp = pProd.multiply(prime);
            BigIntEuclidean er = BigIntEuclidean.a(prime, pProd);
            BigInteger resPrev = res;
            res = res.multiply(er.a.multiply(prime)).add(crr.b.multiply(er.b.multiply(pProd))).mod(temp);
            pProd = temp;
            BigInteger pProd2 = pProd.divide(BigInteger.valueOf(2));
            BigInteger pProd2n = pProd2.negate();
            if (res.compareTo(pProd2) > 0) {
                res = res.subtract(pProd);
            } else if (res.compareTo(pProd2n) < 0) {
                res = res.add(pProd);
            }
            if (res.equals(resPrev)) {
                numEqual++;
                if (numEqual >= 3) {
                    break;
                }
            } else {
                numEqual = 1;
            }
        }
        while (modResultants.size() > 1) {
            modResultants.addLast(ModularResultant.a(modResultants.removeFirst(), modResultants.removeFirst()));
        }
        BigIntPolynomial rhoP = modResultants.getFirst().a;
        BigInteger pProd22 = pProd.divide(BigInteger.valueOf(2));
        BigInteger pProd2n2 = pProd22.negate();
        if (res.compareTo(pProd22) > 0) {
            res = res.subtract(pProd);
        }
        if (res.compareTo(pProd2n2) < 0) {
            res = res.add(pProd);
        }
        for (int i = 0; i < N; i++) {
            BigInteger c2 = rhoP.b[i];
            if (c2.compareTo(pProd22) > 0) {
                rhoP.b[i] = c2.subtract(pProd);
            }
            if (c2.compareTo(pProd2n2) < 0) {
                rhoP.b[i] = c2.add(pProd);
            }
        }
        return new Resultant(rhoP, res);
    }

    public ModularResultant G(int p) {
        int i = p;
        int[] iArr = this.c;
        int[] fcoeffs = Arrays.y(iArr, iArr.length + 1);
        IntegerPolynomial f = new IntegerPolynomial(fcoeffs);
        int N = fcoeffs.length;
        IntegerPolynomial a2 = new IntegerPolynomial(N);
        int[] iArr2 = a2.c;
        iArr2[0] = -1;
        iArr2[N - 1] = 1;
        IntegerPolynomial b2 = new IntegerPolynomial(f.c);
        IntegerPolynomial v1 = new IntegerPolynomial(N);
        IntegerPolynomial v2 = new IntegerPolynomial(N);
        v2.c[0] = 1;
        int da = N - 1;
        int db = b2.n();
        int ta = da;
        int r = 1;
        while (db > 0) {
            int c2 = (a2.c[da] * Util.c(b2.c[db], i)) % i;
            a2.F(b2, c2, da - db, i);
            v1.F(v2, c2, da - db, i);
            da = a2.n();
            if (da < db) {
                r = (r * Util.e(b2.c[db], ta - da, i)) % i;
                if (ta % 2 == 1 && db % 2 == 1) {
                    r = (-r) % i;
                }
                IntegerPolynomial temp = a2;
                a2 = b2;
                b2 = temp;
                int tempdeg = da;
                da = db;
                IntegerPolynomial temp2 = v1;
                v1 = v2;
                v2 = temp2;
                ta = db;
                db = tempdeg;
            }
        }
        int r2 = (r * Util.e(b2.c[0], da, i)) % i;
        v2.B(Util.c(b2.c[0], i));
        v2.w(i);
        v2.B(r2);
        v2.w(i);
        int[] iArr3 = v2.c;
        v2.c = Arrays.y(iArr3, iArr3.length - 1);
        int[] iArr4 = fcoeffs;
        IntegerPolynomial integerPolynomial = f;
        return new ModularResultant(new BigIntPolynomial(v2), BigInteger.valueOf((long) r2), BigInteger.valueOf((long) i));
    }

    private void F(IntegerPolynomial b2, int c2, int k, int p) {
        int N = this.c.length;
        for (int i = k; i < N; i++) {
            int[] iArr = this.c;
            iArr[i] = (iArr[i] - (b2.c[i - k] * c2)) % p;
        }
    }

    /* access modifiers changed from: package-private */
    public int n() {
        int degree = this.c.length - 1;
        while (degree > 0 && this.c[degree] == 0) {
            degree--;
        }
        return degree;
    }

    public void i(IntegerPolynomial b2, int modulus) {
        h(b2);
        w(modulus);
    }

    public void h(IntegerPolynomial b2) {
        int[] iArr = b2.c;
        int length = iArr.length;
        int[] iArr2 = this.c;
        if (length > iArr2.length) {
            this.c = Arrays.y(iArr2, iArr.length);
        }
        int i = 0;
        while (true) {
            int[] iArr3 = b2.c;
            if (i < iArr3.length) {
                int[] iArr4 = this.c;
                iArr4[i] = iArr4[i] + iArr3[i];
                i++;
            } else {
                return;
            }
        }
    }

    public void N(IntegerPolynomial b2, int modulus) {
        M(b2);
        w(modulus);
    }

    public void M(IntegerPolynomial b2) {
        int[] iArr = b2.c;
        int length = iArr.length;
        int[] iArr2 = this.c;
        if (length > iArr2.length) {
            this.c = Arrays.y(iArr2, iArr.length);
        }
        int i = 0;
        while (true) {
            int[] iArr3 = b2.c;
            if (i < iArr3.length) {
                int[] iArr4 = this.c;
                iArr4[i] = iArr4[i] - iArr3[i];
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void L(int b2) {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = iArr[i] - b2;
                i++;
            } else {
                return;
            }
        }
    }

    public void B(int factor) {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = iArr[i] * factor;
                i++;
            } else {
                return;
            }
        }
    }

    private void C(int modulus) {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = iArr[i] * 2;
                iArr[i] = iArr[i] % modulus;
                i++;
            } else {
                return;
            }
        }
    }

    public void D(int modulus) {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = iArr[i] * 3;
                iArr[i] = iArr[i] % modulus;
                i++;
            } else {
                return;
            }
        }
    }

    public void y() {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = iArr[i] % 3;
                if (iArr[i] > 1) {
                    iArr[i] = iArr[i] - 3;
                }
                if (iArr[i] < -1) {
                    iArr[i] = iArr[i] + 3;
                }
                i++;
            } else {
                return;
            }
        }
    }

    public void A(int modulus) {
        w(modulus);
        o(modulus);
    }

    /* access modifiers changed from: package-private */
    public void z(int modulus) {
        w(modulus);
        for (int j = 0; j < this.c.length; j++) {
            while (true) {
                int[] iArr = this.c;
                if (iArr[j] >= modulus / 2) {
                    break;
                }
                iArr[j] = iArr[j] + modulus;
            }
            while (true) {
                int[] iArr2 = this.c;
                if (iArr2[j] < modulus / 2) {
                    break;
                }
                iArr2[j] = iArr2[j] - modulus;
            }
        }
    }

    public void w(int modulus) {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = iArr[i] % modulus;
                i++;
            } else {
                return;
            }
        }
    }

    public void o(int modulus) {
        for (int i = 0; i < this.c.length; i++) {
            while (true) {
                int[] iArr = this.c;
                if (iArr[i] >= 0) {
                    break;
                }
                iArr[i] = iArr[i] + modulus;
            }
        }
    }

    public long k(int q) {
        int N = this.c.length;
        IntegerPolynomial p = (IntegerPolynomial) clone();
        p.J(q);
        long sum = 0;
        long sqSum = 0;
        int i = 0;
        while (true) {
            int[] iArr = p.c;
            if (i == iArr.length) {
                return sqSum - ((sum * sum) / ((long) N));
            }
            int c2 = iArr[i];
            sum += (long) c2;
            sqSum += (long) (c2 * c2);
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public void J(int q) {
        int shift;
        z(q);
        int[] sorted = Arrays.k(this.c);
        K(sorted);
        int maxrange = 0;
        int maxrangeStart = 0;
        for (int i = 0; i < sorted.length - 1; i++) {
            int range = sorted[i + 1] - sorted[i];
            if (range > maxrange) {
                maxrange = range;
                maxrangeStart = sorted[i];
            }
        }
        int pmin = sorted[0];
        int pmax = sorted[sorted.length - 1];
        if ((q - pmax) + pmin > maxrange) {
            shift = (pmax + pmin) / 2;
        } else {
            shift = (maxrange / 2) + maxrangeStart + (q / 2);
        }
        L(shift);
    }

    private void K(int[] ints) {
        boolean swap = true;
        while (swap) {
            swap = false;
            for (int i = 0; i != ints.length - 1; i++) {
                if (ints[i] > ints[i + 1]) {
                    int tmp = ints[i];
                    ints[i] = ints[i + 1];
                    ints[i + 1] = tmp;
                    swap = true;
                }
            }
        }
    }

    public void j(int q) {
        for (int i = 0; i < this.c.length; i++) {
            while (true) {
                int[] iArr = this.c;
                if (iArr[i] >= (-q) / 2) {
                    break;
                }
                iArr[i] = iArr[i] + q;
            }
            while (true) {
                int[] iArr2 = this.c;
                if (iArr2[i] <= q / 2) {
                    break;
                }
                iArr2[i] = iArr2[i] - q;
            }
        }
    }

    public int O() {
        int sum = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i >= iArr.length) {
                return sum;
            }
            sum += iArr[i];
            i++;
        }
    }

    private boolean r() {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i >= iArr.length) {
                return true;
            }
            if (iArr[i] != 0) {
                return false;
            }
            i++;
        }
    }

    public boolean q() {
        int i = 1;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                if (iArr[i] != 0) {
                    return false;
                }
                i++;
            } else if (iArr[0] == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean p() {
        int i = 1;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                if (iArr[i] != 0) {
                    return false;
                }
                i++;
            } else if (Math.abs(iArr[0]) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public int m(int value) {
        int count = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i == iArr.length) {
                return count;
            }
            if (iArr[i] == value) {
                count++;
            }
            i++;
        }
    }

    public void I() {
        int[] iArr = this.c;
        int clast = iArr[iArr.length - 1];
        for (int i = iArr.length - 1; i > 0; i--) {
            int[] iArr2 = this.c;
            iArr2[i] = iArr2[i - 1];
        }
        this.c[0] = clast;
    }

    public void l() {
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = 0;
                i++;
            } else {
                return;
            }
        }
    }

    public IntegerPolynomial a() {
        return (IntegerPolynomial) clone();
    }

    public Object clone() {
        return new IntegerPolynomial((int[]) this.c.clone());
    }

    public boolean equals(Object obj) {
        if (obj instanceof IntegerPolynomial) {
            return Arrays.d(this.c, ((IntegerPolynomial) obj).c);
        }
        return false;
    }

    public class ModResultantTask implements Callable<ModularResultant> {
        private int c;
        final /* synthetic */ IntegerPolynomial d;

        /* renamed from: a */
        public ModularResultant call() {
            return this.d.G(this.c);
        }
    }

    public class CombineTask implements Callable<ModularResultant> {
        private ModularResultant c;
        private ModularResultant d;

        /* renamed from: a */
        public ModularResultant call() {
            return ModularResultant.a(this.c, this.d);
        }
    }

    public class PrimeGenerator {
        private int a;
        private BigInteger b;

        private PrimeGenerator() {
            this.a = 0;
        }

        public BigInteger a() {
            if (this.a < IntegerPolynomial.b.size()) {
                List g = IntegerPolynomial.b;
                int i = this.a;
                this.a = i + 1;
                this.b = (BigInteger) g.get(i);
            } else {
                this.b = this.b.nextProbablePrime();
            }
            return this.b;
        }
    }
}

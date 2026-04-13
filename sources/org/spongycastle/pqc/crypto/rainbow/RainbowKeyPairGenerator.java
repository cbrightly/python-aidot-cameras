package org.spongycastle.pqc.crypto.rainbow;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.spongycastle.pqc.crypto.rainbow.util.GF2Field;

public class RainbowKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private boolean g = false;
    private SecureRandom h;
    private RainbowKeyGenerationParameters i;
    private short[][] j;
    private short[][] k;
    private short[] l;
    private short[][] m;
    private short[][] n;
    private short[] o;
    private int p;
    private Layer[] q;
    private int[] r;
    private short[][] s;
    private short[][] t;
    private short[] u;

    public AsymmetricCipherKeyPair d() {
        if (!this.g) {
            j();
        }
        k();
        RainbowPrivateKeyParameters rainbowPrivateKeyParameters = new RainbowPrivateKeyParameters(this.k, this.l, this.n, this.o, this.r, this.q);
        int[] iArr = this.r;
        return new AsymmetricCipherKeyPair(new RainbowPublicKeyParameters(iArr[iArr.length - 1] - iArr[0], this.s, this.t, this.u), rainbowPrivateKeyParameters);
    }

    public void i(KeyGenerationParameters param) {
        RainbowKeyGenerationParameters rainbowKeyGenerationParameters = (RainbowKeyGenerationParameters) param;
        this.i = rainbowKeyGenerationParameters;
        this.h = rainbowKeyGenerationParameters.a();
        this.r = this.i.c().c();
        this.p = this.i.c().b();
        this.g = true;
    }

    private void j() {
        i(new RainbowKeyGenerationParameters(new SecureRandom(), new RainbowParameters()));
    }

    private void k() {
        f();
        g();
        e();
        c();
    }

    private void f() {
        int[] iArr = this.r;
        int dim = iArr[iArr.length - 1] - iArr[0];
        int[] iArr2 = new int[2];
        iArr2[1] = dim;
        iArr2[0] = dim;
        this.j = (short[][]) Array.newInstance(short.class, iArr2);
        this.k = null;
        ComputeInField c = new ComputeInField();
        while (this.k == null) {
            for (int i2 = 0; i2 < dim; i2++) {
                for (int j2 = 0; j2 < dim; j2++) {
                    this.j[i2][j2] = (short) (this.h.nextInt() & 255);
                }
            }
            this.k = c.e(this.j);
        }
        this.l = new short[dim];
        for (int i3 = 0; i3 < dim; i3++) {
            this.l[i3] = (short) (this.h.nextInt() & 255);
        }
    }

    private void g() {
        int[] iArr = this.r;
        int dim = iArr[iArr.length - 1];
        int[] iArr2 = new int[2];
        iArr2[1] = dim;
        iArr2[0] = dim;
        this.m = (short[][]) Array.newInstance(short.class, iArr2);
        this.n = null;
        ComputeInField c = new ComputeInField();
        while (this.n == null) {
            for (int i2 = 0; i2 < dim; i2++) {
                for (int j2 = 0; j2 < dim; j2++) {
                    this.m[i2][j2] = (short) (this.h.nextInt() & 255);
                }
            }
            this.n = c.e(this.m);
        }
        this.o = new short[dim];
        for (int i3 = 0; i3 < dim; i3++) {
            this.o[i3] = (short) (this.h.nextInt() & 255);
        }
    }

    private void e() {
        this.q = new Layer[this.p];
        for (int i2 = 0; i2 < this.p; i2++) {
            Layer[] layerArr = this.q;
            int[] iArr = this.r;
            layerArr[i2] = new Layer(iArr[i2], iArr[i2 + 1], this.h);
        }
    }

    private void c() {
        Class<short> cls = short.class;
        ComputeInField c = new ComputeInField();
        int[] iArr = this.r;
        int rows = iArr[iArr.length - 1] - iArr[0];
        int vars = iArr[iArr.length - 1];
        int[] iArr2 = new int[3];
        iArr2[2] = vars;
        iArr2[1] = vars;
        iArr2[0] = rows;
        short[][][] coeff_quadratic_3dim = (short[][][]) Array.newInstance(cls, iArr2);
        int[] iArr3 = new int[2];
        iArr3[1] = vars;
        iArr3[0] = rows;
        this.t = (short[][]) Array.newInstance(cls, iArr3);
        this.u = new short[rows];
        int oils = 0;
        int vins = 0;
        int crnt_row = 0;
        short[] vect_tmp = new short[vars];
        short sclr_tmp = 0;
        int l2 = 0;
        while (true) {
            Layer[] layerArr = this.q;
            if (l2 >= layerArr.length) {
                break;
            }
            short[][][] coeff_alpha = layerArr[l2].a();
            short[][][] coeff_beta = this.q[l2].b();
            short[][] coeff_gamma = this.q[l2].d();
            short[] coeff_eta = this.q[l2].c();
            int i2 = oils;
            oils = coeff_alpha[0].length;
            int i3 = vins;
            vins = coeff_beta[0].length;
            short[] vect_tmp2 = vect_tmp;
            int p2 = 0;
            while (p2 < oils) {
                short sclr_tmp2 = sclr_tmp;
                int x1 = 0;
                while (x1 < oils) {
                    Class<short> cls2 = cls;
                    int x2 = 0;
                    while (x2 < vins) {
                        int rows2 = rows;
                        int vars2 = vars;
                        short[] vect_tmp3 = c.g(coeff_alpha[p2][x1][x2], this.m[x1 + vins]);
                        short[] coeff_eta2 = coeff_eta;
                        coeff_quadratic_3dim[crnt_row + p2] = c.a(coeff_quadratic_3dim[crnt_row + p2], c.h(vect_tmp3, this.m[x2]));
                        short[] vect_tmp4 = c.g(this.o[x2], vect_tmp3);
                        short[][] sArr = this.t;
                        sArr[crnt_row + p2] = c.b(vect_tmp4, sArr[crnt_row + p2]);
                        short[] vect_tmp5 = c.g(this.o[x1 + vins], c.g(coeff_alpha[p2][x1][x2], this.m[x2]));
                        short[][] sArr2 = this.t;
                        sArr2[crnt_row + p2] = c.b(vect_tmp5, sArr2[crnt_row + p2]);
                        short sclr_tmp3 = GF2Field.c(coeff_alpha[p2][x1][x2], this.o[x1 + vins]);
                        short[] sArr3 = this.u;
                        short[] vect_tmp6 = vect_tmp5;
                        sArr3[crnt_row + p2] = GF2Field.a(sArr3[crnt_row + p2], GF2Field.c(sclr_tmp3, this.o[x2]));
                        x2++;
                        sclr_tmp2 = sclr_tmp3;
                        vars = vars2;
                        rows = rows2;
                        l2 = l2;
                        coeff_eta = coeff_eta2;
                        vect_tmp2 = vect_tmp6;
                        coeff_alpha = coeff_alpha;
                    }
                    int i4 = rows;
                    short[][][] sArr4 = coeff_alpha;
                    short[] sArr5 = coeff_eta;
                    int i5 = l2;
                    x1++;
                    cls = cls2;
                }
                Class<short> cls3 = cls;
                int vars3 = vars;
                int rows3 = rows;
                short[][][] coeff_alpha2 = coeff_alpha;
                short[] coeff_eta3 = coeff_eta;
                int l3 = l2;
                sclr_tmp = sclr_tmp2;
                for (int x12 = 0; x12 < vins; x12++) {
                    for (int x22 = 0; x22 < vins; x22++) {
                        short[] vect_tmp7 = c.g(coeff_beta[p2][x12][x22], this.m[x12]);
                        coeff_quadratic_3dim[crnt_row + p2] = c.a(coeff_quadratic_3dim[crnt_row + p2], c.h(vect_tmp7, this.m[x22]));
                        short[] vect_tmp8 = c.g(this.o[x22], vect_tmp7);
                        short[][] sArr6 = this.t;
                        sArr6[crnt_row + p2] = c.b(vect_tmp8, sArr6[crnt_row + p2]);
                        short[] vect_tmp9 = c.g(this.o[x12], c.g(coeff_beta[p2][x12][x22], this.m[x22]));
                        short[][] sArr7 = this.t;
                        sArr7[crnt_row + p2] = c.b(vect_tmp9, sArr7[crnt_row + p2]);
                        sclr_tmp = GF2Field.c(coeff_beta[p2][x12][x22], this.o[x12]);
                        short[] sArr8 = this.u;
                        vect_tmp2 = vect_tmp9;
                        sArr8[crnt_row + p2] = GF2Field.a(sArr8[crnt_row + p2], GF2Field.c(sclr_tmp, this.o[x22]));
                    }
                }
                for (int n2 = 0; n2 < vins + oils; n2++) {
                    short[] vect_tmp10 = c.g(coeff_gamma[p2][n2], this.m[n2]);
                    short[][] sArr9 = this.t;
                    sArr9[crnt_row + p2] = c.b(vect_tmp10, sArr9[crnt_row + p2]);
                    short[] sArr10 = this.u;
                    vect_tmp2 = vect_tmp10;
                    sArr10[crnt_row + p2] = GF2Field.a(sArr10[crnt_row + p2], GF2Field.c(coeff_gamma[p2][n2], this.o[n2]));
                }
                short[] sArr11 = this.u;
                sArr11[crnt_row + p2] = GF2Field.a(sArr11[crnt_row + p2], coeff_eta3[p2]);
                p2++;
                cls = cls3;
                vars = vars3;
                rows = rows3;
                l2 = l3;
                coeff_eta = coeff_eta3;
                coeff_alpha = coeff_alpha2;
            }
            Class<short> cls4 = cls;
            int i6 = vars;
            int i7 = rows;
            short[][][] sArr12 = coeff_alpha;
            short[] sArr13 = coeff_eta;
            crnt_row += oils;
            l2++;
            vect_tmp = vect_tmp2;
        }
        Class<short> cls5 = cls;
        int vars4 = vars;
        int rows4 = rows;
        int i8 = oils;
        int i9 = vins;
        int i10 = l2;
        int[] iArr4 = new int[3];
        iArr4[2] = vars4;
        iArr4[1] = vars4;
        iArr4[0] = rows4;
        Class<short> cls6 = cls5;
        short[][][] tmp_c_quad = (short[][][]) Array.newInstance(cls6, iArr4);
        int[] iArr5 = new int[2];
        iArr5[1] = vars4;
        iArr5[0] = rows4;
        short[][] tmp_c_sing = (short[][]) Array.newInstance(cls6, iArr5);
        int rows5 = rows4;
        short[] tmp_c_scal = new short[rows5];
        for (int r2 = 0; r2 < rows5; r2++) {
            int q2 = 0;
            while (true) {
                short[][] sArr14 = this.j;
                if (q2 >= sArr14.length) {
                    break;
                }
                tmp_c_quad[r2] = c.a(tmp_c_quad[r2], c.f(sArr14[r2][q2], coeff_quadratic_3dim[q2]));
                tmp_c_sing[r2] = c.b(tmp_c_sing[r2], c.g(this.j[r2][q2], this.t[q2]));
                tmp_c_scal[r2] = GF2Field.a(tmp_c_scal[r2], GF2Field.c(this.j[r2][q2], this.u[q2]));
                q2++;
            }
            tmp_c_scal[r2] = GF2Field.a(tmp_c_scal[r2], this.l[r2]);
        }
        this.t = tmp_c_sing;
        this.u = tmp_c_scal;
        b(tmp_c_quad);
    }

    private void b(short[][][] coeff_quadratic_to_compact) {
        int polynomials = coeff_quadratic_to_compact.length;
        int n2 = coeff_quadratic_to_compact[0].length;
        int[] iArr = new int[2];
        iArr[1] = ((n2 + 1) * n2) / 2;
        iArr[0] = polynomials;
        this.s = (short[][]) Array.newInstance(short.class, iArr);
        for (int p2 = 0; p2 < polynomials; p2++) {
            int offset = 0;
            for (int x = 0; x < n2; x++) {
                for (int y = x; y < n2; y++) {
                    if (y == x) {
                        this.s[p2][offset] = coeff_quadratic_to_compact[p2][x][y];
                    } else {
                        this.s[p2][offset] = GF2Field.a(coeff_quadratic_to_compact[p2][x][y], coeff_quadratic_to_compact[p2][y][x]);
                    }
                    offset++;
                }
            }
        }
    }

    public void h(KeyGenerationParameters param) {
        i(param);
    }

    public AsymmetricCipherKeyPair a() {
        return d();
    }
}

package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;

public class GMSSLeaf {
    private Digest a;
    private int b;
    private int c;
    private GMSSRandom d;
    private byte[] e;
    private byte[] f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private byte[] l;
    byte[] m;

    GMSSLeaf(Digest digest, int w, int numLeafs) {
        this.j = w;
        this.a = digest;
        this.d = new GMSSRandom(digest);
        int e2 = this.a.e();
        this.b = e2;
        int messagesize = (int) Math.ceil(((double) (e2 << 3)) / ((double) w));
        int ceil = ((int) Math.ceil(((double) b((messagesize << w) + 1)) / ((double) w))) + messagesize;
        this.c = ceil;
        this.i = 1 << w;
        this.k = (int) Math.ceil(((double) (((((1 << w) - 1) * ceil) + 1) + ceil)) / ((double) numLeafs));
        int i2 = this.b;
        this.l = new byte[i2];
        this.e = new byte[i2];
        this.m = new byte[i2];
        this.f = new byte[(i2 * this.c)];
    }

    public GMSSLeaf(Digest digest, int w, int numLeafs, byte[] seed0) {
        this.j = w;
        this.a = digest;
        this.d = new GMSSRandom(digest);
        int e2 = this.a.e();
        this.b = e2;
        int messagesize = (int) Math.ceil(((double) (e2 << 3)) / ((double) w));
        int ceil = ((int) Math.ceil(((double) b((messagesize << w) + 1)) / ((double) w))) + messagesize;
        this.c = ceil;
        this.i = 1 << w;
        this.k = (int) Math.ceil(((double) (((((1 << w) - 1) * ceil) + 1) + ceil)) / ((double) numLeafs));
        int i2 = this.b;
        this.l = new byte[i2];
        this.e = new byte[i2];
        this.m = new byte[i2];
        this.f = new byte[(i2 * this.c)];
        e(seed0);
    }

    private GMSSLeaf(GMSSLeaf original) {
        this.a = original.a;
        this.b = original.b;
        this.c = original.c;
        this.d = original.d;
        this.e = Arrays.h(original.e);
        this.f = Arrays.h(original.f);
        this.g = original.g;
        this.h = original.h;
        this.i = original.i;
        this.j = original.j;
        this.k = original.k;
        this.l = Arrays.h(original.l);
        this.m = Arrays.h(original.m);
    }

    /* access modifiers changed from: package-private */
    public void e(byte[] seed0) {
        this.g = 0;
        this.h = 0;
        byte[] dummy = new byte[this.b];
        System.arraycopy(seed0, 0, dummy, 0, this.l.length);
        this.l = this.d.c(dummy);
    }

    /* access modifiers changed from: package-private */
    public GMSSLeaf f() {
        GMSSLeaf nextLeaf = new GMSSLeaf(this);
        nextLeaf.g();
        return nextLeaf;
    }

    private void g() {
        byte[] buf = new byte[this.a.e()];
        for (int s = 0; s < this.k + 10000; s++) {
            int i2 = this.g;
            if (i2 == this.c && this.h == this.i - 1) {
                Digest digest = this.a;
                byte[] bArr = this.f;
                digest.update(bArr, 0, bArr.length);
                byte[] bArr2 = new byte[this.a.e()];
                this.e = bArr2;
                this.a.c(bArr2, 0);
                return;
            }
            if (i2 == 0 || this.h == this.i - 1) {
                this.g = i2 + 1;
                this.h = 0;
                this.m = this.d.c(this.l);
            } else {
                Digest digest2 = this.a;
                byte[] bArr3 = this.m;
                digest2.update(bArr3, 0, bArr3.length);
                this.m = buf;
                this.a.c(buf, 0);
                int i3 = this.h + 1;
                this.h = i3;
                if (i3 == this.i - 1) {
                    byte[] bArr4 = this.m;
                    byte[] bArr5 = this.f;
                    int i4 = this.b;
                    System.arraycopy(bArr4, 0, bArr5, (this.g - 1) * i4, i4);
                }
            }
        }
        throw new IllegalStateException("unable to updateLeaf in steps: " + this.k + " " + this.g + " " + this.h);
    }

    public byte[] a() {
        return Arrays.h(this.e);
    }

    private int b(int intValue) {
        int log = 1;
        int i2 = 2;
        while (i2 < intValue) {
            i2 <<= 1;
            log++;
        }
        return log;
    }

    public byte[][] c() {
        int i2 = this.b;
        byte[][] statByte = {new byte[i2], new byte[i2], new byte[(this.c * i2)], new byte[i2]};
        statByte[0] = this.m;
        statByte[1] = this.l;
        statByte[2] = this.f;
        statByte[3] = this.e;
        return statByte;
    }

    public int[] d() {
        return new int[]{this.g, this.h, this.k, this.j};
    }

    public String toString() {
        String out = "";
        for (int i2 = 0; i2 < 4; i2++) {
            out = out + d()[i2] + " ";
        }
        String out2 = out + " " + this.b + " " + this.c + " " + this.i + " ";
        byte[][] temp = c();
        for (int i3 = 0; i3 < 4; i3++) {
            if (temp[i3] != null) {
                out2 = out2 + new String(Hex.b(temp[i3])) + " ";
            } else {
                out2 = out2 + "null ";
            }
        }
        return out2;
    }
}

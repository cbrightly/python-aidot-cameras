package org.spongycastle.pqc.crypto.gmss;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.encoders.Hex;

public class GMSSRootCalc {
    private int a;
    private int b;
    private Treehash[] c;
    private Vector[] d = new Vector[(this.g - 1)];
    private byte[] e = new byte[this.b];
    private byte[][] f;
    private int g;
    private Vector h;
    private Vector i;
    private Digest j;
    private GMSSDigestProvider k;
    private int[] l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;

    public GMSSRootCalc(int heightOfTree, int K, GMSSDigestProvider digestProvider) {
        this.a = heightOfTree;
        this.k = digestProvider;
        Digest digest = digestProvider.get();
        this.j = digest;
        int e2 = digest.e();
        this.b = e2;
        this.g = K;
        this.l = new int[heightOfTree];
        int[] iArr = new int[2];
        iArr[1] = e2;
        iArr[0] = heightOfTree;
        this.f = (byte[][]) Array.newInstance(byte.class, iArr);
        for (int i2 = 0; i2 < K - 1; i2++) {
            this.d[i2] = new Vector();
        }
    }

    public void h(Vector sharedStack) {
        int i2;
        this.c = new Treehash[(this.a - this.g)];
        int i3 = 0;
        while (true) {
            i2 = this.a;
            if (i3 >= i2 - this.g) {
                break;
            }
            this.c[i3] = new Treehash(sharedStack, i3, this.k.get());
            i3++;
        }
        this.l = new int[i2];
        int[] iArr = new int[2];
        iArr[1] = this.b;
        iArr[0] = i2;
        this.f = (byte[][]) Array.newInstance(byte.class, iArr);
        this.e = new byte[this.b];
        this.h = new Vector();
        this.i = new Vector();
        this.m = true;
        this.n = false;
        for (int i4 = 0; i4 < this.a; i4++) {
            this.l[i4] = -1;
        }
        this.d = new Vector[(this.g - 1)];
        for (int i5 = 0; i5 < this.g - 1; i5++) {
            this.d[i5] = new Vector();
        }
        this.o = 3;
        this.p = 0;
    }

    public void k(byte[] seed, byte[] leaf) {
        int i2 = this.p;
        if (i2 < this.a - this.g && this.o - 2 == this.l[0]) {
            i(seed, i2);
            this.p++;
            this.o *= 2;
        }
        j(leaf);
    }

    public void j(byte[] leaf) {
        if (this.n) {
            System.out.print("Too much updates for Tree!!");
        } else if (!this.m) {
            System.err.println("GMSSRootCalc not initialized!");
        } else {
            int[] iArr = this.l;
            iArr[0] = iArr[0] + 1;
            if (iArr[0] == 1) {
                System.arraycopy(leaf, 0, this.f[0], 0, this.b);
            } else if (iArr[0] == 3 && this.a > this.g) {
                this.c[0].i(leaf);
            }
            int[] iArr2 = this.l;
            if ((iArr2[0] - 3) % 2 == 0 && iArr2[0] >= 3 && this.a == this.g) {
                this.d[0].insertElementAt(leaf, 0);
            }
            if (this.l[0] == 0) {
                this.h.addElement(leaf);
                this.i.addElement(Integers.b(0));
                return;
            }
            int i2 = this.b;
            byte[] help = new byte[i2];
            byte[] toBeHashed = new byte[(i2 << 1)];
            System.arraycopy(leaf, 0, help, 0, i2);
            int helpHeight = 0;
            while (this.h.size() > 0 && helpHeight == ((Integer) this.i.lastElement()).intValue()) {
                System.arraycopy(this.h.lastElement(), 0, toBeHashed, 0, this.b);
                Vector vector = this.h;
                vector.removeElementAt(vector.size() - 1);
                Vector vector2 = this.i;
                vector2.removeElementAt(vector2.size() - 1);
                int i3 = this.b;
                System.arraycopy(help, 0, toBeHashed, i3, i3);
                this.j.update(toBeHashed, 0, toBeHashed.length);
                help = new byte[this.j.e()];
                this.j.c(help, 0);
                helpHeight++;
                if (helpHeight < this.a) {
                    int[] iArr3 = this.l;
                    iArr3[helpHeight] = iArr3[helpHeight] + 1;
                    if (iArr3[helpHeight] == 1) {
                        System.arraycopy(help, 0, this.f[helpHeight], 0, this.b);
                    }
                    if (helpHeight >= this.a - this.g) {
                        if (helpHeight == 0) {
                            System.out.println("M���P");
                        }
                        int[] iArr4 = this.l;
                        if ((iArr4[helpHeight] - 3) % 2 == 0 && iArr4[helpHeight] >= 3) {
                            this.d[helpHeight - (this.a - this.g)].insertElementAt(help, 0);
                        }
                    } else if (this.l[helpHeight] == 3) {
                        this.c[helpHeight].i(help);
                    }
                }
            }
            this.h.addElement(help);
            this.i.addElement(Integers.b(helpHeight));
            if (helpHeight == this.a) {
                this.n = true;
                this.m = false;
                this.e = (byte[]) this.h.lastElement();
            }
        }
    }

    public void i(byte[] seed, int index) {
        this.c[index].h(seed);
    }

    public boolean l() {
        return this.n;
    }

    public byte[][] a() {
        return GMSSUtils.c(this.f);
    }

    public Treehash[] g() {
        return GMSSUtils.b(this.c);
    }

    public Vector[] b() {
        return GMSSUtils.a(this.d);
    }

    public byte[] c() {
        return Arrays.h(this.e);
    }

    public Vector d() {
        Vector copy = new Vector();
        Enumeration en = this.h.elements();
        while (en.hasMoreElements()) {
            copy.addElement(en.nextElement());
        }
        return copy;
    }

    public byte[][] e() {
        int tailLength;
        Vector vector = this.h;
        if (vector == null) {
            tailLength = 0;
        } else {
            tailLength = vector.size();
        }
        int[] iArr = new int[2];
        iArr[1] = 64;
        iArr[0] = this.a + 1 + tailLength;
        byte[][] statByte = (byte[][]) Array.newInstance(byte.class, iArr);
        statByte[0] = this.e;
        for (int i2 = 0; i2 < this.a; i2++) {
            statByte[i2 + 1] = this.f[i2];
        }
        for (int i3 = 0; i3 < tailLength; i3++) {
            statByte[this.a + 1 + i3] = (byte[]) this.h.elementAt(i3);
        }
        return statByte;
    }

    public int[] f() {
        int tailLength;
        Vector vector = this.h;
        if (vector == null) {
            tailLength = 0;
        } else {
            tailLength = vector.size();
        }
        int i2 = this.a;
        int[] statInt = new int[(i2 + 8 + tailLength)];
        statInt[0] = i2;
        statInt[1] = this.b;
        statInt[2] = this.g;
        statInt[3] = this.o;
        statInt[4] = this.p;
        if (this.n) {
            statInt[5] = 1;
        } else {
            statInt[5] = 0;
        }
        if (this.m) {
            statInt[6] = 1;
        } else {
            statInt[6] = 0;
        }
        statInt[7] = tailLength;
        for (int i3 = 0; i3 < this.a; i3++) {
            statInt[i3 + 8] = this.l[i3];
        }
        for (int i4 = 0; i4 < tailLength; i4++) {
            statInt[this.a + 8 + i4] = ((Integer) this.i.elementAt(i4)).intValue();
        }
        return statInt;
    }

    public String toString() {
        int tailLength;
        String out = "";
        Vector vector = this.h;
        if (vector == null) {
            tailLength = 0;
        } else {
            tailLength = vector.size();
        }
        for (int i2 = 0; i2 < this.a + 8 + tailLength; i2++) {
            out = out + f()[i2] + " ";
        }
        for (int i3 = 0; i3 < this.a + 1 + tailLength; i3++) {
            out = out + new String(Hex.b(e()[i3])) + " ";
        }
        return out + JustifyTextView.TWO_CHINESE_BLANK + this.k.get().e();
    }
}

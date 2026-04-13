package org.spongycastle.pqc.crypto.gmss;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.util.Integers;
import org.spongycastle.util.encoders.Hex;

public class Treehash {
    private int a;
    private Vector b;
    private Vector c;
    private byte[] d = null;
    private byte[] e;
    private byte[] f;
    private int g;
    private int h;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private Digest l;

    public Treehash(Vector tailStack, int maxHeight, Digest digest) {
        this.b = tailStack;
        this.a = maxHeight;
        this.l = digest;
        this.f = new byte[digest.e()];
        this.e = new byte[this.l.e()];
    }

    public void h(byte[] seedIn) {
        System.arraycopy(seedIn, 0, this.f, 0, this.l.e());
        this.k = true;
    }

    public void g() {
        if (!this.k) {
            PrintStream printStream = System.err;
            printStream.println("Seed " + this.a + " not initialized");
            return;
        }
        this.c = new Vector();
        this.g = 0;
        this.d = null;
        this.h = -1;
        this.i = true;
        System.arraycopy(this.f, 0, this.e, 0, this.l.e());
    }

    public void j(GMSSRandom gmssRandom, byte[] leaf) {
        if (this.j) {
            System.err.println("No more update possible for treehash instance!");
        } else if (!this.i) {
            System.err.println("Treehash instance not initialized before update");
        } else {
            byte[] bArr = new byte[this.l.e()];
            gmssRandom.c(this.e);
            if (this.d == null) {
                this.d = leaf;
                this.h = 0;
            } else {
                byte[] help = leaf;
                int helpHeight = 0;
                while (this.g > 0 && helpHeight == ((Integer) this.c.lastElement()).intValue()) {
                    byte[] toBeHashed = new byte[(this.l.e() << 1)];
                    System.arraycopy(this.b.lastElement(), 0, toBeHashed, 0, this.l.e());
                    Vector vector = this.b;
                    vector.removeElementAt(vector.size() - 1);
                    Vector vector2 = this.c;
                    vector2.removeElementAt(vector2.size() - 1);
                    System.arraycopy(help, 0, toBeHashed, this.l.e(), this.l.e());
                    this.l.update(toBeHashed, 0, toBeHashed.length);
                    help = new byte[this.l.e()];
                    this.l.c(help, 0);
                    helpHeight++;
                    this.g--;
                }
                this.b.addElement(help);
                this.c.addElement(Integers.b(helpHeight));
                this.g++;
                if (((Integer) this.c.lastElement()).intValue() == this.h) {
                    byte[] toBeHashed2 = new byte[(this.l.e() << 1)];
                    System.arraycopy(this.d, 0, toBeHashed2, 0, this.l.e());
                    System.arraycopy(this.b.lastElement(), 0, toBeHashed2, this.l.e(), this.l.e());
                    Vector vector3 = this.b;
                    vector3.removeElementAt(vector3.size() - 1);
                    Vector vector4 = this.c;
                    vector4.removeElementAt(vector4.size() - 1);
                    this.l.update(toBeHashed2, 0, toBeHashed2.length);
                    byte[] bArr2 = new byte[this.l.e()];
                    this.d = bArr2;
                    this.l.c(bArr2, 0);
                    this.h++;
                    this.g = 0;
                }
            }
            if (this.h == this.a) {
                this.j = true;
            }
        }
    }

    public void a() {
        this.i = false;
        this.j = false;
        this.d = null;
        this.g = 0;
        this.h = -1;
    }

    public int c() {
        if (this.d == null) {
            return this.a;
        }
        if (this.g == 0) {
            return this.h;
        }
        return Math.min(this.h, ((Integer) this.c.lastElement()).intValue());
    }

    public boolean m() {
        return this.i;
    }

    public boolean l() {
        return this.j;
    }

    public byte[] b() {
        return this.d;
    }

    public byte[] d() {
        return this.e;
    }

    public void i(byte[] hash) {
        if (!this.i) {
            g();
        }
        this.d = hash;
        this.h = this.a;
        this.j = true;
    }

    public void k(GMSSRandom gmssRandom) {
        gmssRandom.c(this.f);
    }

    public byte[][] e() {
        int[] iArr = new int[2];
        iArr[1] = this.l.e();
        iArr[0] = this.g + 3;
        byte[][] statByte = (byte[][]) Array.newInstance(byte.class, iArr);
        statByte[0] = this.d;
        statByte[1] = this.e;
        statByte[2] = this.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            statByte[i2 + 3] = (byte[]) this.b.elementAt(i2);
        }
        return statByte;
    }

    public int[] f() {
        int i2 = this.g;
        int[] statInt = new int[(i2 + 6)];
        statInt[0] = this.a;
        statInt[1] = i2;
        statInt[2] = this.h;
        if (this.j) {
            statInt[3] = 1;
        } else {
            statInt[3] = 0;
        }
        if (this.i) {
            statInt[4] = 1;
        } else {
            statInt[4] = 0;
        }
        if (this.k) {
            statInt[5] = 1;
        } else {
            statInt[5] = 0;
        }
        for (int i3 = 0; i3 < this.g; i3++) {
            statInt[i3 + 6] = ((Integer) this.c.elementAt(i3)).intValue();
        }
        return statInt;
    }

    public String toString() {
        String out;
        String out2 = "Treehash    : ";
        for (int i2 = 0; i2 < this.g + 6; i2++) {
            out2 = out + f()[i2] + " ";
        }
        for (int i3 = 0; i3 < this.g + 3; i3++) {
            if (e()[i3] != null) {
                out = out + new String(Hex.b(e()[i3])) + " ";
            } else {
                out = out + "null ";
            }
        }
        return out + JustifyTextView.TWO_CHINESE_BLANK + this.l.e();
    }
}

package org.spongycastle.crypto.digests;

import com.alibaba.fastjson.asm.Opcodes;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.engines.ThreefishEngine;
import org.spongycastle.crypto.params.SkeinParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Memoable;

public class SkeinEngine implements Memoable {
    private static final Hashtable a = new Hashtable();
    final ThreefishEngine b;
    private final int c;
    long[] d;
    private long[] e;
    private byte[] f;
    private Parameter[] g;
    private Parameter[] h;
    private final UBI i;
    private final byte[] j;

    public static class Configuration {
        private byte[] a;

        public Configuration(long outputSizeBits) {
            byte[] bArr = new byte[32];
            this.a = bArr;
            bArr[0] = 83;
            bArr[1] = 72;
            bArr[2] = 65;
            bArr[3] = 51;
            bArr[4] = 1;
            bArr[5] = 0;
            ThreefishEngine.o(outputSizeBits, bArr, 8);
        }

        public byte[] a() {
            return this.a;
        }
    }

    public static class Parameter {
        private int a;
        private byte[] b;

        public Parameter(int type, byte[] value) {
            this.a = type;
            this.b = value;
        }

        public int a() {
            return this.a;
        }

        public byte[] b() {
            return this.b;
        }
    }

    static {
        j(256, 128, new long[]{-2228972824489528736L, -8629553674646093540L, 1155188648486244218L, -3677226592081559102L});
        j(256, Opcodes.IF_ICMPNE, new long[]{1450197650740764312L, 3081844928540042640L, -3136097061834271170L, 3301952811952417661L});
        j(256, 224, new long[]{-4176654842910610933L, -8688192972455077604L, -7364642305011795836L, 4056579644589979102L});
        j(256, 256, new long[]{-243853671043386295L, 3443677322885453875L, -5531612722399640561L, 7662005193972177513L});
        j(512, 128, new long[]{-6288014694233956526L, 2204638249859346602L, 3502419045458743507L, -4829063503441264548L, 983504137758028059L, 1880512238245786339L, -6715892782214108542L, 7602827311880509485L});
        j(512, Opcodes.IF_ICMPNE, new long[]{2934123928682216849L, -4399710721982728305L, 1684584802963255058L, 5744138295201861711L, 2444857010922934358L, -2807833639722848072L, -5121587834665610502L, 118355523173251694L});
        j(512, 224, new long[]{-3688341020067007964L, -3772225436291745297L, -8300862168937575580L, 4146387520469897396L, 1106145742801415120L, 7455425944880474941L, -7351063101234211863L, -7048981346965512457L});
        j(512, 384, new long[]{-6631894876634615969L, -5692838220127733084L, -7099962856338682626L, -2911352911530754598L, 2000907093792408677L, 9140007292425499655L, 6093301768906360022L, 2769176472213098488L});
        j(512, 512, new long[]{5261240102383538638L, 978932832955457283L, -8083517948103779378L, -7339365279355032399L, 6752626034097301424L, -1531723821829733388L, -7417126464950782685L, -5901786942805128141L});
    }

    private static void j(int blockSize, int outputSize, long[] state) {
        a.put(t(blockSize / 8, outputSize / 8), state);
    }

    private static Integer t(int blockSizeBytes, int outputSizeBytes) {
        return new Integer((outputSizeBytes << 16) | blockSizeBytes);
    }

    public static class UbiTweak {
        private long[] a = new long[2];
        private boolean b;

        public UbiTweak() {
            f();
        }

        public void g(UbiTweak tweak) {
            this.a = Arrays.m(tweak.a, this.a);
            this.b = tweak.b;
        }

        public void f() {
            long[] jArr = this.a;
            jArr[0] = 0;
            jArr[1] = 0;
            this.b = false;
            i(true);
        }

        public void j(int type) {
            long[] jArr = this.a;
            jArr[1] = (jArr[1] & -274877906944L) | ((((long) type) & 63) << 56);
        }

        public int b() {
            return (int) ((this.a[1] >>> 56) & 63);
        }

        public void i(boolean first) {
            if (first) {
                long[] jArr = this.a;
                jArr[1] = jArr[1] | 4611686018427387904L;
                return;
            }
            long[] jArr2 = this.a;
            jArr2[1] = jArr2[1] & -4611686018427387905L;
        }

        public boolean e() {
            return (this.a[1] & 4611686018427387904L) != 0;
        }

        public void h(boolean last) {
            if (last) {
                long[] jArr = this.a;
                jArr[1] = jArr[1] | Long.MIN_VALUE;
                return;
            }
            long[] jArr2 = this.a;
            jArr2[1] = jArr2[1] & DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }

        public boolean d() {
            return (this.a[1] & Long.MIN_VALUE) != 0;
        }

        public void a(int advance) {
            if (this.b) {
                long[] jArr = this.a;
                long[] parts = {jArr[0] & 4294967295L, (jArr[0] >>> 32) & 4294967295L, jArr[1] & 4294967295L};
                long carry = (long) advance;
                for (int i = 0; i < parts.length; i++) {
                    long carry2 = carry + parts[i];
                    parts[i] = carry2;
                    carry = carry2 >>> 32;
                }
                long[] jArr2 = this.a;
                jArr2[0] = ((parts[1] & 4294967295L) << 32) | (parts[0] & 4294967295L);
                jArr2[1] = (parts[2] & 4294967295L) | (jArr2[1] & -4294967296L);
                return;
            }
            long[] jArr3 = this.a;
            long position = jArr3[0] + ((long) advance);
            jArr3[0] = position;
            if (position > 9223372034707292160L) {
                this.b = true;
            }
        }

        public long[] c() {
            return this.a;
        }

        public String toString() {
            return b() + " first: " + e() + ", final: " + d();
        }
    }

    public class UBI {
        private final UbiTweak a = new UbiTweak();
        private byte[] b;
        private int c;
        private long[] d;

        public UBI(int blockSize) {
            byte[] bArr = new byte[blockSize];
            this.b = bArr;
            this.d = new long[(bArr.length / 8)];
        }

        public void d(UBI ubi) {
            this.b = Arrays.i(ubi.b, this.b);
            this.c = ubi.c;
            this.d = Arrays.m(ubi.d, this.d);
            this.a.g(ubi.a);
        }

        public void c(int type) {
            this.a.f();
            this.a.j(type);
            this.c = 0;
        }

        public void e(byte[] value, int offset, int len, long[] output) {
            int copied = 0;
            while (len > copied) {
                if (this.c == this.b.length) {
                    b(output);
                    this.a.i(false);
                    this.c = 0;
                }
                int toCopy = Math.min(len - copied, this.b.length - this.c);
                System.arraycopy(value, offset + copied, this.b, this.c, toCopy);
                copied += toCopy;
                this.c += toCopy;
                this.a.a(toCopy);
            }
        }

        private void b(long[] output) {
            long[] jArr;
            SkeinEngine skeinEngine = SkeinEngine.this;
            skeinEngine.b.j(true, skeinEngine.d, this.a.c());
            int i = 0;
            while (true) {
                jArr = this.d;
                if (i >= jArr.length) {
                    break;
                }
                jArr[i] = ThreefishEngine.i(this.b, i * 8);
                i++;
            }
            SkeinEngine.this.b.k(jArr, output);
            for (int i2 = 0; i2 < output.length; i2++) {
                output[i2] = output[i2] ^ this.d[i2];
            }
        }

        public void a(long[] output) {
            int i = this.c;
            while (true) {
                byte[] bArr = this.b;
                if (i < bArr.length) {
                    bArr[i] = 0;
                    i++;
                } else {
                    this.a.h(true);
                    b(output);
                    return;
                }
            }
        }
    }

    public SkeinEngine(int blockSizeBits, int outputSizeBits) {
        this.j = new byte[1];
        if (outputSizeBits % 8 == 0) {
            this.c = outputSizeBits / 8;
            ThreefishEngine threefishEngine = new ThreefishEngine(blockSizeBits);
            this.b = threefishEngine;
            this.i = new UBI(threefishEngine.c());
            return;
        }
        throw new IllegalArgumentException("Output size must be a multiple of 8 bits. :" + outputSizeBits);
    }

    public SkeinEngine(SkeinEngine engine) {
        this(engine.f() * 8, engine.g() * 8);
        c(engine);
    }

    private void c(SkeinEngine engine) {
        this.i.d(engine.i);
        this.d = Arrays.m(engine.d, this.d);
        this.e = Arrays.m(engine.e, this.e);
        this.f = Arrays.i(engine.f, this.f);
        this.g = b(engine.g, this.g);
        this.h = b(engine.h, this.h);
    }

    private static Parameter[] b(Parameter[] data, Parameter[] existing) {
        if (data == null) {
            return null;
        }
        if (existing == null || existing.length != data.length) {
            existing = new Parameter[data.length];
        }
        System.arraycopy(data, 0, existing, 0, existing.length);
        return existing;
    }

    public Memoable copy() {
        return new SkeinEngine(this);
    }

    public void m(Memoable other) {
        SkeinEngine s = (SkeinEngine) other;
        if (f() == s.f() && this.c == s.c) {
            c(s);
            return;
        }
        throw new IllegalArgumentException("Incompatible parameters in provided SkeinEngine.");
    }

    public int g() {
        return this.c;
    }

    public int f() {
        return this.b.c();
    }

    public void h(SkeinParameters params) {
        this.d = null;
        this.f = null;
        this.g = null;
        this.h = null;
        if (params != null) {
            if (params.a().length >= 16) {
                i(params.b());
            } else {
                throw new IllegalArgumentException("Skein key must be at least 128 bits.");
            }
        }
        d();
        q(48);
    }

    private void i(Hashtable parameters) {
        Enumeration keys = parameters.keys();
        Vector pre = new Vector();
        Vector post = new Vector();
        while (keys.hasMoreElements()) {
            Integer type = (Integer) keys.nextElement();
            byte[] value = (byte[]) parameters.get(type);
            if (type.intValue() == 0) {
                this.f = value;
            } else if (type.intValue() < 48) {
                pre.addElement(new Parameter(type.intValue(), value));
            } else {
                post.addElement(new Parameter(type.intValue(), value));
            }
        }
        Parameter[] parameterArr = new Parameter[pre.size()];
        this.g = parameterArr;
        pre.copyInto(parameterArr);
        n(this.g);
        Parameter[] parameterArr2 = new Parameter[post.size()];
        this.h = parameterArr2;
        post.copyInto(parameterArr2);
        n(this.h);
    }

    private static void n(Parameter[] params) {
        if (params != null) {
            for (int i2 = 1; i2 < params.length; i2++) {
                Parameter param = params[i2];
                int hole = i2;
                while (hole > 0 && param.a() < params[hole - 1].a()) {
                    params[hole] = params[hole - 1];
                    hole--;
                }
                params[hole] = param;
            }
        }
    }

    private void d() {
        long[] precalc = (long[]) a.get(t(f(), g()));
        if (this.f != null || precalc == null) {
            this.d = new long[(f() / 8)];
            byte[] bArr = this.f;
            if (bArr != null) {
                o(0, bArr);
            }
            o(4, new Configuration((long) (this.c * 8)).a());
        } else {
            this.d = Arrays.l(precalc);
        }
        if (this.g != null) {
            int i2 = 0;
            while (true) {
                Parameter[] parameterArr = this.g;
                if (i2 >= parameterArr.length) {
                    break;
                }
                Parameter param = parameterArr[i2];
                o(param.a(), param.b());
                i2++;
            }
        }
        this.e = Arrays.l(this.d);
    }

    public void l() {
        long[] jArr = this.e;
        long[] jArr2 = this.d;
        System.arraycopy(jArr, 0, jArr2, 0, jArr2.length);
        q(48);
    }

    private void o(int type, byte[] value) {
        q(type);
        this.i.e(value, 0, value.length, this.d);
        p();
    }

    private void q(int type) {
        this.i.c(type);
    }

    private void p() {
        this.i.a(this.d);
    }

    private void a() {
        if (this.i == null) {
            throw new IllegalArgumentException("Skein engine is not initialised.");
        }
    }

    public void r(byte in) {
        byte[] bArr = this.j;
        bArr[0] = in;
        s(bArr, 0, 1);
    }

    public void s(byte[] in, int inOff, int len) {
        a();
        this.i.e(in, inOff, len, this.d);
    }

    public int e(byte[] out, int outOff) {
        a();
        if (out.length >= this.c + outOff) {
            p();
            if (this.h != null) {
                int i2 = 0;
                while (true) {
                    Parameter[] parameterArr = this.h;
                    if (i2 >= parameterArr.length) {
                        break;
                    }
                    Parameter param = parameterArr[i2];
                    o(param.a(), param.b());
                    i2++;
                }
            }
            int blockSize = f();
            int blocksRequired = ((this.c + blockSize) - 1) / blockSize;
            for (int i3 = 0; i3 < blocksRequired; i3++) {
                k((long) i3, out, outOff + (i3 * blockSize), Math.min(blockSize, this.c - (i3 * blockSize)));
            }
            l();
            return this.c;
        }
        throw new OutputLengthException("Output buffer is too short to hold output");
    }

    private void k(long outputSequence, byte[] out, int outOff, int outputBytes) {
        byte[] currentBytes = new byte[8];
        ThreefishEngine.o(outputSequence, currentBytes, 0);
        long[] outputWords = new long[this.d.length];
        q(63);
        this.i.e(currentBytes, 0, currentBytes.length, outputWords);
        this.i.a(outputWords);
        int wordsRequired = ((outputBytes + 8) - 1) / 8;
        for (int i2 = 0; i2 < wordsRequired; i2++) {
            int toWrite = Math.min(8, outputBytes - (i2 * 8));
            if (toWrite == 8) {
                ThreefishEngine.o(outputWords[i2], out, (i2 * 8) + outOff);
            } else {
                ThreefishEngine.o(outputWords[i2], currentBytes, 0);
                System.arraycopy(currentBytes, 0, out, (i2 * 8) + outOff, toWrite);
            }
        }
    }
}

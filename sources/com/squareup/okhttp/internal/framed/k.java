package com.squareup.okhttp.internal.framed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
import okio.c;
import okio.e;
import okio.e0;
import okio.f;
import okio.n;
import okio.p;

/* compiled from: NameValueBlockReader */
public class k {
    private final n a;
    /* access modifiers changed from: private */
    public int b;
    private final e c;

    public k(e source) {
        n nVar = new n(new a(source), new b());
        this.a = nVar;
        this.c = p.d(nVar);
    }

    /* compiled from: NameValueBlockReader */
    public class a extends okio.k {
        a(e0 x0) {
            super(x0);
        }

        public long read(c sink, long byteCount) {
            if (k.this.b == 0) {
                return -1;
            }
            long read = super.read(sink, Math.min(byteCount, (long) k.this.b));
            if (read == -1) {
                return -1;
            }
            k kVar = k.this;
            int unused = kVar.b = (int) (((long) kVar.b) - read);
            return read;
        }
    }

    /* compiled from: NameValueBlockReader */
    public class b extends Inflater {
        b() {
        }

        public int inflate(byte[] buffer, int offset, int count) {
            int result = super.inflate(buffer, offset, count);
            if (result != 0 || !needsDictionary()) {
                return result;
            }
            setDictionary(o.a);
            return super.inflate(buffer, offset, count);
        }
    }

    public List<f> f(int length) {
        this.b += length;
        int numberOfPairs = this.c.readInt();
        if (numberOfPairs < 0) {
            throw new IOException("numberOfPairs < 0: " + numberOfPairs);
        } else if (numberOfPairs <= 1024) {
            List<Header> entries = new ArrayList<>(numberOfPairs);
            int i = 0;
            while (i < numberOfPairs) {
                f name = e().toAsciiLowercase();
                f values = e();
                if (name.size() != 0) {
                    entries.add(new f(name, values));
                    i++;
                } else {
                    throw new IOException("name.size == 0");
                }
            }
            d();
            return entries;
        } else {
            throw new IOException("numberOfPairs > 1024: " + numberOfPairs);
        }
    }

    private f e() {
        return this.c.m0((long) this.c.readInt());
    }

    private void d() {
        if (this.b > 0) {
            this.a.c();
            if (this.b != 0) {
                throw new IOException("compressedLimit > 0: " + this.b);
            }
        }
    }

    public void c() {
        this.c.close();
    }
}

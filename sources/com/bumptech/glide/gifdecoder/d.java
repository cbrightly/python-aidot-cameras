package com.bumptech.glide.gifdecoder;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.druk.dnssd.NSType;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* compiled from: GifHeaderParser */
public class d {
    private final byte[] a = new byte[256];
    private ByteBuffer b;
    private c c;
    private int d = 0;

    public d p(@NonNull ByteBuffer data) {
        o();
        ByteBuffer asReadOnlyBuffer = data.asReadOnlyBuffer();
        this.b = asReadOnlyBuffer;
        asReadOnlyBuffer.position(0);
        this.b.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public void a() {
        this.b = null;
        this.c = null;
    }

    private void o() {
        this.b = null;
        Arrays.fill(this.a, (byte) 0);
        this.c = new c();
        this.d = 0;
    }

    @NonNull
    public c c() {
        if (this.b == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        } else if (b()) {
            return this.c;
        } else {
            k();
            if (!b()) {
                h();
                c cVar = this.c;
                if (cVar.c < 0) {
                    cVar.b = 1;
                }
            }
            return this.c;
        }
    }

    private void h() {
        i(Integer.MAX_VALUE);
    }

    private void i(int maxFrames) {
        boolean done = false;
        while (!done && !b() && this.c.c <= maxFrames) {
            switch (d()) {
                case 33:
                    switch (d()) {
                        case 1:
                            q();
                            break;
                        case NSType.TKEY /*249*/:
                            this.c.d = new b();
                            j();
                            break;
                        case 254:
                            q();
                            break;
                        case 255:
                            f();
                            StringBuilder app = new StringBuilder();
                            for (int i = 0; i < 11; i++) {
                                app.append((char) this.a[i]);
                            }
                            if (!app.toString().equals("NETSCAPE2.0")) {
                                q();
                                break;
                            } else {
                                m();
                                break;
                            }
                        default:
                            q();
                            break;
                    }
                case 44:
                    c cVar = this.c;
                    if (cVar.d == null) {
                        cVar.d = new b();
                    }
                    e();
                    break;
                case 59:
                    done = true;
                    break;
                default:
                    this.c.b = 1;
                    break;
            }
        }
    }

    private void j() {
        d();
        int packed = d();
        b bVar = this.c.d;
        int i = (packed & 28) >> 2;
        bVar.g = i;
        boolean z = true;
        if (i == 0) {
            bVar.g = 1;
        }
        if ((packed & 1) == 0) {
            z = false;
        }
        bVar.f = z;
        int delayInHundredthsOfASecond = n();
        if (delayInHundredthsOfASecond < 2) {
            delayInHundredthsOfASecond = 10;
        }
        b bVar2 = this.c.d;
        bVar2.i = delayInHundredthsOfASecond * 10;
        bVar2.h = d();
        d();
    }

    private void e() {
        this.c.d.a = n();
        this.c.d.b = n();
        this.c.d.c = n();
        this.c.d.d = n();
        int packed = d();
        boolean z = false;
        boolean lctFlag = (packed & 128) != 0;
        int lctSize = (int) Math.pow(2.0d, (double) ((packed & 7) + 1));
        b bVar = this.c.d;
        if ((packed & 64) != 0) {
            z = true;
        }
        bVar.e = z;
        if (lctFlag) {
            bVar.k = g(lctSize);
        } else {
            bVar.k = null;
        }
        this.c.d.j = this.b.position();
        r();
        if (!b()) {
            c cVar = this.c;
            cVar.c++;
            cVar.e.add(cVar.d);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m() {
        /*
            r4 = this;
        L_0x0000:
            r4.f()
            byte[] r0 = r4.a
            r1 = 0
            byte r1 = r0[r1]
            r2 = 1
            if (r1 != r2) goto L_0x001b
            byte r1 = r0[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 2
            byte r0 = r0[r2]
            r0 = r0 & 255(0xff, float:3.57E-43)
            com.bumptech.glide.gifdecoder.c r2 = r4.c
            int r3 = r0 << 8
            r3 = r3 | r1
            r2.m = r3
        L_0x001b:
            int r0 = r4.d
            if (r0 <= 0) goto L_0x0025
            boolean r0 = r4.b()
            if (r0 == 0) goto L_0x0000
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.d.m():void");
    }

    private void k() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            id.append((char) d());
        }
        if (!id.toString().startsWith("GIF")) {
            this.c.b = 1;
            return;
        }
        l();
        if (this.c.h && !b()) {
            c cVar = this.c;
            cVar.a = g(cVar.i);
            c cVar2 = this.c;
            cVar2.l = cVar2.a[cVar2.j];
        }
    }

    private void l() {
        this.c.f = n();
        this.c.g = n();
        int packed = d();
        c cVar = this.c;
        cVar.h = (packed & 128) != 0;
        cVar.i = (int) Math.pow(2.0d, (double) ((packed & 7) + 1));
        this.c.j = d();
        this.c.k = d();
    }

    @Nullable
    private int[] g(int nColors) {
        int[] tab = null;
        byte[] c2 = new byte[(nColors * 3)];
        try {
            this.b.get(c2);
            tab = new int[256];
            int i = 0;
            int r = 0;
            while (i < nColors) {
                int j = r + 1;
                int j2 = j + 1;
                int j3 = j2 + 1;
                int i2 = i + 1;
                tab[i] = -16777216 | ((c2[r] & 255) << 16) | ((c2[j] & 255) << 8) | (c2[j2] & 255);
                r = j3;
                i = i2;
            }
        } catch (BufferUnderflowException e) {
            if (Log.isLoggable("GifHeaderParser", 3)) {
                Log.d("GifHeaderParser", "Format Error Reading Color Table", e);
            }
            this.c.b = 1;
        }
        return tab;
    }

    private void r() {
        d();
        q();
    }

    private void q() {
        int blockSize;
        do {
            blockSize = d();
            this.b.position(Math.min(this.b.position() + blockSize, this.b.limit()));
        } while (blockSize > 0);
    }

    private void f() {
        int d2 = d();
        this.d = d2;
        int n = 0;
        if (d2 > 0) {
            int count = 0;
            while (true) {
                try {
                    int i = this.d;
                    if (n < i) {
                        count = i - n;
                        this.b.get(this.a, n, count);
                        n += count;
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    if (Log.isLoggable("GifHeaderParser", 3)) {
                        Log.d("GifHeaderParser", "Error Reading Block n: " + n + " count: " + count + " blockSize: " + this.d, e);
                    }
                    this.c.b = 1;
                    return;
                }
            }
        }
    }

    private int d() {
        try {
            return this.b.get() & 255;
        } catch (Exception e) {
            this.c.b = 1;
            return 0;
        }
    }

    private int n() {
        return this.b.getShort();
    }

    private boolean b() {
        return this.c.b != 0;
    }
}

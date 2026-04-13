package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.h;
import com.bumptech.glide.i;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.m;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GifFrameLoader */
public class f {
    private final com.bumptech.glide.gifdecoder.a a;
    private final Handler b;
    private final List<b> c;
    final i d;
    private final e e;
    private boolean f;
    private boolean g;
    private boolean h;
    private h<Bitmap> i;
    private a j;
    private boolean k;
    private a l;
    private Bitmap m;
    private m<Bitmap> n;
    private a o;
    @Nullable
    private d p;
    private int q;
    private int r;
    private int s;

    /* compiled from: GifFrameLoader */
    public interface b {
        void a();
    }

    @VisibleForTesting
    /* compiled from: GifFrameLoader */
    public interface d {
        void a();
    }

    f(com.bumptech.glide.b glide, com.bumptech.glide.gifdecoder.a gifDecoder, int width, int height, m<Bitmap> transformation, Bitmap firstFrame) {
        this(glide.f(), com.bumptech.glide.b.t(glide.h()), gifDecoder, (Handler) null, i(com.bumptech.glide.b.t(glide.h()), width, height), transformation, firstFrame);
    }

    f(e bitmapPool, i requestManager, com.bumptech.glide.gifdecoder.a gifDecoder, Handler handler, h<Bitmap> requestBuilder, m<Bitmap> transformation, Bitmap firstFrame) {
        this.c = new ArrayList();
        this.d = requestManager;
        handler = handler == null ? new Handler(Looper.getMainLooper(), new c()) : handler;
        this.e = bitmapPool;
        this.b = handler;
        this.i = requestBuilder;
        this.a = gifDecoder;
        o(transformation, firstFrame);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.bumptech.glide.load.m, com.bumptech.glide.load.m<android.graphics.Bitmap>, java.lang.Object] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void o(com.bumptech.glide.load.m<android.graphics.Bitmap> r3, android.graphics.Bitmap r4) {
        /*
            r2 = this;
            java.lang.Object r0 = com.bumptech.glide.util.i.d(r3)
            com.bumptech.glide.load.m r0 = (com.bumptech.glide.load.m) r0
            r2.n = r0
            java.lang.Object r0 = com.bumptech.glide.util.i.d(r4)
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            r2.m = r0
            com.bumptech.glide.h<android.graphics.Bitmap> r0 = r2.i
            com.bumptech.glide.request.f r1 = new com.bumptech.glide.request.f
            r1.<init>()
            com.bumptech.glide.request.a r1 = r1.p0(r3)
            com.bumptech.glide.h r0 = r0.a(r1)
            r2.i = r0
            int r0 = com.bumptech.glide.util.j.g(r4)
            r2.q = r0
            int r0 = r4.getWidth()
            r2.r = r0
            int r0 = r4.getHeight()
            r2.s = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.gif.f.o(com.bumptech.glide.load.m, android.graphics.Bitmap):void");
    }

    /* access modifiers changed from: package-private */
    public Bitmap e() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public void r(b frameCallback) {
        if (this.k) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        } else if (!this.c.contains(frameCallback)) {
            boolean start = this.c.isEmpty();
            this.c.add(frameCallback);
            if (start) {
                p();
            }
        } else {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
    }

    /* access modifiers changed from: package-private */
    public void s(b frameCallback) {
        this.c.remove(frameCallback);
        if (this.c.isEmpty()) {
            q();
        }
    }

    /* access modifiers changed from: package-private */
    public int k() {
        return this.r;
    }

    /* access modifiers changed from: package-private */
    public int h() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    public int j() {
        return this.a.g() + this.q;
    }

    /* access modifiers changed from: package-private */
    public int d() {
        a aVar = this.j;
        if (aVar != null) {
            return aVar.x;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer b() {
        return this.a.getData().asReadOnlyBuffer();
    }

    /* access modifiers changed from: package-private */
    public int f() {
        return this.a.b();
    }

    private void p() {
        if (!this.f) {
            this.f = true;
            this.k = false;
            l();
        }
    }

    private void q() {
        this.f = false;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.c.clear();
        n();
        q();
        a aVar = this.j;
        if (aVar != null) {
            this.d.l(aVar);
            this.j = null;
        }
        a aVar2 = this.l;
        if (aVar2 != null) {
            this.d.l(aVar2);
            this.l = null;
        }
        a aVar3 = this.o;
        if (aVar3 != null) {
            this.d.l(aVar3);
            this.o = null;
        }
        this.a.clear();
        this.k = true;
    }

    /* access modifiers changed from: package-private */
    public Bitmap c() {
        a aVar = this.j;
        return aVar != null ? aVar.h() : this.m;
    }

    private void l() {
        if (this.f && !this.g) {
            if (this.h) {
                com.bumptech.glide.util.i.a(this.o == null, "Pending target must be null when starting from the first frame");
                this.a.e();
                this.h = false;
            }
            if (this.o != null) {
                a temp = this.o;
                this.o = null;
                m(temp);
                return;
            }
            this.g = true;
            long targetTime = SystemClock.uptimeMillis() + ((long) this.a.d());
            this.a.a();
            this.l = new a(this.b, this.a.f(), targetTime);
            this.i.a(com.bumptech.glide.request.f.y0(g())).L0(this.a).D0(this.l);
        }
    }

    private void n() {
        Bitmap bitmap = this.m;
        if (bitmap != null) {
            this.e.b(bitmap);
            this.m = null;
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setOnEveryFrameReadyListener(@Nullable d onEveryFrameListener) {
        this.p = onEveryFrameListener;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void m(a delayTarget) {
        d dVar = this.p;
        if (dVar != null) {
            dVar.a();
        }
        this.g = false;
        if (this.k) {
            this.b.obtainMessage(2, delayTarget).sendToTarget();
        } else if (this.f) {
            if (delayTarget.h() != null) {
                n();
                a previous = this.j;
                this.j = delayTarget;
                for (int i2 = this.c.size() - 1; i2 >= 0; i2--) {
                    this.c.get(i2).a();
                }
                if (previous != null) {
                    this.b.obtainMessage(2, previous).sendToTarget();
                }
            }
            l();
        } else if (this.h) {
            this.b.obtainMessage(2, delayTarget).sendToTarget();
        } else {
            this.o = delayTarget;
        }
    }

    /* compiled from: GifFrameLoader */
    public class c implements Handler.Callback {
        c() {
        }

        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                f.this.m((a) msg.obj);
                return true;
            } else if (i != 2) {
                return false;
            } else {
                f.this.d.l((a) msg.obj);
                return false;
            }
        }
    }

    @VisibleForTesting
    /* compiled from: GifFrameLoader */
    public static class a extends com.bumptech.glide.request.target.c<Bitmap> {
        private final Handler q;
        final int x;
        private final long y;
        private Bitmap z;

        a(Handler handler, int index, long targetTime) {
            this.q = handler;
            this.x = index;
            this.y = targetTime;
        }

        /* access modifiers changed from: package-private */
        public Bitmap h() {
            return this.z;
        }

        /* renamed from: i */
        public void d(@NonNull Bitmap resource, @Nullable com.bumptech.glide.request.transition.b<? super Bitmap> bVar) {
            this.z = resource;
            this.q.sendMessageAtTime(this.q.obtainMessage(1, this), this.y);
        }

        public void c(@Nullable Drawable placeholder) {
            this.z = null;
        }
    }

    private static h<Bitmap> i(i requestManager, int width, int height) {
        return requestManager.i().a(((com.bumptech.glide.request.f) ((com.bumptech.glide.request.f) com.bumptech.glide.request.f.x0(com.bumptech.glide.load.engine.i.b).u0(true)).m0(true)).c0(width, height));
    }

    private static com.bumptech.glide.load.f g() {
        return new com.bumptech.glide.signature.d(Double.valueOf(Math.random()));
    }
}

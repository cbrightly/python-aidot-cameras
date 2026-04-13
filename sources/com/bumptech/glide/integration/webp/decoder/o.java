package com.bumptech.glide.integration.webp.decoder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.i;
import com.bumptech.glide.load.h;
import com.bumptech.glide.load.m;
import com.bumptech.glide.request.f;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/* compiled from: WebpFrameLoader */
public class o {
    public static final h<n> a = h.f("com.bumptech.glide.integration.webp.decoder.WebpFrameLoader.CacheStrategy", n.b);
    private final i b;
    private final Handler c;
    private final List<b> d;
    final i e;
    private final com.bumptech.glide.load.engine.bitmap_recycle.e f;
    private boolean g;
    private boolean h;
    private boolean i;
    private com.bumptech.glide.h<Bitmap> j;
    private a k;
    private boolean l;
    private a m;
    private Bitmap n;
    private m<Bitmap> o;
    private a p;
    @Nullable
    private d q;
    private int r;
    private int s;
    private int t;

    /* compiled from: WebpFrameLoader */
    public interface b {
        void a();
    }

    /* compiled from: WebpFrameLoader */
    public interface d {
        void a();
    }

    public o(com.bumptech.glide.b glide, i webpDecoder, int width, int height, m<Bitmap> transformation, Bitmap firstFrame) {
        this(glide.f(), com.bumptech.glide.b.t(glide.h()), webpDecoder, (Handler) null, i(com.bumptech.glide.b.t(glide.h()), width, height), transformation, firstFrame);
    }

    o(com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool, i requestManager, i webpDecoder, Handler handler, com.bumptech.glide.h<Bitmap> requestBuilder, m<Bitmap> transformation, Bitmap firstFrame) {
        this.d = new ArrayList();
        this.g = false;
        this.h = false;
        this.i = false;
        this.e = requestManager;
        handler = handler == null ? new Handler(Looper.getMainLooper(), new c()) : handler;
        this.f = bitmapPool;
        this.c = handler;
        this.j = requestBuilder;
        this.b = webpDecoder;
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
            r2.o = r0
            java.lang.Object r0 = com.bumptech.glide.util.i.d(r4)
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            r2.n = r0
            com.bumptech.glide.h<android.graphics.Bitmap> r0 = r2.j
            com.bumptech.glide.request.f r1 = new com.bumptech.glide.request.f
            r1.<init>()
            com.bumptech.glide.request.a r1 = r1.p0(r3)
            com.bumptech.glide.h r0 = r0.a(r1)
            r2.j = r0
            int r0 = com.bumptech.glide.util.j.g(r4)
            r2.r = r0
            int r0 = r4.getWidth()
            r2.s = r0
            int r0 = r4.getHeight()
            r2.t = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.integration.webp.decoder.o.o(com.bumptech.glide.load.m, android.graphics.Bitmap):void");
    }

    /* access modifiers changed from: package-private */
    public Bitmap e() {
        return this.n;
    }

    /* access modifiers changed from: package-private */
    public void r(b frameCallback) {
        if (this.l) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        } else if (!this.d.contains(frameCallback)) {
            boolean start = this.d.isEmpty();
            this.d.add(frameCallback);
            if (start) {
                p();
            }
        } else {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
    }

    /* access modifiers changed from: package-private */
    public void s(b frameCallback) {
        this.d.remove(frameCallback);
        if (this.d.isEmpty()) {
            q();
        }
    }

    /* access modifiers changed from: package-private */
    public int k() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    public int h() {
        return this.t;
    }

    /* access modifiers changed from: package-private */
    public int j() {
        return this.b.g() + this.r;
    }

    /* access modifiers changed from: package-private */
    public int d() {
        a aVar = this.k;
        if (aVar != null) {
            return aVar.x;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer b() {
        return this.b.getData().asReadOnlyBuffer();
    }

    /* access modifiers changed from: package-private */
    public int f() {
        return this.b.b();
    }

    private void p() {
        if (!this.g) {
            this.g = true;
            this.l = false;
            l();
        }
    }

    private void q() {
        this.g = false;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.d.clear();
        n();
        q();
        a aVar = this.k;
        if (aVar != null) {
            this.e.l(aVar);
            this.k = null;
        }
        a aVar2 = this.m;
        if (aVar2 != null) {
            this.e.l(aVar2);
            this.m = null;
        }
        a aVar3 = this.p;
        if (aVar3 != null) {
            this.e.l(aVar3);
            this.p = null;
        }
        this.b.clear();
        this.l = true;
    }

    /* access modifiers changed from: package-private */
    public Bitmap c() {
        a aVar = this.k;
        return aVar != null ? aVar.h() : this.n;
    }

    private void l() {
        if (this.g && !this.h) {
            if (this.i) {
                com.bumptech.glide.util.i.a(this.p == null, "Pending target must be null when starting from the first frame");
                this.b.e();
                this.i = false;
            }
            if (this.p != null) {
                a temp = this.p;
                this.p = null;
                m(temp);
                return;
            }
            this.h = true;
            long targetTime = SystemClock.uptimeMillis() + ((long) this.b.d());
            this.b.a();
            int frameIndex = this.b.f();
            this.m = new a(this.c, frameIndex, targetTime);
            this.j.a((f) f.y0(g(frameIndex)).m0(this.b.k().c())).L0(this.b).D0(this.m);
        }
    }

    private void n() {
        Bitmap bitmap = this.n;
        if (bitmap != null) {
            this.f.b(bitmap);
            this.n = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnEveryFrameReadyListener(@Nullable d onEveryFrameListener) {
        this.q = onEveryFrameListener;
    }

    /* access modifiers changed from: package-private */
    public void m(a delayTarget) {
        d dVar = this.q;
        if (dVar != null) {
            dVar.a();
        }
        this.h = false;
        if (this.l) {
            this.c.obtainMessage(2, delayTarget).sendToTarget();
        } else if (this.g) {
            if (delayTarget.h() != null) {
                n();
                a previous = this.k;
                this.k = delayTarget;
                for (int i2 = this.d.size() - 1; i2 >= 0; i2--) {
                    this.d.get(i2).a();
                }
                if (previous != null) {
                    this.c.obtainMessage(2, previous).sendToTarget();
                }
            }
            l();
        } else if (this.i) {
            this.c.obtainMessage(2, delayTarget).sendToTarget();
        } else {
            this.p = delayTarget;
        }
    }

    /* compiled from: WebpFrameLoader */
    public class c implements Handler.Callback {
        c() {
        }

        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                o.this.m((a) msg.obj);
                return true;
            } else if (i != 2) {
                return false;
            } else {
                o.this.e.l((a) msg.obj);
                return false;
            }
        }
    }

    /* compiled from: WebpFrameLoader */
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
        public void d(Bitmap resource, com.bumptech.glide.request.transition.b<? super Bitmap> bVar) {
            this.z = resource;
            this.q.sendMessageAtTime(this.q.obtainMessage(1, this), this.y);
        }

        public void c(@Nullable Drawable placeholder) {
            this.z = null;
        }
    }

    private static com.bumptech.glide.h<Bitmap> i(i requestManager, int width, int height) {
        return requestManager.i().a(((f) ((f) f.x0(com.bumptech.glide.load.engine.i.b).u0(true)).m0(true)).c0(width, height));
    }

    private com.bumptech.glide.load.f g(int frameIndex) {
        return new e(new com.bumptech.glide.signature.d(this.b), frameIndex);
    }

    /* compiled from: WebpFrameLoader */
    public static class e implements com.bumptech.glide.load.f {
        private final com.bumptech.glide.load.f b;
        private final int c;

        e(com.bumptech.glide.load.f sourceKey, int frameIndex) {
            this.b = sourceKey;
            this.c = frameIndex;
        }

        public boolean equals(Object o) {
            if (!(o instanceof e)) {
                return false;
            }
            e other = (e) o;
            if (!this.b.equals(other.b) || this.c != other.c) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.b.hashCode() * 31) + this.c;
        }

        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ByteBuffer.allocate(12).putInt(this.c).array());
            this.b.updateDiskCacheKey(messageDigest);
        }
    }
}

package coil.fetch;

import android.webkit.MimeTypeMap;
import androidx.annotation.VisibleForTesting;
import coil.bitmap.c;
import coil.decode.m;
import coil.fetch.g;
import coil.size.Size;
import com.yanzhenjie.andserver.util.h;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import okhttp3.d;
import okhttp3.e;
import okhttp3.e0;
import okhttp3.v;
import okhttp3.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpFetcher.kt */
public abstract class i<T> implements g<T> {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    private static final d b = new d.a().d().e().a();
    private static final d c = new d.a().d().f().a();
    @NotNull
    private final e.a d;

    @f(c = "coil.fetch.HttpFetcher", f = "HttpFetcher.kt", l = {125}, m = "fetch$suspendImpl")
    /* compiled from: HttpFetcher.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ i<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(i<T> iVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return i.d(this.this$0, (c) null, (Object) null, (Size) null, (m) null, this);
        }
    }

    @Nullable
    public Object b(@NotNull c cVar, @NotNull T t, @NotNull Size size, @NotNull m mVar, @NotNull kotlin.coroutines.d<? super f> dVar) {
        return d(this, cVar, t, size, mVar, dVar);
    }

    @NotNull
    public abstract v f(@NotNull T t);

    public i(@NotNull e.a callFactory) {
        k.e(callFactory, "callFactory");
        this.d = callFactory;
    }

    public boolean a(@NotNull T data) {
        return g.a.a(this, data);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object d(coil.fetch.i r16, coil.bitmap.c r17, java.lang.Object r18, coil.size.Size r19, coil.decode.m r20, kotlin.coroutines.d r21) {
        /*
            r0 = r21
            boolean r1 = r0 instanceof coil.fetch.i.b
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.fetch.i$b r1 = (coil.fetch.i.b) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r16
            goto L_0x0020
        L_0x0018:
            coil.fetch.i$b r1 = new coil.fetch.i$b
            r2 = r16
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x004c;
                case 1: goto L_0x0035;
                default: goto L_0x002b;
            }
        L_0x002b:
            r21 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            r2 = 0
            r3 = r2
            r2 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            okhttp3.e r4 = (okhttp3.e) r4
            java.lang.Object r4 = r0.L$1
            okhttp3.v r4 = (okhttp3.v) r4
            java.lang.Object r5 = r0.L$0
            coil.fetch.i r5 = (coil.fetch.i) r5
            kotlin.p.b(r1)
            r21 = r1
            goto L_0x0122
        L_0x004c:
            kotlin.p.b(r1)
            r5 = r16
            r2 = r18
            r4 = r20
            r6 = r17
            r7 = r19
            okhttp3.v r8 = r5.f(r2)
            okhttp3.b0$a r9 = new okhttp3.b0$a
            r9.<init>()
            okhttp3.b0$a r9 = r9.q(r8)
            okhttp3.u r10 = r4.g()
            okhttp3.b0$a r9 = r9.h(r10)
            coil.request.c r10 = r4.h()
            boolean r10 = r10.getReadEnabled()
            coil.request.c r11 = r4.f()
            boolean r11 = r11.getReadEnabled()
            if (r10 != 0) goto L_0x0089
            if (r11 == 0) goto L_0x0089
            okhttp3.d r12 = okhttp3.d.b
            r9.c(r12)
            goto L_0x00ac
        L_0x0089:
            if (r10 == 0) goto L_0x00a3
            if (r11 != 0) goto L_0x00a3
            coil.request.c r12 = r4.f()
            boolean r12 = r12.getWriteEnabled()
            if (r12 == 0) goto L_0x009d
            okhttp3.d r12 = okhttp3.d.a
            r9.c(r12)
            goto L_0x00ac
        L_0x009d:
            okhttp3.d r12 = b
            r9.c(r12)
            goto L_0x00ac
        L_0x00a3:
            if (r10 != 0) goto L_0x00ac
            if (r11 != 0) goto L_0x00ac
            okhttp3.d r12 = c
            r9.c(r12)
        L_0x00ac:
            kotlin.coroutines.g r12 = r0.getContext()
            kotlinx.coroutines.i0$a r13 = kotlinx.coroutines.i0.Key
            kotlin.coroutines.g$b r12 = r12.get(r13)
            boolean r12 = r12 instanceof kotlinx.coroutines.k2
            if (r12 == 0) goto L_0x00d3
            if (r10 != 0) goto L_0x00cd
            okhttp3.e$a r3 = r5.d
            okhttp3.b0 r12 = r9.b()
            okhttp3.e r3 = r3.a(r12)
            okhttp3.d0 r3 = r3.execute()
            r21 = r1
            goto L_0x0128
        L_0x00cd:
            android.os.NetworkOnMainThreadException r3 = new android.os.NetworkOnMainThreadException
            r3.<init>()
            throw r3
        L_0x00d3:
            okhttp3.e$a r12 = r5.d
            okhttp3.b0 r13 = r9.b()
            okhttp3.e r12 = r12.a(r13)
            java.lang.String r13 = "callFactory.newCall(request.build())"
            kotlin.jvm.internal.k.d(r12, r13)
            r13 = 0
            r6 = 0
            r0.L$0 = r5
            r0.L$1 = r8
            r0.L$2 = r12
            r7 = 1
            r0.label = r7
            r14 = r0
            r15 = 0
            r21 = r1
            kotlinx.coroutines.o r1 = new kotlinx.coroutines.o
            r16 = r2
            kotlin.coroutines.d r2 = kotlin.coroutines.intrinsics.b.c(r14)
            r1.<init>(r2, r7)
            r1.w()
            r2 = r1
            r7 = 0
            r18 = r4
            coil.util.k r4 = new coil.util.k
            r4.<init>(r12, r2)
            r12.o0(r4)
            r2.f(r4)
            java.lang.Object r1 = r1.t()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            if (r1 != r2) goto L_0x011c
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x011c:
            if (r1 != r3) goto L_0x011f
            return r3
        L_0x011f:
            r3 = r6
            r4 = r8
            r2 = r13
        L_0x0122:
            r3 = r1
            okhttp3.d0 r3 = (okhttp3.d0) r3
            r8 = r4
        L_0x0128:
            r1 = r3
            boolean r2 = r1.h0()
            if (r2 != 0) goto L_0x0145
            okhttp3.e0 r2 = r1.a()
            if (r2 != 0) goto L_0x0136
            goto L_0x0139
        L_0x0136:
            r2.close()
        L_0x0139:
            coil.network.HttpException r2 = new coil.network.HttpException
            java.lang.String r3 = "response"
            kotlin.jvm.internal.k.d(r1, r3)
            r2.<init>(r1)
            throw r2
        L_0x0145:
            okhttp3.e0 r2 = r1.a()
            if (r2 == 0) goto L_0x0169
            coil.fetch.m r3 = new coil.fetch.m
            okio.e r4 = r2.source()
            java.lang.String r6 = "body.source()"
            kotlin.jvm.internal.k.d(r4, r6)
            java.lang.String r5 = r5.e(r8, r2)
            okhttp3.d0 r6 = r1.g()
            if (r6 == 0) goto L_0x0163
            coil.decode.b r6 = coil.decode.b.DISK
            goto L_0x0165
        L_0x0163:
            coil.decode.b r6 = coil.decode.b.NETWORK
        L_0x0165:
            r3.<init>(r4, r5, r6)
            return r3
        L_0x0169:
            r2 = 0
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "Null response body!"
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.fetch.i.d(coil.fetch.i, coil.bitmap.c, java.lang.Object, coil.size.Size, coil.decode.m, kotlin.coroutines.d):java.lang.Object");
    }

    @VisibleForTesting
    @Nullable
    public final String e(@NotNull v data, @NotNull e0 body) {
        k.e(data, "data");
        k.e(body, "body");
        x contentType = body.contentType();
        String rawContentType = contentType == null ? null : contentType.toString();
        if (rawContentType == null || w.N(rawContentType, h.TEXT_PLAIN_VALUE, false, 2, (Object) null)) {
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            k.d(singleton, "getSingleton()");
            String it = coil.util.f.e(singleton, data.toString());
            if (it != null) {
                return it;
            }
        }
        if (rawContentType == null) {
            return null;
        }
        return kotlin.text.x.Y0(rawContentType, ';', (String) null, 2, (Object) null);
    }

    /* compiled from: HttpFetcher.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}

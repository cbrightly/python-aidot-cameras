package io.ktor.routing;

import androidx.core.app.NotificationCompat;
import io.ktor.application.b;
import io.ktor.http.a;
import io.ktor.request.e;
import io.ktor.routing.w;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingResolve.kt */
public final class v {
    @NotNull
    private final List<String> a;
    private final x b;
    @NotNull
    private final i c;
    @NotNull
    private final b d;
    private final List<l<x, x>> e;

    public v(@NotNull i routing, @NotNull b call, @NotNull List<? extends l<? super x, x>> tracers) {
        k.f(routing, "routing");
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(tracers, "tracers");
        this.c = routing;
        this.d = call;
        this.e = tracers;
        List<String> c2 = c(e.g(call.getRequest()));
        this.a = c2;
        this.b = tracers.isEmpty() ? null : new x(call, c2);
    }

    @NotNull
    public final b a() {
        return this.d;
    }

    @NotNull
    public final List<String> b() {
        return this.a;
    }

    private final List<String> c(String path) {
        if ((path.length() == 0) || k.a(path, "/")) {
            return q.g();
        }
        int length = path.length();
        int beginSegment = 0;
        int nextSegment = 0;
        CharSequence $this$count$iv = path;
        int count$iv = 0;
        for (int i = 0; i < $this$count$iv.length(); i++) {
            if (($this$count$iv.charAt(i) == '/' ? (char) 1 : 0) != 0) {
                count$iv++;
            }
        }
        ArrayList segments = new ArrayList(count$iv);
        while (nextSegment < length) {
            nextSegment = kotlin.text.x.e0(path, '/', beginSegment, false, 4, (Object) null);
            if (nextSegment == -1) {
                nextSegment = length;
            }
            if (nextSegment == beginSegment) {
                beginSegment = nextSegment + 1;
            } else {
                segments.add(a.e(path, beginSegment, nextSegment, (Charset) null, 4, (Object) null));
                beginSegment = nextSegment + 1;
            }
        }
        return segments;
    }

    @NotNull
    public final w d() {
        i root = this.c;
        k rootResult = root.I().a(this, 0);
        if (!rootResult.i()) {
            return f(root);
        }
        w result = e(root, rootResult.h());
        x $this$apply = this.b;
        if ($this$apply != null) {
            for (l it : this.e) {
                it.invoke($this$apply);
            }
        }
        return result;
    }

    private final w.a f(i root) {
        w.a aVar = new w.a(root, "rootPath didn't match");
        w.a result = aVar;
        x xVar = this.b;
        if (xVar != null) {
            xVar.d(root, 0, result);
        }
        return aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00df A[LOOP:0: B:5:0x001e->B:39:0x00df, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00e3 A[EDGE_INSN: B:63:0x00e3->B:40:0x00e3 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final io.ktor.routing.w e(io.ktor.routing.i r20, int r21) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            io.ktor.routing.x r3 = r0.b
            if (r3 == 0) goto L_0x000d
            r3.a(r1, r2)
        L_0x000d:
            r3 = 0
            r4 = r3
            r5 = r3
            r6 = 0
            r8 = 0
            java.util.List r9 = r20.G()
            int r9 = kotlin.collections.q.i(r9)
            if (r9 < 0) goto L_0x00e3
        L_0x001e:
            java.util.List r10 = r20.G()
            java.lang.Object r10 = r10.get(r8)
            io.ktor.routing.i r10 = (io.ktor.routing.i) r10
            io.ktor.routing.j r11 = r10.I()
            io.ktor.routing.k r11 = r11.a(r0, r2)
            boolean r12 = r11.i()
            if (r12 != 0) goto L_0x0048
            io.ktor.routing.x r12 = r0.b
            if (r12 == 0) goto L_0x0044
            io.ktor.routing.w$a r13 = new io.ktor.routing.w$a
            java.lang.String r14 = "Selector didn't match"
            r13.<init>(r10, r14)
            r12.d(r10, r2, r13)
        L_0x0044:
            r16 = r5
            goto L_0x00da
        L_0x0048:
            double r12 = r11.g()
            int r14 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r14 >= 0) goto L_0x0065
            io.ktor.routing.x r14 = r0.b
            if (r14 == 0) goto L_0x0061
            io.ktor.routing.w$a r15 = new io.ktor.routing.w$a
            r16 = r5
            java.lang.String r5 = "Better match was already found"
            r15.<init>(r10, r5)
            r14.d(r10, r2, r15)
            goto L_0x0063
        L_0x0061:
            r16 = r5
        L_0x0063:
            goto L_0x00da
        L_0x0065:
            r16 = r5
            int r5 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r5 != 0) goto L_0x0093
            if (r3 != 0) goto L_0x0070
            kotlin.jvm.internal.k.n()
        L_0x0070:
            io.ktor.routing.j r5 = r3.I()
            double r14 = r5.b()
            io.ktor.routing.j r5 = r10.I()
            double r17 = r5.b()
            int r5 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r5 < 0) goto L_0x0093
            io.ktor.routing.x r5 = r0.b
            if (r5 == 0) goto L_0x0092
            io.ktor.routing.w$a r14 = new io.ktor.routing.w$a
            java.lang.String r15 = "Lost in ambiguity tie"
            r14.<init>(r10, r15)
            r5.d(r10, r2, r14)
        L_0x0092:
            goto L_0x00da
        L_0x0093:
            int r5 = r11.h()
            int r5 = r5 + r2
            io.ktor.routing.w r5 = r0.e(r10, r5)
            boolean r14 = r5 instanceof io.ktor.routing.w.a
            if (r14 == 0) goto L_0x00aa
            if (r4 != 0) goto L_0x00da
            io.ktor.routing.i r4 = r5.b()
            r5 = r16
            goto L_0x00dc
        L_0x00aa:
            boolean r14 = r5 instanceof io.ktor.routing.w.b
            if (r14 == 0) goto L_0x00da
            r3 = r10
            r6 = r12
            io.ktor.http.y r14 = r11.f()
            boolean r14 = r14.isEmpty()
            if (r14 == 0) goto L_0x00be
            r17 = r3
            r15 = r5
            goto L_0x00d5
        L_0x00be:
            io.ktor.http.y r14 = r11.f()
            io.ktor.http.y r15 = r5.a()
            io.ktor.http.y r14 = io.ktor.http.b0.d(r14, r15)
            io.ktor.routing.w$b r15 = new io.ktor.routing.w$b
            r17 = r3
            io.ktor.routing.i r3 = r5.b()
            r15.<init>(r3, r14)
        L_0x00d5:
            r3 = r15
            r5 = r3
            r3 = r17
            goto L_0x00dc
        L_0x00da:
            r5 = r16
        L_0x00dc:
            if (r8 == r9) goto L_0x00e3
            int r8 = r8 + 1
            goto L_0x001e
        L_0x00e3:
            java.util.List<java.lang.String> r8 = r0.a
            int r8 = r8.size()
            if (r2 != r8) goto L_0x010f
            java.util.ArrayList r8 = r20.H()
            boolean r8 = r8.isEmpty()
            r8 = r8 ^ 1
            if (r8 == 0) goto L_0x010f
            if (r5 == 0) goto L_0x0103
            r8 = 4596373779694328218(0x3fc999999999999a, double:0.2)
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0103
            goto L_0x0112
        L_0x0103:
            io.ktor.routing.w$b r8 = new io.ktor.routing.w$b
            io.ktor.http.y$a r9 = io.ktor.http.y.b
            io.ktor.http.y r9 = r9.a()
            r8.<init>(r1, r9)
            goto L_0x012c
        L_0x010f:
            if (r5 == 0) goto L_0x0114
        L_0x0112:
            r8 = r5
            goto L_0x012c
        L_0x0114:
            java.util.List<java.lang.String> r8 = r0.a
            int r8 = r8.size()
            if (r2 != r8) goto L_0x011f
            java.lang.String r8 = "Segments exhausted but no handlers found"
            goto L_0x0121
        L_0x011f:
            java.lang.String r8 = "Not all segments matched"
        L_0x0121:
            io.ktor.routing.w$a r9 = new io.ktor.routing.w$a
            if (r4 == 0) goto L_0x0127
            r10 = r4
            goto L_0x0128
        L_0x0127:
            r10 = r1
        L_0x0128:
            r9.<init>(r10, r8)
            r8 = r9
        L_0x012c:
            io.ktor.routing.x r9 = r0.b
            if (r9 == 0) goto L_0x0135
            r9.b(r1, r2, r8)
        L_0x0135:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.routing.v.e(io.ktor.routing.i, int):io.ktor.routing.w");
    }
}

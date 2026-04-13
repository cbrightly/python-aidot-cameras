package io.ktor.server.cio.backend;

import io.ktor.http.cio.internals.g;
import io.ktor.http.cio.n;
import io.ktor.http.v;
import io.ktor.utils.io.core.q;
import io.ktor.utils.io.i;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.channels.m;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.w;
import kotlinx.coroutines.z1;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ServerPipeline.kt */
public final class c {
    /* access modifiers changed from: private */
    public static final q a;

    @f(c = "io.ktor.server.cio.backend.ServerPipelineKt$startServerConnectionPipeline$1", f = "ServerPipeline.kt", l = {72, 81, 101, 130, 160, 162, 176}, m = "invokeSuspend")
    /* compiled from: ServerPipeline.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ b $connection;
        final /* synthetic */ kotlin.jvm.functions.q $handler;
        final /* synthetic */ g $timeout;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$10;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        boolean Z$0;
        boolean Z$1;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g gVar, b bVar, kotlin.jvm.functions.q qVar, d dVar) {
            super(2, dVar);
            this.$timeout = gVar;
            this.$connection = bVar;
            this.$handler = qVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            k.f(dVar, "completion");
            a aVar = new a(this.$timeout, this.$connection, this.$handler, dVar);
            o0 o0Var = (o0) obj;
            aVar.p$ = (o0) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((a) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 41 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v0, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v4, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v29, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r28v0, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v37, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v39, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v40, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v47, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v48, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v47, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r32v3, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v50, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v51, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v34, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v12, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v55, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v57, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v37, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v66, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v70, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v43, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v72, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v44, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v73, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v45, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v74, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v76, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v77, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v51, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v79, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v80, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v50, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v47, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v48, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v49, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v84, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v85, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v86, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v53, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v44, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v16, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v88, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: io.ktor.http.cio.m} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v28, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v90, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v91, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v92, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v21, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v94, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v95, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v63, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v51, resolved type: java.lang.CharSequence} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v97, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v19, resolved type: io.ktor.http.cio.m} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v62, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v99, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v100, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v30, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v102, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v103, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v31, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v70, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v40, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v76, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v78, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v79, resolved type: long} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v80, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v81, resolved type: kotlinx.coroutines.channels.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v83, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v84, resolved type: kotlinx.coroutines.o0} */
        /* JADX WARNING: type inference failed for: r13v0, types: [kotlinx.coroutines.o0] */
        /* JADX WARNING: type inference failed for: r13v4 */
        /* JADX WARNING: type inference failed for: r13v10 */
        /* JADX WARNING: type inference failed for: r13v13 */
        /* JADX WARNING: type inference failed for: r13v16 */
        /* JADX WARNING: type inference failed for: r13v21 */
        /* JADX WARNING: type inference failed for: r4v15, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r4v19, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r4v21 */
        /* JADX WARNING: type inference failed for: r4v22 */
        /* JADX WARNING: type inference failed for: r13v28 */
        /* JADX WARNING: type inference failed for: r4v23 */
        /* JADX WARNING: type inference failed for: r4v25 */
        /* JADX WARNING: type inference failed for: r4v27 */
        /* JADX WARNING: type inference failed for: r13v30 */
        /* JADX WARNING: type inference failed for: r13v32 */
        /* JADX WARNING: type inference failed for: r13v35 */
        /* JADX WARNING: type inference failed for: r4v36 */
        /* JADX WARNING: type inference failed for: r4v37 */
        /* JADX WARNING: type inference failed for: r4v38 */
        /* JADX WARNING: type inference failed for: r13v66 */
        /* JADX WARNING: type inference failed for: r10v61, types: [kotlinx.coroutines.o0] */
        /* JADX WARNING: type inference failed for: r13v77 */
        /* JADX WARNING: type inference failed for: r13v82 */
        /* JADX WARNING: Code restructure failed: missing block: B:101:0x03d7, code lost:
            r6 = io.ktor.http.cio.e.a(r11.c(), r3, r7, r5, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:0x03d9, code lost:
            if (r6 != false) goto L_0x03f8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:105:0x03e5, code lost:
            if (io.ktor.http.cio.e.b(r11.c(), r14, r5) == false) goto L_0x03f8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x03e7, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x03e9, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:108:0x03ea, code lost:
            r2 = r6;
            r1 = r9;
            r9 = r7;
            r7 = r12;
            r12 = r8;
            r8 = r0;
            r0 = r5;
            r5 = r3;
            r3 = r17;
            r4 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:0x03f8, code lost:
            r2 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x03f9, code lost:
            r18 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x03fc, code lost:
            if (r6 != false) goto L_0x041e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x03fe, code lost:
            if (r18 == false) goto L_0x0401;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x0407, code lost:
            r2 = io.ktor.utils.io.i.a.a();
            r1 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x040a, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x040b, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0415, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x041e, code lost:
            r1 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
            r2 = io.ktor.utils.io.g.a(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x0424, code lost:
            if (r18 == false) goto L_0x042e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x0426, code lost:
            r19 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
            r23 = kotlinx.coroutines.y.b((kotlinx.coroutines.z1) null, r1, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x042e, code lost:
            r19 = r2;
            r23 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:128:0x0432, code lost:
            r1 = r23;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:?, code lost:
            r23 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x043a, code lost:
            r26 = r0;
            r0 = kotlinx.coroutines.q0.UNDISPATCHED;
            r25 = r19;
            r2 = r2;
            r30 = r3;
            r32 = r20;
            r33 = r5;
            r34 = r6;
            r20 = r9;
            r9 = r7;
            r27 = r12;
            r12 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:133:?, code lost:
            r2 = new io.ktor.server.cio.backend.c.a.C0264a(r13, r25, r17, r1, r11, (kotlin.coroutines.d) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x045e, code lost:
            r4 = r32;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
            kotlinx.coroutines.h.c(r4, r15, r0, r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x0463, code lost:
            if (r1 == null) goto L_0x0565;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x0465, code lost:
            r13.L$0 = r4;
            r13.L$1 = r10;
            r13.L$2 = r15;
            r13.L$3 = r11;
            r3 = r17;
            r13.L$4 = r3;
            r13.L$5 = r9;
            r13.L$6 = r14;
            r12 = r23;
            r13.L$7 = r12;
            r7 = r27;
            r13.Z$0 = r7;
            r2 = r33;
            r13.L$8 = r2;
            r5 = r30;
            r13.J$0 = r5;
            r8 = r34;
            r13.Z$1 = r8;
            r0 = r25;
            r13.L$9 = r0;
            r13.L$10 = r1;
            r25 = r0;
            r13.label = 5;
            r0 = r1.r(r13);
            r17 = r1;
            r1 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:0x049c, code lost:
            if (r0 != r1) goto L_0x049f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x049e, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x049f, code lost:
            r19 = r17;
            r17 = r1;
            r1 = r9;
            r35 = r5;
            r6 = r2;
            r5 = r8;
            r2 = r25;
            r8 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x04b2, code lost:
            if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x0529;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:144:0x04b4, code lost:
            r0 = r5;
            r23 = r8;
            kotlinx.coroutines.channels.a0.a.a(r10, (java.lang.Throwable) null, 1, (java.lang.Object) null);
            r27 = r13.$connection.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:145:0x04c2, code lost:
            if (r2 == null) goto L_0x0519;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:146:0x04c4, code lost:
            r13.L$0 = r4;
            r13.L$1 = r10;
            r13.L$2 = r15;
            r13.L$3 = r11;
            r13.L$4 = r3;
            r13.L$5 = r1;
            r13.L$6 = r14;
            r13.L$7 = r12;
            r13.Z$0 = r7;
            r13.L$8 = r6;
            r8 = r23;
            r13.J$0 = r8;
            r13.Z$1 = r0;
            r13.L$9 = r2;
            r5 = r19;
            r13.L$10 = r5;
            r19 = r1;
            r13.label = 6;
            r20 = r3;
            r3 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:147:0x04fd, code lost:
            if (io.ktor.utils.io.k.c(r27, (io.ktor.utils.io.f) r2, 0, r13, 2, (java.lang.Object) null) != r3) goto L_0x0500;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:148:0x04ff, code lost:
            return r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x0500, code lost:
            r17 = r2;
            r2 = r5;
            r3 = r10;
            r1 = r13;
            r10 = r15;
            r5 = r0;
            r13 = r4;
            r4 = r18;
            r0 = r42;
            r18 = r6;
            r6 = r7;
            r7 = r8;
            r9 = r14;
            r14 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x0513, code lost:
            r2 = r0;
            r7 = r1;
            r4 = 1;
            r5 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x0519, code lost:
            r20 = r3;
            r5 = r19;
            r8 = r23;
            r19 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:152:0x0528, code lost:
            throw new kotlin.TypeCastException("null cannot be cast to non-null type io.ktor.utils.io.ByteChannel");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:153:0x0529, code lost:
            r20 = r3;
            r0 = r5;
            r3 = r17;
            r5 = r19;
            r19 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:154:0x0532, code lost:
            if (r0 != false) goto L_0x053e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x0536, code lost:
            if ((r2 instanceof io.ktor.utils.io.f) == false) goto L_0x053e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x0538, code lost:
            io.ktor.utils.io.m.a((io.ktor.utils.io.l) r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:0x053e, code lost:
            r17 = r5;
            r5 = r6;
            r1 = r19;
            r6 = r2;
            r2 = r15;
            r15 = r7;
            r7 = r13;
            r13 = r0;
            r0 = r3;
            r3 = r20;
            r20 = r18;
            r35 = r12;
            r12 = r4;
            r4 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:159:0x0553, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:0x0554, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x055d, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:0x0565, code lost:
            r3 = r17;
            r17 = r1;
            r0 = r20;
            r1 = r9;
            r20 = r18;
            r5 = r33;
            r2 = r15;
            r6 = r25;
            r15 = r27;
            r7 = r13;
            r13 = r34;
            r8 = r30;
            r12 = r4;
            r4 = r23;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:164:0x058a, code lost:
            if (r13 == false) goto L_0x0680;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:165:0x058c, code lost:
            r18 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:168:0x0590, code lost:
            if ((r6 instanceof io.ktor.utils.io.l) == false) goto L_0x0661;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:?, code lost:
            r31 = r7.$connection.a();
            r32 = (io.ktor.utils.io.l) r6;
            r7.L$0 = r12;
            r7.L$1 = r10;
            r7.L$2 = r2;
            r7.L$3 = r11;
            r7.L$4 = r3;
            r7.L$5 = r1;
            r7.L$6 = r14;
            r7.L$7 = r4;
            r7.Z$0 = r15;
            r7.L$8 = r5;
            r7.J$0 = r8;
            r7.Z$1 = r13;
            r7.L$9 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:0x05bb, code lost:
            r19 = r2;
            r2 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:173:?, code lost:
            r7.L$10 = r2;
            r7.label = 7;
            r0 = io.ktor.http.cio.e.c(r8, r1, r5, r31, r32, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:174:0x05d0, code lost:
            r17 = r1;
            r1 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:175:0x05d4, code lost:
            if (r0 != r1) goto L_0x05d7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:176:0x05d6, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:177:0x05d7, code lost:
            r0 = r4;
            r4 = r2;
            r2 = r1;
            r1 = r42;
            r35 = r19;
            r19 = r3;
            r3 = r11;
            r11 = r13;
            r13 = r17;
            r17 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:180:?, code lost:
            io.ktor.utils.io.m.a((io.ktor.utils.io.l) r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:181:0x05ed, code lost:
            r42 = r4;
            r4 = r0;
            r0 = r15;
            r15 = r14;
            r35 = r2;
            r2 = r1;
            r1 = r13;
            r13 = r8;
            r9 = r11;
            r11 = r3;
            r8 = r7;
            r3 = r19;
            r7 = r6;
            r6 = r5;
            r5 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:182:0x0602, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:183:0x0603, code lost:
            r2 = r1;
            r3 = r10;
            r13 = r12;
            r10 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:185:0x060b, code lost:
            r2 = r1;
            r3 = r10;
            r13 = r12;
            r10 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:186:0x0612, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:187:0x0613, code lost:
            r17 = r1;
            r1 = r42;
            r18 = r4;
            r4 = r2;
            r2 = r3;
            r3 = r11;
            r35 = r8;
            r8 = r7;
            r9 = r12;
            r7 = r14;
            r14 = r19;
            r11 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:188:0x0626, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:189:0x0627, code lost:
            r19 = r2;
            r2 = r17;
            r17 = r1;
            r1 = r42;
            r18 = r4;
            r4 = r2;
            r2 = r3;
            r3 = r11;
            r35 = r8;
            r8 = r7;
            r9 = r12;
            r7 = r14;
            r14 = r19;
            r11 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:203:0x0661, code lost:
            r19 = r2;
            r2 = r17;
            r17 = r1;
            r1 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:204:0x066a, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:205:0x066b, code lost:
            r19 = r2;
            r2 = r42;
            r3 = r10;
            r13 = r12;
            r10 = r19;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:207:0x0676, code lost:
            r19 = r2;
            r2 = r42;
            r3 = r10;
            r13 = r12;
            r10 = r19;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:208:0x0680, code lost:
            r19 = r2;
            r2 = r17;
            r17 = r1;
            r1 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:209:0x0687, code lost:
            r0 = r15;
            r15 = r14;
            r35 = r2;
            r2 = r42;
            r42 = r35;
            r36 = r5;
            r5 = r1;
            r1 = r17;
            r17 = r19;
            r37 = r6;
            r6 = r36;
            r38 = r7;
            r7 = r37;
            r39 = r8;
            r8 = r38;
            r9 = r13;
            r13 = r39;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:212:0x06a9, code lost:
            if (io.ktor.server.cio.backend.c.b(r0, r6) == false) goto L_0x06b4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:213:0x06ab, code lost:
            r7 = r8;
            r3 = r10;
            r13 = r12;
            r10 = r17;
            r4 = 1;
            r5 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:214:0x06b4, code lost:
            r1 = r41;
            r4 = r5;
            r7 = r8;
            r5 = r10;
            r3 = r12;
            r6 = r17;
            r0 = r26;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:215:0x06c0, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:216:0x06c1, code lost:
            r7 = r8;
            r3 = r10;
            r13 = r12;
            r10 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:218:0x06c9, code lost:
            r7 = r8;
            r3 = r10;
            r13 = r12;
            r10 = r17;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:219:0x06d0, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:220:0x06d1, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r32;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:222:0x06dc, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r32;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:223:0x06e6, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:224:0x06e7, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:226:0x06f2, code lost:
            r2 = r42;
            r3 = r10;
            r7 = r13;
            r10 = r15;
            r13 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:227:0x06fc, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:228:0x06fd, code lost:
            r18 = r3;
            r1 = r9;
            r3 = r17;
            r4 = r20;
            r9 = r7;
            r7 = r12;
            r12 = r8;
            r8 = r0;
            r0 = r5;
            r2 = r6;
            r5 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:229:0x070e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:230:0x070f, code lost:
            r1 = r9;
            r3 = r17;
            r4 = r20;
            r9 = r7;
            r7 = r12;
            r12 = r8;
            r8 = r0;
            r0 = r5;
            r2 = r6;
            r5 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:231:0x071d, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:232:0x071e, code lost:
            r29 = r4;
            r1 = r9;
            r4 = r3;
            r9 = r7;
            r7 = r12;
            r3 = r2;
            r12 = r8;
            r8 = r0;
            r0 = r5;
            r2 = r6;
            r5 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:234:?, code lost:
            r11.release();
            r18 = r2;
            r2 = io.ktor.server.cio.backend.c.a().v1();
            r13.L$0 = r4;
            r13.L$1 = r10;
            r13.L$2 = r15;
            r13.L$3 = r11;
            r13.L$4 = r3;
            r13.L$5 = r9;
            r13.L$6 = r14;
            r13.L$7 = r12;
            r13.Z$0 = r7;
            r13.L$8 = r0;
            r13.J$0 = r5;
            r13.L$9 = r8;
            r17 = r0;
            r13.label = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:235:0x0759, code lost:
            if (r3.x(r2, r13) != r1) goto L_0x075c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:236:0x075b, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:237:0x075c, code lost:
            r1 = r42;
            r2 = r8;
            r0 = r17;
            r17 = r3;
            r3 = r10;
            r10 = r15;
            r15 = r4;
            r4 = r29;
            r6 = r7;
            r7 = r5;
            r5 = r18;
            r37 = r14;
            r14 = r9;
            r9 = r37;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:239:?, code lost:
            io.ktor.utils.io.m.a(r17);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:240:0x0778, code lost:
            throw r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:241:0x0779, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:242:0x077a, code lost:
            r2 = r1;
            r7 = r13;
            r13 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:244:0x0780, code lost:
            r2 = r1;
            r7 = r13;
            r13 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:245:0x0785, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:246:0x0786, code lost:
            r1 = r42;
            r4 = r3;
            r3 = r5;
            r35 = r13;
            r13 = r6;
            r15 = r10;
            r10 = r8;
            r8 = r7;
            r7 = r9;
            r9 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:247:0x0798, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:248:0x0799, code lost:
            r1 = r42;
            r4 = r3;
            r3 = r5;
            r35 = r13;
            r13 = r6;
            r15 = r10;
            r10 = r8;
            r8 = r7;
            r7 = r9;
            r9 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:256:0x07c1, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:257:0x07c2, code lost:
            r42 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:259:0x07c7, code lost:
            r42 = r2;
            r13 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:260:0x07cb, code lost:
            r42 = r2;
            r4 = 1;
            r5 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:261:0x07d0, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:262:0x07d1, code lost:
            r42 = r2;
            r13 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:263:0x07d4, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:264:0x07d5, code lost:
            r42 = r2;
            r13 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:265:0x07d8, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:266:0x07d9, code lost:
            r42 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:267:0x07dc, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:268:0x07dd, code lost:
            r13 = r3;
            r3 = r5;
            r10 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:280:0x0818, code lost:
            r13 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:281:0x081a, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:282:0x081b, code lost:
            r13 = r3;
            r3 = r5;
            r10 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:285:0x0820, code lost:
            r0 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:286:0x0821, code lost:
            r13 = r3;
            r3 = r5;
            r10 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x02c5, code lost:
            r13 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
            r8 = r7.$connection.a();
            r7.L$0 = r3;
            r7.L$1 = r5;
            r7.L$2 = r6;
            r7.label = 1;
            r8 = io.ktor.http.cio.i.k(r8, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x02d8, code lost:
            if (r8 != r4) goto L_0x02db;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x02da, code lost:
            return r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x02db, code lost:
            r13 = r3;
            r3 = r5;
            r10 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
            r8 = (io.ktor.http.cio.m) r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x02e0, code lost:
            if (r8 == null) goto L_0x07cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x02e2, code lost:
            r11 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
            r5 = io.ktor.utils.io.g.b(false, 1, (java.lang.Object) null);
            r6 = r11.a().e(org.glassfish.grizzly.http.server.Constants.TRANSFERENCODING);
            r8 = r11.a().e(org.glassfish.tyrus.spi.UpgradeRequest.UPGRADE);
            r12 = r11.a().e("Content-Type");
            r9 = kotlin.jvm.internal.k.a(r11.i(), org.glassfish.grizzly.http.server.Constants.HTTP_11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0314, code lost:
            r42 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            r7.L$0 = r13;
            r7.L$1 = r3;
            r7.L$2 = r10;
            r7.L$3 = r11;
            r7.L$4 = r5;
            r7.L$5 = r6;
            r7.L$6 = r8;
            r7.L$7 = r12;
            r7.Z$0 = r9;
            r7.J$0 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x0331, code lost:
            r18 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
            r7.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x033a, code lost:
            if (r3.D(r5, r7) != r4) goto L_0x033d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x033c, code lost:
            return r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x033d, code lost:
            r1 = r42;
            r2 = r5;
            r5 = null;
            r14 = r8;
            r8 = r12;
            r12 = r9;
            r9 = r4;
            r4 = false;
            r35 = r10;
            r10 = r3;
            r3 = r13;
            r13 = r7;
            r7 = r6;
            r6 = false;
            r15 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x0350, code lost:
            r42 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0359, code lost:
            r17 = r2;
            r20 = r3;
            r29 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
            r1 = io.ktor.http.cio.f.d(r11.a(), r0, 0, 2, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x0377, code lost:
            r5 = io.ktor.http.cio.d.e.b(r11.a().e("Connection"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x0379, code lost:
            if (r1 == -1) goto L_0x03c3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
            r3 = io.ktor.http.cio.internals.d.i(r11.a().k(r1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x038b, code lost:
            r18 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:0x0394, code lost:
            if (r11.a().c(r0, r1 + 1) != -1) goto L_0x0399;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:0x0396, code lost:
            r3 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:93:0x03a0, code lost:
            throw new io.ktor.http.cio.ParserException("Duplicate Content-Length header");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x03a1, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x03a2, code lost:
            r18 = r3;
            r2 = r6;
            r1 = r9;
            r3 = r17;
            r4 = r20;
            r9 = r7;
            r7 = r12;
            r12 = r8;
            r8 = r0;
            r0 = r5;
            r5 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x03b3, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:0x03b4, code lost:
            r2 = r6;
            r1 = r9;
            r3 = r17;
            r4 = r20;
            r9 = r7;
            r7 = r12;
            r12 = r8;
            r8 = r0;
            r0 = r5;
            r5 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x03c3, code lost:
            r3 = -1;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:236:0x075b A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:237:0x075c  */
        /* JADX WARNING: Removed duplicated region for block: B:273:0x07ed A[Catch:{ IOException -> 0x0818 }] */
        /* JADX WARNING: Unknown variable types count: 1 */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r42) {
            /*
                r41 = this;
                r1 = r41
                java.lang.String r0 = "Content-Length"
                java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
                int r3 = r1.label
                r5 = 0
                r7 = 0
                r8 = 0
                switch(r3) {
                    case 0: goto L_0x0291;
                    case 1: goto L_0x0256;
                    case 2: goto L_0x0221;
                    case 3: goto L_0x01ba;
                    case 4: goto L_0x0158;
                    case 5: goto L_0x00e0;
                    case 6: goto L_0x0088;
                    case 7: goto L_0x0019;
                    default: goto L_0x0011;
                }
            L_0x0011:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0019:
                r3 = r7
                r10 = r7
                r11 = r5
                r12 = r7
                r13 = r7
                r14 = r7
                r15 = r5
                r16 = r7
                r17 = r7
                r18 = r7
                r19 = r7
                r20 = r5
                r21 = r7
                r22 = r7
                java.lang.Object r4 = r1.L$10
                kotlinx.coroutines.w r4 = (kotlinx.coroutines.w) r4
                java.lang.Object r6 = r1.L$9
                io.ktor.utils.io.i r6 = (io.ktor.utils.io.i) r6
                boolean r11 = r1.Z$1
                long r8 = r1.J$0
                java.lang.Object r5 = r1.L$8
                io.ktor.http.cio.d r5 = (io.ktor.http.cio.d) r5
                boolean r12 = r1.Z$0
                java.lang.Object r15 = r1.L$7
                java.lang.CharSequence r15 = (java.lang.CharSequence) r15
                java.lang.Object r7 = r1.L$6
                java.lang.CharSequence r7 = (java.lang.CharSequence) r7
                java.lang.Object r13 = r1.L$5
                java.lang.CharSequence r13 = (java.lang.CharSequence) r13
                r18 = r2
                java.lang.Object r2 = r1.L$4
                io.ktor.utils.io.f r2 = (io.ktor.utils.io.f) r2
                java.lang.Object r3 = r1.L$3
                io.ktor.http.cio.m r3 = (io.ktor.http.cio.m) r3
                java.lang.Object r14 = r1.L$2
                kotlin.coroutines.g r14 = (kotlin.coroutines.g) r14
                r19 = r2
                java.lang.Object r2 = r1.L$1
                kotlinx.coroutines.channels.a0 r2 = (kotlinx.coroutines.channels.a0) r2
                java.lang.Object r10 = r1.L$0
                kotlinx.coroutines.o0 r10 = (kotlinx.coroutines.o0) r10
                kotlin.p.b(r42)     // Catch:{ all -> 0x0077 }
                r26 = r0
                r17 = r14
                r0 = r15
                r14 = r7
                r15 = r12
                r7 = r1
                r12 = r10
                r1 = r42
                r10 = r2
                r2 = r18
                goto L_0x05e6
            L_0x0077:
                r0 = move-exception
                r17 = r13
                r18 = r15
                r13 = r11
                r15 = r12
                r11 = r8
                r9 = r10
                r8 = r1
                r10 = r2
                r2 = r19
                r1 = r42
                goto L_0x063d
            L_0x0088:
                r2 = 0
                r0 = r2
                r3 = r2
                r4 = 0
                r5 = r4
                r6 = r2
                r7 = r8
                r9 = r2
                r10 = r2
                r11 = r4
                r12 = r2
                r13 = r2
                r14 = r2
                r15 = r2
                r17 = r2
                r18 = r2
                java.lang.Object r2 = r1.L$10
                kotlinx.coroutines.w r2 = (kotlinx.coroutines.w) r2
                r18 = r0
                java.lang.Object r0 = r1.L$9
                io.ktor.utils.io.i r0 = (io.ktor.utils.io.i) r0
                boolean r5 = r1.Z$1
                long r7 = r1.J$0
                r17 = r0
                java.lang.Object r0 = r1.L$8
                io.ktor.http.cio.d r0 = (io.ktor.http.cio.d) r0
                boolean r6 = r1.Z$0
                java.lang.Object r11 = r1.L$7
                java.lang.CharSequence r11 = (java.lang.CharSequence) r11
                java.lang.Object r12 = r1.L$6
                r9 = r12
                java.lang.CharSequence r9 = (java.lang.CharSequence) r9
                java.lang.Object r12 = r1.L$5
                java.lang.CharSequence r12 = (java.lang.CharSequence) r12
                java.lang.Object r14 = r1.L$4
                io.ktor.utils.io.f r14 = (io.ktor.utils.io.f) r14
                r18 = r0
                java.lang.Object r0 = r1.L$3
                io.ktor.http.cio.m r0 = (io.ktor.http.cio.m) r0
                java.lang.Object r10 = r1.L$2
                kotlin.coroutines.g r10 = (kotlin.coroutines.g) r10
                java.lang.Object r15 = r1.L$1
                r3 = r15
                kotlinx.coroutines.channels.a0 r3 = (kotlinx.coroutines.channels.a0) r3
                java.lang.Object r15 = r1.L$0
                r13 = r15
                kotlinx.coroutines.o0 r13 = (kotlinx.coroutines.o0) r13
                kotlin.p.b(r42)     // Catch:{ IOException -> 0x01b4, all -> 0x01ae }
                r19 = r12
                r12 = r11
                r11 = r0
                r0 = r42
                goto L_0x0513
            L_0x00e0:
                r18 = r2
                r2 = 0
                r3 = r2
                r4 = r2
                r5 = 0
                r6 = r5
                r7 = r2
                r10 = r2
                r11 = r2
                r12 = r5
                r13 = r2
                r14 = r2
                r15 = r2
                r17 = r2
                r19 = r5
                r5 = r2
                r20 = r2
                java.lang.Object r2 = r1.L$10
                kotlinx.coroutines.w r2 = (kotlinx.coroutines.w) r2
                r20 = r2
                java.lang.Object r2 = r1.L$9
                io.ktor.utils.io.i r2 = (io.ktor.utils.io.i) r2
                boolean r5 = r1.Z$1
                long r8 = r1.J$0
                java.lang.Object r6 = r1.L$8
                io.ktor.http.cio.d r6 = (io.ktor.http.cio.d) r6
                boolean r7 = r1.Z$0
                java.lang.Object r12 = r1.L$7
                java.lang.CharSequence r12 = (java.lang.CharSequence) r12
                java.lang.Object r13 = r1.L$6
                r10 = r13
                java.lang.CharSequence r10 = (java.lang.CharSequence) r10
                java.lang.Object r13 = r1.L$5
                java.lang.CharSequence r13 = (java.lang.CharSequence) r13
                java.lang.Object r15 = r1.L$4
                r3 = r15
                io.ktor.utils.io.f r3 = (io.ktor.utils.io.f) r3
                java.lang.Object r15 = r1.L$3
                r11 = r15
                io.ktor.http.cio.m r11 = (io.ktor.http.cio.m) r11
                java.lang.Object r15 = r1.L$2
                kotlin.coroutines.g r15 = (kotlin.coroutines.g) r15
                r17 = r2
                java.lang.Object r2 = r1.L$1
                kotlinx.coroutines.channels.a0 r2 = (kotlinx.coroutines.channels.a0) r2
                java.lang.Object r4 = r1.L$0
                kotlinx.coroutines.o0 r4 = (kotlinx.coroutines.o0) r4
                kotlin.p.b(r42)     // Catch:{ IOException -> 0x014f, all -> 0x0146 }
                r26 = r0
                r14 = r10
                r0 = r42
                r10 = r2
                r2 = r17
                r17 = r18
                r18 = r19
                r19 = r20
                r35 = r13
                r13 = r1
                r1 = r35
                goto L_0x04ac
            L_0x0146:
                r0 = move-exception
                r7 = r1
                r3 = r2
                r13 = r4
                r10 = r15
                r2 = r42
                goto L_0x0833
            L_0x014f:
                r0 = move-exception
                r7 = r1
                r3 = r2
                r13 = r4
                r10 = r15
                r2 = r42
                goto L_0x0826
            L_0x0158:
                r2 = 0
                r0 = r2
                r3 = r2
                r4 = 0
                r5 = r4
                r6 = r2
                r7 = r8
                r9 = r2
                r10 = r2
                r11 = r4
                r12 = r2
                r13 = r2
                r14 = r2
                r15 = r2
                r17 = r2
                java.lang.Object r2 = r1.L$9
                java.lang.Throwable r2 = (java.lang.Throwable) r2
                long r7 = r1.J$0
                r17 = r0
                java.lang.Object r0 = r1.L$8
                io.ktor.http.cio.d r0 = (io.ktor.http.cio.d) r0
                boolean r6 = r1.Z$0
                java.lang.Object r11 = r1.L$7
                java.lang.CharSequence r11 = (java.lang.CharSequence) r11
                java.lang.Object r12 = r1.L$6
                r9 = r12
                java.lang.CharSequence r9 = (java.lang.CharSequence) r9
                java.lang.Object r12 = r1.L$5
                java.lang.CharSequence r12 = (java.lang.CharSequence) r12
                java.lang.Object r14 = r1.L$4
                io.ktor.utils.io.f r14 = (io.ktor.utils.io.f) r14
                r17 = r0
                java.lang.Object r0 = r1.L$3
                io.ktor.http.cio.m r0 = (io.ktor.http.cio.m) r0
                java.lang.Object r10 = r1.L$2
                kotlin.coroutines.g r10 = (kotlin.coroutines.g) r10
                java.lang.Object r15 = r1.L$1
                r3 = r15
                kotlinx.coroutines.channels.a0 r3 = (kotlinx.coroutines.channels.a0) r3
                java.lang.Object r15 = r1.L$0
                r13 = r15
                kotlinx.coroutines.o0 r13 = (kotlinx.coroutines.o0) r13
                kotlin.p.b(r42)     // Catch:{ IOException -> 0x01b4, all -> 0x01ae }
                r15 = r13
                r13 = r1
                r1 = r42
                r35 = r11
                r11 = r0
                r0 = r17
                r17 = r14
                r14 = r12
                r12 = r35
                goto L_0x0774
            L_0x01ae:
                r0 = move-exception
                r2 = r42
                r7 = r1
                goto L_0x0833
            L_0x01b4:
                r0 = move-exception
                r2 = r42
                r7 = r1
                goto L_0x0826
            L_0x01ba:
                r18 = r2
                r2 = 0
                r3 = r2
                r4 = r2
                r5 = 0
                r6 = r5
                r7 = r2
                r10 = r2
                r11 = r2
                r12 = r5
                r13 = r2
                r14 = r2
                r15 = r2
                r17 = r2
                r2 = r5
                long r8 = r1.J$0
                r5 = 0
                boolean r7 = r1.Z$0
                java.lang.Object r12 = r1.L$7
                java.lang.CharSequence r12 = (java.lang.CharSequence) r12
                java.lang.Object r13 = r1.L$6
                r10 = r13
                java.lang.CharSequence r10 = (java.lang.CharSequence) r10
                java.lang.Object r13 = r1.L$5
                java.lang.CharSequence r13 = (java.lang.CharSequence) r13
                java.lang.Object r15 = r1.L$4
                r3 = r15
                io.ktor.utils.io.f r3 = (io.ktor.utils.io.f) r3
                java.lang.Object r15 = r1.L$3
                r11 = r15
                io.ktor.http.cio.m r11 = (io.ktor.http.cio.m) r11
                java.lang.Object r15 = r1.L$2
                kotlin.coroutines.g r15 = (kotlin.coroutines.g) r15
                r17 = r2
                java.lang.Object r2 = r1.L$1
                kotlinx.coroutines.channels.a0 r2 = (kotlinx.coroutines.channels.a0) r2
                java.lang.Object r4 = r1.L$0
                kotlinx.coroutines.o0 r4 = (kotlinx.coroutines.o0) r4
                kotlin.p.b(r42)     // Catch:{ all -> 0x0215 }
                r14 = r10
                r10 = r2
                r2 = r3
                r3 = r4
                r4 = r17
                r35 = r1
                r1 = r42
                r36 = r13
                r13 = r35
                r37 = r12
                r12 = r7
                r7 = r36
                r38 = r8
                r8 = r37
                r9 = r18
                r18 = r38
                goto L_0x0350
            L_0x0215:
                r0 = move-exception
                r18 = r8
                r8 = r1
                r9 = r4
                r1 = r42
                r4 = r2
                r2 = r17
                goto L_0x07ac
            L_0x0221:
                r2 = 0
                r0 = r2
                r3 = r2
                r4 = r2
                r5 = r2
                r6 = r2
                java.lang.Object r2 = r1.L$4
                io.ktor.utils.io.f r2 = (io.ktor.utils.io.f) r2
                java.lang.Object r3 = r1.L$3
                java.lang.Throwable r3 = (java.lang.Throwable) r3
                java.lang.Object r5 = r1.L$2
                r10 = r5
                kotlin.coroutines.g r10 = (kotlin.coroutines.g) r10
                java.lang.Object r5 = r1.L$1
                kotlinx.coroutines.channels.a0 r5 = (kotlinx.coroutines.channels.a0) r5
                java.lang.Object r0 = r1.L$0
                r13 = r0
                kotlinx.coroutines.o0 r13 = (kotlinx.coroutines.o0) r13
                kotlin.p.b(r42)     // Catch:{ IOException -> 0x024f, all -> 0x0248 }
                r7 = r1
                r1 = r2
                r0 = r3
                r3 = r5
                r2 = r42
                goto L_0x0809
            L_0x0248:
                r0 = move-exception
                r2 = r42
                r7 = r1
                r3 = r5
                goto L_0x0833
            L_0x024f:
                r0 = move-exception
                r2 = r42
                r7 = r1
                r3 = r5
                goto L_0x0826
            L_0x0256:
                r18 = r2
                r2 = 0
                r3 = r2
                r4 = r2
                r5 = r2
                java.lang.Object r2 = r1.L$2
                kotlin.coroutines.g r2 = (kotlin.coroutines.g) r2
                java.lang.Object r5 = r1.L$1
                r3 = r5
                kotlinx.coroutines.channels.a0 r3 = (kotlinx.coroutines.channels.a0) r3
                java.lang.Object r5 = r1.L$0
                r4 = r5
                kotlinx.coroutines.o0 r4 = (kotlinx.coroutines.o0) r4
                kotlin.p.b(r42)     // Catch:{ IOException -> 0x0289, CancellationException -> 0x0281, all -> 0x0277 }
                r8 = r42
                r7 = r1
                r10 = r2
                r13 = r4
                r4 = r18
                r2 = r8
                goto L_0x02de
            L_0x0277:
                r0 = move-exception
                r7 = r1
                r10 = r2
                r13 = r4
                r4 = r18
                r2 = r42
                goto L_0x07e0
            L_0x0281:
                r0 = move-exception
                r7 = r1
                r10 = r2
                r13 = r4
                r2 = r42
                goto L_0x081e
            L_0x0289:
                r0 = move-exception
                r7 = r1
                r10 = r2
                r13 = r4
                r2 = r42
                goto L_0x0824
            L_0x0291:
                r18 = r2
                kotlin.p.b(r42)
                kotlinx.coroutines.o0 r2 = r1.p$
                kotlinx.coroutines.n0 r6 = io.ktor.http.cio.l.b()
                r7 = 3
                kotlinx.coroutines.q0 r8 = kotlinx.coroutines.q0.UNDISPATCHED
                r9 = 0
                io.ktor.server.cio.backend.c$a$b r10 = new io.ktor.server.cio.backend.c$a$b
                r3 = 0
                r10.<init>(r1, r3)
                r11 = 8
                r12 = 0
                r5 = r2
                kotlinx.coroutines.channels.a0 r3 = kotlinx.coroutines.channels.e.b(r5, r6, r7, r8, r9, r10, r11, r12)
                kotlinx.coroutines.n0 r4 = io.ktor.http.cio.l.c()
                kotlinx.coroutines.i0 r5 = kotlinx.coroutines.d1.d()
                kotlin.coroutines.g r4 = r4.plus(r5)
                r7 = r1
                r5 = r3
                r6 = r4
                r4 = r18
                r3 = r2
                r2 = r42
            L_0x02c3:
                io.ktor.server.cio.backend.b r8 = r7.$connection     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                io.ktor.utils.io.i r8 = r8.a()     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                r7.L$0 = r3     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                r7.L$1 = r5     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                r7.L$2 = r6     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                r9 = 1
                r7.label = r9     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                java.lang.Object r8 = io.ktor.http.cio.i.k(r8, r7)     // Catch:{ IOException -> 0x0820, CancellationException -> 0x081a, all -> 0x07dc }
                if (r8 != r4) goto L_0x02db
                return r4
            L_0x02db:
                r13 = r3
                r3 = r5
                r10 = r6
            L_0x02de:
                io.ktor.http.cio.m r8 = (io.ktor.http.cio.m) r8     // Catch:{ IOException -> 0x07d8, CancellationException -> 0x07d4, all -> 0x07d0 }
                if (r8 == 0) goto L_0x07cb
                r11 = r8
                r5 = 0
                r6 = 1
                r8 = 0
                io.ktor.utils.io.f r9 = io.ktor.utils.io.g.b(r5, r6, r8)     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                r5 = r9
                io.ktor.http.cio.f r6 = r11.a()     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                java.lang.String r8 = "Transfer-Encoding"
                java.lang.CharSequence r6 = r6.e(r8)     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                io.ktor.http.cio.f r8 = r11.a()     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                java.lang.String r9 = "Upgrade"
                java.lang.CharSequence r8 = r8.e(r9)     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                io.ktor.http.cio.f r9 = r11.a()     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                java.lang.String r12 = "Content-Type"
                java.lang.CharSequence r9 = r9.e(r12)     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                r12 = r9
                java.lang.CharSequence r9 = r11.i()     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                java.lang.String r14 = "HTTP/1.1"
                boolean r9 = kotlin.jvm.internal.k.a(r9, r14)     // Catch:{ IOException -> 0x07c6, all -> 0x07c1 }
                r14 = 0
                r42 = r2
                r1 = 0
                r15 = 0
                r17 = 0
                r7.L$0 = r13     // Catch:{ all -> 0x0798 }
                r7.L$1 = r3     // Catch:{ all -> 0x0798 }
                r7.L$2 = r10     // Catch:{ all -> 0x0798 }
                r7.L$3 = r11     // Catch:{ all -> 0x0798 }
                r7.L$4 = r5     // Catch:{ all -> 0x0798 }
                r7.L$5 = r6     // Catch:{ all -> 0x0798 }
                r7.L$6 = r8     // Catch:{ all -> 0x0798 }
                r7.L$7 = r12     // Catch:{ all -> 0x0798 }
                r7.Z$0 = r9     // Catch:{ all -> 0x0798 }
                r7.J$0 = r1     // Catch:{ all -> 0x0798 }
                r18 = r1
                r1 = 3
                r7.label = r1     // Catch:{ all -> 0x0785 }
                java.lang.Object r1 = r3.D(r5, r7)     // Catch:{ all -> 0x0785 }
                if (r1 != r4) goto L_0x033d
                return r4
            L_0x033d:
                r1 = r42
                r2 = r5
                r5 = r14
                r14 = r8
                r8 = r12
                r12 = r9
                r9 = r4
                r4 = r17
                r35 = r10
                r10 = r3
                r3 = r13
                r13 = r7
                r7 = r6
                r6 = r15
                r15 = r35
            L_0x0350:
                r42 = r1
                io.ktor.http.cio.f r1 = r11.a()     // Catch:{ all -> 0x071d }
                r17 = r2
                r20 = r3
                r29 = r4
                r2 = 2
                r3 = 0
                r4 = 0
                int r1 = io.ktor.http.cio.f.d(r1, r0, r3, r2, r4)     // Catch:{ all -> 0x070e }
                io.ktor.http.cio.d$c r2 = io.ktor.http.cio.d.e     // Catch:{ all -> 0x070e }
                io.ktor.http.cio.f r3 = r11.a()     // Catch:{ all -> 0x070e }
                java.lang.String r4 = "Connection"
                java.lang.CharSequence r3 = r3.e(r4)     // Catch:{ all -> 0x070e }
                io.ktor.http.cio.d r2 = r2.b(r3)     // Catch:{ all -> 0x070e }
                r5 = r2
                r2 = -1
                if (r1 == r2) goto L_0x03c3
                io.ktor.http.cio.f r3 = r11.a()     // Catch:{ all -> 0x03b3 }
                java.lang.CharSequence r3 = r3.k(r1)     // Catch:{ all -> 0x03b3 }
                long r3 = io.ktor.http.cio.internals.d.i(r3)     // Catch:{ all -> 0x03b3 }
                io.ktor.http.cio.f r2 = r11.a()     // Catch:{ all -> 0x03a1 }
                r18 = r3
                int r3 = r1 + 1
                int r2 = r2.c(r0, r3)     // Catch:{ all -> 0x03b3 }
                r3 = -1
                if (r2 != r3) goto L_0x0399
                r3 = r18
                goto L_0x03c5
            L_0x0399:
                io.ktor.http.cio.ParserException r0 = new io.ktor.http.cio.ParserException     // Catch:{ all -> 0x03b3 }
                java.lang.String r2 = "Duplicate Content-Length header"
                r0.<init>(r2)     // Catch:{ all -> 0x03b3 }
                throw r0     // Catch:{ all -> 0x03b3 }
            L_0x03a1:
                r0 = move-exception
                r18 = r3
                r2 = r6
                r1 = r9
                r3 = r17
                r4 = r20
                r9 = r7
                r7 = r12
                r12 = r8
                r8 = r0
                r0 = r5
                r5 = r18
                goto L_0x072b
            L_0x03b3:
                r0 = move-exception
                r2 = r6
                r1 = r9
                r3 = r17
                r4 = r20
                r9 = r7
                r7 = r12
                r12 = r8
                r8 = r0
                r0 = r5
                r5 = r18
                goto L_0x072b
            L_0x03c3:
                r3 = -1
            L_0x03c5:
                io.ktor.http.u r23 = r11.c()     // Catch:{ all -> 0x06fc }
                r24 = r3
                r26 = r7
                r27 = r5
                r28 = r8
                boolean r2 = io.ktor.http.cio.e.a(r23, r24, r26, r27, r28)     // Catch:{ all -> 0x06fc }
                r6 = r2
                if (r6 != 0) goto L_0x03f8
                io.ktor.http.u r2 = r11.c()     // Catch:{ all -> 0x03e9 }
                boolean r2 = io.ktor.http.cio.e.b(r2, r14, r5)     // Catch:{ all -> 0x03e9 }
                if (r2 == 0) goto L_0x03f8
                r2 = 1
                goto L_0x03f9
            L_0x03e9:
                r0 = move-exception
                r2 = r6
                r1 = r9
                r9 = r7
                r7 = r12
                r12 = r8
                r8 = r0
                r0 = r5
                r5 = r3
                r3 = r17
                r4 = r20
                goto L_0x072b
            L_0x03f8:
                r2 = 0
            L_0x03f9:
                r18 = r2
                if (r6 != 0) goto L_0x041e
                if (r18 == 0) goto L_0x0401
                goto L_0x041e
            L_0x0401:
                io.ktor.utils.io.i$a r1 = io.ktor.utils.io.i.a     // Catch:{ IOException -> 0x0414, all -> 0x040a }
                io.ktor.utils.io.i r1 = r1.a()     // Catch:{ IOException -> 0x0414, all -> 0x040a }
                r2 = r1
                r1 = 1
                goto L_0x0423
            L_0x040a:
                r0 = move-exception
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r20
                goto L_0x0833
            L_0x0414:
                r0 = move-exception
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r20
                goto L_0x0826
            L_0x041e:
                r1 = 1
                io.ktor.utils.io.f r2 = io.ktor.utils.io.g.a(r1)     // Catch:{ IOException -> 0x06f1, all -> 0x06e6 }
            L_0x0423:
                if (r18 == 0) goto L_0x042e
                r19 = r2
                r2 = 0
                kotlinx.coroutines.w r23 = kotlinx.coroutines.y.b(r2, r1, r2)     // Catch:{ IOException -> 0x0414, all -> 0x040a }
                goto L_0x0432
            L_0x042e:
                r19 = r2
                r23 = 0
            L_0x0432:
                r1 = r23
                kotlinx.coroutines.q0 r2 = kotlinx.coroutines.q0.UNDISPATCHED     // Catch:{ IOException -> 0x06f1, all -> 0x06e6 }
                r23 = r8
                io.ktor.server.cio.backend.c$a$a r8 = new io.ktor.server.cio.backend.c$a$a     // Catch:{ IOException -> 0x06f1, all -> 0x06e6 }
                r24 = 0
                r26 = r0
                r0 = r2
                r25 = r19
                r2 = r8
                r30 = r3
                r4 = r20
                r3 = r13
                r32 = r4
                r4 = r25
                r33 = r5
                r5 = r17
                r34 = r6
                r6 = r1
                r20 = r9
                r9 = r7
                r7 = r11
                r27 = r12
                r12 = r8
                r8 = r24
                r2.<init>(r3, r4, r5, r6, r7, r8)     // Catch:{ IOException -> 0x06db, all -> 0x06d0 }
                r4 = r32
                kotlinx.coroutines.h.c(r4, r15, r0, r12)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                if (r1 == 0) goto L_0x0565
                r13.L$0 = r4     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$1 = r10     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$2 = r15     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$3 = r11     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r3 = r17
                r13.L$4 = r3     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$5 = r9     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$6 = r14     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r12 = r23
                r13.L$7 = r12     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r7 = r27
                r13.Z$0 = r7     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r2 = r33
                r13.L$8 = r2     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r5 = r30
                r13.J$0 = r5     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r8 = r34
                r13.Z$1 = r8     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r0 = r25
                r13.L$9 = r0     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$10 = r1     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r25 = r0
                r0 = 5
                r13.label = r0     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                java.lang.Object r0 = r1.r(r13)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r17 = r1
                r1 = r20
                if (r0 != r1) goto L_0x049f
                return r1
            L_0x049f:
                r19 = r17
                r17 = r1
                r1 = r9
                r35 = r5
                r6 = r2
                r5 = r8
                r2 = r25
                r8 = r35
            L_0x04ac:
                java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                boolean r0 = r0.booleanValue()     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                if (r0 == 0) goto L_0x0529
                r0 = r5
                r23 = r8
                r5 = 1
                r8 = 0
                kotlinx.coroutines.channels.a0.a.a(r10, r8, r5, r8)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                io.ktor.server.cio.backend.b r5 = r13.$connection     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                io.ktor.utils.io.i r27 = r5.a()     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                if (r2 == 0) goto L_0x0519
                r28 = r2
                io.ktor.utils.io.f r28 = (io.ktor.utils.io.f) r28     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r29 = 0
                r32 = 2
                r33 = 0
                r13.L$0 = r4     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$1 = r10     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$2 = r15     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$3 = r11     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$4 = r3     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$5 = r1     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$6 = r14     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$7 = r12     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.Z$0 = r7     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$8 = r6     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r8 = r23
                r13.J$0 = r8     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.Z$1 = r0     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$9 = r2     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r5 = r19
                r13.L$10 = r5     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r19 = r1
                r1 = 6
                r13.label = r1     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r31 = r13
                java.lang.Object r1 = io.ktor.utils.io.k.c(r27, r28, r29, r31, r32, r33)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r20 = r3
                r3 = r17
                if (r1 != r3) goto L_0x0500
                return r3
            L_0x0500:
                r17 = r2
                r2 = r5
                r3 = r10
                r1 = r13
                r10 = r15
                r5 = r0
                r13 = r4
                r4 = r18
                r0 = r42
                r18 = r6
                r6 = r7
                r7 = r8
                r9 = r14
                r14 = r20
            L_0x0513:
                r2 = r0
                r7 = r1
                r4 = 1
                r5 = 0
                goto L_0x0812
            L_0x0519:
                r20 = r3
                r5 = r19
                r8 = r23
                r19 = r1
                kotlin.TypeCastException r1 = new kotlin.TypeCastException     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                java.lang.String r3 = "null cannot be cast to non-null type io.ktor.utils.io.ByteChannel"
                r1.<init>(r3)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                throw r1     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
            L_0x0529:
                r20 = r3
                r0 = r5
                r3 = r17
                r5 = r19
                r19 = r1
                if (r0 != 0) goto L_0x053e
                boolean r1 = r2 instanceof io.ktor.utils.io.f     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                if (r1 == 0) goto L_0x053e
                r1 = r2
                io.ktor.utils.io.l r1 = (io.ktor.utils.io.l) r1     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                io.ktor.utils.io.m.a(r1)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
            L_0x053e:
                r17 = r5
                r5 = r6
                r1 = r19
                r6 = r2
                r2 = r15
                r15 = r7
                r7 = r13
                r13 = r0
                r0 = r3
                r3 = r20
                r20 = r18
                r35 = r12
                r12 = r4
                r4 = r35
                goto L_0x0589
            L_0x0553:
                r0 = move-exception
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r4
                goto L_0x0833
            L_0x055c:
                r0 = move-exception
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r4
                goto L_0x0826
            L_0x0565:
                r3 = r17
                r12 = r23
                r7 = r27
                r5 = r30
                r2 = r33
                r8 = r34
                r17 = r1
                r1 = r20
                r0 = r1
                r1 = r9
                r20 = r18
                r35 = r5
                r5 = r2
                r2 = r15
                r6 = r25
                r15 = r7
                r7 = r13
                r13 = r8
                r8 = r35
                r37 = r12
                r12 = r4
                r4 = r37
            L_0x0589:
                if (r13 == 0) goto L_0x0680
                r18 = r0
                boolean r0 = r6 instanceof io.ktor.utils.io.l     // Catch:{ IOException -> 0x0675, all -> 0x066a }
                if (r0 == 0) goto L_0x0661
                io.ktor.server.cio.backend.b r0 = r7.$connection     // Catch:{ all -> 0x0626 }
                io.ktor.utils.io.i r31 = r0.a()     // Catch:{ all -> 0x0626 }
                r32 = r6
                io.ktor.utils.io.l r32 = (io.ktor.utils.io.l) r32     // Catch:{ all -> 0x0626 }
                r7.L$0 = r12     // Catch:{ all -> 0x0626 }
                r7.L$1 = r10     // Catch:{ all -> 0x0626 }
                r7.L$2 = r2     // Catch:{ all -> 0x0626 }
                r7.L$3 = r11     // Catch:{ all -> 0x0626 }
                r7.L$4 = r3     // Catch:{ all -> 0x0626 }
                r7.L$5 = r1     // Catch:{ all -> 0x0626 }
                r7.L$6 = r14     // Catch:{ all -> 0x0626 }
                r7.L$7 = r4     // Catch:{ all -> 0x0626 }
                r7.Z$0 = r15     // Catch:{ all -> 0x0626 }
                r7.L$8 = r5     // Catch:{ all -> 0x0626 }
                r7.J$0 = r8     // Catch:{ all -> 0x0626 }
                r7.Z$1 = r13     // Catch:{ all -> 0x0626 }
                r7.L$9 = r6     // Catch:{ all -> 0x0626 }
                r19 = r2
                r2 = r17
                r7.L$10 = r2     // Catch:{ all -> 0x0612 }
                r0 = 7
                r7.label = r0     // Catch:{ all -> 0x0612 }
                r27 = r8
                r29 = r1
                r30 = r5
                r33 = r7
                java.lang.Object r0 = io.ktor.http.cio.e.c(r27, r29, r30, r31, r32, r33)     // Catch:{ all -> 0x0612 }
                r17 = r1
                r1 = r18
                if (r0 != r1) goto L_0x05d7
                return r1
            L_0x05d7:
                r0 = r4
                r4 = r2
                r2 = r1
                r1 = r42
                r35 = r19
                r19 = r3
                r3 = r11
                r11 = r13
                r13 = r17
                r17 = r35
            L_0x05e6:
                r18 = r6
                io.ktor.utils.io.l r18 = (io.ktor.utils.io.l) r18     // Catch:{ IOException -> 0x060a, all -> 0x0602 }
                io.ktor.utils.io.m.a(r18)     // Catch:{ IOException -> 0x060a, all -> 0x0602 }
                r42 = r4
                r4 = r0
                r0 = r15
                r15 = r14
                r35 = r2
                r2 = r1
                r1 = r13
                r13 = r8
                r9 = r11
                r11 = r3
                r8 = r7
                r3 = r19
                r7 = r6
                r6 = r5
                r5 = r35
                goto L_0x06a5
            L_0x0602:
                r0 = move-exception
                r2 = r1
                r3 = r10
                r13 = r12
                r10 = r17
                goto L_0x0833
            L_0x060a:
                r0 = move-exception
                r2 = r1
                r3 = r10
                r13 = r12
                r10 = r17
                goto L_0x0826
            L_0x0612:
                r0 = move-exception
                r17 = r1
                r1 = r42
                r18 = r4
                r4 = r2
                r2 = r3
                r3 = r11
                r35 = r8
                r8 = r7
                r9 = r12
                r7 = r14
                r14 = r19
                r11 = r35
                goto L_0x063d
            L_0x0626:
                r0 = move-exception
                r19 = r2
                r2 = r17
                r17 = r1
                r1 = r42
                r18 = r4
                r4 = r2
                r2 = r3
                r3 = r11
                r35 = r8
                r8 = r7
                r9 = r12
                r7 = r14
                r14 = r19
                r11 = r35
            L_0x063d:
                r42 = r1
                r1 = r6
                io.ktor.utils.io.l r1 = (io.ktor.utils.io.l) r1     // Catch:{ all -> 0x0647 }
                r1.d(r0)     // Catch:{ all -> 0x0647 }
                throw r0     // Catch:{ all -> 0x0647 }
            L_0x0647:
                r0 = move-exception
                r1 = r6
                io.ktor.utils.io.l r1 = (io.ktor.utils.io.l) r1     // Catch:{ IOException -> 0x0658, all -> 0x064f }
                io.ktor.utils.io.m.a(r1)     // Catch:{ IOException -> 0x0658, all -> 0x064f }
                throw r0     // Catch:{ IOException -> 0x0658, all -> 0x064f }
            L_0x064f:
                r0 = move-exception
                r2 = r42
                r7 = r8
                r13 = r9
                r3 = r10
                r10 = r14
                goto L_0x0833
            L_0x0658:
                r0 = move-exception
                r2 = r42
                r7 = r8
                r13 = r9
                r3 = r10
                r10 = r14
                goto L_0x0826
            L_0x0661:
                r19 = r2
                r2 = r17
                r17 = r1
                r1 = r18
                goto L_0x0687
            L_0x066a:
                r0 = move-exception
                r19 = r2
                r2 = r42
                r3 = r10
                r13 = r12
                r10 = r19
                goto L_0x0833
            L_0x0675:
                r0 = move-exception
                r19 = r2
                r2 = r42
                r3 = r10
                r13 = r12
                r10 = r19
                goto L_0x0826
            L_0x0680:
                r19 = r2
                r2 = r17
                r17 = r1
                r1 = r0
            L_0x0687:
                r0 = r15
                r15 = r14
                r35 = r2
                r2 = r42
                r42 = r35
                r36 = r5
                r5 = r1
                r1 = r17
                r17 = r19
                r37 = r6
                r6 = r36
                r38 = r7
                r7 = r37
                r39 = r8
                r8 = r38
                r9 = r13
                r13 = r39
            L_0x06a5:
                boolean r18 = io.ktor.server.cio.backend.c.b(r0, r6)     // Catch:{ IOException -> 0x06c8, all -> 0x06c0 }
                if (r18 == 0) goto L_0x06b4
                r7 = r8
                r3 = r10
                r13 = r12
                r10 = r17
                r4 = 1
                r5 = 0
                goto L_0x0812
            L_0x06b4:
                r1 = r41
                r4 = r5
                r7 = r8
                r5 = r10
                r3 = r12
                r6 = r17
                r0 = r26
                goto L_0x02c3
            L_0x06c0:
                r0 = move-exception
                r7 = r8
                r3 = r10
                r13 = r12
                r10 = r17
                goto L_0x0833
            L_0x06c8:
                r0 = move-exception
                r7 = r8
                r3 = r10
                r13 = r12
                r10 = r17
                goto L_0x0826
            L_0x06d0:
                r0 = move-exception
                r4 = r32
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r4
                goto L_0x0833
            L_0x06db:
                r0 = move-exception
                r4 = r32
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r4
                goto L_0x0826
            L_0x06e6:
                r0 = move-exception
                r4 = r20
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r4
                goto L_0x0833
            L_0x06f1:
                r0 = move-exception
                r4 = r20
                r2 = r42
                r3 = r10
                r7 = r13
                r10 = r15
                r13 = r4
                goto L_0x0826
            L_0x06fc:
                r0 = move-exception
                r18 = r3
                r2 = r5
                r1 = r9
                r3 = r17
                r4 = r20
                r9 = r7
                r7 = r12
                r12 = r8
                r8 = r0
                r0 = r2
                r2 = r6
                r5 = r18
                goto L_0x072b
            L_0x070e:
                r0 = move-exception
                r1 = r9
                r3 = r17
                r4 = r20
                r9 = r7
                r7 = r12
                r12 = r8
                r8 = r0
                r0 = r5
                r2 = r6
                r5 = r18
                goto L_0x072b
            L_0x071d:
                r0 = move-exception
                r29 = r4
                r1 = r9
                r4 = r3
                r9 = r7
                r7 = r12
                r3 = r2
                r12 = r8
                r8 = r0
                r0 = r5
                r2 = r6
                r5 = r18
            L_0x072b:
                r11.release()     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                io.ktor.utils.io.core.q r17 = io.ktor.server.cio.backend.c.a     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r18 = r2
                io.ktor.utils.io.core.q r2 = r17.v1()     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$0 = r4     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$1 = r10     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$2 = r15     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$3 = r11     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$4 = r3     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$5 = r9     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$6 = r14     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$7 = r12     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.Z$0 = r7     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$8 = r0     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.J$0 = r5     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r13.L$9 = r8     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                r17 = r0
                r0 = 4
                r13.label = r0     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                java.lang.Object r0 = r3.x(r2, r13)     // Catch:{ IOException -> 0x055c, all -> 0x0553 }
                if (r0 != r1) goto L_0x075c
                return r1
            L_0x075c:
                r1 = r42
                r2 = r8
                r0 = r17
                r17 = r3
                r3 = r10
                r10 = r15
                r15 = r4
                r4 = r29
                r35 = r5
                r6 = r7
                r7 = r35
                r5 = r18
                r37 = r14
                r14 = r9
                r9 = r37
            L_0x0774:
                io.ktor.utils.io.m.a(r17)     // Catch:{ IOException -> 0x077f, all -> 0x0779 }
                throw r2     // Catch:{ IOException -> 0x077f, all -> 0x0779 }
            L_0x0779:
                r0 = move-exception
                r2 = r1
                r7 = r13
                r13 = r15
                goto L_0x0833
            L_0x077f:
                r0 = move-exception
                r2 = r1
                r7 = r13
                r13 = r15
                goto L_0x0826
            L_0x0785:
                r0 = move-exception
                r1 = r42
                r4 = r3
                r3 = r5
                r5 = r14
                r2 = r17
                r35 = r13
                r13 = r6
                r6 = r15
                r15 = r10
                r10 = r8
                r8 = r7
                r7 = r9
                r9 = r35
                goto L_0x07ac
            L_0x0798:
                r0 = move-exception
                r18 = r1
                r1 = r42
                r4 = r3
                r3 = r5
                r5 = r14
                r2 = r17
                r35 = r13
                r13 = r6
                r6 = r15
                r15 = r10
                r10 = r8
                r8 = r7
                r7 = r9
                r9 = r35
            L_0x07ac:
                r11.release()     // Catch:{ IOException -> 0x07b9, all -> 0x07b1 }
                throw r0     // Catch:{ IOException -> 0x07b9, all -> 0x07b1 }
            L_0x07b1:
                r0 = move-exception
                r2 = r1
                r3 = r4
                r7 = r8
                r13 = r9
                r10 = r15
                goto L_0x0833
            L_0x07b9:
                r0 = move-exception
                r2 = r1
                r3 = r4
                r7 = r8
                r13 = r9
                r10 = r15
                goto L_0x0826
            L_0x07c1:
                r0 = move-exception
                r42 = r2
                goto L_0x0833
            L_0x07c6:
                r0 = move-exception
                r42 = r2
                goto L_0x0826
            L_0x07cb:
                r42 = r2
                r4 = 1
                r5 = 0
                goto L_0x0812
            L_0x07d0:
                r0 = move-exception
                r42 = r2
                goto L_0x07e0
            L_0x07d4:
                r0 = move-exception
                r42 = r2
                goto L_0x081e
            L_0x07d8:
                r0 = move-exception
                r42 = r2
                goto L_0x0824
            L_0x07dc:
                r0 = move-exception
                r13 = r3
                r3 = r5
                r10 = r6
            L_0x07e0:
                r1 = 0
                r5 = 1
                r6 = 0
                io.ktor.utils.io.f r1 = io.ktor.utils.io.g.b(r1, r5, r6)     // Catch:{ IOException -> 0x0818 }
                boolean r5 = r3.offer(r1)     // Catch:{ IOException -> 0x0818 }
                if (r5 == 0) goto L_0x080c
                io.ktor.utils.io.core.q r5 = io.ktor.server.cio.backend.c.a     // Catch:{ IOException -> 0x0818 }
                io.ktor.utils.io.core.q r5 = r5.v1()     // Catch:{ IOException -> 0x0818 }
                r7.L$0 = r13     // Catch:{ IOException -> 0x0818 }
                r7.L$1 = r3     // Catch:{ IOException -> 0x0818 }
                r7.L$2 = r10     // Catch:{ IOException -> 0x0818 }
                r7.L$3 = r0     // Catch:{ IOException -> 0x0818 }
                r7.L$4 = r1     // Catch:{ IOException -> 0x0818 }
                r6 = 2
                r7.label = r6     // Catch:{ IOException -> 0x0818 }
                java.lang.Object r5 = r1.x(r5, r7)     // Catch:{ IOException -> 0x0818 }
                if (r5 != r4) goto L_0x0809
                return r4
            L_0x0809:
                io.ktor.utils.io.m.a(r1)     // Catch:{ IOException -> 0x0818 }
            L_0x080c:
                r4 = 1
                r5 = 0
                kotlinx.coroutines.channels.a0.a.a(r3, r5, r4, r5)     // Catch:{ IOException -> 0x0818 }
            L_0x0812:
                kotlinx.coroutines.channels.a0.a.a(r3, r5, r4, r5)
                goto L_0x0830
            L_0x0816:
                r0 = move-exception
                goto L_0x0833
            L_0x0818:
                r0 = move-exception
                goto L_0x0826
            L_0x081a:
                r0 = move-exception
                r13 = r3
                r3 = r5
                r10 = r6
            L_0x081e:
                throw r0     // Catch:{ IOException -> 0x0818 }
            L_0x0820:
                r0 = move-exception
                r13 = r3
                r3 = r5
                r10 = r6
            L_0x0824:
                throw r0     // Catch:{ IOException -> 0x0818 }
            L_0x0826:
                kotlin.coroutines.g r1 = r13.getCoroutineContext()     // Catch:{ all -> 0x0816 }
                r4 = 1
                r5 = 0
                kotlinx.coroutines.e2.d(r1, r5, r4, r5)     // Catch:{ all -> 0x0816 }
                goto L_0x0812
            L_0x0830:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0833:
                r1 = 1
                r4 = 0
                kotlinx.coroutines.channels.a0.a.a(r3, r4, r1, r4)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.backend.c.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "io.ktor.server.cio.backend.ServerPipelineKt$startServerConnectionPipeline$1$outputsActor$1", f = "ServerPipeline.kt", l = {49, 51}, m = "invokeSuspend")
        /* compiled from: ServerPipeline.kt */
        public static final class b extends l implements p<kotlinx.coroutines.channels.f<i>, d<? super x>, Object> {
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            private kotlinx.coroutines.channels.f p$;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(a aVar, d dVar) {
                super(2, dVar);
                this.this$0 = aVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                k.f(dVar, "completion");
                b bVar = new b(this.this$0, dVar);
                kotlinx.coroutines.channels.f fVar = (kotlinx.coroutines.channels.f) obj;
                bVar.p$ = (kotlinx.coroutines.channels.f) obj;
                return bVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((b) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            /* Debug info: failed to restart local var, previous not found, register: 9 */
            /* JADX INFO: finally extract failed */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: io.ktor.server.cio.backend.c$a$b$a} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: kotlin.jvm.functions.p} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: kotlinx.coroutines.channels.f} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: io.ktor.utils.io.i} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: kotlin.jvm.functions.p} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: kotlinx.coroutines.channels.f} */
            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x0069  */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
                /*
                    r9 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r9.label
                    r2 = 0
                    switch(r1) {
                        case 0: goto L_0x0046;
                        case 1: goto L_0x002e;
                        case 2: goto L_0x0012;
                        default: goto L_0x000a;
                    }
                L_0x000a:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0012:
                    r1 = r2
                    r3 = r2
                    java.lang.Object r4 = r9.L$2
                    r3 = r4
                    io.ktor.utils.io.i r3 = (io.ktor.utils.io.i) r3
                    java.lang.Object r4 = r9.L$1
                    r1 = r4
                    kotlin.jvm.functions.p r1 = (kotlin.jvm.functions.p) r1
                    java.lang.Object r4 = r9.L$0
                    r2 = r4
                    kotlinx.coroutines.channels.f r2 = (kotlinx.coroutines.channels.f) r2
                    kotlin.p.b(r10)     // Catch:{ all -> 0x0029 }
                    r4 = r9
                    goto L_0x0093
                L_0x0029:
                    r4 = move-exception
                    r5 = r4
                    r4 = r9
                    goto L_0x00ac
                L_0x002e:
                    r1 = r2
                    java.lang.Object r3 = r9.L$1
                    r1 = r3
                    kotlin.jvm.functions.p r1 = (kotlin.jvm.functions.p) r1
                    java.lang.Object r3 = r9.L$0
                    r2 = r3
                    kotlinx.coroutines.channels.f r2 = (kotlinx.coroutines.channels.f) r2
                    kotlin.p.b(r10)     // Catch:{ all -> 0x0042 }
                    r4 = r9
                    r3 = r2
                    r2 = r1
                    r1 = r0
                    r0 = r10
                    goto L_0x006f
                L_0x0042:
                    r0 = move-exception
                    r3 = r9
                    goto L_0x00d8
                L_0x0046:
                    kotlin.p.b(r10)
                    kotlinx.coroutines.channels.f r1 = r9.p$
                    io.ktor.server.cio.backend.c$a$b$a r3 = new io.ktor.server.cio.backend.c$a$b$a     // Catch:{ all -> 0x00d5 }
                    r3.<init>(r1, r2)     // Catch:{ all -> 0x00d5 }
                    r2 = r3
                    r3 = 0
                    r3 = r9
                L_0x0056:
                    io.ktor.server.cio.backend.c$a r4 = r3.this$0     // Catch:{ all -> 0x00d2 }
                    io.ktor.http.cio.internals.g r4 = r4.$timeout     // Catch:{ all -> 0x00d2 }
                    r3.L$0 = r1     // Catch:{ all -> 0x00d2 }
                    r3.L$1 = r2     // Catch:{ all -> 0x00d2 }
                    r5 = 1
                    r3.label = r5     // Catch:{ all -> 0x00d2 }
                    java.lang.Object r4 = r4.e(r2, r3)     // Catch:{ all -> 0x00d2 }
                    if (r4 != r0) goto L_0x0069
                    return r0
                L_0x0069:
                    r8 = r0
                    r0 = r10
                    r10 = r4
                    r4 = r3
                    r3 = r1
                    r1 = r8
                L_0x006f:
                    io.ktor.utils.io.i r10 = (io.ktor.utils.io.i) r10     // Catch:{ all -> 0x00cb }
                    if (r10 == 0) goto L_0x00bf
                    io.ktor.server.cio.backend.c$a r5 = r4.this$0     // Catch:{ all -> 0x00a5 }
                    io.ktor.server.cio.backend.b r5 = r5.$connection     // Catch:{ all -> 0x00a5 }
                    io.ktor.utils.io.l r5 = r5.c()     // Catch:{ all -> 0x00a5 }
                    r6 = 0
                    r4.L$0 = r3     // Catch:{ all -> 0x00a5 }
                    r4.L$1 = r2     // Catch:{ all -> 0x00a5 }
                    r4.L$2 = r10     // Catch:{ all -> 0x00a5 }
                    r7 = 2
                    r4.label = r7     // Catch:{ all -> 0x00a5 }
                    java.lang.Object r5 = io.ktor.utils.io.j.c(r10, r5, r6, r4)     // Catch:{ all -> 0x00a5 }
                    if (r5 != r1) goto L_0x008d
                    return r1
                L_0x008d:
                    r8 = r3
                    r3 = r10
                    r10 = r0
                    r0 = r1
                    r1 = r2
                    r2 = r8
                L_0x0093:
                    io.ktor.server.cio.backend.c$a r5 = r4.this$0     // Catch:{ all -> 0x00a3 }
                    io.ktor.server.cio.backend.b r5 = r5.$connection     // Catch:{ all -> 0x00a3 }
                    io.ktor.utils.io.l r5 = r5.c()     // Catch:{ all -> 0x00a3 }
                    r5.flush()     // Catch:{ all -> 0x00a3 }
                    r3 = r4
                    r8 = r2
                    r2 = r1
                    r1 = r8
                    goto L_0x00ba
                L_0x00a3:
                    r5 = move-exception
                    goto L_0x00ac
                L_0x00a5:
                    r5 = move-exception
                    r8 = r3
                    r3 = r10
                    r10 = r0
                    r0 = r1
                    r1 = r2
                    r2 = r8
                L_0x00ac:
                    boolean r6 = r3 instanceof io.ktor.utils.io.l     // Catch:{ all -> 0x00bc }
                    if (r6 == 0) goto L_0x00b6
                    r6 = r3
                    io.ktor.utils.io.l r6 = (io.ktor.utils.io.l) r6     // Catch:{ all -> 0x00bc }
                    r6.d(r5)     // Catch:{ all -> 0x00bc }
                L_0x00b6:
                    r3 = r4
                    r8 = r2
                    r2 = r1
                    r1 = r8
                L_0x00ba:
                    goto L_0x0056
                L_0x00bc:
                    r0 = move-exception
                    r3 = r4
                    goto L_0x00d8
                L_0x00bf:
                    io.ktor.server.cio.backend.c$a r10 = r4.this$0
                    io.ktor.server.cio.backend.b r10 = r10.$connection
                    io.ktor.utils.io.l r10 = r10.c()
                    io.ktor.utils.io.m.a(r10)
                    goto L_0x00f2
                L_0x00cb:
                    r10 = move-exception
                    r2 = r3
                    r3 = r4
                    r8 = r0
                    r0 = r10
                    r10 = r8
                    goto L_0x00d8
                L_0x00d2:
                    r0 = move-exception
                    r2 = r1
                    goto L_0x00d8
                L_0x00d5:
                    r0 = move-exception
                    r3 = r9
                    r2 = r1
                L_0x00d8:
                    io.ktor.server.cio.backend.c$a r1 = r3.this$0     // Catch:{ all -> 0x00f5 }
                    io.ktor.server.cio.backend.b r1 = r1.$connection     // Catch:{ all -> 0x00f5 }
                    io.ktor.utils.io.l r1 = r1.c()     // Catch:{ all -> 0x00f5 }
                    r1.d(r0)     // Catch:{ all -> 0x00f5 }
                    io.ktor.server.cio.backend.c$a r0 = r3.this$0
                    io.ktor.server.cio.backend.b r0 = r0.$connection
                    io.ktor.utils.io.l r0 = r0.c()
                    io.ktor.utils.io.m.a(r0)
                    r0 = r10
                    r4 = r3
                    r3 = r2
                L_0x00f2:
                    kotlin.x r10 = kotlin.x.a
                    return r10
                L_0x00f5:
                    r0 = move-exception
                    io.ktor.server.cio.backend.c$a r1 = r3.this$0
                    io.ktor.server.cio.backend.b r1 = r1.$connection
                    io.ktor.utils.io.l r1 = r1.c()
                    io.ktor.utils.io.m.a(r1)
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.backend.c.a.b.invokeSuspend(java.lang.Object):java.lang.Object");
            }

            @f(c = "io.ktor.server.cio.backend.ServerPipelineKt$startServerConnectionPipeline$1$outputsActor$1$receiveChildOrNull$1", f = "ServerPipeline.kt", l = {46}, m = "invokeSuspend")
            /* renamed from: io.ktor.server.cio.backend.c$a$b$a  reason: collision with other inner class name */
            /* compiled from: ServerPipeline.kt */
            public static final class C0265a extends l implements p<o0, d<? super i>, Object> {
                final /* synthetic */ kotlinx.coroutines.channels.f $this_actor;
                Object L$0;
                int label;
                private o0 p$;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0265a(kotlinx.coroutines.channels.f fVar, d dVar) {
                    super(2, dVar);
                    this.$this_actor = fVar;
                }

                @NotNull
                public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                    k.f(dVar, "completion");
                    C0265a aVar = new C0265a(this.$this_actor, dVar);
                    o0 o0Var = (o0) obj;
                    aVar.p$ = (o0) obj;
                    return aVar;
                }

                public final Object invoke(Object obj, Object obj2) {
                    return ((C0265a) create(obj, (d) obj2)).invokeSuspend(x.a);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    Object d = kotlin.coroutines.intrinsics.c.d();
                    switch (this.label) {
                        case 0:
                            kotlin.p.b($result);
                            o0 $this$suspendLambda = this.p$;
                            kotlinx.coroutines.channels.i channel = this.$this_actor.getChannel();
                            this.L$0 = $this$suspendLambda;
                            this.label = 1;
                            Object b = m.b(channel, this);
                            if (b == d) {
                                return d;
                            }
                            o0 o0Var = $this$suspendLambda;
                            return b;
                        case 1:
                            o0 $this$suspendLambda2 = (o0) this.L$0;
                            kotlin.p.b($result);
                            return $result;
                        default:
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }
            }
        }

        @f(c = "io.ktor.server.cio.backend.ServerPipelineKt$startServerConnectionPipeline$1$1", f = "ServerPipeline.kt", l = {149}, m = "invokeSuspend")
        /* renamed from: io.ktor.server.cio.backend.c$a$a  reason: collision with other inner class name */
        /* compiled from: ServerPipeline.kt */
        public static final class C0264a extends l implements p<o0, d<? super x>, Object> {
            final /* synthetic */ io.ktor.http.cio.m $request;
            final /* synthetic */ i $requestBody;
            final /* synthetic */ io.ktor.utils.io.f $response;
            final /* synthetic */ w $upgraded;
            Object L$0;
            Object L$1;
            int label;
            private o0 p$;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0264a(a aVar, i iVar, io.ktor.utils.io.f fVar, w wVar, io.ktor.http.cio.m mVar, d dVar) {
                super(2, dVar);
                this.this$0 = aVar;
                this.$requestBody = iVar;
                this.$response = fVar;
                this.$upgraded = wVar;
                this.$request = mVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                k.f(dVar, "completion");
                C0264a aVar = new C0264a(this.this$0, this.$requestBody, this.$response, this.$upgraded, this.$request, dVar);
                o0 o0Var = (o0) obj;
                aVar.p$ = (o0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0264a) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0060, code lost:
                io.ktor.utils.io.m.a(r12.$response);
                r3 = r12.$upgraded;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0067, code lost:
                if (r3 == null) goto L_0x0094;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:16:0x0069, code lost:
                kotlin.coroutines.jvm.internal.b.a(r3.x(kotlin.coroutines.jvm.internal.b.a(false)));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x0091, code lost:
                if (r3 == null) goto L_0x0094;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:0x0096, code lost:
                return kotlin.x.a;
             */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) {
                /*
                    r12 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r12.label
                    r2 = 0
                    switch(r1) {
                        case 0: goto L_0x0024;
                        case 1: goto L_0x0012;
                        default: goto L_0x000a;
                    }
                L_0x000a:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0012:
                    r0 = 0
                    r1 = r0
                    java.lang.Object r3 = r12.L$1
                    r1 = r3
                    io.ktor.server.cio.backend.d r1 = (io.ktor.server.cio.backend.d) r1
                    java.lang.Object r3 = r12.L$0
                    r0 = r3
                    kotlinx.coroutines.o0 r0 = (kotlinx.coroutines.o0) r0
                    kotlin.p.b(r13)     // Catch:{ all -> 0x0022 }
                    goto L_0x0060
                L_0x0022:
                    r3 = move-exception
                    goto L_0x007a
                L_0x0024:
                    kotlin.p.b(r13)
                    kotlinx.coroutines.o0 r1 = r12.p$
                    io.ktor.server.cio.backend.d r10 = new io.ktor.server.cio.backend.d
                    kotlin.coroutines.g r4 = r1.getCoroutineContext()
                    io.ktor.utils.io.i r5 = r12.$requestBody
                    io.ktor.utils.io.f r6 = r12.$response
                    io.ktor.server.cio.backend.c$a r3 = r12.this$0
                    io.ktor.server.cio.backend.b r3 = r3.$connection
                    java.net.SocketAddress r7 = r3.d()
                    io.ktor.server.cio.backend.c$a r3 = r12.this$0
                    io.ktor.server.cio.backend.b r3 = r3.$connection
                    java.net.SocketAddress r8 = r3.b()
                    kotlinx.coroutines.w r9 = r12.$upgraded
                    r3 = r10
                    r3.<init>(r4, r5, r6, r7, r8, r9)
                    io.ktor.server.cio.backend.c$a r4 = r12.this$0     // Catch:{ all -> 0x0075 }
                    kotlin.jvm.functions.q r4 = r4.$handler     // Catch:{ all -> 0x0075 }
                    io.ktor.http.cio.m r5 = r12.$request     // Catch:{ all -> 0x0075 }
                    r12.L$0 = r1     // Catch:{ all -> 0x0075 }
                    r12.L$1 = r3     // Catch:{ all -> 0x0075 }
                    r6 = 1
                    r12.label = r6     // Catch:{ all -> 0x0075 }
                    java.lang.Object r4 = r4.invoke(r3, r5, r12)     // Catch:{ all -> 0x0075 }
                    if (r4 != r0) goto L_0x005e
                    return r0
                L_0x005e:
                    r0 = r1
                    r1 = r3
                L_0x0060:
                    io.ktor.utils.io.f r3 = r12.$response
                    io.ktor.utils.io.m.a(r3)
                    kotlinx.coroutines.w r3 = r12.$upgraded
                    if (r3 == 0) goto L_0x0074
                L_0x0069:
                    java.lang.Boolean r2 = kotlin.coroutines.jvm.internal.b.a(r2)
                    boolean r2 = r3.x(r2)
                    kotlin.coroutines.jvm.internal.b.a(r2)
                L_0x0074:
                    goto L_0x0094
                L_0x0075:
                    r0 = move-exception
                    r11 = r3
                    r3 = r0
                    r0 = r1
                    r1 = r11
                L_0x007a:
                    io.ktor.utils.io.f r4 = r12.$response     // Catch:{ all -> 0x0097 }
                    r4.d(r3)     // Catch:{ all -> 0x0097 }
                    kotlinx.coroutines.w r4 = r12.$upgraded     // Catch:{ all -> 0x0097 }
                    if (r4 == 0) goto L_0x008a
                    boolean r4 = r4.a(r3)     // Catch:{ all -> 0x0097 }
                    kotlin.coroutines.jvm.internal.b.a(r4)     // Catch:{ all -> 0x0097 }
                L_0x008a:
                    io.ktor.utils.io.f r3 = r12.$response
                    io.ktor.utils.io.m.a(r3)
                    kotlinx.coroutines.w r3 = r12.$upgraded
                    if (r3 == 0) goto L_0x0074
                    goto L_0x0069
                L_0x0094:
                    kotlin.x r1 = kotlin.x.a
                    return r1
                L_0x0097:
                    r3 = move-exception
                    io.ktor.utils.io.f r4 = r12.$response
                    io.ktor.utils.io.m.a(r4)
                    kotlinx.coroutines.w r4 = r12.$upgraded
                    if (r4 == 0) goto L_0x00ac
                    java.lang.Boolean r2 = kotlin.coroutines.jvm.internal.b.a(r2)
                    boolean r2 = r4.x(r2)
                    kotlin.coroutines.jvm.internal.b.a(r2)
                L_0x00ac:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.backend.c.a.C0264a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    @NotNull
    public static final z1 c(@NotNull o0 $this$startServerConnectionPipeline, @NotNull b connection, @NotNull g timeout, @NotNull kotlin.jvm.functions.q<? super d, ? super io.ktor.http.cio.m, ? super d<? super x>, ? extends Object> handler) {
        k.f($this$startServerConnectionPipeline, "$this$startServerConnectionPipeline");
        k.f(connection, "connection");
        k.f(timeout, "timeout");
        k.f(handler, "handler");
        return j.d($this$startServerConnectionPipeline, io.ktor.http.cio.l.a(), (q0) null, new a(timeout, connection, handler, (d) null), 2, (Object) null);
    }

    static {
        n nVar = new n();
        n $this$apply = nVar;
        $this$apply.e(Constants.HTTP_10, v.c0.c().b0(), "Bad Request");
        $this$apply.c("Connection", "close");
        $this$apply.b();
        a = nVar.a();
    }

    public static final boolean b(boolean http11, @Nullable io.ktor.http.cio.d connectionOptions) {
        if (connectionOptions == null) {
            return !http11;
        }
        if (connectionOptions.e()) {
            return false;
        }
        if (connectionOptions.d()) {
            return true;
        }
        return false;
    }
}

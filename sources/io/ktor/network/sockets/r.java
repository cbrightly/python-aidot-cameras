package io.ktor.network.sockets;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaSocketOptions.kt */
public final class r {
    private static final Map<String, Field> a;
    private static final Method b;
    private static final Method c;
    private static final Method d;
    public static final r e = new r();

    /* JADX WARNING: Removed duplicated region for block: B:114:0x0256 A[LOOP:4: B:89:0x01ec->B:114:0x0256, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01d1 A[EDGE_INSN: B:126:0x01d1->B:86:0x01d1 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0254 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01c5 A[LOOP:3: B:60:0x015d->B:82:0x01c5, LOOP_END] */
    static {
        /*
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            java.lang.String r2 = "setOption"
            java.lang.String r3 = "method"
            java.lang.String r4 = "socketChannelClass.methods"
            java.lang.String r5 = "socketChannelClass"
            java.lang.String r6 = "java.net.SocketOption"
            io.ktor.network.sockets.r r0 = new io.ktor.network.sockets.r
            r0.<init>()
            e = r0
            java.lang.String r0 = "java.net.StandardSocketOptions"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ all -> 0x00b6 }
            java.lang.reflect.Field[] r0 = r0.getFields()     // Catch:{ all -> 0x00b6 }
            if (r0 == 0) goto L_0x00b1
            r9 = 0
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ all -> 0x00b6 }
            r10.<init>()     // Catch:{ all -> 0x00b6 }
            r11 = r0
            r12 = 0
            int r13 = r11.length     // Catch:{ all -> 0x00b6 }
            r14 = 0
        L_0x0031:
            java.lang.String r15 = "it"
            if (r14 >= r13) goto L_0x006c
            r16 = r11[r14]     // Catch:{ all -> 0x00b6 }
            r17 = r16
            r18 = 0
            r7 = r16
            kotlin.jvm.internal.k.b(r7, r15)     // Catch:{ all -> 0x00b6 }
            int r15 = r7.getModifiers()     // Catch:{ all -> 0x00b6 }
            r16 = 0
            boolean r20 = java.lang.reflect.Modifier.isStatic(r15)     // Catch:{ all -> 0x00b6 }
            if (r20 == 0) goto L_0x005d
            boolean r20 = java.lang.reflect.Modifier.isFinal(r15)     // Catch:{ all -> 0x00b6 }
            if (r20 == 0) goto L_0x005d
            boolean r20 = java.lang.reflect.Modifier.isPublic(r15)     // Catch:{ all -> 0x00b6 }
            if (r20 == 0) goto L_0x005d
            r15 = 1
            goto L_0x005e
        L_0x005d:
            r15 = 0
        L_0x005e:
            if (r15 == 0) goto L_0x0067
            r7 = r17
            r10.add(r7)     // Catch:{ all -> 0x00b6 }
            goto L_0x0069
        L_0x0067:
            r7 = r17
        L_0x0069:
            int r14 = r14 + 1
            goto L_0x0031
        L_0x006c:
            r0 = r10
            r7 = 0
            r9 = 10
            int r9 = kotlin.collections.r.r(r0, r9)     // Catch:{ all -> 0x00b6 }
            int r9 = kotlin.collections.k0.b(r9)     // Catch:{ all -> 0x00b6 }
            r10 = 16
            int r9 = kotlin.ranges.n.b(r9, r10)     // Catch:{ all -> 0x00b6 }
            java.util.LinkedHashMap r10 = new java.util.LinkedHashMap     // Catch:{ all -> 0x00b6 }
            r10.<init>(r9)     // Catch:{ all -> 0x00b6 }
            r11 = r0
            r12 = 0
            java.util.Iterator r13 = r11.iterator()     // Catch:{ all -> 0x00b6 }
        L_0x008b:
            boolean r14 = r13.hasNext()     // Catch:{ all -> 0x00b6 }
            if (r14 == 0) goto L_0x00ae
            java.lang.Object r14 = r13.next()     // Catch:{ all -> 0x00b6 }
            r16 = r14
            java.lang.reflect.Field r16 = (java.lang.reflect.Field) r16     // Catch:{ all -> 0x00b6 }
            r17 = r16
            r16 = 0
            r8 = r17
            kotlin.jvm.internal.k.b(r8, r15)     // Catch:{ all -> 0x00b6 }
            r17 = r0
            java.lang.String r0 = r8.getName()     // Catch:{ all -> 0x00b6 }
            r10.put(r0, r14)     // Catch:{ all -> 0x00b6 }
            r0 = r17
            goto L_0x008b
        L_0x00ae:
            r17 = r0
            goto L_0x00b5
        L_0x00b1:
            java.util.Map r10 = kotlin.collections.l0.f()     // Catch:{ all -> 0x00b6 }
        L_0x00b5:
            goto L_0x00bb
        L_0x00b6:
            r0 = move-exception
            java.util.Map r10 = kotlin.collections.l0.f()
        L_0x00bb:
            a = r10
            r7 = 2
            java.lang.Class r0 = java.lang.Class.forName(r6)     // Catch:{ all -> 0x0141 }
            java.lang.String r9 = "java.nio.channels.SocketChannel"
            java.lang.Class r9 = java.lang.Class.forName(r9)     // Catch:{ all -> 0x0141 }
            kotlin.jvm.internal.k.b(r9, r5)     // Catch:{ all -> 0x0141 }
            java.lang.reflect.Method[] r10 = r9.getMethods()     // Catch:{ all -> 0x0141 }
            kotlin.jvm.internal.k.b(r10, r4)     // Catch:{ all -> 0x0141 }
            r11 = 0
            int r12 = r10.length     // Catch:{ all -> 0x0141 }
            r13 = 0
        L_0x00d6:
            if (r13 >= r12) goto L_0x013f
            r14 = r10[r13]     // Catch:{ all -> 0x0141 }
            r15 = r14
            r16 = 0
            kotlin.jvm.internal.k.b(r15, r3)     // Catch:{ all -> 0x0141 }
            int r17 = r15.getModifiers()     // Catch:{ all -> 0x0141 }
            r20 = 0
            boolean r21 = java.lang.reflect.Modifier.isPublic(r17)     // Catch:{ all -> 0x0141 }
            if (r21 == 0) goto L_0x00fb
            boolean r21 = java.lang.reflect.Modifier.isStatic(r17)     // Catch:{ all -> 0x0141 }
            if (r21 != 0) goto L_0x00fb
            r17 = 1
            goto L_0x00fd
        L_0x00fb:
            r17 = 0
        L_0x00fd:
            if (r17 == 0) goto L_0x0138
            java.lang.String r8 = r15.getName()     // Catch:{ all -> 0x0141 }
            boolean r8 = kotlin.jvm.internal.k.a(r8, r2)     // Catch:{ all -> 0x0141 }
            if (r8 == 0) goto L_0x0138
            java.lang.Class[] r8 = r15.getParameterTypes()     // Catch:{ all -> 0x0141 }
            int r8 = r8.length     // Catch:{ all -> 0x0141 }
            if (r8 != r7) goto L_0x0138
            java.lang.Class r8 = r15.getReturnType()     // Catch:{ all -> 0x0141 }
            boolean r8 = kotlin.jvm.internal.k.a(r8, r9)     // Catch:{ all -> 0x0141 }
            if (r8 == 0) goto L_0x0138
            java.lang.Class[] r8 = r15.getParameterTypes()     // Catch:{ all -> 0x0141 }
            r18 = 0
            r8 = r8[r18]     // Catch:{ all -> 0x0141 }
            boolean r8 = kotlin.jvm.internal.k.a(r8, r0)     // Catch:{ all -> 0x0141 }
            if (r8 == 0) goto L_0x0138
            java.lang.Class[] r8 = r15.getParameterTypes()     // Catch:{ all -> 0x0141 }
            r19 = 1
            r8 = r8[r19]     // Catch:{ all -> 0x0141 }
            boolean r8 = kotlin.jvm.internal.k.a(r8, r1)     // Catch:{ all -> 0x0141 }
            if (r8 == 0) goto L_0x0138
            r8 = 1
            goto L_0x0139
        L_0x0138:
            r8 = 0
        L_0x0139:
            if (r8 == 0) goto L_0x013c
            goto L_0x0140
        L_0x013c:
            int r13 = r13 + 1
            goto L_0x00d6
        L_0x013f:
            r14 = 0
        L_0x0140:
            goto L_0x0143
        L_0x0141:
            r0 = move-exception
            r14 = 0
        L_0x0143:
            b = r14
            java.lang.Class r0 = java.lang.Class.forName(r6)     // Catch:{ all -> 0x01cf }
            java.lang.String r8 = "java.nio.channels.ServerSocketChannel"
            java.lang.Class r8 = java.lang.Class.forName(r8)     // Catch:{ all -> 0x01cf }
            kotlin.jvm.internal.k.b(r8, r5)     // Catch:{ all -> 0x01cf }
            java.lang.reflect.Method[] r9 = r8.getMethods()     // Catch:{ all -> 0x01cf }
            kotlin.jvm.internal.k.b(r9, r4)     // Catch:{ all -> 0x01cf }
            r10 = 0
            int r11 = r9.length     // Catch:{ all -> 0x01cf }
            r12 = 0
        L_0x015d:
            if (r12 >= r11) goto L_0x01cb
            r13 = r9[r12]     // Catch:{ all -> 0x01cf }
            r14 = r13
            r15 = 0
            kotlin.jvm.internal.k.b(r14, r3)     // Catch:{ all -> 0x01cf }
            int r16 = r14.getModifiers()     // Catch:{ all -> 0x01cf }
            r20 = 0
            boolean r21 = java.lang.reflect.Modifier.isPublic(r16)     // Catch:{ all -> 0x01cf }
            if (r21 == 0) goto L_0x0181
            boolean r21 = java.lang.reflect.Modifier.isStatic(r16)     // Catch:{ all -> 0x01cf }
            if (r21 != 0) goto L_0x0181
            r16 = 1
            goto L_0x0183
        L_0x0181:
            r16 = 0
        L_0x0183:
            if (r16 == 0) goto L_0x01bf
            java.lang.String r7 = r14.getName()     // Catch:{ all -> 0x01cf }
            boolean r7 = kotlin.jvm.internal.k.a(r7, r2)     // Catch:{ all -> 0x01cf }
            if (r7 == 0) goto L_0x01bf
            java.lang.Class[] r7 = r14.getParameterTypes()     // Catch:{ all -> 0x01cf }
            int r7 = r7.length     // Catch:{ all -> 0x01cf }
            r20 = r9
            r9 = 2
            if (r7 != r9) goto L_0x01c1
            java.lang.Class r7 = r14.getReturnType()     // Catch:{ all -> 0x01cf }
            boolean r7 = kotlin.jvm.internal.k.a(r7, r8)     // Catch:{ all -> 0x01cf }
            if (r7 == 0) goto L_0x01c1
            java.lang.Class[] r7 = r14.getParameterTypes()     // Catch:{ all -> 0x01cf }
            r9 = 0
            r7 = r7[r9]     // Catch:{ all -> 0x01cf }
            boolean r7 = kotlin.jvm.internal.k.a(r7, r0)     // Catch:{ all -> 0x01cf }
            if (r7 == 0) goto L_0x01c1
            java.lang.Class[] r7 = r14.getParameterTypes()     // Catch:{ all -> 0x01cf }
            r9 = 1
            r7 = r7[r9]     // Catch:{ all -> 0x01cf }
            boolean r7 = kotlin.jvm.internal.k.a(r7, r1)     // Catch:{ all -> 0x01cf }
            if (r7 == 0) goto L_0x01c1
            r7 = 1
            goto L_0x01c2
        L_0x01bf:
            r20 = r9
        L_0x01c1:
            r7 = 0
        L_0x01c2:
            if (r7 == 0) goto L_0x01c5
            goto L_0x01ce
        L_0x01c5:
            int r12 = r12 + 1
            r9 = r20
            r7 = 2
            goto L_0x015d
        L_0x01cb:
            r20 = r9
            r13 = 0
        L_0x01ce:
            goto L_0x01d1
        L_0x01cf:
            r0 = move-exception
            r13 = 0
        L_0x01d1:
            c = r13
            java.lang.Class r0 = java.lang.Class.forName(r6)     // Catch:{ all -> 0x025b }
            java.lang.String r6 = "java.nio.channels.DatagramChannel"
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch:{ all -> 0x025b }
            kotlin.jvm.internal.k.b(r6, r5)     // Catch:{ all -> 0x025b }
            java.lang.reflect.Method[] r5 = r6.getMethods()     // Catch:{ all -> 0x025b }
            kotlin.jvm.internal.k.b(r5, r4)     // Catch:{ all -> 0x025b }
            r4 = r5
            r5 = 0
            int r7 = r4.length     // Catch:{ all -> 0x025b }
            r8 = 0
        L_0x01ec:
            if (r8 >= r7) goto L_0x0259
            r9 = r4[r8]     // Catch:{ all -> 0x025b }
            r10 = r9
            r11 = 0
            kotlin.jvm.internal.k.b(r10, r3)     // Catch:{ all -> 0x025b }
            int r12 = r10.getModifiers()     // Catch:{ all -> 0x025b }
            r13 = 0
            boolean r14 = java.lang.reflect.Modifier.isPublic(r12)     // Catch:{ all -> 0x025b }
            if (r14 == 0) goto L_0x020e
            boolean r14 = java.lang.reflect.Modifier.isStatic(r12)     // Catch:{ all -> 0x025b }
            if (r14 != 0) goto L_0x020e
            r12 = 1
            goto L_0x020f
        L_0x020e:
            r12 = 0
        L_0x020f:
            if (r12 == 0) goto L_0x024c
            java.lang.String r12 = r10.getName()     // Catch:{ all -> 0x025b }
            boolean r12 = kotlin.jvm.internal.k.a(r12, r2)     // Catch:{ all -> 0x025b }
            if (r12 == 0) goto L_0x024c
            java.lang.Class[] r12 = r10.getParameterTypes()     // Catch:{ all -> 0x025b }
            int r12 = r12.length     // Catch:{ all -> 0x025b }
            r13 = 2
            if (r12 != r13) goto L_0x024d
            java.lang.Class r12 = r10.getReturnType()     // Catch:{ all -> 0x025b }
            boolean r12 = kotlin.jvm.internal.k.a(r12, r6)     // Catch:{ all -> 0x025b }
            if (r12 == 0) goto L_0x024d
            java.lang.Class[] r12 = r10.getParameterTypes()     // Catch:{ all -> 0x025b }
            r18 = 0
            r12 = r12[r18]     // Catch:{ all -> 0x025b }
            boolean r12 = kotlin.jvm.internal.k.a(r12, r0)     // Catch:{ all -> 0x025b }
            if (r12 == 0) goto L_0x024a
            java.lang.Class[] r12 = r10.getParameterTypes()     // Catch:{ all -> 0x025b }
            r14 = 1
            r12 = r12[r14]     // Catch:{ all -> 0x025b }
            boolean r12 = kotlin.jvm.internal.k.a(r12, r1)     // Catch:{ all -> 0x025b }
            if (r12 == 0) goto L_0x0250
            r10 = r14
            goto L_0x0252
        L_0x024a:
            r14 = 1
            goto L_0x0250
        L_0x024c:
            r13 = 2
        L_0x024d:
            r14 = 1
            r18 = 0
        L_0x0250:
            r10 = r18
        L_0x0252:
            if (r10 == 0) goto L_0x0256
            r8 = r9
            goto L_0x025a
        L_0x0256:
            int r8 = r8 + 1
            goto L_0x01ec
        L_0x0259:
            r8 = 0
        L_0x025a:
            goto L_0x025d
        L_0x025b:
            r0 = move-exception
            r8 = 0
        L_0x025d:
            d = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.r.<clinit>():void");
    }

    private r() {
    }

    public final void c(@NotNull SocketChannel channel) {
        k.f(channel, "channel");
        Object option = d("SO_REUSEPORT");
        Method method = b;
        if (method == null) {
            k.n();
        }
        method.invoke(channel, new Object[]{option, true});
    }

    public final void b(@NotNull ServerSocketChannel channel) {
        k.f(channel, "channel");
        Object option = d("SO_REUSEPORT");
        Method method = c;
        if (method == null) {
            k.n();
        }
        method.invoke(channel, new Object[]{option, true});
    }

    public final void a(@NotNull DatagramChannel channel) {
        k.f(channel, "channel");
        Object option = d("SO_REUSEPORT");
        Method method = d;
        if (method == null) {
            k.n();
        }
        method.invoke(channel, new Object[]{option, true});
    }

    private final Object d(String name) {
        Object obj;
        Field field = a.get(name);
        if (field != null && (obj = field.get((Object) null)) != null) {
            return obj;
        }
        throw new IOException("Socket option " + name + " is not supported");
    }
}

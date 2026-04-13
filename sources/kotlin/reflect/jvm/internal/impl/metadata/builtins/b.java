package kotlin.reflect.jvm.internal.impl.metadata.builtins;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.b;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.g;
import kotlin.reflect.jvm.internal.impl.metadata.l;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.protobuf.w;

/* compiled from: BuiltInsProtoBuf */
public final class b {
    public static final h.f<l, Integer> a = h.newSingularGeneratedExtension(l.getDefaultInstance(), 0, (o) null, (i.b<?>) null, Opcodes.DCMPL, w.b.INT32, Integer.class);
    public static final h.f<c, List<kotlin.reflect.jvm.internal.impl.metadata.b>> b;
    public static final h.f<d, List<kotlin.reflect.jvm.internal.impl.metadata.b>> c;
    public static final h.f<kotlin.reflect.jvm.internal.impl.metadata.i, List<kotlin.reflect.jvm.internal.impl.metadata.b>> d;
    public static final h.f<n, List<kotlin.reflect.jvm.internal.impl.metadata.b>> e;
    public static final h.f<n, List<kotlin.reflect.jvm.internal.impl.metadata.b>> f;
    public static final h.f<n, List<kotlin.reflect.jvm.internal.impl.metadata.b>> g;
    public static final h.f<n, b.C0379b.c> h;
    public static final h.f<g, List<kotlin.reflect.jvm.internal.impl.metadata.b>> i;
    public static final h.f<u, List<kotlin.reflect.jvm.internal.impl.metadata.b>> j;
    public static final h.f<q, List<kotlin.reflect.jvm.internal.impl.metadata.b>> k;
    public static final h.f<s, List<kotlin.reflect.jvm.internal.impl.metadata.b>> l;

    public static void a(f registry) {
        registry.a(a);
        registry.a(b);
        registry.a(c);
        registry.a(d);
        registry.a(e);
        registry.a(f);
        registry.a(g);
        registry.a(h);
        registry.a(i);
        registry.a(j);
        registry.a(k);
        registry.a(l);
    }

    static {
        c defaultInstance = c.getDefaultInstance();
        kotlin.reflect.jvm.internal.impl.metadata.b defaultInstance2 = kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance();
        w.b bVar = w.b.MESSAGE;
        b = h.newRepeatedGeneratedExtension(defaultInstance, defaultInstance2, (i.b<?>) null, 150, bVar, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        w.b bVar2 = bVar;
        c = h.newRepeatedGeneratedExtension(d.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar2, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        d = h.newRepeatedGeneratedExtension(kotlin.reflect.jvm.internal.impl.metadata.i.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar2, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        e = h.newRepeatedGeneratedExtension(n.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar2, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        f = h.newRepeatedGeneratedExtension(n.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 152, bVar2, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        g = h.newRepeatedGeneratedExtension(n.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, Opcodes.IFEQ, bVar2, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        h = h.newSingularGeneratedExtension(n.getDefaultInstance(), b.C0379b.c.getDefaultInstance(), b.C0379b.c.getDefaultInstance(), (i.b<?>) null, Opcodes.DCMPL, bVar, b.C0379b.c.class);
        w.b bVar3 = bVar;
        i = h.newRepeatedGeneratedExtension(g.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar3, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        j = h.newRepeatedGeneratedExtension(u.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar3, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        k = h.newRepeatedGeneratedExtension(q.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar3, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        l = h.newRepeatedGeneratedExtension(s.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 150, bVar3, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
    }
}

package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.k0;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.m;
import kotlin.reflect.jvm.internal.impl.name.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProtoBasedClassDataFinder.kt */
public final class z implements i {
    private final Map<a, c> a;
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.c b;
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.a c;
    private final l<a, o0> d;

    public z(@NotNull m proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a metadataVersion, @NotNull l<? super a, ? extends o0> classSource) {
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar = nameResolver;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.a aVar = metadataVersion;
        l<? super a, ? extends o0> lVar = classSource;
        k.f(proto, "proto");
        k.f(cVar, "nameResolver");
        k.f(aVar, "metadataVersion");
        k.f(lVar, "classSource");
        this.b = cVar;
        this.c = aVar;
        this.d = lVar;
        Iterable $this$associateByTo$iv$iv = proto.getClass_List();
        k.b($this$associateByTo$iv$iv, "proto.class_List");
        Map destination$iv$iv = new LinkedHashMap(n.b(k0.b(r.r($this$associateByTo$iv$iv, 10)), 16));
        for (T next : $this$associateByTo$iv$iv) {
            c klass = (c) next;
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar2 = this.b;
            k.b(klass, "klass");
            destination$iv$iv.put(y.a(cVar2, klass.getFqName()), next);
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar3 = nameResolver;
        }
        this.a = destination$iv$iv;
    }

    @NotNull
    public final Collection<a> b() {
        return this.a.keySet();
    }

    @Nullable
    public h a(@NotNull a classId) {
        k.f(classId, "classId");
        c classProto = this.a.get(classId);
        if (classProto != null) {
            return new h(this.b, classProto, this.c, this.d.invoke(classId));
        }
        return null;
    }
}

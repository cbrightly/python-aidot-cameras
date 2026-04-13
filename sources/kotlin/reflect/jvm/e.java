package kotlin.reflect.jvm;

import kotlin.c;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.n;
import kotlin.reflect.f;
import kotlin.reflect.jvm.internal.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.g;
import kotlin.reflect.jvm.internal.impl.metadata.t;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: reflectLambda.kt */
public final class e {
    @Nullable
    public static final <R> f<R> a(@NotNull c<? extends R> $this$reflect) {
        k.f($this$reflect, "$this$reflect");
        l annotation = (l) $this$reflect.getClass().getAnnotation(l.class);
        if (annotation == null) {
            return null;
        }
        String[] data = annotation.d1();
        boolean z = true;
        if ((data.length == 0 ? 1 : null) != null) {
            data = null;
        }
        if (data == null) {
            return null;
        }
        n<g, i> j = kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i.j(data, annotation.d2());
        g nameResolver = j.component1();
        i proto = j.component2();
        int[] mv = annotation.mv();
        if ((annotation.xi() & 8) == 0) {
            z = false;
        }
        kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f metadataVersion = new kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f(mv, z);
        Class<?> cls = $this$reflect.getClass();
        t typeTable = proto.getTypeTable();
        k.b(typeTable, "proto.typeTable");
        n0 descriptor = (n0) h0.e(cls, proto, nameResolver, new h(typeTable), metadataVersion, a.INSTANCE);
        if (descriptor == null) {
            return null;
        }
        return new kotlin.reflect.jvm.internal.k(kotlin.reflect.jvm.internal.a.q, descriptor);
    }

    /* compiled from: reflectLambda.kt */
    public static final /* synthetic */ class a extends kotlin.jvm.internal.h implements p<x, i, n0> {
        public static final a INSTANCE = new a();

        a() {
            super(2);
        }

        public final String getName() {
            return "loadFunction";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(x.class);
        }

        public final String getSignature() {
            return "loadFunction(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;)Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;";
        }

        @NotNull
        public final n0 invoke(@NotNull x p1, @NotNull i p2) {
            k.f(p1, "p1");
            k.f(p2, "p2");
            return p1.n(p2);
        }
    }
}

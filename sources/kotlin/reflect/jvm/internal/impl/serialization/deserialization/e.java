package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.metadata.b;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.f;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.serialization.a;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.a0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationAndConstantLoaderImpl.kt */
public final class e implements c<c, g<?>> {
    private final g a;
    private final a b;

    public e(@NotNull y module, @NotNull a0 notFoundClasses, @NotNull a protocol) {
        k.f(module, "module");
        k.f(notFoundClasses, "notFoundClasses");
        k.f(protocol, "protocol");
        this.b = protocol;
        this.a = new g(module, notFoundClasses);
    }

    @NotNull
    public List<c> b(@NotNull a0.a container) {
        k.f(container, "container");
        List annotations = (List) container.f().getExtension(this.b.a());
        if (annotations == null) {
            annotations = q.g();
        }
        List<b> $this$map$iv = annotations;
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (b proto : $this$map$iv) {
            arrayList.add(this.a.a(proto, container.b()));
        }
        return arrayList;
    }

    @NotNull
    public List<c> e(@NotNull a0 container, @NotNull o proto, @NotNull b kind) {
        Iterable annotations;
        k.f(container, "container");
        k.f(proto, "proto");
        k.f(kind, "kind");
        if (proto instanceof d) {
            annotations = (List) ((d) proto).getExtension(this.b.c());
        } else if (proto instanceof i) {
            annotations = (List) ((i) proto).getExtension(this.b.f());
        } else if (proto instanceof n) {
            switch (d.a[kind.ordinal()]) {
                case 1:
                    annotations = (List) ((n) proto).getExtension(this.b.h());
                    break;
                case 2:
                    annotations = (List) ((n) proto).getExtension(this.b.i());
                    break;
                case 3:
                    annotations = (List) ((n) proto).getExtension(this.b.j());
                    break;
                default:
                    throw new IllegalStateException("Unsupported callable kind with property proto".toString());
            }
        } else {
            throw new IllegalStateException(("Unknown message: " + proto).toString());
        }
        if (annotations == null) {
            annotations = q.g();
        }
        Iterable<b> $this$map$iv = annotations;
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (b annotationProto : $this$map$iv) {
            arrayList.add(this.a.a(annotationProto, container.b()));
        }
        return arrayList;
    }

    @NotNull
    public List<c> h(@NotNull a0 container, @NotNull n proto) {
        k.f(container, "container");
        k.f(proto, "proto");
        return q.g();
    }

    @NotNull
    public List<c> j(@NotNull a0 container, @NotNull n proto) {
        k.f(container, "container");
        k.f(proto, "proto");
        return q.g();
    }

    @NotNull
    public List<c> d(@NotNull a0 container, @NotNull kotlin.reflect.jvm.internal.impl.metadata.g proto) {
        k.f(container, "container");
        k.f(proto, "proto");
        List annotations = (List) proto.getExtension(this.b.d());
        if (annotations == null) {
            annotations = q.g();
        }
        List<b> $this$map$iv = annotations;
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (b annotationProto : $this$map$iv) {
            arrayList.add(this.a.a(annotationProto, container.b()));
        }
        return arrayList;
    }

    @NotNull
    public List<c> a(@NotNull a0 container, @NotNull o callableProto, @NotNull b kind, int parameterIndex, @NotNull u proto) {
        e eVar = this;
        u uVar = proto;
        k.f(container, "container");
        k.f(callableProto, "callableProto");
        k.f(kind, "kind");
        k.f(uVar, "proto");
        List annotations = (List) uVar.getExtension(eVar.b.g());
        if (annotations == null) {
            annotations = q.g();
        }
        List<b> $this$map$iv = annotations;
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (b annotationProto : $this$map$iv) {
            arrayList.add(eVar.a.a(annotationProto, container.b()));
            eVar = this;
        }
        return arrayList;
    }

    @NotNull
    public List<c> i(@NotNull a0 container, @NotNull o proto, @NotNull b kind) {
        k.f(container, "container");
        k.f(proto, "proto");
        k.f(kind, "kind");
        return q.g();
    }

    @NotNull
    public List<c> c(@NotNull kotlin.reflect.jvm.internal.impl.metadata.q proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        k.f(proto, "proto");
        k.f(nameResolver, "nameResolver");
        Iterable<b> $this$map$iv = (List) proto.getExtension(this.b.k());
        if ($this$map$iv == null) {
            $this$map$iv = q.g();
        }
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (b it : $this$map$iv) {
            arrayList.add(this.a.a(it, nameResolver));
        }
        return arrayList;
    }

    @NotNull
    public List<c> f(@NotNull s proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        k.f(proto, "proto");
        k.f(nameResolver, "nameResolver");
        Iterable<b> $this$map$iv = (List) proto.getExtension(this.b.l());
        if ($this$map$iv == null) {
            $this$map$iv = q.g();
        }
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (b it : $this$map$iv) {
            arrayList.add(this.a.a(it, nameResolver));
        }
        return arrayList;
    }

    @Nullable
    /* renamed from: k */
    public g<?> g(@NotNull a0 container, @NotNull n proto, @NotNull b0 expectedType) {
        k.f(container, "container");
        k.f(proto, "proto");
        k.f(expectedType, "expectedType");
        b.C0379b.c value = (b.C0379b.c) f.a(proto, this.b.b());
        if (value != null) {
            return this.a.f(expectedType, value, container.b());
        }
        return null;
    }
}

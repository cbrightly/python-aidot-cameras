package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.b;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.g;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.a;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import kotlin.reflect.jvm.internal.impl.metadata.l;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmProtoBufUtil.kt */
public final class i {
    @NotNull
    private static final f a;
    public static final i b = new i();

    static {
        f p1 = f.d();
        a.a(p1);
        k.b(p1, "ExtensionRegistryLite.ne…f::registerAllExtensions)");
        a = p1;
    }

    private i() {
    }

    @NotNull
    public final f a() {
        return a;
    }

    @NotNull
    public static final n<g, c> i(@NotNull String[] data, @NotNull String[] strings) {
        k.f(data, "data");
        k.f(strings, "strings");
        byte[] e = a.e(data);
        k.b(e, "BitEncoding.decodeBytes(data)");
        return h(e, strings);
    }

    @NotNull
    public static final n<g, c> h(@NotNull byte[] bytes, @NotNull String[] strings) {
        k.f(bytes, "bytes");
        k.f(strings, "strings");
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return new n<>(b.k(input, strings), c.parseFrom(input, a));
    }

    @NotNull
    public static final n<g, l> m(@NotNull String[] data, @NotNull String[] strings) {
        k.f(data, "data");
        k.f(strings, "strings");
        byte[] e = a.e(data);
        k.b(e, "BitEncoding.decodeBytes(data)");
        return l(e, strings);
    }

    @NotNull
    public static final n<g, l> l(@NotNull byte[] bytes, @NotNull String[] strings) {
        k.f(bytes, "bytes");
        k.f(strings, "strings");
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return new n<>(b.k(input, strings), l.parseFrom(input, a));
    }

    @NotNull
    public static final n<g, kotlin.reflect.jvm.internal.impl.metadata.i> j(@NotNull String[] data, @NotNull String[] strings) {
        k.f(data, "data");
        k.f(strings, "strings");
        ByteArrayInputStream input = new ByteArrayInputStream(a.e(data));
        return new n<>(b.k(input, strings), kotlin.reflect.jvm.internal.impl.metadata.i.parseFrom(input, a));
    }

    private final g k(@NotNull InputStream $this$readNameResolver, String[] strings) {
        a.e parseDelimitedFrom = a.e.parseDelimitedFrom($this$readNameResolver, a);
        k.b(parseDelimitedFrom, "JvmProtoBuf.StringTableT…this, EXTENSION_REGISTRY)");
        return new g(parseDelimitedFrom, strings);
    }

    @Nullable
    public final e.b e(@NotNull kotlin.reflect.jvm.internal.impl.metadata.i proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull h typeTable) {
        String desc;
        kotlin.reflect.jvm.internal.impl.metadata.i iVar = proto;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar = nameResolver;
        h hVar = typeTable;
        k.f(iVar, "proto");
        k.f(cVar, "nameResolver");
        k.f(hVar, "typeTable");
        h.f<kotlin.reflect.jvm.internal.impl.metadata.i, a.c> fVar = a.b;
        k.b(fVar, "JvmProtoBuf.methodSignature");
        a.c signature = (a.c) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a(iVar, fVar);
        int name = (signature == null || !signature.hasName()) ? proto.getName() : signature.getName();
        if (signature == null || !signature.hasDesc()) {
            List k = q.k(g.g(iVar, hVar));
            Iterable<u> $this$mapTo$iv$iv = proto.getValueParameterList();
            k.b($this$mapTo$iv$iv, "proto.valueParameterList");
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (u it : $this$mapTo$iv$iv) {
                k.b(it, "it");
                destination$iv$iv.add(g.m(it, hVar));
            }
            Iterable<kotlin.reflect.jvm.internal.impl.metadata.q> $this$map$iv = y.n0(k, destination$iv$iv);
            Collection destination$iv$iv2 = new ArrayList(r.r($this$map$iv, 10));
            for (kotlin.reflect.jvm.internal.impl.metadata.q it2 : $this$map$iv) {
                String g = b.g(it2, cVar);
                if (g == null) {
                    return null;
                }
                destination$iv$iv2.add(g);
            }
            String returnTypeDesc = g(g.i(iVar, hVar), cVar);
            if (returnTypeDesc == null) {
                return null;
            }
            desc = y.b0(destination$iv$iv2, "", "(", ")", 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 56, (Object) null) + returnTypeDesc;
        } else {
            desc = cVar.getString(signature.getDesc());
        }
        return new e.b(cVar.getString(name), desc);
    }

    @Nullable
    public final e.b b(@NotNull d proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.h typeTable) {
        String name;
        String desc;
        d dVar = proto;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar = nameResolver;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.h hVar = typeTable;
        k.f(dVar, "proto");
        k.f(cVar, "nameResolver");
        k.f(hVar, "typeTable");
        h.f<d, a.c> fVar = a.a;
        k.b(fVar, "JvmProtoBuf.constructorSignature");
        a.c signature = (a.c) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a(dVar, fVar);
        if (signature == null || !signature.hasName()) {
            name = "<init>";
        } else {
            name = cVar.getString(signature.getName());
        }
        if (signature == null || !signature.hasDesc()) {
            Iterable<u> $this$mapTo$iv$iv = proto.getValueParameterList();
            k.b($this$mapTo$iv$iv, "proto.valueParameterList");
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (u it : $this$mapTo$iv$iv) {
                i iVar = b;
                k.b(it, "it");
                String g = iVar.g(g.m(it, hVar), cVar);
                if (g == null) {
                    return null;
                }
                destination$iv$iv.add(g);
            }
            desc = y.b0(destination$iv$iv, "", "(", ")V", 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 56, (Object) null);
        } else {
            desc = cVar.getString(signature.getDesc());
        }
        return new e.b(name, desc);
    }

    public static /* synthetic */ e.a d(i iVar, kotlin.reflect.jvm.internal.impl.metadata.n nVar, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar, kotlin.reflect.jvm.internal.impl.metadata.deserialization.h hVar, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return iVar.c(nVar, cVar, hVar, z);
    }

    @Nullable
    public final e.a c(@NotNull kotlin.reflect.jvm.internal.impl.metadata.n proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.h typeTable, boolean requireHasFieldFlag) {
        String desc;
        k.f(proto, "proto");
        k.f(nameResolver, "nameResolver");
        k.f(typeTable, "typeTable");
        h.f<kotlin.reflect.jvm.internal.impl.metadata.n, a.d> fVar = a.d;
        k.b(fVar, "JvmProtoBuf.propertySignature");
        a.d signature = (a.d) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a(proto, fVar);
        if (signature == null) {
            return null;
        }
        a.b field = signature.hasField() ? signature.getField() : null;
        if (field == null && requireHasFieldFlag) {
            return null;
        }
        int name = (field == null || !field.hasName()) ? proto.getName() : field.getName();
        if (field == null || !field.hasDesc()) {
            String g = g(g.j(proto, typeTable), nameResolver);
            if (g == null) {
                return null;
            }
            desc = g;
        } else {
            desc = nameResolver.getString(field.getDesc());
        }
        return new e.a(nameResolver.getString(name), desc);
    }

    private final String g(kotlin.reflect.jvm.internal.impl.metadata.q type, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        if (type.hasClassName()) {
            return b.a(nameResolver.b(type.getClassName()));
        }
        return null;
    }

    public static final boolean f(@NotNull kotlin.reflect.jvm.internal.impl.metadata.n proto) {
        k.f(proto, "proto");
        b.C0386b a2 = d.b.a();
        Object extension = proto.getExtension(a.e);
        k.b(extension, "proto.getExtension(JvmProtoBuf.flags)");
        Boolean g = a2.d(((Number) extension).intValue());
        k.b(g, "JvmFlags.IS_MOVED_FROM_I…nsion(JvmProtoBuf.flags))");
        return g.booleanValue();
    }
}

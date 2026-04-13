package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.a;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassData.kt */
public final class h {
    @NotNull
    private final c a;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.metadata.c b;
    @NotNull
    private final a c;
    @NotNull
    private final o0 d;

    @NotNull
    public final c a() {
        return this.a;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.metadata.c b() {
        return this.b;
    }

    @NotNull
    public final a c() {
        return this.c;
    }

    @NotNull
    public final o0 d() {
        return this.d;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return k.a(this.a, hVar.a) && k.a(this.b, hVar.b) && k.a(this.c, hVar.c) && k.a(this.d, hVar.d);
    }

    public int hashCode() {
        c cVar = this.a;
        int i = 0;
        int hashCode = (cVar != null ? cVar.hashCode() : 0) * 31;
        kotlin.reflect.jvm.internal.impl.metadata.c cVar2 = this.b;
        int hashCode2 = (hashCode + (cVar2 != null ? cVar2.hashCode() : 0)) * 31;
        a aVar = this.c;
        int hashCode3 = (hashCode2 + (aVar != null ? aVar.hashCode() : 0)) * 31;
        o0 o0Var = this.d;
        if (o0Var != null) {
            i = o0Var.hashCode();
        }
        return hashCode3 + i;
    }

    @NotNull
    public String toString() {
        return "ClassData(nameResolver=" + this.a + ", classProto=" + this.b + ", metadataVersion=" + this.c + ", sourceElement=" + this.d + ")";
    }

    public h(@NotNull c nameResolver, @NotNull kotlin.reflect.jvm.internal.impl.metadata.c classProto, @NotNull a metadataVersion, @NotNull o0 sourceElement) {
        k.f(nameResolver, "nameResolver");
        k.f(classProto, "classProto");
        k.f(metadataVersion, "metadataVersion");
        k.f(sourceElement, "sourceElement");
        this.a = nameResolver;
        this.b = classProto;
        this.c = metadataVersion;
        this.d = sourceElement;
    }
}

package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProtoContainer.kt */
public abstract class a0 {
    @NotNull
    private final c a;
    @NotNull
    private final h b;
    @Nullable
    private final o0 c;

    @NotNull
    public abstract kotlin.reflect.jvm.internal.impl.name.b a();

    private a0(c nameResolver, h typeTable, o0 source) {
        this.a = nameResolver;
        this.b = typeTable;
        this.c = source;
    }

    public /* synthetic */ a0(c nameResolver, h typeTable, o0 source, DefaultConstructorMarker $constructor_marker) {
        this(nameResolver, typeTable, source);
    }

    @NotNull
    public final c b() {
        return this.a;
    }

    @NotNull
    public final h d() {
        return this.b;
    }

    @Nullable
    public final o0 c() {
        return this.c;
    }

    /* compiled from: ProtoContainer.kt */
    public static final class a extends a0 {
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a d;
        @NotNull
        private final c.C0384c e;
        private final boolean f;
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.metadata.c g;
        @Nullable
        private final a h;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull kotlin.reflect.jvm.internal.impl.metadata.c classProto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull h typeTable, @Nullable o0 source, @Nullable a outerClass) {
            super(nameResolver, typeTable, source, (DefaultConstructorMarker) null);
            k.f(classProto, "classProto");
            k.f(nameResolver, "nameResolver");
            k.f(typeTable, "typeTable");
            this.g = classProto;
            this.h = outerClass;
            this.d = y.a(nameResolver, classProto.getFqName());
            c.C0384c d2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.e.d(classProto.getFlags());
            this.e = d2 == null ? c.C0384c.CLASS : d2;
            Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.f.d(classProto.getFlags());
            k.b(g2, "Flags.IS_INNER.get(classProto.flags)");
            this.f = g2.booleanValue();
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.metadata.c f() {
            return this.g;
        }

        @Nullable
        public final a h() {
            return this.h;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a e() {
            return this.d;
        }

        @NotNull
        public final c.C0384c g() {
            return this.e;
        }

        public final boolean i() {
            return this.f;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.name.b a() {
            kotlin.reflect.jvm.internal.impl.name.b b = this.d.b();
            k.b(b, "classId.asSingleFqName()");
            return b;
        }
    }

    /* compiled from: ProtoContainer.kt */
    public static final class b extends a0 {
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.b d;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull h typeTable, @Nullable o0 source) {
            super(nameResolver, typeTable, source, (DefaultConstructorMarker) null);
            k.f(fqName, "fqName");
            k.f(nameResolver, "nameResolver");
            k.f(typeTable, "typeTable");
            this.d = fqName;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.name.b a() {
            return this.d;
        }
    }

    @NotNull
    public String toString() {
        return getClass().getSimpleName() + ": " + a();
    }
}

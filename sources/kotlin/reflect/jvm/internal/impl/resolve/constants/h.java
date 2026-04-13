package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ConstantValueFactory.kt */
public final class h {
    public static final h a = new h();

    /* compiled from: ConstantValueFactory.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<y, b0> {
        final /* synthetic */ b0 $type;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b0 b0Var) {
            super(1);
            this.$type = b0Var;
        }

        @NotNull
        public final b0 invoke(@NotNull y it) {
            k.f(it, "it");
            return this.$type;
        }
    }

    private h() {
    }

    @NotNull
    public final b b(@NotNull List<? extends g<?>> value, @NotNull b0 type) {
        k.f(value, "value");
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return new b(value, new a(type));
    }

    @Nullable
    public final g<?> c(@Nullable Object value) {
        if (value instanceof Byte) {
            return new d(((Number) value).byteValue());
        }
        if (value instanceof Short) {
            return new v(((Number) value).shortValue());
        }
        if (value instanceof Integer) {
            return new m(((Number) value).intValue());
        }
        if (value instanceof Long) {
            return new s(((Number) value).longValue());
        }
        if (value instanceof Character) {
            return new e(((Character) value).charValue());
        }
        if (value instanceof Float) {
            return new l(((Number) value).floatValue());
        }
        if (value instanceof Double) {
            return new i(((Number) value).doubleValue());
        }
        if (value instanceof Boolean) {
            return new c(((Boolean) value).booleanValue());
        }
        if (value instanceof String) {
            return new w((String) value);
        }
        if (value instanceof byte[]) {
            return a(kotlin.collections.l.O((byte[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.BYTE);
        }
        if (value instanceof short[]) {
            return a(kotlin.collections.l.V((short[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.SHORT);
        }
        if (value instanceof int[]) {
            return a(kotlin.collections.l.S((int[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.INT);
        }
        if (value instanceof long[]) {
            return a(kotlin.collections.l.T((long[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.LONG);
        }
        if (value instanceof char[]) {
            return a(kotlin.collections.l.P((char[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.CHAR);
        }
        if (value instanceof float[]) {
            return a(kotlin.collections.l.R((float[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.FLOAT);
        }
        if (value instanceof double[]) {
            return a(kotlin.collections.l.Q((double[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.DOUBLE);
        }
        if (value instanceof boolean[]) {
            return a(kotlin.collections.l.W((boolean[]) value), kotlin.reflect.jvm.internal.impl.builtins.h.BOOLEAN);
        }
        if (value == null) {
            return new t();
        }
        return null;
    }

    /* compiled from: ConstantValueFactory.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<y, i0> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.builtins.h $componentType;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(kotlin.reflect.jvm.internal.impl.builtins.h hVar) {
            super(1);
            this.$componentType = hVar;
        }

        @NotNull
        public final i0 invoke(@NotNull y module) {
            k.f(module, "module");
            i0 P = module.j().P(this.$componentType);
            k.b(P, "module.builtIns.getPrimi…KotlinType(componentType)");
            return P;
        }
    }

    private final b a(List<?> value, kotlin.reflect.jvm.internal.impl.builtins.h componentType) {
        Iterable<Object> $this$mapNotNullTo$iv$iv = kotlin.collections.y.C0(value);
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$mapNotNullTo$iv$iv) {
            Object p1 = c(element$iv$iv);
            if (p1 != null) {
                arrayList.add(p1);
            }
        }
        return new b(arrayList, new b(componentType));
    }
}

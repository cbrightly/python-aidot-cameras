package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ReflectJavaType.kt */
public abstract class w implements v {
    public static final a a = new a((DefaultConstructorMarker) null);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Type J();

    /* compiled from: ReflectJavaType.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final w a(@NotNull Type type) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if ((type instanceof Class) && ((Class) type).isPrimitive()) {
                return new v((Class) type);
            }
            if ((type instanceof GenericArrayType) || ((type instanceof Class) && ((Class) type).isArray())) {
                return new i(type);
            }
            if (type instanceof WildcardType) {
                return new z((WildcardType) type);
            }
            return new l(type);
        }
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof w) && k.a(J(), ((w) other).J());
    }

    public int hashCode() {
        return J().hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + J();
    }
}

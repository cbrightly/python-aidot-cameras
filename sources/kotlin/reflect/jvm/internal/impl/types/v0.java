package kotlin.reflect.jvm.internal.impl.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSubstitution.kt */
public abstract class v0 extends z0 {
    public static final a c = new a((DefaultConstructorMarker) null);

    @NotNull
    public static final z0 h(@NotNull u0 u0Var, @NotNull List<? extends w0> list) {
        return c.b(u0Var, list);
    }

    @NotNull
    public static final v0 i(@NotNull Map<u0, ? extends w0> map) {
        return a.d(c, map, false, 2, (Object) null);
    }

    @Nullable
    public abstract w0 j(@NotNull u0 u0Var);

    @Nullable
    public w0 e(@NotNull b0 key) {
        k.f(key, CacheEntity.KEY);
        return j(key.I0());
    }

    /* compiled from: TypeSubstitution.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static /* synthetic */ v0 d(a aVar, Map map, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return aVar.c(map, z);
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.types.v0$a$a  reason: collision with other inner class name */
        /* compiled from: TypeSubstitution.kt */
        public static final class C0430a extends v0 {
            final /* synthetic */ Map d;
            final /* synthetic */ boolean e;

            C0430a(Map $captured_local_variable$0, boolean $captured_local_variable$1) {
                this.d = $captured_local_variable$0;
                this.e = $captured_local_variable$1;
            }

            @Nullable
            public w0 j(@NotNull u0 key) {
                k.f(key, CacheEntity.KEY);
                return (w0) this.d.get(key);
            }

            public boolean f() {
                return this.d.isEmpty();
            }

            public boolean a() {
                return this.e;
            }
        }

        @NotNull
        public final v0 c(@NotNull Map<u0, ? extends w0> map, boolean approximateCapturedTypes) {
            k.f(map, "map");
            return new C0430a(map, approximateCapturedTypes);
        }

        @NotNull
        public final z0 a(@NotNull b0 kotlinType) {
            k.f(kotlinType, "kotlinType");
            return b(kotlinType.I0(), kotlinType.H0());
        }

        @NotNull
        public final z0 b(@NotNull u0 typeConstructor, @NotNull List<? extends w0> arguments) {
            k.f(typeConstructor, "typeConstructor");
            k.f(arguments, "arguments");
            List parameters = typeConstructor.getParameters();
            k.b(parameters, "typeConstructor.parameters");
            t0 t0Var = (t0) y.f0(parameters);
            if (!(t0Var != null ? t0Var.N() : false)) {
                return new z(parameters, arguments);
            }
            Iterable parameters2 = typeConstructor.getParameters();
            k.b(parameters2, "typeConstructor.parameters");
            Iterable<t0> $this$map$iv = parameters2;
            Collection destination$iv$iv = new ArrayList(r.r($this$map$iv, 10));
            for (t0 it : $this$map$iv) {
                k.b(it, "it");
                destination$iv$iv.add(it.i());
            }
            return d(this, l0.o(y.K0(destination$iv$iv, arguments)), false, 2, (Object) null);
        }
    }
}

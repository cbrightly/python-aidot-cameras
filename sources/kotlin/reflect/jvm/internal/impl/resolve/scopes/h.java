package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: MemberScope.kt */
public interface h extends j {
    public static final a a = a.b;

    @NotNull
    Set<f> a();

    @NotNull
    Collection<? extends n0> b(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b bVar);

    @NotNull
    Collection<? extends i0> e(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b bVar);

    @NotNull
    Set<f> f();

    /* compiled from: MemberScope.kt */
    public static final class b extends i {
        public static final b b = new b();

        private b() {
        }

        @NotNull
        public Set<f> a() {
            return o0.d();
        }

        @NotNull
        public Set<f> f() {
            return o0.d();
        }
    }

    /* compiled from: MemberScope.kt */
    public static final class a {
        @NotNull
        private static final l<f, Boolean> a = C0413a.INSTANCE;
        static final /* synthetic */ a b = new a();

        /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.scopes.h$a$a  reason: collision with other inner class name */
        /* compiled from: MemberScope.kt */
        public static final class C0413a extends kotlin.jvm.internal.l implements l<f, Boolean> {
            public static final C0413a INSTANCE = new C0413a();

            C0413a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((f) obj));
            }

            public final boolean invoke(@NotNull f it) {
                k.f(it, "it");
                return true;
            }
        }

        private a() {
        }

        @NotNull
        public final l<f, Boolean> a() {
            return a;
        }
    }
}

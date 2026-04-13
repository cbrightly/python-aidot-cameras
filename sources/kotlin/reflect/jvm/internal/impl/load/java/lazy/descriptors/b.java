package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeclaredMemberIndex.kt */
public interface b {
    @NotNull
    Set<f> a();

    @NotNull
    Set<f> b();

    @NotNull
    Collection<q> c(@NotNull f fVar);

    @Nullable
    n d(@NotNull f fVar);

    /* compiled from: DeclaredMemberIndex.kt */
    public static final class a implements b {
        public static final a a = new a();

        private a() {
        }

        @NotNull
        /* renamed from: e */
        public List<q> c(@NotNull f name) {
            k.f(name, "name");
            return kotlin.collections.q.g();
        }

        @NotNull
        public Set<f> a() {
            return o0.d();
        }

        @Nullable
        public n d(@NotNull f name) {
            k.f(name, "name");
            return null;
        }

        @NotNull
        public Set<f> b() {
            return o0.d();
        }
    }
}

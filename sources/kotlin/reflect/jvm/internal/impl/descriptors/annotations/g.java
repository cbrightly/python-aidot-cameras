package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Annotations.kt */
public interface g extends Iterable<c>, kotlin.jvm.internal.markers.a {
    public static final a b = a.b;

    boolean I(@NotNull kotlin.reflect.jvm.internal.impl.name.b bVar);

    @Nullable
    c c(@NotNull kotlin.reflect.jvm.internal.impl.name.b bVar);

    boolean isEmpty();

    /* compiled from: Annotations.kt */
    public static final class b {
        @Nullable
        public static c a(g $this, @NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
            Object element$iv;
            k.f(fqName, "fqName");
            Iterator it = $this.iterator();
            while (true) {
                if (!it.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it.next();
                if (k.a(((c) element$iv).e(), fqName)) {
                    break;
                }
            }
            return (c) element$iv;
        }

        public static boolean b(g $this, @NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
            k.f(fqName, "fqName");
            return $this.c(fqName) != null;
        }
    }

    /* compiled from: Annotations.kt */
    public static final class a {
        @NotNull
        private static final g a = new C0349a();
        static final /* synthetic */ a b = new a();

        /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a$a  reason: collision with other inner class name */
        /* compiled from: Annotations.kt */
        public static final class C0349a implements g {
            C0349a() {
            }

            public boolean I(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
                k.f(fqName, "fqName");
                return b.b(this, fqName);
            }

            public /* bridge */ /* synthetic */ c c(kotlin.reflect.jvm.internal.impl.name.b bVar) {
                return (c) a(bVar);
            }

            public boolean isEmpty() {
                return true;
            }

            @Nullable
            public Void a(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
                k.f(fqName, "fqName");
                return null;
            }

            @NotNull
            public Iterator<c> iterator() {
                return q.g().iterator();
            }

            @NotNull
            public String toString() {
                return "EMPTY";
            }
        }

        private a() {
        }

        @NotNull
        public final g b() {
            return a;
        }

        @NotNull
        public final g a(@NotNull List<? extends c> annotations) {
            k.f(annotations, "annotations");
            return annotations.isEmpty() ? a : new h(annotations);
        }
    }
}

package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.l;
import kotlin.collections.y;
import kotlin.sequences.h;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Annotations.kt */
public final class k implements g {
    private final List<g> c;

    public k(@NotNull List<? extends g> delegates) {
        kotlin.jvm.internal.k.f(delegates, "delegates");
        this.c = delegates;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public k(@NotNull g... delegates) {
        this((List<? extends g>) l.U(delegates));
        kotlin.jvm.internal.k.f(delegates, "delegates");
    }

    public boolean isEmpty() {
        List<g> list = this.c;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (g it : list) {
            if (!it.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean I(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        kotlin.jvm.internal.k.f(fqName, "fqName");
        for (g it : y.L(this.c)) {
            if (it.I(fqName)) {
                return true;
            }
        }
        return false;
    }

    /* compiled from: Annotations.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<g, c> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.name.b $fqName;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlin.reflect.jvm.internal.impl.name.b bVar) {
            super(1);
            this.$fqName = bVar;
        }

        @Nullable
        public final c invoke(@NotNull g it) {
            kotlin.jvm.internal.k.f(it, "it");
            return it.c(this.$fqName);
        }
    }

    @Nullable
    public c c(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        kotlin.jvm.internal.k.f(fqName, "fqName");
        return (c) o.r(o.x(y.L(this.c), new a(fqName)));
    }

    /* compiled from: Annotations.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<g, h<? extends c>> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final h<c> invoke(@NotNull g it) {
            kotlin.jvm.internal.k.f(it, "it");
            return y.L(it);
        }
    }

    @NotNull
    public Iterator<c> iterator() {
        return o.s(y.L(this.c), b.INSTANCE).iterator();
    }
}

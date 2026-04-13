package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.metadata.v;
import kotlin.reflect.jvm.internal.impl.metadata.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: VersionRequirement.kt */
public final class k {
    /* access modifiers changed from: private */
    @NotNull
    public static final k a = new k(q.g());
    public static final a b = new a((DefaultConstructorMarker) null);
    private final List<v> c;

    private k(List<v> infos) {
        this.c = infos;
    }

    public /* synthetic */ k(List infos, DefaultConstructorMarker $constructor_marker) {
        this(infos);
    }

    @Nullable
    public final v b(int id) {
        return (v) y.V(this.c, id);
    }

    /* compiled from: VersionRequirement.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final k b() {
            return k.a;
        }

        @NotNull
        public final k a(@NotNull w table) {
            kotlin.jvm.internal.k.f(table, "table");
            if (table.getRequirementCount() == 0) {
                return b();
            }
            List<v> requirementList = table.getRequirementList();
            kotlin.jvm.internal.k.b(requirementList, "table.requirementList");
            return new k(requirementList, (DefaultConstructorMarker) null);
        }
    }
}

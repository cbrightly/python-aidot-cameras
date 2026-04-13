package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.l0;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Jsr305State.kt */
public final class e {
    @NotNull
    public static final e a = new e(h.WARN, (h) null, l0.f(), false, 8, (DefaultConstructorMarker) null);
    @NotNull
    public static final e b;
    @NotNull
    public static final e c;
    public static final a d = new a((DefaultConstructorMarker) null);
    @NotNull
    private final g e;
    @NotNull
    private final h f;
    @Nullable
    private final h g;
    @NotNull
    private final Map<String, h> h;
    private final boolean i;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return k.a(this.f, eVar.f) && k.a(this.g, eVar.g) && k.a(this.h, eVar.h) && this.i == eVar.i;
    }

    public int hashCode() {
        h hVar = this.f;
        int i2 = 0;
        int hashCode = (hVar != null ? hVar.hashCode() : 0) * 31;
        h hVar2 = this.g;
        int hashCode2 = (hashCode + (hVar2 != null ? hVar2.hashCode() : 0)) * 31;
        Map<String, h> map = this.h;
        if (map != null) {
            i2 = map.hashCode();
        }
        int i3 = (hashCode2 + i2) * 31;
        boolean z = this.i;
        if (z) {
            z = true;
        }
        return i3 + (z ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "Jsr305State(global=" + this.f + ", migration=" + this.g + ", user=" + this.h + ", enableCompatqualCheckerFrameworkAnnotations=" + this.i + ")";
    }

    public e(@NotNull h global, @Nullable h migration, @NotNull Map<String, ? extends h> user, boolean enableCompatqualCheckerFrameworkAnnotations) {
        k.f(global, "global");
        k.f(user, "user");
        this.f = global;
        this.g = migration;
        this.h = user;
        this.i = enableCompatqualCheckerFrameworkAnnotations;
        this.e = i.b(new b(this));
    }

    @NotNull
    public final h c() {
        return this.f;
    }

    @Nullable
    public final h d() {
        return this.g;
    }

    @NotNull
    public final Map<String, h> e() {
        return this.h;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e(h hVar, h hVar2, Map map, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(hVar, hVar2, map, (i2 & 8) != 0 ? true : z);
    }

    public final boolean b() {
        return this.i;
    }

    /* compiled from: Jsr305State.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<String[]> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final String[] invoke() {
            ArrayList thisCollection$iv = new ArrayList();
            thisCollection$iv.add(this.this$0.c().getDescription());
            h it = this.this$0.d();
            if (it != null) {
                thisCollection$iv.add("under-migration:" + it.getDescription());
            }
            for (Map.Entry it2 : this.this$0.e().entrySet()) {
                thisCollection$iv.add('@' + ((String) it2.getKey()) + ':' + ((h) it2.getValue()).getDescription());
            }
            Object[] array = thisCollection$iv.toArray(new String[0]);
            if (array != null) {
                return (String[]) array;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    public final boolean a() {
        return this == b;
    }

    /* compiled from: Jsr305State.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        h hVar = h.IGNORE;
        b = new e(hVar, hVar, l0.f(), false, 8, (DefaultConstructorMarker) null);
        h hVar2 = h.STRICT;
        c = new e(hVar2, hVar2, l0.f(), false, 8, (DefaultConstructorMarker) null);
    }
}

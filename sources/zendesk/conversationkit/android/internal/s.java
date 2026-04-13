package zendesk.conversationkit.android.internal;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.c;
import zendesk.conversationkit.android.d;
import zendesk.conversationkit.android.g;

/* compiled from: EffectProcessor.kt */
public abstract class s {
    @Nullable
    private final a a;
    @NotNull
    private final List<d> b;
    @NotNull
    private final List<c> c;

    public /* synthetic */ s(a aVar, List list, List list2, DefaultConstructorMarker defaultConstructorMarker) {
        this(aVar, list, list2);
    }

    private s(a newAccessLevel, List<? extends d> events, List<? extends c> supplementaryActions) {
        this.a = newAccessLevel;
        this.b = events;
        this.c = supplementaryActions;
    }

    @Nullable
    public a b() {
        return this.a;
    }

    @NotNull
    public List<d> a() {
        return this.b;
    }

    @NotNull
    public List<c> c() {
        return this.c;
    }

    /* compiled from: EffectProcessor.kt */
    public static final class a extends s {
        @Nullable
        private final a d;
        @NotNull
        private final List<d> e;
        @NotNull
        private final List<c> f;
        @NotNull
        private final c g;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(b(), aVar.b()) && k.a(a(), aVar.a()) && k.a(c(), aVar.c()) && k.a(this.g, aVar.g);
        }

        public int hashCode() {
            return ((((((b() == null ? 0 : b().hashCode()) * 31) + a().hashCode()) * 31) + c().hashCode()) * 31) + this.g.hashCode();
        }

        @NotNull
        public String toString() {
            return "Continues(newAccessLevel=" + b() + ", events=" + a() + ", supplementaryActions=" + c() + ", followingAction=" + this.g + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(a aVar, List list, List list2, c cVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : aVar, (i & 2) != 0 ? q.g() : list, (i & 4) != 0 ? q.g() : list2, cVar);
        }

        @Nullable
        public a b() {
            return this.d;
        }

        @NotNull
        public List<d> a() {
            return this.e;
        }

        @NotNull
        public List<c> c() {
            return this.f;
        }

        @NotNull
        public final c d() {
            return this.g;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@Nullable a newAccessLevel, @NotNull List<? extends d> events, @NotNull List<? extends c> supplementaryActions, @NotNull c followingAction) {
            super(newAccessLevel, events, supplementaryActions, (DefaultConstructorMarker) null);
            k.e(events, "events");
            k.e(supplementaryActions, "supplementaryActions");
            k.e(followingAction, "followingAction");
            this.d = newAccessLevel;
            this.e = events;
            this.f = supplementaryActions;
            this.g = followingAction;
        }
    }

    /* compiled from: EffectProcessor.kt */
    public static final class b extends s {
        @Nullable
        private final a d;
        @NotNull
        private final List<d> e;
        @NotNull
        private final List<c> f;
        @NotNull
        private final g<Object> g;

        public b() {
            this((a) null, (List) null, (List) null, (g) null, 15, (DefaultConstructorMarker) null);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(b(), bVar.b()) && k.a(a(), bVar.a()) && k.a(c(), bVar.c()) && k.a(this.g, bVar.g);
        }

        public int hashCode() {
            return ((((((b() == null ? 0 : b().hashCode()) * 31) + a().hashCode()) * 31) + c().hashCode()) * 31) + this.g.hashCode();
        }

        @NotNull
        public String toString() {
            return "Ends(newAccessLevel=" + b() + ", events=" + a() + ", supplementaryActions=" + c() + ", result=" + this.g + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ b(a aVar, List list, List list2, g gVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : aVar, (i & 2) != 0 ? q.g() : list, (i & 4) != 0 ? q.g() : list2, (i & 8) != 0 ? new g.a(c.C0503c.INSTANCE) : gVar);
        }

        @Nullable
        public a b() {
            return this.d;
        }

        @NotNull
        public List<d> a() {
            return this.e;
        }

        @NotNull
        public List<c> c() {
            return this.f;
        }

        @NotNull
        public final g<Object> d() {
            return this.g;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@Nullable a newAccessLevel, @NotNull List<? extends d> events, @NotNull List<? extends c> supplementaryActions, @NotNull g<? extends Object> result) {
            super(newAccessLevel, events, supplementaryActions, (DefaultConstructorMarker) null);
            k.e(events, "events");
            k.e(supplementaryActions, "supplementaryActions");
            k.e(result, "result");
            this.d = newAccessLevel;
            this.e = events;
            this.f = supplementaryActions;
            this.g = result;
        }
    }
}

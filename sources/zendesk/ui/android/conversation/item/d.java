package zendesk.ui.android.conversation.item;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemRendering.kt */
public final class d {
    @Nullable
    private final l<a<?>, x> a;
    @NotNull
    private final e b;

    public d(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
        this.b = builder.c();
    }

    @Nullable
    public final l<a<?>, x> a() {
        return this.a;
    }

    @NotNull
    public final e b() {
        return this.b;
    }

    public d() {
        this(new a());
    }

    @NotNull
    public final a c() {
        return new a(this);
    }

    /* compiled from: ItemRendering.kt */
    public static final class a {
        @Nullable
        private l<? super a<?>, x> a;
        @NotNull
        private e b;

        public a() {
            this.b = new e((a) null, (Integer) null, (Integer) null, 7, (DefaultConstructorMarker) null);
        }

        @Nullable
        public final l<a<?>, x> b() {
            return this.a;
        }

        public final void e(@Nullable l<? super a<?>, x> lVar) {
            this.a = lVar;
        }

        @NotNull
        public final e c() {
            return this.b;
        }

        public final void f(@NotNull e eVar) {
            k.e(eVar, "<set-?>");
            this.b = eVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull d rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
            this.b = rendering.b();
        }

        @NotNull
        public final <T> a d(@NotNull l<? super a<T>, x> onItemClicked) {
            k.e(onItemClicked, "onItemClicked");
            e(onItemClicked);
            return this;
        }

        @NotNull
        public final a g(@NotNull l<? super e, e> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            f(stateUpdate.invoke(c()));
            return this;
        }

        @NotNull
        public final d a() {
            return new d(this);
        }
    }
}

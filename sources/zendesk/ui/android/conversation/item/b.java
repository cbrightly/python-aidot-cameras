package zendesk.ui.android.conversation.item;

import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemGroupRendering.kt */
public final class b {
    @Nullable
    private final l<a<?>, x> a;
    @NotNull
    private final c b;

    public b(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
        this.b = builder.c();
    }

    @Nullable
    public final l<a<?>, x> a() {
        return this.a;
    }

    @NotNull
    public final c b() {
        return this.b;
    }

    public b() {
        this(new a());
    }

    @NotNull
    public final a c() {
        return new a(this);
    }

    /* compiled from: ItemGroupRendering.kt */
    public static final class a {
        @Nullable
        private l<? super a<?>, x> a;
        @NotNull
        private c b;

        public a() {
            this.b = new c((List) null, (Integer) null, (Integer) null, 7, (DefaultConstructorMarker) null);
        }

        @Nullable
        public final l<a<?>, x> b() {
            return this.a;
        }

        public final void e(@Nullable l<? super a<?>, x> lVar) {
            this.a = lVar;
        }

        @NotNull
        public final c c() {
            return this.b;
        }

        public final void f(@NotNull c cVar) {
            k.e(cVar, "<set-?>");
            this.b = cVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull b rendering) {
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
        public final a g(@NotNull l<? super c, c> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            f(stateUpdate.invoke(c()));
            return this;
        }

        @NotNull
        public final b a() {
            return new b(this);
        }
    }
}

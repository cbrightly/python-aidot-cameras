package zendesk.ui.android.conversation.header;

import android.net.Uri;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConversationHeaderRendering.kt */
public final class b {
    @Nullable
    private final kotlin.jvm.functions.a<x> a;
    @NotNull
    private final c b;

    public b(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
        this.b = builder.c();
    }

    @Nullable
    public final kotlin.jvm.functions.a<x> a() {
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

    /* compiled from: ConversationHeaderRendering.kt */
    public static final class a {
        @Nullable
        private kotlin.jvm.functions.a<x> a;
        @NotNull
        private c b;

        public a() {
            this.b = new c((String) null, (String) null, (Uri) null, (Integer) null, (Integer) null, 31, (DefaultConstructorMarker) null);
        }

        @Nullable
        public final kotlin.jvm.functions.a<x> b() {
            return this.a;
        }

        public final void e(@Nullable kotlin.jvm.functions.a<x> aVar) {
            this.a = aVar;
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
        public final a d(@Nullable kotlin.jvm.functions.a<x> onBackButtonClicked) {
            e(onBackButtonClicked);
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

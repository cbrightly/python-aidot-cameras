package zendesk.ui.android.conversation.avatar;

import android.net.Uri;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AvatarImageRendering.kt */
public final class a {
    @NotNull
    private final b a;

    public a(@NotNull C0556a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
    }

    @NotNull
    public final b a() {
        return this.a;
    }

    public a() {
        this(new C0556a());
    }

    @NotNull
    public final C0556a b() {
        return new C0556a(this);
    }

    /* renamed from: zendesk.ui.android.conversation.avatar.a$a  reason: collision with other inner class name */
    /* compiled from: AvatarImageRendering.kt */
    public static final class C0556a {
        @NotNull
        private b a;

        public C0556a() {
            this.a = new b((Uri) null, false, 0, (Integer) null, (c) null, 31, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final b b() {
            return this.a;
        }

        public final void c(@NotNull b bVar) {
            k.e(bVar, "<set-?>");
            this.a = bVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public C0556a(@NotNull a rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
        }

        @NotNull
        public final C0556a d(@NotNull l<? super b, b> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            c(stateUpdate.invoke(b()));
            return this;
        }

        @NotNull
        public final a a() {
            return new a(this);
        }
    }
}

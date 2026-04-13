package zendesk.ui.android.conversation.unreadmessagedivider;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: UnreadMessageDividerRendering.kt */
public final class a {
    @NotNull
    private final b a;

    public a(@NotNull C0577a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
    }

    @NotNull
    public final b a() {
        return this.a;
    }

    public a() {
        this(new C0577a());
    }

    @NotNull
    public final C0577a b() {
        return new C0577a(this);
    }

    /* renamed from: zendesk.ui.android.conversation.unreadmessagedivider.a$a  reason: collision with other inner class name */
    /* compiled from: UnreadMessageDividerRendering.kt */
    public static final class C0577a {
        @NotNull
        private b a;

        public C0577a() {
            this.a = new b((String) null, (Integer) null, 3, (DefaultConstructorMarker) null);
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
        public C0577a(@NotNull a rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
        }

        @NotNull
        public final C0577a d(@NotNull l<? super b, b> stateUpdate) {
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

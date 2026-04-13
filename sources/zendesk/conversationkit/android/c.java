package zendesk.conversationkit.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConversationKitError.kt */
public abstract class c extends Throwable {
    @NotNull
    private final String message;

    public /* synthetic */ c(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private c(String message2) {
        super(message2);
        this.message = message2;
    }

    @NotNull
    public String getMessage() {
        return this.message;
    }

    /* renamed from: zendesk.conversationkit.android.c$c  reason: collision with other inner class name */
    /* compiled from: ConversationKitError.kt */
    public static final class C0503c extends c {
        @NotNull
        public static final C0503c INSTANCE = new C0503c();

        private C0503c() {
            super("No ConversationKitResult to return for this call.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationKitError.kt */
    public static final class a extends c {
        @NotNull
        public static final a INSTANCE = new a();

        private a() {
            super("The provided parameters were not valid to create an instance of ConversationKit.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationKitError.kt */
    public static final class b extends c {
        @NotNull
        public static final b INSTANCE = new b();

        private b() {
            super("Action cannot be performed in current state of the SDK.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationKitError.kt */
    public static final class d extends c {
        @NotNull
        public static final d INSTANCE = new d();

        private d() {
            super("A user already exists for this client.", (DefaultConstructorMarker) null);
        }
    }
}

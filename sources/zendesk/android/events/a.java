package zendesk.android.events;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskEvent.kt */
public abstract class a {
    public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private a() {
    }

    /* compiled from: ZendeskEvent.kt */
    public static final class b extends a {
        private final int a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && this.a == ((b) obj).a;
        }

        public int hashCode() {
            return this.a;
        }

        @NotNull
        public String toString() {
            return "UnreadMessageCountChanged(currentUnreadCount=" + this.a + ')';
        }

        public final int a() {
            return this.a;
        }

        public b(int currentUnreadCount) {
            super((DefaultConstructorMarker) null);
            this.a = currentUnreadCount;
        }
    }

    /* renamed from: zendesk.android.events.a$a  reason: collision with other inner class name */
    /* compiled from: ZendeskEvent.kt */
    public static final class C0499a extends a {
        @NotNull
        private final Throwable a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C0499a) && k.a(this.a, ((C0499a) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "AuthenticationFailed(error=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0499a(@NotNull Throwable error) {
            super((DefaultConstructorMarker) null);
            k.e(error, "error");
            this.a = error;
        }

        @NotNull
        public final Throwable a() {
            return this.a;
        }
    }
}

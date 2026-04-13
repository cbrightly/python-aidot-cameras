package zendesk.android.messaging;

import android.content.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Messaging.kt */
public interface b {
    @NotNull
    public static final a a = a.a;

    void a(@NotNull Context context);

    int b();

    /* compiled from: Messaging.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();
        @Nullable
        private static c b;

        private a() {
        }

        public final void b(@Nullable c messagingDelegate) {
            b = messagingDelegate;
        }

        public final /* synthetic */ c a() {
            c cVar = b;
            return cVar == null ? new c() : cVar;
        }
    }
}

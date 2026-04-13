package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.k;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConversationKitDispatchers.kt */
public interface j {
    @NotNull
    i0 a();

    @NotNull
    i0 b();

    @NotNull
    i0 c();

    /* compiled from: ConversationKitDispatchers.kt */
    public static final class a {
        @NotNull
        public static i0 c(@NotNull j jVar) {
            k.e(jVar, "this");
            return d1.c();
        }

        @NotNull
        public static i0 a(@NotNull j jVar) {
            k.e(jVar, "this");
            return d1.a();
        }

        @NotNull
        public static i0 b(@NotNull j jVar) {
            k.e(jVar, "this");
            return d1.b();
        }
    }
}

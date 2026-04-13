package zendesk.messaging.android.internal;

import android.content.Context;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.android.internal.conversationscreen.o;

/* compiled from: Environment.kt */
public interface e {
    @NotNull
    public static final a a = a.a;

    @NotNull
    o a();

    /* compiled from: Environment.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        @NotNull
        public final e a(@NotNull Context context) {
            k.e(context, "context");
            return new g(context);
        }
    }
}

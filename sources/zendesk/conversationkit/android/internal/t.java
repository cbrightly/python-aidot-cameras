package zendesk.conversationkit.android.internal;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Environment.kt */
public interface t {
    @NotNull
    public static final a a = a.a;

    @NotNull
    l a();

    @NotNull
    i b();

    /* compiled from: Environment.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        @NotNull
        public final t a(@NotNull Context context) {
            k.e(context, "context");
            return new z(context, (j) null, 2, (DefaultConstructorMarker) null);
        }
    }
}

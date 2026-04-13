package zendesk.messaging.android.push.internal;

import com.squareup.moshi.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NotificationProcessorFactory.kt */
public final class c {
    @NotNull
    public static final c a = new c();
    @Nullable
    private static b b;

    private c() {
    }

    @NotNull
    public final b a() {
        b bVar = b;
        if (bVar != null) {
            return bVar;
        }
        b it = new b((r) null, 1, (DefaultConstructorMarker) null);
        b = it;
        return it;
    }
}
